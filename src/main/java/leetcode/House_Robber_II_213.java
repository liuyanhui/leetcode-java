package leetcode;

/**
 * 213. House Robber II
 * Medium
 * -------------------
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * <p>
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 * <p>
 * Example 1:
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 * <p>
 * Example 2:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * <p>
 * Example 3:
 * Input: nums = [0]
 * Output: 0
 * <p>
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */
public class House_Robber_II_213 {

    public static int rob(int[] nums) {
        return rob_1(nums);
    }

    /**
     * review round 3
     * Score[2] Lower is harder
     *
     * rob_1()的方案容易理解
     *
     */

    /**
     * 思考：
     * 1.暴力法+DP。遍历数组，每个数字作为第一个被抢的房间。最终取每次遍历的结果的最大值即可。
     * 时间复杂度O(N*N)
     * 2.在暴力法的基础上，相邻的两个数字必然只能有一个参与在最优解计算中，那么只需要对前两个数字作为起始开始计算即可。第一个数字计算时，最后一个数字不参与计算。第二个数字计算时，第一个数字不参与计算。
     * 3.DP公式：dp[i]=max(dp[i-1],dp[i-2]+nums[i])
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber II.
     * Memory Usage: 39.6 MB, less than 96.48% of Java online submissions for House Robber II.
     *
     * @param nums
     * @return
     */
    public static int rob_2(int[] nums) {
        if (nums.length == 1) return nums[0];
        int res = 0;
        int a = 0, b = 0;
        //第一个数字参与计算，最后一个数字不参与计算
        for (int i = 0; i < nums.length - 1; i++) {
            int t = Math.max(b, a + nums[i]);
            a = b;
            b = t;
        }
        res = b;
        a = 0;
        b = 0;
        //最后一个数字参与计算，第一个数组不参与计算
        for (int i = 1; i < nums.length; i++) {
            int t = Math.max(b, a + nums[i]);
            a = b;
            b = t;
        }
        res = Math.max(res, b);
        return res;
    }

    /**
     * dp问题，公式如下：
     * dp[0]=0
     * dp[1]=nums[1]
     * dp[i] = max(nums[i]+dp[i-2], dp[i-1])
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/house-robber-ii/discuss/60044/Good-performance-DP-solution-using-Java
     * https://leetcode.com/problems/house-robber-ii/discuss/59934/Simple-AC-solution-in-Java-in-O(n)-with-explanation
     * <p>
     * 问题转化为两个子集：
     * 1.包含第0个元素，不包含最后一个元素
     * 2.不包含第0个元素，包含最后一个元素
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for House Robber II.
     * Memory Usage: 36.2 MB, less than 78.84% of Java online submissions for House Robber II.
     *
     * @param nums
     * @return
     */
    public static int rob_1(int[] nums) {
        if (nums.length == 1) return nums[0];
        //计算first，不计算last
        int[] dp1 = new int[nums.length];
        dp1[0] = 0;
        dp1[1] = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            dp1[i + 1] = Math.max(dp1[i - 1] + nums[i], dp1[i]);
        }

        //不计算first，计算last
        int[] dp2 = new int[nums.length];
        dp2[0] = 0;
        dp2[1] = nums[1];
        for (int i = 2; i < nums.length; i++) {
            dp2[i] = Math.max(dp2[i - 2] + nums[i], dp2[i - 1]);
        }
        return Math.max(dp1[nums.length - 1], dp2[nums.length - 1]);
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 3, 2}, 3);
        do_func(new int[]{1, 2, 3, 1}, 4);
        do_func(new int[]{1, 2, 3, 1, 2}, 5);
        do_func(new int[]{0}, 0);
        do_func(new int[]{1}, 1);
        do_func(new int[]{99, 1, 1, 100, 1}, 199);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = rob(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
