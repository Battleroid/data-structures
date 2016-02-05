import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by casey on 2016-02-05.
 */
public class Exercise21_01 {
    public static void main(String[] args) {
        // init and add all strings
        LinkedHashSet<String> s1 = new LinkedHashSet<>();
        LinkedHashSet<String> s2 = new LinkedHashSet<>();
        String[] s1Names = new String[]{
                "George", "Jim", "John",
                "Blake", "Kevin", "Michael"
        };
        String[] s2Names = new String[]{
                "George", "Katie", "Kevin",
                "Michelle", "Ryan"
        };

        for (String s : s1Names)
            s1.add(s);

        for (String s : s2Names)
            s2.add(s);

        // union
        HashSet<String> union = (LinkedHashSet<String>) s1.clone();
        s1.addAll(s2);
        System.out.println("Union: " + union.toString());

        // difference
        HashSet<String> difference = (LinkedHashSet<String>) s1.clone();
        difference.removeAll(s2);
        System.out.println("Difference: " + difference.toString());

        // intersection
        HashSet<String> intersection = (LinkedHashSet<String>) s1.clone();
        intersection.retainAll(s2);
        System.out.println("Intersection: " + intersection.toString());
    }
}
