import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

interface Sayable {
  default void say() {
    saySomething();
  }

  private void saySomething() {
    System.out.println("Hello... I'm private method");
  }
}

public class Java9 {

  private static void tryPrivateMethodInterface() {
    new Sayable() {}.say();
  }
  
  private static void tryTryWithResource() throws FileNotFoundException {
    FileOutputStream out1 = new FileOutputStream("out1");
    FileOutputStream out2 = new FileOutputStream("out2");
    try (out1; out2) {
      out1.write("Hello".getBytes());
      out2.write("World".getBytes());
      System.out.println(out1.toString());
      System.out.println(out2.toString());
    } catch (IOException e) {
      System.out.println(e);
    }
    try {
      out2.write("Hello world".getBytes());
      System.out.println(out2.toString());
      out2.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    tryPrivateMethodInterface();
    tryTryWithResource();
  }
}
