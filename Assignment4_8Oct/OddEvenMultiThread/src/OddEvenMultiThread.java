//import java.util.Thread;
import java.lang.Thread;
import java.util.InputMismatchException;
import java.util.Scanner;

class OddEvenPrint {
    int n;
    OddEvenPrint(String name, int n)
    {
        Thread.currentThread().setName(name);
        this.n= n;
    }
    int value = 1;
    void printOdd() throws InterruptedException{
        while(value < n){
            try {
                synchronized (this) {
                    if (value % 2 != 0) {
                        System.out.println("Odd " + value);
                        value++;
                        notify();
                    } else {
                        wait();
                    }
                }
            }catch(InterruptedException e){
              e.printStackTrace();
            }
        }
    }
        void printEven() throws  InterruptedException  {
            while (value < n){
                try{
                    synchronized(this) {
                        if (value % 2 == 0) {
                            System.out.println("Even " + value);
                            value++;
                            notify();
                        } else {
                            wait();
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }
class OddEvenMultiThread{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of odd even to print");

        int n = sc.nextInt();
        OddEvenPrint obj = new OddEvenPrint("Even Thread", n);
        Thread t1 =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    obj.printOdd();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    obj.printEven();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }
}

