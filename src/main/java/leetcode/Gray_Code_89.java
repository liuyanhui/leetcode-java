package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/gray-code/
 * 89. Gray Code
 * Medium
 * -----------------
 * An n-bit gray code sequence is a sequence of 2^n integers where:
 * Every integer is in the inclusive range [0, 2^n - 1],
 * The first integer is 0,
 * An integer appears no more than once in the sequence,
 * The binary representation of every pair of adjacent integers differs by exactly one bit, and
 * The binary representation of the first and last integers differs by exactly one bit.
 *
 * Given an integer n, return any valid n-bit gray code sequence.
 *
 * Example 1:
 * Input: n = 2
 * Output: [0,1,3,2]
 * Explanation:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 * [0,2,3,1] is also a valid gray code sequence.
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * Example 2:
 * Input: n = 1
 * Output: [0,1]
 *
 * Constraints:
 * 1 <= n <= 16
 */
public class Gray_Code_89 {
    public static List<Integer> grayCode(int n) {
        return grayCode_3(n);
    }

    /**
     * n=1
     * 000000
     * 000001
     * n=2
     * 000011
     * 000010
     * n=3
     * 000110
     * 000111
     * 000101
     * 000100
     * n=4
     * 001100
     * 001101
     * 001111
     * 001110
     * 001010
     * 001011
     * 001001
     * 001000
     *
     * 思路:
     * 从低位开始计数第i位,第i-1位..第1位
     * n=i时，第i位设为1，从第i-1位到第1位是n=i-1时的结果列表的倒序
     *
     * 验证通过：
     * Runtime: 12 ms, faster than 47.28% of Java online submissions for Gray Code.
     * Memory Usage: 55.3 MB, less than 32.01% of Java online submissions for Gray Code.
     *
     * @param n
     * @return
     */
    public static List<Integer> grayCode_3(int n) {
        List<Integer> ret = new ArrayList<>();
        if (n < 1) return ret;
        ret.add(0);
        for (int i = 1; i <= n; i++) {
            int t = 1 << (i - 1);
            int len = ret.size();
            for (int j = len - 1; j >= 0; j--) {
                ret.add(ret.get(j) | t);
            }
        }
        return ret;
    }

    /**
     * 迭代思路，不使用递归
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 31.24% of Java online submissions for Gray Code.
     * Memory Usage: 46.1 MB, less than 47.91% of Java online submissions for Gray Code.
     * @param n
     * @return
     */
    public static List<Integer> grayCode_2(int n) {
        List<Integer> ret = new ArrayList<>();
        ret.add(0);
        for (int i = 0; i < n; i++) {
            int base = (int) Math.pow(2, i);
            for (int j = ret.size() - 1; j >= 0; j--) {
                ret.add(base + ret.get(j));
            }
        }
        return ret;
    }

    /**
     * 重点在于归纳出规律
     * 递归思路，
     * f(0) = [1]
     * f(1) = [f(0)] + [1+reverse(f(0))] --> [0]+[1] -> [0,1]
     * f(2) = [f(1)] + [2+reverse(f(1))] --> [0,1]+[2,3] -> [0,1,3,2]
     * f(n) = [f(n-1] + [Math.pow(2,n-1)+reverse(f(n-1)]
     *
     * 验证通过：
     * Runtime: 11 ms, faster than 19.22% of Java online submissions for Gray Code.
     * Memory Usage: 45.7 MB, less than 53.55% of Java online submissions for Gray Code.
     * @param n
     * @return
     */
    public static List<Integer> grayCode_1(int n) {
        List<Integer> ret = new ArrayList<>();
        if (n == 0) {
            ret.add(0);
            return ret;
        }
        ret.addAll(grayCode_1(n - 1));
        int base = (int) Math.pow(2, n - 1);
        for (int i = ret.size() - 1; i >= 0; i--) {
            ret.add(base + ret.get(i));
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(2, Arrays.asList(new Integer[]{0, 1, 3, 2}));
        do_func(1, Arrays.asList(new Integer[]{0, 1}));
        do_func(3, Arrays.asList(new Integer[]{0, 1, 3, 2, 6, 7, 5, 4}));
    }

    private static void do_func(int n, List<Integer> expected) {
        List<Integer> ret = grayCode(n);
        System.out.println(ret);
        System.out.println(leetcode.ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
