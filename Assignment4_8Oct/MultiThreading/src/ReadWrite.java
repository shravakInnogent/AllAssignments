import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWrite {
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    String sharedValue = "Initial Value";

    public String readValue() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading: " + sharedValue);
            return sharedValue;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void writeConfig(String newValue) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing value: " + newValue);
            sharedValue = newValue;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWrite manager = new ReadWrite();

        Runnable readerTask = () -> {
            for (int i = 0; i < 3; i++) {
                manager.readValue();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable writerTask = () -> {
            String[] values = {"Value1", "value2", "Value3"};
            for (String val : values) {
                manager.writeConfig(val);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread reader1 = new Thread(readerTask, "Reader-1");
        Thread reader2 = new Thread(readerTask, "Reader-2");
        Thread writer = new Thread(writerTask, "Writer");

        reader1.start();
        reader2.start();
        writer.start();
    }
}
