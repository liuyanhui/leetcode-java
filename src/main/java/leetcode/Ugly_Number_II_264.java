package leetcode;

/**
 * 264. Ugly Number II
 * Medium
 * --------------------------
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return the nth ugly number.
 * <p>
 * Example 1:
 * Input: n = 10
 * Output: 12
 * Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.
 * <p>
 * Example 2:
 * Input: n = 1
 * Output: 1
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 * <p>
 * Constraints:
 * 1 <= n <= 1690
 */
public class Ugly_Number_II_264 {
    public static int nthUglyNumber(int n) {
        return nthUglyNumber_r3_1(n);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking：
     * 公式为  F(n)=f(i,j,k) = 2^i * 3^j * 5^k
     * 然后i, j, k的变化不是独立的，当{2，3，5}中的一个或多个是F(n)的因子的时候，{i,j,k}中对应的项需要加1。
     * 如：
     * F(n)=2时，i=i+1
     * F(n)=6时，i=i+1,j=j+1
     * F(n)=10时，i=i+1,k=k+1
     * F(n)=30时，i=i+1,j=j+1,k=k+1
     * <p>
     * 任一F(n)都是从 F(i)*2 or F(j)*3 or F(k)*5 而来，其中i<n，F(0)=1。
     * 即 F(n) = min(F(i)*2 , F(j)*3 , F(k)*5)
     * 任意一个F(n)都会分别与2,3,5进行乘法计算，计算结果成为后续的某个F(x) ，n<x。并且 F(i)*2 ， F(j)*3 ， F(k)*5 只能计算一次。
     * 当每个F(i)*2被使用过，就无法再次参与计算（只能计算一次），下次就是F(n+1)*2参与计算。同理F(j)*3 和 F(k)*5。
     * 通过(i,j,k)分别保存f(n)是否被2，nthUglyNumber_r3_13，5计算过.
     * 需要注意F(n)重复出现的情况。
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 98.35%
     * Memory 41.37 MB Beats 92.71%
     */
    public static int nthUglyNumber_r3_1(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int idx2 = 0, idx3 = 0, idx5 = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(dp[idx2] * 2, Math.min(dp[idx3] * 3, dp[idx5] * 5));
            if (dp[i] % 2 == 0) idx2++;
            if (dp[i] % 3 == 0) idx3++;
            if (dp[i] % 5 == 0) idx5++;
        }
        return dp[n - 1];
    }

    /**
     * round 2
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/ugly-number-ii/discuss/69364/My-16ms-C%2B%2B-DP-solution-with-short-explanation
     * https://leetcode.com/problems/ugly-number-ii/discuss/69362/O(n)-Java-solution
     * <p>
     * 思考：
     * 1.有三个因子影响结果。如果能把三个因子通过公式一维化。公式最好是递增的。
     * 2.公式：n=i+j+k+1，F(n)=f(i,j,k) = 2^i * 3^j * 5^k ，初始时：i=j=k=0。然而，通过用例可以发现i,j,k与F(n)的关系不是线性的。
     * 3.通过Discuss中的文章发现。问题可以转化为"merge k sorted list"问题，即合并已排序列表问题。
     * 4.如果F(n)与i,j,k的关系无法推导，那么需要思考F(n)和F(n-1)的关系。公式为：F(n)=min(F(n-a)*2,F(n-b)*3,F(n-c)*5)，其中a<n,b<n,c<n。F(n)是前面的某个F(n-k)乘以[2,3,5]得到的结果。如果选择公式中的a,b,c成为要点。通过规律可以发现a,b,c是递增的，即它们不会减少只会增加。
     * 5.本题中"merge k sorted list"中共有3个sorted list，分别为{F(a)*2}，{F(b)*3}，{F(c)*5}。
     * 6.本题的合并排序是要去重的。所以当sorted list中的数字重复出现时，要忽略。如数字6时，2和3的sorted list都要从list中去除。
     * 6.问题分为3部分：1.构建3个sorted list；2.合并排序这3个sorted list；3.去重。
     * <p>
     * 验证通过：
     * Runtime: 7 ms, faster than 43.89% of Java online submissions for Ugly Number II.
     * Memory Usage: 41.9 MB, less than 71.18% of Java online submissions for Ugly Number II.
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber_3(int n) {
        if (n < 0) return 0;
        int[] res = new int[n];
        res[0] = 1;
        int a = 0, b = 0, c = 0;
        int i = 1;
        while (i < n) {
            res[i] = Math.min(res[a] * 2, Math.min(res[b] * 3, res[c] * 5));
            if (res[i] % 2 == 0) a++;
            if (res[i] % 3 == 0) b++;
            if (res[i] % 5 == 0) c++;
            i++;
        }
        return res[n - 1];
    }

    /**
     * 问题转换为：min(2^i * 3^j * 5^k), i+j+k<n。每次计算后i,j,k有条件的加1
     * <p>
     * DP思路：
     * We have an array k of first n ugly number. We only know, at the beginning, the first one, which is 1. Then
     * k[1] = min( k[0]x2, k[0]x3, k[0]x5). The answer is k[0]x2. So we move 2's pointer to 1. Then we test:
     * k[2] = min( k[1]x2, k[0]x3, k[0]x5). And so on.
     * Be careful about the cases such as 6, in which we need to forward both pointers of 2 and 3.
     * x here is multiplication.
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/ugly-number-ii/discuss/69364/My-16ms-C%2B%2B-DP-solution-with-short-explanation
     * 详细解释见，是一个k sorted list 问题：
     * https://leetcode.com/problems/super-ugly-number/discuss/277313/My-view-of-this-question-hope-it-can-help-you-understand!!!
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 53.95% of Java online submissions for Ugly Number II.
     * Memory Usage: 38 MB, less than 72.40% of Java online submissions for Ugly Number II.
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber_2(int n) {
        int[] arr = new int[n];
        int k1 = 0, k2 = 0, k3 = 0;
        arr[0] = 1;
        for (int i = 1; i < n; i++) {
            arr[i] = Math.min(Math.min(arr[k1] * 2, arr[k2] * 3), arr[k3] * 5);
            if (arr[i] % 2 == 0) k1++;
            if (arr[i] % 3 == 0) k2++;
            if (arr[i] % 5 == 0) k3++;
        }
        return arr[n - 1];
    }

    /**
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber_1(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        int ret = 1;
        int count = 1;
        while (count < n) {
            int tmp = ++ret;
            while (tmp % 2 == 0) {
                tmp /= 2;
            }
            while (tmp % 3 == 0) {
                tmp /= 3;
            }
            while (tmp % 5 == 0) {
                tmp /= 5;
            }
            if (tmp == 1) count++;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(10, 12);
        do_func(2, 2);
        do_func(1, 1);
        do_func(11, 15);
        do_func(100, 1536);
        do_func(1352, 402653184);
        System.out.println("==================");
    }

    private static void do_func(int n, int expected) {
        int ret = nthUglyNumber(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
