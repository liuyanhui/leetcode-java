package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 392. Is Subsequence
 * Easy
 * --------------------
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 *
 * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 *
 * Example 1:
 * Input: s = "abc", t = "ahbgdc"
 * Output: true
 *
 * Example 2:
 * Input: s = "axc", t = "ahbgdc"
 * Output: false
 *
 * Constraints:
 * 0 <= s.length <= 100
 * 0 <= t.length <= 10^4
 * s and t consist only of lowercase English letters.
 *
 * Follow up: Suppose there are lots of incoming s, say s1, s2, ..., sk where k >= 10^9, and you want to check one by one to see if t has its subsequence. In this scenario, how would you change your code?
 */
public class Is_Subsequence_392 {

    public static boolean isSubsequence(String s, String t) {
        return isSubsequence_1(s, t);
    }

    /**
     * 照抄参考思路的代码。
     *
     * Follow up要求的参考思路：
     *https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments
     *
     * Follow-up: O(N) time for pre-processing, O(Mlog?) for each S.
     * Eg-1. s="abc", t="bahbgdca"
     * idx=[a={1,7}, b={0,3}, c={6}]
     *  i=0 ('a'): prev=1
     *  i=1 ('b'): prev=3
     *  i=2 ('c'): prev=6 (return true)
     * Eg-2. s="abc", t="bahgdcb"
     * idx=[a={1}, b={0,6}, c={5}]
     *  i=0 ('a'): prev=1
     *  i=1 ('b'): prev=6
     *  i=2 ('c'): prev=? (return false)
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence_2(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }

        int prev = 0;
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) return false; // Note: char of S does NOT exist in T causing NPE
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);
            if (j < 0) j = -j - 1;
            if (j == idx[s.charAt(i)].size()) return false;
            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }

    /**
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 82.46% of Java online submissions for Is Subsequence.
     * Memory Usage: 37.1 MB, less than 56.34% of Java online submissions for Is Subsequence.
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence_1(String s, String t) {
        if (s == null || s.length() == 0) return true;
        if (t == null || t.length() == 0) return false;
        int idxS = 0;
        for (int i = 0; i < t.length(); i++) {
            if (s.charAt(idxS) == t.charAt(i)) {
                idxS++;
            }
            if (idxS == s.length()) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func("abc", "ahbgdc", true);
        do_func("axc", "ahbgdc", false);
        do_func("a", "b", false);
        do_func("acb", "ahbgdc", false);
    }

    private static void do_func(String s, String t, boolean expected) {
        boolean ret = isSubsequence(s, t);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
