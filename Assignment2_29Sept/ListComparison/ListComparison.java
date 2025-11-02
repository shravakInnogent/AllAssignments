
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ListComparison{
    public static void main(String[] args){
        int[] sizes = {10000, 50000, 100000};
        for (int size : sizes){
            System.out.println("\nTest with "+ size + " elements");
            
            List<Integer> arrayList = new ArrayList<>(size);
            long start = System.currentTimeMillis();
            for(int i=0;i<size;i++){
                arrayList.add(i);
            }
            long end = System.currentTimeMillis();
            System.out.println("ArrayList Insertion "  + (end-start) + " ms");

            List<Integer> linkedList = new LinkedList<>();
            start = System.currentTimeMillis();
            for(int i =0;i<size;i++){
                linkedList.add(i);
            }
            end = System.currentTimeMillis();
            System.out.println("LinkedList Insertion:" + (end - start) + " ms");

            start = System.currentTimeMillis();
            for(int i = size-1;i>=0;i--){
                arrayList.remove(i);
            }
            end = System.currentTimeMillis();
            System.out.println("ArrayList deletion: " + (end - start) + " ms");

            start = System.currentTimeMillis();
            for (int i= size- 1;i>= 0;i--)
                linkedList.remove(i);
            end = System.currentTimeMillis();
            System.out.println("LinkedList Deletion " + (end -start) + " ms");

        }
    }
}