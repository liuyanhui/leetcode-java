package leetcode;

/**
 * https://leetcode.com/problems/palindrome-number/
 * 9. Palindrome Number
 * Easy
 * --------------
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 * Follow up: Could you solve it without converting the integer to a string?
 *
 * Example 1:
 * Input: x = 121
 * Output: true
 *
 * Example 2:
 * Input: x = -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 *
 * Example 3:
 * Input: x = 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 *
 * Example 4:
 * Input: x = -101
 * Output: false
 *
 * Constraints:
 * -2^31 <= x <= 2^31 - 1
 */
public class Palindrome_Number_9 {
    /**
     * 思路：按照数学方法，把数字反转。如果反转后的数字等于输入，说明输入是palingdrome number
     * 验证通过:
     * Runtime: 7 ms, faster than 66.86% of Java online submissions for Palindrome Number.
     * Memory Usage: 38.1 MB, less than 90.00% of Java online submissions for Palindrome Number.
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int palindromeInt = 0;
        int input = x;
        while (input > 0) {
            palindromeInt = palindromeInt * 10 + input % 10;
            input /= 10;
        }
        return palindromeInt == x;
    }

    public static void main(String[] args) {
        do_func(121, true);
        do_func(-121, false);
        do_func(10, false);
        do_func(-101, false);
    }

    private static void do_func(int x, boolean expected) {
        boolean ret = isPalindrome(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
