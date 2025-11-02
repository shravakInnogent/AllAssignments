
import java.util.concurrent.CountDownLatch;

public class CountDownlatch
{
    public static void main(String args[])
            throws InterruptedException
    {

        CountDownLatch latch = new CountDownLatch(4);

        Worker first = new Worker(1000, latch,
                "Worker-1");
        Worker second = new Worker(2000, latch,
                "Worker-2");
        Worker third = new Worker(3000, latch,
                "Worker-3");

        first.start();
        second.start();
        third.start();

        // The main task waits for four threads
        latch.await();

        // Main thread has started
        System.out.println(Thread.currentThread().getName() +
                " has finished");



    }
}

// A class to represent threads for which
// the main thread waits.
class Worker extends Thread
{
    int delay;
    CountDownLatch latch;

    public Worker(int delay, CountDownLatch latch,
                  String name)
    {
        super(name);
        this.delay = delay;
        this.latch = latch;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(delay);
            latch.countDown();
            System.out.println(Thread.currentThread().getName()
                    + " has finished loading configuration");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}