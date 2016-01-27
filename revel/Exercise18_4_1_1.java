import java.util.Scanner;

/**
 * Created by casey on 1/27/16.
 */
public class Exercise18_4_1_1 {
    public static void main(String[] args) {
        copy(new Scanner("apple banana orange"));
    }

    public static void copy(Scanner in) {
        if (in.hasNext()) {
            System.out.println(in.next());
            copy(in);
        }
    }
}
