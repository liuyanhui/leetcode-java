package leetcode;

/**
 * https://leetcode.com/problems/spiral-matrix-ii/
 * 59. Spiral Matrix II
 * Medium
 * ----------------------
 * Given a positive integer n, generate an n x n matrix filled with elements from 1 to n2 in spiral order.
 *
 * Example 1:
 * Input: n = 3
 * Output: [[1,2,3],[8,9,4],[7,6,5]]
 *
 * Example 2:
 * Input: n = 1
 * Output: [[1]]
 *
 * Constraints:
 * 1 <= n <= 20
 */
public class Spiral_Matrix_II_59 {

    public static int[][] generateMatrix(int n) {
        return generateMatrix_1(n);
    }

    /**
     * 参考思路：https://leetcode.com/problems/spiral-matrix-ii/solution/
     * 分层法
     * @param n
     * @return
     */
    public static int[][] generateMatrix_2(int n) {
        int[][] result = new int[n][n];
        int cnt = 1;
        for (int layer = 0; layer < (n + 1) / 2; layer++) {
            // direction 1 - traverse from left to right
            for (int ptr = layer; ptr < n - layer; ptr++) {
                result[layer][ptr] = cnt++;
            }
            // direction 2 - traverse from top to bottom
            for (int ptr = layer + 1; ptr < n - layer; ptr++) {
                result[ptr][n - layer - 1] = cnt++;
            }
            // direction 3 - traverse from right to left
            for (int ptr = layer + 1; ptr < n - layer; ptr++) {
                result[n - layer - 1][n - ptr - 1] = cnt++;
            }
            // direction 4 - traverse from bottom to top
            for (int ptr = layer + 1; ptr < n - layer - 1; ptr++) {
                result[n - ptr - 1][layer] = cnt++;
            }
        }
        return result;
    }

    /**
     * 跟Spiral_Matrix中的spiralOrder_3()的思路一样
     *
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix II.
     * Memory Usage: 37.4 MB, less than 33.52% of Java online submissions for Spiral Matrix II.
     * @param n
     * @return
     */
    public static int[][] generateMatrix_1(int n) {
        if (n <= 0) {
            return null;
        }
        int[][] ret = new int[n][n];
        int cur = 1;
        int r0 = 0, r1 = n - 1, c0 = 0, c1 = n - 1;
        while (cur <= n * n) {
            for (int i = c0; i <= c1 && cur <= n * n; i++) {
                ret[r0][i] = cur++;
            }
            r0++;
            for (int i = r0; i <= r1 && cur <= n * n; i++) {
                ret[i][c1] = cur++;
            }
            c1--;
            for (int i = c1; i >= c0 && cur <= n * n; i--) {
                ret[r1][i] = cur++;
            }
            r1--;
            for (int i = r1; i >= r0 && cur <= n * n; i--) {
                ret[i][c0] = cur++;
            }
            c0++;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, new int[][]{{1, 2, 3}, {8, 9, 4}, {7, 6, 5}});
        do_func(1, new int[][]{{1}});
    }

    private static void do_func(int n, int[][] expected) {
        int[][] ret = generateMatrix(n);
        boolean same = true;
        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret[0].length; j++) {
                if (ret[i][j] != expected[i][j]) {
                    same = false;
                    break;
                }
            }
        }
        System.out.println(same);
        System.out.println("-----------");
    }
}
