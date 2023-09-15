package leetcode;

/**
 *  这里有金矿
 * https://leetcode.com/problems/reverse-integer/
 * 7. Reverse Integer
 * Medium
 * ----------------------
 * Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-2^31, 2^31 - 1], then return 0.
 *
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 *
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
 * -2^31 <= x <= 2^31 - 1
 */
public class Reverse_Integer_7 {

    public static int reverse(int x) {
        return reverse_6(x);
    }

    /**
     * round 3
     *
     * Thinking：
     * 1.要注意reverse之后越界的情况。如果越界，返回0。
     * 2.由于两个数相乘后会发生越界的情况，所以用除法替代乘法。期望计算a*b>c，用a>c/b替代。即，加减互换，乘除互换。
     * 2.1.num*10+digit>MAX_VALUE 转化为 num>(MAX_VALUE-digit)/10
     * 2.2.num*10+digit<MIN_VALUE 不可以转化为 num<(MIN_VALUE-digit)/10，因为当digit>0时，MIN_VALUE-digit会越界。
     * 3.先判断是否会越界，再计算。
     *
     * review round3 reverse_5()和reverse_4()的思路更巧妙
     *
     * 验证通过：
     * Runtime 1 ms Beats 97.97%
     * Memory 39.3 MB Beats 94.35%
     *
     * @param x
     * @return
     */
    public static int reverse_6(int x) {
        int res = 0;
        int singal = 1;
        if (x < 0) singal = -1;
        int divide = 10;
        while (x != 0) {
            int d = Math.abs(x % (divide * singal));
            //MIN_VALUE的判断需要注意
            if (((Integer.MIN_VALUE + d) / divide > res)
                    || (res > (Integer.MAX_VALUE - d) / divide)) {
                return 0;
            }
            res = res * 10 + d;
            x /= 10;
        }
        return res * singal;
    }

    /**
     * Round 2 : 2021.11.5 , 难度从easy变为medium
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Reverse Integer.
     * Memory Usage: 36 MB, less than 76.62% of Java online submissions for Reverse Integer.
     *
     * @param x
     * @return
     */
    public static int reverse_5(int x) {
        if (x == Integer.MIN_VALUE) return 0;
        int ret = 0;
        int sign = x > 0 ? 1 : -1;
        x = sign * x;
        while (x > 0) {
            int t = ret * 10 + x % 10;
            if (t / 10 != ret) {//review 
                return 0;
            }
            ret = ret * 10 + x % 10;
            x /= 10;
        }
        return ret * sign;
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
            if ((newResult - tail) / 10 != result) {//review 这里非常精妙
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
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.MIN_VALUE);

        do_func(123, 321);
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
        do_func(1534236469, 0);
        do_func(1003, 3001);
    }

    private static void do_func(int x, int expected) {
        int ret = reverse(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
