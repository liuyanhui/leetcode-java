package leetcode;

/**
 * 397. Integer Replacement
 * Medium
 * ------------------------
 * Given a positive integer n, you can apply one of the following operations:
 * If n is even, replace n with n / 2.
 * If n is odd, replace n with either n + 1 or n - 1.
 *
 * Return the minimum number of operations needed for n to become 1.
 *
 * Example 1:
 * Input: n = 8
 * Output: 3
 * Explanation: 8 -> 4 -> 2 -> 1
 *
 * Example 2:
 * Input: n = 7
 * Output: 4
 * Explanation: 7 -> 8 -> 4 -> 2 -> 1
 * or 7 -> 6 -> 3 -> 2 -> 1
 *
 * Example 3:
 * Input: n = 4
 * Output: 2
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 */
public class Integer_Replacement_397 {
    public static int integerReplacement(int n) {
        return integerReplacement_2(n);
    }

    /**
     * 递归+DP
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 35.52% of Java.
     * Memory Usage: 35.7 MB, less than 75.69% of Java.
     *
     * @param n
     * @return
     */
    public static int integerReplacement_2(int n) {
        if (n < 2) return 0;
        if (n == Integer.MAX_VALUE) return 32;//处理特殊情况
        if (n % 2 == 0) {
            return integerReplacement_2(n / 2) + 1;
        } else {
            return Math.min(integerReplacement_2((n + 1) / 2), integerReplacement_2((n - 1) / 2)) + 2;
        }
    }

    /**
     * 典型的DP问题。思路如下：
     * dp[0]=0,dp[1]=0
     * even : dp[i]=dp[i/2]+1
     * odd  :  dp[i]=min(dp[i+1],dp[i-1])+1 ==> i+1 or i-1 are even ==> dp[i]=min(dp[(i+1)/2]+1,dp[(i-1)/2]+1)+1
     *
     * 逻辑正确，但是n=Integer.MAX_VALUE时，报错。数组长度超出最大上限
     *
     * @param n
     * @return
     */
    public static int integerReplacement_1(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            if (i % 2 == 0) {
                dp[i] = dp[i / 2] + 1;
            } else {
                dp[i] = Math.min(dp[(i + 1) / 2], dp[(i - 1) / 2]) + 2;
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        do_func(8, 3);
        do_func(7, 4);
        do_func(4, 2);
        do_func(1, 0);
        do_func(100000, 21);
        do_func(Integer.MAX_VALUE, 32);
    }

    private static void do_func(int n, int expected) {
        int ret = integerReplacement(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
