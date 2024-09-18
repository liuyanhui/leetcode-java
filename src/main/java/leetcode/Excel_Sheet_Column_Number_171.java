package leetcode;

/**
 * 171. Excel Sheet Column Number
 * Easy
 * --------------
 * Given a string columnTitle that represents the column title as appears in an Excel sheet, return its corresponding column number.
 * For example:
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * ...
 * <p>
 * Example 1:
 * Input: "A"
 * Output: 1
 * <p>
 * Example 2:
 * Input: "AB"
 * Output: 28
 * <p>
 * Example 3:
 * Input: "ZY"
 * Output: 701
 * <p>
 * Constraints:
 * 1 <= columnTitle.length <= 7
 * columnTitle consists only of uppercase English letters.
 * columnTitle is in the range ["A", "FXSHRXW"].
 */
public class Excel_Sheet_Column_Number_171 {

    public static int titleToNumber(String s) {
        return titleToNumber_r3_1(s);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 26进制专车10进制，没有数字0
     * A = 1
     * B = 2
     * AB = 26*1+2
     * ZY = 26*26+25
     * ZYA = 26*26*26+25*26+1
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 90.86%
     * Memory 41.99 MB Beats 82.80%
     *
     * @param columnTitle
     * @return
     */
    public static int titleToNumber_r3_1(String columnTitle) {
        int ret = 0;
        for (int i = 0; i < columnTitle.length(); i++) {
            ret = ret * 26 + (columnTitle.charAt(i) - 'A' + 1);
        }
        return ret;
    }

    /**
     * 迭代思路
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Number.
     * Memory Usage: 42.8 MB, less than 44.15% of Java online submissions for Excel Sheet Column Number.
     *
     * @param s
     * @return
     */
    public static int titleToNumber_3(String s) {
        int ret = 0;
        for (int i = 0; i < s.length(); i++) {
            ret = ret * 26 + s.charAt(i) - 'A' + 1;
        }
        return ret;
    }

    /**
     * round 2
     * 递归思路
     * 类似于2进制转10进制的算法，只不过不需要考虑0的情况
     * <p>
     * 公式为:F(s)=F(s[0:n-1])+value[s[n]]
     * ZY -> 701
     * 26*[Z]+[Y]=26*26+25
     * AA -> 27
     * 26*[A]+[A]=26*1+1=27
     * Z -> 26
     * [Z]=26
     * AAA
     * 26*(F(AA))+[A]=26*(s6*[A]+[A])+[A]
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 77.21% of Java online submissions for Excel Sheet Column Number.
     * Memory Usage: 43.3 MB, less than 13.89% of Java online submissions for Excel Sheet Column Number.
     *
     * @param s
     * @return
     */
    public static int titleToNumber_2(String s) {
        if (s == null || s.length() == 0) return 0;
        int ret = 0;
        if (s.length() > 1) {
            ret = titleToNumber(s.substring(0, s.length() - 1)) * 26;
        }
        return ret + s.charAt(s.length() - 1) - 'A' + 1;
    }

    /**
     * 递归法，公式为：
     * T(a) =  a
     * T(ab) = T(a) * 26 + b
     * T(abc)= T(ab) * 26 + c = (T(a) * 26 + b) * 26 + c
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Number.
     * Memory Usage: 39 MB, less than 62.48% of Java online submissions for Excel Sheet Column Number.
     *
     * @param s
     * @return
     */
    public static int titleToNumber_1(String s) {
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
