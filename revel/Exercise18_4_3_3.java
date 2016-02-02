/**
 * Created by casey on 2016-02-01.
 */
public class Exercise18_4_3_3 {
    public static void main(String[] args) {
        System.out.println("Five stars (*): " + makeLine(5, '*'));
    }

    public static String makeLine(int n, char c) {
        if (n == 0) return "";
        return c + makeLine(n - 1, c);
    }
}
