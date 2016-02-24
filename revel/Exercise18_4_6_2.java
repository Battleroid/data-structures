/**
 * Created by casey on 2016-02-23.
 */
public class Exercise18_4_6_2 {
    public static void main(String[] args) {
        System.out.println("3: " + makeStars(3));
    }

    public static String makeStars(int n) {
        if (n == 0) return "";
        return "*" + makeStars(n - 1);
    }
}
