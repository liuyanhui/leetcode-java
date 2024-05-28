package leetcode;

import java.util.List;

/**
 * 2812. Find the Safest Path in a Grid
 * Medium
 * -------------------------------
 * You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:
 *  - A cell containing a thief if grid[r][c] = 1
 *  - An empty cell if grid[r][c] = 0
 *
 * You are initially positioned at cell (0, 0). In one move, you can move to any adjacent cell in the grid, including cells containing thieves.
 *
 * The safeness factor of a path on the grid is defined as the minimum manhattan distance from any cell in the path to any thief in the grid.
 *
 * Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).
 *
 * An adjacent cell of cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) and (r - 1, c) if it exists.
 *
 * The Manhattan distance between two cells (a, b) and (x, y) is equal to |a - x| + |b - y|, where |val| denotes the absolute value of val.
 *
 * Example 1:
 * Input: grid = [[1,0,0],[0,0,0],[0,0,1]]
 * Output: 0
 * Explanation: All paths from (0, 0) to (n - 1, n - 1) go through the thieves in cells (0, 0) and (n - 1, n - 1).
 *
 * Example 2:
 * Input: grid = [[0,0,1],[0,0,0],[0,0,0]]
 * Output: 2
 * Explanation: The path depicted in the picture above has a safeness factor of 2 since:
 * - The closest cell of the path to the thief at cell (0, 2) is cell (0, 0). The distance between them is | 0 - 0 | + | 0 - 2 | = 2.
 * It can be shown that there are no other paths with a higher safeness factor.
 *
 * Example 3:
 * Input: grid = [[0,0,0,1],[0,0,0,0],[0,0,0,0],[1,0,0,0]]
 * Output: 2
 * Explanation: The path depicted in the picture above has a safeness factor of 2 since:
 * - The closest cell of the path to the thief at cell (0, 3) is cell (1, 2). The distance between them is | 0 - 1 | + | 3 - 2 | = 2.
 * - The closest cell of the path to the thief at cell (3, 0) is cell (3, 2). The distance between them is | 3 - 3 | + | 0 - 2 | = 2.
 * It can be shown that there are no other paths with a higher safeness factor.
 *
 * Constraints:
 * 1 <= grid.length == n <= 400
 * grid[i].length == n
 * grid[i][j] is either 0 or 1.
 * There is at least one thief in the grid.
 *
 */
public class Find_the_Safest_Path_in_a_Grid_2812 {
    public static int maximumSafenessFactor(List<List<Integer>> grid) {
        return maximumSafenessFactor_1(grid);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     *
     * Thinking：
     * 1. navive solution
     * 穷举每种可能(从左上到右下），找到全局最优解。每个cell都有3种可能，即：向上、向右、向下、向左，中的3种（因为每个cell都有它的上一个cell，所以要去掉1种）。
     * 采用DFS方案实现。
     * Time Complexity:O(3^N)
     * 2. Greedy solution
     * 每一步都选局部最优解，组合在一起仍然为全局最优解。因为：已经计算过的cell，如果在第二次访问时，其manhattan distance是固定的。即使在不同的路径上也是固定不变的。所以只要
     * 每个cell都需要在grid中查找其局部最优解。
     * 问题转化为：先计算所有cell的maxmum safeness manhattan distance，保存在二维数组A中。在A中查找从起点到终点的最大manhattan distance路径，并返回该路径中的最小值。
     * Time Complexity:O(N*N)
     * 3.先找到所有路径，并计算这些路径的最小曼哈顿距离，再计算出全局最小值。
     * 如果没有找到路径，直接返回0。
     * 4. DFS方案。
     * 4.1. 先计算每个cell的最小安全距离，并保存在二维数组A中。
     * 4.2. 在A中使用DFS法找到从起点到终点的路径，路径中每个cell都选择该cell的局部最优解。
     *
     * 实现过于复杂，放弃。
     *
     * @param grid
     * @return
     */
    public static int maximumSafenessFactor_1(List<List<Integer>> grid) {
        return -1;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 1}}, 0);
        do_func(new int[][]{{0, 0, 1}, {0, 0, 0}, {0, 0, 0}}, 2);
        do_func(new int[][]{{0, 0, 0, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 0}}, 2);
        System.out.println("------- THE END -------");
    }

    private static void do_func(int[][] grid, int expected) {
        int ret = maximumSafenessFactor(ArrayListUtils.arrayToList(grid));
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
