package leetcode;

/**
 * 套路
 * 260. Single Number III
 * Medium
 * ------------------
 * Given an integer array nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once. You can return the answer in any order.
 *
 * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
 *
 * Example 1:
 * Input: nums = [1,2,1,3,2,5]
 * Output: [3,5]
 * Explanation:  [5, 3] is also a valid answer.
 *
 * Example 2:
 * Input: nums = [-1,0]
 * Output: [-1,0]
 *
 * Example 3:
 * Input: nums = [0,1]
 * Output: [1,0]
 *
 * Constraints:
 * 2 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Each integer in nums will appear twice, only two integers will appear once.
 */
public class Single_Number_III_260 {
    /**
     *
     * 参考思路：
     * https://leetcode-cn.com/problems/single-number-iii/solution/cai-yong-fen-zhi-de-si-xiang-jiang-wen-ti-jiang-we/
     * 验证通过：
     * Runtime: 1 ms, faster than 96.92% of Java online submissions for Single Number III.
     * Memory Usage: 38.6 MB, less than 97.06% of Java online submissions for Single Number III.
     *
     * @param nums
     * @return
     */
    public static int[] singleNumber(int[] nums) {
        int[] ret = new int[2];
        int xor = 0;
        for (int n : nums) {
            xor ^= n;
        }
        int mask = xor & (-xor);
        for (int n : nums) {
            if ((mask & n) == 0) {
                ret[0] ^= n;
            } else {
                ret[1] ^= n;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 1, 3, 2, 5}, new int[]{3, 5});
        do_func(new int[]{-1, 0}, new int[]{-1, 0});
        do_func(new int[]{0, 1}, new int[]{1, 0});
    }

    private static void do_func(int[] nums, int[] expected) {
        int[] ret = singleNumber(nums);
        ArrayUtils.printIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
