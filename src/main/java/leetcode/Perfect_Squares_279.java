package leetcode;

/**
 * 279. Perfect Squares
 * Medium
 * --------------------------
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 *
 * A perfect square is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 *
 * Example 1:
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 *
 * Constraints:
 * 1 <= n <= 10^4
 */
public class Perfect_Squares_279 {
    public static int numSquares(int n) {
        return numSquares_4(n);
    }

    /**
     * round 2
     *
     * 参考思路：
     * https://leetcode.com/problems/perfect-squares/discuss/71495/An-easy-understanding-DP-solution-in-Java
     * numSquares_2()
     *
     * DP思路：
     * dp[0]=0
     * dp[i]=min(dp[i],dp[i-j*j]+1) , 1<=j<=sqrt(n)
     *
     * 验证通过：
     * 执行用时：49 ms, 在所有 Java 提交中击败了18.10%的用户
     * 内存消耗：40.3 MB, 在所有 Java 提交中击败了92.08%的用户
     * 通过测试用例：588 / 588
     *
     * @param n
     * @return
     */
    public static int numSquares_4(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    static int least = Integer.MAX_VALUE;

    /**
     * round 2
     * 思考：
     * 1.两部分内容：a.计算所有square number组合；b.找出the least number
     * 2.递归+缓存是一个思路
     *
     * review 递归算法可以转换为DP算法或使用栈的算法
     *
     * 验证通过：
     * Runtime: 780 ms, faster than 8.33% of Java online submissions for Perfect Squares.
     * Memory Usage: 41.3 MB, less than 96.72% of Java online submissions for Perfect Squares.
     *
     * @param n
     * @return
     */
    public static int numSquares_3(int n) {
        least = Integer.MAX_VALUE;
        helper(n, 0);
        return least;
    }

    private static void helper(int n, int cnt) {
        //小于局部最优解的直接排除
        if (cnt >= least) return;
        int i = getMaxRoot(n);
        for (; i >= 1; i--) {
            if (i * i == n) {
                least = Math.min(least, cnt + 1);
                return;
            } else {
                helper(n - i * i, cnt + 1);
            }
        }
    }

    //计算平方根的整数部分
    private static int getMaxRoot(int n) {
        int i = 1;
        while ((i + 1) * (i + 1) <= n) {
            i++;
        }
        return i;
    }

    /**
     * DP方案，参考思路，是numSquares_1的优化方案，减少了计算次数：
     * https://leetcode.com/problems/perfect-squares/discuss/71495/An-easy-understanding-DP-solution-in-Java
     * 公式如下：
     * dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1
     *
     * 验证通过：
     * Runtime: 53 ms, faster than 41.30% of Java online submissions for Perfect Squares.
     * Memory Usage: 39.9 MB, less than 22.51% of Java online submissions for Perfect Squares.
     *
     * @param n
     * @return
     */
    public static int numSquares_2(int n) {
        if (n < 1) return 0;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int j = 1;
            while (i - j * j >= 0) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                j++;
            }
        }

        return dp[n];
    }

    /**
     * DP思路，公式如下：
     * 1.dp[n]=min(dp[i]+dp[n-i-1]),0<i<n
     * 2.if Math.sqrt(i) * Math.sqrt(i) == i then dp[i]=1
     *
     * 验证通过：
     * Runtime: 1694 ms, faster than 5.01% of Java online submissions for Perfect Squares.
     * Memory Usage: 37.7 MB, less than 88.69% of Java online submissions for Perfect Squares.
     * @param n
     * @return
     */
    public static int numSquares_1(int n) {
        if (n < 1) return 0;
        int[] dp = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= n; i++) {
            if ((int) Math.sqrt(i) * (int) Math.sqrt(i) == i) {
                dp[i] = 1;
            } else {
                for (int j = 1; j <= i / 2; j++) {
                    dp[i] = Math.min(dp[i], dp[j] + dp[i - j]);
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        do_func(2, 2);
        do_func(3, 3);
        do_func(5, 2);
        do_func(11, 3);
        do_func(12, 3);
        do_func(13, 2);
        do_func(36, 1);
        do_func(81, 1);
        do_func(80, 2);
        do_func(79, 4);
        do_func(1, 1);
        do_func(9999, 4);
    }

    private static void do_func(int nums, int expected) {
        int ret = numSquares(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
