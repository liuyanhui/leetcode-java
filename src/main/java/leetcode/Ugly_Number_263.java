package leetcode;

/**
 * 263. Ugly Number
 * Easy
 * -------------------------
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return true if n is an ugly number.
 * <p>
 * Example 1:
 * Input: n = 6
 * Output: true
 * Explanation: 6 = 2 × 3
 * <p>
 * Example 2:
 * Input: n = 8
 * Output: true
 * Explanation: 8 = 2 × 2 × 2
 * <p>
 * Example 3:
 * Input: n = 14
 * Output: false
 * Explanation: 14 is not ugly since it includes the prime factor 7.
 * <p>
 * Example 4:
 * Input: n = 1
 * Output: true
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 * <p>
 * Constraints:
 * -2^31 <= n <= 2^31 - 1
 */
public class Ugly_Number_263 {
    public static boolean isUgly(int n) {
        return isUgly_r3_1(n);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     * <p>
     * 验证通过
     *
     * @param n
     * @return
     */
    public static boolean isUgly_r3_1(int n) {
        if (n <= 0) return false;
        while (n > 1 && n % 2 == 0) {
            n /= 2;
        }
        while (n > 1 && n % 3 == 0) {
            n /= 3;
        }
        while (n > 1 && n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }

    /**
     * round 2
     * 思考：
     * 1.暴力法。n依次循环与2,3,5做除法，直到无法整除。如果最终结果是1，返回true；否则返回false。
     * <p>
     * isUgly_1()的实现更优
     * <p>
     * 验证成功：
     * Runtime: 2 ms, faster than 75.69% of Java online submissions for Ugly Number.
     * Memory Usage: 41.7 MB, less than 21.38% of Java online submissions for Ugly Number.
     *
     * @param n
     * @return
     */
    public static boolean isUgly_2(int n) {
        if (n <= 0) return false;
        while (n / 2 * 2 == n) {
            n /= 2;
        }
        while (n / 3 * 3 == n) {
            n /= 3;
        }
        while (n / 5 * 5 == n) {
            n /= 5;
        }
        return n == 1;
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Ugly Number.
     * Memory Usage: 36.1 MB, less than 42.04% of Java online submissions for Ugly Number.
     *
     * @param n
     * @return
     */
    public static boolean isUgly_1(int n) {
        if (n <= 0) return false;
        while (n % 2 == 0) {
            n /= 2;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        while (n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }

    public static void main(String[] args) {
        do_func(6, true);
        do_func(8, true);
        do_func(14, false);
        do_func(1, true);
        do_func(0, false);
        do_func(-1, false);
        do_func(-2, false);
        do_func(-21, false);
        do_func(7, false);
        do_func(Integer.MAX_VALUE, false);
        do_func(Integer.MIN_VALUE, false);
        System.out.println("==================");
    }

    private static void do_func(int num, boolean expected) {
        boolean ret = isUgly(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
