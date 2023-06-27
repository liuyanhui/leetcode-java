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
 * You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
 *
 * The test cases are generated so that the length of the output will never exceed 10^5.
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
 * Constraints:
 * 1 <= s.length <= 30
 * s consists of lowercase English letters, digits, and square brackets '[]'.
 * s is guaranteed to be a valid input.
 * All the integers in s are in the range [1, 300].
 */
public class Decode_String_394 {
    public static String decodeString(String s) {
        return decodeString_3(s);
    }

    /**
     * round 2
     *
     * 2.非递归思路，使用stack_times和stack_str。
     * 2.1.遇到[，数字和字符串分别入栈stack_times和stack_str，重置缓冲变量
     * 2.2.遇到]，stack_times出栈，生成新字符串，并追加到stack_str栈顶字符串的尾部
     * 2.3.遇到数字，计算数字
     * 2.4.遇到字母，追加到缓冲字符串尾部
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 41 MB Beats 34.30%
     *
     * @param s
     * @return
     */
    public static String decodeString_3(String s) {
        Stack<StringBuilder> stack_str = new Stack<>();
        Stack<Integer> stack_times = new Stack<>();
        int times = 0;
        StringBuilder curStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                stack_str.push(curStr);
                stack_times.push(times);
                curStr = new StringBuilder();
                times = 0;
            } else if (c == ']') {
                int len = stack_times.pop();
                for (int j = 0; j < len; j++) {
                    stack_str.peek().append(curStr.toString());
                }
                curStr = stack_str.pop();
                times = 0;
            } else if (Character.isDigit(c)) {
                times = times * 10 + (c - '0');
            } else {
                curStr.append(c);
            }
        }
        return curStr.toString();
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.递归思路
     * 1.1.遇到[，进入递归
     * 1.2.进入递归时，初始化返回字符串
     * 1.2.1.遇到字母，追加到缓冲字符串尾部
     * 1.2.2.遇到数字，计算数字
     * 1.3.遇到]，跳出递归
     * 1.4.递归结束后，根据数字和字符串生产新的字符串，追加到返回字符串尾部。
     * 2.非递归思路，使用stack
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 40.8 MB Beats 58.95%
     *
     * @param s
     * @return
     */
    public static String decodeString_2(String s) {
        StringBuilder res = new StringBuilder();
        helper(s, 0, res);
        return res.toString();
    }

    /**
     *
     * @param s
     * @param idx
     * @param res
     * @return 原始字符串的长度
     */
    private static int helper(String s, int idx, StringBuilder res) {
        int beg = idx;
        int times = 0;
        while (idx < s.length()) {
            if (s.charAt(idx) == '[') {
                StringBuilder tmp = new StringBuilder();
                int cnt = helper(s, idx + 1, tmp);
                res.append(getResStr(times, tmp.toString()));
                times = 0;
                idx += cnt;
            } else if (s.charAt(idx) == ']') {
                idx++;//这里不能省略
                break;
            } else if (Character.isDigit(s.charAt(idx))) {
                times = times * 10 + (s.charAt(idx) - '0');
            } else {
                res.append(s.charAt(idx));
            }
            idx++;
        }
        return idx - beg;
    }

    private static String getResStr(int times, String str) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < times; i++) {
            res.append(str);
        }
        return res.toString();
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
