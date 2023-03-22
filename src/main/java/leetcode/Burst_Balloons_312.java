package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 312. Burst Balloons
 * Hard
 * ---------------------------------
 * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.
 *
 * If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
 *
 * Return the maximum coins you can collect by bursting the balloons wisely.
 *
 * Example 1:
 * Input: nums = [3,1,5,8]
 * Output: 167
 * Explanation:
 * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 *
 * Example 2:
 * Input: nums = [1,5]
 * Output: 10
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 300
 * 0 <= nums[i] <= 100
 */
public class Burst_Balloons_312 {
    public static int maxCoins(int[] nums) {
        return maxCoins_2(nums);
    }

    public static int maxCoins_2(int[] nums) {
        int[] newNums = new int[nums.length + 2];
        int t = 1;
        //为了避免后续边界值的校验，提前在边界扩位
        for (int i = 0; i < nums.length; i++) {
            newNums[t++] = nums[i];
        }
        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;
        int[][] cache = new int[newNums.length][newNums.length];
        helper_2(newNums, 1, newNums.length - 2, cache);
        return cache[1][newNums.length - 2];
    }

    private static int helper_2(int[] nums, int beg, int end, int[][] cache) {
        if (beg > end) return 0;
        if (cache[beg][end] > 0) return cache[beg][end];
        int res = 0;
        for (int i = beg; i <= end; i++) {
            res = Math.max(res, helper_2(nums, beg, i - 1, cache) + nums[beg - 1] * nums[i] * nums[end + 1] + helper_2(nums, i + 1, end, cache));
        }
        cache[beg][end] = res;
        return res;
    }

    /**
     * 思路：
     * 1.暴力法+递归，时间复杂度O(N!)，公式为N*F(N-1)=N*(N-1)*(N-2)*..*1。
     * 2.递归法可以使用缓存优化时间复杂度，也可以转化成BP问题。
     *
     * 验证失败：Time Exceed Limit
     *
     * @param nums
     * @return
     */
    public static int maxCoins_1(int[] nums) {
        Map<String, Integer> cached = new HashMap<>();
        return helper(nums, cached);
    }

    private static int helper(int[] nums, Map<String, Integer> cached) {
        if (nums.length == 0) return 0;
        String key = getKey(nums);
        if (cached.containsKey(key)) return cached.get(key);
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int a = i == 0 ? 1 : nums[i - 1];
            int b = i == nums.length - 1 ? 1 : nums[i + 1];
            int s = a * nums[i] * b;
            int[] newArr = getNewArray(nums, i);
            s += helper(newArr, cached);
            res = Math.max(res, s);
        }
        cached.put(key, res);
        return res;
    }

    private static int[] getNewArray(int[] nums, int exclude) {
        int[] res = new int[nums.length - 1];
        int signal = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == exclude) signal = 1;
            else res[i - signal] = nums[i];
        }
        return res;
    }

    private static String getKey(int[] nums) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            res.append(nums[i] + ",");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 1, 5, 8}, 167);
        do_func(new int[]{1, 5}, 10);
        do_func(new int[]{31, 12, 5, 8, 45, 6, 2, 3, 67, 33, 2, 4, 5, 6, 3, 91}, 667865);
        do_func(new int[]{8, 2, 6, 8, 9, 8, 1, 4, 1, 5, 3, 0, 7, 7, 0, 4, 2, 2, 5}, 3630);
        do_func(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 32);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxCoins(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}

