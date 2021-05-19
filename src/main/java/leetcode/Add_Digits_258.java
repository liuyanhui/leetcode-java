package leetcode;

/**
 * 258. Add Digits
 * Easy
 * ---------------
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 *
 * Example 1:
 * Input: num = 38
 * Output: 2
 * Explanation: The process is
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * Since 2 has only one digit, return it.
 *
 * Example 2:
 * Input: num = 0
 * Output: 0
 *
 * Constraints:
 * 0 <= num <= 2^31 - 1
 *
 * Follow up: Could you do it without any loop/recursion in O(1) runtime?
 */
public class Add_Digits_258 {
    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Add Digits.
     * Memory Usage: 35.7 MB, less than 96.92% of Java online submissions for Add Digits.
     *
     * @param num
     * @return
     */
    public static int addDigits(int num) {
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
    }

    private static void do_func(int num, int expected) {
        int ret = addDigits(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
