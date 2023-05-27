package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        return combinationSum4_4(nums, target);
    }

    /**
     * 在combinationSum4_3()基础上优化，与combinationSum4_2()思路相同
     *
     * 由于target是有限的，可以采用DP思路，把从1到target的结果依次计算出来。
     * 1.n[]排序
     * 2.公式为 dp[k]=sum(d[n[i]])
     * 3.时间复杂度：O(N*T)
     *
     * 验证通过：
     * Runtime 1 ms Beats 74.69%
     * Memory 40.6 MB Beats 13.71%
     *
     * @param nums
     * @param target
     * @return
     */
    public static int combinationSum4_4(int[] nums, int target) {
        Arrays.sort(nums);
        //初始化DP数组
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int n : nums) {
            if (n > target) break;
            dp[n] = 1;
        }
        //
        for (int t = 1; t <= target; t++) {
            for (int n : nums) {
                if (t - n <= 0) break;
                dp[t] += dp[t - n];
            }
        }

        return dp[target];
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.naive solution
     * 递归法
     * f(n[],t)=f(n[],t-n[0])+f(n[],t-n[1])+...f(n[],t-n[k-1])，n[]的长度为k。
     * 时间复杂度：O(N^N*T)
     * 2.递归+缓存
     *
     * 验证通过：
     * Runtime 2 ms Beats 23.27%
     * Memory 41.1 MB Beats 5.47%
     *
     * @param nums
     * @param target
     * @return
     */
    public static int combinationSum4_3(int[] nums, int target) {
        Map<Integer, Integer> cache = new HashMap<>();
        Arrays.sort(nums);
        return helper(nums, target, cache);
    }

    private static int helper(int[] nums, int target, Map<Integer, Integer> cache) {
        if (target < 0) return 0;
        if (target == 0) return 1;
        if (cache.containsKey(target)) return cache.get(target);
        int res = 0;
        for (int n : nums) {
            //nums已经过排序，如果当前已经无解，后续也无需在计算
            if (target - n <= 0) {
                if (target - n == 0) res++;
                break;
            }
            res += helper(nums, target - n, cache);
        }
        cache.put(target, res);
        return res;
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
        do_func(new int[]{1, 2, 3}, 1, 1);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = combinationSum4(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
