package leetcode;

/**
 * Longest Palindromic Substring
 * Medium
 * ------------------------
 * Given a string s, return the longest palindromic substring in s.
 *
 *  Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 *
 * Example 3:
 * Input: s = "a"
 * Output: "a"
 *
 * Example 4:
 * Input: s = "ac"
 * Output: "a"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters.
 */
public class Longest_Palindromic_Substring_5 {
    /**
     * 直观思路：
     * 从当前字符向两边扩散，需要考虑单数字符和双数字符的情况。
     * 实际复杂度O(N*N)
     * 验证通过：
     * Runtime: 30 ms, faster than 67.43% of Java
     * Memory Usage: 39.5 MB, less than 66.92% of Java
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        String lp = "";
        for (int i = 0; i < s.length(); i++) {
            int j = 0;
            //单数情况
            while (i - j >= 0 && i + j <= s.length() - 1 && s.charAt(i - j) == s.charAt(i + j)) {
                j++;
            }
            j--;
            if (lp.length() < 2 * j + 1) {
                lp = s.substring(i - j, i + j + 1);
            }
            //双数情况
            j = 0;
            while (i - j >= 0 && i + j + 1 <= s.length() - 1 && s.charAt(i - j) == s.charAt(i + j + 1)) {
                j++;
            }
            j--;
            if (lp.length() < 2 * j + 2) {
                lp = s.substring(i - j, i + j + 2);
            }
        }
        return lp;
    }

    public static void main(String[] args) {
        do_func("babad", "bab");
        do_func("cbbd", "bb");
        do_func("a", "a");
        do_func("ac", "a");
        do_func("aaaaaaaa", "aaaaaaaa");
        do_func("zxcvbbbvcxz", "zxcvbbbvcxz");
    }

    private static void do_func(String s, String expected) {
        String ret = longestPalindrome(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
