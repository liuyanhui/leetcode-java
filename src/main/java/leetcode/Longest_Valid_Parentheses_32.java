package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 32. Longest Valid Parentheses
 * Hard
 * ----------------------------
 * Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed) parentheses substring .
 *
 * Example 1:
 * Input: s = "(()"
 * Output: 2
 * Explanation: The longest valid parentheses substring is "()".
 *
 * Example 2:
 * Input: s = ")()())"
 * Output: 4
 * Explanation: The longest valid parentheses substring is "()()".
 *
 * Example 3:
 * Input: s = ""
 * Output: 0
 *
 * Constraints:
 * 0 <= s.length <= 3 * 10^4
 * s[i] is '(', or ')'.
 */
public class Longest_Valid_Parentheses_32 {
    public static int longestValidParentheses(String s) {
        return longestValidParentheses_1(s);
    }
    /**round 3
     * Score[2] Lower is harder
     *
     * longestValidParentheses_1()中"2."无法通过类似"()(())"和"()()"的用例。
     * 可以通过初始化stack.push(-1)解决。
     * It is such an amazing solution.
     *
     */

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1.naive solution
     * 先遍历s，依次以s[i]为起点查找出最长有效()字符串；再比较长度获得结果。
     * 时间复杂度O(N*N)
     * 2.使用stack。
     * 2.1stack中的值是字符的下标。'('入栈；')'出栈，根据stack中栈顶下标和')'的下标计算长度t，并更新res=max(res,t)。
     * 2.2.上面的方案无法解决"()(())"和"()()"类似的用例。
     * 3.分步骤计算法。
     * 3.1.先利用stack，计算出每个'(...)'的结果，并保存它们的起始位置。（按区间保存）
     * 3.2.再合并相邻的中间结果。（合并相邻的区间）
     * 3.3.最后计算最大值。（查找最大的区间）
     *
     * 验证通过：
     * Runtime 4 ms
     * Beats 37.28% of users with Java
     * Memory 43.56 MB Beats 12.38% of users with Java
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses_1(String s) {
        if (s == null || s.length() == 0) return 0;

        //1.先计算出形如'(...)'的区间，并保存起来
        List<Integer> ranges = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (!stack.empty()) {
                    int beg = stack.pop();
                    int end = i;
                    //如果上一个加入ranges中的区间包含在本次的区间中，去掉上一个range区间
                    while (ranges.size() >= 2
                            && beg < ranges.get(ranges.size() - 2)
                            && ranges.get(ranges.size() - 1) < end) {
                        ranges.remove(ranges.size() - 1);
                        ranges.remove(ranges.size() - 1);
                    }
                    //再把本次区间加入ranges
                    ranges.add(beg);
                    ranges.add(end);
                }
            }
        }

        int res = 0;
        //合并相邻的区间，如：[4,6][7,9]合并为[4,6][4,9]
        for (int i = 2; i < ranges.size(); ) {
            if (ranges.get(i - 1) + 1 == ranges.get(i)) {
                ranges.set(i, ranges.get(i - 2));
            }
            i += 2;
        }

        //计算最大区间
        for (int i = 0; i < ranges.size(); ) {
            res = Math.max(res, ranges.get(i + 1) - ranges.get(i) + 1);
            i += 2;
        }
        return res;
    }

    public static void main(String[] args) {
        do_func("(()", 2);
        do_func(")()())", 4);
        do_func("", 0);
        do_func("()()", 4);
        do_func("()(())", 6);
        do_func("))))))))", 0);
        do_func("(()((((()))))()()()())))))()()((((()))))))))(((((()()()()()()()())", 22);
    }

    private static void do_func(String s, int expected) {
        int ret = longestValidParentheses(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
