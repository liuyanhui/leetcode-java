package leetcode;

import java.util.Arrays;

/**
 * 372. Super Pow
 * Medium
 * -------------------------------
 * Your task is to calculate a^b mod 1337 where a is a positive integer and b is an extremely large positive integer given in the form of an array.
 *
 * Example 1:
 * Input: a = 2, b = [3]
 * Output: 8
 *
 * Example 2:
 * Input: a = 2, b = [1,0]
 * Output: 1024
 *
 * Example 3:
 * Input: a = 1, b = [4,3,3,8,5,2]
 * Output: 1
 *
 * Example 4:
 * Input: a = 2147483647, b = [2,0,0]
 * Output: 1198
 *
 * Constraints:
 * 1 <= a <= 2^31 - 1
 * 1 <= b.length <= 2000
 * 0 <= b[i] <= 9
 * b doesn't contain leading zeros.
 */
public class Super_Pow_372 {
    public static int superPow(int a, int[] b) {
        return superPow_2(a, b);
    }

    static int base = 1337;

    /**
     * round 2
     * 参考思路：superPow_1()
     *
     * Thinking:
     * (a^12345)%c =
     * (a^12340*a^5)%c =
     * (((a^12340)%c)*((a^5)%c))%c =
     * (((((a^1234)%c)^10)%c)*((a^5)%c))%c
     *
     * 假设：f(a,b)=a^b%1337
     * f(a,12345)=f(f(a,12340)*f(a,5))
     * =f(f(f(a,1234),10)*f(a,5))
     *
     * 验证通过：
     * Runtime 19 ms Beats 17.27%
     * Memory 54.4 MB Beats 5%
     *
     * @param a
     * @param b
     * @return
     */
    public static int superPow_2(int a, int[] b) {
        if (b == null || b.length == 0) return 1;
        if (b.length == 1) {
            return calc(a, b[0]);
        }
        int[] head = Arrays.copyOfRange(b, 0, b.length - 1);
        return (calc(superPow_2(a, head), 10) * calc(a, b[b.length - 1])) % base;
    }

