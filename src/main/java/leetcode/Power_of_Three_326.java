package leetcode;

/**
 * 326. Power of Three
 * Easy
 * ------------------------
 * Given an integer n, return true if it is a power of three. Otherwise, return false.
 * An integer n is a power of three, if there exists an integer x such that n == 3^x.
 *
 * Example 1:
 * Input: n = 27
 * Output: true
 *
 * Example 2:
 * Input: n = 0
 * Output: false
 *
 * Example 3:
 * Input: n = 9
 * Output: true
 *
 * Example 4:
 * Input: n = 45
 * Output: false
 *
 * Constraints:
 * -2^31 <= n <= 2^31 - 1
 *
 * Follow up: Could you solve it without loops/recursion?
 */
public class Power_of_Three_326 {

    public static boolean isPowerOfThree(int n) {
        return isPowerOfThree_1(n);
    }

    /**
     * 还有几个通过数学方式的方案：
     * https://leetcode.com/problems/power-of-three/solution/
     * @param n
     * @return
     */
    public static boolean isPowerOfThree_2(int n) {
        return false;
    }

    /**
     *
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
