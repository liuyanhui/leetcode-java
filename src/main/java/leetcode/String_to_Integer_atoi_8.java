package leetcode;

/**
 * https://leetcode.com/problems/string-to-integer-atoi/
 *
 * 8. String to Integer (atoi)
 *
 * Medium
 *
 * ----------------------------
 * Implement atoi which converts a string to an integer.
 *
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
 *
 * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
 *
 * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 *
 * If no valid conversion could be performed, a zero value is returned.
 *
 * Note:
 * Only the space character ' ' is considered a whitespace character.
 * Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, 231 − 1 or −231 is returned.
 *
 *
 * Example 1:
 * Input: str = "42"
 * Output: 42
 *
 * Example 2:
 * Input: str = "   -42"
 * Output: -42
 * Explanation: The first non-whitespace character is '-', which is the minus sign. Then take as many numerical digits as possible, which gets 42.
 *
 * Example 3:
 * Input: str = "4193 with words"
 * Output: 4193
 * Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
 *
 * Example 4:
 * Input: str = "words and 987"
 * Output: 0
 * Explanation: The first non-whitespace character is 'w', which is not a numerical digit or a +/- sign. Therefore no valid conversion could be performed.
 *
 * Example 5:
 * Input: str = "-91283472332"
 * Output: -2147483648
 * Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer. Thefore INT_MIN (−231) is returned.
 *
 * Constraints:
 * 0 <= s.length <= 200
 * s consists of English letters (lower-case and upper-case), digits, ' ', '+', '-' and '.'.
 */
public class String_to_Integer_atoi_8 {
    public static int myAtoi(String s) {
        return myAtoi_2(s);
    }

    /**
     * 相对myAtoi_1(),做了以下优化：
     * 提取数字后，从左向右遍历字符串，然后与MAX或MIN比较即可
     * 参考思路：
     * https://leetcode.com/problems/string-to-integer-atoi/discuss/4643/Java-Solution-with-4-steps-explanations
     * -----------------
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for String to Integer (atoi).
     * Memory Usage: 39.3 MB, less than 19.71% of Java online submissions for String to Integer (atoi).
     * @param s
     * @return
     */
    public static int myAtoi_2(String s) {
        if (s == null || s == "" || s.trim() == "") {
            return 0;
        }
        int ret = 0;
        int head = 0;
        int positive = 1;
        //去掉开头的空格
        while (head < s.length() && s.charAt(head) == ' ') {
            head++;
        }
        //判断第一个非空字符的正负和是否为数字
        if (head < s.length() && s.charAt(head) == '+') {
            head++;
        } else if (head < s.length() && s.charAt(head) == '-') {
            positive = -1;
            head++;
        } /*else if (!Character.isDigit(s.charAt(head))) {//非数字字符
            return 0;
        }*/
        //提取数字，并累加，然后判断是否越界
        while (head < s.length()) {
            int digit = s.charAt(head) - '0';
            if (digit < 0 || digit > 9) break;

            //这里是精髓
            //check if ret will be overflow after 10 times and add digit
            if (Integer.MAX_VALUE / 10 < ret || (Integer.MAX_VALUE / 10 == ret && Integer.MAX_VALUE % 10 < digit))
                return positive == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

            ret = 10 * ret + digit;
            head++;
        }

        return ret * positive;

    }

    /**
     * 朴素思路：从前向后遍历字符串，具体见代码中的注释
     * 方案比较复杂，花了超过一个小时进行调试和试错。
     * 改进思路：提取所有数字的字符串后，使用字符串转数字的方式进行处理。详见myAtoi_2()
     * 验证通过，
     * Runtime: 7 ms, faster than 12.15% of Java online submissions for String to Integer (atoi).
     * Memory Usage: 39.1 MB, less than 38.65% of Java online submissions for String to Integer (atoi).
     * @param s
     * @return
     */
    public static int myAtoi_1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int ret = 0;
        String numStr = "";//提取的数字字符串
        int head = 0;//数字起始位
        int positive = 1;//正负标志位
        //过滤开头的为空白字符
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            head = i;
            //过滤开头的为空白字符
            if (c == ' ') {
                continue;
            } else {
                break;
            }
        }
        //第一个非空字符判断
        for (int i = head; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                positive = -1;
                head++;
                break;
            } else if (c == '+') {
                head++;
                break;
            } else if (Character.isDigit(c)) {
                break;
            } else {
                return 0;
            }
        }
        //数字字符收集
        for (int i = head; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                if (numStr.length() == 0 && c == '0') {//过滤开头为0的字符
                    continue;
                }
                numStr += c;
            } else {
                break;
            }
        }
        //数字转换,需要考虑int最大值，int最小值等边界
        if (numStr.length() > 10) {
            if (positive == 1) {
                ret = Integer.MAX_VALUE;
            } else {
                ret = Integer.MIN_VALUE;
            }
        } else if (numStr.length() == 10) {
            String pattern = "2147483647";
            boolean overflow = false;
            for (int i = 0; i < numStr.length(); i++) {
                int t = numStr.charAt(i);
                int p = pattern.charAt(i);
                if (t < p) {//小于时，在边界内，不需要继续判断
                    break;
                } else if (t == p) {//等于时需要继续比较判断
                    continue;
                } else {//大于是，在边界外，表示溢出了
                    overflow = true;
                    break;
                }
            }
            if (overflow) {
                if (positive == 1) {
                    ret = Integer.MAX_VALUE;
                } else {
                    ret = Integer.MIN_VALUE;
                }
            } else {
                ret = positive * Integer.valueOf(numStr);
            }
        } else if (numStr.length() > 0) {
            ret = positive * Integer.valueOf(numStr);
        } else {
            ret = 0;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("42", 42);
        do_func("   -42", -42);
        do_func("4193 with words", 4193);
        do_func("words and 987", 0);
        do_func("-91283472332", -2147483648);
        do_func("-2147483648", -2147483648);
        do_func("2147483647", 2147483647);
        do_func("-2147483647", -2147483647);
        do_func("+-12", 0);
        do_func("+12", 12);
        do_func("3.45678", 3);
        do_func("1095502006p8", 1095502006);
    }

    private static int do_func(String input, int expected) {
        int ret = myAtoi(input);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
        return ret;
    }
}
