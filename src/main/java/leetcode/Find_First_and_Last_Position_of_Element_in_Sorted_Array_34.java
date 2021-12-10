package leetcode;

/**
 * 34. Find First and Last Position of Element in Sorted Array
 * Medium
 * ------------------------
 * Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
 *
 * If target is not found in the array, return [-1, -1].
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 *
 * Example 2:
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 *
 * Example 3:
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 *
 * Constraints:
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums is a non-decreasing array.
 * -10^9 <= target <= 10^9
 */
public class Find_First_and_Last_Position_of_Element_in_Sorted_Array_34 {
    public static int[] searchRange(int[] nums, int target) {
        return searchRange_2(nums, target);
    }

    /**
     * 1.通过二分查找先找到最左侧的beg，再通过二分查找找到最右侧的end
     * Time Complexity:O(logN)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java .
     * Memory Usage: 42.5 MB, less than 45.46% of Java
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange_2(int[] nums, int target) {
        if (nums == null || nums.length == 0
                || target < nums[0] || nums[nums.length - 1] < target)
            return new int[]{-1, -1};
        int beg = -1, end = -1;
        //找到左侧
        int l = 0, r = nums.length - 1;
        while (l < r) {
            //保证在只有两个节点时，mid始终指向前面的一个。mid的前倾向
            int m = (l + r) / 2;
            if (nums[m] < target) l = m + 1;
            else if (nums[m] > target) r = m - 1;
            else {
                r = m;
            }
        }
        if (nums[r] == target) {
            beg = r;
            //找到右侧
            r = nums.length - 1;
            while (l < r) {
                //这里是重点，与上面找到左右节点的不同。保证在只有两个节点时，mid始终指向后面的一个。mid的后倾向。
                int m = (l + r) / 2 + 1;
                if (nums[m] < target) l = m + 1;
                else if (nums[m] > target) r = m - 1;
                else {
                    l = m;
                }
            }
            end = l;
        }
        return new int[]{beg, end};
    }

    /**
     * Time Complexity:O(N) 不满足要求
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java
     * Memory Usage: 42 MB, less than 94.49% of Java
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange_1(int[] nums, int target) {
        if (nums == null || nums.length == 0
                || target < nums[0] || nums[nums.length - 1] < target)
            return new int[]{-1, -1};
        int beg = -1, end = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                beg = beg == -1 ? i : beg;
                end = i;
            } else if (nums[i] > target) {
                break;
            }
        }

        return new int[]{beg, end};
    }

    public static void main(String[] args) {
        do_func(new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4});
        do_func(new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1});
        do_func(new int[]{}, 0, new int[]{-1, -1});
        do_func(new int[]{5, 7, 7, 8, 8, 10}, 5, new int[]{0, 0});
        do_func(new int[]{5, 7, 7, 8, 8, 10}, 10, new int[]{5, 5});
        do_func(new int[]{1}, 1, new int[]{0, 0});
        do_func(new int[]{7, 7, 7, 7, 7, 7}, 7, new int[]{0, 5});
    }

    private static void do_func(int[] nums, int target, int[] expected) {
        int[] ret = searchRange(nums, target);
        ArrayUtils.printIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
