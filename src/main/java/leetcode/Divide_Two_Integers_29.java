package leetcode;

/**
 * 29. Divide Two Integers
 * Medium
 * -----------------------
 * Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.
 *
 * The integer division should truncate toward zero, which means losing its fractional part. For example, 8.345 would be truncated to 8, and -2.7335 would be truncated to -2.
 *
 * Return the quotient after dividing dividend by divisor.
 *
 * Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−2^31, 2^31 − 1]. For this problem, if the quotient is strictly greater than 2^31 - 1, then return 2^31 - 1, and if the quotient is strictly less than -2^31, then return -2^31.
 *
 * Example 1:
 * Input: dividend = 10, divisor = 3
 * Output: 3
 * Explanation: 10/3 = 3.33333.. which is truncated to 3.
 *
 * Example 2:
 * Input: dividend = 7, divisor = -3
 * Output: -2
 * Explanation: 7/-3 = -2.33333.. which is truncated to -2.
 *
 * Example 3:
 * Input: dividend = 0, divisor = 1
 * Output: 0
 *
 * Example 4:
 * Input: dividend = 1, divisor = 1
 * Output: 1
 *
 * Constraints:
 * -2^31 <= dividend, divisor <= 2^31 - 1
 * divisor != 0
 */
public class Divide_Two_Integers_29 {
    public static int divide(int dividend, int divisor) {
        return divide_4(dividend, divisor);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1.naive solution
     * 1.1. 用减法替代除法，循环执行dividend=dividend-dividor，直到dividend<divisor，循环次数就是结果。
     * 1.2. 要注意超过上限和下限。
     * 1.3. 时间复杂度O(N)
     * 2.不能使用乘、除和取模运算，是否可以使用位运算？不可以
     * 3.穷举法。
     * 3.1. 穷举i的所有可能，公式为：dividor*i<dividend-dividor*i，i从0开始增加。这个过程只用加减法。
     * 3.2. sum(dividor*{i})中最接近dividend的组合的i的和就是所求。
     * 3.3. divide_3()中的说明更直观
     *
     * 参考divide_3()中的实现
     * 边界值的计算比较繁琐
     *
     * 验证通过
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide_4(int dividend, int divisor) {
        //唯一会发生溢出的情况，特殊处理
        if (dividend == 1 << 31 && divisor == -1) return (1 << 31) - 1;
        int res = 0;
        int sign = ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) ? 1 : -1;
        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);
        while (dividend - divisor >= 0) {
            int t = divisor;
            int r = 1;
            //加班变减法 dividend>=t+t
            while (dividend - t - t >= 0) {
                t += t;
                r += r;
            }
            res += r;
            dividend -= t;
        }

