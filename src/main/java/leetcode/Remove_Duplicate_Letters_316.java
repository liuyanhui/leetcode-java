package leetcode;

import java.util.Stack;

/**
 * 316. Remove Duplicate Letters
 * Medium
 * -------------------------
 * Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.
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
 * 1 <= s.length <= 10^4
 * s consists of lowercase English letters.
 *
 * Note: This question is the same as 1081: https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
 */
public class Remove_Duplicate_Letters_316 {
    /**
     * 参考思路：
     * https://leetcode-cn.com/problems/remove-duplicate-letters/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-4/
     * https://leetcode.com/problems/remove-duplicate-letters/discuss/76769/Java-solution-using-Stack-with-comments
     *
     * 思路：
     * 1.使用单调栈，确保栈中的字符满足局部最优解，即按照字母序+原始串中的先后顺序存放
     * 2.因为栈中已经是局部最优解，所以只需要考虑下一个字母和栈顶字母的情况即可。这样降低了算法复杂度。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 78.66% of Java online submissions for Remove Duplicate Letters.
     * Memory Usage: 39.4 MB, less than 23.36% of Java online submissions for Remove Duplicate Letters.
     *
     * @param s
     * @return
     */
    public static String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        int[] stackCached = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stackCached[c - 'a'] <=0) {
                while (!stack.empty() && stack.peek() > c && count[stack.peek() - 'a'] > 0) {
                    stackCached[stack.pop() - 'a']--;
                }
                stack.push(c);
                stackCached[c - 'a']++;
            }
            count[c - 'a']--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(String.valueOf(stack.pop()));
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        do_func("bcabc", "abc");
        do_func("cbacdcbc", "acdb");
        do_func("bcabac", "abc");
        do_func("bcabbbbbbbc", "abc");
    }

    private static void do_func(String s, String expected) {
        String ret = removeDuplicateLetters(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
