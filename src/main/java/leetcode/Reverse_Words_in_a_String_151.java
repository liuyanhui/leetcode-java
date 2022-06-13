package leetcode;

/**
 * 151. Reverse Words in a String
 * Medium
 * -------------------------------
 * Given an input string s, reverse the order of the words.
 * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
 * Return a string of the words in reverse order concatenated by a single space.
 * Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.
 *
 * Example 1:
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 *
 * Example 2:
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 *
 * Example 3:
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * s contains English letters (upper-case and lower-case), digits, and spaces ' '.
 * There is at least one word in s.
 *
 * Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?
 */
public class Reverse_Words_in_a_String_151 {
    public static String reverseWords(String s) {
        return reverseWords_1(s);
    }

    /**
     * 直观思路：
     * 1.根据空格把s切割成数组
     * 2.从后向前遍历数组，过滤空字符串，把字符串加入结果集中
     * 这个思路空间复杂度为O(N)，时间复杂读O(N)
     *
     * 指针截取word+从后向前遍历思路
     * 算法：
     * 1.end=s.length
     * 2.从后向前遍历s
     * 2.1.如果s[i]!=' '，那么i--
     * 2.2.如果s[i]==' '，
     * 2.2.1.如果s[i+1,end]为空，那么end=i,i--
     * 2.2.2.如果s[i+1,end]不为空，那么s[i+1,end]加入结果集，end=i,i--
     *
     * 这个思路空间复杂度O(1)，时间复杂读O(N)
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 97.82% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 43.7 MB, less than 45.99% of Java online submissions for Reverse Words in a String.
     *
     * @param s
     * @return
     */
    public static String reverseWords_1(String s) {
        StringBuilder sb = new StringBuilder();
        int end = s.length();
        boolean needSpace = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (i + 1 < end) {
                    if (needSpace) {
                        sb.append(" ");
                    } else {
                        needSpace = true;
                    }
                    sb.append(s.substring(i + 1, end));

                }
                end = i;
            }
        }
        if (end > 0) {
            if (needSpace) {
                sb.append(" ");
            } else {
                needSpace = true;
            }
            sb.append(s.substring(0, end));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        do_func("the sky is blue", "blue is sky the");
        do_func("  hello world  ", "world hello");
        do_func("a good   example", "example good a");
        do_func("  ", "");
        do_func("good", "good");
    }

    private static void do_func(String s, String expected) {
        String ret = reverseWords(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
