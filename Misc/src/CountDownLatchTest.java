import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

  /* Simulated 100 - meter race, 10 players are ready, just waiting for the referee gave.
   * When everyone at the finish, the game is over.
   */
  public static void main(String[] args) throws InterruptedException {

    // The CountDownLatch for start. When it reach 0, all players start to run
    final CountDownLatch begin = new CountDownLatch(1);

    // The CountDownLatch for end. Once a player finishes, it decreases by 1 and reaches 0 when all finish.
    final CountDownLatch end = new CountDownLatch(10);

    // Ten players
    final ExecutorService exec = Executors.newFixedThreadPool(10);

    for (int index = 0; index < 10; index++) {
      final int NO = index + 1;
      Runnable run =
          new Runnable() {
            public void run() {
              try {
                // Get ready and wait for the "Go" signal
                begin.await();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("No." + NO + " arrived");
              } catch (InterruptedException e) {
              } finally {
                // When the player finishes, end latch decreases by 1
                end.countDown();
              }
            }
          };
      exec.submit(run);
    }
    System.out.println("Game Start");
    // Ready, go
    begin.countDown();
    // When end reaches 0, game is over
    end.await();
    System.out.println("Game Over");
    exec.shutdown();
  }
}
