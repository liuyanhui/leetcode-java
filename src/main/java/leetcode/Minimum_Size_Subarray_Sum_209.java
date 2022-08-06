package leetcode;

/**
 * 209. Minimum Size Subarray Sum
 * Medium
 * ---------------------------------
 * Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 *
 * Example 1:
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 *
 * Example 2:
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 *
 * Example 3:
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 *
 * Constraints:
 * 1 <= target <= 10^9
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^4
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log(n)).
 */
public class Minimum_Size_Subarray_Sum_209 {
    public static int minSubArrayLen(int target, int[] nums) {
        return minSubArrayLen_3(target, nums);
    }

    /**
     * review
     * 更优的实现
     * 参考文档：
     * https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59078/Accepted-clean-Java-O(n)-solution-(two-pointers)
     *
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen_3(int target, int[] nums) {
        int left = 0, right = 0;
        int sum = 0;
        int ret = Integer.MAX_VALUE;
        while (right < nums.length) {
            sum += nums[right];
            while (sum >= target) {
                ret = Math.min(ret, right - left + 1);
                sum -= nums[left++];
            }
            right++;
        }
        return ret == Integer.MAX_VALUE ? 0 : ret;
    }

    /**
     * 采用滑动窗口思路2
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 75.79% of Java online submissions for Minimum Size Subarray Sum.
     * Memory Usage: 49.9 MB, less than 63.62% of Java online submissions for Minimum Size Subarray Sum.
     *
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen_2(int target, int[] nums) {
        int minsize = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0, right = 0;
        while (left < nums.length && right <= nums.length) {
            if (sum < target && right < nums.length) {
                sum += nums[right];
                right++;
            } else {
                if (sum >= target) {
                    minsize = Math.min(minsize, right - left);
                }
                sum -= nums[left];
                left++;
            }
        }
        return minsize == Integer.MAX_VALUE ? 0 : minsize;
    }

    /**
     * 暴力思路：
     * 依次遍历数组。从每一个数字开始向后累加，当等于或第一次大于target时，计算全局最优解，并跳出数字的循环。
     * 时间复杂度O(N*N)，空间复杂度O(1)
     *
     * 时间复杂度的一般优化路线为：2^N -> N*N -> NlogN -> N -> logN -> 1
     * 所以，本题基于暴力思路的下一个优化目标的复杂度为O(N)
     *
     * 采用滑动窗口思路，复杂度为O(N)：
     * 1.数组向右累加，sum=nums[l:r]
     * 2.如果sum<target，那么sum=sum+nums[right]，right++
     * 3.如果sum>=target，那么ret=min(ret,r-l)，sum=sum-nums[left] ，l++
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 75.79% of Java online submissions for Minimum Size Subarray Sum.
     * Memory Usage: 58 MB, less than 38.06% of Java online submissions for Minimum Size Subarray Sum.
     *
     * @param target
     * @param nums
     * @return
     */
    public static int minSubArrayLen_1(int target, int[] nums) {
        int minsize = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0, right = 0;
        while (right < nums.length) {
            sum += nums[right];
            if (sum < target) {
                right++;
            } else {
                minsize = Math.min(minsize, right - left + 1);
                sum -= nums[left];
                left++;
                sum -= nums[right];
            }
        }
        return minsize == Integer.MAX_VALUE ? 0 : minsize;
    }

    public static void main(String[] args) {
        do_func(7, new int[]{2, 3, 1, 2, 4, 3}, 2);
        do_func(4, new int[]{1, 4, 4}, 1);
        do_func(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}, 0);
        do_func(4, new int[]{1, 2, 3, 6}, 1);
    }

    private static void do_func(int target, int[] nums, int expected) {
        int ret = minSubArrayLen(target, nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
