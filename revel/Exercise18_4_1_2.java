import java.util.Scanner;

/**
 * Created by casey on 1/27/16.
 */
public class Exercise18_4_1_2 {
    public static void main(String[] args) {
        System.out.println("'apple orange banana': " + count(new Scanner("apple orange banana")));
    }

    public static int count(Scanner in) {
        if (in.hasNext()) {
            in.next();
            return 1 + count(in);
        }
        return 0;
    }
}
