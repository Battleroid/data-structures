/**
 * Created by casey on 2016-02-26.
 */
public class Exercise18_4_7_1 {
    public static void main(String[] args) {
        System.out.println("apple: " + containsVowel("apple"));
        System.out.println("rss: " + containsVowel("rss"));
    }

    public static boolean containsVowel(String s) {
        if (s.isEmpty()) return false;
        if (s.charAt(0) == 'a' || s.charAt(0) == 'e' || s.charAt(0) == 'i' || s.charAt(0) == 'o'
                || s.charAt(0) == 'u' || s.charAt(0) == 'A' || s.charAt(0) == 'E' || s.charAt(0) == 'I'
                || s.charAt(0) == 'O' || s.charAt(0) == 'U') return true;
        else return containsVowel(s.substring(1));
    }
}
