/**
 * Created by casey on 2016-01-17.
 */
public class Exercise18_2_2 {
    public static void main(String[] args) {
        System.out.println("3^2: " + power(3, 2));
    }

    public static long power(int x, int n) {
        if (n == 0) {
            return 1;
        } else {
            return x * power(x, n - 1);
        }
    }
}
