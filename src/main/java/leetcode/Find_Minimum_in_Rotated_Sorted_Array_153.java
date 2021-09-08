package leetcode;

/**
 *153. Find Minimum in Rotated Sorted Array
 * Medium
 * -----------------------
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
 * [4,5,6,7,0,1,2] if it was rotated 4 times.
 * [0,1,2,4,5,6,7] if it was rotated 7 times.
 *
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 *
 * Given the sorted rotated array nums of unique elements, return the minimum element of this array.
 * You must write an algorithm that runs in O(log n) time.
 *
 * Example 1:
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 *
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
 *
 * Example 3:
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * All the integers of nums are unique.
 * nums is sorted and rotated between 1 and n times.
 */
public class Find_Minimum_in_Rotated_Sorted_Array_153 {
    /**
     * 金矿：在未排序的集合中查找某个数，采用基于数字的binary search；在已排序的集合中查找某个数，采用基于下标的binary search
     * 与Find_the_Duplicate_Number_287和Kth_Smallest_Element_in_a_Sorted_Matrix_378可以组成一个系列
     *
     * 参考：
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code
     *
     * 验证通过:
     * Runtime: 1 ms, faster than 19.79% of Java .
     * Memory Usage: 39.3 MB, less than 25.84% of Java .
     *
     * @param nums
     * @return
     */
    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            if (nums[low] <= nums[mid] && nums[mid] <= nums[high]) {
                break;
            } else if (nums[low] <= nums[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 4, 5, 1, 2}, 1);
        do_func(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        do_func(new int[]{11, 13, 15, 17}, 11);
        do_func(new int[]{2, 3, 4, 5, 1}, 1);
        do_func(new int[]{1, 2}, 1);
        do_func(new int[]{2, 1}, 1);
        do_func(new int[]{3, 1, 2}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = findMin(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
