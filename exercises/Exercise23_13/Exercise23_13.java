package Exercise23_13;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by casey on 2016-02-28.
 */
public class Exercise23_13 {
    public static void main(String[] args) {
        // create our massive array of random integers
        Random rng = new Random();
        int[] unsorted = new int[300000];
        for (int i = 0; i < unsorted.length; i++) {
            unsorted[i] = rng.nextInt(100); // 100 sounds reasonable
        }

        // build list of sorting methods for int array
        List<Class<?>> sortingMethods = new ArrayList<>();
        sortingMethods.add(BubbleSort.class);
        sortingMethods.add(InsertionSort.class);
        sortingMethods.add(MergeSort.class);
        sortingMethods.add(QuickSort.class);
        sortingMethods.add(RadixSort.class);

        // hashmap of times (1-6 * 50,000), with class name & the execution time
        LinkedHashMap<String, TreeMap<Integer, Long>> times = new LinkedHashMap<>();

        for (Class c : sortingMethods) {
            TreeMap<Integer, Long> record = new TreeMap<>();
            times.put(c.getSimpleName(), record);
            for (int i = 1; i <= 6; i++) {
                int amt = i * 50000;
                int[] fresh = Arrays.copyOfRange(unsorted, 0, amt);

                Long start = null;
                Long end = null;

                try {
                    Constructor<?> ctor = c.getConstructor(int[].class);
                    start = System.nanoTime();
                    Object obj = ctor.newInstance(new Object[]{fresh});
                    end = System.nanoTime();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                // record execution time
                Long exec = end - start;
                long secs = TimeUnit.MILLISECONDS.convert(exec, TimeUnit.NANOSECONDS);
                record.put(amt, secs);
            }
        }

        // convert unsorted array to Integer for use in the remaining methods
        Integer[] unsortedInteger = new Integer[300000];
        for (int i = 0; i < unsorted.length; i++) {
            unsortedInteger[i] = unsorted[i];
        }

        // finish up by doing heap sort on Integer array
        TreeMap<Integer, Long> record = new TreeMap<>();
        for (int i = 1; i <= 6; i++) {
            int amt = i * 50000;
            // fresh, newly messy array of fixed size to sort
            Integer[] fresh = Arrays.copyOfRange(unsortedInteger, 0, amt);

            Long start = System.nanoTime();
            HeapSort hs = new HeapSort(fresh);

            // record execution time
            Long exec = System.nanoTime() - start;
            Long secs = TimeUnit.MILLISECONDS.convert(exec, TimeUnit.NANOSECONDS);
            record.put(amt, secs);
            times.put(hs.getClass().getSimpleName(), record);
        }

        // build our little table of times with legend
        System.out.println("Note: Time is in milliseconds");
        String header = new String();
        header += String.format("%-15s", "meth/amt");
        for (int i = 1; i <= 6; i++) {
            header += String.format("%-15s", i * 50000);
        }
        System.out.println(header);

        // Build individual times for each method
        for (Map.Entry<String, TreeMap<Integer, Long>> methodEntry : times.entrySet()) {
            String line = String.format("%-15s", methodEntry.getKey());
            for (Map.Entry<Integer, Long> timeEntry : methodEntry.getValue().entrySet()) {
                line += String.format("%-15s", timeEntry.getValue());
            }
            System.out.println(line);
        }
    }
}