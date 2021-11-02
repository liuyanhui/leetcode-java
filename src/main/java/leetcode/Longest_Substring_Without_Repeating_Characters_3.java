package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Longest Substring Without Repeating Characters
 * Medium
 * --------------------------------
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Example 4:
 * Input: s = ""
 * Output: 0
 *
 * Constraints:
 * 0 <= s.length <= 5 * 10^4
 * s consists of English letters, digits, symbols and spaces.
 */
public class Longest_Substring_Without_Repeating_Characters_3 {

    /**
     * 双指针法，两个指针i,j把s分割成三部分，[0,i)是已经遍历过的，[i,j)是当前的substring,[j,s.length)是未遍历的部分。
     * 用数组cache缓存当前的substring部分，即cache保存[i,j)部分。
     * 当cache中存在s[j]时：1.计算最长子串长度；2.循环执行i++直到s[i]==s[j]。
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 85.21% of Java .
     * Memory Usage: 38.9 MB, less than 93.58% of Java .
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = 0;
        Set<Character> cache = new HashSet<>();
        int i = 0, j = 0;
        for (; j < s.length(); j++) {
            Character c = s.charAt(j);
            if (cache.contains(c)) {
                len = Math.max(len, j - i);
                while (i < j) {
                    Character t = s.charAt(i++);
                    if (t == c) {
                        break;
                    } else {
                        cache.remove(t);
                    }
                }
            }
            cache.add(c);
        }

        return Math.max(len, j - i);
    }

    public static void main(String[] args) {
        do_func("abcabcbb", 3);
        do_func("bbbbb", 1);
        do_func("pwwkew", 3);
        do_func("", 0);
        do_func("a", 1);
        do_func(" ", 1);
        do_func("abc", 3);
        do_func("tmmzuxt", 5);
    }

    private static void do_func(String s, int expected) {
        int ret = lengthOfLongestSubstring(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
