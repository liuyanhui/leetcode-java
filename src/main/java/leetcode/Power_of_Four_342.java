package leetcode;

/**
 * 342. Power of Four
 * Easy
 * -------------------------
 * Given an integer n, return true if it is a power of four. Otherwise, return false.
 * An integer n is a power of four, if there exists an integer x such that n == 4^x.
 * <p>
 * Example 1:
 * Input: n = 16
 * Output: true
 * <p>
 * Example 2:
 * Input: n = 5
 * Output: false
 * <p>
 * Example 3:
 * Input: n = 1
 * Output: true
 * <p>
 * Constraints:
 * -2^31 <= n <= 2^31 - 1
 * Follow up: Could you solve it without loops/recursion?
 */
public class Power_of_Four_342 {
    public static boolean isPowerOfFour(int n) {
        return isPowerOfFour_r3_1(n);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     * <p>
     * Thinking
     * 1. navie solution
     * loops or recursion
     * 2. math solution
     * isPowerOfFour_2()
     * <p>
     * 验证通过：
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfFour_r3_1(int n) {
        if (n == 1) return true;
        if (n <= 0 || n % 4 != 0) return false;
        return (isPowerOfFour(n / 4));
    }

    /**
     * review round 2 math solution
     * 找到规律
     * 参考思路：
     * https://leetcode.com/problems/power-of-four/discuss/80460/1-line-C%2B%2B-solution-without-confusing-bit-manipulations
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 99.46% of Java online submissions for Power of Four.
     * Memory Usage: 35.8 MB, less than 84.50% of Java online submissions for Power of Four.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfFour_2(int n) {
        return n > 0 && ((n & (n - 1)) == 0) && ((n - 1) % 3 == 0);
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 99.46% of Java online submissions for Power of Four.
     * Memory Usage: 35.8 MB, less than 92.52% of Java online submissions for Power of Four.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfFour_1(int n) {
        while (n > 0 && n % 4 == 0) {
            n /= 4;
        }
        return n == 1;
    }

    public static void main(String[] args) {
        do_func(16, true);
        do_func(5, false);
        do_func(8, false);
        do_func(1, true);
        do_func(0, false);
        do_func(256 * 256, true);
        System.out.println("-------All test completed-------");
    }

    private static void do_func(int n, boolean expected) {
        boolean ret = isPowerOfFour(n);
        System.out.println(ret);
        System.out.println(ret == expected);
    }
}
