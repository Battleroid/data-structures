/**
 * Created by casey on 2016-01-20.
 */
public class Exercise18_3_2 {
    public static void main(String[] args) {
        System.out.println("4th: " + harmonic(4));
    }

    private static double harmonic(int n) {
        if (n == 1) {
            return 1.0;
        } else {
            return (1.0 / n) + harmonic(n - 1);
        }
    }
}
