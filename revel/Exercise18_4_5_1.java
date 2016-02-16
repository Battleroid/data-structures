public class Exercise18_4_5_1 {
	public static void main(String[] args) {
		System.out.println(sum(new int[] {1, 3, 4}, 3));
	}

	public static sum(int[] arr, int n) {
		if (n == 0) return 0;
		return arr[n - 1] + sum(arr, n - 1);
	}
}
