package leetcode;

/**
 * 44. Wildcard Matching
 * Hard
 * ---------------------------
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
 *  - '?' Matches any single character.
 *  - '*' Matches any sequence of characters (including the empty sequence).
 *
 * The matching should cover the entire input string (not partial).
 *
 * Example 1:
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 * Input: s = "aa", p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.
 *
 * Example 3:
 * Input: s = "cb", p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 *
 * Constraints:
 * 0 <= s.length, p.length <= 2000
 * s contains only lowercase English letters.
 * p contains only lowercase English letters, '?' or '*'.
 */
public class Wildcard_Matching_44 {
    public static boolean isMatch(String s, String p) {
        return isMatch_1(s, p);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1. 匹配方式不唯一，存在多中匹配方式的情况。
     * 2. 字符优先级，越精确优先级越高，优先级由高到低为：a，?，*
     * 3. 递归思路，分别针对p中的三种情况进行计算
     * 公式为f(s,i,p,j):
     * 遍历p FROM j TO len(p)
     * 	IF p[j]=='?' AND i+1<len(s) THEN i++,j++
     * 	IF i<len(s) AND p[j]=='a' AND s[j]=='a' THEN i++,j++
     * 	IF p[j]=='*' THEN f(s,i, p,j+1),f(s,i+1,p,j)
     * IF i==len(s) AND j==len(p) THEN return true
     *
     * 更巧妙、更优雅、更高效的方法，
     * https://leetcode.com/problems/wildcard-matching/solutions/17810/linear-runtime-and-constant-space-solution/
     * 总结如下：
     * 分为四种情况分别考虑：
     * 1.p[j]==?或p[j]==s[i]的情况（当前字符根据?匹配或字符相同匹配）==> i++,j++
     * 2.p[j]==*的情况（当前字符根据*匹配）==> j++, 缓存*开始的的位置（包括si和pj）
     * 3.不满足1.和2.，但是p[j-1]==*的情况, ==> i++，更新si和pj
     * 4.其他情况 ==> return false
     *
     * 验证通过：
     * Runtime 42 ms Beats 8.51% of users with Java
     * Memory 47.21 MB Beats 15.47% of users with Java
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch_1(String s, String p) {
        int[][] cache = new int[s.length() + 1][p.length() + 1];
        return helper(s, 0, p, 0, cache);
    }

    private static boolean helper(String s, int begS, String p, int begP, int[][] cache) {
        if (begS > s.length() || begP > p.length()) return false;
        if (cache[begS][begP] != 0) return cache[begS][begP] == 1;
        int i = begS;
        int j = begP;
        while (j < p.length()) {
            if (p.charAt(j) == '?') {
                if (i >= s.length()) {
                    cache[begS][begP] = -1;
                    return false;
                }
                i++;
            } else if ('a' <= p.charAt(j) && p.charAt(j) <= 'z') {
                if (i < s.length() && p.charAt(j) != s.charAt(i)) {
                    cache[begS][begP] = -1;
                    return false;
                }
                i++;
            } else if (p.charAt(j) == '*') {
                if (helper(s, i, p, j + 1, cache) || helper(s, i + 1, p, j, cache)) {
                    cache[begS][begP] = 1;
                    return true;
                }
            }
            j++;
        }
        cache[begS][begP] = (i == s.length() && j == p.length()) ? 1 : -1;
        return cache[begS][begP] == 1;
    }

    public static void main(String[] args) {
        do_func("aa", "a", false);
        do_func("", "*", true);
        do_func("a", "*", true);
        do_func("aa", "*", true);
        do_func("a", "a", true);
        do_func("abcs", "abcs", true);
        do_func("cb", "?a", false);
        do_func("abcabcabc", "*a*", true);
        do_func("abcabcabcza", "*a", true);
        do_func("abcabcabcdd", "*a*d?", true);
        do_func("abcabcdabcdd", "*a*cd*?", true);
        do_func("abcabcdabcd", "*a*cd", true);
        do_func("acdcb", "a*c?b", false);
        do_func("babaaababaabababbbbbbaabaabbabababbaababbaaabbbaaab", "***bba**a*bbba**aab**b", false);
    }

    private static void do_func(String s, String t, boolean expected) {
        boolean ret = isMatch(s, t);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
