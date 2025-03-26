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
 * <p>
 * Example 1:
 * Input: n = 12, primes = [2,7,13,19]
 * Output: 32
 * Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12 super ugly numbers given primes = [2,7,13,19].
 * <p>
 * Example 2:
 * Input: n = 1, primes = [2,3,5]
 * Output: 1
 * Explanation: 1 has no prime factors, therefore all of its prime factors are in the array primes = [2,3,5].
 * <p>
 * Constraints:
 * 1 <= n <= 10^6
 * 1 <= primes.length <= 100
 * 2 <= primes[i] <= 1000
 * primes[i] is guaranteed to be a prime number.
 * All the values of primes are unique and sorted in ascending order.
 */
public class Super_Ugly_Number_313 {
    public static int nthSuperUglyNumber(int n, int[] primes) {
        return nthSuperUglyNumber_r3_1(n, primes);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * [Group] Ugly_Number_II_264
     * <p>
     * Thinking
     * 1. 使用 【264. Ugly Number I】的思路。
     * 设 ugly_arr[] 为结果集，index_arr[] 为 primes[] 中的数在 ugly_arr[]中的位置。
     * 每次在 ugly_arr[] 的新值，需要计算所有的 primes[]
     * WHILE cur<n
     * ugly_arr[cur]=min(primes[i]*ugly_arr[index_arr[i]])
     * 被选中的 primes[i] 执行 index_arr[i]++
     * cur++
     * <p>
     * 验证通过：
     * Runtime 59 ms Beats 42.86%
     * Memory 42.37 MB Beats 69.01%
     *
     * @param n
     * @param primes
     * @return
     */
    public static int nthSuperUglyNumber_r3_1(int n, int[] primes) {
        if (n <= 0) return 0;
        int[] ugly_arr = new int[n];
        ugly_arr[0] = 1;
        int[] index_arr = new int[primes.length];
        int cur = 1;
        while (cur < n) {
            //先计算 nth super ugly number
            int t_min = Integer.MAX_VALUE;
            for (int i = 0; i < primes.length; i++) {
                //防止int溢出
                if (Integer.MAX_VALUE / primes[i] < ugly_arr[index_arr[i]]) continue;
                t_min = Math.min(t_min, primes[i] * ugly_arr[index_arr[i]]);
            }

            //再计算 index_arr[] 下标
            //review 这里很关键， 因为 ugly_arr[cur] 可能来自多个源头
            for (int i = 0; i < primes.length; i++) {
                if (t_min % primes[i] == 0) {
                    index_arr[i]++;
                }
            }
            ugly_arr[cur] = t_min;
            cur++;
        }
        return ugly_arr[n - 1];
    }


    /**
     * 参考题目：264. Ugly Number II
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/super-ugly-number/discuss/277313/My-view-of-this-question-hope-it-can-help-you-understand!!!
     * https://leetcode.com/problems/super-ugly-number/discuss/76291/Java-three-methods-23ms-36-ms-58ms(with-heap)-performance-explained
     * <p>
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
                val = Math.min(val, ugly[idxArr[j]] * primes[j]);
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
     * round 3 不是DP思路。是 brute force 思路。i从 1~无穷, 依次计算是否 primes[] 中的数是i的 prime factor 。如果是，计入结果集；如果不是，不计入结果集。
     * <p>
     * 把数分解成质数因子，如：16->{2},15->{3,5},100->{}
     * map<int,list>保存分解后质数，key-待分解的整数，value->质数因子列表
     * f(n) = f(i) && f(n/i)
     * f(100)=f(2) && f(50)
     * 1.整数分解；2.从小到大计算；3.结果缓存
     * <p>
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
        do_func(5911, new int[]{2, 3, 5, 7}, 2144153025);
    }

    private static void do_func(int n, int[] primes, int expected) {
        int ret = nthSuperUglyNumber(n, primes);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
