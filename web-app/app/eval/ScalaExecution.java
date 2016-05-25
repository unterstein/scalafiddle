package eval;

import com.twitter.util.OwnEval;
import eval.print.OwnPrintStreamThread;
import play.i18n.Messages;
import security.MySecurityManager;

// TODO migrate to scala code
public class ScalaExecution {

  private static final Long WAIT_DURATION = 5L * 1000; // 5 second

  @SuppressWarnings("deprecation")
  public ScalaExecution(final String script, final ScalaCompilationResult result) {
    if (result == null) {
      return;
    }
    result.script = script;
    result.startTime = System.currentTimeMillis();

    // run
    final OwnEval eval = new OwnEval();
    MySecurityManager.enableCheck();
    try {
      OwnPrintStreamThread thread = new OwnPrintStreamThread() {

        @Override
        public void run() {
          try {
            Object res = eval.apply(script, true);

            result.result = res == null ? "null" : res.toString();
            stopExecutingWithoutTime();
            result.compilationResult = CompilationResult.SUCCESS;
          } catch (RuntimeException e) {
            stopExecutingWithoutTime();
            if (e instanceof SecurityException) {
              SecurityException se = (SecurityException) e;
              if (se.getMessage().contains("exit")) {
                System.err.println(Messages.get("exception.exit"));
              } else {
                System.err.println(Messages.get("error.accessViolation"));
              }
            }
            result.result = e.getMessage();
            result.compilationResult = CompilationResult.FAILED;
          }
        }
      };
      thread.start();
      thread.join(WAIT_DURATION);
      if (thread.isAlive()) {
        // i am sorry, you are dying now!
        thread.stop();
        thread.err(Messages.get("error.tooLong"));
      }
      result.stdOut = thread.getOutputStream().toString();
      result.stdErr = thread.getErrorStream().toString();
      result.compilationTime = thread.compilationDuration();
      result.executionTime = thread.executionDuration();
    } catch (Exception e) {
      // TODO logging
      // something with the thread went wront -> internal logging
    }
    result.endTime = System.currentTimeMillis();
  }

}
