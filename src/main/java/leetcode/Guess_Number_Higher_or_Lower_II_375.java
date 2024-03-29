package leetcode;

/**
 * 375. Guess Number Higher or Lower II
 * Medium
 * ----------------------
 * We are playing the Guessing Game. The game will work as follows:
 * I pick a number between 1 and n.
 * You guess a number.
 * If you guess the right number, you win the game.
 * If you guess the wrong number, then I will tell you whether the number I picked is higher or lower, and you will continue guessing.
 * Every time you guess a wrong number x, you will pay x dollars. If you run out of money, you lose the game.
 *
 * Given a particular n, return the minimum amount of money you need to guarantee a win regardless of what number I pick.
 *
 * Example 1:
 * Input: n = 10
 * Output: 16
 * Explanation: The winning strategy is as follows:
 * - The range is [1,10]. Guess 7.
 *     - If this is my number, your total is $0. Otherwise, you pay $7.
 *     - If my number is higher, the range is [8,10]. Guess 9.
 *         - If this is my number, your total is $7. Otherwise, you pay $9.
 *         - If my number is higher, it must be 10. Guess 10. Your total is $7 + $9 = $16.
 *         - If my number is lower, it must be 8. Guess 8. Your total is $7 + $9 = $16.
 *     - If my number is lower, the range is [1,6]. Guess 3.
 *         - If this is my number, your total is $7. Otherwise, you pay $3.
 *         - If my number is higher, the range is [4,6]. Guess 5.
 *             - If this is my number, your total is $7 + $3 = $10. Otherwise, you pay $5.
 *             - If my number is higher, it must be 6. Guess 6. Your total is $7 + $3 + $5 = $15.
 *             - If my number is lower, it must be 4. Guess 4. Your total is $7 + $3 + $5 = $15.
 *         - If my number is lower, the range is [1,2]. Guess 1.
 *             - If this is my number, your total is $7 + $3 = $10. Otherwise, you pay $1.
 *             - If my number is higher, it must be 2. Guess 2. Your total is $7 + $3 + $1 = $11.
 * The worst case in all these scenarios is that you pay $16. Hence, you only need $16 to guarantee a win.
 *
 * Example 2:
 * Input: n = 1
 * Output: 0
 * Explanation: There is only one possible number, so you can guess 1 and not have to pay anything.
 *
 * Example 3:
 * Input: n = 2
 * Output: 1
 * Explanation: There are two possible numbers, 1 and 2.
 * - Guess 1.
 *     - If this is my number, your total is $0. Otherwise, you pay $1.
 *     - If my number is higher, it must be 2. Guess 2. Your total is $1.
 * The worst case is that you pay $1.
 *
 * Constraints:
 * 1 <= n <= 200
 */
public class Guess_Number_Higher_or_Lower_II_375 {

    public static int getMoneyAmount(int n) {
        return getMoneyAmount_4(n);
    }

    /**
     * round 2
     * 迭代法，递归法计算方向相反的方法
     * getMoneyAmount_3()的方式是自顶向下的，时间复杂度高。如果是自底向上计算，那么时间复杂度会降低到O(N*N*N)
     *
     *
     * 验证通过：
     * Runtime 17 ms Beats 89.32%
     * Memory 40.7 MB Beats 98.29%
     *
     * @param n
     * @return
     */
    public static int getMoneyAmount_4(int n) {
        int[][] cache = new int[n + 2][n + 2];//四个边缘扩大一周，防止下标溢出
        //沿对角线，从左向右遍历计算
        for (int i = 1; i <= n; i++) {//这一层表示两个下标[m][n]的gap，即m+gap=n
            for (int j = 1; i + j <= n; j++) {//这一层表示沿对角线平行线遍历的范围
                //计算cache[j][i+j]
                int t = Integer.MAX_VALUE;
                for (int k = j; k <= i + j; k++) {
                    t = Math.min(t, k + Math.max(cache[j][k - 1], cache[k + 1][i + j]));
                }
                cache[j][i + j] = t;
            }
        }
        return cache[1][n];
    }

    /**
     * round 2
     * 递归法
     *
     * Thinking:
     * 1.naive solution。采用递归+缓存中间值的思路。
     * 1.1.公式为：
     * f(1,n)=min(i+min(f(1,i-1),f(i+1,n)))
     * f(1,1)=0
     * f(1,2)=1
     * ..
     * f(n-1,n)=n-1
     * f(n,n)=0
     *
     * f(1,3)
     * =min(1+min(f(1,-1),f(2,3)),2+min(f(1,1),f(3,3)),3+min(f(1,2),f(3,2)))
     * =min(2,2,4)
     * =2
     * 1.2.时间复杂度：O(N^N)
     * 3.上面的方式是自顶向下的，时间复杂度高。如果是自底向上计算，那么时间复杂度会降低到O(N*N*N)
     *
     * 时间复杂度过高，会Time Limit Exceed
     *
     *
     * @param n
     * @return
     */
    public static int getMoneyAmount_3(int n) {
        int[][] cache = new int[n + 1][n + 1];
        return helper(1, n, cache);
    }

    private static int helper(int beg, int end, int[][] cache) {
        if (beg >= end) return 0;
        if (beg + 1 == end) return beg;
        if (cache[beg][end] > 1) return cache[beg][end];
        int res = Integer.MAX_VALUE;
        for (int i = beg; i <= end; i++) {
            res = Math.min(res, i + Math.max(helper(beg, i - 1, cache), helper(i + 1, end, cache)));
        }
        return res;
    }

    /**
     * review 金矿：如何从左向右沿对角线，遍历二维数组。
     * 例如：4*4的二维数组，遍历路径为
     * 第一次：[1,2] [2,3] [3,4]
     * 第二次：[1:3] [2:4]
     * 第三次：[1:4]
     *
     * 参考思路：
     * https://leetcode.com/problems/guess-number-higher-or-lower-ii/discuss/84764/Simple-DP-solution-with-explanation~~
     * https://leetcode.com/submissions/detail/286846985/
     *
     * DP思路：
     * 二维DP数组，对角线遍历法，沿对角线从左向右遍历。公式如下：
     * dp[i][j]=min{k+max(dp[i,k-1][k+1,j])},其中i<=k<=j，需要遍历k
     *
     * 验证通过：
     * Runtime: 30 ms, faster than 60.76% of Java online submissions for Guess Number Higher or Lower II.
     * Memory Usage: 38.7 MB, less than 14.45% of Java online submissions for Guess Number Higher or Lower II.
     *
     * @param n
     * @return
     */
    public static int getMoneyAmount_2(int n) {
        int dp[][] = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; i + j <= n; j++) {
                int t = Integer.MAX_VALUE;
                for (int k = j; k <= i + j; k++) {
                    t = Math.min(t, k + Math.max(dp[j][k - 1], dp[k + 1][i + j]));
                }
                dp[j][i + j] = t;
            }
        }
//        ArrayUtils.printIntArray(dp);
        return dp[1][n];
    }

    public static void main(String[] args) {
        do_func(10, 16);
        do_func(1, 0);
        do_func(2, 1);
        do_func(20, 49);
        do_func(4, 4);
        do_func(23, 58);
        do_func(24, 61);
        do_func(50, 172);
        do_func(200, 952);
    }

    private static void do_func(int n, int expected) {
        int ret = getMoneyAmount(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
