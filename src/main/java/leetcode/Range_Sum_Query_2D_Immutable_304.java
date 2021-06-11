package leetcode;

/**
 * 304. Range Sum Query 2D - Immutable
 * Medium
 * ---------------------------
 * Given a 2D matrix matrix, handle multiple queries of the following type:
 * Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 *
 * Implement the NumMatrix class:
 * NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
 * int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 *
 * Example 1:
 * Input
 * ["NumMatrix", "sumRegion", "sumRegion", "sumRegion"]
 * [[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [1, 1, 2, 2], [1, 2, 2, 4]]
 * Output
 * [null, 8, 11, 12]
 *
 * Explanation
 * NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
 * numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e sum of the red rectangle)
 * numMatrix.sumRegion(1, 1, 2, 2); // return 11 (i.e sum of the green rectangle)
 * numMatrix.sumRegion(1, 2, 2, 4); // return 12 (i.e sum of the blue rectangle)
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * -10^5 <= matrix[i][j] <= 10^5
 * 0 <= row1 <= row2 < m
 * 0 <= col1 <= col2 < n
 * At most 10^4 calls will be made to sumRegion.
 */
public class Range_Sum_Query_2D_Immutable_304 {

    /**
     * 有点套路
     * 参考思路
     * https://leetcode.com/problems/range-sum-query-2d-immutable/solution/ 之 Approach 4
     *
     * Time Complexity:O(mn)
     * Space Complexity:O(1)
     *
     * 验证通过：
     * Runtime: 80 ms, faster than 19.30% of Java online submissions for Range Sum Query 2D - Immutable.
     * Memory Usage: 97.5 MB, less than 5.12% of Java online submissions for Range Sum Query 2D - Immutable.
     */
    class NumMatrix_2 {

        int[][] sumMatrix = null;

        public NumMatrix_2(int[][] matrix) {
            sumMatrix = new int[matrix.length + 1][matrix[0].length + 1];
            for (int i = 1; i <= matrix.length; i++) {
                for (int j = 1; j <= matrix[0].length; j++) {
                    sumMatrix[i][j] = sumMatrix[i][j - 1] + sumMatrix[i - 1][j] - sumMatrix[i - 1][j - 1] + matrix[i - 1][j - 1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sumMatrix[row2 + 1][col2 + 1] - sumMatrix[row1][col2+1] - sumMatrix[row2+1][col1] + sumMatrix[row1][col1];
        }
    }

    /**
     * 与Range_Sum_Query_Immutable_303中的NumArray_2方法的思路是一致的
     *
     * Time Complexity:O(mn)
     * Space Complexity:O(m)
     *
     * 验证通过：
     * Runtime: 72 ms, faster than 20.07% of Java online submissions for Range Sum Query 2D - Immutable.
     * Memory Usage: 64.5 MB, less than 5.77% of Java online submissions for Range Sum Query 2D - Immutable.
     */
    class NumMatrix {

        int[][] sumMatrix = null;

        public NumMatrix(int[][] matrix) {
            sumMatrix = new int[matrix.length][matrix[0].length + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 1; j <= matrix[0].length; j++) {
                    sumMatrix[i][j] = sumMatrix[i][j - 1] + matrix[i][j - 1];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            int ret = 0;
            for (int i = row1; i <= row2; i++) {
                ret += sumMatrix[i][col2 + 1] + sumMatrix[i][col1];
            }
            return ret;
        }
    }

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
}
