import java.util.Random;


public class ConnectedProbability {
	
	public static boolean connected(int M, int N, float threshold) {
		int rows = M + 2;
		int cols = N + 1;
		
		UnionFind uf = new UnionFind(rows * cols);
		
		boolean[][] bitmap = new boolean[rows][cols];
		for (int j = 1; j < cols; j ++) {
			bitmap[0][j] = true;
			uf.union(j, 0);
		}
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < rows; i ++) {
			for (int j = 1; j < cols; j ++) {
				if (i != 0) {
					if (i == rows - 1) {
						bitmap[i][j] = true;
					} else {
						bitmap[i][j] = random.nextFloat() > threshold;
					}
					if (bitmap[i][j]) {
						int p = i * cols + j;
						if (bitmap[i - 1][j]) {
							uf.union(p, p - cols);
						}
						if (bitmap[i][j - 1]) {
							uf.union(p, p - 1);
						}
					}	
				}
				System.out.print(bitmap[i][j] ? "  X " : "  O ");
			}
			System.out.println();
		}
		for (int i = 0; i < rows; i ++) {
			for (int j = 1; j < cols; j ++) {
				System.out.print(String.format("%4d", uf.find(i * cols + j)));
			}
			System.out.println();
		}
		return uf.connected(0, cols * rows - 1);
	}

	public static void main(String[] args) {
		int ROWS = 10;
		int COLS = 10;
		int count = 0;
		int tests = 1;
		float threshold = 0.40f;
		for (int i = 0; i < tests; i ++) {
			if (connected(ROWS, COLS, threshold)) {
				count ++;
			}
		}
		System.out.println("Connected probability under threshold " + threshold + " is " + ((float)count) / tests * 100 + "%");

		
	}

}
