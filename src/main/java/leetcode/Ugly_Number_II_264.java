package leetcode;

/**
 * 264. Ugly Number II
 * Medium
 * --------------------------
 * An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
 * Given an integer n, return the nth ugly number.
 *
 * Example 1:
 * Input: n = 10
 * Output: 12
 * Explanation: [1, 2, 3, 4, 5, 6, 8, 9, 10, 12] is the sequence of the first 10 ugly numbers.
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 * Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.
 *
 * Constraints:
 * 1 <= n <= 1690
 */
public class Ugly_Number_II_264 {
    public static int nthUglyNumber(int n) {
        return nthUglyNumber_2(n);
    }

    /**
     * 问题转换为：min(2^i * 3^j * 5^k), i+j+k<n。每次计算后i,j,k有条件的加1
     *
     * DP思路：
     *  We have an array k of first n ugly number. We only know, at the beginning, the first one, which is 1. Then
     * k[1] = min( k[0]x2, k[0]x3, k[0]x5). The answer is k[0]x2. So we move 2's pointer to 1. Then we test:
     * k[2] = min( k[1]x2, k[0]x3, k[0]x5). And so on.
     * Be careful about the cases such as 6, in which we need to forward both pointers of 2 and 3.
     * x here is multiplication.
     *
     * 参考思路：
     * https://leetcode.com/problems/ugly-number-ii/discuss/69364/My-16ms-C%2B%2B-DP-solution-with-short-explanation
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 53.95% of Java online submissions for Ugly Number II.
     * Memory Usage: 38 MB, less than 72.40% of Java online submissions for Ugly Number II.
     *
     * @param n
     * @return
     */
    public static int nthUglyNumber_2(int n) {
        int[] arr = new int[n];
        int k1 = 0, k2 = 0, k3 = 0;
        arr[0] = 1;
        for (int i = 1; i < n; i++) {
            arr[i] = Math.min(Math.min(arr[k1] * 2, arr[k2] * 3), arr[k3] * 5);
            if (arr[i] % 2 == 0) k1++;
            if (arr[i] % 3 == 0) k2++;
            if (arr[i] % 5 == 0) k3++;
        }
        return arr[n - 1];
    }

    /**
     * 验证失败：Time Limit Exceeded
     * @param n
     * @return
     */
    public static int nthUglyNumber_1(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        int ret = 1;
        int count = 1;
        while (count < n) {
            int tmp = ++ret;
            while (tmp % 2 == 0) {
                tmp /= 2;
            }
            while (tmp % 3 == 0) {
                tmp /= 3;
            }
            while (tmp % 5 == 0) {
                tmp /= 5;
            }
            if (tmp == 1) count++;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(10, 12);
        do_func(2, 2);
        do_func(1, 1);
        do_func(11, 15);
        do_func(100, 1536);
        do_func(1352, 402653184);
    }

    private static void do_func(int n, int expected) {
        int ret = nthUglyNumber(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
