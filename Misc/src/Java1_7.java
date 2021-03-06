import java.util.ArrayList;
import java.util.List;

public class Java1_7 {

  private static void trySwitchString() {
    System.out.println("- Switch string");
    // Switch(String) use String.equals(), so the following two lines have the same result.
    String key = "test";
    //String key = new String("test");
    int a;
    switch (key) {
      case "test":
        a = 1;
        break;
      case "test1":
        a = 2;
        break;
      case "test2":
        a = 3;
        break;
      default:
        a = 0;
    }
    System.out.println(a);
  }

  private static void tryAutoCloseable() {
    System.out.println("- AutoCloseable");
    try (MyAutoCloseable autoClose = new MyAutoCloseable()) {
      throw new Exception();
    } catch (Exception e) {
      // The AutoCloseable will close before catch clause.
      System.out.println("Exception occurs");
      Throwable[] throwables = e.getSuppressed();
      for (Throwable throwable : throwables) {
        System.out.println(throwable);
      }
    }
  }

  private static void tryGeneric() {
    System.out.println("- Simplified generic");
    List<String> list = new ArrayList<>();
    list.add("hello");
    list.add("world");
    for (String str : list) {
      System.out.println(str);
    }
  }

  private static void tryNumbers() {
    System.out.println("- Number Literature");
    System.out.println(1000_000_0);
    System.out.println(1000_000);
    System.out.println(0b0000_0001);
    System.out.println(0b0111_1111);
  }

  private static void tryMultipleCatch() {
    System.out.println("- Multiple catch clauses");
    int b = 0, x[] = {10, 20, 30};
    try {
      int c = x[3] / b;
      System.out.println(c);
    } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    System.out.println("Java 1.7 new features:");
    trySwitchString();
    tryAutoCloseable();
    tryGeneric();
    tryNumbers();
    tryMultipleCatch();
  }
}

class MyAutoCloseable implements AutoCloseable {

  @Override
  public void close() {
    System.out.println("MyAutoCloseable is closed automatically");
    throw new RuntimeException("MyAutoCloseable close exception");
  }
}
