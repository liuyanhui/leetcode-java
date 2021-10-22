package leetcode;

/**
 * 395. Longest Substring with At Least K Repeating Characters
 * Medium
 * ----------------------
 * Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.
 *
 * Example 1:
 * Input: s = "aaabb", k = 3
 * Output: 3
 * Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.
 *
 * Example 2:
 * Input: s = "ababbc", k = 2
 * Output: 5
 * Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of only lowercase English letters.
 * 1 <= k <= 10^5
 */
public class Longest_Substring_with_At_Least_K_Repeating_Characters_395 {
    public static int longestSubstring(String s, int k) {
        return longestSubstring_2(s, k);
    }

    /**
     * 滑动窗口法：
     * https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/zhi-shao-you-kge-zhong-fu-zi-fu-de-zui-c-o6ww/
     * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/
     * @param s
     * @param k
     * @return
     */
    public static int longestSubstring_2(String s, int k) {
        return 0;
    }

    /**
     *
     * Divide And Conquer
     * 参考思路：
     * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/
     *
     * 可以优化的地方：s.substring()改为起止下标的方式
     *
     * 验证通过：
     * Runtime: 244 ms, faster than 11.30% of Java
     * Memory Usage: 66.3 MB, less than 5.21% of Java
     *
     * @param s
     * @param k
     * @return
     */
    public static int longestSubstring_1(String s, int k) {
        if (s == null || "".equals(s)) return 0;
        if (k == 1) return s.length();
        int[] countMap = new int[26];
        for (int i = 0; i < s.length(); i++) {
            countMap[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (0 < countMap[i] && countMap[i] < k) {
                int mid = s.indexOf('a' + i);
                int left = 0, right = 0;
                if (mid > 0) {
                    left = longestSubstring(s.substring(0, mid), k);
                }
                if (mid < s.length() - 1) {
                    right = longestSubstring(s.substring(mid + 1), k);
                }
                return Math.max(left, right);
            }
        }

        return s.length();
    }

    public static void main(String[] args) {
        do_func("aaabb", 3, 3);
        do_func("ababbc", 2, 5);
        do_func("a", 2, 0);
        do_func("a", 1, 1);
        do_func("aaabb", 30, 0);
        do_func("aaabb", 1, 5);
        do_func("ababacb", 3, 0);
    }

    private static void do_func(String s, int k, int expected) {
        int ret = longestSubstring(s, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
