/**
 * Created by casey on 2016-01-20.
 */
public class Exercise18_3_3 {
    public static void main(String[] args) {
        System.out.println("6: " + fib(6));
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
