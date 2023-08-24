package leetcode;

/**
 * 419. Battleships in a Board
 * Medium
 * -------------------------------
 * Given an m x n matrix board where each cell is a battleship 'X' or empty '.', return the number of the battleships on board.
 *
 * Battleships can only be placed horizontally or vertically on board. In other words, they can only be made of the shape 1 x k (1 row, k columns) or k x 1 (k rows, 1 column), where k can be of any size. At least one horizontal or vertical cell separates between two battleships (i.e., there are no adjacent battleships).
 *
 * Example 1:
 * Input: board = [["X",".",".","X"],[".",".",".","X"],[".",".",".","X"]]
 * Output: 2
 *
 * Example 2:
 * Input: board = [["."]]
 * Output: 0
 *
 * Constraints:
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] is either '.' or 'X'.
 *
 * Follow up: Could you do it in one-pass, using only O(1) extra memory and without modifying the values board?
 */
public class Battleships_in_a_Board_419 {
    public static int countBattleships(char[][] board) {
        return countBattleships_1(board);
    }

    /**
     * Thinking：
     * 1.只存在水平或垂直的连续的X，并且不存在水平和垂直时相连的情况。
     * 2.按行遍历。
     * 2.1.如果board[i][j]=='.'，计算下一元素。
     * 2.2.如果board[i-1][j]=='X'，计算下一个元素；否则，返回结果+1。
     * 2.3.如果board[i][j-1]=='X'，计算下一个元素；否则，返回结果+1。
     *
     * 验证通过：
     * Runtime 1 ms Beats 69.95%
     * Memory 43.7 MB Beats 80.14%
     *
     * @param board
     * @return
     */
    public static int countBattleships_1(char[][] board) {
        int res = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') continue;
                //判断同列的上一个元素
                if (j > 0 && board[i][j - 1] == 'X') continue;
                //判断通话的上一个元素
                if (i > 0 && board[i - 1][j] == 'X') continue;
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'X', '.', '.', 'X'}, {'.', '.', '.', 'X'}, {'.', '.', '.', 'X'}}, 2);
        do_func(new char[][]{{'.'}}, 0);
    }

    private static void do_func(char[][] board, int expected) {
        int ret = countBattleships(board);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
