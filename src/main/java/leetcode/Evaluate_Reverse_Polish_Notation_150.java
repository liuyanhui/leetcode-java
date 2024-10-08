package leetcode;

import java.util.Stack;

/**
 * 150. Evaluate Reverse Polish Notation
 * Medium
 * -------------------------
 * You are given an array of strings tokens that represents an arithmetic expression in a Reverse Polish Notation.
 *
 * Evaluate the expression. Return an integer that represents the value of the expression.
 *
 * Note that:
 *  - The valid operators are '+', '-', '*', and '/'.
 *  - Each operand may be an integer or another expression.
 *  - The division between two integers always truncates toward zero.
 *  - There will not be any division by zero.
 *  - The input represents a valid arithmetic expression in a reverse polish notation.
 *  - The answer and all the intermediate calculations can be represented in a 32-bit integer.
 *
 * Example 1:
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 *
 * Example 2:
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 *
 * Example 3:
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *
 * Constraints:
 * 1 <= tokens.length <= 10^4
 * tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 */
public class Evaluate_Reverse_Polish_Notation_150 {
    public static int evalRPN(String[] tokens) {
        return evalRPN_r3_1(tokens);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1. 利用Stack。遇到计算符，出栈两个数，并通过计算符计算，计算结果入栈。
     *
     * 验证通过：
     * Runtime 5 ms Beats 97.75%
     * Memory 44.62 MB Beats 37.71%
     *
     * @param tokens
     * @return
     */
    public static int evalRPN_r3_1(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                int a = stack.pop(), b = stack.pop();
                stack.push(b - a);
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (s.equals("/")) {
                int a = stack.pop(), b = stack.pop();
                stack.push(b / a);
            } else {
                stack.push(Integer.valueOf(s));
            }
        }
        return stack.peek();
    }

    /**
     * round 2
     *
     * 使用栈，算法如下：
     * 1.遇到数字入栈
     * 2.遇到操作符出栈数字（出栈2个数字），然后计算，计算结果再把数字入栈
     *
     * 验证通过：
     * Runtime: 13 ms, faster than 17.46% of Java online submissions for Evaluate Reverse Polish Notation.
     * Memory Usage: 44 MB, less than 51.32% of Java online submissions for Evaluate Reverse Polish Notation.
     *
     * @param tokens
     * @return
     */
    public static int evalRPN_2(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        stack.push(Integer.valueOf(tokens[0]));
        for (int i = 1; i < tokens.length; i++) {
            String s = tokens[i];
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                int n2 = stack.pop();
                int n1 = stack.pop();
                int r = 0;
                if (s.equals("+")) {
                    r = n1 + n2;
                } else if (s.equals("-")) {
                    r = n1 - n2;
                } else if (s.equals("*")) {
                    r = n1 * n2;
                } else if (s.equals("/")) {
                    r = n1 / n2;
                }
                stack.push(r);
            } else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.pop();
    }

    /**
     *
     * 题目说明：
     * 本质上是binary tree（非叶子节点是操作符，叶子节点是数字）的遍历。
     * RPN是postorder traversal。
     * 我们常见的数学表达式是preorder traversal。
     * 然而，本题不能使用"倒序处理tokens，按照preorder且从右子节点到左子节点的顺序"的思路。因为仅根据一种遍历结果无法准确推断出树的形状。
     *
     * 思路如下：
     * 利用stack，遇到数字入栈；遇到操作符，出栈两个数字，并用操作符计算，将结果入栈。
     * 注意：减法和除法需要留意数字出栈顺序。
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 92.66% of Java online submissions for Evaluate Reverse Polish Notation.
     * Memory Usage: 38.6 MB, less than 78.72% of Java online submissions for Evaluate Reverse Polish Notation.
     *
     * @param tokens
     * @return
     */
    public static int evalRPN_1(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s : tokens) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.push(n2 - n1);
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (s.equals("/")) {
                int n1 = stack.pop();
                int n2 = stack.pop();
                stack.push(n2 / n1);
            } else {
                stack.push(Integer.valueOf(s));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        do_func(new String[]{"2", "1", "+", "3", "*"}, 9);
        do_func(new String[]{"4", "13", "5", "/", "+"}, 6);
        do_func(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}, 22);
//        do_func(new String[]{"3", "2", "*", "5", "3", "-", "+"}, 8);

    }

    private static void do_func(String[] tokens, int expected) {
        int ret = evalRPN(tokens);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
