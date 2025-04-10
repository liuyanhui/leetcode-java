package leetcode;

import java.util.Stack;

/**
 * 1081. Smallest Subsequence of Distinct Characters
 * Medium
 * --------------------------
 * Return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.
 *
 * Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/
 *
 * Example 1:
 * Input: s = "bcabc"
 * Output: "abc"
 *
 * Example 2:
 * Input: s = "cbacdcbc"
 * Output: "acdb"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consists of lowercase English letters.
 */
public class Smallest_Subsequence_of_Distinct_Characters_1081 {
    /**
     * 单调栈系列问题。316，321，402，1081是相同的方案。
     * 栈中的元素已经是局部最优解，即已经是去处重复字母后满足字典序中的最小值。
     *
     * [group] 与 Smallest_Subsequence_of_Distinct_Characters_1081 一毛一样
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 92.69% .
     * Memory Usage: 37 MB, less than 82.11% .
     *
     * @param s
     * @return
     */
    public static String smallestSubsequence(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        Stack<Character> stack = new Stack<>();
        int[] appeared = new int[26];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //栈中的元素已经是局部最优解，即已经是去处重复字母后满足字典序中的最小值。所以重复出现的字母直接忽略。
            if (appeared[c - 'a'] <= 0) {
                while (!stack.empty() && stack.peek() > c && count[stack.peek() - 'a'] > 0) {
                    appeared[stack.peek() - 'a']--;
                    stack.pop();
                }
                stack.push(c);
                appeared[c - 'a']++;
            }
            count[c - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        do_func("bcabc", "abc");
        do_func("cbacdcbc", "acdb");
        do_func("aaaaabaaaaa", "ab");
        do_func("bcbcbcababa", "bca");
    }

    private static void do_func(String s, String expected) {
        String ret = smallestSubsequence(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
