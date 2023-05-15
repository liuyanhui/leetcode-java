package leetcode;

import java.util.TreeSet;

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
        return maxSumSubmatrix_3(matrix, k);
    }

    /**
     * review 有种更巧妙的方法，在maxSumSubmatrix_2()基础上进行了优化
     *
     * 参考资料：
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/solutions/83599/accepted-c-codes-with-explanation-and-references/
     * https://www.quora.com/Given-an-array-of-integers-A-and-an-integer-k-find-a-subarray-that-contains-the-largest-sum-subject-to-a-constraint-that-the-sum-is-less-than-k
     * https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/solution/ju-xing-qu-yu-bu-chao-guo-k-de-zui-da-sh-70q2/
     *
     * 思路：
     * 1.问题分为两个部分：计算rectangle的面积；判断面积是否为不大于k的最大值。
     * 2.计算rectangle的面积通过枚举矩形的上下边界，并计算出该边界内每列的元素和的方式解决。这部分的复杂度是O(M*N*M)，参考maxSumSubmatrix_2()
     * 3.在2.的基础上问题转化成：给定一个整数数组和一个整数k，计算该数组的最大区间和，要求区间和不超过k。 参考下面的分析：
     *  定义长度为 n 的数组 a 的前缀和:
     *  PrefixSum[0]=0
     *  PrefixSum[1]=nums[1]
     *  PrefixSum[2]=nums[2]+PrefixSum[1]
     *  ...
     *  PrefixSum[i]=nums[i]+PrefixSum[i-1]
     *
     *  区间[l,r)的和表示为PrefixSum[r]-PrefixSum[l]。
     *  问题的条件转化成PrefixSum[r]-PrefixSum[l]<=k，即查找PrefixSum[r]-PrefixSum[l]的最大值，且该值小于等于k。
     *  这个不等式中有两个变量r和l。我们通过枚举r将其转化（降维）为一个变量的问题。
     *  在r确定的情况下（枚举r时，PrefixSum[r]是确定的值），不等式转化为PrefixSum[r]-k<=PrefixSum[l]，这时我们只需要找到满足条件的l。思路如下：由于l<r，所以枚举r时,PrefixSum[l]已经计算过了；我们把PrefixSum[l]保存在有序集合S中；在S中查找大于等于PrefixSum[r]-k的最小值。
     *
     * 验证通过：
     * Runtime 422 ms Beats 16.41%
     * Memory 42.5 MB Beats 82.56%
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int maxSumSubmatrix_3(int[][] matrix, int k) {
        if (matrix.length == 0) return 0;
        int res = Integer.MIN_VALUE;
        for (int col0 = 0; col0 < matrix[0].length; col0++) {
            int[] rows = new int[matrix.length];
            for (int col1 = col0; col1 < matrix[0].length; col1++) {
                //两侧列确定的情况下计算没行的sum，利用了前缀和思路
                for (int i = 0; i < matrix.length; i++) {
                    rows[i] += matrix[i][col1];
                }
                TreeSet<Integer> treeSet = new TreeSet<>();
                treeSet.add(0);
                int r = 0;
                for (int n : rows) {
                    r += n;
                    //FIXME 这里很巧妙，查询满足条件r-k<=l时，l的最小值
                    //TreeSet.ceiling(E e) 方法用于返回此集合中大于或等于给定元素的最小元素，如果没有这样的元素，则返回 null。
                    Integer ceiling = treeSet.ceiling(r - k);
                    if (ceiling != null) {
                        res = Math.max(res, r - ceiling);
                    }
                    treeSet.add(r);
                }
            }
        }
        return res;
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/solutions/83599/accepted-c-codes-with-explanation-and-references/
     * https://leetcode.cn/problems/max-sum-of-rectangle-no-larger-than-k/solution/javacong-bao-li-kai-shi-you-hua-pei-tu-pei-zhu-shi/
     *
     * 与maxSumSubmatrix_1()思路略有不同
     * 1.划分左右边界，并求出在此边界下，每行的总和
     * 2.暴力求解不大于k的最大值
     *
     *
     * Time Complexity:O(M*M*N*N)
     * Space Complexity:O(M*N)
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
                //FIXME maxSumSubmatrix_3()对下面的代码进行了优化
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
