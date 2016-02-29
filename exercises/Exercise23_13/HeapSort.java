package Exercise23_13;

/**
 * Created by casey on 2016-02-28.
 */
public class HeapSort {
    public HeapSort(Integer[] fresh) {
        this.heapSort(fresh);
    }

    /**
     * Heap sort method
     */
    public static <E extends Comparable> void heapSort(E[] list) {
        // Create a Heap of integers
        Heap<E> heap = new Heap<E>();

        // Add elements to the heap
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        // Remove elements from the heap
        for (int i = list.length - 1; i >= 0; i--)
            list[i] = heap.remove();
    }
}