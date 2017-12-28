/* Matrix Layer Rotation
You are given a 2D matrix, a, of dimension MxN and a positive integer R. You have to rotate the matrix R times and print the resultant matrix. Rotation should be in anti-clockwise direction.

Rotation of a 4x5 matrix is represented by the following figure. Note that in one rotation, you have to shift elements by one step only (refer sample tests for more clarity).

matrix-rotation

It is guaranteed that the minimum of M and N will be even.

Input Format
First line contains three space separated integers, M, N and R, where M is the number of rows, N is number of columns in matrix, and R is the number of times the matrix has to be rotated.
Then M lines follow, where each line contains N space separated positive integers. These M lines represent the matrix.

Constraints
2 <= M, N <= 300
1 <= R <= 109
min(M, N) % 2 == 0
1 <= aij <= 108, where i ¡Ê [1..M] & j ¡Ê [1..N]

Output Format
Print the rotated matrix.

Sample Input #00

4 4 1
1 2 3 4
5 6 7 8
9 10 11 12
13 14 15 16
Sample Output #00

2 3 4 8
1 7 11 12
5 6 10 16
9 13 14 15
Sample Input #01

4 4 2
1 2 3 4
5 6 7 8
9 10 11 12
13 14 15 16
Sample Output #01

3 4 8 12
2 11 10 16
1 7 6 15
5 9 13 14
Sample Input #02

5 4 7
1 2 3 4
7 8 9 10
13 14 15 16
19 20 21 22
25 26 27 28
Sample Output #02

28 27 26 25
22 9 15 19
16 8 21 13
10 14 20 7
4 3 2 1
Sample Input #03

2 2 3
1 1
1 1
Sample Output #03

1 1
1 1
Explanation
Sample Case #00: Here is an illustration of what happens when the matrix is rotated once.

 1  2  3  4      2  3  4  8
 5  6  7  8      1  7 11 12
 9 10 11 12  ->  5  6 10 16
13 14 15 16      9 13 14 15
Sample Case #01: Here is what happens when to the matrix after two rotations.

 1  2  3  4      2  3  4  8      3  4  8 12
 5  6  7  8      1  7 11 12      2 11 10 16
 9 10 11 12  ->  5  6 10 16  ->  1  7  6 15
13 14 15 16      9 13 14 15      5  9 13 14
Sample Case #02: Following are the intermediate states.

1  2  3  4      2  3  4 10    3  4 10 16    4 10 16 22
7  8  9 10      1  9 15 16    2 15 21 22    3 21 20 28
13 14 15 16 ->  7  8 21 22 -> 1  9 20 28 -> 2 15 14 27 ->
19 20 21 22    13 14 20 28    7  8 14 27    1  9  8 26
25 26 27 28    19 25 26 27    13 19 25 26   7 13 19 25



10 16 22 28    16 22 28 27    22 28 27 26    28 27 26 25
 4 20 14 27    10 14  8 26    16  8  9 25    22  9 15 19
 3 21  8 26 ->  4 20  9 25 -> 10 14 15 19 -> 16  8 21 13
 2 15  9 25     3 21 15 19     4 20 21 13    10 14 20  7
 1  7 13 19     2  1  7 13     3  2  1  7     4  3  2  1
*/

public class Rotate {

  private static void print(int[][] a) {
    for (int[] b : a) {
      for (int c : b) {
        System.out.printf("%5d ", c);
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {

    int[][] a = {
      {1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20} /*,
      {21, 22, 23, 24, 25},
      {26, 27, 28, 29, 30}*/
    };
    print(a);
    int r = -2;
    System.out.println("Rotate counter-clockwise " + r);
    a = rotate(a, r);
    print(a);
    r = 2;
    System.out.println("Rotate counter-clockwise " + r);
    a = rotate(a, r);
    print(a);
  }

  private static int[][] rotate(int[][] a, int r) {
    int m = a.length;
    int n = a[0].length;
    int[][] array = new int[m][n];
    for (int row = 0; row < m; row++) {
      for (int col = 0; col < n; col++) {
        int i = row;
        int j = col;
        int rectIndex = getRectIndex(i, j, m, n);
        int eleNumber = getTotalElementNumberOnRect(m, n, rectIndex);
        int height = getRectHeight(m, rectIndex);
        int width = getRectWidth(n, rectIndex);
        int rotateTime = ((r % eleNumber) + eleNumber) % eleNumber;
        i -= rectIndex;
        j -= rectIndex;
        int indexOnRect = getIndexOnRect(height, width, i, j);
        indexOnRect += rotateTime;
        indexOnRect %= eleNumber;
        i = getRowOnRect(height, width, indexOnRect);
        j = getColOnRect(height, width, indexOnRect);
        i += rectIndex;
        j += rectIndex;
        array[i][j] = a[row][col];
      }
    }
    return array;
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
      return 2 * height + width - 3 - index;
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
      return width - 1;
    } else {
      return 2 * height + 2 * width - 4 - index;
    }
  }

  // 00000000
  // 01111110
  // 01222210
  // 01233210
  // 01222210
  // 01111110
  // 00000000
  // Return 0 if the element is on the boarder of the matrix
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
