package leetcode;

import java.util.Arrays;

/**
 * 338. Counting Bits
 * Easy
 * ------------------------------
 * Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 *
 * Example 1:
 * Input: n = 2
 * Output: [0,1,1]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 *
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
 *
 * Constraints:
 * 0 <= n <= 10^5
 *
 * Follow up:
 * It is very easy to come up with a solution with a runtime of O(n log n). Can you do it in linear time O(n) and possibly in a single pass?
 * Can you do it without using any built-in function (i.e., like __builtin_popcount in C++)?
 */
public class Counting_Bits_338 {
    public static int[] countBits(int n) {
        return countBits_1(n);
    }

    /**
     * O(NlogN)的解法简单，O(N)的解法有难度
     *
     * O(N)的解法参考思路：
     * https://leetcode.com/problems/counting-bits/solutions/1808016/c-vectors-only-easy-to-understand-full-explanation/
     *
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
