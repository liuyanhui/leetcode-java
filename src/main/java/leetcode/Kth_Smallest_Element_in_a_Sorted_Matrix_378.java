package leetcode;

import java.util.PriorityQueue;

/**
 * 378. Kth Smallest Element in a Sorted Matrix
 * Medium
 * ---------------------------
 * Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * You must find a solution with a memory complexity better than O(n2).
 *
 * Example 1:
 * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
 * Output: 13
 * Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
 *
 * Example 2:
 * Input: matrix = [[-5]], k = 1
 * Output: -5
 *
 * Constraints:
 * n == matrix.length
 * n == matrix[i].length
 * 1 <= n <= 300
 * -10^9 <= matrix[i][j] <= 10^9
 * All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
 * 1 <= k <= n^2
 *
 * Follow up:
 * Could you solve the problem with a constant memory (i.e., O(1) memory complexity)?
 * Could you solve the problem in O(n) time complexity? The solution may be too advanced for an interview but you may find reading this paper fun.
 *
 */
public class Kth_Smallest_Element_in_a_Sorted_Matrix_378 {
    public static int kthSmallest(int[][] matrix, int k) {
        return kthSmallest_4(matrix, k);
    }

    /**
     * round 2
     * review  有两种思路：
     * 1.本题的思路。本质上还是排序的思路，只不过是只排序了kth最小值。
     * 2.kthSmallest_3()的思路。这个思路更优，是二分查找思路。属于#"在未排序的集合中查找第k小的数"系列，参见kthSmallest_3()的注释
     *
     * 类似查找kth的最大或最小值，有时候无需排序，只需要查找即可。这类查找问题有两种情况（如上所述）。
     *
     * 参考了373. Find K Pairs with Smallest Sums的方案
     *
     * Thinking:
     * 1.属于行和列分别递增有序二维数组的排序问题。只是本题不要求排序，只查找kth最小值。
     * 2.堆排序思路。
     * 2.1.维护一个小顶堆heap。初始化时，每行的第一个元素加入heap。
     * 2.2.heap的root删除并加入排序结果集，然后把root对应行的下一个元素加入heap。
     * 2.3.重复步骤"2.2"
     * 2.4.时间复杂度：O((M+N)logN)
     * 2.5.需要单独记录heap中每个元素的位置。
     * 2.5.这个思路只要行或列其中一个排序就可以使用，没有利用行和列都已经排序的条件。
     *
     * 验证通过：
     * Runtime 30 ms Beats 9.89%
     * Memory 48.4 MB Beats 30.58%
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest_4(int[][] matrix, int k) {
        PriorityQueue<Integer[]> heap = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int i = 0; i < k && i < matrix.length; i++) {
            Integer[] t = new Integer[3];
            t[0] = matrix[i][0];
            t[1] = i;
            t[2] = 0;
            heap.offer(t);
        }

        while (k-- > 1) {
            Integer[] s = heap.poll();
            if (s[2] + 1 >= matrix[0].length) continue;
            Integer[] n = new Integer[3];
            n[1] = s[1];
            n[2] = s[2] + 1;
            n[0] = matrix[n[1]][n[2]];
            heap.offer(n);
        }
        return heap.poll()[0];
    }

    /**
     * 金矿：
     * 与Find_Minimum_in_Rotated_Sorted_Array_153和Find_the_Duplicate_Number_287可以组成一个系列
     * 该问题属于"在未排序的集合中查找第k小的数"
     *
     * 参考思路：
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 67.55% of Java
     * Memory Usage: 56.2 MB, less than 5.54% of Java
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest_3(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return Integer.MIN_VALUE;
        int low = matrix[0][0], high = matrix[matrix.length - 1][matrix[0].length - 1];
        while (low < high) {
            int count = 0;
            int mid = low + (high - low) / 2;
            // 关键：每次都要遍历整个matrix
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {//FIXME: round 2: 这里可以优化。由于行和列是分别有序的，所以列的比较范围是非递增的。
                    if (matrix[i][j] <= mid) {
                        count++;
                    } else {
                        continue;
                    }
                }
            }
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    /**
     * 每次从各行中获取最小值，然后提取该值。
     * 用数组记录各行的提取情况，数组的下标是行号，数组的值是该行当前被提取的列号。
     *
     * Time Complexity: O(n*n*n)
     * Space Complexity: O(n*n)
     *
     * 验证通过：
     * Runtime: 48 ms, faster than 5.03% of Java
     * Memory Usage: 56.2 MB, less than 6.09% of Java
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest_2(int[][] matrix, int k) {
        int[] idxArr = new int[matrix.length];//记录每行提取状态，下标为行号，值为该行的未被提取的索引。
        int ret = 0;

        while (k > 0) {
            int minIdx = 0, minValue = Integer.MAX_VALUE;
            for (int j = 0; j < idxArr.length; j++) {
                if (idxArr[j] >= matrix.length) {//表示该行已被全部提取
                    continue;
                }
                if (minValue > matrix[j][idxArr[j]]) {//比较各行可以被提取的第一个数字，并获取这些数的最小值
                    minValue = matrix[j][idxArr[j]];
                    minIdx = j;
                }
            }
            //记录当前被提取的数字
            ret = minValue;
            idxArr[minIdx]++;
            k--;
        }

        return ret;
    }

    /**
     * 把matrix排序后输出第k小的数字
     *
     * Time Complexity: O(NlogN) N=n*n
     * Space Complexity: O(N*N)  N=n*n
     *
     * 验证通过：
     * Runtime: 36 ms, faster than 10.03% of Java
     * Memory Usage: 54.3 MB, less than 25.36% of Java
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int kthSmallest_1(int[][] matrix, int k) {
        PriorityQueue<Integer> sortedQueue = new PriorityQueue<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sortedQueue.offer(matrix[i][j]);
            }
        }
        int ret = 0;
        while (k > 0) {
            ret = sortedQueue.poll();
            k--;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 5, 9}, {10, 11, 13}, {12, 13, 15}}, 8, 13);
        do_func(new int[][]{{1, 5, 9}, {10, 11, 13}, {12, 13, 15}}, 7, 13);
        do_func(new int[][]{{-5}}, 1, -5);
        do_func(new int[][]{{1, 2}, {1, 3}}, 3, 2);

    }

    private static void do_func(int[][] matrix, int k, int expected) {
        int ret = kthSmallest(matrix, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
