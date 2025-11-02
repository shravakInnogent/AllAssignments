
import  java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
    static   ReentrantLock lock1 = new ReentrantLock();
    static   ReentrantLock lock2 = new ReentrantLock();


    static class TryLockTask2 extends Thread {
    public void run() {
        try {
            if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println("Task2: locked Resource2");
                    Thread.sleep(50);
                    if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Task2: locked Resource1");
                        } finally {
                            lock1.unlock();
                        }
                    } else {
                        System.out.println("Task2: could not lock Resource1");
                    }
                } finally {
                    lock2.unlock();
                }
            } else {
                System.out.println("Task2: could not lock Resource2");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
static class TryLockTask1 extends Thread {
    public void run() {
        try {
            if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println("Task1: locked Resource1");
                    Thread.sleep(50);
                    if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("Task1: locked Resource2");
                        } finally {
                            lock2.unlock();
                        }
                    } else {
                        System.out.println("Task1: could not lock Resource2");
                    }
                } finally {
                    lock1.unlock();
                }
            } else {
                System.out.println("Task1: could not lock Resource1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    public static void main(String[] args) {
        TryLockTask1 task1 = new TryLockTask1();
        Thread t1 = new Thread(task1);

        TryLockTask2 task2 = new TryLockTask2();
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
    }
}

