public class Exercise18_4_5_3 {
	public static void main(String[] args) {
		int[] sample = new int[] {1, 3, 5};
		reverse(sample, 0, 2);
		System.out.println(sample);
	}

	public static void reverse(int[] arr, int s, int e) {
		if ((e - s) < 1) return;
		int t = arr[e];
		arr[e] = arr[s];
		arr[s] = t;
		reverse(arr, s + 1, e - 1);
	}
}
