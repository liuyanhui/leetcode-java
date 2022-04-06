package leetcode;

/**
 * https://leetcode.com/problems/length-of-last-word/
 * 58. Length of Last Word
 * Easy
 * ----------------------
 * Given a string s consisting of some words separated by some number of spaces, return the length of the last word in the string.
 *
 * A word is a maximal substring consisting of non-space characters only.
 *
 * Example 1:
 * Input: s = "Hello World"
 * Output: 5
 * Explanation: The last word is "World" with length 5.
 *
 * Example 2:
 * Input: s = "   fly me   to   the moon  "
 * Output: 4
 * Explanation: The last word is "moon" with length 4.
 *
 * Example 3:
 * Input: s = "luffy is still joyboy"
 * Output: 6
 * Explanation: The last word is "joyboy" with length 6.
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of only English letters and spaces ' '.
 * There will be at least one word in s.
 */
public class Length_of_Last_Word_58 {
    public static int lengthOfLastWord(String s) {
        return lengthOfLastWord_4(s);
    }

    /**
     * round-2
     * review R2 完成耗时有点长
     *
     * 从后向前遍历，遍历两次。
     * 1.第一次遍历，跳过结尾的空格
     * 2.第二次遍历，如果字符不是空白字符，count++
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Length of Last Word.
     * Memory Usage: 38.8 MB, less than 25.04% of Java online submissions for Length of Last Word.
     *
     * @param s
     * @return
     */
    public static int lengthOfLastWord_4(String s) {
        int count = 0;
        int i = s.length() - 1;
        while (i >= 0) {
            if (s.charAt(i) != ' ') break;
            else i--;
        }
        while (i >= 0) {
            if (s.charAt(i) == ' ') break;
            else count++;
            i--;
        }
        return count;
    }

    /**
     * 充分利用jdk的工具类，一行代码解决
     * https://leetcode.com/problems/length-of-last-word/discuss/21878/A-single-line-of-Code-in-Java
     * @param s
     * @return
     */
    public static int lengthOfLastWord1_3(String s) {
        return s.trim().length() - s.trim().lastIndexOf(" ") - 1;
    }

    /**
     * 依次遍历每个字符
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Length of Last Word.
     * Memory Usage: 38.9 MB, less than 8.76% of Java online submissions for Length of Last Word.
     * @param s
     * @return
     */
    public static int lengthOfLastWord1_2(String s) {
        int ret = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (ret == 0 && ' ' == s.charAt(i)) {
                continue;
            } else if (ret > 0 && ' ' == s.charAt(i)) {
                return ret;
            } else {
                ret++;
            }
        }
        return ret;
    }

    /**
     * 利用String的split()方法
     * 验证通过，性能一般：
     * Runtime: 1 ms, faster than 51.15% of Java online submissions for Length of Last Word.
     * Memory Usage: 38.9 MB, less than 8.76% of Java online submissions for Length of Last Word.
     * @param s
     * @return
     */
    public static int lengthOfLastWord1_1(String s) {
        String[] words = s.split(" ");
        for (int i = words.length - 1; i >= 0; i--) {
            if (!"".equals(words[i])) {
                return words[i].length();
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func("Hello World", 5);
        do_func(" ", 0);
        do_func("Today is a nice day", 3);
        do_func("a", 1);
        do_func("swap", 4);
        do_func("   fly me   to   the moon  ", 4);
        do_func("luffy is still joyboy", 6);
    }

    private static void do_func(String s, int expected) {
        int ret = lengthOfLastWord(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