        return res * sign;
    }

    /**
     * round2
     * 金矿：使用位运算+幂运算
     *
     * 思路如下：
     * 循环计算dividend = dividend-(divisor*(2^n))，直到dividend<divisor，所有的2^n相加即为所求。
     * 假设dividend=88，divisor=3，计算过程如下：
     * 1.dividend=88
     *   3*(2^5)=3*32=96
     *   3*(2^4)=3*16=48
     * 2.dividend=88-48=40 :
     *   3*(2^3)=3*8=24
     * 3.dividend=40-24=16
     *   3*(2^2)=3*4=12
     * 4.dividend=16-12=4
     *   3*(2^0)=3*1=3
     * 5.dividend=4-3=1
     * 6.所有的2^n相加：16+8+4+1=29
     *
     * 参考资料：
     * https://leetcode.com/problems/divide-two-integers/discuss/142849/C%2B%2BJavaPython-Should-Not-Use-%22long%22-Int
     *
     * @param A
     * @param B
     * @return
     */
    public static int divide_3(int A, int B) {
        //唯一会发生溢出的情况，特殊处理
        if (A == 1 << 31 && B == -1) return (1 << 31) - 1;
        //这里的Math.abs()很精妙，即使Integer.MIN_VALUE是最小的负数
        int a = Math.abs(A), b = Math.abs(B), res = 0, x = 0;
        while (a - b >= 0) {
            //找到满足条件的最大的2^x
            //公式：dividend-(divisor*(2^x))
            for (x = 0; a - (b << x << 1) >= 0; x++) ;
            //下面的代码有问题，不可以替换上一行代码。
            // 因为：虽然一般来说i<<x<<1等价于i<<(x+1)，但是1<<32却不等于1<<31<<1。
            // 原因：如果移动的位数超过了该类型的最大位数，那么编译器会对移动的位数取模。如对int型移动33位，实际上只移动了33%32=1位。
            //for (x = 0; a - (b << (x + 1)) >= 0; x++);
            //每个2^n累加
            res += 1 << x;
            a -= b << x;
        }
        //review 这里也很巧妙，"(A > 0) == (B > 0)"用来判断结果的正负
        return (A > 0) == (B > 0) ? res : -res;
    }

    /**
     * 使用位运算+幂运算
     *
     * 验证失败，边界值和正负关系没有考虑，思路大体正确
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide_2(int dividend, int divisor) {
        if (dividend == divisor) return 1;
        int ret = 0;
        while (dividend - divisor > 0) {
            int i = 0;
            //找到满足条件的最大的2^n
            //公式：dividend-(divisor*(2^n))
            while (dividend - (divisor << (i + 1)) >= 0) {
                i++;
            }
            //每个2^n累加
            ret += 1 << i;
            dividend -= divisor << i;
        }
        return ret;
    }

    /**
     * 除法转换为减法
     * 验证失败 Time limit exceeded
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static int divide_1(int dividend, int divisor) {
        //唯一会发生溢出的情况，特殊处理
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        if (dividend == divisor) return 1;
        int ret = 0;
        int signal = 1;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) {
            signal = -1;
        }
        //为了防止溢出，把正数转化为负数。即转化成两个负数的除法
        if (signal == -1) {
            if (divisor > 0) divisor = -divisor;
            else dividend = -dividend;
        }
        //会超时,优化思路：divisor在每次循环后divisor*=2，时间复杂度简化为O(logN^2)
        //除法转化成减法，时间复杂度O(N)
        while ((signal == 1 && dividend >= divisor) || (signal == -1 && dividend <= divisor)) {
            dividend = dividend - divisor;
            ret++;
        }
        return signal * ret;
    }

    public static void main(String[] args) {
        System.out.println(Math.abs(Integer.MIN_VALUE));
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(Math.log(Integer.MAX_VALUE) / Math.log(2));
//        System.out.println(Math.abs(Integer.MIN_VALUE) - 55);
//        System.out.println(Integer.MIN_VALUE - 55);
//        System.out.println(Math.abs(Integer.MIN_VALUE) - (-55));
//        System.out.println(Integer.MIN_VALUE - (-55));
//        System.out.println(Integer.MIN_VALUE - 1);
//        System.out.println(Integer.MIN_VALUE - (-1));
//        System.out.println(Math.abs(Integer.MIN_VALUE) - (-603979776));
//        System.out.println(1 << 31);
//        System.out.println(1 << 32);
//        System.out.println(1 << 31 << 1);
//        System.out.println(3 << 31);
//        System.out.println(3 << 32);
        System.out.println("==========");

        do_func(10, 3, 3);
        do_func(7, -3, -2);
        do_func(-7, 3, -2);
        do_func(-7, -3, 2);
        do_func(0, 1, 0);
        do_func(1, 1, 1);
        do_func(1, 16789, 0);
        do_func(1, -16789, 0);
        do_func(Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
        do_func(Integer.MAX_VALUE, -1, -Integer.MAX_VALUE);
        do_func(Integer.MIN_VALUE, 1, Integer.MIN_VALUE);
        do_func(Integer.MIN_VALUE, -1, Integer.MAX_VALUE);
        do_func(Integer.MIN_VALUE, Integer.MIN_VALUE, 1);
        do_func(Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
        do_func(1, Integer.MAX_VALUE, 0);
        do_func(1, Integer.MIN_VALUE, 0);
        do_func(Integer.MIN_VALUE, 2, -1073741824);
        do_func(Integer.MIN_VALUE, Integer.MIN_VALUE, 1);
        do_func(Integer.MIN_VALUE, -55, 39045157);
        do_func(32, 2, 16);
    }

    private static void do_func(int dividend, int divisor, int expected) {
        int ret = divide(dividend, divisor);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
