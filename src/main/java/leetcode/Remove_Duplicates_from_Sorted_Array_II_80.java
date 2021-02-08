package leetcode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * 80. Remove Duplicates from Sorted Array II
 * Medium
 * --------------------
 *Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.
 *
 * Do not allocate extra space for another array; you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * Clarification:
 * Confused why the returned value is an integer, but your answer is an array?
 *
 * Note that the input array is passed in by reference, which means a modification to the input array will be known to the caller.
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
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3]
 * Output: 5, nums = [1,1,2,2,3]
 * Explanation: Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively. It doesn't matter what you leave beyond the returned length.
 * Example 2:
 *
 * Input: nums = [0,0,1,1,1,1,2,3,3]
 * Output: 7, nums = [0,0,1,1,2,3,3]
 * Explanation: Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively. It doesn't matter what values are set beyond the returned length.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 3 * 104
 * -104 <= nums[i] <= 104
 * nums is sorted in ascending order.
 *
 */
public class Remove_Duplicates_from_Sorted_Array_II_80 {

    public static int removeDuplicates(int[] nums) {
        return removeDuplicates_3(nums);
    }

    /**
     * nums是有序的，通过nums[i]的值的大小可以判断是否
     *
     * 参考资料：
     * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/discuss/27976/3-6-easy-lines-C%2B%2B-Java-Python-Ruby
     * @param nums
     * @return
     */
    public static int removeDuplicates_3(int[] nums) {
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i - 2])
                nums[i++] = n;
        return i;
    }

    /**
     * removeDuplicates_1的优化版，一次遍历。然鹅，性能没有多大提升。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 24.76% of Java online submissions for Remove Duplicates from Sorted Array II.
     * Memory Usage: 41.2 MB, less than 6.08% of Java online submissions for Remove Duplicates from Sorted Array II.
     * @param nums
     * @return
     */
    public static int removeDuplicates_2(int[] nums) {
        int c = 0, t = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[c] == nums[i]) {
                if (t < 2) {
                    nums[++c] = nums[i];
                    t++;
                }
            } else {
                nums[++c] = nums[i];
                t = 1;
            }
        }
        return c + 1;
    }

    /**
     * 两次遍历，第一次遍历标记重复的数字，第二次遍历清楚被标记的重复的数字
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 24.76% of Java online submissions for Remove Duplicates from Sorted Array II.
     * Memory Usage: 41.2 MB, less than 5.20% of Java online submissions for Remove Duplicates from Sorted Array II.
     * @param nums
     * @return
     */
    public static int removeDuplicates_1(int[] nums) {
        int c = 0;
        //标记
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[c] && c + 1 < i) {
                nums[i] = 9999;
            } else if (nums[c] != nums[i]) {
                c = i;
            }
        }
        //清除
        c = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != 9999) {
                nums[c] = nums[i];
                c++;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 1, 1, 2, 2, 3}, 5);
        do_func(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}, 7);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = removeDuplicates(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
