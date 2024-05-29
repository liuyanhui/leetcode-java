package leetcode;

import java.util.Arrays;

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
    /**
     * 1.maxProfit_1()是一个单独的思路。
     *
     * 2.套路
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     * 从maxProfit_2()到maxProfit_6()是一个演变过程。
     * 最关键的是maxProfit_2()中的dp公式
     *
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        return maxProfit_r3_1(prices);
    }

    /**
     *
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. naive solution
     * 分别计算恰好买卖0次、恰好买卖1次和恰好买卖2次的情况，然后得到最大值。
     * 计算恰好买卖2次时，由于必须"you must sell the stock before you buy again"的约束，计算第2次买卖时，可以使用恰好买卖1次的算法。第2次买卖的起点在第1次卖出的下一个。
     * 2. 把输入区间划分为2部分，分别对应买卖2次的输入。
     * 第1次买卖区间为[0:i]
     * 第2次买卖区间为[i+1:~]
     * i=0或i=len(prices)-1 时表示买卖1次
     * 0<i<len(prices)-1 时表示买卖2次
     * 其余为买卖0次
     * 2.1. 只需要计算出[0:i]区间和[i+1:~]区间买卖1次的全部解，然后再找出最优的i就是恰好买卖2次的最优解
     * 2.2. 再与恰好买卖1次的最优解进行比较，取较大值
     * 2.3. 再与恰好买卖0次的解进行比较，取较大值
     * 2.4. 设dp1[i]为[0:i]区间买卖1次的最大收益，dp2[j]为[i+1:~]区间买卖1次的最大收益
     * 2.5. max(dp1[i]+dp2[i+1])记为所求
     * 3. 最多交易4次，日期分别为b1,s1,b2,s2，并且b1<s1<b2<s2。
     * 3.1. 在b1,s1,b2,s2都有值的情况下，表示已经计算的区间经过了1次和2次买卖的最优解计算。
     * 3.1.1
     * 当p[s2]<p[i]时，s2=i，计算局部最优解，并判断是否更新全局最优解。
     * 当p[i]<p[s2]时，表示在[i:~]将开展第2次买卖，需要重新计算[0:i]为一次买卖的最优解。从 p[s1]-p[b1]，p[s2]-p[b2]，p[s2]-p[b1] 中选最大值作为第1次买卖的买卖日期组合。并设置b2=i，s2=-1。
     *
     * 采用【2.】的方案
     *
     * 验证通过:
     * Runtime 3 ms Beats 86.44%
     * Memory 60.76 MB Beats 71.76%
     *
     * @param prices
     * @return
     */
    public static int maxProfit_r3_1(int[] prices) {
        int ret = 0;
        //计算[0:i]恰好买卖1次的最优解
        int[] dp1 = new int[prices.length];
        int buy = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[buy] < prices[i]) {//只能卖或者不卖，不能买
                dp1[i] = Math.max(dp1[i - 1], prices[i] - prices[buy]);
            } else {//只能买，不能卖
                buy = i;
                dp1[i] = dp1[i - 1];//总收益不变
            }
            ret = Math.max(ret, dp1[i]);
        }
        //计算[i:~]恰好买卖1次的最优解
        int[] dp2 = new int[prices.length];
        int sell = prices.length - 1;
        for (int i = prices.length - 2; i > 0; i--) {
            if (prices[i] < prices[sell]) {//只能买，不能卖
                dp2[i] = Math.max(dp2[i + 1], prices[sell] - prices[i]);
            } else {//只能卖，不能买
                sell = i;
                dp2[i] = dp2[i + 1];//总收益不变
            }
            ret = Math.max(ret, dp2[i]);
        }

        //组合dp1和dp2
        for (int i = 1; i < prices.length - 1; i++) {
            ret = Math.max(ret, dp1[i] + dp2[i + 1]);
        }

        return ret;
    }

    /**
     * 终极优化版，基于maxProfit_5()
     *
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java
     * Memory Usage: 58.6 MB, less than 83.70% of Java
     *
     * @param prices
     * @return
     */
    public static int maxProfit_6(int[] prices) {
        int buy1 = prices[0], buy2 = prices[0];
        int sell1 = 0, sell2 = 0;
        for (int i = 1; i < prices.length; i++) {
            buy1 = Math.min(buy1, prices[i]);
            sell1 = Math.max(sell1, prices[i] - buy1);
            //这里是关键的地方
            //prices[i] - sell1表示把第一次获得的收益合并到第2次买入的价格中。
            //对应maxProfit_5()中的第7行代码："min[k] = Math.min(min[k], prices[i] - dp[k - 1]);"
            buy2 = Math.min(buy2, prices[i] - sell1);
            sell2 = Math.max(sell2, prices[i] - buy2);
        }
        return sell2;
    }

    /**
     * maxProfit_4()的优化版。
     * 把DP数据从二维优化成一维。
     * 从new int[k][prices.length]改为new int[k]。
     *
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 69.36% of Java online submissions for Best Time to Buy and Sell Stock III.
     * Memory Usage: 76.3 MB, less than 75.06% of Java online submissions for Best Time to Buy and Sell Stock III.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_5(int[] prices) {
        int[] dp = new int[3];
        int[] min = new int[3];
        Arrays.fill(min, prices[0]);
        for (int i = 1; i < prices.length; i++) {
            for (int k = 1; k <= 2; k++) {
                min[k] = Math.min(min[k], prices[i] - dp[k - 1]);
                dp[k] = Math.max(dp[k], prices[i] - min[k]);
            }
        }
        return dp[2];
    }

    /**
     * maxProfit_3()的一个变种。交换两层循环。
     * 也是maxProfit_5()的基础，通过观察dp数组的使用情况，可以把dp优化成一维k长的数组
     *
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 79.77% of Java online submissions for Best Time to Buy and Sell Stock III.
     * Memory Usage: 57.8 MB, less than 89.23% of Java online submissions for Best Time to Buy and Sell Stock III.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_4(int[] prices) {
        int[][] dp = new int[3][prices.length];
        int[] min = new int[3];
        Arrays.fill(min, prices[0]);
        for (int i = 1; i < prices.length; i++) {
            for (int k = 1; k <= 2; k++) {
                min[k] = Math.min(min[k], prices[i] - dp[k - 1][i - 1]);
                dp[k][i] = Math.max(dp[k][i - 1], prices[i] - min[k]);
            }
        }
        return dp[2][prices.length - 1];
    }

    /**
     * maxProfit_2()的优化版
     * Time Complexity:O(K*N)
     *
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 59.78% of Java .
     * Memory Usage: 80 MB, less than 41.37% of Java .
     *
     * @param prices
     * @return
     */
    public static int maxProfit_3(int[] prices) {
        int[][] dp = new int[3][prices.length];
        for (int k = 1; k <= 2; k++) {
            int min = prices[0];
            for (int i = 1; i < prices.length; i++) {
                min = Math.min(min, prices[i] - dp[k - 1][i - 1]);
                dp[k][i] = Math.max(dp[k][i - 1], prices[i] - min);
            }
        }
        return dp[2][prices.length - 1];
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/135704/Detail-explanation-of-DP-solution
     *
     * DP思路
     * dp[k][i]表示第i天交易k次的最大收益。
     * dp[k][i]分为两种情况：第i天不交易；第i天交易。
     * dp[k][i]=max(dp[k][i-1],prices[i]-prices[j]+dp[k-1][j-1])，其中0<=j<i
     *
     * Time Complexity:O(k*N*N)
     *
     * 验证失败：Time Limit Exceeded.
     * 207 / 214 test cases passed.
     *
     * @param prices
     * @return
     */
    public static int maxProfit_2(int[] prices) {
        int[][] dp = new int[3][prices.length];
        for (int k = 1; k <= 2; k++) {
            for (int i = 1; i < prices.length; i++) {
                //FIXME 下面的max循环重复计算了，可以优化。
                //FIXME "prices[j] + dp[k - 1][j - 1]"这部分与i无关，并且是重复计算的。详见maxProfit_3()
                int max = prices[i] - prices[0];
                for (int j = 1; j < i; j++) {
                    max = Math.max(max, prices[i] - prices[j] + dp[k - 1][j - 1]);
                }
                dp[k][i] = Math.max(dp[k][i - 1], max);
            }
        }
        return dp[2][prices.length - 1];
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
     * 但是只能解决交易次数小于等于2次的情况。无法解决大于允许交易大于2次的情况。
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
        do_func(new int[]{2, 1, 2, 1, 0, 0, 1}, 2);
        System.out.println("------- THE END -------");

    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxProfit(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
