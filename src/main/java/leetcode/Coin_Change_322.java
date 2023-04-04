package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 322. Coin Change
 * Medium
 * --------------------------
 * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
 * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 * Example 4:
 * Input: coins = [1], amount = 1
 * Output: 1
 *
 * Example 5:
 * Input: coins = [1], amount = 2
 * Output: 2
 *
 * Constraints:
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 */
public class Coin_Change_322 {

    public static int coinChange(int[] coins, int amount) {
        return coinChange_1(coins, amount);
    }

    /**
     * review
     * round 2
     * Thinking：
     * 1.暴力法+递归法+Greedy。先排序coins；每次取最大的一个coin，然后递归；如果不满足条件，取第二大的coin。
     * 此方法行不通，它每次选取最大面值的coin，会影响最终结果，如：coins={1, 4, 5, 8}, amount=11的用例无法通过。
     *
     * 以下是某的马后炮思考：
     * 此问题转化为：
     * 存在等式ax+by+cz+...=amount成立，{x,y,z,..}为coins，本题为求{a,b,c,..}中的a+b+c+...的最小值。
     * 这里面有两类变量，{x,y,z,..}和amount，通过这两类变量求a+b+c+...的最小值。
     * 既然有两类变量，那么分别思考其中一种变量变化时，如何求解。
     * 如果采用{x,y,z,..}为主变量变化进行求解，那么影响因子太多（所有的xyz..都是因子，应为它们选择是有关联的），导致计算异常复杂。
     * 如果采用amount变化进行求解，那么只有2个影响因子（变化的amount和xyz中的一个），求解的难度降低很多。coinChange_1()就是这种思路。
     *
     * 所以有时候把貌似确定的结果作为一个变量，从小到大或从大到小推导，可以简化问题复杂度，缩小解空间。
     */

    /**
     * dp思路，dp[i]是amount=i时的最优解
     * dp[i]=min(dp[i-coins[j]])
     *
     * 参考：
     * https://leetcode.com/problems/coin-change/discuss/77360/C%2B%2B-O(n*amount)-time-O(amount)-space-DP-solution
     *
     * 验证通过：
     * Runtime: 11 ms, faster than 93.99% of Java online submissions for Coin Change.
     * Memory Usage: 38.1 MB, less than 85.25% of Java online submissions for Coin Change.
     *
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange_1(int[] coins, int amount) {
        if (amount == 0) return 0;
        int[] dp = new int[amount + 1];
        for (int i = 1; i < amount + 1; i++) {
            //下面的代码有问题，会导致后面dp[i - c] + 1的运行变成负数
            //dp[i] = Integer.MAX_VALUE;
            dp[i] = amount + 1;
        }
        for (int i = 1; i <= amount; i++) {
            for (int c : coins) {
                if (i - c >= 0) {
                    //如果dp[i]默认是Integer.MAX_VALUE，可能会导致后面dp[i - c] + 1变成负数
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        return dp[amount] < amount + 1 ? dp[amount] : -1;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 5}, 6, 2);
        do_func(new int[]{1, 2, 5}, 11, 3);
        do_func(new int[]{2}, 3, -1);
        do_func(new int[]{1}, 0, 0);
        do_func(new int[]{1}, 1, 1);
        do_func(new int[]{1}, 2, 2);
        do_func(new int[]{1, 4, 5, 8}, 11, 3);
        do_func(new int[]{1, 5, 6, 7, 8}, 12, 2);
        do_func(new int[]{83}, 84, -1);
        do_func(new int[]{186, 419, 83, 408}, 6249, 20);
    }

    private static void do_func(int[] coins, int amount, int expected) {
        int ret = coinChange(coins, amount);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
