package leetcode;

/**
 * 162. Find Peak Element
 * Medium
 * ----------------------------
 * A peak element is an element that is strictly greater than its neighbors.
 * Given an integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.
 * You may imagine that nums[-1] = nums[n] = -∞.
 * You must write an algorithm that runs in O(log n) time.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 *
 * Example 2:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * -2^31 <= nums[i] <= 2^31 - 1
 * nums[i] != nums[i + 1] for all valid i.
 */
public class Find_Peak_Element_162 {
    public static int findPeakElement(int[] nums) {
        return findPeakElement_2(nums);
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/find-peak-element/solution/ 之Approach3
     *
     * 更巧妙的思路
     * 二分查找，比较与mid相邻的数字
     * 0. mi=lo+(hi-lo)/2，
     * 1. 如果[mi]>[mi+1]，那么hi=mi
     * 2. 否则lo=mi+1
     * 3. 因为有条件“nums[i] != nums[i + 1] for all valid i.”，所以无需考虑==的情况。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find Peak Element.
     * Memory Usage: 43.4 MB, less than 17.57% of Java online submissions for Find Peak Element.
     *
     * @param nums
     * @return
     */
    public static int findPeakElement_2(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        int mi = 0;
        while (lo < hi) {
            mi = lo + (hi - lo) / 2;
            if (nums[mi] > nums[mi + 1]) hi = mi;
            else lo = mi + 1;
        }
        return lo;
    }

    /**
     * review round 2
     * 一般思路
     * 遍历数组，当nums[i-1]<nums[i]>nums[i+1]时，返回i
     * 时间复杂度O(N)，空间复杂度O(1)
     * 不满足要求
     *
     * 二分查找，趋势比较
     * 0. mi=lo+(hi-lo)/2，
     * 1. 如果[lo]<=[mi]<[hi]，那么[mi,hi]中必然存在peak，lo=mi+1
     * 2. 如果[lo]>[mi]>=[hi]，那么[lo,mi]中必然存在peak，hi=mi-1
     * 3. 如果[lo]<=[mid]>=[hi]，表示在[lo]和[hi]都不是peak，lo++，hi--
     * 4. 如果[lo]>[mid]<[hi]，表示[mi]不是peak，peak在[lo,mi-1]或[mi+1,hi]
     * 满足要求。
     *
     * 金矿
     * FIXME：二分查找也可以用来进行趋势查找，不仅仅是严格的比较查找
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 11.30% of Java online submissions for Find Peak Element.
     * Memory Usage: 42.9 MB, less than 43.10% of Java online submissions for Find Peak Element.
     *
     * @param nums
     * @return
     */
    public static int findPeakElement_1(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        int mi = 0;
        while (lo < hi) {
            mi = lo + (hi - lo) / 2;
            if (nums[lo] < nums[mi] && nums[mi] <= nums[hi]) {//趋势比较
                lo = mi + 1;
            } else if (nums[lo] >= nums[mi] && nums[mi] > nums[hi]) {//趋势比较
                hi = mi - 1;
            } else if (nums[lo] <= nums[mi] && nums[mi] >= nums[hi]) {
                lo++;
                hi--;
            } else {
                lo = mi + 1;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 1}, 2);
        do_func(new int[]{1, 3, 2, 1}, 1);
        do_func(new int[]{1, 2, 1, 3, 5, 6, 4}, 5);
        do_func(new int[]{0}, 0);
        do_func(new int[]{1}, 0);
        do_func(new int[]{1, 3}, 1);
        do_func(new int[]{14, 3}, 0);
        do_func(new int[]{1, 6, 5, 4, 3, 2, 1}, 1);
        do_func(new int[]{3, 1, 2}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = findPeakElement(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
