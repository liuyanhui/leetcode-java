package leetcode;

/**
 * 240. Search a 2D Matrix II
 * Medium
 * --------------------------------
 * Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. This matrix has the following properties:
 * - Integers in each row are sorted in ascending from left to right.
 * - Integers in each column are sorted in ascending from top to bottom.
 * <p>
 * Example 1:
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
 * Output: true
 * <p>
 * Example 2:
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
 * Output: false
 * <p>
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= n, m <= 300
 * -10^9 <= matrix[i][j] <= 10^9
 * All the integers in each row are sorted in ascending order.
 * All the integers in each column are sorted in ascending order.
 * -10^9 <= target <= 10^9
 */
public class Search_a_2D_Matrix_II_240 {
    /**
     * @param matrix
     * @param target
     * @return
     * @see Search_a_2D_Matrix_74
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrix_r3_1(matrix, target);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 暴力查找。不使用 row 和 column 已经排序的特征。
     * Time Complexity: O(M*N)
     * 2. binary search
     * 依次对每行（从小到大）或每列（从小到大），采用 binary search 。
     * Time Complexity: O(M*logN)
     * 3. ruduce the matrix with binary search
     * 交替对行和列使用 binary search ，缩小 Matirx 的有效查找区域。
     * 充分利用行和列有序的特点。
     * 3.1. 依次从四个边去掉不满足条件的部分。从外到内，裁剪一定不满足的行或列。
     * 在【上边】中查找-去掉大于target的列
     * 在【右边】中查找-去掉小于target的列
     * 在【下边】中查找-去掉小于target的列
     * 在【左边】中查找-去掉大于target的列
     * 3.2. 重复【3.1】知道找到target或找不到
     * Time Complexity: O(logM+logN)
     * 4. 【3.】不用binary search时，可以在O(M+N)完成计算。
     * <p>
     * 采用【4.】
     * <p>
     * 验证通过：
     * Runtime 5 ms Beats 99.88%
     * Memory 45.84 MB Beats 65.88%
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix_r3_1(int[][] matrix, int target) {
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        //顺时针裁剪
        while (r1 <= r2 && c1 <= c2) {
            while (r1 <= r2 && c1 <= c2 && matrix[r1][c2] > target) {
                c2--;
            }
            while (r1 <= r2 && c1 <= c2 && matrix[r1][c2] < target) {
                r1++;
            }
            while (r1 <= r2 && c1 <= c2 && matrix[r2][c1] < target) {
                c1++;
            }
            while (r1 <= r2 && c1 <= c2 && matrix[r2][c1] > target) {
                r2--;
            }
            if (r1 > r2 || c1 > c2) break;
            if (matrix[r1][c1] == target || matrix[r1][c2] == target || matrix[r2][c1] == target || matrix[r2][c2] == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/search-a-2d-matrix-ii/discuss/66140/My-concise-O(m%2Bn)-Java-solution
     * <p>
     * 马后炮思考：
     * 1.二维数组是部分有序的（偏序而不是全序？）。需要通过某种方式可以是的查找结果的过程是有序的。
     * 2.右上角的元素是有序的。左边的都小于它，下边的都大于它。
     * 3.如果target < m[row][col]，那么col-1；如果target > m[row][col]，那么row+1；
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix_2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int row = 0, col = matrix[0].length - 1;
        while (col >= 0 && row <= matrix[0].length - 1) {
            if (target < matrix[row][col]) {
                col--;
            } else if (target > matrix[row][col]) {
                row++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 思考：
     * 1.分别按行或按列采用二分查找法。时间复杂度：O(M*logN)
     * 2.在1.的基础上先判断target是否在行或列范围内
     * <p>
     * 验证通过：
     * Runtime: 15 ms, faster than 23.20% of Java online submissions for Search a 2D Matrix II.
     * Memory Usage: 58 MB, less than 36.59% of Java online submissions for Search a 2D Matrix II.
     *
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix_1(int[][] matrix, int target) {
        for (int i = 0; i < matrix.length; i++) {
            int l = 0, r = matrix[i].length - 1;
            int mid = 0;
            //先比较首尾
            if (matrix[i][l] <= target && target <= matrix[i][r]) {
                //binary search
                while (l <= r) {
                    mid = (l + r) / 2;
                    if (matrix[i][mid] < target) {
                        l = mid + 1;
                    } else if (matrix[i][mid] > target) {
                        r = mid - 1;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 5, true);
        do_func(new int[][]{{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}}, 20, false);
        do_func(new int[][]{{-5}}, -2, false);
        do_func(new int[][]{{-5}}, -20, false);
    }

    private static void do_func(int[][] matrix, int target, boolean expected) {
        boolean ret = searchMatrix(matrix, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
