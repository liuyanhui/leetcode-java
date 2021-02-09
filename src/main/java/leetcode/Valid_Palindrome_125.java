package leetcode;

/**
 * https://leetcode.com/problems/valid-palindrome/
 * 125. Valid Palindrome
 * Easy
 * ---------------------
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * Note: For the purpose of this problem, we define empty string as valid palindrome.
 *
 * Example 1:
 * Input: "A man, a plan, a canal: Panama"
 * Output: true
 *
 * Example 2:
 * Input: "race a car"
 * Output: false
 *
 * Constraints:
 * s consists only of printable ASCII characters.
 */
public class Valid_Palindrome_125 {
    public static boolean isPalindrome(String s) {
        return isPalindrome_2(s);
    }

    /**
     * isPalindrome_1的简化版，使用Java自带的Character类
     * @param s
     * @return
     */
    public static boolean isPalindrome_2(String s) {
        if (s == null) return false;
        int l = 0, r = s.length() - 1;
        while (l < r) {
            char left = s.charAt(l), right = s.charAt(r);
            if (!Character.isLetterOrDigit(left)) {
                l++;
            } else if (!Character.isLetterOrDigit(right)) {
                r--;
            } else {
                if (Character.toLowerCase(left) != Character.toLowerCase(right)) return false;
                r--;
                l++;
            }
        }
        return true;
    }

    /**
     * 1.重点是过滤非字母和非数字的字符，并且统一转换大小写字母为大写字母
     * 2.夹逼法
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Valid Palindrome.
     * Memory Usage: 39.1 MB, less than 64.21% of Java online submissions for Valid Palindrome.
     * @param s
     * @return
     */
    public static boolean isPalindrome_1(String s) {
        if (s == null) return false;
        int l = 0, r = s.length() - 1;
        char left = 0, right = 0;
        while (l < r) {
            //获取left
            while (l < r && (left = filterLowcaseAndNumeric(s.charAt(l))) == 1) {
                l++;
            }
            //获取right
            while (l < r && (right = filterLowcaseAndNumeric(s.charAt(r))) == 1) {
                r--;
            }
            //比较
            if (l < r && left != right) return false;
            l++;
            r--;
        }
        return true;
    }

    private static char filterLowcaseAndNumeric(char c) {
        if ((48 <= c && c <= 57) || (65 <= c && c <= 90)) return c;
        else if (97 <= c && c <= 122) return (char) (c - 32);
        else return 1;
    }

    public static void main(String[] args) {
        do_func("A man, a plan, a canal: Panama", true);
        do_func("race a car", false);
        do_func("     ", true);
        do_func("", true);
        do_func(null, false);

    }

    private static void do_func(String s, boolean expected) {
        boolean ret = isPalindrome(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
