package leetcode;

/**
 * 221. Maximal Square
 * Medium
 * -----------------------
 * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
 *
 * Example 1:
 * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 4
 *
 * Example 2:
 * Input: matrix = [["0","1"],["1","0"]]
 * Output: 1
 *
 * Example 3:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 300
 * matrix[i][j] is '0' or '1'.
 */
public class Maximal_Square_221 {
    public static int maximalSquare(char[][] matrix) {
        return maximalSquare_4(matrix);
    }

    /**
     * round 2
     *
     * 思考：
     * 1.暴力法。遍历整个matrix，依次计算以[i,j]作为左上角顶点的square的面积，最后取最大值。Time Complexity:O(M*N*M*N)
     * 2.正方形由右下的实心小正方形+左上部分的厂形组成。可用DP思路。
     *
     * DB思路
     * 1.从左下到右上遍历整个矩阵
     * 2.DP公式为：
     * 2.1.dp[i,j]=1+min(dp[i-1,j],dp[i,j-1],dp[i-1,j-1]) 当[i,j]==1时
     * 2.2.dp[i,j]=0 当[i,j]==0时
     * 3.max(dp)*max(dp)就是所求
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 89.04% of Java online submissions for Maximal Square.
     * Memory Usage: 57.6 MB, less than 70.48% of Java online submissions for Maximal Square.
     *
     * @param matrix
     * @return
     */
    public static int maximalSquare_4(char[][] matrix) {
        int res = 0;
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], Math.min(dp[i][j + 1], dp[i + 1][j + 1]));
                    res = Math.max(res, dp[i][j]);
                }
            }
        }
        return res * res;
    }

    /**
     * maximalSquare_2()的简化版
     * 参考思路：
     * https://leetcode.com/problems/maximal-square/solution/
     * @param matrix
     * @return
     */
    public static int maximalSquare_3(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        //dp容量比matrix扩大一圈，保证matrix从[0][0]开始
        int[][] dp = new int[rows + 1][cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[i][j]);
                }
            }
        }
        return maxsqlen * maxsqlen;
    }

    /**
     * DP思路,公式如下：
     * dp[i][j]表示[i,j]作为右下角的最大正方形面积
     * t=min(dp[i-1][j],dp[i][j-1],dp[i-1][j-1])
     * dp[i][j] = (Math.sqrt(t)+1)^2
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 98.15% of Java online submissions for Maximal Square.
     * Memory Usage: 42 MB, less than 68.80% of Java online submissions for Maximal Square.
     *
     * @param matrix
     * @return
     */
    public static int maximalSquare_2(char[][] matrix) {
        int ret = 0;
        int dp[][] = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                dp[i][j] = matrix[i][j] - '0';
                if (ret == 0 && matrix[i][j] == '1') ret = 1;
            }
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (dp[i][j] == 1) {
                    int t = Math.min(dp[i - 1][j], dp[i][j - 1]);
                    t = Math.min(t, dp[i - 1][j - 1]);
                    dp[i][j] = (int) Math.pow(Math.sqrt(t) + 1, 2);
                    ret = Math.max(ret, dp[i][j]);
                }
            }
        }

        return ret;
    }

    /**
     * 思路如下：
     * 1.从[0,0]开始依次遍历每个元素。
     * 2.采用递归的方式，每次递归分为三步：向右扩展一列；向下扩展一行；斜向下扩展一格
     *
     * Time Complexity:O(n^3)
     * Space Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 11 ms, faster than 10.20% of Java online submissions for Maximal Square.
     * Memory Usage: 42.6 MB, less than 14.97% of Java online submissions for Maximal Square.
     * @param matrix
     * @return
     */
    public static int maximalSquare_1(char[][] matrix) {
        int ret = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int t = 0;
                if (matrix[i][j] == '1') {
                    t = 1;
                    t += area(matrix, i, j, i, j);
                }
                ret = ret > t ? ret : t;
            }
        }
        return ret;
    }

    private static int area(char[][] matrix, int r1, int c1, int r2, int c2) {
        if (r2 >= matrix.length - 1 || c2 >= matrix[0].length - 1) return 0;
        int ret = 0;
        //向右扩展
        for (int i = r1; i <= r2; i++) {
            if (matrix[i][c2 + 1] == '1') {
                ret++;
            } else {
                ret = 0;
                break;
            }
        }
        //向下扩展
        if (ret > 0) {
            for (int i = c1; i <= c2; i++) {
                if (matrix[r2 + 1][i] == '1') {
                    ret++;
                } else {
                    ret = 0;
                    break;
                }
            }
        }
        //斜向加1
        if (ret > 0) {
            if (matrix[r2 + 1][c2 + 1] == '1') {
                ret++;
            } else {
                ret = 0;
            }
        }
        //递归扩展
        if (ret > 0) {
            ret += area(matrix, r1, c1, r2 + 1, c2 + 1);
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}, 4);
        do_func(new char[][]{{'0', '1'}, {'1', '0'}}, 1);
        do_func(new char[][]{{'0'}}, 0);
        do_func(new char[][]{{'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'0', '0', '0', '0', '0'}, {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}}, 4);
    }

    private static void do_func(char[][] matrix, int expected) {
        int ret = maximalSquare(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