    private static int calc(int a, int b) {
        int res = 1;
        for (int i = 0; i < b; i++) {
            res *= (a % base);
            res %= base;
        }
        return res;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/super-pow/discuss/84472/C%2B%2B-Clean-and-Short-Solution
     *
     * 数学公式：
     * (a*b)%c = ((a%c)*(b%c))%c
     * a^b = a^i * a^(b-i)
     * (a^b)%c = ((a%c)^b)%c
     * 如何降幂？每次剥离b的个位数字。这样2000位的超长b，也只需要剥离2000次。具体见下面的用例:
     *  (a^12345)%c = (a^12340*a^5)%c = (((a^12340)%c)*((a^5)%c))%c = (((((a^1234)%c)^10)%c)*((a^5)%c))%c
     *
     * if a>1337 then a%1337 ,即先把a减小到小于1337.
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 73.06% of Java online submissions for Super Pow.
     * Memory Usage: 39 MB, less than 88.93% of Java online submissions for Super Pow.
     *
     * @param a
     * @param b
     * @return
     */
    public static int superPow_1(int a, int[] b) {
        return do_recursive(a, b, b.length - 1);
    }

    /**
     * 利用公式，降幂，计算
     * @param a
     * @param b
     * @param bIdx
     * @return
     */
    public static int do_recursive(int a, int[] b, int bIdx) {
        if (bIdx < 0) return 1;
        int lastDigit = b[bIdx];
        return ((calcAndMod_2(do_recursive(a, b, bIdx - 1), 10)) * calcAndMod_2(a, lastDigit)) % base;
    }

    /**
     * 计算(a^b)%base
     * 递归实现
     * @param a
     * @param b 0<b<=10
     * @return
     */
    public static int calcAndMod_1(int a, int b) {
        if (a == 1 || b <= 0) return 1;
        if (b == 1) return a % base;
        a = a % base;
        return (((a * a) % base) * calcAndMod_1(a, b - 2)) % base;
    }

    /**
     * calcAndMod_1的循环实现
     * @param a
     * @param b
     * @return
     */
    public static int calcAndMod_2(int a, int b) {
        if (a == 1 || b <= 0) return 1;
        if (b == 1) return a % base;
        a = a % base;
        int ret = 1;
        for (int i = 0; i < b; i++) {
            ret = (a * ret) % base;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(2, new int[]{3}, 8);
        do_func(2, new int[]{1, 0}, 1024);
        do_func(1, new int[]{4, 3, 3, 8, 5, 2}, 1);
        do_func(2147483647, new int[]{2, 0, 0}, 1198);
        do_func(200, new int[]{1}, 200);
        do_func(25454, new int[]{9, 8, 7, 6, 4, 4, 4, 3, 3, 1, 0}, 177);
        do_func(78267, new int[]{1, 7, 7, 4, 3, 1, 7, 0, 1, 4, 4, 9, 2, 8, 5, 0, 0, 9, 3, 1, 2, 5, 9, 6, 0, 9, 9, 0, 9, 6, 0, 5, 3, 7, 9, 8, 8, 9, 8, 2, 5, 4, 1, 9, 3, 8, 0, 5, 9, 5, 6, 1, 1, 8, 9, 3, 7, 8, 5, 8, 5, 5, 3, 0, 4, 3, 1, 5, 4, 1, 7, 9, 6, 8, 8, 9, 8, 0, 6, 7, 8, 3, 1, 1, 1, 0, 6, 8, 1, 1, 6, 6, 9, 1, 8, 5, 6, 9, 0, 0, 1, 7, 1, 7, 7, 2, 8, 5, 4, 4, 5, 2, 9, 6, 5, 0, 8, 1, 0, 9}, 1288);
        do_func(78267, new int[]{1, 7, 7, 4, 3, 1, 7, 0, 1, 4, 4, 9, 2, 8, 5, 0, 0, 9, 3, 1, 2, 5, 9, 6, 0, 9, 9, 0, 9, 6, 0, 5, 3, 7, 9, 8, 8, 9, 8, 2, 5, 4, 1, 9, 3, 8, 0, 5, 9, 5, 6, 1, 1, 8, 9, 3, 7, 8, 5, 8, 5, 5, 3, 0, 4, 3, 1, 5, 4, 1, 7, 9, 6, 8, 8, 9, 8, 0, 6, 7, 8, 3, 1, 1, 1, 0, 6, 8, 1, 1, 6, 6, 9, 1, 8, 5, 6, 9, 0, 0, 1, 7, 1, 7, 7, 2, 8, 5, 4, 4, 5, 2, 9, 6, 5, 0, 8, 1, 0, 9, 5}, 819);
        do_func(78267, new int[]{1, 7, 7, 4, 3, 1, 7, 0, 1, 4, 4, 9, 2, 8, 5, 0, 0, 9, 3, 1, 2, 5, 9, 6, 0, 9, 9, 0, 9, 6, 0, 5, 3, 7, 9, 8, 8, 9, 8, 2, 5, 4, 1, 9, 3, 8, 0, 5, 9, 5, 6, 1, 1, 8, 9, 3, 7, 8, 5, 8, 5, 5, 3, 0, 4, 3, 1, 5, 4, 1, 7, 9, 6, 8, 8, 9, 8, 0, 6, 7, 8, 3, 1, 1, 1, 0, 6, 8, 1, 1, 6, 6, 9, 1, 8, 5, 6, 9, 0, 0, 1, 7, 1, 7, 7, 2, 8, 5, 4, 4, 5, 2, 9, 6, 5, 0, 8, 1, 0, 9, 5, 8, 7, 6, 0, 6, 1, 8, 7, 2, 9, 8, 1, 0, 7, 9, 4, 7, 6, 9, 2, 3, 1, 3, 9, 9, 6, 8, 0, 8, 9, 7, 7, 7, 3, 9, 5, 5, 7, 4, 9, 8, 3, 0, 1, 2, 1, 5, 0, 8, 4, 4, 3, 8, 9, 3, 7, 5, 3, 9, 4, 4, 9, 3, 3, 2, 4, 8, 9, 3, 3, 8, 2, 8, 1, 3, 2, 2, 8, 4, 2, 5, 0, 6, 3, 0, 9, 0, 5, 4, 1, 1, 8, 0, 4, 2, 5, 8, 2, 4, 2, 7, 5, 4, 7, 6, 9, 0, 8, 9, 6, 1, 4, 7, 7, 9, 7, 8, 1, 4, 4, 3, 6, 4, 5, 2, 6, 0, 1, 1, 5, 3, 8, 0, 9, 8, 8, 0, 0, 6, 1, 6, 9, 6, 5, 8, 7, 4, 8, 9, 9, 2, 4, 7, 7, 9, 9, 5, 2, 2, 6, 9, 7, 7, 9, 8, 5, 9, 8, 5, 5, 0, 3, 5, 8, 9, 5, 7, 3, 4, 6, 4, 6, 2, 3, 5, 2, 3, 1, 4, 5, 9, 3, 3, 6, 4, 1, 3, 3, 2, 0, 0, 4, 4, 7, 2, 3, 3, 9, 8, 7, 8, 5, 5, 0, 8, 3, 4, 1, 4, 0, 9, 5, 5, 4, 4, 9, 7, 7, 4, 1, 8, 7, 5, 2, 4, 9, 7, 9, 1, 7, 8, 9, 2, 4, 1, 1, 7, 6, 4, 3, 6, 5, 0, 2, 1, 4, 3, 9, 2, 0, 0, 2, 9, 8, 4, 5, 7, 3, 5, 8, 2, 3, 9, 5, 9, 1, 8, 8, 9, 2, 3, 7, 0, 4, 1, 1, 8, 7, 0, 2, 7, 3, 4, 6, 1, 0, 3, 8, 5, 8, 9, 8, 4, 8, 3, 5, 1, 1, 4, 2, 5, 9, 0, 5, 3, 1, 7, 4, 8, 9, 6, 7, 2, 3, 5, 5, 3, 9, 6, 9, 9, 5, 7, 3, 5, 2, 9, 9, 5, 5, 1, 0, 6, 3, 8, 0, 5, 5, 6, 5, 6, 4, 5, 1, 7, 0, 6, 3, 9, 4, 4, 9, 1, 3, 4, 7, 7, 5, 8, 2, 0, 9, 2, 7, 3, 0, 9, 0, 7, 7, 7, 4, 1, 2, 5, 1, 3, 3, 6, 4, 8, 2, 5, 9, 5, 0, 8, 2, 5, 6, 4, 8, 8, 8, 7, 3, 1, 8, 5, 0, 5, 2, 4, 8, 5, 1, 1, 0, 7, 9, 6, 5, 1, 2, 6, 6, 4, 7, 0, 9, 5, 6, 9, 3, 7, 8, 8, 8, 6, 5, 8, 3, 8, 5, 4, 5, 8, 5, 7, 5, 7, 3, 2, 8, 7, 1, 7, 1, 8, 7, 3, 3, 6, 2, 9, 3, 3, 9, 3, 1, 5, 1, 5, 5, 8, 1, 2, 7, 8, 9, 2, 5, 4, 5, 4, 2, 6, 1, 3, 6, 0, 6, 9, 6, 1, 0, 1, 4, 0, 4, 5, 5, 8, 2, 2, 6, 3, 4, 3, 4, 3, 8, 9, 7, 5, 5, 9, 1, 8, 5, 9, 9, 1, 8, 7, 2, 1, 1, 8, 1, 5, 6, 8, 5, 8, 0, 2, 4, 4, 7, 8, 9, 5, 9, 8, 0, 5, 0, 3, 5, 5, 2, 6, 8, 3, 4, 1, 4, 7, 1, 7, 2, 7, 5, 8, 8, 7, 2, 2, 3, 9, 2, 2, 7, 3, 2, 9, 0, 2, 3, 6, 9, 7, 2, 8, 0, 8, 1, 6, 5, 2, 3, 0, 2, 0, 0, 0, 9, 2, 2, 2, 3, 6, 6, 0, 9, 1, 0, 0, 3, 5, 8, 3, 2, 0, 3, 5, 1, 4, 1, 6, 8, 7, 6, 0, 9, 8, 0, 1, 0, 4, 5, 6, 0, 2, 8, 2, 5, 0, 2, 8, 5, 2, 3, 0, 2, 6, 7, 3, 0, 0, 2, 1, 9, 0, 1, 9, 9, 2, 0, 1, 6, 7, 7, 9, 9, 6, 1, 4, 8, 5, 5, 6, 7, 0, 6, 1, 7, 3, 5, 9, 3, 9, 0, 5, 9, 2, 4, 8, 6, 6, 2, 2, 3, 9, 3, 5, 7, 4, 1, 6, 9, 8, 2, 6, 9, 0, 0, 8, 5, 7, 7, 0, 6, 0, 5, 7, 4, 9, 6, 0, 7, 8, 4, 3, 9, 8, 8, 7, 4, 1, 5, 6, 0, 9, 4, 1, 9, 4, 9, 4, 1, 8, 6, 7, 8, 2, 5, 2, 3, 3, 4, 3, 3, 1, 6, 4, 1, 6, 1, 5, 7, 8, 1, 9, 7, 6, 0, 8, 0, 1, 4, 4, 0, 1, 1, 8, 3, 8, 3, 8, 3, 9, 1, 6, 0, 7, 1, 3, 3, 4, 9, 3, 5, 2, 4, 2, 0, 7, 3, 3, 8, 7, 7, 8, 8, 0, 9, 3, 1, 2, 2, 4, 3, 3, 3, 6, 1, 6, 9, 6, 2, 0, 1, 7, 5, 6, 2, 5, 3, 5, 0, 3, 2, 7, 2, 3, 0, 3, 6, 1, 7, 8, 7, 0, 4, 0, 6, 7, 6, 6, 3, 9, 8, 5, 8, 3, 3, 0, 9, 6, 7, 1, 9, 2, 1, 3, 5, 1, 6, 3, 4, 3, 4, 1, 6, 8, 4, 2, 5}, 70);
    }

    private static void do_func(int a, int[] b, int expected) {
        int ret = superPow(a, b);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
