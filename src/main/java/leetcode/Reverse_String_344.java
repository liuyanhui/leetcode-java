package leetcode;

/**
 * 344. Reverse String
 * Easy
 * -------------------------
 * Write a function that reverses a string. The input string is given as an array of characters s.
 *
 * Example 1:
 * Input: s = ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 *
 * Example 2:
 * Input: s = ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 *
 * Constraints:
 * 1 <= s.length <= 105
 * s[i] is a printable ascii character.
 *
 * Follow up: Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
 */
public class Reverse_String_344 {
    /**
     * Runtime: 1 ms, faster than 95.68% of Java online submissions for Reverse String.
     * Memory Usage: 46.1 MB, less than 40.45% of Java online submissions for Reverse String.
     * @param s
     */
    public static void reverseString(char[] s) {
        int l = 0, r = s.length - 1;
        while (l < r) {
            char t = s[l];
            s[l] = s[r];
            s[r] = t;
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        do_func(new char[]{'h', 'e', 'l', 'l', 'o'}, new char[]{'o', 'l', 'l', 'e', 'h'});
        do_func(new char[]{'H', 'a', 'n', 'n', 'a', 'h'}, new char[]{'h', 'a', 'n', 'n', 'a', 'H'});
        do_func(new char[]{'H'}, new char[]{'H'});
        do_func(new char[]{'H', 'a'}, new char[]{'a', 'H'});

    }

    private static void do_func(char[] s, char[] expected) {
        reverseString(s);
        ArrayUtils.printIntArray(s);
        ArrayUtils.isSameThenPrintln(s, expected);
        System.out.println("--------------");
    }
}
