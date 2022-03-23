package leetcode;

/**
 * 121. Best Time to Buy and Sell Stock
 * Easy
 * ------------------------------
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 *
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 * Constraints:
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^4
 */
public class Best_Time_to_Buy_and_Sell_Stock_121 {
    public static int maxProfit(int[] prices) {
        return maxProfit_1(prices);
    }

    /**
     * 已提交Accepted中耗时最短的解
     *
     * 参考思路：
     * https://leetcode.com/submissions/detail/664765170/ 中的耗时最短的解
     * https://leetcode.com/submissions/api/detail/121/java/1/
     *
     * 遍历数组
     * 直接跟最小价格minPrice比较：
     * 大于minPrice，更新收益；
     * 小于minPrice，更新minPrice。
     *
     * @param prices
     * @return
     */
    public int maxProfit_2(int[] prices) {
        int answer = 0;
        int length = prices.length;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }else {
                int i1 = prices[i] - minPrice;
                if (i1 > answer){
                    answer = i1;
                }
            }
        }
        return answer;
    }

    /**
     * 不可取的暴力法
     * profile[i]表示第i天买入能获得的最大利润，那么max(profile[])为所求。
     * profile[i] = max(price[j]-price[i])，其中i<j<len(price)
     * 时间复杂度O(N*N)
     * 显然不满足要求
     *
     * one pass法
     * 如果price上升，计算盈利，更新待返回的最大盈利值；
     * 如果price下降，刷新最小price。
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 27.32% of Java online submissions for Best Time to Buy and Sell Stock.
     * Memory Usage: 76.4 MB, less than 76.27% of Java online submissions for Best Time to Buy and Sell Stock.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_1(int[] prices) {
        int max = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] < prices[i]) {
                max = Math.max(max, prices[i] - min);
            } else {
                min = Math.min(prices[i], min);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        do_func(new int[]{7, 1, 5, 3, 6, 4}, 5);
        do_func(new int[]{7, 6, 4, 3, 1}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxProfit(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
