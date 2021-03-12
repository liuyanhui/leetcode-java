package leetcode;

/**
 * 171. Excel Sheet Column Number
 * Easy
 * --------------
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 * For example:
 *
 *     A -> 1
 *     B -> 2
 *     C -> 3
 *     ...
 *     Z -> 26
 *     AA -> 27
 *     AB -> 28
 *     ...
 * Example 1:
 * Input: "A"
 * Output: 1
 *
 * Example 2:
 * Input: "AB"
 * Output: 28
 *
 * Example 3:
 * Input: "ZY"
 * Output: 701
 *
 * Constraints:
 * 1 <= s.length <= 7
 * s consists only of uppercase English letters.
 * s is between "A" and "FXSHRXW".
 */
public class Excel_Sheet_Column_Number_171 {
    /**
     * 递归法，公式为：
     * T(a) =  a
     * T(ab) = T(a) * 26 + b
     * T(abc)= T(ab) * 26 + c = (T(a) * 26 + b) * 26 + c
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Number.
     * Memory Usage: 39 MB, less than 62.48% of Java online submissions for Excel Sheet Column Number.
     *
     * @param s
     * @return
     */
    public static int titleToNumber(String s) {
        if (s == null || s.length() == 0) return 0;
        int ret = 0;
        if (s.length() == 1) {
            ret = s.charAt(0) - 64;
        } else {
            ret = titleToNumber(s.substring(0, s.length() - 1)) * 26 + (s.charAt(s.length() - 1) - 64);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("A", 1);
        do_func("AB", 28);
        do_func("ZY", 701);
        do_func("ZZ", 702);
        do_func("AAA", 703);
        do_func("FXSHRXW", 2147483647);
    }

    private static void do_func(String s, int expected) {
        int ret = titleToNumber(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
