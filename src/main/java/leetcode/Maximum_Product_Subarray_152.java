package leetcode;

/**
 * 152. Maximum Product Subarray
 * Medium
 * ------------------------------
 * Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, and return the product.
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 * A subarray is a contiguous subsequence of the array.
 *
 * Example 1:
 * Input: nums = [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 *
 * Example 2:
 * Input: nums = [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 10^4
 * -10 <= nums[i] <= 10
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 */
public class Maximum_Product_Subarray_152 {
    public static int maxProduct(int[] nums) {
        return maxProduct_r3_1(nums);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Kadane's Algorithm是解决连续子序列和最大值的问题的最优解。
     *
     * 验证通过：性能一般，耗时3ms
     *
     */
    public static int maxProduct_r3_1(int[] nums) {
        int[] maxarr = new int[nums.length + 1];
        int[] minarr = new int[nums.length + 1];
        maxarr[0] = 1;
        minarr[0] = 1;
        int ret = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int max_t = maxarr[i] * nums[i];
            int min_t = minarr[i] * nums[i];

            maxarr[i + 1] = Math.max(nums[i], max_t);
            maxarr[i + 1] = Math.max(maxarr[i + 1], min_t);

            minarr[i + 1] = Math.min(nums[i], max_t);
            minarr[i + 1] = Math.min(minarr[i + 1], min_t);

            ret = Math.max(ret, maxarr[i + 1]);
        }

        return ret;
    }

    /**
     * 所有提交记录中时间复杂度最优的解。
     * 1.从左往右计算一次最大值
     * 2.从右往左计算一次最大值
     *
     * @param nums
     * @return
     */
    public static int maxProduct_3(int[] nums) {
        int len = nums.length;

        int best = Integer.MIN_VALUE, curr = 1;
        for (int i = 0; i < len; i++) {
            curr *= nums[i];
            best = Math.max(best, curr);
            if (curr == 0) {
                curr = 1;
            }
        }

        curr = 1;
        for (int i = len - 1; i >= 0; i--) {
            curr *= nums[i];
            best = Math.max(best, curr);
            if (curr == 0) {
                curr = 1;
            }
        }

        return best;
    }

    /**
     * review round2
     * maxProduct_1()的基础上优化了DP数组
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 76.53% of Java online submissions for Maximum Product Subarray.
     * Memory Usage: 45.2 MB, less than 21.33% of Java online submissions for Maximum Product Subarray.
     *
     * @param nums
     * @return
     */
    public static int maxProduct_2(int[] nums) {
        int ret = Integer.MIN_VALUE;
        int maxarr = 1;
        int minarr = 1;
        int a, b;
        for (int i = 0; i < nums.length; i++) {
            a = maxarr * nums[i];
            b = minarr * nums[i];
            maxarr = Math.max(nums[i], Math.max(a, b));
            minarr = Math.min(nums[i], Math.min(a, b));
            ret = Math.max(ret, maxarr);
        }
        return ret;
    }

    /**
     * 暴力法
     * 1.连续子序列意味着可以通过穷举法，计算所有可能的组合
     * 2.有重复计算的部分（可以优化，用二维数组记录中间值，[i,j]表示第i位到第j位的乘积）
     * 3.二维数组的最大值记为所求。
     * 4.时间复杂度O(N*N)，空间复杂度O(N*N)
     *
     * 采用DP法
     * DP法
     * 因为有负数参与计算的乘积会发生反转，所以计算过程中要同时记录最大值和最小值，确保下一个数字计算时不会把会反转的信息丢失。
     * 每个数字都需要计算包含该数字最大值和最小值，分别用两个数组存储，记为maxarr[]和minarr[]
     * 1.遍历数组nums
     * 2.1.计算a=maxarr[i-1]*nums[i],b=minarr[i-1]*nums[i],c=nums[i]
     * 2.2.maxarr[i]=max(a,b,c)，minarr[i]=min(a,b,c)
     * 2.3.ret = max(maxarr[i],ret)
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 76.53% of Java online submissions for Maximum Product Subarray.
     * Memory Usage: 45 MB, less than 38.03% of Java online submissions for Maximum Product Subarray.
     *
     * @param nums
     * @return
     */
    public static int maxProduct_1(int[] nums) {
        int ret = Integer.MIN_VALUE;
        int[] maxarr = new int[nums.length + 1];
        int[] minarr = new int[nums.length + 1];
        maxarr[0] = 1;
        minarr[0] = 1;
        int a, b;
        for (int i = 0; i < nums.length; i++) {
            a = maxarr[i] * nums[i];
            b = minarr[i] * nums[i];
            maxarr[i + 1] = Math.max(nums[i], Math.max(a, b));
            minarr[i + 1] = Math.min(nums[i], Math.min(a, b));
            ret = Math.max(ret, maxarr[i + 1]);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 3, -2, 4}, 6);
        do_func(new int[]{-2, 0, -1}, 0);
        do_func(new int[]{1}, 1);
        do_func(new int[]{-1, -2, -3, -4}, 24);
        do_func(new int[]{-1, -2, 2, -3, -4}, 48);
        do_func(new int[]{2, -1, -2, -3, -4}, 48);
        do_func(new int[]{2, -1, -2, 0, -3, -4}, 12);
        do_func(new int[]{3, -2, 4}, 4);
        do_func(new int[]{2, -5, -2, -4, 3}, 24);
        do_func(new int[]{-3}, -3);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxProduct(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
