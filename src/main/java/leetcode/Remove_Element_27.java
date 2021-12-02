package leetcode;

/**
 * https://leetcode.com/problems/remove-element/
 * 27. Remove Element
 * Easy
 * ----------------------
 * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
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
 * int len = removeElement(nums, val);
 *
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 *     print(nums[i]);
 * }
 *
 * Example 1:
 * Input: nums = [3,2,2,3], val = 3
 * Output: 2, nums = [2,2]
 * Explanation: Your function should return length = 2, with the first two elements of nums being 2.
 * It doesn't matter what you leave beyond the returned length. For example if you return 2 with nums = [2,2,3,3] or nums = [2,2,0,0], your answer will be accepted.
 *
 * Example 2:
 * Input: nums = [0,1,2,2,3,0,4,2], val = 2
 * Output: 5, nums = [0,1,4,0,3]
 * Explanation: Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4. Note that the order of those five elements can be arbitrary. It doesn't matter what values are set beyond the returned length.
 *
 * Constraints:
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 50
 * 0 <= val <= 100
 */
public class Remove_Element_27 {
    public static int removeElement(int[] nums, int val) {
        return removeElement_3(nums, val);
    }

    /**
     * round2
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00%.
     * Memory Usage: 39.1 MB, less than 26.01%
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement_3(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[cur++] = nums[i];
            }
        }
        return cur;
    }

    /**
     * 官方Solution中的更巧妙的方法，性能更优。both i and j traverse at most n steps
     * https://leetcode.com/problems/remove-element/solution/
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement_2(int[] nums, int val) {
        int n = nums.length, i = 0;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * 普通方法，
     * Assume the array has a total of nn elements, both i and j traverse at most 2n steps.
     * 官方Solution中有个更巧妙的方法
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Remove Element.
     * Memory Usage: 37.6 MB, less than 48.25% of Java online submissions for Remove Element.
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement_1(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[end++] = nums[i];
            }
        }
        return end;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 2, 2, 3}, 3, 2);
        do_func(new int[]{0, 1, 2, 2, 3, 0, 4, 2}, 2, 5);
        do_func(new int[]{2, 2, 2, 2}, 2, 0);
    }

    private static void do_func(int[] nums, int val, int expected) {
        int ret = removeElement(nums, val);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
