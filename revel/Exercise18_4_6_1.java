/**
 * Created by casey on 2016-02-22.
 */
public class Exercise18_4_6_1 {
    public static void main(String[] args) {
        System.out.println("a p ple: " + replace("a p ple"));
    }

    public static String replace(String s) {
        if (s.isEmpty()) return "";
        if (s.charAt(0) == ' ') {
            return '*' + replace(s.substring(1, s.length()));
        } else {
            return s.charAt(0) + replace(s.substring(1, s.length()));
        }
    }
}
