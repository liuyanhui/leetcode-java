package leetcode;

/**
 * 309. Best Time to Buy and Sell Stock with Cooldown
 * Medium
 * ------------------
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
 * After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [1,2,3,0,2]
 * Output: 3
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 *
 * Example 2:
 * Input: prices = [1]
 * Output: 0
 *
 * Constraints:
 * 1 <= prices.length <= 5000
 * 0 <= prices[i] <= 1000
 */
public class Best_Time_to_Buy_and_Sell_Stock_with_Cooldown_309 {
    public static int maxProfit(int[] prices) {
        return maxProfit_2(prices);
    }

    /**
     * maxProfit_1的简化版本，优化了Space Complexity
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions .
     * Memory Usage: 37.3 MB, less than 35.26% of Java online submissions.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_2(int[] prices) {
        int rest = 0, bought = 0, selled = 0;
        bought = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            int t0 = Math.max(rest, selled);
            int t1 = Math.max(bought, rest - prices[i]);
            int t2 = bought + prices[i];
            rest = t0;
            bought = t1;
            selled = t2;
        }
        return Math.max(rest, selled);
    }

    /**
     * 参考思路:
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)
     *
     * 状态机的抽象是关键
     *
     * @param prices
     * @return
     */
    public static int maxProfit_1(int[] prices) {
        int[] s0 = new int[prices.length];
        int[] s1 = new int[prices.length];
        int[] s2 = new int[prices.length];
        s1[0] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            s0[i] = Math.max(s0[i - 1], s2[i - 1]);
            s1[i] = Math.max(s1[i - 1], s0[i - 1] - prices[i]);
            s2[i] = s1[i - 1] + prices[i];
        }
        return Math.max(s0[prices.length - 1], s2[prices.length - 1]);
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 0, 2}, 3);
        do_func(new int[]{1}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxProfit(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
