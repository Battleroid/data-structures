/**
 * Created by casey on 2016-01-22.
 */
public class Exercise18_4_3 {
    public static void main(String[] args) {
        System.out.println("Sorted: " + isSorted(new int[] {1, 0, 2}, 3));
        System.out.println("Sorted: " + isSorted(new int[] {0, 1, 4}, 3));
        System.out.println("Sorted: " + isSorted(new int[] {0, 1, 5, 4}, 4));
    }

    public static boolean isSorted(int[] arr, int num) {
        if (arr.length <= 1) return true;
        if (num <= 1) return true;

        if (arr[num - 2] > arr[num - 1]) {
            return false;
        } else {
            return isSorted(arr, num - 1);
        }
    }
}
