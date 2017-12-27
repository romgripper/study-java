import java.util.Arrays;
import java.util.Scanner;

public class PriorityQueue {
	private static final int INIT_CAPICITY = 8;
	private int[] array;
	private int size = 0;
	private int capicity;
	
	public PriorityQueue() {
		this(INIT_CAPICITY);
	}
	
	public PriorityQueue(int capicity) {
		this.capicity = capicity;
		array = new int[capicity];
	}
	
	public PriorityQueue(int[] initArray) {
		capicity = size = initArray.length;
		array = Arrays.copyOf(initArray, size);
		buildHeap();
	}
	
	private void enlargeCapicity() {
		capicity <<= 1;
		System.out.println("Enlarge capacity to " + capicity);
		/*int[] newArray = new int[capicity];
		for (int i = 0; i < size; i ++) {
			newArray[i] = array[i]; 
		}
		array = newArray;*/
		array = Arrays.copyOf(array, capicity);
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
			heapifyDown(0);
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
			heapifyDown(i);
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
	
	private void heapifyDown(int i) {
		heapifyDown(i, size);
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
	
	public static void main(String[] args) {
		int a[] = { 2, 4, 6, 10, 8, 9, 5, 7, 1, 3};
		
		// Test 1
		System.out.println("Test 1");
		PriorityQueue pq = new PriorityQueue();
		for (int i = 0; i < a.length; i ++) {
			pq.enqueue(a[i]);
			pq.print();
		}
		//pq.print();
		Integer cur;
		while ((cur = pq.dequeue()) != null) {
			System.out.println(cur);
		}
		
		// Test 2
		System.out.println("Test 2");
		pq = new PriorityQueue(a.length);
		for (int i = 0; i < a.length; i ++) {
			pq.enqueue(a[i]);
			pq.print();
		}
		//pq.print();
		while ((cur = pq.dequeue()) != null) {
			System.out.println(cur);
		}
		
		// Test 3
		System.out.println("Test 3");
		pq = new PriorityQueue(a);
		pq.print();
		pq.sort();
		pq.print();
	}
}
