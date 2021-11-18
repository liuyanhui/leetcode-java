package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/valid-parentheses/
 * 20. Valid Parentheses
 * Easy
 * ------------------------
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: s = "(]"
 * Output: false
 *
 * Example 4:
 * Input: s = "([)]"
 * Output: false
 *
 * Example 5:
 * Input: s = "{[]}"
 * Output: true
 *
 *  Constraints:
 * 1 <= s.length <= 10^4
 * s consists of parentheses only '()[]{}'.
 */
public class Valid_Parentheses_20 {
    public static boolean isValid(String s) {
        return isValid_3(s);
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 98.96% of Java online submissions for Valid Parentheses.
     * Memory Usage: 37.3 MB, less than 46.95% of Java online submissions for Valid Parentheses.
     *
     * @param s
     * @return
     */
    public static boolean isValid_3(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.empty() || stack.pop() != '(') {
                    return false;
                }
            } else if (c == '}') {
                if (stack.empty() || stack.pop() != '{') {
                    return false;
                }
            } else if (c == ']') {
                if (stack.empty() || stack.pop() != '[') {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    /**
     * 用Java 自带的Stack类
     * @param s
     * @return
     */
    public static boolean isValid_2(String s) {
        Stack<Character> stack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.empty()) return false;
                Character top = stack.pop();
                if (c == ')' && '(' == top) {
                } else if (c == '}' && '{' == top) {
                } else if (c == ']' && '[' == top) {
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    /**
     * 利用stack进行判断，需要考虑第一个字符为)、]、}的情况
     * 验证通过:
     * Runtime: 1 ms, faster than 98.73% of Java online submissions for Valid Parentheses.
     * Memory Usage: 36.7 MB, less than 98.43% of Java online submissions for Valid Parentheses
     * @param s
     * @return
     */
    public static boolean isValid_1(String s) {
        if (s == null || s.trim().equals("")) {
            return false;
        }
        List<Character> stack = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.add(c);
            } else {
                if (stack.size() == 0) {
                    return false;
                }
                Character top = stack.get(stack.size() - 1);
                if (c == ')' && top == '(') {
                    stack.remove(stack.size() - 1);
                } else if (c == '}' && top == '{') {
                    stack.remove(stack.size() - 1);
                } else if (c == ']' && top == '[') {
                    stack.remove(stack.size() - 1);
                } else {
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    public static void main(String[] args) {
        do_func("()", true);
        do_func("()[]{}", true);
        do_func("(]", false);
        do_func("([)]", false);
        do_func("{[]}", true);
        do_func("[([]])", false);
    }

    private static void do_func(String s, boolean expected) {
        boolean ret = isValid(s);
        System.out.println(ret);

        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
