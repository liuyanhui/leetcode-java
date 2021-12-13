package leetcode;

/**
 * 36. Valid Sudoku
 * Medium
 * ----------------------
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 * Note:
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 *
 * Example 1:
 * Input: board =
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: true
 *
 * Example 2:
 * Input: board =
 * [["8","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: false
 * Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
 *
 * Constraints:
 * board.length == 9
 * board[i].length == 9
 * board[i][j] is a digit 1-9 or '.'.
 */
public class Valid_Sudoku_36 {
    public static boolean isValidSudoku(char[][] board) {
        return isValidSudoku_2(board);
    }

    /**
     * isValidSudoku_1的代码优化版
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Valid Sudoku.
     * Memory Usage: 39.4 MB, less than 57.04% of Java online submissions for Valid Sudoku.
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku_2(char[][] board) {
        int[][] r = new int[9][10], c = new int[9][10], s = new int[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int t = board[i][j] - '0';
                int subI = (i / 3) * 3 + j / 3;
                r[i][t]++;
                c[j][t]++;
                s[subI][t]++;
                if (r[i][t] > 1 || c[j][t] > 1 || s[subI][t] > 1) return false;
            }
        }
        return true;
    }

    /**
     * 1.按row,col,sub-box进行依次校验,分别记为r[9][9],c[9][9],s[9][9]
     * 2.value=board[i][j]。如果r[row][value]>0表示重复，返回false。否则 r[row][value]=1。以此类推c和s。
     * 3.sub-box的i计算规则：i=(row/3)*3+col/3
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Valid Sudoku.
     * Memory Usage: 39 MB, less than 82.95% of Java online submissions for Valid Sudoku.
     *
     * @param board
     * @return
     */
    public static boolean isValidSudoku_1(char[][] board) {
        int[][] r = new int[9][10], c = new int[9][10], s = new int[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int t = board[i][j] - '0';

                if (r[i][t] > 0) return false;
                else r[i][t] = 1;

                if (c[j][t] > 0) return false;
                else c[j][t] = 1;

                int subI = (i / 3) * 3 + j / 3;
                if (s[subI][t] > 0) return false;
                else s[subI][t] = 1;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}}, true);
        do_func(new char[][]{{'8', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}}, false);
    }

    private static void do_func(char[][] matrix, boolean expected) {
        boolean ret = isValidSudoku(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
