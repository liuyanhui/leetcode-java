package leetcode;

/**
 * 123. Best Time to Buy and Sell Stock III
 * Hard
 * ------------------------
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * Find the maximum profit you can achieve. You may complete at most two transactions.
 * Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 * Example 1:
 * Input: prices = [3,3,5,0,0,3,1,4]
 * Output: 6
 * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
 *
 * Example 2:
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.
 *
 * Example 3:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 * Constraints:
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^5
 */
public class Best_Time_to_Buy_and_Sell_Stock_III_123 {
    public static int maxProfit(int[] prices) {
        return maxProfit_1(prices);
    }

    /**
     * 以下三种思路是思考过程。
     * 思路1：
     * 二维数组，profile[i][j]表示第i天买入第j天卖出的收益。profile[i][len]表示第i天买入（只买卖一次）可获益的最大值。
     * 在profile[][]中找到满足先后顺序的两个数字和的最大值即可。
     * 遍历profile数组，返回值为：
     * max(profile[i][j]+max(profile[j+1][len],profile[j+2][len],..,profile[len-1][len]))
     * 时间复杂度:O(N*N)
     *
     * 思路2：
     * 二维数组，profile[i][j]表示第i天买入第j天卖出的收益。
     * 在profile[][]中找到满足先后顺序的两个数字和的最大值即可。
     * 时间复杂度:O(N*N)
     *
     * 思路3：
     * 分治法+DP
     * 分为左右两部分，左半部分的最大收益+右半部分的最大收益就是局部最优解。
     * price[]被分割成两部分[0:i]和[i+1:~]
     * 设L(prices[0:i])是从第0天到第i天只买卖一次的最大收益
     * 设R(prices[i:])是从第i天到最后一天只买卖一次的最大收益
     * 那么L(prices[0:i])+R(prices[i+1:])就是以i为分割点的局部最优解。
     * 所有局部最优解的最大值就是全局最优解。
     * 时间复杂度O(N)
     *
     * 思路3是可行的方案
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 69.34% of Java
     * Memory Usage: 51.1 MB, less than 97.28% of Java
     * @param prices
     * @return
     */
    public static int maxProfit_1(int[] prices) {
        int ret = 0;
        int[] L = new int[prices.length];
        int[] R = new int[prices.length];
        //计算从第0天到第i天只买卖一次的最大收益
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            //递增时更新收益，递减时更新最小值
            if (prices[i - 1] < prices[i]) {
                L[i] = Math.max(L[i - 1], prices[i] - min);
            } else {
                min = Math.min(min, prices[i]);
                L[i] = L[i - 1];
            }
        }
        //计算从第i天到最后已天只买卖一次的最大收益
        int max = prices[prices.length - 1];
        for (int i = prices.length - 2; i >= 0; i--) {
            //递减时更新收益；递增时更新最小值
            if (prices[i] < prices[i + 1]) {
                R[i] = Math.max(R[i + 1], max - prices[i]);
            } else {
                max = Math.max(max, prices[i]);
                R[i] = R[i + 1];
            }
        }
        //合并
        for (int i = 0; i < prices.length; i++) {
            ret = Math.max(L[i] + R[i], ret);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 3, 5, 0, 0, 3, 1, 4}, 6);
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
