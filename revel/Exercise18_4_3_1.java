/**
 * Created by casey on 2016-02-01.
 */
public class Exercise18_4_3_1 {
    public static void main(String[] args) {
        System.out.println("Apple: " + len("apple"));
    }

    public static int len(String word) {
        if (word.isEmpty()) return 0;
        return 1 + len(word.substring(1, word.length()));
    }
}
