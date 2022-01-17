package leetcode;

/**
 * https://leetcode.com/problems/sqrtx/
 * 69. Sqrt(x)
 * Easy
 * --------------
 * Given a non-negative integer x, compute and return the square root of x.
 * Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.
 *
 * Note: You are not allowed to use any built-in exponent function or operator, such as pow(x, 0.5) or x ** 0.5.
 *
 * Example 1:
 * Input: x = 4
 * Output: 2
 *
 * Example 2:
 * Input: x = 8
 * Output: 2
 * Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.
 *
 * Constraints:
 * 0 <= x <= 2^31 - 1
 */
public class Sqrt_x_69 {

    /**
     * round 2
     * review 20220117
     * @param x
     * @return
     */
    public static int mySqrt(int x) {
        return mySqrt_2(x);
    }

    /**
     * 采用binary search思路
     * 验证通过。
     * @param x
     * @return
     */
    public static int mySqrt_2(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        //left =0 会报错（当x=3时，mid可能为0）
        int left = 1, right = x / 2;
        while (left <= right) {
            int mid = (left + right) / 2;
            //这里不可以写成mid*mid>x，因为mid*mid会溢出。
            if (mid > x / mid) right = mid - 1;
            else if (mid < x / mid) left = mid + 1;
            else return mid;
        }

        return right;
    }

    /**
     * 暴力法，类似穷举法
     * 验证失败，Time Limit Exceeded
     * @param x
     * @return
     */
    public static int mySqrt_1(int x) {
        for (int i = 0; i <= x; i++) {
            if (i * i > x) {
                return i - 1;
            } else if (i * i == x) {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func(1, 1);
        do_func(0, 0);
        do_func(2, 1);
        do_func(3, 1);
        do_func(4, 2);
        do_func(8, 2);
        do_func(24, 4);
        do_func(1600000000, 40000);
        do_func(2147483647, 46340);
        do_func(2147395599, 46339);
    }

    private static void do_func(int x, int expected) {
        int ret = mySqrt(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
