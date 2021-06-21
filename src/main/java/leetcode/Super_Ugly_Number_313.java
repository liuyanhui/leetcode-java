package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 313. Super Ugly Number
 * Medium
 * ---------------------------
 * A super ugly number is a positive integer whose prime factors are in the array primes.
 * Given an integer n and an array of integers primes, return the nth super ugly number.
 * The nth super ugly number is guaranteed to fit in a 32-bit signed integer.
 *
 * Example 1:
 * Input: n = 12, primes = [2,7,13,19]
 * Output: 32
 * Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12 super ugly numbers given primes = [2,7,13,19].
 *
 * Example 2:
 * Input: n = 1, primes = [2,3,5]
 * Output: 1
 * Explanation: 1 has no prime factors, therefore all of its prime factors are in the array primes = [2,3,5].
 *
 * Constraints:
 * 1 <= n <= 10^6
 * 1 <= primes.length <= 100
 * 2 <= primes[i] <= 1000
 * primes[i] is guaranteed to be a prime number.
 * All the values of primes are unique and sorted in ascending order.
 */
public class Super_Ugly_Number_313 {
    public static int nthSuperUglyNumber(int n, int[] primes) {
        return nthSuperUglyNumber_2(n, primes);
    }

    /**
     * 参考题目：264. Ugly Number II
     *
     * 参考思路：
     * https://leetcode.com/problems/super-ugly-number/discuss/277313/My-view-of-this-question-hope-it-can-help-you-understand!!!
     * https://leetcode.com/problems/super-ugly-number/discuss/76291/Java-three-methods-23ms-36-ms-58ms(with-heap)-performance-explained
     *
     * 验证通过:
     * Runtime: 20 ms, faster than 66.02% of Java online submissions for Super Ugly Number.
     * Memory Usage: 36.9 MB, less than 71.80% of Java online submissions for Super Ugly Number.
     *
     * @param n
     * @param primes
     * @return
     */
    public static int nthSuperUglyNumber_2(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] idxArr = new int[primes.length];
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            //计算最小值
            int val = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++) {
                val  = Math.min(val,ugly[idxArr[j]] * primes[j]);
            }
            //下标步进
            for (int j = 0; j < primes.length; j++) {
                if (val % primes[j] == 0) {
                    idxArr[j]++;
                }
            }
            //ugly 赋值
            ugly[i] = val;
        }

        return ugly[n - 1];
    }

    /**
     * 貌似是DP思路。
     *
     * 把数分解成质数因子，如：16->{2},15->{3,5},100->{}
     * map<int,list>保存分解后质数，key-待分解的整数，value->质数因子列表
     * f(n) = f(i) && f(n/i)
     * f(100)=f(2) && f(50)
     * 1.整数分解；2.从小到大计算；3.结果缓存
     *
     * 验证失败：Time Limit Exceeded。 81 / 83 test cases passed.
     *
     * @param n
     * @param primes
     * @return
     */
    public static int nthSuperUglyNumber_1(int n, int[] primes) {
        if (n <= 0 || primes == null || primes.length == 0) return -1;
        int ret = 1;
        Set<Integer> cached = new HashSet<>();
        int cur = 1;
        cached.add(1);
        while (cached.size() < n) {
            cur++;
            for (int p : primes) {
                if (cur < p) break;
                if (cur % p == 0) {
                    if (cached.contains(cur / p)) {
                        cached.add(cur);
                        ret = cur;
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(12, new int[]{2, 7, 13, 19}, 32);
        do_func(1, new int[]{2, 3, 5}, 1);
        do_func(100000, new int[]{7, 19, 29, 37, 41, 47, 53, 59, 61, 79, 83, 89, 101, 103, 109, 127, 131, 137, 139, 157, 167, 179, 181, 199, 211, 229, 233, 239, 241, 251}, 1092889481);
    }

    private static void do_func(int n, int[] primes, int expected) {
        int ret = nthSuperUglyNumber(n, primes);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
