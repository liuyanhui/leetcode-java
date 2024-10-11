package leetcode;

/**
 * 200. Number of Islands
 * Medium
 * ----------------------
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Example 2:
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 */
public class Number_of_Islands_200 {
    public static int numIslands(char[][] grid) {
        return numIslands_1(grid);
    }
    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 先找出每个island，并编号；再根据编号计算出island数量。
     * 采用dfs思路找出island。
     *
     * 参考numIslands_1()
     */

    /**
     * 1.先查找再统计
     * 2.矩阵的遍历，一般由两种：dfs和bfs
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 55.84% of Java online submissions for Number of Islands.
     * Memory Usage: 57.4 MB, less than 47.50% of Java online submissions for Number of Islands.
     *
     * @param grid
     * @return
     */
    public static int numIslands_1(char[][] grid) {
        int cnt = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    cnt++;
                }
                dfs(grid, i, j);
            }
        }
        return cnt;
    }

    private static void dfs(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return;
        }
        if (grid[row][col] == '1') {
            grid[row][col] = '2';
        } else if (grid[row][col] == '0' || grid[row][col] == '2') {
            return;
        }
        dfs(grid, row - 1, col);
        dfs(grid, row, col + 1);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}}, 1);
        do_func(new char[][]{{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}}, 3);
        do_func(new char[][]{{'1'}}, 1);
        do_func(new char[][]{{'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}}, 1);
        do_func(new char[][]{{'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}}, 0);
    }

    private static void do_func(char[][] matrix, int expected) {
        int ret = numIslands(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
