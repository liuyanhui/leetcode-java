package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 202. Happy Number
 * Easy
 * ------------------
 * Write an algorithm to determine if a number n is happy.
 * A happy number is a number defined by the following process:
 * 1.Starting with any positive integer, replace the number by the sum of the squares of its digits.
 * 2.Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 * 3.Those numbers for which this process ends in 1 are happy.
 *
 * Return true if n is a happy number, and false if not.
 *
 * Example 1:
 * Input: n = 19
 * Output: true
 * Explanation:
 * 1**2 + 9**2 = 82
 * 8**2 + 2**2 = 68
 * 6**2 + 8**2 = 100
 * 1**2 + 0**2 + 0**2 = 1
 *
 * Example 2:
 * Input: n = 2
 * Output: false
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 */
public class Happy_Number_202 {
    public static boolean isHappy(int n) {
        return isHappy_3(n);
    }

    /**
     * round 2
     *
     * isHappy_2()的方法更有意思。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 45.75% of Java online submissions for Happy Number.
     * Memory Usage: 41.6 MB, less than 25.94% of Java online submissions for Happy Number.
     *
     * @param n
     * @return
     */
    public static boolean isHappy_3(int n) {
        Set<Integer> set = new HashSet<>();
        while (true) {
            int t = 0;
            while (n > 0) {
                t += Math.pow(n % 10, 2);
                n /= 10;
            }
            if (t == 1) return true;
            if (set.contains(t)) return false;
            set.add(t);
            n = t;
        }
    }

    /**
     * O(1) 空间复杂度的思路，问题可以转化为Floyd Cycle Detection问题，用快慢指针发
     *
     * 参考思路：
     * https://leetcode.com/problems/happy-number/discuss/56917/My-solution-in-C(-O(1)-space-and-no-magic-math-property-involved-)
     * https://leetcode.com/problems/happy-number/discuss/56917/My-solution-in-C(-O(1)-space-and-no-magic-math-property-involved-)
     *
     * for example:
     * 1^2 + 1^2 = 2
     * 2^2 = 4 ------> notice that from here we are starting with 4
     * 4^2 = 16
     * 1^2 + 6^2 = 37
     * 3^2 + 7^2 = 58
     * 5^2 + 8^2 = 89
     * 8^2 + 9^2 = 145
     * 1^2 + 4^2 + 5^2 = 42
     * 4^2 + 2^2 = 20
     * 2^2 + 0^2 = 4 -------> notice that we just get back to 4 again
     *
     * @param n
     * @return
     */
    public static boolean isHappy_2(int n) {
        int slow = n, fast = n;
        do {
            slow = calc(slow);
            fast = calc(fast);
            fast = calc(fast);
        } while (slow != fast);
        if (slow == 1) return true;
        return false;
    }

    /**
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 85.74% of Java online submissions for Happy Number.
     * Memory Usage: 35.7 MB, less than 89.37% of Java online submissions for Happy Number.
     *
     * @param n
     * @return
     */
    public static boolean isHappy_1(int n) {
        Set<Integer> cached = new HashSet<>();
        int t = n;
        while (t >= 1) {
            if (t == 1) return true;
            t = calc(t);
            if (cached.contains(t)) {
                break;
            } else {
                cached.add(t);
            }
        }

        return false;
    }

    private static int calc(int n) {
        int ret = 0;
        while (n > 0) {
            //这里不能写成 n % 10 * n % 10，必须加括号
            ret += (n % 10) * (n % 10);
            n = n / 10;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(19, true);
        do_func(2, false);
        do_func(1, true);
        do_func(11, false);
    }

    private static void do_func(int n, boolean expected) {
        boolean ret = isHappy(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
