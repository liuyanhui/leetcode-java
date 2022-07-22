package leetcode;

/**
 * 188. Best Time to Buy and Sell Stock IV
 * Hard
 * -------------------------
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 * Find the maximum profit you can achieve. You may complete at most k transactions.
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 *
 * Example 2:
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *
 * Constraints:
 * 0 <= k <= 100
 * 0 <= prices.length <= 1000
 * 0 <= prices[i] <= 1000
 */
public class Best_Time_to_Buy_and_Sell_Stock_IV_188 {
    public static int maxProfit(int k, int[] prices) {
        return maxProfit_2(k, prices);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/54117/Clean-Java-DP-solution-with-comment
     *
     * maxProfit_1()的优化版
     *
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
     *
     * 思考：
     * 1.直觉，这是DP问题。因为后面步骤的最优解依赖于前面步骤的选择；存在局部最优解和全局最优解。
     * 2.DP公式
     * dp[i,j] 第i天最多交易j次的最大收益。
     * dp[i,j]=max(dp[i-1,j], max(prices[i]-prices[ii]+dp[ii,j-1])) , 0<=ii<i
     * 简化后的公式如下：
     * dp[i,j]=max(dp[i-1,j], prices[i]+max(dp[ii,j-1]-prices[ii])) , 0<=ii<i
     * 3.特殊用例
     * 当k>prices.length/2时，需要单独处理。因为可以支持每天都买卖。这样直接低买高卖即可，全部在全局最优解的解空间中。
     *
     * 性能较差
     * Time Complexity:O(N*N*N)
     * 可以优化为O(N*N)
     *
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
                dp[i][j] = Math.max(dp[i - 1][j], prices[i - 1] + t);
            }
        }
        return dp[prices.length][k];
    }

    public static void main(String[] args) {
        do_func(2, new int[]{2, 4, 1}, 2);
        do_func(2, new int[]{3, 2, 6, 5, 0, 3}, 7);
        do_func(2, new int[]{1, 2, 4}, 3);
    }

    private static void do_func(int k, int[] nums, int expected) {
        int ret = maxProfit(k, nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
