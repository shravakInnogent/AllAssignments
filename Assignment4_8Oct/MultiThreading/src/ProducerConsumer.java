
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

class SharedData {

    int n;
    List<Integer> productlist = new ArrayList<>();
    SharedData (int n){
        this.n =n;
    }
    int runTime=5;
    public void produce() throws InterruptedException
    {
        int value = 0;
        int i =0;
        while ( i!= runTime) {
            synchronized (this)
            {
                if (productlist.size() == n) {
                    System.out.println("List is full, producer is waiting...");
                    notify();
                    wait();
                }
                productlist.add(value);
                System.out.println("Produced - " + value);
                value++;
                notify();
                Thread.sleep(1000);
            }
            i++;
        }
    }

    public void consume() throws InterruptedException
    {   int j=0;
        while (j!=runTime) {
            synchronized (this)
            {
                if (productlist.size() == 0) {
                    System.out.println("List is empty, consumer is waiting...");
                    notify();
                    wait();
                }
                int val = productlist.removeFirst();
                System.out.println("Consumed - " + val);
                notify();
                Thread.sleep(1000);
            }
            j++;
        }
    }
}
public class ProducerConsumer {
    public static void main(String[] args)
            throws InterruptedException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of product to produce & consume");
        int n = sc.nextInt();


        SharedData obj = new SharedData(n);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    obj.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    obj.consume();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}