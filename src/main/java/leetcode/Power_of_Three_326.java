package leetcode;

/**
 * 326. Power of Three
 * Easy
 * ------------------------
 * Given an integer n, return true if it is a power of three. Otherwise, return false.
 * An integer n is a power of three, if there exists an integer x such that n == 3^x.
 * <p>
 * Example 1:
 * Input: n = 27
 * Output: true
 * Explanation: 27 = 3^3
 * <p>
 * Example 2:
 * Input: n = 0
 * Output: false
 * Explanation: There is no x where 3^x = 0.
 * <p>
 * Example 3:
 * Input: n = -1
 * Output: false
 * Explanation: There is no x where 3^x = (-1).
 * <p>
 * Constraints:
 * -2^31 <= n <= 2^31 - 1
 * <p>
 * Follow up: Could you solve it without loops/recursion?
 */
public class Power_of_Three_326 {

    public static boolean isPowerOfThree(int n) {
        return isPowerOfThree_r3_2(n);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * 1. isPowerOfThree_1()是循环方案
     * 2. 本方法是递归方案
     * 3. isPowerOfThree_r3_2()是数学方案
     * 验证通过：
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree_r3_1(int n) {
        if (n == 1) return true;
        if (n <= 0 || n % 3 != 0) return false;
        return isPowerOfThree(n / 3);
    }

    public static boolean isPowerOfThree_r3_2(int n) {
        int t = (int) Math.pow(3, 19);
        return n > 0 && t % n == 0;
    }

    /**
     * round 2
     * isPowerOfThree_2() is the best solution that uses a mathematics trick
     */

    /**
     * 还有几个通过数学方式的方案：
     * https://leetcode.com/problems/power-of-three/solution/
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree_2(int n) {
        // 1162261467 is 3^19,  3^20 is bigger than int
        return (n > 0 && 1162261467 % n == 0);
    }

    /**
     * https://leetcode.com/problems/power-of-three/discuss/77876/**-A-summary-of-all-solutions-(new-method-included-at-15%3A30pm-Jan-8th)
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree_3(int n) {
        return n == 0 ? false : n == Math.pow(3, Math.round(Math.log(n) / Math.log(3)));
    }

    /**
     * 验证通过：
     * Runtime: 11 ms, faster than 65.13% of Java online submissions for Power of Three.
     * Memory Usage: 39 MB, less than 33.19% of Java online submissions for Power of Three.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfThree_1(int n) {
        while (n > 1 && (n / 3) * 3 == n) {
            n /= 3;
        }
        return n == 1;
    }

    public static void main(String[] args) {
        do_func(27, true);
        do_func(0, false);
        do_func(9, true);
        do_func(45, false);
        do_func(-27, false);
        do_func(-9, false);
        do_func(1, true);
    }

    private static void do_func(int n, boolean expected) {
        boolean ret = isPowerOfThree(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
