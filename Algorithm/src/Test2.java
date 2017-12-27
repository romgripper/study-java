import java.util.Scanner;


public class Test2 {

	public static boolean rotateOutline(int a[][], int start, int end) {
		if (start >= end -1 ) {
			return false;
		}
		int tmp = a[start][start];
		int i = start;
		int j = start;
		for (; i < end - 1; i ++) {
			a[i][j] = a[i + 1][j];
		}
		// now i = end -1, j = start
		for (; j < end -1; j ++) {
			a[i][j] = a[i][j + 1];
		}
		// now i = end -1, j = end -1
		for (; i > start; i --) {
			a[i][j] =  a[i - 1][j];
		}
		// now i = start, j= end -1 
		for (; j > start + 1; j --) {
			a[i][j] = a[i][j - 1];
		}
		a[i][j] = tmp;
		return true;
	}
	
	public static void rotate(int a[][], int dim) {
		int start = 0, end = dim;
		while (rotateOutline(a, start ++, end --));
	}
	
	/*public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int dim = readDim(scanner);
		int[][] a = readArray(scanner, dim);
		scanner.close();
		System.out.println("Array readed:");
		printArray(a, dim);
		rotate(a, dim);
		System.out.println("Array rotated:");
		printArray(a, dim);
		
	}*/
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int dim = readDim(scanner);
		int[][] a = readArray1(scanner, dim);
		scanner.close();
		System.out.println("Array rotated:");
		printArray(a, dim);
	}
	
	public static int readDim(Scanner scanner) {
		System.out.println("Input the dimension of the square array:");
		int dim = 1;
		String line;
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			Scanner dimScanner = new Scanner(line);
			if (dimScanner.hasNextInt()) {
				dim = dimScanner.nextInt();
				if (dim > 0) {
					break;
				}
			} else {
				System.out.println("Please enter an integer larger than 0");
			}
			dimScanner.close();
		}
		return dim;
	}

	public static void printArray(int[][] a, int dim) {
		for (int i = 0; i < dim; i ++) {
			for (int j = 0; j < dim; j ++) {
				System.out.print("" + a[i][j] +"\t");
			}
			System.out.println();
		}
	}
	
	public static int[][] readArray(Scanner scanner, int dim) {
		int[][] a = new int[dim][dim];
		System.out.println("Please enter the square array:");
		String line;
		for (int i = 0; i < dim; i ++) {
			if (!scanner.hasNextLine()) {
				System.out.println("ERROR: less rows");
				System.exit(1);
			}
			line = scanner.nextLine();
			Scanner eleScanner = new Scanner(line);
			eleScanner.useDelimiter("\\s+");
			for (int j = 0 ; j < dim; j ++) {
				if (! eleScanner.hasNextInt()) {
					System.out.println("ERROR: less integer in a row");
					System.exit(3);
				} 
				a[i][j] = eleScanner.nextInt();
			}
			if (eleScanner.hasNextInt()) {
				System.out.println("ERROR: more integer in a row");
				System.exit(4);
			}
			eleScanner.close();
		}
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (! line.equals("done")) {
				System.out.println("ERROR: More rows");
				System.exit(2);
			}
		}
		return a;
	}
	
	public static int[][] readArray1(Scanner scanner, int dim) {
		int[][] a = new int[dim][dim];
		System.out.println("Please enter the square array:");
		String line;
		for (int i = 0; i < dim; i ++) {
			if (!scanner.hasNextLine()) {
				System.out.println("ERROR: less rows");
				System.exit(1);
			}
			line = scanner.nextLine();
			Scanner eleScanner = new Scanner(line);
			eleScanner.useDelimiter("\\s+");
			for (int j = 0 ; j < dim; j ++) {
				if (! eleScanner.hasNextInt()) {
					System.out.println("ERROR: less integer in a row");
					System.exit(3);
				}
				int cur = eleScanner.nextInt();
				//a[i][j] = eleScanner.nextInt();
				if (i <= j && i + j < dim - 1) {
					a[i][j+1] = cur;
				}
				if (i < j && i + j >= dim - 1) {
					a[i+1][j] = cur;
				}
				if (i > j && i + j <= dim -1 ) {
					a[i-1][j] = cur;
				}
				if (i >= j && i + j > dim -1 ) {
					a[i][j-1] = cur;
				}
				if (i == j && i + j == dim -1 ){
					a[i][j] = cur;
				}
			}
			if (eleScanner.hasNextInt()) {
				System.out.println("ERROR: more integer in a row: " + eleScanner.nextInt());
				System.exit(4);
			}
			eleScanner.close();
		}
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (! line.equals("done")) {
				System.out.println("ERROR: More rows");
				System.exit(2);
			}
		}
		return a;
	}
}
