/**
 * Created by casey on 2016-01-17.
 */
public class Exercise18_2_1 {
    public static void main(String[] args) {
        System.out.println("10: " + sum(10));
    }

    private static int sum(int i) {
        if (i == 1) {
            return 1;
        } else {
            return i + sum(i - 1);
        }
    }
}
