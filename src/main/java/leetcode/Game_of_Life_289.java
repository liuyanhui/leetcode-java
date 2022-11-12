package leetcode;

import java.util.Arrays;

/**
 * 289. Game of Life
 * Medium
 * -----------------------------
 * According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 *
 * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
 *
 * Any live cell with fewer than two live neighbors dies as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population.
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 *
 * Example 1:
 * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 *
 * Example 2:
 * Input: board = [[1,1],[1,0]]
 * Output: [[1,1],[1,1]]
 *
 * Constraints:
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 25
 * board[i][j] is 0 or 1.
 *
 * Follow up:
 * Could you solve it in-place? Remember that the board needs to be updated simultaneously: You cannot update some cells first and then use their updated values to update other cells.
 * In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border). How would you address these problems?
 */
public class Game_of_Life_289 {
    /**
     * 思路：
     * 1.思路1：为了防止计算过程污染原始数据，复制输入参数到一个新的二维数组中，然后以新数组为源头计算结果。
     * 2.当增加"in-place"的约束条件时，某个cell值发生变化时，先把它的值修改成特定中间的值。计算完成后再修改值。
     * 共有四种情况：0变0，0变1，1变1，1变0，
     * 中间值为：0变1时，中间为-2；1变0时中间值为2
     *
     * 验证通过：
     * Runtime 2 ms Beats 16.58%
     * Memory 42.8 MB Beats 7.37%
     * @param board
     */
    public static void gameOfLife(int[][] board) {
        gameOfLife_1(board);
    }

    public static void gameOfLife_1(int[][] board) {
        //计算中间值
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                update(i, j, board);
            }
        }
        //修改中间值
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] > 1) board[i][j] = 0;
                else if (board[i][j] < 0) board[i][j] = 1;
            }
        }
    }

    private static void update(int i, int j, int[][] board) {
        int p = 0;
        //顺时针方向的变化值
        int[][] delta = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int m = 0; m < delta.length; m++) {
            int r = i + delta[m][0], c = j + delta[m][1];
            if (0 <= r && r < board.length
                    && 0 <= c && c < board[0].length
                    && (board[r][c] > 0)) {
                p++;
            }
        }
        if (board[i][j] > 0) {
            if (p < 2 || p > 3)
                board[i][j] = 2;
        } else {
            if (p == 3)
                board[i][j] = -2;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        int[][] expected = new int[][]{{0, 0, 0}, {1, 0, 1}, {0, 1, 1}, {0, 1, 0}};
        prepareThenRun(matrix, expected);

        matrix = new int[][]{{1, 1}, {1, 0}};
        expected = new int[][]{{1, 1}, {1, 1}};
        prepareThenRun(matrix, expected);
    }

    private static void prepareThenRun(int[][] matrix, int[][] expected) {
        gameOfLife(matrix);
        for (int[] m : matrix) {
            System.out.println(Arrays.toString(m));
        }
        boolean ret = doseTheTwoMatrixSame(expected, matrix);
        System.out.println(ret);
        System.out.println("--------------");
    }


    private static boolean doseTheTwoMatrixSame(int[][] m1, int[][] m2) {
        if (m1.length != m2.length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
