package leetcode;

/**
 * 74. Search a 2D Matrix
 * Medium
 * ---------------------------
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 *
 * Example 1:
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 *
 * Example 2:
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * Output: false
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -10^4 <= matrix[i][j], target <= 10^4
 */
public class Search_a_2D_Matrix_74 {
    /**
     * @see Search_a_2D_Matrix_II_240
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrix_1(matrix, target);
    }

    /**
     * 思路：
     * 先定位到行，再使用binary search从该行中查找
     *
     * 另一种更优的方案，把二维数组当成一维已排序数组。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search a 2D Matrix.
     * Memory Usage: 39.2 MB, less than 47.15% of Java online submissions for Search a 2D Matrix.
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix_1(int[][] matrix, int target) {
        int row = -1;
        while (row + 1 < matrix.length && matrix[row + 1][0] <= target) {
            row++;
        }
        if (row < 0 || row > matrix.length - 1) return false;
        int l = 0, r = matrix[0].length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 13, false);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 1, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 7, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 10, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 400, false);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 20, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 60, true);
        do_func(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, -60, false);

    }

    private static void do_func(int[][] matrix, int target, boolean expected) {
        boolean ret = searchMatrix(matrix, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
