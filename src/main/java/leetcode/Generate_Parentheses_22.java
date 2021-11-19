package leetcode;

import java.util.*;

/**
 * 22. Generate Parentheses
 * Medium
 * -----------------------
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 *
 * Constraints:
 * 1 <= n <= 8
 */
public class Generate_Parentheses_22 {

    public static List<String> generateParenthesis(int n) {
        return generateParenthesis_3(n);
    }

    /**
     * 套路
     * 非常巧妙的办法
     * 参考思路：
     * https://leetcode.com/problems/generate-parentheses/solution/
     *
     * 思路描述：
     * 递归法，每个递归依次放入(和)，并分别递归。通过括号数量进行约束。
     * 不需要使用Set进行去重。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java
     * Memory Usage: 39.4 MB, less than 48.80% of Java
     * @param n
     * @return
     */
    public static List<String> generateParenthesis_3(int n) {
        List<String> result = new ArrayList();
        char[] current = new char[n * 2];
        generateAll(current, 0, 0, result);
        return result;
    }

    private static void generateAll(char[] current, int leftCount, int rightCount, List<String> result) {
        int n = current.length;
        //左右括号的数量约束
        if (rightCount > leftCount || rightCount > n / 2 || leftCount > n / 2) {
            return;
        }
        if (rightCount == n / 2 && leftCount == n / 2) {
            result.add(new String(current));
            return;
        }
        current[leftCount + rightCount] = '(';//先放入左括号，保证顺序
        generateAll(current, leftCount + 1, rightCount, result);
        current[leftCount + rightCount] = ')';//再放入右括号，保证顺序
        generateAll(current, leftCount, rightCount + 1, result);
    }

    /**
     * 递归+cache
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 14.24% of Java online submissions for Generate Parentheses.
     * Memory Usage: 38.7 MB, less than 98.71% of Java online submissions for Generate Parentheses.
     *
     * @param n
     * @return
     */
    public static List<String> generateParenthesis_2(int n) {
        Set<String>[] dp = new HashSet[n + 1];
        List<String> ret = new ArrayList();
        for (String s : do_recursive(n, dp)) {
            ret.add(s);
        }
        return ret;
    }

    private static Set<String> do_recursive(int n, Set<String>[] dp) {
        if (dp[n] != null && dp[n].size() > 0) return dp[n];
        Set<String> set = new HashSet<>();
        if (n == 0) {
            set.add("");
            dp[n] = set;
            return set;
        }
        for (String s1 : do_recursive(n - 1, dp)) {
            set.add("(" + s1 + ")");
        }
        for (int i = 1; i < n; i++) {
            for (String s1 : do_recursive(i, dp)) {
                for (String s2 : do_recursive(n - i, dp)) {
                    set.add(s1 + s2);
                    set.add(s2 + s1);
                }
            }
        }
        dp[n] = set;
        return set;
    }

    /**
     * DP思路：
     * dp[i] = "("+dp[i-1]+")" + f(dp[1],dp[i-1]) + f(dp[2],dp[i-2]) + ... + f(dp[i-1],dp[1])
     * f(dp[j],dp[i-j]) 表示：分别将两个dp数组的元素进行合并操作，逻辑如下：
     * for(s1: dp[j]){
     *  for (s2:dp[i-j]){
     *      s1+s2;
     *      s2+s1;
     *  }
     * }
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 14.24% of Java
     * Memory Usage: 38.9 MB, less than 91.06% of Java
     * @param n
     * @return
     */
    public static List<String> generateParenthesis_1(int n) {
        Set<String>[] dp = new Set[n + 1];
        dp[0] = new HashSet<String>() {{
            add("");
        }};
        for (int i = 1; i <= n; i++) {
            dp[i] = new HashSet<>();
            for (String s1 : dp[i - 1]) {
                dp[i].add("(" + s1 + ")");
            }
            for (int j = 1; j < i; j++) {
                for (String s1 : dp[j]) {
                    for (String s2 : dp[i - j]) {
                        dp[i].add(s1 + s2);
                        dp[i].add(s2 + s1);
                    }
                }
            }
        }
        List<String> ret = new ArrayList();
        for (String s : dp[n]) {
            ret.add(s);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, Arrays.asList("((()))", "(()())", "(())()", "()(())", "()()()"));
        do_func(1, Arrays.asList("()"));

    }

    private static void do_func(int s, List<String> expected) {
        List<String> ret = generateParenthesis(s);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
