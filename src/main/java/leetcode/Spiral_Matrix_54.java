package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/spiral-matrix/
 * 54. Spiral Matrix
 * Medium
 * --------------
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 *
 * Example 2:
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 */
public class Spiral_Matrix_54 {
    public static List<Integer> spiralOrder(int[][] matrix) {
        return spiralOrder_3(matrix);
    }

    /**
     * 分层法layer by layer
     * 参考思路：https://leetcode.com/problems/spiral-matrix/solution/ 中的Approach2
     * 1.每一层都是顺时针(右，下，左，上)的方式遍历，然后进行下一层遍历。
     * 2.用左上和右下两个坐标作为每层的边界，每层遍历结束后重新计算。
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
     * Memory Usage: 37.1 MB, less than 60.11% of Java online submissions for Spiral Matrix.
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder_3(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        List<Integer> ret = new ArrayList<>();
        int r0 = 0, c0 = 0, r1 = matrix.length - 1, c1 = matrix[0].length - 1;
        while (r0 <= r1 && c0 <= c1) {
            //向右
            for (int j = c0; j <= c1; j++) {
                ret.add(matrix[r0][j]);
            }
            r0++;
            //向下
            for (int j = r0; j <= r1; j++) {
                ret.add(matrix[j][c1]);
            }
            c1--;
            if (r0 < r1) {
                //向左
                for (int j = c1; j >= c0; j--) {
                    ret.add(matrix[r1][j]);
                }
            }
            r1--;
            if (c0 < c1) {
                //向上
                for (int j = r1; j >= r0; j--) {
                    ret.add(matrix[j][c0]);
                }
            }
            c0++;
        }
        return ret;
    }

    /**
     * simulation思路
     * 不采用递归法，直接用循环遍历法。采用while循环的方式，当ret的大小等于数组元素个数时停止循环。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
     * Memory Usage: 37.5 MB, less than 11.58% of Java online submissions for Spiral Matrix.
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder_2(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        List<Integer> ret = new ArrayList<>();
        int VISITED = -99999;
        int direct = 1;//1:right;2:down;3:left;4:up
        int row = 0, col = 0;
        while (ret.size() < matrix.length * matrix[0].length) {
            if (direct == 1) {
                if (col < matrix[0].length && matrix[row][col] != VISITED) {
                    ret.add(matrix[row][col]);
                    matrix[row][col] = VISITED;
                    col++;
                } else {
                    row++;
                    col--;
                    direct = 2;
                }
            } else if (direct == 2) {
                if (row < matrix.length && matrix[row][col] != VISITED) {
                    ret.add(matrix[row][col]);
                    matrix[row][col] = VISITED;
                    row++;
                } else {
                    row--;
                    col--;
                    direct = 3;
                }
            } else if (direct == 3) {
                if (col >= 0 && matrix[row][col] != VISITED) {
                    ret.add(matrix[row][col]);
                    matrix[row][col] = VISITED;
                    col--;
                } else {
                    row--;
                    col++;
                    direct = 4;
                }
            } else if (direct == 4) {
                if (row >= 0 && matrix[row][col] != VISITED) {
                    ret.add(matrix[row][col]);
                    matrix[row][col] = VISITED;
                    row--;
                } else {
                    row++;
                    col++;
                    direct = 1;
                }
            }
        }
        return ret;
    }

    /**
     * simulation思路，递归法。根据右->下->左->上->右->...一次遍历，需要判断是否已经遍历过当前元素。遍历过的元素修改为一个极小值。
     * Time Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
     * Memory Usage: 37.2 MB, less than 60.11% of Java online submissions for Spiral Matrix.
     */
    public static List<Integer> spiralOrder_1(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        List<Integer> ret = new ArrayList<>();
        do_right(matrix, 0, 0, ret);
        return ret;
    }

    private static void do_right(int[][] matrix, int row, int col, List<Integer> ret) {
        if (ret.size() == matrix.length * matrix[0].length) {
            return;
        }
        for (int i = col; i < matrix[row].length; i++) {
            if (matrix[row][i] == -9999) {
                do_down(matrix, row + 1, i - 1, ret);
                break;
            } else {
                ret.add(matrix[row][i]);
                matrix[row][i] = -9999;
                if (i == matrix[row].length - 1) {
                    do_down(matrix, row + 1, i, ret);
                }
            }
        }
    }

    private static void do_down(int[][] matrix, int row, int col, List<Integer> ret) {
        if (ret.size() == matrix.length * matrix[0].length) {
            return;
        }
        for (int i = row; i < matrix.length; i++) {
            if (matrix[i][col] == -9999) {
                do_left(matrix, i - 1, col - 1, ret);
                break;
            } else {
                ret.add(matrix[i][col]);
                matrix[i][col] = -9999;
                if (i == matrix.length - 1) {
                    do_left(matrix, i, col - 1, ret);
                }
            }
        }
    }

    private static void do_left(int[][] matrix, int row, int col, List<Integer> ret) {
        if (ret.size() == matrix.length * matrix[0].length) {
            return;
        }
        for (int i = col; i >= 0; i--) {
            if (matrix[row][i] == -9999) {
                do_up(matrix, row - 1, i + 1, ret);
                break;
            } else {
                ret.add(matrix[row][i]);
                matrix[row][i] = -9999;
                if (i == 0) {
                    do_up(matrix, row - 1, i, ret);
                }
            }
        }
    }

    private static void do_up(int[][] matrix, int row, int col, List<Integer> ret) {
        if (ret.size() == matrix.length * matrix[0].length) {
            return;
        }
        for (int i = row; i >= 0; i--) {
            if (matrix[i][col] == -9999) {
                do_right(matrix, i + 1, col + 1, ret);
                break;
            } else {
                ret.add(matrix[i][col]);
                matrix[i][col] = -9999;
                // if(i==0){
                //     do_right(matrix, i, col+1);
                // }
            }
        }
    }

    public static void main(String[] args) {

        int[][] matrix7 = new int[][]{{1, 2}, {3, 4}};
        List<Integer> expected7 = Arrays.asList(new Integer[]{1, 2, 4, 3});
        do_func(matrix7, expected7);

        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        List<Integer> expected = Arrays.asList(new Integer[]{1, 2, 3, 6, 9, 8, 7, 4, 5});
        do_func(matrix, expected);

        int[][] matrix2 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        List<Integer> expected2 = Arrays.asList(new Integer[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7});
        do_func(matrix2, expected2);

        int[][] matrix3 = new int[][]{{1}};
        List<Integer> expected3 = Arrays.asList(new Integer[]{1});
        do_func(matrix3, expected3);

        int[][] matrix4 = new int[][]{{}};
        List<Integer> expected4 = Arrays.asList(new Integer[]{});
        do_func(matrix4, expected4);

        int[][] matrix5 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        List<Integer> expected5 = Arrays.asList(new Integer[]{1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10});
        do_func(matrix5, expected5);

        int[][] matrix6 = new int[][]{{2, 5, 8}, {4, 0, -1}};
        List<Integer> expected6 = Arrays.asList(new Integer[]{2, 5, 8, -1, 0, 4});
        do_func(matrix6, expected6);
    }

    private static void do_func(int[][] input, List<Integer> expected) {
        List<Integer> ret = spiralOrder(input);
        boolean same = true;
        for (int i = 0; i < ret.size(); i++) {
            if (ret.get(i) != expected.get(i)) {
                same = false;
            }
        }
        System.out.println(same);
        System.out.println("-----------");
    }
}
