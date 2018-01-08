import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.System.out;

public class Java1_5 {

  public static void autoBoxing() {
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

  private static void printVarArgs(int... a) {
    for (int i : a) {
      System.out.println(i);
    }
  }

  public static void main(String[] args) {
    autoBoxing();
    tryEnum();
    printVarArgs(1, 2, 3, 4, 5, 6);
  }
}

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
