package eval;

import com.google.gson.Gson;
import play.i18n.Messages;

import java.io.Serializable;

// TODO migrate to scala code
public class ScalaCompilationResult implements Serializable {

  public String script;

  public String result;

  public String stdOut;

  public String stdErr;

  public Long startTime;

  public Long endTime;

  public Long compilationTime;

  public Long executionTime;

  public CompilationResult compilationResult;

  public String hash;

  // TODO remove this
  public String stdOutTitle = Messages.get("out.title");

  public String stdErrTitle = Messages.get("err.title");

  public String returnTitle = Messages.get("return.title");

  public String compilationTitle = Messages.get("compilation.title");

  public String executionTitle = Messages.get("execution.title");

  public String toJson() {
    return new Gson().toJson(this);
  }

}
