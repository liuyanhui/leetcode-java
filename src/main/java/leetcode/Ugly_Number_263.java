package leetcode;

/**
 * 263. Ugly Number
 * Easy
 * -------------------------
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return true if n is an ugly number.
 *
 * Example 1:
 * Input: n = 6
 * Output: true
 * Explanation: 6 = 2 × 3
 *
 * Example 2:
 * Input: n = 8
 * Output: true
 * Explanation: 8 = 2 × 2 × 2
 *
 * Example 3:
 * Input: n = 14
 * Output: false
 * Explanation: 14 is not ugly since it includes the prime factor 7.
 *
 * Example 4:
 * Input: n = 1
 * Output: true
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 *
 *  Constraints:
 * -2^31 <= n <= 2^31 - 1
 */
public class Ugly_Number_263 {
    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Ugly Number.
     * Memory Usage: 36.1 MB, less than 42.04% of Java online submissions for Ugly Number.
     * @param n
     * @return
     */
    public static boolean isUgly(int n) {
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
        do_func(0, true);
        do_func(-1, true);
        do_func(-2, true);
        do_func(-21, false);
    }

    private static void do_func(int num, boolean expected) {
        boolean ret = isUgly(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
