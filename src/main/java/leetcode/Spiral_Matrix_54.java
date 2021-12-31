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
        return spiralOrder_4(matrix);
    }

    /**
     * round2
     * 递归法，每次操作一圈，然后递归操作下一圈
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix.
     * Memory Usage: 37.1 MB, less than 75.95% of Java online submissions for Spiral Matrix.
     *
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder_4(int[][] matrix) {
        List<Integer> ret = new ArrayList<>();
        do_recursive(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1, ret);
        return ret;
    }

    private static void do_recursive(int[][] matrix, int r0, int r1, int c0, int c1, List<Integer> ret) {
        if (r0 > r1 || c0 > c1) {
            return;
        }
        //topside，行操作是闭区间
        for (int i = c0; i <= c1; i++) {
            ret.add(matrix[r0][i]);
        }
        //rightside，列操作是开区间
        for (int i = r0 + 1; i < r1; i++) {
            ret.add(matrix[i][c1]);
        }
        //如果只有一行，防止重复操作
        if (r0 < r1) {
            //bottomside，行操作是闭区间
            for (int i = c1; i >= c0; i--) {
                ret.add(matrix[r1][i]);
            }
        }
        //如果只有一列防止重复操作
        if (c0 < c1) {
            //leftside，列操作是开区间
            for (int i = r1 - 1; i > r0; i--) {
                ret.add(matrix[i][c0]);
            }
        }
        do_recursive(matrix, r0 + 1, r1 - 1, c0 + 1, c1 - 1, ret);
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
            if (r0 <= r1) {
                //向左
                for (int j = c1; j >= c0; j--) {
                    ret.add(matrix[r1][j]);
                }
            }
            r1--;
            if (c0 <= c1) {
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
        do_func(new int[][]{{1, 2}, {3, 4}}, Arrays.asList(new Integer[]{1, 2, 4, 3}));
        do_func(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, Arrays.asList(new Integer[]{1, 2, 3, 6, 9, 8, 7, 4, 5}));
        do_func(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}, Arrays.asList(new Integer[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7}));
        do_func(new int[][]{{1}}, Arrays.asList(new Integer[]{1}));
        do_func(new int[][]{{}}, Arrays.asList(new Integer[]{}));
        do_func(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}, Arrays.asList(new Integer[]{1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10}));
        do_func(new int[][]{{2, 5, 8}, {4, 0, -1}}, Arrays.asList(new Integer[]{2, 5, 8, -1, 0, 4}));
        do_func(new int[][]{{2, 5, 8}}, Arrays.asList(new Integer[]{2, 5, 8}));
    }

    private static void do_func(int[][] input, List<Integer> expected) {
        List<Integer> ret = spiralOrder(input);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("-----------");
    }
}
