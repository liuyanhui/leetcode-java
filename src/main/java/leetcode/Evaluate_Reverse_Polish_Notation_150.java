package leetcode;

import java.util.Stack;

/**
 * 150. Evaluate Reverse Polish Notation
 * Medium
 * -------------------------
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, and /. Each operand may be an integer or another expression.
 * Note that division between two integers should truncate toward zero.
 * It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate to a result, and there will not be any division by zero operation.
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
 * 1 <= tokens.length <= 104
 * tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 */
public class Evaluate_Reverse_Polish_Notation_150 {
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
    public static int evalRPN(String[] tokens) {
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
