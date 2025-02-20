package leetcode;

import java.util.*;

/**
 * 301. Remove Invalid Parentheses
 * Hard
 * ------------------------------
 * Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.
 * Return all the possible results. You may return the answer in any order.
 * <p>
 * Example 1:
 * Input: s = "()())()"
 * Output: ["(())()","()()()"]
 * <p>
 * Example 2:
 * Input: s = "(a)())()"
 * Output: ["(a())()","(a)()()"]
 * <p>
 * Example 3:
 * Input: s = ")("
 * Output: [""]
 * <p>
 * Constraints:
 * 1 <= s.length <= 25
 * s consists of lowercase English letters and parentheses '(' and ')'.
 * There will be at most 20 parentheses in s.
 */
public class Remove_Invalid_Parentheses_301 {
    public static List<String> removeInvalidParentheses(String s) {
        return removeInvalidParentheses_r3_1(s);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     * <p>
     * removeInvalidParentheses_r3_1() 是源自下面链接的源代码。很难理解。
     * https://leetcode.com/problems/remove-invalid-parentheses/solutions/75027/easy-short-concise-and-fast-java-dfs-3-ms-solution/
     *
     * 下面的方案是removeInvalidParentheses_1()思路的BFS实现。很好理解，但是性能一般
     * Score[2] Lower is harder
     * https://leetcode.com/problems/remove-invalid-parentheses/solutions/75032/share-my-java-bfs-solution/
     *
     * @param s
     * @return
     */
    public static List<String> removeInvalidParentheses_r3_1(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    public static void remove(String s, List<String> ans, int last_query_i, int last_del_j,  char[] par) {
        for (int stack = 0, i = last_query_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]) stack++;
            if (s.charAt(i) == par[1]) stack--;
            if (stack >= 0) continue;
            //review 这里很关键。从第一个')'出现的位置开始删除，而不仅仅是当前位置i
            for (int j = last_del_j; j <= i; ++j)
                // review 从 last_j 开始寻找可以删除的右括号位置。为了避免生成重复的结果，只有当找到的第一个右括号或者连续右括号序列中的第一个右括号才会被考虑删除。
                if (s.charAt(j) == par[1] && (j == last_del_j || s.charAt(j - 1) != par[1]))
                    remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
            return;
        }
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') // finished left to right
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        else // finished right to left
            ans.add(reversed);
    }

    /**
     * 思考：
     * 1.满足条件的结果中'('和')'是成对出现的，并且'('先于')'出现。
     * 2.忽略字母，只处理'('和')'
     * 3.暴力法。第一轮，去掉0个括号；第二轮，依次去掉1个括号；第三轮，依次去掉2个括号;..;第n轮...。当其中某一轮存在解时，直接返回该轮计算的所有解。
     * 4.问题分解：判断字符串是否为有效字符串；去掉字符串中的一个或多个字符；组装结果集
     * <p>
     * 递归思路
     * <p>
     * round 3 : 统一的思考，但是是BFS实现。
     * https://leetcode.com/problems/remove-invalid-parentheses/solutions/75032/share-my-java-bfs-solution/
     * <p>
     * 时间更优的一个方案：
     * https://leetcode.com/problems/remove-invalid-parentheses/solutions/75027/easy-short-concise-and-fast-java-dfs-3-ms-solution/
     * <p>
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
        do_func("(())))((()", Arrays.asList("(())()"));
        do_func("()(()()", Arrays.asList("()(())", "()()()"));
    }

    private static void do_func(String s, List<String> expected) {
        Collections.sort(expected);
        List<String> ret = removeInvalidParentheses(s);
        Collections.sort(ret);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
