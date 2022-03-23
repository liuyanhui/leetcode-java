package leetcode;

/**
 * 122. Best Time to Buy and Sell Stock II
 * Medium
 * -----------------------------
 * You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
 * On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.
 * Find and return the maximum profit you can achieve.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 * Total profit is 4 + 3 = 7.
 *
 * Example 2:
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Total profit is 4.
 *
 * Example 3:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
 *
 * Constraints:
 * 1 <= prices.length <= 3 * 10^4
 * 0 <= prices[i] <= 10^4
 */
public class Best_Time_to_Buy_and_Sell_Stock_II_122 {
    public static int maxProfit(int[] prices) {
        return maxProfit_2(prices);
    }

    /**
     * 代码精简版
     *
     * 思路：
     * 价格上升，计算收益，并更新买入价格为当前价格。
     * 价格下跌，更新买入价格为当前价格
     *
     * 简化后的思路：
     * 价格上升，计算当天-昨天的收益
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 31.99% of Java online submissions for Best Time to Buy and Sell Stock II.
     * Memory Usage: 43.9 MB, less than 65.99% of Java online submissions for Best Time to Buy and Sell Stock II.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_2(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] < prices[i]) {
                max += prices[i] - prices[i - 1];
            }
        }
        return max;
    }

    /**
     * 由于可以多次交易，并且可以预知将来的价格，总体思路是低买高卖。即在拐点买卖，价格由将转升时买，由升转降时卖，其他时间不动。
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 31.99% of Java
     * Memory Usage: 43.8 MB, less than 65.99% of Java
     *
     * @param prices
     * @return
     */
    public static int maxProfit_1(int[] prices) {
        int max = 0;
        int buy = 0;
        int rise = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] < prices[i]) {
                if (rise == 0 || rise < 0) {
                    buy = prices[i - 1];
                }
                rise = 1;
            } else {
                if (rise > 0) {
                    max += prices[i - 1] - buy;
                }
                rise = -1;
            }
        }
        if (rise == 1) {
            max += prices[prices.length - 1] - buy;
        }
        return max;
    }

    public static void main(String[] args) {
        do_func(new int[]{7, 1, 5, 3, 6, 4}, 7);
        do_func(new int[]{1, 2, 3, 4, 5}, 4);
        do_func(new int[]{7, 6, 4, 3, 1}, 0);

    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxProfit(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
