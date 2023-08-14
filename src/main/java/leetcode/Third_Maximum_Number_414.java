package leetcode;

/**
 * 414. Third Maximum Number
 * Easy
 * ---------------------------
 * Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.
 *
 * Example 1:
 * Input: nums = [3,2,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2.
 * The third distinct maximum is 1.
 *
 * Example 2:
 * Input: nums = [1,2]
 * Output: 2
 * Explanation:
 * The first distinct maximum is 2.
 * The second distinct maximum is 1.
 * The third distinct maximum does not exist, so the maximum (2) is returned instead.
 *
 * Example 3:
 * Input: nums = [2,2,3,1]
 * Output: 1
 * Explanation:
 * The first distinct maximum is 3.
 * The second distinct maximum is 2 (both 2's are counted together since they have the same value).
 * The third distinct maximum is 1.
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 *
 * Follow up: Can you find an O(n) solution?
 */
public class Third_Maximum_Number_414 {
    public static int thirdMax(int[] nums) {
        return thirdMax_1(nums);
    }

    /**
     * 验证通过：
     *
     * @param nums
     * @return
     */
    public static int thirdMax_1(int[] nums) {
        long first = Long.MIN_VALUE;
        long second = Long.MIN_VALUE;
        long third = Long.MIN_VALUE;
        for (int n : nums) {
            if (n <= third || n == second || n == first) {
                continue;
            } else if (third < n && n < second) {
                third = n;
            } else if (second < n && n < first) {
                third = second;
                second = n;
            } else {
                third = second;
                second = first;
                first = n;
            }
        }
        if (third == Long.MIN_VALUE) {
            return Math.max((int) first, (int) second);
        } else {
            return (int) third;
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 2, 1}, 1);
        do_func(new int[]{1, 2}, 2);
        do_func(new int[]{2, 2, 3, 1}, 1);
        do_func(new int[]{1, 1, 1, 1, 1}, 1);
        do_func(new int[]{1, 2, Integer.MIN_VALUE}, Integer.MIN_VALUE);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = thirdMax(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
