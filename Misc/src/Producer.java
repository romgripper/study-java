import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {
  static final int MAXQUEUE = 5;
  private List<String> messages = new ArrayList<>();
  private int id = 0;

  public void run() {
    while (true) {
      String message = putMessage();
      System.out.println("Put message: " + message);
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
      }
    }
  }

  // called by Producer internally
  private synchronized String putMessage() {
    while (messages.size() >= MAXQUEUE)
      try {
        wait();
      } catch (InterruptedException e) {
      }
    String message = "" + (id++) + ": ";
    message += new java.util.Date().toString();
    messages.add(message);
    notifyAll();
    return message;
  }

  // called by Consumer externally
  public synchronized String getMessage() {
    while (messages.size() == 0)
      try {
        wait();
      } catch (InterruptedException e) {
      }
    String message = messages.remove(0);
    notifyAll();
    return message;
  }
}
