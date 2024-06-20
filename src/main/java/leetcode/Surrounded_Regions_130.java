package leetcode;

import java.util.Arrays;

/**
 * 130. Surrounded Regions
 * Medium
 * --------------
 * You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:
 *  - Connect: A cell is connected to adjacent cells horizontally or vertically.
 *  - Region: To form a region connect every 'O' cell.
 *  - Surround: The region is surrounded with 'X' cells if you can connect the region with 'X' cells and none of the region cells are on the edge of the board.
 *
 * A surrounded region is captured by replacing all 'O's with 'X's in the input matrix board.
 *
 * Example 1:
 * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * Explanation:
 * In the above diagram, the bottom region is not captured because it is on the edge of the board and cannot be surrounded.
 *
 * Example 2:
 * Input: board = [["X"]]
 * Output: [["X"]]
 *
 * Constraints:
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 200
 * board[i][j] is 'X' or 'O'.
 */
public class Surrounded_Regions_130 {
    /**
     * review R2 20220402
     */
    public static void solve(char[][] board) {
        solve_r3_1(board);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1.分两步。先标记，再修改。
     * 1.1. 依次对4个边界上的'O'单元进行DFS计算，把并把满足条件的单元标记为'A'。
     * 1.2. 遍历board把'A'改为'O'，其余单元改为'X'
     *
     * 验证通过：
     * Runtime 2 ms Beats 85.12%
     * Memory 45.85 MB Beats 11.22%
     *
     * @param board
     */
    public static void solve_r3_1(char[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            //计算第0行，并标记
            helper_r3_1(board, 0, j);
            //计算第m行，并标记
            helper_r3_1(board, board.length - 1, j);
        }

        for (int i = 0; i < board.length; i++) {
            //计算第0列，并标记
            helper_r3_1(board, i, 0);
            //计算第n列，并标记
            helper_r3_1(board, i, board[0].length - 1);
        }

        //修改
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'A') board[i][j] = 'O';
                else board[i][j] = 'X';
            }
        }
    }

    private static void helper_r3_1(char[][] board, int r, int c) {
        if (0 > r || r >= board.length) return;
        if (0 > c || c >= board[0].length) return;
        if (board[r][c] == 'O') {
            board[r][c] = 'A';
            helper_r3_1(board, r - 1, c);
            helper_r3_1(board, r, c + 1);
            helper_r3_1(board, r + 1, c);
            helper_r3_1(board, r, c - 1);
        }
    }

    /**
     * DFS思路，
     * 1.分别遍历第0行、第0列、最后行、最后列。TIP：只需要遍历四个边，不需要遍历内部元素。
     * 2.每个元素用顺时针（上、右、下、左）的方向进行递归调用，将需要保留的元素修改为'Y'
     * 3.遍历完成后，将board中所有的'O'修改为'X'，所有的'Y'修改为'0'
     *
     * 传染着色法，跟Y相邻的O要变成Y。
     * 这个思路是只计算最外面的边，从最外面的边开始递归，无需单独递归非边缘的元素。
     *
     * Time Complexity: O(m*n)
     * Space Complexity:O(m*n)
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.63% of Java online submissions for Surrounded Regions.
     * Memory Usage: 40.9 MB, less than 75.90% of Java online submissions for Surrounded Regions.
     *
     * @param board
     */
    public static void solve_1(char[][] board) {
        //第0行和最后一行
        for (int i = 0; i < board[0].length; i++) {
            do_recursive(board, 0, i);
            do_recursive(board, board.length - 1, i);
        }
        //第0列和最后一列
        for (int i = 0; i < board.length; i++) {
            do_recursive(board, i, 0);
            do_recursive(board, i, board[0].length - 1);
        }

        //所有的'O'修改为'X'，所有的'Y'修改为'0'
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'Y') {
                    board[i][j] = 'O';
                }
            }
        }

    }

    private static void do_recursive(char[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length
                || board[r][c] == 'Y') {
            return;
        }
        if (board[r][c] == 'O') {
            board[r][c] = 'Y';
            //up
            do_recursive(board, r - 1, c);
            //right
            do_recursive(board, r, c + 1);
            //down
            do_recursive(board, r + 1, c);
            //left
            do_recursive(board, r, c - 1);
        }
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}}, new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'X', 'X', 'X'}, {'X', 'X', 'X', 'X'}, {'X', 'O', 'X', 'X'}});
        do_func(new char[][]{{'X'}}, new char[][]{{'X'}});
        do_func(new char[][]{{'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}}, new char[][]{{'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O'}});
        do_func(new char[][]{{'X', 'O', 'X', 'O', 'X', 'O'}, {'O', 'X', 'O', 'X', 'O', 'X'}, {'X', 'O', 'X', 'O', 'X', 'O'}, {'O', 'X', 'O', 'X', 'O', 'X'}}, new char[][]{{'X', 'O', 'X', 'O', 'X', 'O'}, {'O', 'X', 'X', 'X', 'X', 'X'}, {'X', 'X', 'X', 'X', 'X', 'O'}, {'O', 'X', 'O', 'X', 'O', 'X'}});
        do_func(new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'O', 'X'}}, new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'O', 'X'}});
        do_func(new char[][]{{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O'}, {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X'}, {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X'}, {'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X'}, {'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O', 'O'}, {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'O'}, {'X', 'O', 'X', 'O', 'O', 'X', 'X', 'O', 'X', 'O'}, {'X', 'X', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'X'}, {'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O', 'X', 'O'}, {'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O'}}, new char[][]{{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'O', 'X', 'O'}, {'X', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X'}, {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'X', 'X'}, {'O', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X'}, {'O', 'O', 'X', 'X', 'O', 'X', 'X', 'O', 'O', 'O'}, {'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O'}, {'X', 'O', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'O'}, {'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'X'}, {'O', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X', 'O'}, {'X', 'X', 'O', 'X', 'X', 'X', 'X', 'O', 'O', 'O'}});
    }

    private static void do_func(char[][] board, char[][] expected) {
        solve(board);
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
