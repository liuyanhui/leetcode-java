package leetcode;

/**
 * 327. Count of Range Sum
 * Hard
 * -----------------------------
 * Given an integer array nums and two integers lower and upper, return the number of range sums that lie in [lower, upper] inclusive.
 *
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.
 *
 * Example 1:
 * Input: nums = [-2,5,-1], lower = -2, upper = 2
 * Output: 3
 * Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2.
 *
 * Example 2:
 * Input: nums = [0], lower = 0, upper = 0
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * -10^5 <= lower <= upper <= 10^5
 * The answer is guaranteed to fit in a 32-bit integer.
 */
public class Count_of_Range_Sum_327 {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        return countRangeSum_1(nums, lower, upper);
    }

    /**
     * review
     * 变态题，有点绕。
     *
     * 参考思路：
     * https://leetcode.com/problems/count-of-range-sum/solutions/77990/share-my-solution/
     * https://leetcode.cn/problems/count-of-range-sum/solution/qu-jian-he-de-ge-shu-by-leetcode-solution/
     *
     * Thinking：
     * 1.使用前缀和数组求解。
     * 2.Naive Solution。使用两次遍历前缀和数组的方法。时间复杂度O(N*N)。
     * 3.参考思路中的方法的一些要点：
     * 3.1.为什么不可以一开始就对前缀和数组排序？因为本题要求range sum，如果提前排序，就违反这个条件了。
     * 3.2.为什么又可以merge sort了，3.1中已经说明不可以排序了呀？在归并排序前已经这个区间的结果计算完了；使用归并排序时，只计算lower在左半部分和upper在右半部分的情况。此时lower<=sums[j]-sums[i]<=upper的约束中，i在左半部分，j在右半部分，满足i<j。
     *
     * 验证通过：
     * Runtime 73 ms Beats 88.75%
     * Memory 55.1 MB Beats 94.53%
     *
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public static int countRangeSum_1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (start + 1 >= end) return 0;
        int mid = (start + end) / 2;
        int cnt = countWhileMergeSort(sums, start, mid, lower, upper) + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid;
        for (int i = start; i < mid; i++) {
            while (j < end && sums[j] - sums[i] < lower) {
                j++;
            }
            while (k < end && sums[k] - sums[i] <= upper) {
                k++;
            }
            cnt += k - j;
        }
        //merge sort
        long[] sorted = new long[end - start];
        int p1 = start, p2 = mid, p = 0;
        while (p1 < mid || p2 < end) {
            if (p1 >= mid) {
                sorted[p++] = sums[p2++];
            } else if (p2 >= end) {
                sorted[p++] = sums[p1++];
            } else {
                if (sums[p1] < sums[p2]) {
                    sorted[p++] = sums[p1++];
                } else {
                    sorted[p++] = sums[p2++];
                }
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            sums[i + start] = sorted[i];
        }
        return cnt;
    }


    public static void main(String[] args) {
        do_func(new int[]{-2, 5, -1}, -2, 2, 3);
        do_func(new int[]{0}, 0, 0, 1);
        do_func(new int[]{2147483647, -2147483648, -1, 0}, -1, 0, 4);
    }

    private static void do_func(int[] nums, int lower, int upper, int expected) {
        int ret = countRangeSum(nums, lower, upper);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
