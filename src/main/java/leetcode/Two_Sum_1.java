package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Two Sum
 * Easy
 * --------------------------
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 *
 * Example 1:
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Output: Because nums[0] + nums[1] == 9, we return [0, 1].
 *
 * Example 2:
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 *
 * Example 3:
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 *
 * Constraints:
 * 2 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 * Only one valid answer exists.
 *
 * Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?
 */
public class Two_Sum_1 {
    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 99.68% of Java online submissions for Two Sum.
     * Memory Usage: 39.1 MB, less than 75.95% of Java online submissions for Two Sum.
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] ret = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                ret[0] = i;
                ret[1] = map.get(target - nums[i]);
                break;
            }
            map.put(nums[i], i);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1});
        do_func(new int[]{3, 2, 4}, 6, new int[]{1, 2});
        do_func(new int[]{3, 3}, 6, new int[]{0, 1});
    }

    private static void do_func(int[] nums, int target, int[] expected) {
        int[] ret = twoSum(nums, target);
        Arrays.sort(ret);
        ArrayUtils.printIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
