package leetcode;

import java.util.HashSet;
import java.util.Set;
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
    public static String removeKdigits(String num, int k) {
        return removeKdigits_3(num, k);
    }

    /**
     * round 2
     * review 转换或修改约束时要当心，很大可能会放松约束或改变成别的约束，导致结果错误。
     *
     * 【单调栈】场景
     * 参考removeKdigits_1()
     *
     * Thinking：
     * 1.要查找由不减趋势转变为不增趋势的拐点，可以使用单调栈
     *
     * 验证通过:
     * Runtime 26 ms Beats 74.66%
     * Memory 48.2 MB Beats 5.11%
     *
     * @param num
     * @param k
     * @return
     */
    public static String removeKdigits_3(String num, int k) {
        //特殊情况处理 1432219
        if (num.length() == k) return "0";
        //先查找被删除的digit
        Stack<Integer> stack = new Stack<>();//保存下标而不是值
        Set<Integer> delIndex = new HashSet<>();
        for (int i = 0; i < num.length() && k > 0; i++) {
            while (!stack.empty() && num.charAt(stack.peek()) > num.charAt(i) && k > 0) {
                delIndex.add(stack.pop());
                k--;
            }
            stack.push(i);
        }
        //特殊用例处理，如:num=222222或num=1234567等
        while (k > 0) {
            delIndex.add(stack.pop());
            k--;
        }
        //删除digit生成结果
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            if (delIndex.contains(i)) continue;
            res.append(num.charAt(i));
        }
        //定位前导0 leading zeroes
        int beg = 0;
        while (beg < res.length() - 1 && res.charAt(beg) == '0') {
            beg++;
        }

        return res.substring(beg);
    }

    /**
     * round 2
     *
     * Thinking：
     * 1.字符串格式的数的排序问题。
     * 2.naive solution
     * 找到去掉k个数字之后的所有组合，然后获取最小值。需要注意前导0的情况。
     * 3.先分析k=1的情况，然后依次增加k。F(i)的结果包含F(i+1)的结果。
     * 3.1.k=1时，遍历num[]从左向右，第一个局部最大值就是应该删除的digit。
     * 4.具体思路为：每次去掉一个digit，规则为去掉第一个局部最大值。
     *
     * 逻辑正确
     * Time Limit Exceed
     *
     * @param num
     * @param k
     * @return
     */
    public static String removeKdigits_2(String num, int k) {
        //特殊情况处理
        if (num.length() == k) return "0";
        StringBuilder res = new StringBuilder(num);

        while (k > 0) {
            //找到num的第一个极大值
            char last = res.charAt(0);
            for (int i = 0; i < res.length(); i++) {
                if (last > res.charAt(i)) {//1.趋势由单调不减变成单调不增
                    res.deleteCharAt(i - 1);
                    break;
                } else if (i == res.length() - 1) {//2.到达最后一个元素时，如：用例"2222"
                    res.deleteCharAt(i);
                } else {
                    last = res.charAt(i);
                }
            }
            k--;
        }
        //去掉前导0，或保留res=0
        while (res.indexOf("0") == 0 && res.length() > 1) {
            res.deleteCharAt(0);
        }

        return res.toString();
    }

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
    public static String removeKdigits_1(String num, int k) {
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
        do_func("9", 1, "0");
        do_func("98765", 4, "5");
        do_func("98765", 5, "0");
        do_func("112", 1, "11");
    }

    private static void do_func(String num, int k, String expected) {
        String ret = removeKdigits(num, k);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
