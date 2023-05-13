package leetcode;

/**
 * 363. Max Sum of Rectangle No Larger Than K
 * Hard
 * ---------------------------------------------
 * Given an m x n matrix matrix and an integer k, return the max sum of a rectangle in the matrix such that its sum is no larger than k.
 *
 * It is guaranteed that there will be a rectangle with a sum no larger than k.
 *
 * Example 1:
 * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
 * Output: 2
 * Explanation: Because the sum of the blue rectangle [[0, 1], [-2, 3]] is 2, and 2 is the max number no larger than k (k = 2).
 *
 * Example 2:
 * Input: matrix = [[2,2,-1]], k = 3
 * Output: 3
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 100
 * -100 <= matrix[i][j] <= 100
 * -10^5 <= k <= 10^5
 *
 * Follow up: What if the number of rows is much larger than the number of columns?
 */
public class Max_Sum_of_Rectangle_No_Larger_Than_K_363 {
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        return maxSumSubmatrix_2(matrix, k);
    }

    /**
     * 与maxSumSubmatrix_1()思路略有不同
     * 1.划分左右边界，并求出在此边界下，每行的总和
     * 2.暴力求解不大于k的最大值
     *
     * 验证通过：
     * Runtime 237 ms Beats 84.69%
     * Memory 43.4 MB Beats 7.14%
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int maxSumSubmatrix_2(int[][] matrix, int k) {
        int res = Integer.MIN_VALUE;
        for (int col0 = 0; col0 < matrix[0].length; col0++) {
            int[] rows = new int[matrix.length];
            for (int col1 = col0; col1 < matrix[0].length; col1++) {
                //这里是相对maxSumSubmatrix_1()优化的地方，减少了重复计算(BUD中的D)
                for (int i = 0; i < matrix.length; i++) {
                    rows[i] += matrix[i][col1];
                }

                for (int i = 0; i < rows.length; i++) {
                    int sum = 0;
                    for (int j = i; j < rows.length; j++) {
                        sum += rows[j];
                        if (sum <= k && sum > res) {
                            res = sum;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * naive solution
     * 1.穷举法计算每个可能的rectangle，并计算sum
     * 2.如果sum<=k 且 sum>res，那么res=sum
     * 3.根据公式计算sum，公式为:sum[i,j]=sum[i-1,j]+sum[i,j-1]-sum[i-1,j-1]+matrix[i,j]
     *
     * Time Complexity:O(M*M*N*N)
     * Space Complexity:O(M*N)
     *
     * 验证通过：
     * Runtime 578 ms Beats 5.61%
     * Memory 43.3 MB Beats 10.20%
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int maxSumSubmatrix_1(int[][] matrix, int k) {
        int res = Integer.MIN_VALUE;
        for (int i0 = 0; i0 < matrix.length; i0++) {
            for (int j0 = 0; j0 < matrix[i0].length; j0++) {
                //长度+1，避免[0,0]元素越界
                int[][] sum = new int[matrix.length + 1][matrix[i0].length + 1];
                for (int i1 = i0; i1 < matrix.length; i1++) {
                    for (int j1 = j0; j1 < matrix[i1].length; j1++) {
                        sum[i1 + 1][j1 + 1] = sum[i1][j1 + 1] + sum[i1 + 1][j1] - sum[i1][j1] + matrix[i1][j1];
                        if (sum[i1 + 1][j1 + 1] <= k && sum[i1 + 1][j1 + 1] > res) {
                            res = sum[i1 + 1][j1 + 1];
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 0, 1}, {0, -2, 3}}, 2, 2);
        do_func(new int[][]{{2, 2, -1}}, 3, 3);
        do_func(new int[][]{{2, 2, -1}}, 0, -1);
    }

    private static void do_func(int[][] matrix, int k, int expected) {
        int ret = maxSumSubmatrix(matrix, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
