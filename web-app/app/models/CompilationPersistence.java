package models;

import com.avaje.ebean.annotation.Encrypted;
import eval.CompilationResult;
import eval.ScalaCompilationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;

@Entity
public class CompilationPersistence extends AbstractModel {

  private static final Logger LOG = LoggerFactory.getLogger(CompilationPersistence.class);

  private static Finder<String, CompilationPersistence> find = new Finder<>(CompilationPersistence.class);

  @Encrypted
  @Lob
  public String script;

  @Lob
  public String result;

  @Lob
  public String stdOut;

  @Lob
  public String stdErr;

  public String hash;

  public Long startTime;

  public Long endTime;

  public Long compilationTime;

  public Long executionTime;

  @Enumerated(value = EnumType.STRING)
  public CompilationResult compilationResult;


  public CompilationPersistence(ScalaCompilationResult result) {
    script = result.script;
    this.result = result.result;
    stdOut = result.stdOut;
    stdErr = result.stdErr;
    compilationResult = result.compilationResult;
    startTime = result.startTime;
    endTime = result.endTime;
    compilationTime = result.compilationTime;
    executionTime = result.executionTime;
  }

  public static CompilationPersistence getByHash(String hash) {
    return find.where().eq("hash", hash).findUnique();
  }

  public static void save(CompilationPersistence persistence) {
    persistence.save();
    try {
      byte[] bytesOfMessage = ("" + persistence.id).getBytes("UTF-8");

      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] bytes = md.digest(bytesOfMessage);
      persistence.hash = new BigInteger(1, bytes).toString(16);
      persistence.save();
    } catch (Exception e) {
      LOG.error("unable to save persistence hash for bean: " + persistence.id, e);
    }
  }

  public ScalaCompilationResult toCompilationResult() {
    ScalaCompilationResult result = new ScalaCompilationResult();
    result.script = script;
    result.result = this.result;
    result.stdOut = this.stdOut;
    result.stdErr = this.stdErr;
    result.startTime = this.startTime;
    result.endTime = this.endTime;
    result.compilationTime = this.compilationTime;
    result.executionTime = this.executionTime;
    result.hash = this.hash;
    return result;
  }
}
