package leetcode;

/**
 * 12. Integer to Roman
 * Medium
 * -------------------------
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given an integer, convert it to a roman numeral.
 *
 * Example 1:
 * Input: num = 3
 * Output: "III"
 *
 * Example 2:
 * Input: num = 4
 * Output: "IV"
 *
 * Example 3:
 * Input: num = 9
 * Output: "IX"
 *
 * Example 4:
 * Input: num = 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 *
 * Example 5:
 * Input: num = 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * Constraints:
 * 1 <= num <= 3999
 */
public class Integer_to_Roman_12 {
    public static String intToRoman(int num) {
        return intToRoman_2(num);
    }

    /**
     * 巧妙的思路1
     * 参考思路：
     * https://leetcode.com/problems/integer-to-roman/discuss/6310/My-java-solution-easy-to-understand
     *
     * @param num
     * @return
     */
    public String intToRoman_3(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strs = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 巧妙的思路2
     * 参考思路：
     * https://leetcode.com/problems/integer-to-roman/discuss/6274/Simple-Solution
     * @param num
     * @return
     */
    public static String intToRoman_2(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
    }

    /**
     * 普通思路
     * 最大值只有3999
     * 1.从低位到高位分别计算
     * 2.每个位的数字分为几种情况：0~3，4，5，5~8，9
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 83.67% of Java.
     * Memory Usage: 39.1 MB, less than 58.72% of Java
     *
     * @param num
     * @return
     */
    public static String intToRoman_1(int num) {
        if (num > 3999) return "";
        StringBuilder ret = new StringBuilder();
        int t = num % 10;
        if (t > 0) {
            ret.insert(0, getStr(t, "I", "V", "X"));
        }
        t = num / 10 % 10;
        if (t > 0) {
            ret.insert(0, getStr(t, "X", "L", "C"));
        }
        t = num / 100 % 10;
        if (t > 0) {
            ret.insert(0, getStr(t, "C", "D", "M"));
        }
        t = num / 1000 % 10;
        if (0 < t && t < 4) {
            ret.insert(0, getStr(t, "M", "", ""));
        }
        return ret.toString();
    }

    private static String getStr(int n, String s1, String s5, String s10) {
        StringBuilder ret = new StringBuilder();
        if (1 <= n && n <= 3) {
            for (int i = 0; i < n; i++) {
                ret.append(s1);
            }
        } else if (n == 4) {
            ret.append(s1);
            ret.append(s5);
        } else if (n == 5) {
            ret.append(s5);
        } else if (6 <= n && n <= 8) {
            ret.append(s5);
            for (int i = 6; i <= n; i++) {
                ret.append(s1);
            }
        } else if (n == 9) {
            ret.append(s1);
            ret.append(s10);
        }
        return ret.toString();
    }

    public static void main(String[] args) {
//        do_func(3, "III");
        do_func(4, "IV");
        do_func(9, "IX");
        do_func(58, "LVIII");
        do_func(1994, "MCMXCIV");
        do_func(2000, "MM");
    }

    private static void do_func(int num, String expected) {
        String ret = intToRoman(num);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
