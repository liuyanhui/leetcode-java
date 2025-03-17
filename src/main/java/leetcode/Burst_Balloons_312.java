package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 312. Burst Balloons
 * Hard
 * ---------------------------------
 * You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.
 * <p>
 * If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
 * <p>
 * Return the maximum coins you can collect by bursting the balloons wisely.
 * <p>
 * Example 1:
 * Input: nums = [3,1,5,8]
 * Output: 167
 * Explanation:
 * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 * <p>
 * Example 2:
 * Input: nums = [1,5]
 * Output: 10
 * <p>
 * Constraints:
 * n == nums.length
 * 1 <= n <= 300
 * 0 <= nums[i] <= 100
 */
public class Burst_Balloons_312 {
    public static int maxCoins(int[] nums) {
        return maxCoins_r3_1(nums);
    }

    /**
     * 验证通过：
     * Runtime 107 ms Beats 47.61%
     * Memory 42.70 MB Beats 66.79%
     *
     * @param nums
     * @return
     */
    public static int maxCoins_r3_1(int[] nums) {
        //扩大数组，长度+2，新数组头尾都是1
        int[] newArr = new int[nums.length + 2];
        newArr[0] = 1;
        newArr[newArr.length - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            newArr[i + 1] = nums[i];
        }
        //定义缓存，用于加速计算
        int[][] cache = new int[newArr.length][newArr.length];
        //递归计算
        helper_r3_1(newArr, 1, newArr.length - 2, cache);
        return cache[1][newArr.length - 2];
    }

    private static int helper_r3_1(int[] nums, int beg, int end, int[][] cache) {
        if (beg > end) return 0;
        if (cache[beg][end] > 0) return cache[beg][end];

        int res = 0;
        for (int i = beg; i <= end; i++) {
            res = Math.max(res,
                    helper_r3_1(nums, beg, i - 1, cache)
                            + helper_r3_1(nums, i + 1, end, cache)
                            + nums[beg - 1] * nums[i] * nums[end + 1]);
        }
        cache[beg][end] = res;
        return res;
    }

    /**
     * review
     * 参考文档：
     * https://leetcode.com/problems/burst-balloons/solutions/76228/share-some-analysis-and-explanations/
     * <p>
     * DP 思路，在递归思路基础上而来。毕竟递归是自顶向下，DP是自底向上。
     * 需要根据递归算法推导出DP过程。从maxCoins_2的递归发可以得出，最后一层递归是1个气球burst，第二层是2个气球burst...
     * 那么DP需要模拟这个过程。即先计算1个气球burst，计算2个气球burst，计算3个气球burst....， 计算n个气球burst
     * <p>
     * 验证通过：
     * Runtime 47 ms Beats 78.22%
     * Memory 42.4 MB Beats 45.75%
     *
     * @param nums
     * @return
     */
    public static int maxCoins_3(int[] nums) {
        // 创建一个辅助数组，并在首尾各添加1，方便处理边界情况
        // 有了这个辅助数据，DP使用开区间的方式保存数据
        int[] temp = new int[nums.length + 2];
        int t = 1;
        temp[0] = 1;
        temp[temp.length - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            temp[t++] = nums[i];
        }
        //dp[i][j]为开区间方式
        int[][] dp = new int[temp.length][temp.length];
        //每次计算的开区间的长度
        for (int len = 3; len <= temp.length; len++) {
            for (int left = 0; left <= temp.length - len; left++) {
                int right = left + len - 1;
                //开区间内进行计算
                for (int i = left + 1; i <= right - 1; i++) {
                    //要注意下面的"temp[left] * temp[i] * temp[right]"，不能写成"temp[i-1] * temp[i] * temp[i+1]"
                    dp[left][right] = Math.max(dp[left][right],
                            temp[left] * temp[i] * temp[right] + dp[left][i] + dp[i][right]);
                }
            }
        }

        return dp[0][temp.length - 1];
    }

    /**
     * review
     * 参考文档：
     * https://leetcode.com/problems/burst-balloons/solutions/76228/share-some-analysis-and-explanations/
     * <p>
     * Divide and Conquer 算法：
     * 1.采用分治法Divide and Conquer
     * 2.对每一个nums[i]，要么最先选，要么最后选。如果最先选，因为nums[i]最先选了之后，nums还是一个数组，无法使用分治法。所以nums[i]只能最后选。换一个角度，如果nums[i]最先选，那么会影响nums[i-1]和nums[i+1]就变成相邻的了，按现有分治法的思路就会丢失这种情况。简单来说如果最先选nums[i]会对其他分治的结果产生影响。
     * 3.分治+缓存法
     * <p>
     * 验证通过：
     * Runtime 130 ms Beats 39.36%
     * Memory 42.8 MB Beats 19.1%
     *
     * @param nums
     * @return
     */
    public static int maxCoins_2(int[] nums) {
        int[] newNums = new int[nums.length + 2];
        int t = 1;
        //为了避免后续边界值的校验，提前在边界扩位
        for (int i = 0; i < nums.length; i++) {
            newNums[t++] = nums[i];
        }
        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;
        int[][] cache = new int[newNums.length][newNums.length];
        helper_2(newNums, 1, newNums.length - 2, cache);
        return cache[1][newNums.length - 2];
    }

    private static int helper_2(int[] nums, int beg, int end, int[][] cache) {
        if (beg > end) return 0;
        if (cache[beg][end] > 0) return cache[beg][end];
        int res = 0;
        for (int i = beg; i <= end; i++) {
            res = Math.max(res, helper_2(nums, beg, i - 1, cache) + nums[beg - 1] * nums[i] * nums[end + 1] + helper_2(nums, i + 1, end, cache));
        }
        cache[beg][end] = res;
        return res;
    }

    /**
     * 思路：
     * 1.暴力法+递归，时间复杂度O(N!)，公式为N*F(N-1)=N*(N-1)*(N-2)*..*1。
     * 2.递归法可以使用缓存优化时间复杂度，也可以转化成BP问题。
     * <p>
     * 验证失败：Time Exceed Limit
     *
     * @param nums
     * @return
     */
    public static int maxCoins_1(int[] nums) {
        Map<String, Integer> cached = new HashMap<>();
        return helper(nums, cached);
    }

    private static int helper(int[] nums, Map<String, Integer> cached) {
        if (nums.length == 0) return 0;
        String key = getKey(nums);
        if (cached.containsKey(key)) return cached.get(key);
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int a = i == 0 ? 1 : nums[i - 1];
            int b = i == nums.length - 1 ? 1 : nums[i + 1];
            int s = a * nums[i] * b;
            int[] newArr = getNewArray(nums, i);
            s += helper(newArr, cached);
            res = Math.max(res, s);
        }
        cached.put(key, res);
        return res;
    }

    private static int[] getNewArray(int[] nums, int exclude) {
        int[] res = new int[nums.length - 1];
        int signal = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == exclude) signal = 1;
            else res[i - signal] = nums[i];
        }
        return res;
    }

    private static String getKey(int[] nums) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            res.append(nums[i] + ",");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 1, 5, 8}, 167);
        do_func(new int[]{1, 5}, 10);
        do_func(new int[]{31, 12, 5, 8, 45, 6, 2, 3, 67, 33, 2, 4, 5, 6, 3, 91}, 667865);
        do_func(new int[]{8, 2, 6, 8, 9, 8, 1, 4, 1, 5, 3, 0, 7, 7, 0, 4, 2, 2, 5}, 3630);
        do_func(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 32);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxCoins(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}

