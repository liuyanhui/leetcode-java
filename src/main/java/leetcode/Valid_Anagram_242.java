package leetcode;

/**
 * 242. Valid Anagram
 * Easy
 * ------------------------------------------------
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * <p>
 * Example 1:
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * <p>
 * Example 2:
 * Input: s = "rat", t = "car"
 * Output: false
 * <p>
 * Constraints:
 * 1 <= s.length, t.length <= 5 * 104
 * s and t consist of lowercase English letters.
 * <p>
 * Follow up: What if the inputs contain Unicode characters? How would you adapt your solution to such a case?
 */
public class Valid_Anagram_242 {
    public static boolean isAnagram(String s, String t) {
        return isAnagram_r3_1(s, t);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     * <p>
     * 验证通过：
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram_r3_1(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;
        char[] cnt = new char[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
            cnt[t.charAt(i) - 'a']--;
        }
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("anagram", "nagaram", true);
        do_func("car", "rat", false);
        System.out.println("=========End=========");
    }

    private static void do_func(String s, String t, boolean expected) {
        boolean ret = isAnagram(s, t);
        System.out.println(ret);
        System.out.println(expected == ret);
        System.out.println("--------------");
    }
}
