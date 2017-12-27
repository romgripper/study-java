import java.util.Scanner;

public class Rotate {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int r = scanner.nextInt();
        
        int[][] array = new int[m][n];
        for (int row = 0; row < m; row ++) {
            for (int col = 0; col < n; col ++) {
            	int i = row;
            	int j = col;
            	int rect = getRectIndex(i, j, m, n);
            	int eleNumber = getTotalElementNumberOnRect(m, n, rect);
            	int height = getRectHeight(m, rect);
            	int width = getRectWidth(n, rect);
            	int rotateTime = r % eleNumber;
            	i -= rect;
            	j -= rect;
                int indexOnRect = getIndexOnRect(height, width, i, j);
                indexOnRect += rotateTime;
                indexOnRect %= eleNumber;
                i = getRowOnRect(height, width, indexOnRect);
                j = getColOnRect(height, width, indexOnRect);
                i += rect;
                j += rect;
                array[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        
    	for (int i = 0; i < m; i ++) {
			for (int j = 0; j < n; j ++) {
				System.out.print("" + array[i][j] + " ");
			}
			System.out.println();
		}
    }
    
    private static int getIndexOnRect(int height, int width, int row, int col) {
    	if (col == 0) {
    		return row;
    	} else if (row == height - 1) {
    		return height - 1 + col;
    	} else if (col == width - 1) {
    		return width + height - 1 + (height - row - 2);
    	} else { // row == 0 
    		return 2 * (height + width) - 4 - col;
    	}
    }
    
    private static int getRowOnRect(int height, int width, int index) {
    	if (index < height) {
    		return index;
    	} else if (index < height + width - 1) {
    		return height - 1;
    	} else if (index < 2 * height + width - 2) {
    		return  2 * height + width - 3 - index;
    	} else {
    		return 0;
    	}
    }
    
    private static int getColOnRect(int height, int width, int index) {
    	if (index < height) {
    		return 0;
    	} else if (index < height + width - 1) {
    		return index - height + 1;
    	} else if (index < 2 * height + width - 2) {
    		return  width - 1;
    	} else {
    		return 2 * height + 2 * width -4 - index ;
    	}
    }
   
    private static int getRectIndex(int row, int col, int m, int n) {
        return Math.min(Math.min(row, col), Math.min(m - row - 1, n - col - 1));
    }
    
    private static int getRectHeight(int m, int rect) {
    	return m - 2 * rect;
    }
    
    private static int getRectWidth(int n, int rect) {
    	return n - 2 * rect;
    }
    
    private static int getTotalElementNumberOnRect(int m, int n, int rect) {
        return 2 * (getRectHeight(m, rect) + getRectWidth(n, rect)) - 4;
    }
}