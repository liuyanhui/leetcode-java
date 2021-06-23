package leetcode;

import java.util.Stack;

/**
 * 402. Remove K Digits
 * Medium
 * ----------------------------
 * Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.
 *
 * Example 1:
 * Input: num = "1432219", k = 3
 * Output: "1219"
 * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
 *
 * Example 2:
 * Input: num = "10200", k = 1
 * Output: "200"
 * Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
 *
 * Example 3:
 * Input: num = "10", k = 2
 * Output: "0"
 * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 *
 * Constraints:
 * 1 <= k <= num.length <= 10^5
 * num consists of only digits.
 * num does not have any leading zeros except for the zero itself.
 */
public class Remove_K_Digits_402 {
    /**
     * 每次去掉一个数字，执行k次。
     * 思路是：从第0位开始单调递增的最大值就是可以去掉的数字。
     * 如：如果s[0]<s[1]<..<s[i]>s[i+1]，那么s[i]就是可以去掉的数字
     *
     * 按照上面的思路，时间复杂度是O(k*n)。使用stack可以优化时间复杂度。
     * 方案是：
     * 1.从第0位开始单调递增的数字依次入栈，遇到s[i]时，s[i]出栈；
     * 2.继续步骤1，直到k次操作。
     *
     * 与题目316. Remove Duplicate Letters是类似的，都是使用了单调栈，但是316的难度明显大多了。
     *
     * 验证通过：
     * Runtime: 10 ms, faster than 37.24% of Java online submissions for Remove K Digits.
     * Memory Usage: 39.1 MB, less than 75.08% of Java online submissions for Remove K Digits.
     * @param num
     * @param k
     * @return
     */
    public static String removeKdigits(String num, int k) {
        Stack<Integer> stack = new Stack<>();
        int deletedCount = 0;
        for (int i = 0; i < num.length(); i++) {
            int n = num.charAt(i) - '0';
            while (deletedCount < k && !stack.empty() && stack.peek() > n) {
                stack.pop();
                deletedCount++;
            }
            stack.push(n);
        }
        while (deletedCount < k) {
            stack.pop();
            deletedCount++;
        }
        if (stack.empty()) {
            return "0";
        } else {
            StringBuilder sb = new StringBuilder();
            while (!stack.empty()) {
                sb.insert(0, stack.pop());
            }
            String ret = sb.toString();
            //处理前导0的情况
            int leadingZeroIdx = 0;
            for (int i = 0; i < ret.length(); i++) {
                if (ret.charAt(i) != '0' || i == ret.length() - 1) {
                    leadingZeroIdx = i;
                    break;
                }
            }
            return ret.substring(leadingZeroIdx);
        }
    }

    public static void main(String[] args) {
        do_func("1432219", 3, "1219");
        do_func("10200", 1, "200");
        do_func("10", 2, "0");
        do_func("10000", 1, "0");
        do_func("10000", 3, "0");
        do_func("22222", 3, "22");
        do_func("1234567890", 9, "0");
    }

    private static void do_func(String num, int k, String expected) {
        String ret = removeKdigits(num, k);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
