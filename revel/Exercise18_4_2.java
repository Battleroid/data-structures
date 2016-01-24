/**
 * Created by casey on 2016-01-22.
 */
public class Exercise18_4_2 {
    public static void main(String[] args) {
        System.out.println("Product: " + productOfOdds(new int[] {1, 2, 5, 7, 4}, 5));
    }

    public static int productOfOdds(int[] arr, int idx) {
        if (idx == 1) {
            if (arr[idx - 1] % 2 != 0) {
                return arr[idx - 1];
            } else {
                return 1;
            }
        } else if (arr[idx - 1] % 2 != 0) {
            return arr[idx - 1] * productOfOdds(arr, idx - 1);
        } else {
            return productOfOdds(arr, idx - 1);
        }
    }
}
