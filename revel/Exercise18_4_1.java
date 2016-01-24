/**
 * Created by casey on 2016-01-22.
 */
public class Exercise18_4_1 {
    public static void main(String[] args) {
        System.out.println("Sample: " + isPalindrome(new int[] {0, 1, 2, 1}, 1, 3));
    }

    public static boolean isPalindrome(int[] arr, int s, int e) {
        int len = e - s + 1;
        if (len <= 1) return true;

        if (arr[s] != arr[e]) {
            return false;
        } else {
            return isPalindrome(arr, s + 1, e - 1);
        }
    }
}
