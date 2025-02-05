package leetcode;

/**
 * 292. Nim Game
 * Easy
 * --------------------------------------
 * You are playing the following Nim Game with your friend:
 *  - Initially, there is a heap of stones on the table.
 *  - You and your friend will alternate taking turns, and you go first.
 *  - On each turn, the person whose turn it is will remove 1 to 3 stones from the heap.
 *  - The one who removes the last stone is the winner.
 *
 * Given n, the number of stones in the heap, return true if you can win the game assuming both you and your friend play optimally, otherwise return false.
 *
 * Example 1:
 * Input: n = 4
 * Output: false
 * Explanation: These are the possible outcomes:
 * 1. You remove 1 stone. Your friend removes 3 stones, including the last stone. Your friend wins.
 * 2. You remove 2 stones. Your friend removes 2 stones, including the last stone. Your friend wins.
 * 3. You remove 3 stones. Your friend removes the last stone. Your friend wins.
 * In all outcomes, your friend wins.
 *
 * Example 2:
 * Input: n = 1
 * Output: true
 *
 * Example 3:
 * Input: n = 2
 * Output: true
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 */
public class Nim_Game_292 {
    public static boolean canWinNim(int n) {
        return canWinNim_4(n);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * 虽然DP思路可以实现，但是 "return n % 4 != 0" 是最优方案。这里的思路很巧妙。
     *
     */
    /**
     * round 2
     * @param n
     * @return
     */
    public static boolean canWinNim_4(int n) {
        return n % 4 != 0;
    }

    /**
     * round 2：
     *
     * DP问题.dp[i]=!dp[i-1] || !dp[i-2] || !dp[i-3]
     *
     * 规律如下：
     * 1 win
     * 2 win
     * 3 win
     * 4 lose
     * 5 win
     * 6 win
     * 7 win
     * 8 lose
     * 9 win
     * 通过规律可知4的倍数返回false，其他返回true
     *
     * @param n
     * @return
     */
    public static boolean canWinNim_3(int n) {
        if (n <= 3) return true;
        boolean w1 = true, w2 = true, w3 = true;
        boolean res = true;
        // 增加条件i>0，是为防止i溢出
        for (int i = 4; i <= n && i > 0; i++) {
            res = !w1 || !w2 || !w3;
            w1 = w2;
            w2 = w3;
            w3 = res;
        }
        return res;
    }

    /**
     * 通过规律发现，4的倍数结果为false，其他结果为true
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Nim Game.
     * Memory Usage: 37.8 MB, less than 5.59% of Java online submissions for Nim Game.
     *
     * @param n
     * @return
     */
    public static boolean canWinNim_2(int n) {
        return n % 4 != 0;
    }

    /**
     * dp思路，公式为：
     * if dp[n-1]&&dp[n-2]&&dp[n-3]=true then dp[n]=false
     * else dp[n]=true
     *
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @return
     */
    public static boolean canWinNim_1(int n) {
        if (n <= 3) return true;
        boolean[] dp = new boolean[4];
        dp[0] = true;
        dp[1] = true;
        dp[2] = true;
        for (int i = 3; i < n; i++) {
            if (dp[0] && dp[1] && dp[2]) {
                dp[3] = false;
            } else {
                dp[3] = true;
            }
            //roll dp
            dp[0] = dp[1];
            dp[1] = dp[2];
            dp[2] = dp[3];
        }
        return dp[3];
    }

    public static void main(String[] args) {
        do_func(4, false);
        do_func(1, true);
        do_func(2, true);
        do_func(5, true);
        do_func(8, false);
        do_func(Integer.MAX_VALUE, true);
        do_func(1348820612, false);
    }

    private static void do_func(int n, boolean expected) {
        boolean ret = canWinNim(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
