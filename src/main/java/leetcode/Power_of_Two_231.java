package leetcode;

/**
 * 231. Power of Two
 * Easy
 * ----------------
 * Given an integer n, return true if it is a power of two. Otherwise, return false.
 *
 * An integer n is a power of two, if there exists an integer x such that n == 2^x.
 *
 * Example 1:
 * Input: n = 1
 * Output: true
 * Explanation: 2^0 = 1
 *
 * Example 2:
 * Input: n = 16
 * Output: true
 * Explanation: 2^4 = 16
 *
 * Example 3:
 * Input: n = 3
 * Output: false
 *
 * Example 4:
 * Input: n = 4
 * Output: true
 *
 * Example 5:
 * Input: n = 5
 * Output: false
 *
 * Constraints:
 * -2^31 <= n <= 2^31 - 1
 *
 * Follow up: Could you solve it without loops/recursion?
 */
public class Power_of_Two_231 {
    public static boolean isPowerOfTwo(int n) {
        return isPowerOfTwo_4(n);
    }

    /**
     * AC 中更巧妙的方法
     *
     * @param n
     * @return
     */
    public boolean isPowerOfTwo_5(int n) {
        if (n == 0 || n == 3) return false;
        while (n % 2 == 0) {
            n = n / 2;
        }
        return (n == 1);
    }

    /**
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 71.39% of Java online submissions for Power of Two.
     * Memory Usage: 41.2 MB, less than 66.41% of Java online submissions for Power of Two.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo_4(int n) {
        int t = 0;
        for (int i = 0; i < 32; i++) {
            t = 1 << i;
            if (t == n) return true;
            if (t > n) break;
        }
        return false;
    }

    public static boolean isPowerOfTwo_3(int n) {
        if (n == 1) return true;
        int t = 1;
        for (int i = 1; i < 32; i++) {
            t = t * 2;
            if (t == n) return true;
            if (t > n) break;
        }
        return false;
    }

    /**
     * Bit Manipulation
     * 2的幂的二进制为100000...，用bit运算即可
     * 如：二进制运算：1000-1=111 ，那么1000&111=0
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.97% of Java online submissions for Power of Two.
     * Memory Usage: 36.1 MB, less than 30.82% of Java online submissions for Power of Two.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo_2(int n) {
        if (n <= 0) return false;
        return (n & (n - 1)) == 0;
    }

    /**
     * 迭代法
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 8.88% of Java online submissions for Power of Two.
     * Memory Usage: 36.2 MB, less than 30.82% of Java online submissions for Power of Two.
     *
     * @param n
     * @return
     */
    public static boolean isPowerOfTwo_1(int n) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i > n || Math.pow(2, i) > Integer.MAX_VALUE)
                break;
            if (Math.pow(2, i) == n)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(1, true);
        do_func(2, true);
        do_func(3, false);
        do_func(4, true);
        do_func(5, false);
        do_func(-4, false);
        do_func(0, false);
        do_func(Integer.MAX_VALUE, false);

    }

    private static void do_func(int num, boolean expected) {
        boolean ret = isPowerOfTwo(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
