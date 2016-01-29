/**
 * Created by casey on 2016-01-28.
 */
public class Exercise18_4_2_2 {
    public static void main(String[] args) {
        System.out.println("4: ");
        printTriangle(4, 0);
    }

    public static String makeLine(int n, char c) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += c;
        }
        return s.toString();
    }

    public static void printTriangle(int n, int k) {
        if (n <= 0) return;
        if (n % 2 == 0) n++;
        System.out.println(makeLine(k, ' ') + makeLine(n, 'O'));
        printTriangle(n - 2, k + 1);
    }
}
