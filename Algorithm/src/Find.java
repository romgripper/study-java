
public class Find {
	
	private static int findInc(int[] a, int val, int start, int end) {
		if (start > end) {
			return -1;
		}
		int mid = (start + end) / 2;
		if (val ==  a[mid]) {
			return mid;
		} else if (val <  a[mid]) {
			return findInc(a, val, start, mid - 1);
		} else {
			return findInc(a, val, mid + 1, end);
		}
	}
	
	private static int findDec(int[] a, int val, int start, int end) {
		if (start > end) {
			return -1;
		}
		int mid = (start + end) / 2;
		if (val == a[mid]) {
			return mid;
		} else if (val >  a[mid]) {
			return findDec(a, val, start, mid - 1);
		} else {
			return findDec(a, val, mid + 1, end);
		}
	}
	
	
	private static int find(int[] a, int val) {
		int max = findMax(a);
		int i = findInc(a, val, 0, max);
		if (i >= 0) {
			return i;
		}
		return findDec(a, val, max + 1, a.length - 1);
	}
	
	private static int findMax(int[] a) {
		return findMax(a, 0, a.length - 1);
	}
	
	private static int findMax(int[] a, int start, int end) {
		if (start == end) {
			return start;
		} 
		int mid = (start + end) / 2;
		if (a[mid] <= a[mid + 1]) {
			return findMax(a, mid + 1, end);
		} 
		return findMax(a, start, mid);
	}

	public static void main(String[] args) {
		int[] a = {1, 3, 5, 7, 9, 11, 13, 13, 15, 17, 16, 14, 12, 10};
		System.out.println(findMax(a));
		System.out.println(find(a, 10));
		System.out.println(find(a, 11));
		System.out.println(find(a, 14));
		System.out.println(find(a, 8));

	}

}
