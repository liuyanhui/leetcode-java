package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 188. Best Time to Buy and Sell Stock IV
 * Hard
 * -------------------------
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 * Find the maximum profit you can achieve. You may complete at most k transactions.
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 * <p>
 * Example 1:
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 * <p>
 * Example 2:
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * <p>
 * Constraints:
 * 0 <= k <= 100
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 */
public class Best_Time_to_Buy_and_Sell_Stock_IV_188 {
    public static int maxProfit(int k, int[] prices) {
        return maxProfit_r3_1(k, prices);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     *
     * Thinking
     * 1. 两种思路：k从小到大计算对应结果；或者把prices数组分割成k份。即：要么从k入手降低复杂度；要么从prices数组入手简化。
     * 2. p[0:i]区间内j次，p[i+1:~]区间内k-j次
     * 3. 递归法
     * F(k,p[])=max(F(1,p[0:i])+F(k-1,p[i+1:~])), 1<i<len(p[])
     * 先计算出买卖1次的解，然后递归。
     *
     * 验证失败：Time Limit Exceed 。逻辑正确。
     *
     * maxProfit_2()是AC的方法
     *
     * @param k
     * @param prices
     * @return
     */
    public static int maxProfit_r3_1(int k, int[] prices) {
        if (k <= 0 || prices.length == 0) return 0;
        return helper(k, 0, prices.length - 1, prices, new HashMap<>());
    }

    private static int helper(int k, int beg, int end, int[] prices, Map<String, Integer> cache) {
        if (k <= 0 || beg >= end || end > prices.length - 1) return 0;
        String key = k + "." + beg + "." + end;
        if (cache.containsKey(key)) return cache.get(key);
        int ret = 0;
        if (k == 1) {//只买卖1次时的情况
            int buy = beg;
            int sell = beg + 1;
            while (sell <= end) {
                if (prices[buy] < prices[sell]) {
                    ret = Math.max(ret, prices[sell] - prices[buy]);
                } else {
                    buy = sell;
                }
                sell++;
            }
        } else {//买卖对于1次的情况
            for (int i = beg + 1; i <= end; i++) {
                int left = helper(1, beg, i, prices, cache);//先计算买卖1次的情况
                int right = helper(k - 1, i + 1, end, prices, cache);//计算剩余次数
                ret = Math.max(ret, left + right);
            }
        }
        cache.put(key, ret);
        return ret;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/54117/Clean-Java-DP-solution-with-comment
     * <p>
     * maxProfit_1()的优化版
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 90.38% of Java online submissions for Best Time to Buy and Sell Stock IV.
     * Memory Usage: 42.4 MB, less than 61.29% of Java online submissions for Best Time to Buy and Sell Stock IV.
     *
     * @param k
     * @param prices
     * @return
     */
    public static int maxProfit_2(int k, int[] prices) {
        if (k > prices.length / 2) {
            int ret = 0;
            //价格上升时，计算收益；价格下降时，跳过
            for (int i = 1; i < prices.length; i++) {
                if (prices[i - 1] < prices[i]) {
                    ret += prices[i] - prices[i - 1];
                }
            }
            return ret;
        }

        int[][] dp = new int[prices.length + 1][k + 1];
        //注意：为了跟“思考”的内容匹配，这里的循环j在外层，i在内层
        for (int j = 1; j <= k; j++) {
            int t = Integer.MIN_VALUE;
            for (int i = 1; i <= prices.length; i++) {
                t = Math.max(t, dp[i - 1][j - 1] - prices[i - 1]);
                dp[i][j] = Math.max(dp[i - 1][j], prices[i - 1] + t);
            }
        }
        return dp[prices.length][k];
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/54117/Clean-Java-DP-solution-with-comment
     * <p>
     * 思考：
     * 1.直觉，这是DP问题。因为后面步骤的最优解依赖于前面步骤的选择；存在局部最优解和全局最优解。
     * 2.DP公式
     * dp[i,j] 第i天最多交易j次的最大收益。
     * dp[i,j]=max(dp[i-1,j], max(prices[i]-prices[ii]+dp[ii,j-1])) , 0<=ii<i
     * 简化后的公式如下：
     * dp[i,j]=max(dp[i-1,j], prices[i]+max(dp[ii,j-1]-prices[ii])) , 0<=ii<i
     * 3.特殊用例
     * 当k>prices.length/2时，需要单独处理。因为可以支持每天都买卖。这样直接低买高卖即可，全部在全局最优解的解空间中。
     * <p>
     * 性能较差
     * Time Complexity:O(N*N*N)
     * 可以优化为O(N*N)
     * <p>
     * 验证通过：
     * Runtime: 94 ms, faster than 10.31% of Java online submissions for Best Time to Buy and Sell Stock IV.
     * Memory Usage: 42.1 MB, less than 78.28% of Java online submissions for Best Time to Buy and Sell Stock IV.
     *
     * @param k
     * @param prices
     * @return
     */
    public static int maxProfit_1(int k, int[] prices) {

        if (k > prices.length / 2) {
            int ret = 0;
            //价格上升时，计算收益；价格下降时，跳过
            for (int i = 1; i < prices.length; i++) {
                if (prices[i - 1] < prices[i]) {
                    ret += prices[i] - prices[i - 1];
                }
            }
            return ret;
        }

        int[][] dp = new int[prices.length + 1][k + 1];
        //注意：为了跟“思考”的内容匹配，这里的循环j在外层，i在内层
        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= prices.length; i++) {
                int t = Integer.MIN_VALUE;
                //FIXME:如果仅仅利用dp[][]，那么题目难度是medium。本题还增加了内部的循环计算，就是hard难度了。
                //FIXME:下面的代码可以优化，原因：
                //  1.第3层循环只跟ii有关，j-1在第2层循环中是固定的。
                //  2.第2层循环的i是递增的，ii在0~i的范围内。第3层循环是重复计算。
                for (int ii = 0; ii < i; ii++) {
                    t = Math.max(t, dp[ii][j - 1] - prices[ii]);
                }
                dp[i][j] = Math.max(dp[i - 1][j], prices[i - 1] + t);//review：把"prices[i - 1]+"放回到循环中方便理解，更直观一些
            }
        }
        return dp[prices.length][k];
    }

    public static void main(String[] args) {
        do_func(2, new int[]{2, 4, 1}, 2);
        do_func(2, new int[]{3, 2, 6, 5, 0, 3}, 7);
        do_func(2, new int[]{1, 2, 4}, 3);
        do_func(29, new int[]{70, 4, 83, 56, 94, 72, 78, 43, 2, 86, 65, 100, 94, 56, 41, 66, 3, 33, 10, 3, 45, 94, 15, 12, 78, 60, 58, 0, 58, 15, 21, 7, 11, 41, 12, 96, 83, 77, 47, 62, 27, 19, 40, 63, 30, 4, 77, 52, 17, 57, 21, 66, 63, 29, 51, 40, 37, 6, 44, 42, 92, 16, 64, 33, 31, 51, 36, 0, 29, 95, 92, 35, 66, 91, 19, 21, 100, 95, 40, 61, 15, 83, 31, 55, 59, 84, 21, 99, 45, 64, 90, 25, 40, 6, 41, 5, 25, 52, 59, 61, 51, 37, 92, 90, 20, 20, 96, 66, 79, 28, 83, 60, 91, 30, 52, 55, 1, 99, 8, 68, 14, 84, 59, 5, 34, 93, 25, 10, 93, 21, 35, 66, 88, 20, 97, 25, 63, 80, 20, 86, 33, 53, 43, 86, 53, 55, 61, 77, 9, 2, 56, 78, 43, 19, 68, 69, 49, 1, 6, 5, 82, 46, 24, 33, 85, 24, 56, 51, 45, 100, 94, 26, 15, 33, 35, 59, 25, 65, 32, 26, 93, 73, 0, 40, 92, 56, 76, 18, 2, 45, 64, 66, 64, 39, 77, 1, 55, 90, 10, 27, 85, 40, 95, 78, 39, 40, 62, 30, 12, 57, 84, 95, 86, 57, 41, 52, 77, 17, 9, 15, 33, 17, 68, 63, 59, 40, 5, 63, 30, 86, 57, 5, 55, 47, 0, 92, 95, 100, 25, 79, 84, 93, 83, 93, 18, 20, 32, 63, 65, 56, 68, 7, 31, 100, 88, 93, 11, 43, 20, 13, 54, 34, 29, 90, 50, 24, 13, 44, 89, 57, 65, 95, 58, 32, 67, 38, 2, 41, 4, 63, 56, 88, 39, 57, 10, 1, 97, 98, 25, 45, 96, 35, 22, 0, 37, 74, 98, 14, 37, 77, 54, 40, 17, 9, 28, 83, 13, 92, 3, 8, 60, 52, 64, 8, 87, 77, 96, 70, 61, 3, 96, 83, 56, 5, 99, 81, 94, 3, 38, 91, 55, 83, 15, 30, 39, 54, 79, 55, 86, 85, 32, 27, 20, 74, 91, 99, 100, 46, 69, 77, 34, 97, 0, 50, 51, 21, 12, 3, 84, 84, 48, 69, 94, 28, 64, 36, 70, 34, 70, 11, 89, 58, 6, 90, 86, 4, 97, 63, 10, 37, 48, 68, 30, 29, 53, 4, 91, 7, 56, 63, 22, 93, 69, 93, 1, 85, 11, 20, 41, 36, 66, 67, 57, 76, 85, 37, 80, 99, 63, 23, 71, 11, 73, 41, 48, 54, 61, 49, 91, 97, 60, 38, 99, 8, 17, 2, 5, 56, 3, 69, 90, 62, 75, 76, 55, 71, 83, 34, 2, 36, 56, 40, 15, 62, 39, 78, 7, 37, 58, 22, 64, 59, 80, 16, 2, 34, 83, 43, 40, 39, 38, 35, 89, 72, 56, 77, 78, 14, 45, 0, 57, 32, 82, 93, 96, 3, 51, 27, 36, 38, 1, 19, 66, 98, 93, 91, 18, 95, 93, 39, 12, 40, 73, 100, 17, 72, 93, 25, 35, 45, 91, 78, 13, 97, 56, 40, 69, 86, 69, 99, 4, 36, 36, 82, 35, 52, 12, 46, 74, 57, 65, 91, 51, 41, 42, 17, 78, 49, 75, 9, 23, 65, 44, 47, 93, 84, 70, 19, 22, 57, 27, 84, 57, 85, 2, 61, 17, 90, 34, 49, 74, 64, 46, 61, 0, 28, 57, 78, 75, 31, 27, 24, 10, 93, 34, 19, 75, 53, 17, 26, 2, 41, 89, 79, 37, 14, 93, 55, 74, 11, 77, 60, 61, 2, 68, 0, 15, 12, 47, 12, 48, 57, 73, 17, 18, 11, 83, 38, 5, 36, 53, 94, 40, 48, 81, 53, 32, 53, 12, 21, 90, 100, 32, 29, 94, 92, 83, 80, 36, 73, 59, 61, 43, 100, 36, 71, 89, 9, 24, 56, 7, 48, 34, 58, 0, 43, 34, 18, 1, 29, 97, 70, 92, 88, 0, 48, 51, 53, 0, 50, 21, 91, 23, 34, 49, 19, 17, 9, 23, 43, 87, 72, 39, 17, 17, 97, 14, 29, 4, 10, 84, 10, 33, 100, 86, 43, 20, 22, 58, 90, 70, 48, 23, 75, 4, 66, 97, 95, 1, 80, 24, 43, 97, 15, 38, 53, 55, 86, 63, 40, 7, 26, 60, 95, 12, 98, 15, 95, 71, 86, 46, 33, 68, 32, 86, 89, 18, 88, 97, 32, 42, 5, 57, 13, 1, 23, 34, 37, 13, 65, 13, 47, 55, 85, 37, 57, 14, 89, 94, 57, 13, 6, 98, 47, 52, 51, 19, 99, 42, 1, 19, 74, 60, 8, 48, 28, 65, 6, 12, 57, 49, 27, 95, 1, 2, 10, 25, 49, 68, 57, 32, 99, 24, 19, 25, 32, 89, 88, 73, 96, 57, 14, 65, 34, 8, 82, 9, 94, 91, 19, 53, 61, 70, 54, 4, 66, 26, 8, 63, 62, 9, 20, 42, 17, 52, 97, 51, 53, 19, 48, 76, 40, 80, 6, 1, 89, 52, 70, 38, 95, 62, 24, 88, 64, 42, 61, 6, 50, 91, 87, 69, 13, 58, 43, 98, 19, 94, 65, 56, 72, 20, 72, 92, 85, 58, 46, 67, 2, 23, 88, 58, 25, 88, 18, 92, 46, 15, 18, 37, 9, 90, 2, 38, 0, 16, 86, 44, 69, 71, 70, 30, 38, 17, 69, 69, 80, 73, 79, 56, 17, 95, 12, 37, 43, 5, 5, 6, 42, 16, 44, 22, 62, 37, 86, 8, 51, 73, 46, 44, 15, 98, 54, 22, 47, 28, 11, 75, 52, 49, 38, 84, 55, 3, 69, 100, 54, 66, 6, 23, 98, 22, 99, 21, 74, 75, 33, 67, 8, 80, 90, 23, 46, 93, 69, 85, 46, 87, 76, 93, 38, 77, 37, 72, 35, 3, 82, 11, 67, 46, 53, 29, 60, 33, 12, 62, 23, 27, 72, 35, 63, 68, 14, 35, 27, 98, 94, 65, 3, 13, 48, 83, 27, 84, 86, 49, 31, 63, 40, 12, 34, 79, 61, 47, 29, 33, 52, 100, 85, 38, 24, 1, 16, 62, 89, 36, 74, 9, 49, 62, 89}, 2818);
        System.out.println("-------Done-------");
    }

    private static void do_func(int k, int[] nums, int expected) {
        int ret = maxProfit(k, nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
