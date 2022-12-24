package leetcode;

import java.util.*;

/**
 * 301. Remove Invalid Parentheses
 * Hard
 * ------------------------------
 * Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.
 * Return all the possible results. You may return the answer in any order.
 *
 * Example 1:
 * Input: s = "()())()"
 * Output: ["(())()","()()()"]
 *
 * Example 2:
 * Input: s = "(a)())()"
 * Output: ["(a())()","(a)()()"]
 *
 * Example 3:
 * Input: s = ")("
 * Output: [""]
 *
 * Constraints:
 * 1 <= s.length <= 25
 * s consists of lowercase English letters and parentheses '(' and ')'.
 * There will be at most 20 parentheses in s.
 */
public class Remove_Invalid_Parentheses_301 {
    public static List<String> removeInvalidParentheses(String s) {
        return removeInvalidParentheses_1(s);
    }

    /**
     * 思考：
     * 1.满足条件的结果中'('和')'是成对出现的，并且'('先于')'出现。
     * 2.忽略字母，只处理'('和')'
     * 3.暴力法。第一轮，去掉0个括号；第二轮，依次去掉1个括号；第三轮，依次去掉2个括号;..;第n轮...。当其中某一轮存在解时，直接返回该轮计算的所有解。
     * 4.问题分解：判断字符串是否为有效字符串；去掉字符串中的一个或多个字符；组装结果集
     *
     * 递归思路
     *
     * 时间更优的一个方案：
     * https://leetcode.com/problems/remove-invalid-parentheses/solutions/75027/easy-short-concise-and-fast-java-dfs-3-ms-solution/
     *
     * 验证通过：
     * Runtime 541 ms Beats 9.71%
     * Memory 42.4 MB Beats 86.3%
     *
     * @param s
     * @return
     */
    public static List<String> removeInvalidParentheses_1(String s) {
        Set<String> res = new HashSet<>();
        char[] arr = s.toCharArray();
        for (int i = 0; i <= arr.length; i++) {
            helper(arr, 0, i, res);
            if (res.size() > 0) break;
        }
        return new ArrayList<>(res);
    }

    private static void helper(char[] arr, int beg, int cnt, Set<String> res) {
        if (cnt == 0) {
            if (check(arr)) res.add(convert(arr));
            return;
        }
        for (int i = beg; i < arr.length; i++) {
            if (arr[i] == '(') {
                arr[i] = '1';
                helper(arr, i, cnt - 1, res);
                arr[i] = '(';
            } else if (arr[i] == ')') {
                arr[i] = '2';
                helper(arr, i, cnt - 1, res);
                arr[i] = ')';
            }
        }
    }

    private static String convert(char[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != '1' && arr[i] != '2')
                sb.append(arr[i]);
        }
        return sb.toString();
    }

    private static boolean check(char[] arr) {
        if (arr == null || arr.length == 0) return false;
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != '(' && arr[i] != ')') continue;
            if (arr[i] == '(') {
                cnt++;
            } else if (arr[i] == ')') {
                cnt--;
            }
            if (cnt < 0) return false;
        }
        return cnt == 0;
    }

    public static void main(String[] args) {
        do_func("()())()", Arrays.asList("(())()", "()()()"));
        do_func("(a)())()", Arrays.asList("(a())()", "(a)()()"));
        do_func(")(", new ArrayList<String>() {{
            add("");
        }});
        do_func("(", new ArrayList<String>() {{
            add("");
        }});
        do_func("()", Arrays.asList("()"));
        do_func("))(((((()())(()", Arrays.asList("(((())))", "(()())()", "((()()))", "((()))()"));
    }

    private static void do_func(String s, List<String> expected) {
        List<String> ret = removeInvalidParentheses(s);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
