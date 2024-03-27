package leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/gray-code/
 * 89. Gray Code
 * Medium
 * -----------------
 * An n-bit gray code sequence is a sequence of 2^n integers where:
 *  Every integer is in the inclusive range [0, 2^n - 1],
 *  The first integer is 0,
 *  An integer appears no more than once in the sequence,
 *  The binary representation of every pair of adjacent integers differs by exactly one bit, and
 *  The binary representation of the first and last integers differs by exactly one bit.
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
        return grayCode_4(n);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     *
     * Thinking：
     * 1. 递归+穷举+缓存
     * 1.1. 递归函数
     * helper(int n,Set<Integer> exist,List<Integer> ret)
     * 1.2. 查找gray-code算法
     * findGrayCode(int num)
     * 采用Bit运算，针对输入值num，遍历替换n个bit中的某一个。
     * 时间复杂度O(n)
     * 1.3.
     * 整体时间复杂度O(n*(2^n))
     *
     * 找出规律:
     * 1. grayCode(n)与grayCode(n-1)的关系
     * 2. 返回结果的规律
     * 3. https://leetcode.com/problems/gray-code/solutions/29891/share-my-solution/
     * My idea is to generate the sequence iteratively. For example, when n=3, we can get the result based on n=2.
     * 00,01,11,10 -> (000,001,011,010 ) (110,111,101,100). The middle two numbers only differ at their highest bit, while the rest numbers of part two are exactly symmetric of part one. It is easy to see its correctness.
     * 4. grayCode_3()
     *    grayCode_1()
     *
     *
     * 验证失败，逻辑正确
     * Exception in thread "main" java.lang.StackOverflowError
     *
     * @param n
     * @return
     */
    public static List<Integer> grayCode_4(int n) {
        List<Integer> ret = new ArrayList<>();
        Set<Integer> exist = new HashSet<>();
        ret.add(0);
        exist.add(0);
        helper(n, exist, ret);
        return ret;
    }

    private static boolean helper(int n, Set<Integer> exist, List<Integer> ret) {
        if (ret.size() == Math.pow(2, n)) {
            //判断首尾数字是否为gray code
            for (int i = 0; i < n; i++) {
                if (ret.get(ret.size() - 1) == Math.pow(2, i)) return true;
            }
            return false;
        }
        int last = ret.get(ret.size() - 1);
        //查找gray code
        for (int i = 0; i < n; i++) {
            int t = last ^ (1 << i);//XOR
            if (!exist.contains(t)) {
                ret.add(t);
                exist.add(t);
                if (helper(n, exist, ret)) {
                    return true;
                }
                ret.remove(ret.size() - 1);
                exist.remove(t);
            }
        }
        return false;
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
//        do_func(12, Arrays.asList(new Integer[]{0, 1, 3, 2, 6, 7, 5, 4}));
        do_func(16, Arrays.asList(new Integer[]{0, 1, 3, 2, 6, 7, 5, 4}));
        System.out.println("-------Done-------");
    }

    private static void do_func(int n, List<Integer> expected) {
        List<Integer> ret = grayCode(n);
        System.out.println(ret);
        System.out.println(leetcode.ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
