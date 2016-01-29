/**
 * Created by casey on 2016-01-28.
 */
public class Exercise18_4_2_1 {
    public static void main(String[] args) {
        System.out.println("5: ");
        printTriangle(5);
    }

    public static String makeStars(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append("* ");
        }
        return s.toString();
    }

    public static void printTriangle(int n) {
        if (n > 0) {
            System.out.println(makeStars(n));
            printTriangle(n - 1);
        }
    }
}