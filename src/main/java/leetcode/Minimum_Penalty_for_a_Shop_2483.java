package leetcode;

/**
 * 2483. Minimum Penalty for a Shop
 * Medium
 * -------------------------------
 * You are given the customer visit log of a shop represented by a 0-indexed string customers consisting only of characters 'N' and 'Y':
 * - if the ith character is 'Y', it means that customers come at the ith hour
 * - whereas 'N' indicates that no customers come at the ith hour.
 *
 * If the shop closes at the jth hour (0 <= j <= n), the penalty is calculated as follows:
 * - For every hour when the shop is open and no customers come, the penalty increases by 1.
 * - For every hour when the shop is closed and customers come, the penalty increases by 1.
 *
 * Return the earliest hour at which the shop must be closed to incur a minimum penalty.
 *
 * Note that if a shop closes at the jth hour, it means the shop is closed at the hour j.
 *
 * Example 1:
 * Input: customers = "YYNY"
 * Output: 2
 * Explanation:
 * - Closing the shop at the 0th hour incurs in 1+1+0+1 = 3 penalty.
 * - Closing the shop at the 1st hour incurs in 0+1+0+1 = 2 penalty.
 * - Closing the shop at the 2nd hour incurs in 0+0+0+1 = 1 penalty.
 * - Closing the shop at the 3rd hour incurs in 0+0+1+1 = 2 penalty.
 * - Closing the shop at the 4th hour incurs in 0+0+1+0 = 1 penalty.
 * Closing the shop at 2nd or 4th hour gives a minimum penalty. Since 2 is earlier, the optimal closing time is 2.
 *
 * Example 2:
 * Input: customers = "NNNNN"
 * Output: 0
 * Explanation: It is best to close the shop at the 0th hour as no customers arrive.
 *
 * Example 3:
 * Input: customers = "YYYY"
 * Output: 4
 * Explanation: It is best to close the shop at the 4th hour as customers arrive at each hour.
 *
 * Constraints:
 * 1 <= customers.length <= 10^5
 * customers consists only of characters 'Y' and 'N'.
 */
public class Minimum_Penalty_for_a_Shop_2483 {
    public static int bestClosingTime(String customers) {
        return bestClosingTime_1(customers);
    }

    /**
     * Thinking：
     * 1.native solution
     * 依次计算每天([0:n]共n+1天)关门的结果，然后查找最优解。
     * Time Complexity:O(N*N)
     * 2.DP solution
     * cnt_Y[i]为[0:i]的Y的数量
     * penalty[]保存每天的penalty结果
     * IF c[i]=='Y' THEN penalty[i]=(i-cnt_Y[i-1])+cnt_Y[len]-cnt_Y[i-1]
     * IF c[i]=='N' THEN penalty[i]=(i-cnt_Y[i-1])+cnt_Y[len]-cnt_Y[i-1]
     * 查找penalty[]第一个最小值。
     *
     * 验证通过：
     * Runtime 11 ms Beats 75.34%
     * Memory 44.3 MB Beats 44.59%
     *
     * 含有更好的方案：https://leetcode.com/problems/minimum-penalty-for-a-shop/editorial/
     * 1.two pass，跟下面的实现类似，但是更简单
     * 2.one pass，利用趋势一致就可以求出解，并设第0天从0penalty开始，然后利用趋势求解。
     *
     * @param customers
     * @return
     */
    public static int bestClosingTime_1(String customers) {
        //初始化
        int[] cnt_Y = new int[customers.length()];
        if (customers.charAt(0) == 'Y') {
            cnt_Y[0] = 1;
        }
        for (int i = 1; i < customers.length(); i++) {
            cnt_Y[i] = cnt_Y[i - 1];
            if (customers.charAt(i) == 'Y') {
                cnt_Y[i] += 1;
            }
        }
        //计算每天的penalty
        int[] penalty = new int[customers.length() + 1];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i <= customers.length(); i++) {
            if (i == 0) {//第一天
                penalty[0] = cnt_Y[cnt_Y.length - 1];
            } else if (i < customers.length()) {
                penalty[i] = (i - cnt_Y[i - 1]) + (cnt_Y[cnt_Y.length - 1] - cnt_Y[i - 1]);
            } else {//最后一天的后一天
                penalty[i] = cnt_Y.length - cnt_Y[cnt_Y.length - 1];
            }
            min = Math.min(min, penalty[i]);
        }
        //查找结果
        for (int i = 0; i <= penalty.length; i++) {
            if (min == penalty[i])
                return i;
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func("YYNY", 2);
        do_func("NNNNN", 0);
        do_func("YYYY", 4);
    }

    private static void do_func(String s, int expected) {
        int ret = bestClosingTime(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
