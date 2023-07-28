package leetcode;

/**
 * 409. Longest Palindrome
 * Easy
 * ------------------------
 * Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.
 *
 * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
 *
 * Example 1:
 * Input: s = "abccccdd"
 * Output: 7
 * Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
 *
 * Example 2:
 * Input: s = "a"
 * Output: 1
 * Explanation: The longest palindrome that can be built is "a", whose length is 1.
 *
 * Constraints:
 * 1 <= s.length <= 2000
 * s consists of lowercase and/or uppercase English letters only.
 */
public class Longest_Palindrome_409 {
    public static int longestPalindrome(String s) {
        return longestPalindrome_1(s);
    }

    /**
     * Thinking：
     * 1.回文串的特点：如果字母数为偶数，那么所有的字母都出现偶数次；如果字母数为奇数，那么除了中间的哪个字母其他字母都出现偶数次。
     * 2.出现偶数次的字母都在结果集中。需要判断是否是奇数回文串。
     *s
     * 验证通过：
     *
     * @param s
     * @return
     */
    public static int longestPalindrome_1(String s) {
        int res = 0;
        int[] cache = new int[256];
        for (char c : s.toCharArray()) {
            cache[c - 'A']++;
            if (cache[c - 'A'] == 2) {
                res += 2;
                cache[c - 'A'] = 0;
            }
        }
        return res == s.length() ? res : res + 1;
    }

    public static void main(String[] args) {
        do_func("abccccdd", 7);
        do_func("a", 1);
        do_func("aA", 1);
    }

    private static void do_func(String s, int expected) {
        int ret = longestPalindrome(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
