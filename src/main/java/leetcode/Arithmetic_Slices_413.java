package leetcode;

/**
 * 413. Arithmetic Slices
 * Medium
 * -----------------------------
 * An integer array is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
 * For example, [1,3,5,7,9], [7,7,7,7], and [3,-1,-5,-9] are arithmetic sequences.
 *
 * Given an integer array nums, return the number of arithmetic subarrays of nums.
 *
 * A subarray is a contiguous subsequence of the array.
 *
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: 3
 * Explanation: We have 3 arithmetic slices in nums: [1, 2, 3], [2, 3, 4] and [1,2,3,4] itself.
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -1000 <= nums[i] <= 1000
 */
public class Arithmetic_Slices_413 {
    public static int numberOfArithmeticSlices(int[] nums) {
        return numberOfArithmeticSlices_1(nums);
    }

    /**
     * Thinking：
     * 1.分为子串subarray提取 和 判断subarray是否为Arithmetic两个部分。
     * 2.subarray[i:j]是Arithmetic，但subarray[i:j+1]不是Arithmetic，那么从nums[j]开始重新提取。
     * 3.subarray[i:j]如果是Arithmetic，那么统计在subarray[i:j]中的结果。公式为：设subarray[i:j]的长度为n，res=(n-2)+(n-3)..+(n-n+1)=1+2+..+(n-2)
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 40.2 MB Beats 77.37%
     *
     * @param nums
     * @return
     */
    public static int numberOfArithmeticSlices_1(int[] nums) {
        int res = 0;
        //提取subarray
        int beg = 0, end = 0;
        int gap = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[end] != gap) {
                //计算结果，并累加到结果集
                res += calc(beg, end);

                //清理缓存
                beg = end;
                //刷新gap
                gap = nums[i] - nums[end];
            }
            end++;
            if (i == nums.length - 1) {
                res += calc(beg, end);
            }
        }
        return res;
    }

    private static int calc(int beg, int end) {
        int n = end - beg + 1;
        if (n >= 3) {
            return (n - 2) * (n - 1) / 2;
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4}, 3);
        do_func(new int[]{1}, 0);
        do_func(new int[]{1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4}, 9);
        do_func(new int[]{1, 1, 1, 1}, 3);
        do_func(new int[]{-1, -2, -3, -4}, 3);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = numberOfArithmeticSlices(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
