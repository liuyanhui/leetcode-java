package leetcode;

/**
 * https://leetcode.com/problems/count-and-say/
 * 38. Count and Say
 * Easy
 * -----------------
 * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
 * countAndSay(1) = "1"
 * countAndSay(n) is the way you would "say" the digit string from countAndSay(n-1), which is then converted into a different digit string.
 * To determine how you "say" a digit string, split it into the minimal number of groups so that each group is a contiguous section all of the same character. Then for each group, say the number of characters, then say the character. To convert the saying into a digit string, replace the counts with a number and concatenate every saying.
 *
 * For example, the saying and conversion for digit string "3322251":
 * Given a positive integer n, return the nth term of the count-and-say sequence.
 *
 * Example 1:
 * Input: n = 1
 * Output: "1"
 * Explanation: This is the base case.
 *
 * Example 2:
 * Input: n = 4
 * Output: "1211"
 * Explanation:
 * countAndSay(1) = "1"
 * countAndSay(2) = say "1" = one 1 = "11"
 * countAndSay(3) = say "11" = two 1's = "21"
 * countAndSay(4) = say "21" = one 2 + one 1 = "12" + "11" = "1211"
 *
 * Constraints:
 * 1 <= n <= 30
 */
public class Count_and_Say_38 {
    public static String countAndSay(int n) {
        return countAndSay_1(n);
    }

    /**
     * 直观的递归思路
     * 验证通过，性能一般：
     * Runtime: 17 ms, faster than 28.22% of Java online submissions for Count and Say.
     * Memory Usage: 39.7 MB, less than 12.85% of Java online submissions for Count and Say.
     * @param n
     * @return
     */
    public static String countAndSay_1(int n) {
        if (n == 1) return "1";
        String prev = countAndSay_1(n - 1);
        String ret = "";
        char cur = prev.charAt(0);
        int count = 1;
        for (int i = 1; i < prev.length(); i++) {
            if (cur == prev.charAt(i)) {
                count++;
            } else {
                ret += String.valueOf(count) + String.valueOf(cur);
                count = 1;
                cur = prev.charAt(i);
            }
        }
        ret += String.valueOf(count) + String.valueOf(cur);
        return ret;
    }

    public static void main(String[] args) {
        do_func(1, "1");
        do_func(2, "11");
        do_func(3, "21");
        do_func(4, "1211");
        do_func(5, "111221");
        do_func(6, "312211");
    }

    private static void do_func(int n, String expected) {
        String ret = countAndSay(n);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
