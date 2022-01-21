package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 73. Set Matrix Zeroes
 * Medium
 * -------------------------
 * Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's, and return the matrix.
 *
 * You must do it in place.
 *
 * Example 1:
 * Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * Output: [[1,0,1],[0,0,0],[1,0,1]]
 *
 * Example 2:
 * Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
 * Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -2^31 <= matrix[i][j] <= 2^31 - 1
 *
 * Follow up:
 * A straightforward solution using O(mn) space is probably a bad idea.
 * A simple improvement uses O(m + n) space, but still not the best solution.
 * Could you devise a constant space solution?
 */
public class Set_Matrix_Zeroes_73 {
    public static void setZeroes(int[][] matrix) {
        setZeroes_2(matrix);
    }

    /**
     * setZeroes_1的空间复杂度优化版
     * 用第0行和第0列保存需要设置为0的列和行。需要单独处理[0][0]的情况。
     *
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 21.80% of Java online submissions for Set Matrix Zeroes.
     * Memory Usage: 50.4 MB, less than 44.05% of Java online submissions for Set Matrix Zeroes.
     *
     * @param matrix
     */
    public static void setZeroes_2(int[][] matrix) {
        int zeroRow = 0;//第0行是否需要全部修改为0
        int zeroCol = 0;//第0列是否需要全部修改为0
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                zeroCol = 1;
                break;
            }
        }
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                zeroRow = 1;
                break;
            }
        }
        //把值为0的元素对应的第0行和第0列的元素修改为0
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        //根据第0列元素，如果[i][0]==0，则把第i行全部设置为0，除了第0个元素
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[0].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        //根据第0行元素，如果[0][j]==0，则把第j列全部设置为0，除了第0个元素
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        //处理第0行的个元素
        if (zeroRow == 1) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        //处理第0列的元素
        if (zeroCol == 1) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/set-matrix-zeroes/solution/ 之approach 1
     *
     * 1.用rowSet和colSet保存将要设置为0的行和列
     * 2.通过分别遍历rowSet和colSet，把对应的元素修改为0
     *
     * Space Complexity:O(M+N)
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 21.80% of Java online submissions for Set Matrix Zeroes.
     * Memory Usage: 52 MB, less than 9.23% of Java online submissions for Set Matrix Zeroes.
     *
     * @param matrix
     */
    public static void setZeroes_1(int[][] matrix) {
        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowSet.add(i);
                    colSet.add(j);
                }
            }
        }
        for (int i : rowSet) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int j : colSet) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}, new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}});
        do_func(new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}}, new int[][]{{0, 0, 0, 0}, {0, 4, 5, 0}, {0, 3, 1, 0}});
    }

    private static void do_func(int[][] matrix, int[][] expected) {
        setZeroes(matrix);
        ArrayUtils.printIntArray(matrix);
        System.out.println(ArrayUtils.isSame(matrix, expected));
        System.out.println("--------------");
    }

}
