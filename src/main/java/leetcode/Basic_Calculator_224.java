package leetcode;

import java.util.Stack;

/**
 * 这里有金矿
 * 224. Basic Calculator
 * Hard
 * --------------------
 * Given a string s representing an expression, implement a basic calculator to evaluate it.
 *
 * Example 1:
 * Input: s = "1 + 1"
 * Output: 2
 *
 * Example 2:
 * Input: s = " 2-1 + 2 "
 * Output: 3
 *
 * Example 3:
 * Input: s = "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 *
 *  Constraints:
 * 1 <= s.length <= 3 * 10^5
 * s consists of digits, '+', '-', '(', ')', and ' '.
 * s represents a valid expression.
 */
public class Basic_Calculator_224 {
    public static int calculate(String s) {
        return calculate_3(s);
    }

    /**
     * 与227类似的思路：
     * 1.利用stack进行计算，使用sign保存上一个操作符。根据不同类型的字符执行不同的逻辑。
     * 2.digit: it should be one digit from the current number
     * 3.'+': number is over, we can add the previous number and start a new number
     * 4.'-': same as above
     * 5.'(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
     * 6.')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, second is the temporary result before this pair of parenthesis. We add them together.
     *
     * 参考思路：
     * https://leetcode.com/problems/basic-calculator/discuss/62361/Iterative-Java-solution-with-stack
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 92.04% of Java online submissions for Basic Calculator.
     * Memory Usage: 38.9 MB, less than 76.16% of Java online submissions for Basic Calculator.
     *
     * @param s
     * @return
     */
    public static int calculate_3(String s) {
        Stack<Integer> stack = new Stack<>();
        int sign = 1;
        int ret = 0;
        int number = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            } else if (c == '+') {
                ret += sign * number;
                number = 0;
                sign = 1;
            } else if (c == '-') {
                ret += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {//这里是精髓
                stack.push(ret);
                ret = 0;
                stack.push(sign);
                sign = 1;
            } else if (c == ')') {//这里是精髓
                ret += sign * number;
                number = 0;
                sign = 1;
                ret *= stack.pop();
                ret += stack.pop();
            }
        }
        if (number > 0) {
            ret += sign * number;
        }
        return ret;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/basic-calculator/solution/
     *
     * 解释如下：
     * 0.本质上还是两步法，第一步去掉括号，第二步计算无括号的stack
     * 1.从后向前入栈，忽略空格
     * 2.如果遇到"(",出栈并计算。直到出栈的字符为")"，并将计算结果入栈
     *
     * 思路比较清晰，但是验证失败：
     * 无法通过用例："-2+ 1","1-(+1+1)" 等
     * 从后向前计算时，较难处理负数
     *
     * @param s
     * @return
     */
    public static int calculate_2(String s) {
        Stack<Object> stack = new Stack<>();
        int pow = 0;
        int n = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                n += Math.pow(10, pow) * (c - '0');
                pow++;
            } else if (c != ' ') {
                if (pow > 0) {
                    stack.push(n);
                    n = 0;
                    pow = 0;
                }
                if (c == '(') {
                    int t = calcStack(stack);
                    stack.pop();//")"出栈
                    stack.push(t);//括号内的结果入栈
                } else {
                    stack.push(c);
                }
            }
        }
        if (pow > 0) {
            stack.push(n);
        }
        return calcStack(stack);
    }

    private static int calcStack(Stack<Object> stack) {
        int ret = (int) stack.pop();
        while (stack.size() > 0 && !stack.peek().equals(')')) {
            char op = (char) stack.pop();
            if (op == '+') {
                ret += (int) stack.pop();
            } else if (op == '-') {
                ret -= (int) stack.pop();
            }
        }
        return ret;
    }

    /**
     * 参考227的方案
     *
     * 验证通过，性能一般：
     * Runtime: 176 ms, faster than 6.42% of Java online submissions for Basic Calculator.
     * Memory Usage: 107.3 MB, less than 5.01% of Java online submissions for Basic Calculator.
     * @param s
     * @return
     */
    public static int calculate_1(String s) {
        Stack<Integer> stack = new Stack<>();
        char sign = '+';
        int n = 0;
        //第一次去掉括号，减法变加法
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                n = n * 10 + c - '0';
            } else if (c == '(') {//处理括号字符，使用递归
                String s1 = getString(s, i);
                n = calculate(s1);
                i += s1.length() + 1;
            }
            if (c == '+' || c == '-' || i == s.length() - 1) {
                stack.push(sign == '+' ? n : -n);
                sign = c;
                n = 0;
            }
        }
        //第二次累加stack中的数字
        int ret = 0;
        for (int i : stack) {
            ret += i;
        }
        return ret;
    }

    private static String getString(String s, int beg) {
        if (s == null || s.length() == 0) return "";
        if (beg >= s.length()) return "";
        int count = 0;
        int end = beg;
        for (int i = beg; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')') {
                count--;
            } else if (c == '(') {
                count++;
            }
            end++;
            if (count == 0) break;
        }
        return s.substring(beg + 1, end - 1);
    }

    public static void main(String[] args) {
        do_func("1+1", 2);
        do_func("1 + 1", 2);
        do_func(" 2-1 + 2 ", 3);
        do_func(" (2-1) + 2 ", 3);
        do_func(" 5- (2-1) + 2 ", 6);
        do_func("(1+(4+5+2)-3)+(6+8)", 23);
        do_func(" ( 1+(4+5+2)-3)+(6+8)", 23);
        do_func("3+2-2", 3);
        do_func("0-111", -111);
        do_func("0-2147483647", -2147483647);
        do_func("0-2147483646", -2147483646);
        do_func("-2+ 1", -1);
        do_func(" -2+ 1", -1);
        do_func(" -222+ 1", -221);
        do_func("1-(+1+1)", -1);
    }

    private static void do_func(String s, int expected) {
        int ret = calculate(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
