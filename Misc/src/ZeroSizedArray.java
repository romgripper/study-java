
public class ZeroSizedArray {

  public static void main(String[] args) {
    int[] a = new int[0];
    System.out.println(a);
    for (int e : a) {
      System.out.println(e);
    }
    try {
      System.out.println(a[0]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
