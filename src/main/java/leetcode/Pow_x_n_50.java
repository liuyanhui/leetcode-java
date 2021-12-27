package leetcode;

/**
 * 50. Pow(x, n)
 * Medium
 * ----------------------------
 * Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
 *
 * Example 1:
 * Input: x = 2.00000, n = 10
 * Output: 1024.00000
 *
 * Example 2:
 * Input: x = 2.10000, n = 3
 * Output: 9.26100
 *
 * Example 3:
 * Input: x = 2.00000, n = -2
 * Output: 0.25000
 * Explanation: 2^-2 = 1/(2^2) = 1/4 = 0.25
 *
 * Constraints:
 * -100.0 < x < 100.0
 * -2^31 <= n <= 2^31-1
 * -10^4 <= x^n <= 10^4
 */
public class Pow_x_n_50 {
    public static double myPow(double x, int n) {
        return myPow_3(x, n);
    }

    /**
     * review
     *
     * 金矿，要牢记：
     * 1.Integer.MIN_VALUE=-2147483648 是偶数
     * 2.Integer.MAX_VALUE=2147483647 是奇数
     * 3.Integer.MIN_VALUE也没有那么可怕，通过右移位运算或者整除运算都可以持续减小这个数，即使abs(Integer.MIN_VALUE)=Integer.MIN_VALUE
     *
     * 递归法
     * 参考思路：
     * https://leetcode.com/problems/powx-n/discuss/19544/5-different-choices-when-talk-with-interviewers
     * https://leetcode.com/problems/powx-n/discuss/19546/Short-and-easy-to-understand-solution
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Pow(x, n).
     * Memory Usage: 38.4 MB, less than 59.81% of Java online submissions for Pow(x, n).
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow_3(double x, int n) {
        if (x == 0) return 0;
        if (n == 0 || x == 1) return 1;
        if (n == -1) return 1 / x;
        if (n % 2 == 0) {
            return myPow_3(x * x, n / 2);
        } else if (n > 0) {
            return x * myPow_3(x, n - 1);
        } else {
            return 1 / x * myPow_3(x, n + 1);
        }
    }

    public static double myPow_2(double x, int n) {
        if (x == 0) return 0;
        if (n == 0 || x == 1) return 1;
        if (n == 1) {
            return x;
        } else if (n == -1) {
            return 1 / x;
        } else if (n % 2 == 0) {
            double t = myPow_2(x, n / 2);
            return t * t;
        } else if (n % 2 == 1) {
            int p = n > 0 ? n - 1 : n + 1;
            double t = myPow_2(x, p / 2);
            return x * t * t;
        }
        return 0;
    }

    /**
     * 用下面的算法经过O(logN)的复杂度就可以得出结果
     * 初始化时：n=20 base=0 power=1
     * 第一轮，base=0 power依次为1 2 4 8 16，当power=32时，base+2^power大于20了
     * 第二轮，base=16 power依次为1 2，当power=3时，base+2^power大于20了
     * 第三轮，base=18 power依次为1 2，当power=2时，base+2^power等于20
     * 最终base=20==n
     *
     * 验证通过：
     * Runtime: 22 ms, faster than 7.33% of Java online submissions for Pow(x, n).
     * Memory Usage: 38.3 MB, less than 62.78% of Java online submissions for Pow(x, n).
     *
     * @param x
     * @param n
     * @return
     */
    public static double myPow_1(double x, int n) {
        if (n == 0 || x == 1 || (x == -1 && n % 2 == 0)) return 1;
        if (x == 0 || n == Integer.MIN_VALUE) return 0;
        if (n < 0) {
            x = 1 / x;//这样就不用考虑n是负数的情况了
            n = -n;
        }
        double ret = 1;
        int base = 0, power = 1;//base是基数，step是2的幂次
        double m = x;
        while (n - base > 0) {
            if ((n - base) / power / power > 1) {//把加法和乘法转变成减法和出发，防止溢出
                power *= 2;
                m *= m;
            } else {
                base += power;
                power = 1;
                ret *= m;
                m = x;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
//        System.out.println(Integer.MIN_VALUE==Math.abs(Integer.MIN_VALUE));
//        System.out.println(Integer.MIN_VALUE);
        do_func(2, 10, 1024.00000);
        do_func(2.100, 3, 9.26100);
        do_func(2.00000, -2, 0.25000);
        do_func(199.0, 0, 1);
        do_func(-2, 3, -8.00000);
        do_func(-2, -3, -0.125000);
        do_func(2, -3, 0.125000);
        do_func(0.22, 10, 0);
        do_func(0.22, -5, 1940.37913);
        do_func(0.000012, 2147483647, 0);
        do_func(1, -2147483648, 1);
        do_func(2.00000, -2147483648, 0);
        do_func(-1, -2147483648, 1);
    }

    private static void do_func(double x, int n, double expected) {
        double ret = myPow(x, n);
        System.out.println(ret);
        System.out.println(Double.compare(ret, expected) == 0);
        System.out.println("--------------");
    }
}
