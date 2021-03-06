package leetcode;

/**
 * https://leetcode.com/problems/search-insert-position/
 * 35. Search Insert Position
 * Easy
 * ------------
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 *
 * Example 1:
 * Input: nums = [1,3,5,6], target = 5
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,3,5,6], target = 2
 * Output: 1
 *
 * Example 3:
 * Input: nums = [1,3,5,6], target = 7
 * Output: 4
 *
 * Example 4:
 * Input: nums = [1,3,5,6], target = 0
 * Output: 0
 *
 * Example 5:
 * Input: nums = [1], target = 0
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums contains distinct values sorted in ascending order.
 * -104 <= target <= 104
 */
public class Search_Insert_Position_35 {
    public static int searchInsert(int[] nums, int target) {
        return searchInsert_4(nums, target);
    }

    /**
     * 简化版binary search，参考思路：
     * https://leetcode.com/problems/search-insert-position/discuss/15080/My-8-line-Java-solution
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_4(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] > target) high = mid - 1;
            else low = mid + 1;
        }
        return low;
    }

    /**
     * binary search 思路
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search Insert Position.
     * Memory Usage: 40.1 MB, less than 9.73% of Java online submissions for Search Insert Position.
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_3(int[] nums, int target) {
        int mid = 0;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return nums[left] >= target ? left : left + 1;
    }

    /**
     * searchInsert_1的精简版本
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_2(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search Insert Position.
     * Memory Usage: 38.5 MB, less than 85.60% of Java online submissions for Search Insert Position.
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < target) {
                continue;
            } else if (nums[i] == target) {
                return i;
            } else {
                return i;
            }
        }
        return nums.length;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3, 5, 6}, 5, 2);
        do_func(new int[]{1, 3, 5, 6}, 2, 1);
        do_func(new int[]{1, 3, 5, 6}, 7, 4);
        do_func(new int[]{1, 3, 5, 6}, 0, 0);
        do_func(new int[]{1}, 0, 0);
        do_func(new int[]{1}, 1, 0);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = searchInsert(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
