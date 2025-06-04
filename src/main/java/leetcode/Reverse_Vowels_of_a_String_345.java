package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 345. Reverse Vowels of a String
 * Easy
 * ----------------------
 * Given a string s, reverse only all the vowels in the string and return it.
 * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both cases.
 *
 * Example 1:
 * Input: s = "hello"
 * Output: "holle"
 *
 * Example 2:
 * Input: s = "leetcode"
 * Output: "leotcede"
 *
 * Constraints:
 * 1 <= s.length <= 3 * 10^5
 * s consist of printable ASCII characters.
 */
public class Reverse_Vowels_of_a_String_345 {
    public static String reverseVowels(String s) {
        return reverseVowels_r3_1(s);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * 验证通过：
     *
     * @param s
     * @return
     */
    public static String reverseVowels_r3_1(String s) {
        int[] masks = new int[256];
        char[] vowels = {'a', 'e', 'o', 'i', 'u', 'A', 'E', 'O', 'I', 'U'};
        for (char c : vowels) {
            masks[c] = 1;
        }
        char[] res = s.toCharArray();
        int l = 0, r = res.length - 1;
        while (l < r) {
            if (masks[res[l]] == 0) {
                l++;
            } else if (masks[res[r]] == 0) {
                r--;
            } else {
                char t = res[l];
                res[l] = res[r];
                res[r] = t;
                l++;
                r--;
            }
        }
        return String.valueOf(res);
    }
    /**
     * round 2
     *
     * 验证通过：
     * Runtime 4 ms Beats 72.18%
     * Memory 43.2 MB Beats 36.89%
     *
     * @param s
     * @return
     */
    public static String reverseVowels_2(String s) {
        if (s == null || s.length() == 0) return s;
        Set<Character> vowels = new HashSet<Character>();
        vowels.add('a');
        vowels.add('A');
        vowels.add('o');
        vowels.add('O');
        vowels.add('e');
        vowels.add('E');
        vowels.add('i');
        vowels.add('I');
        vowels.add('u');
        vowels.add('U');

        int l = 0, r = s.length() - 1;
        char[] charArr = s.toCharArray();
        while (l < r) {
            while (l < r && !vowels.contains(charArr[l])) {
                l++;
            }
            while (l < r && !vowels.contains(charArr[r])) {
                r--;
            }
            if (l >= r) break;
            char t = charArr[l];
            charArr[l] = charArr[r];
            charArr[r] = t;
            l++;
            r--;
        }
        return new String(charArr);
    }

    /**
     * 验证通过：
     * Runtime: 2 ms, faster than 99.22% of Java online submissions for Reverse Vowels of a String.
     * Memory Usage: 39.1 MB, less than 70.65% of Java online submissions for Reverse Vowels of a String.
     * @param s
     * @return
     */
    public static String reverseVowels_1(String s) {
        if (s == null || s.length() == 0) return "";
        char[] reversedArr = new char[s.length()];
        int l = 0, r = s.length() - 1;
        while (l <= r) {
            while (l <= r && !isVowels(s.charAt(l))) {
                reversedArr[l] = s.charAt(l);
                l++;
            }
            while (l <= r && !isVowels(s.charAt(r))) {
                reversedArr[r] = s.charAt(r);
                r--;
            }
            if (l <= r) {
                reversedArr[l] = s.charAt(r);
                reversedArr[r] = s.charAt(l);
                l++;
                r--;
            }
        }
        return String.valueOf(reversedArr);
    }

    private static boolean isVowels(char c) {
        return c == 'a' || c == 'e' || c == 'o' || c == 'i' || c == 'u'
                || c == 'A' || c == 'E' || c == 'O' || c == 'I' || c == 'U';
    }

    public static void main(String[] args) {
        do_func("hello", "holle");
        do_func("leetcode", "leotcede");
        do_func("a", "a");
        do_func("au", "ua");
        do_func("aA", "Aa");
    }

    private static void do_func(String s, String expected) {
        String ret = reverseVowels(s);
        System.out.println(ret);
        System.out.println(ret.equals(expected));
        System.out.println("--------------");
    }
}
