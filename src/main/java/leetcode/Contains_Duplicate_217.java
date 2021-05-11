package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 217. Contains Duplicate
 * Easy
 * -----------------
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: false
 *
 * Example 3:
 * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Contains_Duplicate_217 {
    /**
     * 验证通过：
     * Runtime: 6 ms, faster than 49.07% of Java online submissions for Contains Duplicate.
     * Memory Usage: 45 MB, less than 35.20% of Java online submissions for Contains Duplicate.
     *
     * @param nums
     * @return
     */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> existed = new HashSet<>();
        for (int n : nums) {
            if (existed.contains(n)) {
                return true;
            } else {
                existed.add(n);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 1}, true);
        do_func(new int[]{1, 2, 3, 4}, false);
        do_func(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}, true);
    }

    private static void do_func(int[] nums, boolean expected) {
        boolean ret = containsDuplicate(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
