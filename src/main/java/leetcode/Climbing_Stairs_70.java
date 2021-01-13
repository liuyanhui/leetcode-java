package leetcode;

/**
 * https://leetcode.com/problems/climbing-stairs/
 * 70. Climbing Stairs
 * Easy
 * -----------------
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 *
 * Constraints:
 * 1 <= n <= 45
 */
public class Climbing_Stairs_70 {
    public static int climbStairs(int n) {
        return climbStairs_2(n);
    }

    /**
     * climbStairs_1的简化版
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
     * Memory Usage: 35.8 MB, less than 54.91% of Java online submissions for Climbing Stairs.
     * @param n
     * @return
     */
    public static int climbStairs_2(int n) {
        int dp1 = 1, dp2 = 1;
        for (int i = 1; i < n; i++) {
            int tmp = dp2;
            dp2 += dp1;
            dp1 = tmp;
        }
        return dp2;
    }

    /**
     * 最简单的DP问题
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
     * Memory Usage: 36 MB, less than 26.20% of Java online submissions for Climbing Stairs.
     * @param n
     * @return
     */
    public static int climbStairs_1(int n) {
        if (n < 2) return 1;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        do_func(1, 1);
        do_func(2, 2);
        do_func(3, 3);
        do_func(30, 1346269);
    }

    private static void do_func(int x, int expected) {
        int ret = climbStairs(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
