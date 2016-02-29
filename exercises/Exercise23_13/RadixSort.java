package Exercise23_13;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by casey on 2016-02-28.
 */
public class RadixSort {
    public RadixSort(int[] unsorted) {
        this.radixSort(unsorted);
    }

    public void radixSort(int[] input) {
        final int totalBuckets = 10;
        // declare and initialize bucket[]
        List<Integer>[] bucket = new ArrayList[10];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<Integer>();
        }

        // sort
        boolean maxLength = false;
        int tmp = -1, placement = 1;
        while (!maxLength) {
            maxLength = true;
            // split input between lists
            for (Integer i : input) {
                tmp = i / placement;
                bucket[tmp % totalBuckets].add(i);
                if (maxLength && tmp > 0) {
                    maxLength = false;
                }
            }
            // empty lists into input array
            int a = 0;
            for (int b = 0; b < totalBuckets; b++) {
                for (Integer i : bucket[b]) {
                    input[a++] = i;
                }
                bucket[b].clear();
            }
            // move to next digit
            placement *= totalBuckets;
        }
    }
}
