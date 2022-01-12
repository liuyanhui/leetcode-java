package leetcode;

/**
 * https://leetcode.com/problems/unique-paths-ii/
 * 63. Unique Paths II
 * Medium
 * --------------------
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * Now consider if some obstacles are added to the grids. How many unique paths would there be?
 * An obstacle and space is marked as 1 and 0 respectively in the grid.
 *
 * Example 1:
 * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * Output: 2
 * Explanation: There is one obstacle in the middle of the 3x3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 *
 * Example 2:
 * Input: obstacleGrid = [[0,1],[0,0]]
 * Output: 1
 *
 * Constraints:
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] is 0 or 1.
 */
public class Unique_Paths_II_63 {
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        return uniquePathsWithObstacles_3(obstacleGrid);
    }

    /**
     * round 2
     * DP思路
     *
     * 验证通过:
     * Runtime: 1 ms, faster than 34.80% of Java online submissions for Unique Paths II.
     * Memory Usage: 38.8 MB, less than 32.97% of Java online submissions for Unique Paths II.
     *
     * 有性能更优的办法是，直接使用obstacleGrid作为dp[][]进行计算。
     *
     * @param obstacleGrid
     * @return
     */
    public static int uniquePathsWithObstacles_3(int[][] obstacleGrid) {
        if (obstacleGrid.length <= 0 || obstacleGrid[0].length <= 0
                || obstacleGrid[0][0] == 1
                || obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1] == 1)
            return 0;
        int[] dp = new int[obstacleGrid[0].length + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[i].length; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j + 1] = 0;
                } else {
                    dp[j + 1] = dp[j] + dp[j + 1];
                }
            }
        }
        return dp[obstacleGrid[0].length];
    }

    /**
     * DP问题，与Unique_Paths_62相似，只不过多了一个obstacle原因。
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Paths II.
     * Memory Usage: 38.1 MB, less than 67.40% of Java online submissions for Unique Paths II.
     * @param obstacleGrid
     * @return
     */
    public static int uniquePathsWithObstacles_1(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        dp[0][0] = 1;
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 1) {//有障碍的点，dp=0
                    dp[i][j] = 0;
                } else if (i == 0 && j > 0) {//第0行
                    dp[0][j] = dp[0][j - 1];
                } else if (j == 0 && i > 0) {//第0列
                    dp[i][0] = dp[i - 1][0];
                } else if (i > 0 && j > 0) {//非第0行和第0列
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    /**
     * uniquePathsWithObstacles_1的精简版本，dp由二维数组该位一位数组
     * 参考思路：https://leetcode.com/problems/unique-paths-ii/discuss/23250/Short-JAVA-solution
     * @param obstacleGrid
     * @return
     */
    public static int uniquePathsWithObstacles_2(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        int dp[] = new int[obstacleGrid[0].length];
        dp[0] = 1;
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (j > 0) {
                    dp[j] += dp[j - 1];
                }
                //上面这段代码是下面被注释代码的简化版。
                /*if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (j == 0) {
                    dp[j] = dp[j];
                } else {
                    dp[j] += dp[j - 1];
                }*/
            }
        }
        return dp[dp.length - 1];

    }

    public static void main(String[] args) {
        do_func(new int[][]{{}}, 0);
        do_func(new int[][]{{1}}, 0);
        do_func(new int[][]{{0}}, 1);
        do_func(new int[][]{{0, 1}, {1, 1}}, 0);
        do_func(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, 2);
        do_func(new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, 6);
        do_func(new int[][]{{0, 1}, {0, 0}}, 1);
        do_func(new int[][]{{0}, {1}}, 0);
        do_func(new int[][]{{0, 0}, {1, 0}}, 1);
    }

    private static void do_func(int[][] obstacleGrid, int expected) {
        int ret = uniquePathsWithObstacles(obstacleGrid);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
