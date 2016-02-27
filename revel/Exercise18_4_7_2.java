/**
 * Created by casey on 2016-02-26.
 */
public class Exercise18_4_7_2 {
    public static void main(String[] args) {
        System.out.println("apple: " + reverse("apple"));
    }

    public static String reverse(String s) {
        if (s.isEmpty() || s.length() < 1) return "";
        return reverse(s.substring(1)) + s.charAt(0);
    }
}
