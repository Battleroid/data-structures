/**
 * Created by casey on 2016-03-06.
 */
public class PFreeChoiceExercise1 {
    public static void main(String[] args) {
        System.out.println("Coins: {1, 2, 5}, Amount: 11, Expected: 3, Result: " + coinChange(new int[]{1, 2, 5}, 11));
    }

    public static int coinChange(int[] coins, int amount) {
        int size = amount + 1;
        int[] arr = new int[size];
        for (int i = 1; i < size; i++) {
            arr[i] = size;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int c : coins) {
                if (i >= c) {
                    arr[i] = Math.min(arr[i - c] + 1, arr[i]);
                } else {
                    break;
                }
            }
        }
        if (arr[amount] == size)
            return -1;
        return arr[amount];
    }
}
