package leetcode;

import java.util.List;

/**
 * https://leetcode.com/problems/unique-paths/
 * 62. Unique Paths
 * Medium
 * --------------
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * How many possible unique paths are there?
 *
 * Example 1:
 * Input: m = 3, n = 7
 * Output: 28
 *
 *  Example 2:
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *
 *  Example 3:
 * Input: m = 7, n = 3
 * Output: 28
 *
 *  Example 4:
 * Input: m = 3, n = 3
 * Output: 6
 *
 * Constraints:
 * 1 <= m, n <= 100
 * It's guaranteed that the answer will be less than or equal to 2 * 109.
 */
public class Unique_Paths_62 {

    public static int uniquePaths(int m, int n) {
        return uniquePaths_dp(m, n);
    }

    /**
     * DP问题，推导公式如下：
     * dp[i][j]为从start到(i,j)的唯一路径数。那么，
     * dp[i][j]=dp[i-1][j] + dp[i][j-1] ,当i>0和j>0时;
     * dp[i][j]=dp[i-1][j],当j==0时:
     * dp[i][j]=dp[i][j-1],当i==0时.
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Paths.
     * Memory Usage: 35.7 MB, less than 64.80% of Java online submissions for Unique Paths.
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths_dp(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[0][j] = 1;
                } else if (j == 0) {
                    dp[i][0] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 递归思路.
     * 逻辑正确，但是验证失败，原因：超时。
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths_recusive(int m, int n) {
        return do_recursive(m, n, 0, 0);
    }

    public static int do_recursive(int m, int n, int curI, int curJ) {
        if (curI == m - 1 && curJ == n - 1) {
            return 1;
        }
        if (curI < m && curJ < n) {
            return do_recursive(m, n, curI + 1, curJ) + do_recursive(m, n, curI, curJ + 1);
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func(3, 7, 28);
        do_func(3, 2, 3);
        do_func(7, 3, 28);
        do_func(3, 3, 6);
        do_func(19, 13, 86493225);
    }

    private static void do_func(int m, int n, int expected) {
        int ret = uniquePaths(m, n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
