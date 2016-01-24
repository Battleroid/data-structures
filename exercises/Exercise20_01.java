import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by casey on 1/22/16.
 */
public class Exercise20_01 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:\n  Exercise20_01 <input>");
            System.exit(1);
        }

        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.exit(1);
        }

        Collections.sort(words);
        for (String s: words) {
            System.out.println(s);
        }
    }
}
