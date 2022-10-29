package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 282. Expression Add Operators
 * Hard
 * -------------------------------
 * Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.
 *
 * Note that operands in the returned expressions should not contain leading zeros.
 *
 * Example 1:
 * Input: num = "123", target = 6
 * Output: ["1*2*3","1+2+3"]
 * Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
 *
 * Example 2:
 * Input: num = "232", target = 8
 * Output: ["2*3+2","2+3*2"]
 * Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
 *
 * Example 3:
 * Input: num = "3456237490", target = 9191
 * Output: []
 * Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.
 *
 * Constraints:
 * 1 <= num.length <= 10
 * num consists of only digits.
 * -2^31 <= target <= 2^31 - 1
 */
public class Expression_Add_Operators_282 {
    public static List<String> addOperators(String num, int target) {
        return addOperators_1(num, target);
    }

    /**
     * 思考：
     * 1.brute force思路。递归+穷举法，依次在每个间隙分别尝试三种计算。
     * 2.brute force+cache思路。
     * cache的数据结构为：Map<Integer,List<String>>[]
     * 缺点：空间复杂度较大。
     * 3.分治法？
     * 4.以上思路错误。
     * 5.没有括号，*比+、-的优先级要高。没有括号的情况下，直接从左向右依次计算，每前进一个数字分别计算三种情况。这样可以得出是否有解。
     * 6.根据数字和操作符组成的字符串计算结果的题目的高难度版本？先计算所有的可能，再跟target匹配？
     *
     * 算法：
     * 1.递归+穷举法
     * 2.分为四种情况：1.插入数字；2.插入+；3.插入-；4.插入*
     *
     * 验证通过：
     * Runtime: 1865 ms, faster than 5.00% of Java online submissions for Expression Add Operators.
     * Memory Usage: 118.1 MB, less than 8.00% of Java online submissions for Expression Add Operators.
     *
     * @param num
     * @param target
     * @return
     */
    public static List<String> addOperators_1(String num, int target) {
        List<String> res = new ArrayList<>();
        helper(num, -1, 0, target, new StringBuilder(), res);
        return res;
    }

    private static void helper(String num, long last, int pos, int target, StringBuilder expression, List<String> res) {
        //计算并判断是否满足条件
        if (pos == num.length()) {
            String exp = expression.toString();
            //计算结果
            if (target == eval(exp)) {
                res.add(exp);
            }
            return;
        }

        int n = num.charAt(pos) - '0';
        //插入数字
        //过滤形如 [00] [?*0] [?+0] [?-0] 的情况，这些情况下，不能直接追加数字
        if (last != 0) {
            last = last == -1 ? 0 : last;
            expression.append(String.valueOf(n));
            helper(num, last * 10 + n, pos + 1, target, expression, res);
            expression.delete(expression.length() - 1, expression.length());
        }

        if (expression.length() > 0) {
            last = n;
            //插入*
            expression.append("*");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
            //插入+
            expression.append("+");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
            //插入-
            expression.append("-");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
        }
    }
    //计算表达式的值
    private static long eval(String expression) {
        long res = 0;
        if (expression == null || expression.length() == 0) return 0;
        Stack<Long> stack = new Stack<>();
        stack.push(0L);
        char lastOps = '+';
        long n = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if ('0' <= c && c <= '9') {
                n = n * 10 + (c - '0');
            }
            if (i == expression.length() - 1 || c == '+' || c == '-' || c == '*') {
                if (lastOps == '*') {
                    stack.push(n * stack.pop());
                } else {
                    n = lastOps == '+' ? n : -n;
                    if (c == '*') {
                        stack.push(n);
                    } else {
                        stack.push(stack.pop() + n);
                    }
                }
                n = 0;
                lastOps = c;
            }
        }
        while (stack.size() > 0) {
            res += stack.pop();
        }
        return res;
    }

    public static void main(String[] args) {
        do_func("123", 6, new String[]{"1*2*3", "1+2+3"});
        do_func("232", 8, new String[]{"2*3+2", "2+3*2"});
        do_func("3456237490", 9191, new String[]{});
        do_func("123", -4, new String[]{"1-2-3"});
        do_func("2222", 0, new String[]{"22-22", "2*2-2*2", "2*2-2-2", "2+2-2*2", "2+2-2-2", "2-2*2+2", "2-2+2-2", "2-2-2+2"});
        do_func("3", 3, new String[]{"3"});
        do_func("105", 5, new String[]{"1*0+5", "10-5"});
        do_func("105", 105, new String[]{"105"});
        do_func("00", 0, new String[]{"0*0", "0+0", "0-0"});
        do_func("2147483648", Integer.MIN_VALUE, new String[]{});
        do_func("2147483647", Integer.MAX_VALUE, new String[]{"2147483647"});
    }

    private static void do_func(String num, int target, String[] expected) {
        List<String> ret = addOperators(num, target);
        System.out.println(ret);
        ArrayUtils.printIntArray(expected);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
