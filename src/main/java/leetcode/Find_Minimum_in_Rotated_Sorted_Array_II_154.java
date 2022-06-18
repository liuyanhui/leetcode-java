package leetcode;

/**
 * 154. Find Minimum in Rotated Sorted Array II
 * Hard
 * ----------------------------------
 * Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,4,4,5,6,7] might become:
 * [4,5,6,7,0,1,4] if it was rotated 4 times.
 * [0,1,4,4,5,6,7] if it was rotated 7 times.
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 *
 * Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
 * You must decrease the overall operation steps as much as possible.
 *
 * Example 1:
 * Input: nums = [1,3,5]
 * Output: 1
 *
 * Example 2:
 * Input: nums = [2,2,2,0,1]
 * Output: 0
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * nums is sorted and rotated between 1 and n times.
 *
 * Follow up: This problem is similar to Find Minimum in Rotated Sorted Array, but nums may contain duplicates. Would this affect the runtime complexity? How and why?
 */
public class Find_Minimum_in_Rotated_Sorted_Array_II_154 {
    public static int findMin(int[] nums) {
        return findMin_1(nums);
    }

    /**
     * review 
     * 参考思路：
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/discuss/167981/Beats-100-Binary-Search-with-Explanations
     *
     * 二分查找思路：
     * 1.mi=lo+(hi-l0/2)
     * 2.当[mi]>[hi]时，说明右半部分的趋势是递减的，那么所求一定在右半部分即[mi+1,hi]中
     * 3.当[lo]>[mi]时，说明左半部分的趋势是递减的，那么所求一定在左半部分即[lo+1,mi]中
     * 4.当同时不是2,3的情况时，说明整个数组的趋势是递增的，那么所求为[lo]
     * 5.需要考虑特殊用例，如[2,2,2,2,2,2,2,0,2,2,2]
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 75.08% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     * Memory Usage: 43.6 MB, less than 47.31% of Java online submissions for Find Minimum in Rotated Sorted Array II.
     *
     * @param nums
     * @return
     */
    public static int findMin_1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        int mi = 0;
        while (lo < hi) {
            mi = lo + (hi - lo) / 2;
            if (nums[lo] > nums[mi]) {
                lo++;
                hi = mi;
            } else if (nums[mi] > nums[hi]) {
                lo = mi + 1;
            } else {
                hi--;
            }
        }
        return nums[lo];
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3, 5}, 1);
        do_func(new int[]{2, 2, 2, 0, 1}, 0);
        do_func(new int[]{2, 2, 2, 2, 2}, 2);

        do_func(new int[]{3, 4, 5, 1, 2}, 1);
        do_func(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        do_func(new int[]{11, 13, 15, 17}, 11);
        do_func(new int[]{2, 3, 4, 5, 1}, 1);
        do_func(new int[]{1, 2}, 1);
        do_func(new int[]{2, 1}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = findMin(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
