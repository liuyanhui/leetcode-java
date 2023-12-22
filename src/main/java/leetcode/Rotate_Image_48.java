package leetcode;

import java.util.Arrays;

/**
 * 这里有金矿
 * https://leetcode.com/problems/rotate-image/
 * 48. Rotate Image
 * Medium
 * -----------------------------------
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
 *
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
 *
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 *
 * Example 2:
 * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * Constraints:
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 */
public class Rotate_Image_48 {
    public static void rotate(int[][] matrix) {
        rotate_4(matrix);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking：
     * 1.图片rotate的常用套路
     * 先上下对称互换，在沿对角线互换
     *
     * 验证通过：
     *
     * @param matrix
     */
    public static void rotate_4(int[][] matrix) {
        //上下对称互换
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - 1 - i][j];
                matrix[matrix.length - 1 - i][j] = t;
            }
        }
        //对角线互换
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                int t = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = t;
            }
        }
    }

    /**
     * round2
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Image.
     * Memory Usage: 38.8 MB, less than 90.83% of Java online submissions for Rotate Image.
     *
     * @param matrix
     */
    public static void rotate_3(int[][] matrix) {
        //水平对称反转
        int beg = 0, end = matrix.length - 1;
        while (beg < end) {
            for (int i = 0; i < matrix[beg].length; i++) {
                swap(matrix, beg, i, end, i);
            }
            beg++;
            end--;
        }
        //对角线对称反转
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix[i].length; j++) {
                swap(matrix, i, j, j, i);
            }
        }
    }

    private static void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int t = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = t;
    }

    /**
     * 参考思路：https://leetcode.com/problems/rotate-image/discuss/18872/A-common-method-to-rotate-the-image
     * <p>
     * 本体是顺时针旋转，思路为：先上下对称变换，在沿对角线交换（first reverse up to down, then swap the symmetry）
     * <p>
     * 这是rotate matrix的一般性通用做法（here give a common method to solve the image rotation problems）
     * <p>
     * 逆时针旋转，思路为：先左右交换，再沿对角线交换（first reverse left to right, then swap the symmetry）
     * <p>
     * 验证通过：
     * <p>
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Image.
     * <p>
     * Memory Usage: 39.2 MB, less than 32.24% of Java online submissions for Rotate Image.
     *
     * @param matrix
     */
    public static void rotate_2(int[][] matrix) {
        int n = matrix.length;
        //先上下对称交换
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - i][j];
                matrix[n - 1 - i][j] = tmp;
            }
        }
        //再沿对角线对称交换
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    /**
     * 参考思路：rotate_error和https://leetcode.com/problems/rotate-image/solution/中Approach 3 : Rotate four rectangles in one
     * single loop
     * <p>
     * 验证通过：
     * <p>
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Image.
     * <p>
     * Memory Usage: 39.2 MB, less than 31.61% of Java online submissions for Rotate Image.
     *
     * @param matrix
     */
    public static void rotate_1(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            int size = n - i - i - 1;
            for (int j = i; j < i + size; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = tmp;
            }
        }
    }

    /**
     * 思路正确，但是实现错误。
     * <p>
     * 问题在于size变量的选择错误。
     *
     * @param matrix
     */
    public static void rotate_error(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            int size = n - i - i - 1;
            for (int j = i; j < i + size; j++) {
                int x = i, y = j;
                int tmp = matrix[x][y];
                matrix[x][y] = matrix[size - y][x];
                matrix[size - y][x] = matrix[size - x][size - y];
                matrix[size - x][size - y] = matrix[y][size - x];
                matrix[y][size - x] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] expected = new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
        prepareThenRun(matrix, expected);

        int[][] matrix2 = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        int[][] expected2 = new int[][]{{15, 13, 2, 5}, {14, 3, 4, 1}, {12, 6, 8, 9}, {16, 7, 10, 11}};
        prepareThenRun(matrix2, expected2);

        int[][] matrix3 = new int[][]{{1}};
        int[][] expected3 = new int[][]{{1}};
        prepareThenRun(matrix3, expected3);

        int[][] matrix4 = new int[][]{{1, 2}, {3, 4}};
        int[][] expected4 = new int[][]{{3, 1}, {4, 2}};
        prepareThenRun(matrix4, expected4);

    }

    private static void prepareThenRun(int[][] matrix, int[][] expected) {
        rotate(matrix);
        for (int[] m : matrix) {
            System.out.println(Arrays.toString(m));
        }
        boolean ret = doseTheTwoMatrixSame(expected, matrix);
        System.out.println(ret);
        System.out.println("--------------");
    }


    private static boolean doseTheTwoMatrixSame(int[][] m1, int[][] m2) {
        if (m1.length != m2.length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
