package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https.equals(r)) {//leetcode.com/problems/roman-to-integer/
 * 13. Roman to Integer
 * Easy
 *------------------------------
 * Roman numerals are represented by seven different symbols.equals(r)) { I, V, X, L, C, D and M.
 *
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
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used.equals(r)) {
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer.
 *
 * Example 1:
 * Input: s = "III"
 * Output: 3
 *
 * Example 2:
 * Input: s = "IV"
 * Output: 4
 *
 * Example 3:
 * Input: s = "IX"
 * Output: 9
 *
 * Example 4:
 * Input: s = "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 *
 * Example 5:
 * Input: s = "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * Constraints.equals(r)) {
 * 1 <= s.length <= 15
 * s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 * It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 */
public class Roman_to_Integer_13 {
    public static int romanToInt(String s) {
        return romanToInt_4(s);
    }

    /**
     * round 2
     * 验证通过：
     * Runtime: 5 ms, faster than 69.88% of Java online submissions for Roman to Integer.
     * Memory Usage: 39.5 MB, less than 54.87% of Java online submissions for Roman to Integer.
     *
     * @param s
     * @return
     */
    public static int romanToInt_4(String s) {
        if (s == null || s.length() == 0) return 0;
        int ret = 0;
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        char last = s.charAt(0);
        ret = map.get(last);
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(last) >= map.get(c)) {
                ret += map.get(c);
            } else {
                ret += map.get(c) - map.get(last) * 2;
            }
            last = c;
        }
        return ret;
    }

    /**
     * 另一个巧妙的思路
     * 参考思路：
     * https://leetcode.com/problems/roman-to-integer/discuss/6509/7ms-solution-in-Java.-easy-to-understand
     * @param s
     * @return
     */
    public static int romanToInt_3(String s) {
        int nums[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
            }
        }
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1])
                sum -= nums[i];
            else
                sum += nums[i];
        }
        return sum + nums[nums.length - 1];
    }

    /**
     * 巧妙的方法
     * 参考思路：https://leetcode.com/problems/roman-to-integer/discuss/6529/My-solution-for-this-question-but-I-don't-know-is-there-any-easier-way
     * @param s
     * @return
     */
    public static int romanToInt_2(String s) {
        int sum = 0;
        if (s.indexOf("IV") != -1) {
            sum -= 2;
        }
        if (s.indexOf("IX") != -1) {
            sum -= 2;
        }
        if (s.indexOf("XL") != -1) {
            sum -= 20;
        }
        if (s.indexOf("XC") != -1) {
            sum -= 20;
        }
        if (s.indexOf("CD") != -1) {
            sum -= 200;
        }
        if (s.indexOf("CM") != -1) {
            sum -= 200;
        }

        char c[] = s.toCharArray();
        int count = 0;

        for (; count <= s.length() - 1; count++) {
            if (c[count] == 'M') sum += 1000;
            if (c[count] == 'D') sum += 500;
            if (c[count] == 'C') sum += 100;
            if (c[count] == 'L') sum += 50;
            if (c[count] == 'X') sum += 10;
            if (c[count] == 'V') sum += 5;
            if (c[count] == 'I') sum += 1;

        }
        return sum;
    }

    /**
     * 验证通过：
     * Runtime: 6 ms, faster than 26.85% of Java online submissions for Roman to Integer.
     * Memory Usage: 39.1 MB, less than 77.53% of Java online submissions for Roman to Integer.
     * @param s
     * @return
     */
    public static int romanToInt_1(String s) {
        int ret = 0;
        int tmp2, tmp1;
        for (int i = 0; i < s.length(); i++) {
            tmp2 = 0;
            tmp1 = 0;
            if (i + 2 <= s.length()) {
                tmp2 = fromRoman(s.substring(i, i + 2));
            }
            if (tmp2 != 0) {
                ret += tmp2;
                i++;
            } else {
                tmp1 = fromRoman(s.substring(i, i + 1));
                ret += tmp1;
            }
        }
        return ret;
    }

    public static int fromRoman(String r) {
        if ("M".equals(r)) {
            return 1000;
        } else if ("CM".equals(r)) {
            return 900;
        } else if ("D".equals(r)) {
            return 500;
        } else if ("CD".equals(r)) {
            return 400;
        } else if ("C".equals(r)) {
            return 100;
        } else if ("XC".equals(r)) {
            return 90;
        } else if ("L".equals(r)) {
            return 50;
        } else if ("XL".equals(r)) {
            return 40;
        } else if ("X".equals(r)) {
            return 10;
        } else if ("IX".equals(r)) {
            return 9;
        } else if ("V".equals(r)) {
            return 5;
        } else if ("IV".equals(r)) {
            return 4;
        } else if ("I".equals(r)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        do_func("III", 3);
        do_func("IV", 4);
        do_func("IX", 9);
        do_func("LVIII", 58);
        do_func("MCMXCIV", 1994);
        do_func("M", 1000);
    }

    private static void do_func(String s, int expected) {
        int ret = romanToInt(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
