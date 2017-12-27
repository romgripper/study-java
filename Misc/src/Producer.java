import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {
	static final int MAXQUEUE = 5;
	private List messages = new ArrayList();

	public void run() {
		while (true) {
			String date = putMessage();
			// System.out.println("Put message: " + date);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	// called by Producer internally
	private synchronized String putMessage() {
		while (messages.size() >= MAXQUEUE)
			try {
				//notifyAll();
				wait();
			} catch (InterruptedException e) {
			}
		String date = new java.util.Date().toString();
		messages.add(date);
		notifyAll();
		return date;
	}

	// called by Consumer externally
	public synchronized String getMessage() {
		while (messages.size() == 0)
			try {
				//notifyAll();
				wait();
			} catch (InterruptedException e) {
			}
		String message = (String) messages.remove(0);
		notifyAll();
		return message;
	}
}