package leetcode;

/**
 * 64. Minimum Path Sum
 * Medium
 * ----------------------------
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in time.
 *
 * Example 1:
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 *
 * Example 2:
 * Input: grid = [[1,2,3],[4,5,6]]
 * Output: 12
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 200
 * 0 <= grid[i][j] <= 100
 */
public class Minimum_Path_Sum_64 {
    public static int minPathSum(int[][] grid) {
        return minPathSum_2(grid);
    }

    /**
     * 直接使用grid作为dp数组
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 15.29% of Java online submissions for Minimum Path Sum.
     * Memory Usage: 42.2 MB, less than 34.97% of Java online submissions for Minimum Path Sum.
     *
     * @param grid
     * @return
     */
    public static int minPathSum_2(int[][] grid) {
        for (int i = 1; i < grid.length; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int j = 1; j < grid[0].length; j++) {
            grid[0][j] += grid[0][j - 1];
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                grid[i][j] = grid[i][j] + Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * DP思路
     * dp[i][j]=grid[i][j]+min(dp[i-1][j],dp[i][j-1])
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 84.15% of Java online submissions for Minimum Path Sum.
     * Memory Usage: 42.3 MB, less than 34.97% of Java online submissions for Minimum Path Sum.
     *
     * @param grid
     * @return
     */
    public static int minPathSum_1(int[][] grid) {
        int[] dp = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (j == 0) {
                    dp[0] += grid[i][0];
                } else {
                    dp[j] = dp[j] == 0 ? 999 : dp[j];//初始化dp数组
                    dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[grid[0].length - 1];
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}, 7);
        do_func(new int[][]{{1, 2, 3}, {4, 5, 6}}, 12);
        do_func(new int[][]{{15}}, 15);
        do_func(new int[][]{{1}, {5}}, 6);


    }

    private static void do_func(int[][] grid, int expected) {
        int ret = minPathSum(grid);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
