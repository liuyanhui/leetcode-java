package leetcode;

/**
 * https://leetcode.com/problems/valid-palindrome/
 * 125. Valid Palindrome
 * Easy
 * ---------------------
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.
 *
 * Given a string s, return true if it is a palindrome, or false otherwise.
 *
 * Example 1:
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 *
 * Example 2:
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 *
 * Example 3:
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric characters.
 * Since an empty string reads the same forward and backward, it is a palindrome.
 *
 * Constraints:
 * 1 <= s.length <= 2 * 10^5
 * s consists only of printable ASCII characters.
 */
public class Valid_Palindrome_125 {
    public static boolean isPalindrome(String s) {
        return isPalindrome_3(s);
    }

    /**
     * round 2
     * 左右指针法
     * 1.i是左边字符下标，j是右边字符的下标
     * 2.需要过滤非字母和数字的字符，需要把大写字母转换成小写，注意数字不需要转换
     * 3.采用夹逼法
     *
     * TODO【Attention】过滤非数字和字符时的下标累加或递减的逻辑需要注意，容易引发bug
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 83.48% of Java online submissions for Valid Palindrome.
     * Memory Usage: 44.1 MB, less than 40.92% of Java online submissions for Valid Palindrome.
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome_3(String s) {
        if (s == null) return false;
        if (s.length() == 0) return true;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
            i++;
            j--;
        }
        return true;
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
        do_func("zxcxz", true);
        do_func("1221", true);

    }

    private static void do_func(String s, boolean expected) {
        boolean ret = isPalindrome(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
