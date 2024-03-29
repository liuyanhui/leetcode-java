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
        return calculate_5(s);
    }

    /**
     * round2
     * review
     * 参考：calculate_3()
     *
     * 思路：
     * 1.参考OS关于线程栈帧相关的思路。把当前函数的栈帧压入栈，然后初始化另一个函数调用并执行逻辑。
     * 2.能算则算，这样就无需再入栈，也无需后续的出栈并计算。那么在只有一层()的情况下，()中的结果无需入栈。这样，因为嵌套()而利用栈时，出栈计算急需要考虑栈顶的2个元素，其中第1个是signal，第2个是+/-()前的数字。对于第1个元素signal使用乘法，对于第2个元素使用加法。
     *
     * 具体算法如下：
     * 1.如果是+、-，那么计算n(n=n*signal)，修改signal，n累加到res中
     * 2.如果是数字，那么计算n
     * 3.如果是(，那么先后入栈res和signal入栈，重置res，重置signal
     * 4.如果是)，那么计算res=res+n*signal，n入栈，栈顶出栈s=stack.pop()，栈顶出栈表示sum=stack.pop()，sum=sum+res*s，res=sum
     * 5.如果抵达字符串表达式末尾，那么res=res+n*signal
     *
     * 验证通过：
     * Runtime: 10 ms, faster than 77.30% of Java online submissions for Basic Calculator.
     * Memory Usage: 44.6 MB, less than 40.51% of Java online submissions for Basic Calculator.
     *
     * @param s
     * @return
     */
    public static int calculate_5(String s) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        int n = 0;
        int signal = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                n = n * 10 + c - '0';
            } else if (c == '(') {
                stack.push(res);
                stack.push(signal);
                signal = 1;
                res = 0;
                n = 0;
            } else if (c == ')') {
                //计算括号内的表达式
                res += n * signal;
                signal = 1;
                n = 0;
                //计算括号外的已经入栈的部分
                res = stack.pop() * res + stack.pop();
            } else if (c == '+' || c == '-') {
                res += n * signal;
                n = 0;
                signal = c == '+' ? 1 : -1;
            }

            if (i == s.length() - 1) {
                res += n * signal;
            }
        }
        return res;
    }

    /**
     * review
     * round 2
     *
     * 递归法：
     * 0.两步法：先计算()内的部分，去掉()，再用常规算法计算没有()的部分
     * 1.去除()
     * 1.1.遇到(，先找出对应的()中的字符串sub，对sub进行递归计算，并将结果结合正负号之后入栈；对循环下标i=i+sub.length
     * 1.2.遇到+、-，n入stack
     * 1.3.遇到)，continue
     * 1.4.遇到空格，continue
     * 1.5.遇到数字，数字计算n=n*10+c-'0'
     * 2.计算stack
     *
     * AC的结果中有使用递归方法的更巧妙简单的解决方案。如：
     * 1.如果是(，那么递归
     * 2.如果是数字，计算数字
     * 3.如果是+或-或)或最后一个字符
     * 3.1.计算res，
     * 3.2.重置现场。
     * 3.3.如果是)，那么返回res
     *
     * 验证通过：
     * Runtime: 354 ms, faster than 5.03% of Java online submissions for Basic Calculator.
     * Memory Usage: 111.7 MB, less than 5.00% of Java online submissions for Basic Calculator.
     *
     * @param s
     * @return
     */
    public static int calculate_4(String s) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        int n = 0;
        int signal = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') {
                stack.push(signal * n);
                signal = c == '+' ? 1 : -1;
                n = 0;
            } else if (c == '(') {
                String sub = helper(s, i);
                n = calculate(sub);
                stack.push(n * signal);
                signal = 1;
                n = 0;
                i += sub.length();
            } else if (Character.isDigit(c)) {
                n = n * 10 + c - '0';
            }
            if (i == s.length() - 1) {
                stack.push(signal * n);
            }
        }
        while (!stack.empty()) {
            res += stack.pop();
        }
        return res;
    }

    private static String helper(String s, int beg) {
        int end = beg;
        int cnt = 0;
        for (int i = beg; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                cnt++;
            } else if (c == ')') {
                cnt--;
            }
            if (cnt == 0) {
                end = i;
                break;
            }
        }
        return s.substring(beg + 1, end);
    }

    /**
     * round 2 : review
     *
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
            } else if (c == '(') {//这里是精髓。
                // Review
                // Round2：所有的递归计算都可以用stack重写
                // 1.巧妙的利用stack实现了类似递归的操作。Awesome!
                // 2.本质上OS也是用stack实现的递归。把需要在递归结束后的继续执行的信息保存在stack中，为还原现场提供数据支持。
                // 3.下面的代码没有计算，只是保存现场，并重置现场。类似于OS把当前栈帧压入栈中。
                stack.push(ret);//这是是把现场保存起来
                stack.push(sign);//这是是把现场保存起来
                ret = 0;//这是重置现场
                sign = 1;//这是重置现场
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
