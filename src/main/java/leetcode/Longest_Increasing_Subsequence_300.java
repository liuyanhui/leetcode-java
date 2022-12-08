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
        return lengthOfLIS_1(nums);
    }

    /**
     * review 解题方法论标准流程的完美执行。从暴力法逐步优化的套路。
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
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                    max = Math.max(max, dp[i]);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        do_func(new int[]{10, 9, 2, 5, 3, 7, 101, 18}, 4);
        do_func(new int[]{0, 1, 0, 3, 2, 3}, 4);
        do_func(new int[]{7, 7, 7, 7, 7, 7, 7}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = lengthOfLIS(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
