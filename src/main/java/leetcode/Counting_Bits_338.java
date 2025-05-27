package leetcode;

import java.util.Arrays;

/**
 * 338. Counting Bits
 * Easy
 * ------------------------------
 * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 * <p>
 * Example 1:
 * Input: n = 2
 * Output: [0,1,1]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * <p>
 * Example 2:
 * Input: n = 5
 * Output: [0,1,1,2,1,2]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 * <p>
 * Constraints:
 * 0 <= n <= 10^5
 * <p>
 * Follow up:
 * It is very easy to come up with a solution with a runtime of O(n log n). Can you do it in linear time O(n) and possibly in a single pass?
 * Can you do it without using any built-in function (i.e., like __builtin_popcount in C++)?
 */
public class Counting_Bits_338 {
    public static int[] countBits(int n) {
        return countBits_r3_1(n);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 从0到n依次计算每个数字。
     * 2. 总结规律。公式为：
     * ans[i]=ans[2^m]+ans[i-2^m]，其中m是最大的且2^m<i的数。
     * countBits_r3_1()采用此思路。
     * 3. 总结规律2.
     * 直观理解。
     * 如果 j=i<<1，即j为i的2倍时，j的二进制中1的个数与i相同的。j为i的2倍加1时，j的二进制中1的个数比i多1.
     * countBits_1()的实现就是此思路
     * <p>
     * 验证通过：
     * Runtime 3 ms Beats 33.22%
     * Memory 46.72 MB Beats 50.65%
     *
     * @param n
     * @return
     */
    public static int[] countBits_r3_1(int n) {
        int[] ans = new int[n + 1];
        int m = 0;
        for (int i = 1; i <= n; i++) {
            if (1 << (m + 1) == i) {
                m++;
            }
            int t = 1 << m;
            if (i == t) {
                ans[i] = 1;
            } else {
                ans[i] = ans[t] + ans[i - t];
            }
        }
        return ans;
    }

    /**
     * O(NlogN)的解法简单，O(N)的解法有难度
     * <p>
     * O(N)的解法参考思路：
     * https://leetcode.com/problems/counting-bits/solutions/1808016/c-vectors-only-easy-to-understand-full-explanation/
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 99.65%
     * Memory 47 MB Beats 27.9%
     *
     * @param n
     * @return
     */
    public static int[] countBits_1(int n) {
        int[] res = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            res[i] = res[i / 2] + i % 2;
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(2, new int[]{0, 1, 1});
        do_func(5, new int[]{0, 1, 1, 2, 1, 2});
    }

    private static void do_func(int n, int[] expected) {
        int[] ret = countBits(n);
        ArrayUtils.printlnIntArray(ret);
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
