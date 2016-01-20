/**
 * Created by casey on 2016-01-20.
 */
public class Exercise18_3_1 {
    public static void main(String[] args) {
        System.out.println("2 * 3: " + product(2, 3));
        System.out.println("3 * 4: " + product(3, 4));
    }

    private static int product(int i, int j) {
        if (i == 0 || j == 0) {
            return 0;
        } else if (i == 1) {
            return j;
        } else if (j == 1) {
            return i;
        } else {
            return i + product(i, j - 1);
        }
    }
}
