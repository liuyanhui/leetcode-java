package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 205. Isomorphic Strings
 * Easy
 * --------------------------
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.
 *
 * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true
 *
 * Example 2:
 * Input: s = "foo", t = "bar"
 * Output: false
 *
 * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 5 * 10^4
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */
public class Isomorphic_Strings_205 {
    public static boolean isIsomorphic(String s, String t) {
        return isIsomorphic_1(s, t);
    }

    /**
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 61.69% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 39 MB, less than 53.05% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_1(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (map.containsKey(sc)) {
                if (map.get(sc) != tc) {
                    return false;
                }
            } else {
                if (map.containsValue(tc)) {
                    return false;
                } else {
                    map.put(sc, tc);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("egg", "add", true);
        do_func("foo", "bar", false);
        do_func("paper", "title", true);
        do_func("egggghhheff", "addddiiiamm", true);
        do_func("ba", "ab", false);
        do_func("badc", "baba", false);
    }

    private static void do_func(String s, String t, boolean expected) {
        boolean ret = isIsomorphic(s, t);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
