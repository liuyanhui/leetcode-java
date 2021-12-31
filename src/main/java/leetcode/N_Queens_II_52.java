package leetcode;

/**
 * 52. N-Queens II
 * Hard
 * ---------------------
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 *
 * Example 1:
 * Input: n = 4
 * Output: 2
 * Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= n <= 9
 */
public class N_Queens_II_52 {
    /**
     * 递归法，思路与"51. N-Queens"类似，但是细节不同。没有采用clone board数组和写board数组的实现方式。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 89.27% of Java online submissions for N-Queens II.
     * Memory Usage: 35.4 MB, less than 94.85% of Java online submissions for N-Queens II.
     *
     * @param n
     * @return
     */
    public static int totalNQueens(int n) {
        int[][] board = new int[n][n];
        return dfs(board, 0);
    }

    private static int dfs(int[][] board, int row) {
        int count = 0;
        for (int i = 0; i < board[row].length; i++) {
            if (!valid(board, row, i)) continue;
            if (row < board.length - 1) {
                board[row][i] = 1;
                count += dfs(board, row + 1);
                board[row][i] = 0;
            } else {
                count += 1;
            }
        }
        return count;
    }

    private static boolean valid(int[][] board, int row, int col) {
        //横向不需要判断(因为是按行循环的，该行元素依次判断)，只判断纵向即可
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        //from row-col to left-top, don't contains row-col
        int tr = row, tc = col;
        while (tr >= 0 && tc >= 0) {
            if (board[tr][tc] == 1) return false;
            tr--;
            tc--;
        }
        //from row-col to right-top, don't contains row-col
        tr = row;
        tc = col;
        while (tr >= 0 && tc < board[row].length) {
            if (board[tr][tc] == 1) return false;
            tr--;
            tc++;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func(4, 2);
        do_func(1, 1);
        do_func(3, 0);
        do_func(2, 0);
        do_func(5, 10);
        do_func(9, 352);
    }

    private static void do_func(int n, int expected) {
        int ret = totalNQueens(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
