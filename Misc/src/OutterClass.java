
public class OutterClass {

  public static class StaticInnerClass {
    private static int s = 1;
    private int v = 2;
  }

  public class NonStaticInnerClass {

    // Non-static inner class cannot have static member except constants
    //private static int s = 1;
    private static final int s = 1;
    private int v = 2;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
}
