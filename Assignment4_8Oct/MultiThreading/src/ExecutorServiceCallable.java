import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ExecutorServiceCallable {
    public static void main(String[] args){

                ExecutorService executor = Executors.newFixedThreadPool(2);


                String file1 = "/Users/shravakjain/Desktop/Innogent/file1.txt";
                String file2 = "/Users/shravakjain/Desktop/Innogent/file2.txt";
                String file3 = "/Users/shravakjain/Desktop/Innogent/file3.txt";

                Callable<Integer> countLinesTask1 = () -> countLines(file1);
                Callable<Integer> countLinesTask2 = () -> countLines(file2);
                Callable<Integer> countLinesTask3 = () -> countLines(file3);

                try {
                    Future<Integer> future1 = executor.submit(countLinesTask1);
                    Future<Integer> future2 = executor.submit(countLinesTask2);
                    Future<Integer> future3 = executor.submit(countLinesTask3);

                    int totalLines = future1.get() + future2.get() + future3.get();

                    System.out.println("Total lines across all files: " + totalLines);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    executor.shutdown();
                }
            }

            private static int countLines(String filePath) {
                int lines = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    while (reader.readLine() != null) {
                        lines++;
                    }
                    System.out.println(filePath + ": " + lines + " lines");
                } catch (IOException e) {
                    System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
                }
                return lines;
            }
        }
