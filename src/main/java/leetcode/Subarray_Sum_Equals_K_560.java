package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 560. Subarray Sum Equals K
 * Medium
 * ------------------------------------
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 10^4
 * -1000 <= nums[i] <= 1000
 * -10^7 <= k <= 10^7
 */
public class Subarray_Sum_Equals_K_560 {
    public static int subarraySum(int[] nums, int k) {
        return subarraySum_1(nums, k);
    }

    /**
     * review 本题是"363. Max Sum of Rectangle No Larger Than K"的一个子集。
     * 参考"363. Max Sum of Rectangle No Larger Than K"中的maxSumSubmatrix_3()的思路
     *
     * Thinking：
     * 1.naive solution。
     * 双层遍历nums，依次计算区间[i,j]的和(其中，i<=j)，并判断是否等于k。时间复杂度O(N*N)
     * 2.prefix solution。
     * 题目为求区间[i,j)的和等于k的情况。
     * 假设:
     * sum[0]=0
     * sum[1]=sum[0]+nums[0]
     * sum[2]=sum[1]+nums[1]
     * 公式为：
     * sum[i]=sum[i-1]+nums[i-1]
     * 可以推导出：
     * sum[j]-sum[i]=nums[i]+..+nums[j-1]
     * 那么sum[j]-sum[i]就表示区间[i,j)的和。
     * 条件转化为：满足sum[j]-sum[i]=k的所有情况，且i<j。
     * 条件中有两个变量i和j，但是由于i<j。所以可以使用遍历的思路。
     * 我们采用枚举j的方法，并且由于i<j，意味着在枚举j时，i已经是已知的。
     * 枚举j时，等式sum[j]-sum[i]=k中，sum[j]是确定的，只需要查找是否有满足等式条件的sum[i]即可。所以sum[i]保存在哈希表中，用来优化查询性能。
     * 时间复杂度O(2N)
     *
     * 验证通过：
     * Runtime 33 ms Beats 29.55%
     * Memory 45.8 MB Beats 86.67%
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum_1(int[] nums, int k) {
        int res = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        counts.put(0, 1);
        int sum = 0;
        for (int n : nums) {
            sum += n;
            //判断是否存在满足条件的解
            if (counts.containsKey(sum - k)) {
                res += counts.get(sum - k);
            }
            counts.putIfAbsent(sum, 0);
            counts.put(sum, counts.get(sum) + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 1, 1}, 2, 2);
        do_func(new int[]{1, 2, 3}, 3, 2);
        do_func(new int[]{-2, 1, 2, 3, -1}, 3, 3);
    }

    private static void do_func(int[] nums, int k, int expected) {
        int ret = subarraySum(nums, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
