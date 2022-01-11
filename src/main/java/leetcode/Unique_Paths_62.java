package leetcode;

/**
 * https://leetcode.com/problems/unique-paths/
 * 62. Unique Paths
 * Medium
 * --------------
 * There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 *
 * Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 *
 * The test cases are generated so that the answer will be less than or equal to 2 * 10^9.
 *
 * Example 1:
 * Input: m = 3, n = 7
 * Output: 28
 *
 * Example 2:
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *
 * Constraints:
 * 1 <= m, n <= 100
 */
public class Unique_Paths_62 {

    public static int uniquePaths(int m, int n) {
        return uniquePaths_dp3(m, n);
    }

    /**
     * round2
     * uniquePaths_dp2优化版：二维dp数组该位一维dp数组
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Paths.
     * Memory Usage: 37.8 MB, less than 14.89% of Java online submissions for Unique Paths.
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths_dp3(int m, int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    /**
     * round2
     * dp思路：dp[m][n]。因为只能向右和向下移动，所以dp[i,j]依赖于其左边和上边的路径数量。
     * 公式为:dp[i][j]=dp[i-1][j]+dp[i][j-1]，return dp[m-1][n-1]
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 19.48% of Java online submissions for Unique Paths.
     * Memory Usage: 37.4 MB, less than 25.45% of Java online submissions for Unique Paths.
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths_dp2(int m, int n) {
        int[][] dp = new int[m][n];//可以优化成一维数组
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][0] = 1;
                    dp[0][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
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
