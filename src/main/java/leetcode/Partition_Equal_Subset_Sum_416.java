package leetcode;

import java.util.Arrays;

/**
 * 416. Partition Equal Subset Sum
 * Medium
 * ---------------------------------
 * Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.
 *
 * Example 1:
 * Input: nums = [1,5,11,5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 * Example 2:
 * Input: nums = [1,2,3,5]
 * Output: false
 * Explanation: The array cannot be partitioned into equal sum subsets.
 *
 * Constraints:
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 */
public class Partition_Equal_Subset_Sum_416 {
    public static boolean canPartition(int[] nums) {
        return canPartition_1(nums);
    }

    /**
     * Thinking：
     * 1.native solution.
     * 递归+分治
     * 2.sum是偶数才存在解，奇数无解。
     * 只要存在一个subset的和是sum的1/2即可。
     * 问题转化为在数组中找到subset，使subset的和等于一个给定的值target。
     * 采用排序+递归+遍历的方案。由于遍历是单向的，递归时需要输入起始位置。
     *
     * 验证通过：
     * Runtime 215 ms Beats 7.1%
     * Memory 77.1 MB Beats 5.16%
     *
     * @param nums
     * @return
     */
    public static boolean canPartition_1(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum % 2 == 1) return false;
        int[][] cache = new int[nums.length + 1][sum + 1];
        return helper(nums, 0, sum / 2, cache);
    }

    /**
     *
     * @param nums 经过排序的源数组
     * @param beg 递归的起始位置
     * @param target 目标sum
     * @param cache 缓存
     * @return
     */
    private static boolean helper(int[] nums, int beg, int target, int[][] cache) {
        if (cache[beg][target] == -1) {
            return false;
        }
        for (int i = beg; i < nums.length; i++) {
            if (nums[i] == target) {
                return true;
            } else if (nums[i] > target) {
                return false;
            } else {
                if (helper(nums, i + 1, target - nums[i], cache)) {
                    return true;
                } else {
                    cache[beg][target] = -1;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 5, 11, 5}, true);
        do_func(new int[]{1, 2, 3, 5}, false);
        do_func(new int[]{1, 1, 1, 1}, true);
        do_func(new int[]{22}, false);
        do_func(new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 99, 97}, false);
    }

    private static void do_func(int[] data, boolean expected) {
        boolean ret = canPartition(data);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
