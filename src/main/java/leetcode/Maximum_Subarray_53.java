package leetcode;

/**
 * 53. Maximum Subarray
 * Easy
 * ----------------------------
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 *
 * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */
public class Maximum_Subarray_53 {
    public static int maxSubArray(int[] nums) {
        return maxSubArray_2(nums);
    }

    /**
     * 和maxSubArray_1相似，但是理解起来更简单
     *
     * 参考思路：
     * https://leetcode.com/problems/maximum-subarray/discuss/1595097/JAVA-or-Kadane's-Algorithm-or-Explanation-Using-Image
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Maximum Subarray.
     * Memory Usage: 90.4 MB, less than 9.30% of Java online submissions for Maximum Subarray.
     *
     * @param nums
     * @return
     */
    public static int maxSubArray_2(int[] nums) {
        int ret = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            ret = ret > sum ? ret : sum;
            //这里是精华
            if (sum < 0) sum = 0;
        }
        return ret;
    }

    /**
     * DP法
     * 思路：局部最大值和包含尾部数字的最大值的比较问题
     * 1.设[0:i]的最大连续子串值为a[i]，设[0:i]以i结尾的最大连续子串值为b[i]
     * 2.a[i+1]=max(a[i],b[i]+n[i],n[i]),b[i+1]=max(b[i]+n[i],n[i])
     * 3.max(a)即为所求
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Maximum Subarray.
     * Memory Usage: 90.5 MB, less than 5.94% of Java online submissions for Maximum Subarray.
     *
     * @param nums
     * @return
     */
    public static int maxSubArray_1(int[] nums) {
        int a = -99999, b = -99999;
        for (int i = 0; i < nums.length; i++) {
            b = Math.max(b + nums[i], nums[i]);
            a = Math.max(a, b);
        }
        return a;
    }

    public static void main(String[] args) {
        do_func(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}, 6);
        do_func(new int[]{1}, 1);
        do_func(new int[]{5, 4, -1, 7, 8}, 23);
        do_func(new int[]{-1, -4, -1, 7, -8}, 7);
        do_func(new int[]{-1, -4, -1, -7, -8}, -1);
        do_func(new int[]{-10, -4, -1, -7, -8}, -1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxSubArray(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
