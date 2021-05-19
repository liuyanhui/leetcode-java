package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 241. Different Ways to Add Parentheses
 * Medium
 * -------------------------
 * Given a string expression of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. You may return the answer in any order.
 *
 * Example 1:
 * Input: expression = "2-1-1"
 * Output: [0,2]
 * Explanation:
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 *
 * Example 2:
 * Input: expression = "2*3-4*5"
 * Output: [-34,-14,-10,-10,10]
 * Explanation:
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 *
 * Constraints:
 * 1 <= expression.length <= 20
 * expression consists of digits and the operator '+', '-', and '*'.
 */
public class Different_Ways_to_Add_Parentheses_241 {
    /**
     * 递归法，公式为：
     * 验证通过：公式为：recursive(0,i-1) op recursive(i+1,n)
     *
     * Runtime: 1 ms, faster than 98.29% of Java online submissions for Different Ways to Add Parentheses.
     * Memory Usage: 38.8 MB, less than 72.49% of Java online submissions for Different Ways to Add Parentheses.
     * @param expression
     * @return
     */
    public static List<Integer> diffWaysToCompute(String expression) {
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
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
