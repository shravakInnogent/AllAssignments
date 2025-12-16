
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

// 1. Extract interfaces
interface Buffer<T> {
    void put(T item) throws InterruptedException;
    T take() throws InterruptedException;
}

interface Producer {
    void produce() throws InterruptedException;
}

interface Consumer {
    void consume() throws InterruptedException;
}

// 2. Separate concerns - Buffer implementation
class SharedBuffer<T> implements Buffer<T> {
    private final List<T> buffer = new ArrayList<>();
    private final int capacity;
    private final Logger logger = Logger.getLogger(SharedBuffer.class.getName());
    
    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }
    
    @Override
    public synchronized void put(T item) throws InterruptedException {
        while (buffer.size() == capacity) {
            System.out.println("Buffer is full, producer waiting...");
            wait();
        }
        buffer.add(item);
        System.out.println("Produced: " + item);
        notifyAll();
    }
    
    @Override
    public synchronized T take() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("Buffer is empty, consumer waiting...");
            wait();
        }
        T item = buffer.remove(0);
        System.out.println("Consumed: " + item);
        notifyAll();
        return item;
    }
}

// 3. Dependency injection - Producer implementation
class ItemProducer implements Producer {
    private final Buffer<Integer> buffer;
    private final int itemCount;
    private final Logger logger = Logger.getLogger(ItemProducer.class.getName());
    
    public ItemProducer(Buffer<Integer> buffer, int itemCount) {
        this.buffer = buffer;
        this.itemCount = itemCount;
    }
    
    @Override
    public void produce() throws InterruptedException {
        for (int i = 0; i < itemCount; i++) {
            buffer.put(i);
        }
    }
}

// 3. Dependency injection - Consumer implementation
class ItemConsumer implements Consumer {
    private final Buffer<Integer> buffer;
    private final int itemCount;
    private final Logger logger = Logger.getLogger(ItemConsumer.class.getName());
    
    public ItemConsumer(Buffer<Integer> buffer, int itemCount) {
        this.buffer = buffer;
        this.itemCount = itemCount;
    }
    
    @Override
    public void consume() throws InterruptedException {
        for (int i = 0; i < itemCount; i++) {
            buffer.take();
        }
    }
}

// 4. Configuration class with validation
class Config {
    private final int bufferSize;
    private final int itemCount;
    
    public Config(int bufferSize, int itemCount) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Buffer size must be positive, got: " + bufferSize);
        }
        if (itemCount <= 0) {
            throw new IllegalArgumentException("Item count must be positive, got: " + itemCount);
        }
        this.bufferSize = bufferSize;
        this.itemCount = itemCount;
    }
    
    public int getBufferSize() { return bufferSize; }
    public int getItemCount() { return itemCount; }
}

public class ProducerConsumer {
    private static final Logger logger = Logger.getLogger(ProducerConsumer.class.getName());
    
    static {
        // Disable verbose logging for production
        Logger.getLogger("").setLevel(Level.OFF);
    }
    
    public static void main(String[] args) {
        try {
            // 4. Configurable parameters
            Config config = getConfiguration();
            
            // 3. Dependency injection
            Buffer<Integer> buffer = new SharedBuffer<>(config.getBufferSize());
            Producer producer = new ItemProducer(buffer, config.getItemCount());
            Consumer consumer = new ItemConsumer(buffer, config.getItemCount());
            
            Thread producerThread = new Thread(() -> {
                try {
                    producer.produce();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Producer failed: " + e.getMessage());
                }
            });
            
            Thread consumerThread = new Thread(() -> {
                try {
                    consumer.consume();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Consumer failed: " + e.getMessage());
                }
            });
            
            producerThread.start();
            consumerThread.start();
            
            producerThread.join();
            consumerThread.join();
            
            System.out.println("\nProducer-Consumer execution completed successfully!");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Program interrupted");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static Config getConfiguration() {
        try (Scanner sc = new Scanner(System.in)) {
            int bufferSize = getValidInteger(sc, "Enter buffer size: ");
            int itemCount = getValidInteger(sc, "Enter number of items to produce/consume: ");
            return new Config(bufferSize, itemCount);
        }
    }
    
    private static int getValidInteger(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // consume invalid input
            }
        }
    }
}