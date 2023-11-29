package leetcode;

import java.util.Arrays;

/**
 * 37. Sudoku Solver
 * Hard
 * -------------------
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 *
 * A sudoku solution must satisfy all of the following rules:
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 *
 * The '.' character indicates empty cells.
 *
 * Example 1:
 * Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
 * Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
 * Explanation: The input board is shown above and the only valid solution is shown below:
 *
 * Constraints:
 * board.length == 9
 * board[i].length == 9
 * board[i][j] is a digit or '.'.
 * It is guaranteed that the input board has only one solution.
 */
public class Sudoku_Solver_37 {
    public static void solveSudoku(char[][] board) {
        solveSudoku_2(board);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1.穷举思路。
     * 1.1. 分别保存行、列、3X3的填写情况，使用List保存，每个List的长度是9。
     * 1.2. 每填一个数字，分别校验行、列、3X3。
     * 1.3. 采用递归法。
     *
     * 验证通过：
     * Runtime 2 ms Beats 97.06% of users with Java
     * Memory 40.30 MB Beats 32.81% of users with Java
     *
     * @param board
     */
    public static void solveSudoku_2(char[][] board) {
        //初始化
        int[][] seenRow = new int[9][10];
        int[][] seenCol = new int[9][10];
        int[][] seenSubbox = new int[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int val = board[i][j] - '0';
                seenRow[i][val]++;
                seenCol[j][val]++;
                int r_sub = i / 3 * 3 + j / 3;
                int c_sub = i % 3 * 3 + j % 3;
                seenSubbox[r_sub][val]++;
            }
        }
        dfs(board, 0, 0, seenRow, seenCol, seenSubbox);
    }

    private static boolean dfs(char[][] board, int i, int j, int[][] seenRow, int[][] seenCol, int[][] seenSubbox) {
        if (board[i][j] != '.') {//题目给定的不需要判断
            return findNext(board, i, j, seenRow, seenCol, seenSubbox);
        }
        int r_sub = i / 3 * 3 + j / 3;
        int c_sub = i % 3 * 3 + j % 3;
        //穷举
        for (int t = 1; t <= 9; t++) {
            //判断是否违反约束
            if (seenRow[i][t] > 0 || seenCol[j][t] > 0 || seenSubbox[r_sub][t] > 0) {
                continue;
            }
            seenRow[i][t]++;
            seenCol[j][t]++;
            seenSubbox[r_sub][t]++;
            board[i][j] = (char) (t + '0');
            if (findNext(board, i, j, seenRow, seenCol, seenSubbox)) {
                return true;
            }
            board[i][j] = '.';
            seenRow[i][t]--;
            seenCol[j][t]--;
            seenSubbox[r_sub][t]--;
        }

        return false;
    }

    private static boolean findNext(char[][] board, int i, int j, int[][] seenRow, int[][] seenCol, int[][] seenSubbox) {
        if (i == 8 && j == 8) return true;
        //先计算列，再计算行。从左到右，从上到下的顺序计算。
        if (j < 8) {
            if (dfs(board, i, j + 1, seenRow, seenCol, seenSubbox))
                return true;
        } else if (i < 8) {
            if (dfs(board, i + 1, 0, seenRow, seenCol, seenSubbox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归法+穷举
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 76.40% of Java online submissions for Sudoku Solver.
     * Memory Usage: 38.2 MB, less than 41.05% of Java online submissions for Sudoku Solver.
     *
     * @param board
     */
    public static void solveSudoku_1(char[][] board) {
        do_recursive(board, 0, 0);
    }

    private static boolean do_recursive(char[][] board, int row, int col) {
        if (board[row][col] == '.') {
            for (int k = 1; k < 10; k++) {
                //这里最关键，把校验某个位置是否合格的算法独立出来，可以简化逻辑。也是模块化开发的思路。
                if (valid(board, row, col, k)) {
                    board[row][col] = String.valueOf(k).charAt(0);
                    //这里不能写成。因为当fillNext()返回false是会提前终止递归。
                    // return fillNext(board, row, col);
                    if (fillNext(board, row, col)) {
                        return true;
                    }
                    //当前数字不满足约束，所以重置
                    board[row][col] = '.';
                }
            }
        } else {
            //题目给定的数字不需要处理，直接下一个就行
            return fillNext(board, row, col);
        }
        return false;
    }

    private static boolean fillNext(char[][] board, int row, int col) {
        if (col < 8) {
            return do_recursive(board, row, col + 1);
        } else if (row < 8) {
            return do_recursive(board, row + 1, 0);
        } else {
            return true;
        }
    }

    private static boolean valid(char[][] board, int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] - '0' == value || board[i][col] - '0' == value) {
                return false;
            }
        }
        //这里需要注意下
        int bi = row / 3 * 3, bj = col / 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[bi + i][bj + j] - '0' == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}}, new char[][]{{'5', '3', '4', '6', '7', '8', '9', '1', '2'}, {'6', '7', '2', '1', '9', '5', '3', '4', '8'}, {'1', '9', '8', '3', '4', '2', '5', '6', '7'}, {'8', '5', '9', '7', '6', '1', '4', '2', '3'}, {'4', '2', '6', '8', '5', '3', '7', '9', '1'}, {'7', '1', '3', '9', '2', '4', '8', '5', '6'}, {'9', '6', '1', '5', '3', '7', '2', '8', '4'}, {'2', '8', '7', '4', '1', '9', '6', '3', '5'}, {'3', '4', '5', '2', '8', '6', '1', '7', '9'}});
    }

    private static void do_func(char[][] board, char[][] expected) {
        solveSudoku(board);
        Arrays.stream(board).forEach(t -> System.out.println(Arrays.toString(t)));
        boolean same = true;
        for (int i = 0; i < board.length; i++) {
            if (!Arrays.equals(board[i], expected[i])) {
                same = false;
                break;
            }
        }
        System.out.println(same);
        System.out.println("--------------");
    }
}
