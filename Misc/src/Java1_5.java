import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;

public class Java1_5 {

  public static void tryAutoBoxing() {
    Integer i = 10;
    Integer m = 10;
    System.out.println(i == m);
    int j = i + 10;
    int k = i;
    System.out.println("" + k + " " + j);
    i = 127;
    m = 127;
    System.out.println(i == m); // true
    i = 128;
    m = 128;
    System.out.println(i == m); // false
    i = new Integer(10);
    m = new Integer(10);
    System.out.println(i == m); // false
    i = Integer.valueOf(10);
    m = Integer.valueOf(10);
    System.out.println(i == m); // true
    i = Integer.valueOf(1000);
    m = Integer.valueOf(1000);
    System.out.println(i == m); // true
  }

  public static void tryEnum() {
    System.out.println(Weekday.Wednesday + "'s order in weekday is " + Weekday.Wednesday.index());
  }

  public static void tryStaticImport() {
    out.println(sqrt(PI));
  }

  private static void tryVarArgs(int... a) {
    for (int i : a) {
      System.out.println(i);
    }
  }

  private static void tryGenericAndForEach() {
    List<String> words = new ArrayList<String>();
    words.add("Hello");
    words.add("World");
    for (String word : words) {
      System.out.println(word);
    }
  }

  private static void tryAnnotation() {
    new Runnable() {
      @Override
      public void run() {
        System.out.println("Run() is a @Override method");
      }
    }.run();
  }

  public static void main(String[] args) {
    tryAutoBoxing();
    tryEnum();
    tryVarArgs(1, 2, 3, 4, 5, 6);
    tryGenericAndForEach();
    tryAnnotation();
  }
}

/* This cannot be public because only one public class is allowed in one jave file and
 * the name should be consistent with the class name.
 */
enum Weekday {
  Sunday(1),
  MONDAY(2),
  Tuesday(3),
  Wednesday(4),
  Thursday(5),
  Friday(6),
  Saturday(7);

  private final int index;

  // Only private is allowed
  private Weekday(int index) {
    this.index = index;
  }

  public int index() {
    return index;
  }
}
