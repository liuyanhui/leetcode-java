package leetcode;

/**
 * 343. Integer Break
 * Medium
 * -------------------------
 * Given an integer n, break it into the sum of k positive integers, where k >= 2, and maximize the product of those integers.
 * Return the maximum product you can get.
 *
 * Example 1:
 * Input: n = 2
 * Output: 1
 * Explanation: 2 = 1 + 1, 1 × 1 = 1.
 *
 * Example 2:
 * Input: n = 10
 * Output: 36
 * Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
 *
 * Constraints:
 * 2 <= n <= 58
 */
public class Integer_Break_343 {
    public static int integerBreak(int n) {
        return integerBreak_2(n);
    }

    /**
     * 数学分析法，参考思路：
     * https://leetcode.com/problems/integer-break/discuss/80689/A-simple-explanation-of-the-math-part-and-a-O(n)-solution
     *
     * @param n
     * @return
     */
    public static int integerBreak_2(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;
        int ret = 1;
        while (n > 4) {
            ret *= 3;
            n -= 3;
        }
        ret *= n;
        return ret;
    }

    /**
     * review round 2
     * round 2 : 忽略k的变化
     *
     * 总结规律之后，公式如下：
     * f(2)=max{1*max{1,f(1)}}
     * f(3)=max{1*max{2,f(2)},2*max{1,f(1)}}
     * f(4)=max{1*max{3,f(3)},2*max{2,f(2)},3*max{1,f(1)}}
     * f(n)=max{1*max{n-1,f(n-1)},2*max{n-2,f(n-2)},...,(n-1)*max{1,f(1)}}
     *
     * 迭代法
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 49.07% of Java online submissions for Integer Break.
     * Memory Usage: 36.1 MB, less than 23.67% of Java online submissions for Integer Break.
     *
     * @param n
     * @return
     */
    public static int integerBreak_1(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], j * Math.max(i - j, dp[i - j]));
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        do_func(2, 1);
        do_func(3, 2);
        do_func(4, 4);
        do_func(7, 12);
        do_func(10, 36);
        do_func(58, 1549681956);
    }

    private static void do_func(int n, int expected) {
        int ret = integerBreak(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
