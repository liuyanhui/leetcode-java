package leetcode;

/**
 * 415. Add Strings
 * Easy
 * -------------------------------------
 * Given two non-negative integers, num1 and num2 represented as string, return the sum of num1 and num2 as a string.
 *
 * You must solve the problem without using any built-in library for handling large integers (such as BigInteger). You must also not convert the inputs to integers directly.
 *
 * Example 1:
 * Input: num1 = "11", num2 = "123"
 * Output: "134"
 *
 * Example 2:
 * Input: num1 = "456", num2 = "77"
 * Output: "533"
 *
 * Example 3:
 * Input: num1 = "0", num2 = "0"
 * Output: "0"
 *
 * Constraints:
 * 1 <= num1.length, num2.length <= 10^4
 * num1 and num2 consist of only digits.
 * num1 and num2 don't have any leading zeros except for the zero itself.
 */
public class Add_Strings_415 {
    public static String addStrings(String num1, String num2) {
        return addStrings_1(num1, num2);
    }

    /**
     * 验证通过：
     * Runtime 1 ms Beats 100%
     * Memory 41.2 MB Beats 97.95%
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String addStrings_1(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1, j = num2.length() - 1;
        while (i >= 0 || j >= 0 || carry > 0) {
            int a = 0, b = 0;
            if (i >= 0) {
                a = num1.charAt(i) - '0';
            }
            if (j >= 0) {
                b = num2.charAt(j) - '0';
            }
            res.append((a + b + carry) % 10);
            carry = (a + b + carry) / 10;
            i--;
            j--;
        }

        return res.reverse().toString();
    }

    public static void main(String[] args) {
        do_func("11", "123", "134");
        do_func("456", "77", "533");
        do_func("0", "0", "0");
        do_func("9", "1", "10");
        do_func("3", "2", "5");
        do_func("75382749340938403984", "897918638913913", "75383647259577317897");
        do_func("9", "99", "108");
    }

    private static void do_func(String num1, String num2, String expected) {
        String ret = addStrings(num1, num2);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
