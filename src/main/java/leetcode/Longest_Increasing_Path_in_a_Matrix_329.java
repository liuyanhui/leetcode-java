package leetcode;

/**
 * 329. Longest Increasing Path in a Matrix
 * Hard
 * ---------------------------
 * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
 *
 * From each cell, you can either move in four directions: left, right, up, or down. You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).
 *
 * Example 1:
 * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 *
 * Example 2:
 * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
 * Output: 4
 * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 *
 * Example 3:
 * Input: matrix = [[1]]
 * Output: 1
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * 0 <= matrix[i][j] <= 2^31 - 1
 */
public class Longest_Increasing_Path_in_a_Matrix_329 {
    public static int longestIncreasingPath(int[][] matrix) {
        return longestIncreasingPath_1(matrix);
    }

    /**
     * Thinking:
     * 1.naive solution.
     * 遍历+递归。遍历每个节点，递归计算最大长度，并缓存已经遍历过的节点。
     * 时间复杂度：O(N!)
     * 2.从matrix[i,j]开始的计算结果是确定不变的，可以用在其他数字matrix[p,q]计算过程中复用。因为结果是递增的，所以局部最优解链路不会出现交叉的情况。
     * 3.二维数组的问题，一般由两种思路：BFS或DFS。
     *
     * 验证通过：
     * Runtime 7 ms Beats 99.52%
     * Memory 42.9 MB Beats 42.26%
     *
     * 这个最多是Medium难度
     *
     * @param matrix
     * @return
     */
    public static int longestIncreasingPath_1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int res = 0;
        int[][] cached = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                res = Math.max(res, helper(matrix, cached, i, j));
            }
        }
        return res;
    }

    private static int helper(int[][] matrix, int[][] cached, int row, int col) {
        if (cached[row][col] > 0) return cached[row][col];
        int res = 0;
        //0 o'clock
        if (row - 1 >= 0) {
            if (matrix[row - 1][col] > matrix[row][col]) {
                res = Math.max(res, helper(matrix, cached, row - 1, col));
            }
        }
        //3 o'clock
        if (col + 1 < matrix[0].length) {
            if (matrix[row][col + 1] > matrix[row][col]) {
                res = Math.max(res, helper(matrix, cached, row, col + 1));
            }
        }
        //6 o'clock
        if (row + 1 < matrix.length) {
            if (matrix[row + 1][col] > matrix[row][col]) {
                res = Math.max(res, helper(matrix, cached, row + 1, col));
            }
        }
        //9 o'clock
        if (col - 1 >= 0) {
            if (matrix[row][col - 1] > matrix[row][col]) {
                res = Math.max(res, helper(matrix, cached, row, col - 1));
            }
        }
        cached[row][col] = res + 1;
        return cached[row][col];
    }

    public static void main(String[] args) {
        do_func(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}}, 4);
        do_func(new int[][]{{3, 4, 5}, {3, 2, 6}, {2, 2, 1}}, 4);
        do_func(new int[][]{{1}}, 1);

    }

    private static void do_func(int[][] matrix, int expected) {
        int ret = longestIncreasingPath(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
