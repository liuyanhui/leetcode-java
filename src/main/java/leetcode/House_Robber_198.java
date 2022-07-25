package leetcode;

/**
 * 198. House Robber
 * Medium
 * ---------------
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 *              Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 *              Total amount you can rob = 2 + 9 + 1 = 12.
 *
 * Constraints:
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 */
public class House_Robber_198 {

    public static int rob(int[] nums) {
        return rob_4(nums);
    }

    /**
     * rob_3()的空间复杂度优化版
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber.
     * Memory Usage: 41.4 MB, less than 62.17% of Java online submissions for House Robber.
     *
     * @param nums
     * @return
     */
    public static int rob_4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int dp_1 = 0, dp_2 = 0;
        for (int i = 0; i < nums.length; i++) {
            int t = Math.max(dp_2 + nums[i], dp_1);
            dp_2 = dp_1;
            dp_1 = t;
        }
        return dp_1;
    }

    /** unconfirmed
     * round 2
     * 典型的DP问题，前面的不同选择会影响后面不同的结果。公式如下
     * dp[i]=max(dp[i-2]+nums[i],dp[i-1])
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 20.84% of Java online submissions for House Robber.
     * Memory Usage: 41 MB, less than 79.20% of Java online submissions for House Robber.
     *
     * @param nums
     * @return
     */
    public static int rob_3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length + 1];
        dp[1] = nums[0];
        for (int i = 2; i <= nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i - 1], dp[i - 1]);
        }
        return dp[nums.length];
    }

    /**
     * rob_1()的优化版本，优化了空间复杂度为O(3)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber.
     * Memory Usage: 36.4 MB, less than 47.03% of Java online submissions for House Robber.
     *
     * @param nums
     * @return
     */
    public static int rob_2(int[] nums) {
        int a = 0, b = 0;
        for (int n : nums) {
            int t = a;
            a = Math.max(b + n, a);
            b = t;
        }
        return Math.max(b, a);
    }

    /**
     * DP思路，公式如下：
     * dp[1,i] = max(dp[1,i-2]+n[i], dp[i,i-1])
     *
     * Time Complexity:O(n)
     * Space Complexity:O(n)
     * 可以优化为Space Complexity:O(1)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber.
     * Memory Usage: 36.4 MB, less than 47.03% of Java online submissions for House Robber.
     *
     * @param nums
     * @return
     */
    public static int rob_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int dp[] = new int[nums.length + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int dpIndex = i + 1;
            dp[dpIndex] = Math.max(dp[dpIndex - 2] + nums[i], dp[dpIndex - 1]);
        }
        return dp[nums.length];
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 1}, 4);
        do_func(new int[]{2, 7, 9, 3, 1}, 12);
        do_func(new int[]{1, 1, 1, 1}, 2);
        do_func(new int[]{1, 1, 1, 1, 1, 1, 1, 1}, 4);
        do_func(new int[]{1, 1, 1, 1, 1, 1, 1}, 4);
        do_func(new int[]{100, 19, 19, 100, 99, 1, 1}, 219);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = rob(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
