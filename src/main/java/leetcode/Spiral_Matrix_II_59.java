package leetcode;

/**
 * https://leetcode.com/problems/spiral-matrix-ii/
 * 59. Spiral Matrix II
 * Medium
 * ----------------------
 * Given a positive integer n, generate an n x n matrix filled with elements from 1 to n^2 in spiral order.
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
        return generateMatrix_3(n);
    }

    /**
     * 类似Spiral_Matrix_54的思路，算法如下：
     * 1.初始化一个n*n的二位数字，num=1
     * 2.按正方形环计算，每个环都分为四个方向（向右->向下->向左->向上），每放置一个数字之后num++。
     * 3.共(n+1)/2个环。每环的边长为n-cricle*2，circle表示环编号从0开始计数。
     * 4.当n-cricle*2<=0时，表示计算结束
     * 5.当n-cricle*2==1时，为由一个点组成的环。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix II.
     * Memory Usage: 38.8 MB, less than 14.02% of Java online submissions for Spiral Matrix II.
     *
     * @param n
     * @return
     */
    public static int[][] generateMatrix_3(int n) {
        int[][] ret = new int[n][n];
        int num = 1, circle = 0;
        while (n - circle * 2 > 0) {
            if ((n - circle * 2) == 1) {
                ret[n / 2][n / 2] = num;
                break;
            }
            //每个方向的最后一个元素都由下一个方向处理，作为下一个方向的第一个元素
            //go right
            for (int i = circle; i < n - circle - 1; i++) {
                ret[circle][i] = num++;
            }
            //go down
            for (int i = circle; i < n - circle - 1; i++) {
                ret[i][n - 1 - circle] = num++;
            }
            //go left
            for (int i = n - 1 - circle; i > circle; i--) {
                ret[n - 1 - circle][i] = num++;
            }
            //go up
            for (int i = n - 1 - circle; i > circle; i--) {
                ret[i][circle] = num++;
            }
            circle++;
        }
        return ret;
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
        do_func(20, new int[][]{{1}});
    }

    private static void do_func(int n, int[][] expected) {
        int[][] ret = generateMatrix(n);
        ArrayUtils.printIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("-----------");
    }
}
