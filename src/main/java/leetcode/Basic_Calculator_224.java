package leetcode;

import java.util.Stack;

/**
 * 这里有金矿
 * 224. Basic Calculator
 * Hard
 * --------------------
 * Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.
 * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
 * <p>
 * Example 1:
 * Input: s = "1 + 1"
 * Output: 2
 * <p>
 * Example 2:
 * Input: s = " 2-1 + 2 "
 * Output: 3
 * <p>
 * Example 3:
 * Input: s = "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 * <p>
 * Constraints:
 * - 1 <= s.length <= 3 * 10^5
 * - s consists of digits, '+', '-', '(', ')', and ' '.
 * - s represents a valid expression.
 * - '+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
 * - '-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
 * - There will be no two consecutive operators in the input.
 * - Every number and running calculation will fit in a signed 32-bit integer.
 */
public class Basic_Calculator_224 {
    public static int calculate(String s) {
        return calculate_r3_2(s);
    }

    /**
     * review
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 典型的逻辑题。采用直观的数学计算法。
     * 由最内层括号开始计算，逐步向外层扩展计算。
     * 遍历 s 为 s[i]
     * IF s[i] == digit THEN n = n*10+(s[i]-'0');
     * IF s[i] == '+' THEN stack.push(n);stack.push(s[i]);
     * IF s[i] == '-' THEN stack.push(n);stack.push(s[i]);
     * IF s[i] == '(' THEN stack.push(n);stack.push(s[i-1]);stack.push(s[i]);
     * IF s[i] == ')' THEN n={计算stack中的值，直到stack.pop()=='('}
     * 遍历s结束后，res = {计算stack中的值，直到stack.empty()}
     * <p>
     * 2. 性能更好的方案。
     * https://leetcode.com/problems/basic-calculator/solutions/62361/iterative-java-solution-with-stack/
     * 遍历s中的每个字符，设为s[i]
     * 共分为5种情况：
     * s[i] is digit : 是当前数cur_num的一部分，{cur_num=cur_num*10+s[i]-'0'}。
     * s[i]=='+' : 表示数字已经结束，{res = res +last_sign*cur_num},?为上一个计算符号，更新{last_sign=1,cur_num=0}
     * s[i]=='-' : 同 s[i]=='+',区别是{last_sign=-1}
     * s[i]=='(' : 把 last_sign 和 res 按照固定顺序入栈，重置res和last_sign,{res=0,last_sign=1}
     * s[i]==')' : 表示数字已经结束，计算res；栈顶的2个元素出栈（分别是计算符号和入栈前的res）,{res=stack.pop()+stack.pop()*res}
     * <p>
     * 3. 遇到'('就递归。
     *
     * [Group] : Basic_Calculator_II_227
     *
     *
     * 验证通过：
     * Runtime 9 ms 62.72%
     * Memory 43.78 MB Beats 47.96%
     *
     * @param s
     * @return
     * @See Basic_Calculator_II_227
     */
    public static int calculate_r3_2(String s) {
        int res = 0;
        int sign = 1;
        int num = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '+' || c == '-') {//num已经结束可以计算了
                res = res + sign * num;
                num = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {//sign和res按顺序入栈，
                stack.push(sign);
                stack.push(res);
                res = 0;
                sign = 1;
            } else if (c == ')') {//先计算当前括号内的res；再出栈2个元素，计算上一层括号的值
                res = res + sign * num;
                res = stack.pop() + stack.pop() * res;
                sign = 1;
                num = 0;
            }
        }
        res = res + sign * num;
        return res;
    }

    /**
     * review
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 典型的逻辑题。采用直观的数学计算法。
     * 由最内层括号开始计算，逐步向外层扩展计算。
     * 遍历 s 为 s[i]
     * IF s[i] == digit THEN n = n*10+(s[i]-'0');
     * IF s[i] == '+' THEN stack.push(n);stack.push(s[i]);
     * IF s[i] == '-' THEN stack.push(n);stack.push(s[i]);
     * IF s[i] == '(' THEN stack.push(n);stack.push(s[i-1]);stack.push(s[i]);
     * IF s[i] == ')' THEN n={计算stack中的值，直到stack.pop()=='('}
     * 遍历s结束后，res = {计算stack中的值，直到stack.empty()}
     * <p>
     * calculate_3() 和 calculate_5()很巧妙，性能更好
     *
     * <p>
     * 验证通过：
     * Runtime 27 ms Beats 9.88%
     * Memory 49.38 MB Beats 6.18%
     */
    public static int calculate_r3_1(String s) {
        if (s == null || s.length() == 0) return 0;
        Stack<String> stack = new Stack<>();
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            if ('0' <= c && c <= '9') {
                n = n * 10 + c - '0';
            } else if (c == '+' || c == '-') {
                stack.push(String.valueOf(n));
                n = 0;
                stack.push(String.valueOf(c));
            } else if (c == '(') {
                stack.push(String.valueOf(c));
            } else if (c == ')') {
                stack.push(String.valueOf(n));
                n = calc_stack_r3_1(stack);
            }
        }
        if (n != 0) stack.push(String.valueOf(n));
        return calc_stack_r3_1(stack);
    }

    private static int calc_stack_r3_1(Stack<String> stack) {
        int res = 0;
        int n = 0;
        while (!stack.empty() && stack.peek() != "(") {
            String s = stack.pop();
            if (s.equals("(")) break;
            if (s.equals("+")) {
                res += n;
            } else if (s.equals("-")) {
                res -= n;
            } else {
                n = Integer.valueOf(s);
            }
        }
        res += n;
        return res;
    }

    /**
     * round2
     * review
     * 参考：calculate_3()
     * <p>
     * 思路：
     * 1.参考OS关于线程栈帧相关的思路。把当前函数的栈帧压入栈，然后初始化另一个函数调用并执行逻辑。
     * 2.能算则算，这样就无需再入栈，也无需后续的出栈并计算。那么在只有一层()的情况下，()中的结果无需入栈。这样，因为嵌套()而利用栈时，出栈计算急需要考虑栈顶的2个元素，其中第1个是signal，第2个是+/-()前的数字。对于第1个元素signal使用乘法，对于第2个元素使用加法。
     * <p>
     * 具体算法如下：
     * 1.如果是+、-，那么计算n(n=n*signal)，修改signal，n累加到res中
     * 2.如果是数字，那么计算n
     * 3.如果是(，那么先后入栈res和signal入栈，重置res，重置signal
     * 4.如果是)，那么计算res=res+n*signal，n入栈，栈顶出栈s=stack.pop()，栈顶出栈表示sum=stack.pop()，sum=sum+res*s，res=sum
     * 5.如果抵达字符串表达式末尾，那么res=res+n*signal
     * <p>
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
     * <p>
     * 递归法：
     * 0.两步法：先计算()内的部分，去掉()，再用常规算法计算没有()的部分
     * 1.去除()
     * 1.1.遇到(，先找出对应的()中的字符串sub，对sub进行递归计算，并将结果结合正负号之后入栈；对循环下标i=i+sub.length
     * 1.2.遇到+、-，n入stack
     * 1.3.遇到)，continue
     * 1.4.遇到空格，continue
     * 1.5.遇到数字，数字计算n=n*10+c-'0'
     * 2.计算stack
     * <p>
     * AC的结果中有使用递归方法的更巧妙简单的解决方案。如：
     * 1.如果是(，那么递归
     * 2.如果是数字，计算数字
     * 3.如果是+或-或)或最后一个字符
     * 3.1.计算res，
     * 3.2.重置现场。
     * 3.3.如果是)，那么返回res
     * <p>
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
     * <p>
     * 与227类似的思路：
     * 1.利用stack进行计算，使用sign保存上一个操作符。根据不同类型的字符执行不同的逻辑。
     * 2.digit: it should be one digit from the current number
     * 3.'+': number is over, we can add the previous number and start a new number
     * 4.'-': same as above
     * 5.'(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
     * 6.')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, second is the temporary result before this pair of parenthesis. We add them together.
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/basic-calculator/discuss/62361/Iterative-Java-solution-with-stack
     * <p>
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
     * <p>
     * 解释如下：
     * 0.本质上还是两步法，第一步去掉括号，第二步计算无括号的stack
     * 1.从后向前入栈，忽略空格
     * 2.如果遇到"(",出栈并计算。直到出栈的字符为")"，并将计算结果入栈
     * <p>
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
     * <p>
     * 验证通过，性能一般：
     * Runtime: 176 ms, faster than 6.42% of Java online submissions for Basic Calculator.
     * Memory Usage: 107.3 MB, less than 5.01% of Java online submissions for Basic Calculator.
     *
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
        System.out.println("input = {" + s + "}");
        int ret = calculate(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
