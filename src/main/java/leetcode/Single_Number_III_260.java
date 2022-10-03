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
     * @see Single_Number_136
     * @see Single_Number_II_137
     *
     * @param nums
     * @return
     */
    public static int[] singleNumber(int[] nums) {
        return singleNumber_2(nums);
    }

    public static int[] singleNumber_2(int[] nums) {
        return null;
    }

    /**
     * review
     *
     * 参考思路：
     * https://leetcode-cn.com/problems/single-number-iii/solution/cai-yong-fen-zhi-de-si-xiang-jiang-wen-ti-jiang-we/
     *
     * 分类法，把问题转化成Single_Number_136问题，“有一个数组每个数字都出现两次，有一个数字只出现了一次，求出该数字”。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 96.92% of Java online submissions for Single Number III.
     * Memory Usage: 38.6 MB, less than 97.06% of Java online submissions for Single Number III.
     *
     * @param nums
     * @return
     */
    public static int[] singleNumber_1(int[] nums) {
        int[] ret = new int[2];
        //把所有的元素进行异或操作，最终得到一个异或值。因为是不同的两个数字，所以这个值必定不为 0；
        int xor = 0;
        for (int n : nums) {
            xor ^= n;
        }
        //取异或值最后一个二进制位为 1 的数字作为 mask，如果是 1 则表示两个数字在这一位上不同。
        int mask = xor & (-xor);//这里很关键
        //通过与这个 mask 进行与操作，如果为 0 的分为一个数组，为 1 的分为另一个数组。
        // 这样就把问题降低成了：“有一个数组每个数字都出现两次，有一个数字只出现了一次，求出该数字”。
        // 对这两个子问题分别进行全异或就可以得到两个解。也就是最终的数组了。
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
