package leetcode;

import java.util.Stack;

/**
 * 394. Decode String
 * Medium
 * ------------------------
 * Given an encoded string, return its decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
 *
 * Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
 *
 * Example 1:
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 *
 * Example 2:
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 *
 * Example 3:
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 * Example 4:
 * Input: s = "abc3[cd]xyz"
 * Output: "abccdcdcdxyz"
 *
 * Constraints:
 * 1 <= s.length <= 30
 * s consists of lowercase English letters, digits, and square brackets '[]'.
 * s is guaranteed to be a valid input.
 * All the integers in s are in the range [1, 300].
 */
public class Decode_String_394 {
    public static String decodeString(String s) {
        return decodeString_1(s);
    }

    /**
     * 按字符处理：
     * [：入栈空字符串和数字
     * ]：出栈字符串和数字，拼接字符串，追加到栈顶
     * 字母：追加到栈顶
     * 数字：合并
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 72.73% of Java online submissions for Decode String.
     * Memory Usage: 39.3 MB, less than 12.26% of Java online submissions for Decode String.
     *
     * @param s
     * @return
     */
    public static String decodeString_1(String s) {
        Stack<StringBuilder> stackStr = new Stack<>();
        Stack<Integer> stackTimes = new Stack<>();
        stackStr.push(new StringBuilder());

        int times = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                times = times * 10 + (c - '0');
            } else if ('a' <= c && c <= 'z') {
                stackStr.peek().append(String.valueOf(c));
            } else if (c == '[') {
                stackStr.push(new StringBuilder());
                stackTimes.push(times);
                times = 0;
            } else {
                StringBuilder seed = stackStr.pop();
                int count = stackTimes.pop();
                for (int j = 0; j < count; j++) {
                    stackStr.peek().append(seed.toString());
                }
            }
        }

        return stackStr.pop().toString();
    }

    public static void main(String[] args) {
        do_func("3[a]2[bc]", "aaabcbc");
        do_func("3[a2[c]]", "accaccacc");
        do_func("2[abc]3[cd]ef", "abcabccdcdcdef");
        do_func("abc3[cd]xyz", "abccdcdcdxyz");
        do_func("10[a]2[bc]", "aaaaaaaaaabcbc");
        do_func("a", "a");
        do_func("cvbn", "cvbn");
    }

    private static void do_func(String s, String expected) {
        String ret = decodeString(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
