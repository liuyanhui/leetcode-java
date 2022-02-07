package leetcode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * 80. Remove Duplicates from Sorted Array II
 * Medium
 * --------------------
 * Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice. The relative order of the elements should be kept the same.
 *
 * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
 *
 * Return k after placing the final result in the first k slots of nums.
 *
 * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
 *
 * Custom Judge:
 * The judge will test your solution with the following code:
 * int[] nums = [...]; // Input array
 * int[] expectedNums = [...]; // The expected answer with correct length
 * int k = removeDuplicates(nums); // Calls your implementation
 * assert k == expectedNums.length;
 * for (int i = 0; i < k; i++) {
 *     assert nums[i] == expectedNums[i];
 * }
 * If all assertions pass, then your solution will be accepted.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3]
 * Output: 5, nums = [1,1,2,2,3,_]
 * Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 * Example 2:
 * Input: nums = [0,0,1,1,1,1,2,3,3]
 * Output: 7, nums = [0,0,1,1,2,3,3,_,_]
 * Explanation: Your function should return k = 7, with the first seven elements of nums being 0, 0, 1, 1, 2, 3 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums is sorted in ascending order.
 */
public class Remove_Duplicates_from_Sorted_Array_II_80 {

    public static int removeDuplicates(int[] nums) {
        return removeDuplicates_4(nums);
    }

    /**
     * round2
     * 双指针+计数器法。
     * [0:i]表示已经符合条件的部分
     * (i:j)表示已经删除的部分
     * [j:~]表示未计算部分
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 65.08% of Java online submissions for Remove Duplicates from Sorted Array II.
     * Memory Usage: 44.8 MB, less than 6.58% of Java online submissions for Remove Duplicates from Sorted Array II.
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates_4(int[] nums) {
        int max = 2;
        int cnt = max - 1;
        int i = 0, j = 1;
        for (; j < nums.length; j++) {
            if (nums[i] == nums[j]) {
                if (cnt > 0) {
                    cnt--;
                    nums[++i] = nums[j];//这里需要进行计算，否在会导致错误
                }
            } else {
                nums[++i] = nums[j];
                cnt = max - 1;
            }
        }
        return i + 1;
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
