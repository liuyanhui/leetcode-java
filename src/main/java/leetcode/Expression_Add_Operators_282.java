package leetcode;

import java.util.*;

/**
 * 282. Expression Add Operators
 * Hard
 * -------------------------------
 * Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.
 * <p>
 * Note that operands in the returned expressions should not contain leading zeros.
 * <p>
 * Example 1:
 * Input: num = "123", target = 6
 * Output: ["1*2*3","1+2+3"]
 * Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
 * <p>
 * Example 2:
 * Input: num = "232", target = 8
 * Output: ["2*3+2","2+3*2"]
 * Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
 * <p>
 * Example 3:
 * Input: num = "3456237490", target = 9191
 * Output: []
 * Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.
 * <p>
 * Constraints:
 * 1 <= num.length <= 10
 * num consists of only digits.
 * -2^31 <= target <= 2^31 - 1
 */
public class Expression_Add_Operators_282 {
    public static List<String> addOperators(String num, int target) {
        return addOperators_r3_1(num, target);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 穷举+递归。
     * 1.1. 初始思路。
     * 每次取一个数字num[i]，然后分别计算三种运算符。
     * 递归函数如下：
     * <pre>
     * {@code
     * F(num,beg,path,res):
     *     IF beg>=len(num) THEN
     *         IF eval(path) == target THEN
     *             res.add(path)
     *         RETURN
     *     //计算加法
     *     path.add('+')
     *     path.add(num[beg])
     *     F(num,beg,path,res)
     *     path.remove(-1)
     *     path.remove(-1)
     *     //计算减法
     *     path.add('-')
     *     path.add(num[beg])
     *     F(num,beg,path,res)
     *     path.remove(-1)
     *     path.remove(-1)
     *     //计算乘法
     *     path.add('*')
     *     path.add(num[beg])
     *     F(num,beg,path,res)
     *     path.remove(-1)
     *     path.remove(-1)
     * }
     * </pre>
     * 1.2. Complexity 复杂度
     * 相当于每个数字都有三种可能，所以 Time Complexity: O(3^N) 。
     * 2. 优化分析
     * 根据BUD优化思路，【1.】 中存在重复的表达式计算，这些计算是可以优化的。
     * 求解的搜索空间过大为(3^N)个需要缩小搜索空间。target 是否可以使用？
     * (3^N) 的复杂度很难优化，只能优化计算字符串的部分。
     *
     * addOperators_2()更简单，性能更好。利用数学方法替代采用Stack计算表达式。思路如下：
     * a+b*c 从左向右计算。 n=a+b => a+b*c = n-b+b*c
     *
     * <p>
     * 验证通过：
     * Runtime 388 ms Beats 5.02%
     * Memory 45.40 MB Beats 74.07%
     *
     * @param num
     * @param target
     * @return
     */
    public static List<String> addOperators_r3_1(String num, int target) {
        List<String> res = new ArrayList<>();
        if (num == null || num.length() == 0) return res;
        StringBuilder expr = new StringBuilder();
        helper_r3_1(num, target, 0, expr, res);
        return res;
    }

    private static void helper_r3_1(String num, int target, int beg, StringBuilder expr, List<String> res) {
        //特殊情况：形如 [00] [?*0] [?+0] [?-0] 的情况，不能直接追加数字。即不能出现Leading zeros。
        //过滤模式为: '0d', '*0d', '+0d', '-0d'
        int len = expr.length();
        //过滤 "00"
        if (len >= 2 && expr.charAt(0) == '0' && Character.isDigit(expr.charAt(1))) return;
        //过滤 '*0d', '+0d', '-0d'
        if (len > 2 && !Character.isDigit(expr.charAt(len - 3)) && expr.charAt(len - 2) == '0' && Character.isDigit(expr.charAt(len - 1)))
            return;

        if (beg >= num.length()) {
            String expr_t = expr.toString();
            if (target == eval_r3_1(expr_t)) {
                res.add(expr_t);
            }
            return;
        }
        // char
        expr.append(num.charAt(beg));
        helper_r3_1(num, target, beg + 1, expr, res);
        expr.deleteCharAt(expr.length() - 1);
        if (expr.length() == 0) return;
        //'+'
        expr.append("+");
        expr.append(num.charAt(beg));
        helper_r3_1(num, target, beg + 1, expr, res);
        expr.deleteCharAt(expr.length() - 1);
        expr.deleteCharAt(expr.length() - 1);
        //'-'
        expr.append("-");
        expr.append(num.charAt(beg));
        helper_r3_1(num, target, beg + 1, expr, res);
        expr.deleteCharAt(expr.length() - 1);
        expr.deleteCharAt(expr.length() - 1);
        //'+'
        expr.append("*");
        expr.append(num.charAt(beg));
        helper_r3_1(num, target, beg + 1, expr, res);
        expr.deleteCharAt(expr.length() - 1);
        expr.deleteCharAt(expr.length() - 1);

    }

    //采用了与eval()不同的实现方案
    private static long eval_r3_1(String expression) {
        if (expression == null || expression.length() == 0) return 0;
        char lastOps = '+';
        long lastNum = 0;
        Stack<Long> stack = new Stack<>();
        long res = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                lastNum = lastNum * 10 + (c - '0');
            } else {
                if (c == '+' || c == '-') {
                    if (lastOps == '+') {
                        res += lastNum;
                    } else if (lastOps == '-') {
                        res -= lastNum;
                    } else if (lastOps == '*') {
                        res *= lastNum;
                    }
                } else if (c == '*') {
                    if (lastOps == '+' || lastOps == '-') {
                        stack.push(res);
                        res = lastOps == '+' ? lastNum : -lastNum;
                    } else if (lastOps == '*') {
                        res *= lastNum;
                    }
                }
                lastOps = c;
                lastNum = 0;
            }
            if (i == expression.length() - 1) {
                if (lastOps == '+') {
                    res += lastNum;
                } else if (lastOps == '-') {
                    res -= lastNum;
                } else if (lastOps == '*') {
                    res *= lastNum;
                }
            }
        }
        while (!stack.empty()) {
            res += stack.pop();
        }
        return res;
    }

    /**
     * review
     * 更巧妙的方法，原因：无需最后才计算字符串格式的表达式，通过巧妙的数学方式可以免去使用Stack计算表达式的值。
     * <p>
     * 参考文档：
     * https://leetcode.com/problems/expression-add-operators/discuss/572099/C%2B%2BJavaPython-Backtracking-and-Evaluate-on-the-fly-Clean-and-Concise
     * <p>
     * 思路说明：
     * 1.形如a+b*c的表达式，无法采用从左向右的计算方式。通常使用Stack进行计算。
     * 2.[review] 本方法引入了一种可以从左向右计算的方法。算法为：记n=a+b，即先计算a+b，那么a+b*c=n-b+b*c
     * <p>
     * 验证通过：
     * Runtime: 343 ms, faster than 19.83% of Java online submissions for Expression Add Operators.
     * Memory Usage: 117.6 MB, less than 23.25% of Java online submissions for Expression Add Operators.
     */
    public static List<String> addOperators_2(String num, int target) {
        List<String> res = new ArrayList<>();
        backtrack(num, 0, target, "", 0, 0, res);
        return res;
    }

    private static void backtrack(String num, int pos, int target,
                                  String path, long sum, long prevNum, List<String> res) {
        if (pos == num.length()) {
            if (sum == target) {
                res.add(path);
                return;
            }
        }
        for (int i = pos; i < num.length(); i++) {
            //过滤形如 [00] [?*0] [?+0] [?-0] 的情况
            if (i > pos && num.charAt(pos) == '0') break;
            long n = Long.parseLong(num.substring(pos, i + 1));
            if (pos == 0) {
                //追加数字
                backtrack(num, i + 1, target, path + n, n, n, res);
            } else {
                //追加*
                backtrack(num, i + 1, target, path + "*" + n, sum - prevNum + prevNum * n, prevNum * n, res);
                //追加+
                backtrack(num, i + 1, target, path + "+" + n, sum + n, n, res);
                //追加-
                backtrack(num, i + 1, target, path + "-" + n, sum - n, -n, res);
            }
        }
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
     * <p>
     * 算法：
     * 1.递归+穷举法
     * 2.分为四种情况：1.插入数字；2.插入+；3.插入-；4.插入*
     * <p>
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

    /**
     * @param num
     * @param last       最新的计算符后的数字
     * @param pos        当前需要计算的数字在num中的位置
     * @param target
     * @param expression 当前表达式
     * @param res
     */
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
        //追加数字
        //过滤形如 [00] [?*0] [?+0] [?-0] 的情况，这些情况下，不能直接追加数字
        if (last != 0) {
            last = last == -1 ? 0 : last;
            expression.append(String.valueOf(n));
            helper(num, last * 10 + n, pos + 1, target, expression, res);
            expression.delete(expression.length() - 1, expression.length());
        }

        if (expression.length() > 0) {
            last = n;
            //追加*
            expression.append("*");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
            //追加+
            expression.append("+");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
            //追加-
            expression.append("-");
            expression.append(String.valueOf(num.charAt(pos)));
            helper(num, last, pos + 1, target, expression, res);
            expression.delete(expression.length() - 2, expression.length());
        }
    }

    /**
     * 计算表达式的值
     *
     * @param expression
     * @return
     */
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
        Collections.sort(ret);
        System.out.println(ret);
        Arrays.sort(expected);
        ArrayUtils.printIntArray(expected);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
