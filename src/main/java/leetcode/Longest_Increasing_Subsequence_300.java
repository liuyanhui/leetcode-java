package leetcode;

/**
 * 300. Longest Increasing Subsequence
 * Medium
 * -------------------------------
 * Given an integer array nums, return the length of the longest strictly increasing subsequence .
 *
 * Example 1:
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
 *
 * Example 2:
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 *
 * Example 3:
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 *
 * Follow up: Can you come up with an algorithm that runs in O(n log(n)) time complexity?
 */
public class Longest_Increasing_Subsequence_300 {
    public static int lengthOfLIS(int[] nums) {
        return lengthOfLIS_2(nums);
    }

    /**
     * review LIS问题的套路2
     *
     * LIS问题有多种方案，有两种是比较容易理解的：1.DP思路(见lengthOfLIS_1()，O(N*N))；2.Patience sorting思路(见lengthOfLIS_2(),O(N*logN))
     *
     * Patience sorting是解决LIS问题的一种思路，性能更优。
     *
     * 1.pile内的数字是递减的。
     * 2.pile之间的top元素是递增的。
     * 3.pile的长度就是LIS的长度。
     *
     * 参考文档：
     * https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf
     * https://en.wikipedia.org/wiki/Patience_sorting
     * https://leetcode.com/problems/longest-increasing-subsequence/solutions/74824/java-python-binary-search-o-nlogn-time-with-explanation/ 中的@Sithis 的comment
     *
     * 验证通过：
     * Runtime 3 ms Beats 96.86%
     * Memory 42.5 MB Beats 33.93%
     *
     * @param nums
     * @return
     */
    public static int lengthOfLIS_2(int[] nums) {
        int[] piles = new int[nums.length];
        piles[0] = nums[0];
        int max = 0;
        for (int n : nums) {
            int l = 0, r = max;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (n > piles[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            piles[l] = n;
            max = Math.max(l, max);
        }

        return max + 1;
    }

    /**
     * review 解题方法论标准流程的完美执行。从暴力法逐步优化的套路。LIS问题的套路1。
     *
     * 思考：
     * 1.暴力+递归法。遍历序列中的数字。当nums[i]<nums[i+1]时，计算子序列长度，判断是否要更新返回结果，从nums[i+1]开始递归；当nums[i]>=nums[i+1]时，执行continue命令。递归方法的含义是计算从i到末尾（必须包含i）的最大子序列。时间复杂度O(N^N)
     * 2.加入缓存，减低时间复杂度。（大多数递归都可以利用缓存、stack、DP降低时间复杂度）。cache[i]记录从i开始到末尾的最大子序列长度。
     * 3.DP思路。dp[i]记录从i开始到末尾(包含i)的最大子序列长度。从后向前遍历。公式为dp[i]=Math.max(dp[i],1+dp[j])，j为i+1~末尾。时间复杂度O(n^2)
     *
     * 验证通过：
     * Runtime 69 ms Beats 63.99%
     * Memory 41.4 MB Beats 99.5%
     *
     * @param nums
     * @return
     */
    public static int lengthOfLIS_1(int[] nums) {
        int max = 1;
        int[] dp = new int[nums.length];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            // 与所有已经计算过的dp[j]进行计算
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                    max = Math.max(max, dp[i]);//round 2 这里可以已到循环外
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        do_func(new int[]{10, 9, 2, 5, 3, 7, 101, 18}, 4);
        do_func(new int[]{0, 1, 0, 3, 2, 3}, 4);
        do_func(new int[]{7, 7, 7, 7, 7, 7, 7}, 1);
        do_func(new int[]{-99}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = lengthOfLIS(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
