package leetcode;

/**
 * 258. Add Digits
 * Easy
 * ---------------
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 * <p>
 * Example 1:
 * Input: num = 38
 * Output: 2
 * Explanation: The process is
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * Since 2 has only one digit, return it.
 * <p>
 * Example 2:
 * Input: num = 0
 * Output: 0
 * <p>
 * Constraints:
 * 0 <= num <= 2^31 - 1
 * <p>
 * Follow up: Could you do it without any loop/recursion in O(1) runtime?
 */
public class Add_Digits_258 {

    public static int addDigits(int num) {
        return addDigits_r3_2(num);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     * <p>
     * Thinking
     * 1. loop 方案
     * 2. recursion 方案
     * 3. review O(1)方案，数学方案
     * <pre>
     * {@code
     * if (num == 0)
     *     return 0;
     * else if (num % 9 == 0)
     *     return 9;
     * else
     *     return num % 9;
     * }
     * </pre>
     * <p>
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 41.10 MB Beats 33.80%
     *
     * @param num
     * @return
     */
    public static int addDigits_r3_2(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num /= 10;
            if (num == 0 && res >= 10) {
                num = res;
                res = 0;
            }
        }
        return res;
    }

    /**
     * 验证通过；
     * Runtime 1 ms Beats 48.52%
     * Memory 41.33 MB Beats 9.71%
     *
     * @param num
     * @return
     */
    public static int addDigits_r3_1(int num) {
        int res = 0;
        while (num > 0) {
            res += num % 10;
            num /= 10;
        }
        if (res >= 10) {
            return addDigits(res);
        } else {
            return res;
        }
    }

    public static int addDigits_r3_3(int num) {
        if (num == 0)
            return 0;
        else if (num % 9 == 0)
            return 9;
        else
            return num % 9;
    }

    /**
     * 思考：
     * 1.有两部分逻辑：1.累加当前数的每个数字；2.判断1.的结果是否为一位数字
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 78.58% of Java online submissions for Add Digits.
     * Memory Usage: 41.7 MB, less than 26.68% of Java online submissions for Add Digits.
     * <p>
     * TODO 数学思路，更优解O(1)： return (num - 1) % 9 + 1;
     *
     * @param num
     * @return
     */
    public static int addDigits_2(int num) {
        if (num < 0) return 0;
        int res = num;
        while (res > 9) {
            num = res;
            res = 0;
            while (num > 0) {
                res += (num % 10);
                num /= 10;
            }
        }
        return res;
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Add Digits.
     * Memory Usage: 35.7 MB, less than 96.92% of Java online submissions for Add Digits.
     *
     * @param num
     * @return
     */
    public static int addDigits_1(int num) {
        if (num <= 0) return 0;
        int ret = num;
        while (ret >= 10) {
            ret = 0;
            while (num > 0) {
                ret += num % 10;
                num /= 10;
            }
            num = ret;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(38, 2);
        do_func(0, 0);
        do_func(5, 5);
        do_func(10, 1);
        do_func(19, 1);
        System.out.println("==================");
    }

    private static void do_func(int num, int expected) {
        int ret = addDigits(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
