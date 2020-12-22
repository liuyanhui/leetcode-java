package leetcode;

/**
 *  这里有金矿
 * https://leetcode.com/problems/reverse-integer/
 * 7. Reverse Integer
 * Easy
 * ----------------------
 * Given a 32-bit signed integer, reverse digits of an integer.
 * Note:
 * Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
 * Example 1:
 * Input: x = 123
 * Output: 321
 *
 * Example 2:
 * Input: x = -123
 * Output: -321
 *
 * Example 3:
 * Input: x = 120
 * Output: 21
 *
 * Example 4:
 * Input: x = 0
 * Output: 0
 *
 * Constraints:
 * -231 <= x <= 231 - 1
 */
public class Reverse_Integer_7 {

    public static int reverse(int x) {
        return reverse_2(x);
    }

    /**
     * 巧妙的方法
     * 参考思路：
     * https://leetcode.com/problems/reverse-integer/discuss/4060/My-accepted-15-lines-of-code-for-Java
     * @param x
     * @return
     */
    public static int reverse_4(int x) {
        int result = 0;

        while (x != 0) {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result) {//这里非常精妙
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return result;
    }

    /**
     * 简介的方法
     * 参考思路：
     * https://leetcode.com/problems/reverse-integer/solution/
     * @param x
     * @return
     */
    public static int reverse_3(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;//这两行是精华
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    /**
     * reverse_1的简化版,
     * 去掉了maxLength和round变量
     * @param x
     * @return
     */
    public static int reverse_2(int x) {
        if (x == Integer.MAX_VALUE || x == Integer.MIN_VALUE) {
            return 0;
        }
        int ret = 0;
        int signal = x > 0 ? 1 : -1;
        x = Math.abs(x);//这里无法处理Integer.MIN_VALUE的情况，因为abs(Integer.MIN_VALUE)==Integer.MIN_VALUE，还是负数
        while (x != 0) {
            if (ret > Integer.MAX_VALUE / 10) {
                return 0;
            } else if (ret < Integer.MAX_VALUE / 10) {
                ret = ret * 10 + x % 10;
                x /= 10;
            } else {
                if (x > Integer.MAX_VALUE % 10) {//这里无法处理Integer.MIN_VALUE这种情况，所以在方法开头新增了if (x == Integer.MAX_VALUE || x == Integer.MIN_VALUE)
                    return 0;
                }
                ret = ret * 10 + x;
                break;
            }
        }
        ret = ret * signal;
        return ret;
    }

    /**
     * 正常思路，需要考虑边界情况
     * 验证通过,
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Reverse Integer.
     * Memory Usage: 35.9 MB, less than 82.18% of Java online submissions for Reverse Integer.
     * @param x
     * @return
     */
    public static int reverse_1(int x) {
        if (x == Integer.MAX_VALUE || x == Integer.MIN_VALUE) {
            return 0;
        }
        int ret = 0;
        int signal = x > 0 ? 1 : -1;
        x = Math.abs(x);//这里无法处理Integer.MIN_VALUE的情况，因为abs(Integer.MIN_VALUE)==Integer.MIN_VALUE，还是负数
        int maxLength = String.valueOf(Integer.MAX_VALUE).length();
        int round = 1;
        while (x != 0) {
            //临界长度判断
            if (round == maxLength) {
                if (ret > Integer.MAX_VALUE / 10) {
                    return 0;
                } else if (ret < Integer.MAX_VALUE / 10) {
                    ret = ret * 10 + x;
                    break;
                } else {
                    if (x > Integer.MAX_VALUE % 10) {//这里无法处理Integer.MIN_VALUE这种情况，所以在方法开头新增了if (x == Integer.MAX_VALUE || x == Integer.MIN_VALUE)
                        return 0;
                    }
                    ret = ret * 10 + x;
                    break;
                }
            } else {
                ret = ret * 10 + x % 10;
                x /= 10;
                round++;
            }
        }
        ret = ret * signal;
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.MIN_VALUE);

//        do_func(123, 321);
        do_func(-123, -321);
        do_func(120, 21);
        do_func(0, 0);
        do_func(-1, -1);
        do_func(2147483642, 0);
        do_func(2147483644, 0);
        do_func(-2147483644, 0);
        do_func(Integer.MAX_VALUE, 0);
        do_func(Integer.MIN_VALUE, 0);
        do_func(-2147483412, -2143847412);
        do_func(-2147483648, 0);
    }

    private static void do_func(int x, int expected) {
        int ret = reverse(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
