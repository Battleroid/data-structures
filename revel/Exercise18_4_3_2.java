/**
 * Created by casey on 2016-02-01.
 */
public class Exercise18_4_3_2 {
    public static void main(String[] args) {
        System.out.println("apple: " + isPalindrome("apple"));
        System.out.println("abccba: " + isPalindrome("abccba"));
    }

    public static boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) return true;
        if (word.charAt(0) == word.charAt(word.length() - 1)) {
            return isPalindrome(word.substring(1, word.length() - 1));
        } else {
            return false;
        }
    }
}
