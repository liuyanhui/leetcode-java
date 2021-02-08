package leetcode;

/**
 * https://leetcode.com/problems/word-search/
 * 79. Word Search
 * Medium
 * ---------------------------
 * Given an m x n board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells, where "adjacent" cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 * Example 1:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 *
 * Example 2:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 *
 * Example 3:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 *
 * Constraints:
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 200
 * 1 <= word.length <= 103
 * board and word consists only of lowercase and uppercase English letters.
 */
public class Word_Search_79 {
    /**
     * 递归思路
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 99.02% of Java online submissions for Word Search.
     * Memory Usage: 41 MB, less than 64.73% of Java online submissions for Word Search.
     * @param board
     * @param word
     * @return
     */
    public static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (do_recursive(board, i, j, word, 0)) return true;
            }
        }
        return false;
    }

    private static boolean do_recursive(char[][] board, int r, int c, String word, int w) {
        if (w == word.length()) {
            return true;
        }
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
            return false;
        }

        if (board[r][c] == word.charAt(w)) {
            char tmp = board[r][c];
            board[r][c] = '0';
            // 顺时针查找 up right down left
            if (do_recursive(board, r - 1, c, word, w + 1)
                    || do_recursive(board, r, c + 1, word, w + 1)
                    || do_recursive(board, r + 1, c, word, w + 1)
                    || do_recursive(board, r, c - 1, word, w + 1)) {
                return true;
            }
            board[r][c] = tmp;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED", true);
        do_func(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "SEE", true);
        do_func(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCB", false);
    }

    private static void do_func(char[][] board, String word, boolean expected) {
        boolean ret = exist(board, word);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
