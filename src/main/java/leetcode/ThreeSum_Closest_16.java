package leetcode;

import java.util.Arrays;

/**
 * 16. 3Sum Closest
 * Medium
 * --------------------------
 * Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.
 * Return the sum of the three integers.
 * You may assume that each input would have exactly one solution.
 *
 * Example 1:
 * Input: nums = [-1,2,1,-4], target = 1
 * Output: 2
 * Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 *
 * Example 2:
 * Input: nums = [0,0,0], target = 1
 * Output: 0
 *
 * Constraints:
 * 3 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * -10^4 <= target <= 10^4
 */
public class ThreeSum_Closest_16 {
    /**
     * round 2
     * 采用类似15.ThreeSum问题中的夹逼法，把问题从三个变量降为两个变量。Time Complexity:O(N*N)
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 86.46% of Java online submissions for 3Sum Closest.
     * Memory Usage: 38.6 MB, less than 81.24% of Java online submissions for 3Sum Closest.
     *
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        int closest = 3000;//这里不能直接用Integer.MAX_VALUE。因为当target<0时，下面的代码"Math.abs(closest - target)"会溢出。
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                int t = nums[i] + nums[j] + nums[k];
                if (t == target) {
                    return t;
                } else if (t < target) {
                    while (j++ < k && nums[j - 1] == nums[j]) {
                    }
                } else {
                    while (j < k-- && nums[k + 1] == nums[k]) {
                    }
                }
                // Cautions:The result of "Math.abs(closest - target)" is overflow when target is less than zero, if closet equals to Integer.MAX_VALUE.
                closest = Math.abs(t - target) < Math.abs(closest - target) ? t : closest;
            }
        }

        return closest;
    }

    public static void main(String[] args) {
        do_func(new int[]{-1, 2, 1, -4}, 1, 2);
        do_func(new int[]{0, 0, 0}, 1, 0);
        do_func(new int[]{-1, 2, 1, -9}, -1, 2);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = threeSumClosest(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
