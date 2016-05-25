package eval.print;

import java.io.OutputStream;
import java.io.PrintStream;

// TODO migrate to scala code
public class OwnPrintStream extends PrintStream {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private final PrintStreamMode mode;


  public OwnPrintStream(OutputStream out, PrintStreamMode mode) {
    super(out);
    this.mode = mode;
  }

  @Override
  public void print(String x) {
    Thread currentThread = Thread.currentThread();
    if (currentThread instanceof OwnPrintStreamThread) {
      if (PrintStreamMode.OUT.equals(this.mode)) {
        ((OwnPrintStreamThread) currentThread).out(x);
      } else {
        ((OwnPrintStreamThread) currentThread).err(x);
      }
    } else {
      super.print(x);
    }
  }

  @Override
  public void println(boolean x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(char x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(int x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(long x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(float x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(double x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(char[] x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(String x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println(Object x) {
    synchronized (this) {
      print("" + x);
      println();
    }
  }

  @Override
  public void println() {
    print(LINE_SEPARATOR);
  }

}
