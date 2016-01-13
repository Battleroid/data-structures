import java.util.Scanner;

import javafx.application.Application;

public class Fiddle extends Application {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		System.out.println("This is " + a);
		in.close();
	}
}
