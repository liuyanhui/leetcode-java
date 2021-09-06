package leetcode;

/**
 * 377. Combination Sum IV
 * Medium
 * -------------------
 * Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.
 *
 * The answer is guaranteed to fit in a 32-bit integer.
 *
 * Example 1:
 * Input: nums = [1,2,3], target = 4
 * Output: 7
 * Explanation:
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * Note that different sequences are counted as different combinations.
 *
 * Example 2:
 * Input: nums = [9], target = 3
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 1000
 * All the elements of nums are unique.
 * 1 <= target <= 1000
 *
 * Follow up: What if negative numbers are allowed in the given array? How does it change the problem? What limitation we need to add to the question to allow negative numbers?
 */
public class Combination_Sum_IV_377 {
    public static int combinationSum4(int[] nums, int target) {
        return combinationSum4_2(nums, target);
    }

    /**
     * DP思路：dp[target] = sum(dp[target - nums[i]])
     *
     * 参考思路：
     * https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 83.18% of Java online submissions for Combination Sum IV.
     * Memory Usage: 36 MB, less than 95.92% of Java online submissions for Combination Sum IV.
     *
     * @param nums
     * @param target
     * @return
     */
    public static int combinationSum4_2(int[] nums, int target) {
        if (target <= 0) return 0;
        int[] dp = new int[target + 1];
        for (int i = 1; i <= target; i++) {
            int t = 0;
            for (int n : nums) {
                if (n == i) {//target和nums[i]相等时
                    t += 1;
                } else if (n < i) {
                    //这里是重点
                    t += dp[i - n];
                }
            }
            dp[i] = t;
        }
        return dp[target];
    }

    /**
     * brute force思路
     * 树的遍历问题，树的最大高度为target，非叶子节点的出度都是nums.length
     * 递归法
     *
     * 逻辑简单，验证失败，原因：Time Limit Exceeded
     *
     * @param nums
     * @param target
     * @return
     */
    public static int combinationSum4_1(int[] nums, int target) {
        if (target == 0) return 1;
        else if (target < 0) return 0;
        int ret = 0;
        for (int n : nums) {
            ret += combinationSum4_1(nums, target - n);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, 4, 7);
        do_func(new int[]{9}, 3, 0);
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 8, 128);
        do_func(new int[]{2, 1, 3}, 35, 1132436852);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = combinationSum4(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
