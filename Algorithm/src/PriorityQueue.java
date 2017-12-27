import java.util.Scanner;

public class PriorityQueue {
	private static final int INIT_CAPICITY = 8;
	private int[] array = new int[INIT_CAPICITY];
	private int size = 0;
	private int capicity = INIT_CAPICITY;
	
	public PriorityQueue() {
	}
	
	public PriorityQueue(int capicity) {
		this.capicity = capicity;
	}
	
	private void enlargeCapicity() {
		capicity <<= 2;
		int[] newArray = new int[capicity];
		for (int i = 0; i < size; i ++) {
			newArray[i] = array[i]; 
		}
		array = newArray;
		//array = Arrays.copyOf(array, capicity);
	}
	
	public void enqueue(int value) {
		System.out.println("Enqueue " + value);
		if (size + 1 > capicity) {
			enlargeCapicity();
		}
		array[size] = value;
		heapifyUp(size ++);
	}
	
	public Integer peek() {
		Integer value = null;
		if (size > 0) {
			value = array[0];
		}
		return value;
	}
	
	public Integer dequeue() {
		Integer value = peek();
		if (value != null) {
			size --;
			swap(0, size);
			heapifyDown(0, size);
		}
		return value;
	}
	
	private int parent(int i) {
		return (i - 1) / 2;
	}
	private void heapifyUp(int i) {
		if (i == 0) {
			return;
		}
		int parent = parent(i);
		if (array[i] > array[parent]) {
			swap(i, parent);
			heapifyUp(parent);
		}
	}
	
	private void buildHeap() {
		for (int i = parent(size - 1); i >= 0; i --) {
			heapifyDown(i, size);
		}
	}
	
	private void heapifyDown(int i, int n) {
		int l = left(i);
		int r = right(i);
		int max = i;
		if (l < n && array[l] > array[max]) {
			max = l;
		}
		if (r < n && array[r] > array[max]) {
			max = r;
		}
		if (max != i) {
			swap(max, i);
			heapifyDown(max, n);
		}
	}
	
	public void sort() {
		for (int i = size - 1; i >= 0 ; i --) {
			swap(0, i);
			heapifyDown(0, i);
		}
	}
	
	private void swap(int i , int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	
	private int left(int i) {
		return 2 * i + 1;
	}
	
	private int right(int i) {
		return 2 * i + 2;
	}
	
	public void print() {
		for (int i = 0; i < size; i ++) {
			System.out.print("" + array[i] + " ");
		}
		System.out.println();
	}	
	
	private static int readSize(Scanner scanner) {
		return Integer.parseInt(scanner.nextLine());
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = readSize(scanner);
		PriorityQueue pq = new PriorityQueue();
		for (int i = 0; i < n; i ++) {
			pq.enqueue(scanner.nextInt());
			pq.print();
		}
		//pq.print();
		Integer cur;
		while ((cur = pq.dequeue()) != null) {
			System.out.println(cur);
		}
		scanner.close();
	}
}
