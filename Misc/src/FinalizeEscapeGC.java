
public class FinalizeEscapeGC {

  private static FinalizeEscapeGC save_hook;

  @Override
  protected void finalize() throws Throwable {
    System.out.println("finalize method executed!");
    super.finalize();
    save_hook = this;
  }

  public static void main(String[] args) throws InterruptedException {
    save_hook = new FinalizeEscapeGC();
    save_hook = null;
    System.gc();
    Thread.sleep(1000);
    if (save_hook == null) {
      System.out.println("No, I am dead :(");
    } else {
      System.out.println("Yes. I am alive :)");
    }

    save_hook = null;
    System.gc();
    Thread.sleep(1000);
    if (save_hook == null) {
      System.out.println("No, I am dead :(");
    } else {
      System.out.println("Yes. I am alive :)");
    }
  }
}
