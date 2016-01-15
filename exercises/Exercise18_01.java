import java.math.BigInteger;
import java.util.Scanner;

public class Exercise18_01 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter: ");
        BigInteger result = fact(in.nextInt());
        System.out.println(result);
    }

    public static BigInteger fact(long n) {
        if (n == 1) {
            return BigInteger.ONE;
        } else {
            return BigInteger.valueOf(n).multiply(fact(n - 1));
        }
    }
}