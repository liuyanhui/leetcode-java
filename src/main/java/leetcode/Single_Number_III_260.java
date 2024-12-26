package leetcode;

import java.util.Arrays;

/**
 * 套路
 * 260. Single Number III
 * Medium
 * ------------------
 * Given an integer array nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once. You can return the answer in any order.
 * <p>
 * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
 * <p>
 * Example 1:
 * Input: nums = [1,2,1,3,2,5]
 * Output: [3,5]
 * Explanation:  [5, 3] is also a valid answer.
 * <p>
 * Example 2:
 * Input: nums = [-1,0]
 * Output: [-1,0]
 * <p>
 * Example 3:
 * Input: nums = [0,1]
 * Output: [1,0]
 * <p>
 * Constraints:
 * 2 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Each integer in nums will appear twice, only two integers will appear once.
 */
public class Single_Number_III_260 {
    /**
     * @see Single_Number_136
     * @see Single_Number_II_137
     */
    public static int[] singleNumber(int[] nums) {
        return singleNumber_r3_1(nums);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * [group] Single_Number_136 , Single_Number_II_137
     * <p>
     * Thinking
     * 1. Bit Maniputation
     * 1.1. 原理
     * XOR 可以作为分类器，把数字划分成2个部分。把输入中只出现1次的两个数字划分到不同的数据集中。
     * 具体原理是：设经过XOR计算的值为 a 。a的二进制中为1的bit，说明在原始参与计算的两个数在该bit的值不同，所以可以作为分类器。分类器中除了该bit为1之外，其他bit都为0。
     * 1.2. 算法。3次遍历法。
     * 第1次遍历。
     * 用XOR遍历数组，得到结果x。
     * 通过x得到只有一个bit为1，其他bit为0的分类器c。
     * 第2次遍历。
     * 用分类器c把数组分为两部分arr1和arr2
     * 第3次遍历。
     * 对arr1和arr2分别进行XOR计算可以得到2个数，这2个数就是所求
     * 1.3. 算法优化：第2、3次遍历结合起来，可以在O(1)空间复杂度实现。
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 100.00%
     * Memory 45.66 MB Beats 61.11%
     *
     * @param nums
     * @return
     */
    public static int[] singleNumber_r3_1(int[] nums) {
        int xor_res = 0;
        for (int n : nums) {
            xor_res ^= n;
        }
        //计算分类器
        int mask = 1;
        while ((xor_res & mask) == 0) {
            mask = mask << 1;
        }
        //用分类器对输入数组进行分类，得到所求
        int n1 = 0, n2 = 0;
        for (int n : nums) {
            if ((n & mask) == 0) {
                n1 ^= n;
            } else {
                n2 ^= n;
            }
        }
        int[] res = new int[2];
        res[0] = n1;
        res[1] = n2;
        return res;
    }


    public static int[] singleNumber_2(int[] nums) {
        return null;
    }

    /**
     * review round 2
     * <p>
     * 参考思路：
     * https://leetcode-cn.com/problems/single-number-iii/solution/cai-yong-fen-zhi-de-si-xiang-jiang-wen-ti-jiang-we/
     * <p>
     * 分类法，把问题转化成Single_Number_136问题，“有一个数组每个数字都出现两次，有一个数字只出现了一次，求出该数字”。
     * 是取模计算分类器的一种运用。跟Single_Number_II_137一样。
     * review 扩展：取模计算是一个简单和方便的分类器算法。
     * <p>
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
        System.out.println("==================");
    }

    private static void do_func(int[] nums, int[] expected) {
        Arrays.sort(expected);
        int[] ret = singleNumber(nums);
        Arrays.sort(ret);
        ArrayUtils.printlnIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
