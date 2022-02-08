package leetcode;

/**
 * 81. Search in Rotated Sorted Array II
 * Medium
 * -----------------------------------
 * There is an integer array nums sorted in non-decreasing order (not necessarily with distinct values).
 *
 * Before being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
 *
 * Given the array nums after the rotation and an integer target, return true if target is in nums, or false if it is not in nums.
 *
 * You must decrease the overall operation steps as much as possible.
 *
 * Example 1:
 * Input: nums = [2,5,6,0,0,1,2], target = 0
 * Output: true
 *
 * Example 2:
 * Input: nums = [2,5,6,0,0,1,2], target = 3
 * Output: false
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums is guaranteed to be rotated at some pivot.
 * -10^4 <= target <= 10^4
 *
 * Follow up: This problem is similar to Search in Rotated Sorted Array, but nums may contain duplicates. Would this affect the runtime complexity? How and why?
 */
public class Search_in_Rotated_Sorted_Array_II_81 {
    public static boolean search(int[] nums, int target) {
        return search_1(nums, target);
    }

    /**
     * 最差的性能就是遍历一次nums，但是O(N)的时间复杂度，显然不满足要求。所以本题需要用O(logN)的时间复杂度解决。
     * 因为数组不是有序的所以不能直接使用二分查找。
     * 递归+二分查找思路
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search in Rotated Sorted Array II.
     * Memory Usage: 42.2 MB, less than 15.45% of Java online submissions for Search in Rotated Sorted Array II.
     *
     * @param nums
     * @param target
     * @return
     */
    public static boolean search_1(int[] nums, int target) {
        return divide(nums, target, 0, nums.length - 1);
    }

    private static boolean divide(int[] nums, int target, int left, int right) {
        if (left > right) return false;
        if (nums[left] == target || nums[right] == target) {
            return true;
        } else if (target < nums[left] && nums[right] < target) {//target一定不在数组范围内
            return false;
        } else {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return true;
            } else {
                //分别查找左半部分和右半部分
                boolean retLeft = divide(nums, target, left, mid - 1);
                boolean retRight = divide(nums, target, mid + 1, right);
                return retLeft || retRight;
            }
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 5, 6, 0, 0, 1, 2}, 0, true);
        do_func(new int[]{2, 5, 6, 0, 0, 1, 2}, 3, false);
        do_func(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 3, false);
        do_func(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0, true);
    }

    private static void do_func(int[] nums, int target, boolean expected) {
        boolean ret = search(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
