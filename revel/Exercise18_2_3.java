public class Exercise18_2_3 {
    public static void main(String[] args) {
        System.out.println("0: " + fact(0));
    }

    public static long fact(int i) {
        if (i == 0) {
            return i;
        } else {
            return i * fact(i - 1);
        }
    }
}