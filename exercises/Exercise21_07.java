import java.util.*;

public class Exercise21_07 {
    public static void main(String[] args) {
        // Set text in a string
        String text = "Good morning. Have a good class. " +
                "Have a good visit. Have fun!";

        // Create a TreeMap to hold words as key and count as value
        Map<String, Integer> map = new TreeMap<>();

        String[] words = text.split("[\\s+\\p{P}]");
        for (int i = 0; i < words.length; i++) {
            String key = words[i].toLowerCase();

            if (key.length() > 0) {
                if (!map.containsKey(key)) {
                    map.put(key, 1);
                } else {
                    int value = map.get(key);
                    value++;
                    map.put(key, value);
                }
            }
        }

        // sort ascending order via lambda, flip e1/e2 for descending
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        Collections.sort(entries, (e1, e2) -> e1.getValue().compareTo(e2.getValue()));

        // Display each key/value in ascending order
        for (Map.Entry<String, Integer> entry : entries)
            System.out.println(entry.getKey() + "\t" + entry.getValue());
    }
}
