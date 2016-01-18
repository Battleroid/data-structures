/**
 * Created by casey on 2016-01-17.
 */
public class Exercise18_2_4 {
    public static void main(String[] args) {
        System.out.println("7!!: " + oddevenfact(7));
    }

    public static long oddevenfact(int i) {
        if (i > 2) {
            return i * oddevenfact(i - 2);
        } else {
            return i;
        }
    }
}
