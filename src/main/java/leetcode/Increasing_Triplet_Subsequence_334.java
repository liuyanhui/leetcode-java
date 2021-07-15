package leetcode;

/**
 * 334. Increasing Triplet Subsequence
 * Medium
 * -----------------------
 * Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5]
 * Output: true
 * Explanation: Any triplet where i < j < k is valid.
 *
 * Example 2:
 * Input: nums = [5,4,3,2,1]
 * Output: false
 * Explanation: No triplet exists.
 *
 * Example 3:
 * Input: nums = [2,1,5,0,4,6]
 * Output: true
 * Explanation: The triplet (3, 4, 5) is valid because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
 *
 * Constraints:
 * 1 <= nums.length <= 5 * 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 *
 * Follow up: Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?
 */
public class Increasing_Triplet_Subsequence_334 {
    public static boolean increasingTriplet(int[] nums) {
        return increasingTriplet_2(nums);
    }

    /**
     * 金矿。运用数学推导思路，减少影响因素，降低复杂度
     * 参考思路：
     * https://leetcode.com/problems/increasing-triplet-subsequence/discuss/79004/Concise-Java-solution-with-comments.
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 55.47% of Java .
     * Memory Usage: 80.6 MB, less than 51.22% of Java
     *
     * @param nums
     * @return
     */
    public static boolean increasingTriplet_2(int[] nums) {
        int i = Integer.MAX_VALUE, j = Integer.MAX_VALUE;
        int x = 0;
        while (x < nums.length) {
            if (nums[x] <= i) {
                i = nums[x];
            } else if (nums[x] <= j) {
                j = nums[x];
            } else {
                return true;
            }
            x++;
        }
        return false;
    }

    /**
     * 1.先找到i和j，再查找k
     * 2.如果同时发现更小的n[i']和n[j']，替换原有的i和j，继续找k
     *
     * 假设 i<j<x，且n[i]<n[j]，i'和j'存储中间状态的i和j
     * 1.if n[i]<n[x]<n[j] then j=x
     * 2.if n[i]<n[j]<n[x] then k=x,返回true
     * 3.if n[x]<n[i]<n[j] then
     * 		if n[i']<n[x] then i=i',j=x
     * 		else i'=x
     *
     * 验证失败，大概思路对路，实现细节有问题
     *
     * @param nums
     * @return
     */
    public static boolean increasingTriplet_1(int[] nums) {
        if (nums == null || nums.length < 3) return false;
        int i = 0, j = 1, k = 2, i0 = 0;
        //初始化，先找到第一个i
        while (i + 1 < nums.length && nums[i] > nums[i + 1]) {
            i++;
        }
        //找到第一个j
        j = i + 1;
        if (j >= nums.length) {
            return false;
        }

        k = j + 1;
        while (k < nums.length) {
            if (nums[k] < nums[i]) {
                if (nums[i0] < nums[k]) {
                    i = i0;
                    j = k;
                } else {
                    i0 = k;
                }
            } else if (nums[i] < nums[k] && nums[k] < nums[j]) {
                j = k;
                i = i0;
            } else if (nums[j] < nums[k]) {
                return true;
            }
            k++;
        }

        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5}, true);
        do_func(new int[]{5, 4, 3, 2, 1}, false);
        do_func(new int[]{2, 1, 5, 0, 4, 6}, true);
        do_func(new int[]{4}, false);
        do_func(new int[]{4, 6}, false);
        do_func(new int[]{2, 1, 100, 4, 6}, true);
        do_func(new int[]{327, 51, 100, 1, 4, 6}, true);
        do_func(new int[]{327, 51, 100, 1, 140, 147}, true);
        do_func(new int[]{327, 100, 140, 2, 147}, true);
        do_func(new int[]{1, 5, 0, 4, 1, 3}, true);
        do_func(new int[]{4, 6, 3, 4, 5}, true);
        do_func(new int[]{4, 6, 4, 5, 6}, true);
    }

    private static void do_func(int[] nums, boolean expected) {
        boolean ret = increasingTriplet(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
