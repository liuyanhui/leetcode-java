package leetcode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * 26. Remove Duplicates from Sorted Array
 * Easy
 * ----------------------
 *Given a sorted array nums, remove the duplicates in-place such that each element appears only once and returns the new length.
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * Clarification:
 *
 * Confused why the returned value is an integer but your answer is an array?
 *
 * Note that the input array is passed in by reference, which means a modification to the input array will be known to the caller as well.
 *
 * Internally you can think of this:
 *
 * // nums is passed in by reference. (i.e., without making a copy)
 * int len = removeDuplicates(nums);
 *
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 *     print(nums[i]);
 * }
 *
 * Example 1:
 * Input: nums = [1,1,2]
 * Output: 2, nums = [1,2]
 * Explanation: Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively. It doesn't matter what you leave beyond the returned length.
 *
 * Example 2:
 * Input: nums = [0,0,1,1,1,2,2,3,3,4]
 * Output: 5, nums = [0,1,2,3,4]
 * Explanation: Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively. It doesn't matter what values are set beyond the returned length.
 *
 * Constraints:
 * 0 <= nums.length <= 3 * 104
 * -104 <= nums[i] <= 104
 * nums is sorted in ascending order.
 */
public class Remove_Duplicates_from_Sorted_Array_26 {
    public static int removeDuplicates(int[] nums) {
        return removeDuplicates_4(nums);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1.分段法。即分为已计算部分[0,i]，空白部分(i+1,j)，未计算部分[j,~]。
     * 2.
     * IF nums[i]==nums[j] THEN j++
     * ELSE nums[i+1]=nums[j] j++
     *
     * 验证通过：
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates_4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int i = 0, j = 1;
        while (j < nums.length) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
            j++;
        }
        return i + 1;
    }

    /**
     * round2
     * 验证通过：
     * Runtime: 1 ms, faster than 83.04%
     * Memory Usage: 45.1 MB, less than 7.79%
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates_3(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0, j = 1;
        for (; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }

    /**
     * removeDuplicates_1的简化版
     * @param nums
     * @return
     */
    public static int removeDuplicates_2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int end = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[end] != nums[i]) {
                nums[++end] = nums[i];
            }
        }
        return end + 1;
    }

    /**
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Remove Duplicates from Sorted Array.
     * Memory Usage: 40.4 MB, less than 94.70% of Java online submissions for Remove Duplicates from Sorted Array.
     * @param nums
     * @return
     */
    public static int removeDuplicates_1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ret = 1;
        int end = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[end] != nums[i]) {
                nums[++end] = nums[i];
                ret++;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{}, 0);
        do_func(new int[]{1, 1, 2}, 2);
        do_func(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}, 5);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = removeDuplicates(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
