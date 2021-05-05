package leetcode;

/**
 * 套路
 * 189. Rotate Array
 * Medium
 * ----------------------------
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 *
 * Follow up:
 * Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */
public class Rotate_Array_189 {
    public static void rotate(int[] nums, int k) {
        rotate_2(nums, k);
    }

    /**
     * 套路
     * Space Complexity:O(1) in-place的方案
     * 1.把nums的[0,k)和[k:]分别反转
     * 2.把nums反转
     *
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Array.
     * Memory Usage: 56.4 MB, less than 5.23% of Java online submissions for Rotate Array.
     *
     * @param nums
     * @param k
     */
    public static void rotate_2(int[] nums, int k) {
        //注意k可能大于nums.length
        k %= nums.length;
        //反转[0:k)
        int l = 0, r = nums.length - 1 - k;
        reverseArray(nums, l, r);
        //反转[k:）
        l = nums.length - k;
        r = nums.length - 1;
        reverseArray(nums, l, r);
        //反转nums
        l = 0;
        r = nums.length - 1;
        reverseArray(nums, l, r);
    }

    private static void reverseArray(int[] nums, int l, int r) {
        while (l < r) {
            int t = nums[r];
            nums[r] = nums[l];
            nums[l] = t;
            l++;
            r--;
        }
    }

    /**
     * 验证成功：
     * Runtime: 1 ms, faster than 47.83% of Java online submissions for Rotate Array.
     * Memory Usage: 56.2 MB, less than 12.39% of Java online submissions for Rotate Array.
     *
     * Time Complexity:O(n)
     * Space Complexity:O(k)
     * @param nums
     * @param k
     */
    public static void rotate_1(int[] nums, int k) {
        //注意k可能大于nums.length
        k %= nums.length;
        //截取后k个数字
        int[] cached = new int[k];
        int j = 0;
        for (int i = nums.length - k; i < nums.length; i++) {
            cached[j++] = nums[i];
        }
        //移动nums
        for (int i = nums.length - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        //cached中的数字复制到数组的头部
        for (int i = 0; i < k; i++) {
            nums[i] = cached[i];
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7}, 3, new int[]{5, 6, 7, 1, 2, 3, 4});
        do_func(new int[]{-1, -100, 3, 99}, 2, new int[]{3, 99, -1, -100});
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7}, 300, new int[]{2, 3, 4, 5, 6, 7, 1});

    }

    private static void do_func(int[] nums, int k, int[] expected) {
        rotate(nums, k);
        ArrayUtils.printIntArray(nums);
        ArrayUtils.isSameThenPrintln(nums, expected);
        System.out.println("--------------");
    }
}
