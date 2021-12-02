package leetcode;

/**
 * 28. Implement strStr()
 * Easy
 * -----------------------------
 * Implement strStr().
 *
 * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 *
 * Clarification:
 * What should we return when needle is an empty string? This is a great question to ask during an interview.
 *
 * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
 *
 * Example 1:
 * Input: haystack = "hello", needle = "ll"
 * Output: 2
 *
 * Example 2:
 * Input: haystack = "aaaaa", needle = "bba"
 * Output: -1
 *
 * Example 3:
 * Input: haystack = "", needle = ""
 * Output: 0
 *
 * Constraints:
 *
 * 0 <= haystack.length, needle.length <= 5 * 10^4
 * haystack and needle consist of only lower-case English characters.
 */
public class Implement_strStr_28 {
    /**
     * 未解决
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        return strStr_2(haystack, needle);
    }

    /**
     * 验证通过：投机取巧
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr_2(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        if (needle.length() == 0) return 0;
        return haystack.indexOf(needle);
    }

    /**
     *
     * 验证失败：Time limit exceeded
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr_1(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        if (needle.length() == 0) return 0;
        //i的最大值不需要是haystack.length()
        for (int i = 0; i < haystack.length() - needle.length(); i++) {
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
                if (j == needle.length() - 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        do_func("hello", "ll", 2);
        do_func("aaaaa", "bba", -1);
        do_func("", "", 0);
        do_func("aaa", "aaaa", -1);
        do_func("mississippi", "issip", 4);
    }

    private static void do_func(String haystack, String needle, int expected) {
        int ret = strStr(haystack, needle);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
