package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 241. Different Ways to Add Parentheses
 * Medium
 * -------------------------
 * Given a string expression of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. You may return the answer in any order.
 * <p>
 * The test cases are generated such that the output values fit in a 32-bit integer and the number of different results does not exceed 10^4.
 * <p>
 * Example 1:
 * Input: expression = "2-1-1"
 * Output: [0,2]
 * Explanation:
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 * <p>
 * Example 2:
 * Input: expression = "2*3-4*5"
 * Output: [-34,-14,-10,-10,10]
 * Explanation:
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 * <p>
 * Constraints:
 * 1 <= expression.length <= 20
 * expression consists of digits and the operator '+', '-', and '*'.
 * All the integer values in the input expression are in the range [0, 99].
 * The integer values in the input expression do not have a leading '-' or '+' denoting the sign.
 */
public class Different_Ways_to_Add_Parentheses_241 {

    public static List<Integer> diffWaysToCompute(String expression) {
        return diffWaysToCompute_r3_2(expression);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 递归思路
     * 任何输入可以转化为 (ex1) ⊙ (ex2) 两部分，⊙表示任一计算符号。
     * f(ex)=[f(ex[0:0]) ⊙ f(ex[1:-1]), f(ex[0:1]) ⊙ f(ex[2:-1]), .. , f(ex[0:-2]) ⊙ f(ex[-1:-1])]
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 100.00%
     * Memory 42.51 MB Beats 60.94%
     *
     * @param expression
     * @return
     */
    public static List<Integer> diffWaysToCompute_r3_2(String expression) {
        List<Integer> res = new ArrayList<>();
        if (expression == null || expression.length() == 0) return res;
        //预处理输入，把数字和计算符号分离，并分别保存在数组中
        List<Integer> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        int t = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                t = t * 10 + (c - '0');
            }
            if (!Character.isDigit(c) || i == expression.length() - 1) {
                nums.add(t);
                ops.add(c);
                t = 0;
            }
        }
        res = helper_r3_2(nums, ops, 0, nums.size() - 1);
        return res;
    }

    private static List<Integer> helper_r3_2(List<Integer> nums, List<Character> ops, int beg, int end) {
        List<Integer> res = new ArrayList<>();
        if (nums.size() == 0 || beg > end) return res;
        if (beg == end) {
            res.add(nums.get(beg));
            return res;
        }
        for (int i = beg; i < end; i++) {//review "i<end" 不能写成 "i<=end"
            List<Integer> res_1 = helper_r3_2(nums, ops, beg, i);
            List<Integer> res_2 = helper_r3_2(nums, ops, i + 1, end);
            //合并，并计算
            if (res_2.size() == 0) {
                res.addAll(new ArrayList<>(res_1));
            } else {
                for (int k : res_1) {
                    for (int l : res_2) {
                        res.add(op_r3_1(k, l, ops.get(i)));
                    }
                }
            }
        }

        return res;
    }

    private static int op_r3_1(int a, int b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        }
        return 0;
    }

    /**
     * round 2
     * 思考：
     * 1.分治+递归思路。
     * 2.现根据操作符分割字符串，再计算字符串，最后合并结果
     * <p>
     * 算法：
     * 1.遍历输入字符串
     * 2.如果输入字符串是单个数字，那么返回该数字
     * 3.如果是运算符，那么以运算符为界，分别递归计算其前面的字符串和后面的字符串，再使用该运算符计算这两个值。
     * 4.公式为F[s] = F[s[0:i-1]] OP F[s[i+1:]] ，i是运算符的下标，0<i<n
     * <p>
     * 可以使用缓存优化耗时，即把计算过的表达式的结果缓存起来。如：memo = new ArrayList[len+1][len+1]
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 70.12% of Java online submissions for Different Ways to Add Parentheses.
     * Memory Usage: 43.7 MB, less than 14.17% of Java online submissions for Different Ways to Add Parentheses.
     *
     * @param expression
     * @return
     */
    public static List<Integer> diffWaysToCompute_2(String expression) {
        List<Integer> res = new ArrayList<>();
        if (expression == null || expression.length() == 0) return res;
        int n = 0;
        boolean onlyDigit = true;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if ('0' <= c && c <= '9') {
                n = n * 10 + c - '0';
            } else if (c == '+' || c == '-' || c == '*') {
                n = 0;
                onlyDigit = false;
                List<Integer> left = diffWaysToCompute(expression.substring(0, i));
                List<Integer> right = diffWaysToCompute(expression.substring(i + 1));
                for (int l : left) {
                    for (int r : right) {
                        res.add(op(l, r, c));
                    }
                }
            }
        }
        if (onlyDigit) res.add(n);
        return res;
    }

    private static int op(int a, int b, char op) {
        int res = 0;
        switch (op) {
            case '+':
                res = a + b;
                break;
            case '-':
                res = a - b;
                break;
            case '*':
                res = a * b;
                break;
            default:
                res = 0;
        }
        return res;
    }

    /**
     * 递归法，公式为：
     * 验证通过：公式为：recursive(0,i-1) op recursive(i+1,n)
     * <p>
     * Runtime: 1 ms, faster than 98.29% of Java online submissions for Different Ways to Add Parentheses.
     * Memory Usage: 38.8 MB, less than 72.49% of Java online submissions for Different Ways to Add Parentheses.
     *
     * @param expression
     * @return
     */
    public static List<Integer> diffWaysToCompute_1(String expression) {
        return do_recursive(expression, 0, expression.length() - 1);
    }

    private static List<Integer> do_recursive(String expression, int beg, int end) {
        List<Integer> ret = new ArrayList<>();
        if (beg > end) return ret;
        //只剩数字时，提取数字并返回
        boolean onlyDigit = true;
        int rs = 0;
        for (int i = beg; i <= end; i++) {
            char c = expression.charAt(i);
            if (c == '*' || c == '+' || c == '-') {
                onlyDigit = false;
                break;
            } else {
                rs = rs * 10 + (c - '0');
            }
        }
        if (onlyDigit) {
            ret.add(rs);
            return ret;
        }

        //递归计算
        for (int i = beg; i <= end; i++) {
            char c = expression.charAt(i);
            if (c == '*' || c == '+' || c == '-') {
                //分别计算左右两部分
                List<Integer> r1 = do_recursive(expression, beg, i - 1);
                List<Integer> r2 = do_recursive(expression, i + 1, end);
                //合并
                for (Integer j : r1) {
                    if (j == null) continue;
                    for (Integer k : r2) {
                        if (k == null) continue;
                        if (c == '*') {
                            ret.add(j * k);
                        } else if (c == '+') {
                            ret.add(j + k);
                        } else if (c == '-') {
                            ret.add(j - k);
                        }

                    }
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("2-1-1", Arrays.asList(new Integer[]{0, 2}));
        do_func("2*3-4*5", Arrays.asList(new Integer[]{-34, -14, -10, -10, 10}));
    }

    private static void do_func(String expression, List<Integer> expected) {
        List<Integer> ret = diffWaysToCompute(expression);
        expected.sort((o1, o2) -> o1 - o2);
        ret.sort(Comparator.comparingInt(o -> o));
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
