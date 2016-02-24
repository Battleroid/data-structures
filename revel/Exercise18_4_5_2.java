public class Exercise18_4_5_2 {
	public static void main(String[] args) {
		int[] sample = new int[3];
		init(sample, 3);
		System.out.println(sample);
	}

	public static void init(int[] arr, int n) {
		if (n == 0) return;
		arr[n - 1] = n - 1;
		init(arr, n - 1);
	}
}
