package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 这里有金矿 与224相似
 * 227. Basic Calculator II
 * Medium
 * -----------------------
 * Given a string s which represents an expression, evaluate this expression and return its value.
 *
 * The integer division should truncate toward zero.
 *
 * Example 1:
 * Input: s = "3+2*2"
 * Output: 7
 *
 * Example 2:
 * Input: s = " 3/2 "
 * Output: 1
 *
 * Example 3:
 * Input: s = " 3+5 / 2 "
 * Output: 5
 *
 *  Constraints:
 * 1 <= s.length <= 3 * 10^5
 * s consists of integers and operators ('+', '-', '*', '/') separated by some number of spaces.
 * s represents a valid expression.
 * All the integers in the expression are non-negative integers in the range [0, 2^31 - 1].
 * The answer is guaranteed to fit in a 32-bit integer.
 */
public class Basic_Calculator_II_227 {
    public static int calculate(String s) {
        return calculate_3(s);
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/basic-calculator-ii/solution/ 之 Approach2
     *
     * 思考：
     * 1.如果操作符是+和-，由于后续的操作符可能是"*和/"或"+和-"，那么这样就不能直接计算，需要引入中间值。
     * 2.本质上还是先计算乘除，再计算加减。
     * 3.如果操作符是*和/，计算中间值，中间值不累加到res中。
     * 4.如果操作符是+和-，把中间值累加到res中，计算中间值。
     * 5.只有中间值才能直接参与res的计算。
     *
     * @param s
     * @return
     */
    public static int calculate_4(String s) {
        if (s == null || s.isEmpty()) return 0;
        int length = s.length();
        int currentNumber = 0, lastNumber = 0, result = 0;
        char operation = '+';
        for (int i = 0; i < length; i++) {
            char currentChar = s.charAt(i);
            if (Character.isDigit(currentChar)) {
                currentNumber = (currentNumber * 10) + (currentChar - '0');
            }
            if (!Character.isDigit(currentChar) && !Character.isWhitespace(currentChar) || i == length - 1) {
                if (operation == '+' || operation == '-') {
                    //这里要注意是先累加lastNumer，而不是currentNumber。
                    //只有中间值lastNumber才能直接参与result的计算。
                    result += lastNumber;
                    //currentNumber只参与lastNumber的计算。
                    lastNumber = (operation == '+') ? currentNumber : -currentNumber;
                } else if (operation == '*') {
                    lastNumber = lastNumber * currentNumber;
                } else if (operation == '/') {
                    lastNumber = lastNumber / currentNumber;
                }
                operation = currentChar;
                currentNumber = 0;
            }
        }
        // 更上面的result计算一样。每次计算都只有res和lastNumber参与。这表示每次都是一个数字参与计算，而不是两个。跟使用stack是一样的方式。
        // lastNumber在这里替代了stack。lastNumber是下一个要参与result计算的值。
        result += lastNumber;//只有中间值lastNumber才能直接参与result的计算。
        return result;
    }

    /**
     * round 2
     * review
     *
     * 思路：
     * 1.两步法。第一步先计算*和/，第二步再计算+和-。可以把-转化成+。
     * 2.遇到第二个操作符才根据第一个操作符计算前面的两个数字，所以需要缓存前两个数字和操作符。
     *
     * 本方法使用了stack。AC和Solution的方案中有不适用stack的实现，太厉害了。它们本质上还是先计算乘除，再计算加减。
     * stack方案的代码优化版本见calculate_2()。其核心思路严格按照上述的思路执行。
     *
     * 验证通过：
     * Runtime: 17 ms, faster than 72.23% of Java online submissions for Basic Calculator II.
     * Memory Usage: 44.8 MB, less than 54.24% of Java online submissions for Basic Calculator II.
     *
     * @param s
     * @return
     */
    public static int calculate_3(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int n = 0;
        char lastOps = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('0' <= c && c <= '9') {
                n = n * 10 + c - '0';
            }
            if (c == '+' || c == '-') {
                if (lastOps == '*') {
                    stack.push(stack.pop() * n);
                } else if (lastOps == '/') {
                    stack.push(stack.pop() / n);
                } else if (lastOps == '+') {
                    stack.push(stack.pop() + n);
                } else if (lastOps == '-') {
                    stack.push(stack.pop() - n);
                }
                lastOps = c;
                n = 0;
            } else if (c == '*' || c == '/' || i == s.length() - 1) {
                if (lastOps == '*') {
                    stack.push(stack.pop() * n);
                } else if (lastOps == '/') {
                    stack.push(stack.pop() / n);
                } else if (lastOps == '+') {
                    stack.push(n);
                } else if (lastOps == '-') {
                    stack.push(-n);
                }
                lastOps = c;
                n = 0;
            }
        }
        int res = 0;
        while (stack.size() > 0) {
            res += stack.pop();
        }
        return res;
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/basic-calculator-ii/discuss/63003/Share-my-java-solution
     *
     * 利用stack进行计算，使用sign保存上一个操作符
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 67.50% of Java online submissions for Basic Calculator II.
     * Memory Usage: 38.5 MB, less than 99.69% of Java online submissions for Basic Calculator II.
     * @param s
     * @return
     */
    public static int calculate_2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = 0;
        char sign = '+';//初始默认为+
        Stack<Integer> stack = new Stack<>();
        // stack,push(n);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                n = n * 10 + c - '0';
            }
            //需要注意最后1位的处理，必须要处理最后运算符；还要注意空字符有可能出现在最后
            if ((!Character.isDigit(c) && ' ' != c) || i == s.length() - 1) {
                //这里很巧妙用sign缓存了上一个操作符
                if ('+' == sign) {
                    stack.push(n);
                } else if ('-' == sign) {
                    stack.push(-n);
                } else if ('*' == sign) {
                    stack.push(stack.pop() * n);
                } else if ('/' == sign) {
                    stack.push(stack.pop() / n);
                }
                //这里很巧妙用sign更新缓存的操作符
                sign = c;
                n = 0;
            }
        }
        int ret = 0;
        for (int i : stack) {
            ret += i;
        }
        return ret;
    }

    /**
     * 两步法：
     * 1.计算*和/
     * 2.计算+和-
     *
     * 验证通过:
     * Runtime: 9 ms, faster than 67.50% of Java online submissions for Basic Calculator II.
     * Memory Usage: 40.4 MB, less than 25.12% of Java online submissions for Basic Calculator II.
     * @param s
     * @return
     */
    public static int calculate_1(String s) {
        s = s.replace(" ", "");
        //这里没有使用stack，因为stack最终遍历时，后入栈的先出栈，计算减法时逻辑比较复杂
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();
        int n1 = 0;
        //第一次先计算*和/，并提取数字
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                n1 = n1 * 10 + Character.getNumericValue(c);
            } else {
                if (c == '*' || c == '/') {
                    int n2 = 0;
                    //获取下一个数
                    i++;
                    while (i < s.length() && Character.isDigit(s.charAt(i))) {
                        n2 = n2 * 10 + Character.getNumericValue(s.charAt(i));
                        i++;
                    }
                    i--;
                    if (c == '*') {
                        n1 *= n2;
                    } else {
                        n1 /= n2;
                    }
                } else {
                    numList.add(n1);
                    opList.add(c);
                    n1 = 0;
                }
            }
        }
        numList.add(n1);
        //第二次计算+和-
        int ret = numList.get(0);
        for (int i = 0; i < opList.size(); i++) {
            char c = opList.get(i);
            if (c == '+') {
                ret += numList.get(i + 1);
            } else {
                ret -= numList.get(i + 1);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("3+2*2", 7);
//        do_func("3/2", 1);
//        do_func("3+5/2", 5);
//        do_func("3+5 / 2", 5);
//        do_func("0 / 2", 0);
//        do_func("323+244*222", 54491);
//        do_func("334+545 / 29", 352);
//        do_func("0-111", -111);
//        do_func("0-2147483647", -2147483647);
//        do_func("0-2147483646", -2147483646);
//        do_func("1-1+1", 1);
//        do_func("2*3+4", 10);
//        do_func("2*30+4", 64);
//        do_func(" 3/2 ", 1);

    }

    private static void do_func(String s, int expected) {
        int ret = calculate(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
