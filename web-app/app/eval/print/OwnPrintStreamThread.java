package eval.print;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

// TODO migrate to scala code
public class OwnPrintStreamThread extends Thread {

  private OutputStream outputStream;

  private PrintWriter outputWriter;

  private OutputStream errorStream;

  private PrintWriter errorWriter;

  private Long executionStart;

  private Long executionStop;

  private Long compilationStart;

  private Long compilationStop;

  private boolean executing;

  private static final int MAX_OUTPUT = 1000; // 1000 * 2 Bytes...is ok!

  private int bytesWrittenStdErr;
  private int bytesWrittenStdOut;

  public OwnPrintStreamThread() {
    outputStream = new ByteArrayOutputStream();
    outputWriter = new PrintWriter(outputStream, true);

    errorStream = new ByteArrayOutputStream();
    errorWriter = new PrintWriter(errorStream, true);
    bytesWrittenStdErr = 0;
    bytesWrittenStdOut = 0;
  }

  public void out(String x) {
    if (x != null) {
      if (bytesWrittenStdOut + x.length() >= MAX_OUTPUT) {
          return;
      }
      bytesWrittenStdOut += x.length();
      synchronized (this) {
        outputWriter.print(x);
        outputWriter.flush();
      }
    }
  }

  public void err(String x) {
    if (x != null) {
      if (bytesWrittenStdErr + x.length() >= MAX_OUTPUT) {
          return;
      }
      bytesWrittenStdErr += x.length();
      synchronized (this) {
        errorWriter.print(x);
        errorWriter.flush();
      }
    }
  }

  public OutputStream getOutputStream() {
    return outputStream;
  }

  public OutputStream getErrorStream() {
    return errorStream;
  }

  public boolean isExecuting() {
    return executing;
  }
  public void stopExecutingWithoutTime() {
    this.executing = false;
  }

  public void startExecuting() {
    this.executing = true;
    this.executionStart = System.currentTimeMillis();
  }

  public void stopExecuting() {
    this.executing = false;
    this.executionStop = System.currentTimeMillis();
  }

  public void startCompilation() {
    this.compilationStart = System.currentTimeMillis();
  }

  public void stopCompilation() {
    this.compilationStop = System.currentTimeMillis();
  }

  public Long compilationDuration() {
    if (this.compilationStop == null) {
      return null;
    }
    return this.compilationStop - this.compilationStart;
  }

  public Long executionDuration() {
    if (this.executionStop == null) {
      return null;
    }
    return this.executionStop - this.executionStart;
  }
}
