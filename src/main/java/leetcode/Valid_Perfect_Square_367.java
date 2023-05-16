package leetcode;

/**
 * 367. Valid Perfect Square
 * Easy
 * -----------------------------
 * Given a positive integer num, return true if num is a perfect square or false otherwise.
 * A perfect square is an integer that is the square of an integer. In other words, it is the product of some integer with itself.
 * You must not use any built-in library function, such as sqrt.
 *
 * Example 1:
 * Input: num = 16
 * Output: true
 *
 * Example 2:
 * Input: num = 14
 * Output: false
 *
 * Constraints:
 * 1 <= num <= 2^31 - 1
 */
public class Valid_Perfect_Square_367 {

    public static boolean isPerfectSquare(int num) {
        return isPerfectSquare_4(num);
    }

    /**
     * round 2
     * Thinking：
     * 1.naive solution
     * 穷举法，从1开始。
     *
     * 验证通过：
     * Runtime 1 ms Beats 21.8%
     * Memory 39.3 MB Beats 80.75%
     *
     * @param num
     * @return
     */
    public static boolean isPerfectSquare_4(int num) {
        for (int i = 1; i <= num / i; i++) {
            if (i * i == num) return true;
        }
        return false;
    }

    /**
     * 参考文档：
     * https://leetcode.com/problems/valid-perfect-square/discuss/83874/A-square-number-is-1%2B3%2B5%2B7%2B...-JAVA-code
     *
     * This is a math problem：
     * 1 = 1
     * 4 = 1 + 3
     * 9 = 1 + 3 + 5
     * 16 = 1 + 3 + 5 + 7
     * 25 = 1 + 3 + 5 + 7 + 9
     * 36 = 1 + 3 + 5 + 7 + 9 + 11
     * ....
     * so 1+3+...+(2n-1) = (2n-1 + 1)n/2 = n*n
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Valid Perfect Square.
     * Memory Usage: 35.5 MB, less than 86.38% of Java online submissions for Valid Perfect Square.
     *
     * @param num
     * @return
     */
    public static boolean isPerfectSquare_2(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i += 2;
        }
        return num == 0;
    }

    /**
     * binary search
     * 参考文档：
     * https://leetcode.com/problems/valid-perfect-square/discuss/83874/A-square-number-is-1%2B3%2B5%2B7%2B...-JAVA-code
     *
     * @param num
     * @return
     */
    public static boolean isPerfectSquare_3(int num) {
        int low = 1, high = num;
        while (low <= high) {
            long mid = (low + high) >>> 1;
            if (mid * mid == num) {
                return true;
            } else if (mid * mid < num) {
                low = (int) mid + 1;
            } else {
                high = (int) mid - 1;
            }
        }
        return false;
    }

    /**
     *
     * 验证通过，性能一般：
     * Runtime: 1 ms, faster than 18.03% of Java online submissions for Valid Perfect Square.
     * Memory Usage: 35.8 MB, less than 59.63% of Java online submissions for Valid Perfect Square.
     *
     * @param num
     * @return
     */
    public static boolean isPerfectSquare_1(int num) {
        if (num <= 0) return false;
        for (int i = 1; i <= num; i++) {
            if (Integer.MAX_VALUE / i < i || i * i > num) break;
            if (i * i == num) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(16, true);
        do_func(14, false);
        do_func(64, true);
        do_func(1, true);
        do_func(Integer.MAX_VALUE, false);
    }

    private static void do_func(int num, boolean expected) {
        boolean ret = isPerfectSquare(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
