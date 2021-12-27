package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 51. N-Queens
 * Hard
 * -----------------------
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
 *
 * Example 1:
 * Input: n = 4
 * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
 *
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 *
 * Constraints:
 * 1 <= n <= 9
 */
public class N_Queens_51 {
    /**
     * 递归思路。
     *
     * 类似于sudoku问题。一共四个方向有约束：横向、纵向、左斜、右斜，呈米字型
     * 1.对每一行进行递归，每行记为r
     * 2.每次递归时，按列遍历，每列记为c.如果ret[r,c]==null，那么ret[r,c]="Q"
     * 3.递归下一行 ，即r+1
     * 4.达到最后一行时，加入结果集。
     * 5.重复步骤1.
     *
     * 验证通过：
     * Runtime: 11 ms, faster than 16.84% of Java online submissions for N-Queens.
     * Memory Usage: 39.2 MB, less than 71.86% of Java online submissions for N-Queens.
     *
     * @param n
     * @return
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> ret = new ArrayList<>();
        String[][] board = new String[n][n];
        backtrack(board, 0, ret);
        return ret;
    }

    private static void backtrack(String[][] board, int r, List<List<String>> ret) {
        int n = board.length;
        if (r >= n) return;
        for (int c = 0; c < n; c++) {
            if (board[r][c] != null) continue;
            // 这里很重要，使用clone之后，后续的递归结束出栈时就不需要回退中间状态了。
            // 不能使用java语言的clone方法
            // replicate the board
            String[][] newBoard = clone(board);
            fill(newBoard, r, c);
            if (r == n - 1) {//递归到最后一行时，合并到结果集中
                List<String> t = new ArrayList<>();
                for (int p = 0; p < n; p++) {
                    StringBuilder sb = new StringBuilder();
                    for (int q = 0; q < n; q++) {
                        sb.append(newBoard[p][q]);
                    }
                    t.add(sb.toString());
                }
                ret.add(t);
            } else {
                backtrack(newBoard, r + 1, ret);
            }
        }
    }

    private static String[][] clone(String[][] board) {
        String[][] cloned = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                cloned[i][j] = board[i][j];
            }
        }
        return cloned;
    }

    private static void fill(String[][] board, int r, int c) {
        board[r][c] = "Q";
        for (int i = 0; i < board.length; i++) {
            //横向
            if (board[r][i] == null) board[r][i] = ".";
            //纵向
            if (board[i][c] == null) board[i][c] = ".";
        }
        //从左上到右下
        int tr = r, tc = c;
        while (tr >= 0 && tc >= 0) {
            if (board[tr][tc] == null) board[tr][tc] = ".";
            tr--;
            tc--;
        }
        tr = r;
        tc = c;
        while (tr < board.length && tc < board.length) {
            if (board[tr][tc] == null) board[tr][tc] = ".";
            tr++;
            tc++;
        }
        //从右上到左下
        tr = r;
        tc = c;
        while (tr >= 0 && tc < board.length) {
            if (board[tr][tc] == null) board[tr][tc] = ".";
            tr--;
            tc++;
        }
        tr = r;
        tc = c;
        while (tr < board.length && tc >= 0) {
            if (board[tr][tc] == null) board[tr][tc] = ".";
            tr++;
            tc--;
        }
    }

    public static void main(String[] args) {
        do_func(4, new String[][]{{".Q..", "...Q", "Q...", "..Q."}, {"..Q.", "Q...", "...Q", ".Q.."}});
        do_func(1, new String[][]{{"Q"}});
        do_func(2, new String[][]{{""}});
        do_func(3, new String[][]{{""}});
        do_func(5, new String[][]{{"Q....", "..Q..", "....Q", ".Q...", "...Q."}, {"Q....", "...Q.", ".Q...", "....Q", "..Q.."}, {".Q...", "...Q.", "Q....", "..Q..", "....Q"}, {".Q...", "....Q", "..Q..", "Q....", "...Q."}, {"..Q..", "Q....", "...Q.", ".Q...", "....Q"}, {"..Q..", "....Q", ".Q...", "...Q.", "Q...."}, {"...Q.", "Q....", "..Q..", "....Q", ".Q..."}, {"...Q.", ".Q...", "....Q", "..Q..", "Q...."}, {"....Q", ".Q...", "...Q.", "Q....", "..Q.."}, {"....Q", "..Q..", "Q....", "...Q.", ".Q..."}});


    }

    private static void do_func(int n, String[][] expected) {
        List<List<String>> ret = solveNQueens(n);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
