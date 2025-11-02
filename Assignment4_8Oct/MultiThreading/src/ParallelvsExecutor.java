
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ParallelvsExecutor {
    public static void main(String[] args) {
        int size = 1000000;
        long start1 = System.currentTimeMillis();
        IntStream.range(0, size).parallel().forEach(i -> Math.sqrt(i));
        long end1 = System.currentTimeMillis();
        System.out.println("time taken by parallel stream: " + (end1 - start1) + " ms");

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService es = Executors.newFixedThreadPool(threads);
        int chunkSize = size / threads;

        long start2 = System.currentTimeMillis();
        Future<?>[] futures = new Future[threads];
        for (int t = 0; t < threads; t++) {
            int start = t * chunkSize + 1;
            int end = (t == threads - 1) ? size : start + chunkSize - 1;

            futures[t] = es.submit(() -> {
                for (int i = start; i <= end; i++) {
                    int square = i * i;
                }
            });
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        long end2 = System.currentTimeMillis();
        es.shutdown();
        System.out.println("time taken by executor service: " + (end2 - start2) + " ms");

    }

}