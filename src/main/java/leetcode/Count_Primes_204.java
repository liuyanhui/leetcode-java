package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 204. Count Primes
 * Medium
 * -------------
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Example 1:
 * Input: n = 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 *
 * Example 2:
 * Input: n = 0
 * Output: 0
 *
 * Example 3:
 * Input: n = 1
 * Output: 0
 *
 * Constraints:
 * 0 <= n <= 5 * 10^6
 */
public class Count_Primes_204 {
    public static int countPrimes(int n) {
        return countPrimes_3(n);
    }

    /**
     * round 2
     *
     * 思考：
     * 1.暴力法。依次计算每个数，时间复杂度O(N*N)。
     * 2.貌似有数学方法和计算机编程方法两种思路。
     * 3.题目要求只给出质数的个数，没要求输出每个质数。
     * 4.所有的合数都是质数的乘积
     * 5.把所有满足条件的质数缓存起来，只要某个数字能被质数集合中的数字整除，那么这个数字一定不是质数。时间复杂度O(k*N)，k为质数个数。
     *
     * 采用了5.的方案
     *
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @return
     */
    public static int countPrimes_3(int n) {
        List<Integer> primes = new ArrayList<>();
        if (n > 2) primes.add(2);
        if (n > 3) primes.add(3);
        boolean isPrime = false;
        for (int i = 4; i < n; i++) {
            isPrime = true;
            for (int j : primes) {
                if (i / j * j == i) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) primes.add(i);
        }
        return primes.size();
    }

    /**
     * 提前计算好是否为质数，不是需要的时候才计算
     * round 2:排除法
     * round 2:要么把质数挑出来，要么把合数排除。排除合数的方法更容易。
     *
     * 参考思路：
     * https://leetcode.com/problems/count-primes/discuss/57588/My-simple-Java-solution
     *
     * 验证通过：
     * Runtime: 13 ms, faster than 80.99% of Java online submissions for Count Primes.
     * Memory Usage: 36.8 MB, less than 88.59% of Java online submissions for Count Primes.
     *
     * @param n
     * @return
     */
    public static int countPrimes_2(int n) {
        if (n < 2) return 0;
        boolean[] cached = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!cached[i]) {
                count++;
                for (int j = 2; i * j < n; j++) {
                    cached[i * j] = true;
                }
            }
        }
        return count;
    }

    /**
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @return
     */
    public static int countPrimes_1(int n) {
        if (n <= 2) return 0;
        int count = 1;
        for (int i = 2; i < n; i++) {
            int c = i / 2;
            while (c > 1) {
                if (i % c == 0) {
                    count++;
                    break;
                }
                c--;
            }
        }
        return n - 1 - count;
    }

    public static void main(String[] args) {
        do_func(10, 4);
        do_func(0, 0);
        do_func(1, 0);
        do_func(2, 0);
        do_func(3, 1);
        do_func(12, 5);
        do_func(500, 95);
        do_func(5000000, 348513);
    }

    private static void do_func(int n, int expected) {
        int ret = countPrimes(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
