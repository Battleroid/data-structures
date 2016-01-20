/**
 * Created by casey on 2016-01-20.
 */
public class Exercise18_3_4 {
    public static void main(String[] args) {
        System.out.println("9 / 3: " + quotient(9, 3));
    }

    public static int quotient(int m, int k) {
        if (k > m) {
            return 0;
        } else {
            return 1 + quotient(m - k, k);
        }
    }
}
