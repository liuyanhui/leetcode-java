package leetcode;

/**
 * 306. Additive Number
 * Medium
 * -------------------------
 * Additive number is a string whose digits can form additive sequence.
 * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.
 * Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
 * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
 *
 * Example 1:
 * Input: "112358"
 * Output: true
 * Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
 *              1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 *
 * Example 2:
 * Input: "199100199"
 * Output: true
 * Explanation: The additive sequence is: 1, 99, 100, 199.
 *              1 + 99 = 100, 99 + 100 = 199
 *
 * Constraints:
 * num consists only of digits '0'-'9'.
 * 1 <= num.length <= 35
 * Follow up:
 * How would you handle overflow for very large input integers?
 */
public class Additive_Number_306 {
    /**
     * 参考思路：
     * https://leetcode.com/problems/additive-number/discuss/75576/0ms-concise-C%2B%2B-solution-(perfectly-handles-the-follow-up-and-leading-0s)
     * https://leetcode.com/problems/additive-number/discuss/75572/*Java*-very-straightforward-solution-with-detailed-explanation
     * https://leetcode.com/problems/additive-number/discuss/75567/Java-Recursive-and-Iterative-Solutions
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 9.58% of Java online submissions for Additive Number.
     * Memory Usage: 38.9 MB, less than 10.54% of Java online submissions for Additive Number.
     *
     * @param num
     * @return
     */
    public static boolean isAdditiveNumber(String num) {
        if (num.length() < 3) return false;
        for (int i = 1; i <= num.length() / 2; i++) {
            for (int j = i + 1; j < num.length(); j++) {
                if (check(num.substring(0, i), num.substring(i, j), num.substring(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check(String a, String b, String num) {
        if (num == null || num.equals("")) return true;
        //去掉b是0开头，位数大于1的情况，即033,0033等情况
        if (b.startsWith("0") && b.length()>1) return false;
        String c = addTwoNum(a, b);
        if (num.startsWith(c)) {
            return check(b, c, num.substring(c.length()));
        }
        return false;
    }

    private static String addTwoNum(String m, String n) {
        int overflow = 0;
        int i = 0;
        String ret = "";
        while (i < m.length() && i < n.length()) {
            int t = (m.charAt(m.length() - 1 - i) - '0') + (n.charAt(n.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        while (i < m.length()) {
            int t = (m.charAt(m.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        while (i < n.length()) {
            int t = (n.charAt(n.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        if (overflow == 1) {
            ret = "1" + ret;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("112358", true);
        do_func("112334", true);
        do_func("199100199", true);
        do_func("112358132124", false);
        do_func("112358132134", true);
        do_func("112", true);
        do_func("2", false);
        do_func("199001200", false);
        do_func("101", true);
        do_func("199001990", true);
        do_func("100010", false);
    }

    private static void do_func(String num, boolean expected) {
        boolean ret = isAdditiveNumber(num);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
