package leetcode;

/**
 * 357. Count Numbers with Unique Digits
 * Medium
 * --------------------------
 * Given an integer n, return the count of all numbers with unique digits, x, where 0 <= x < 10^n.
 *
 * Example 1:
 * Input: n = 2
 * Output: 91
 * Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100, excluding 11,22,33,44,55,66,77,88,99
 *
 * Example 2:
 * Input: n = 0
 * Output: 1
 *
 * Constraints:
 * 0 <= n <= 8
 */
public class Count_Numbers_with_Unique_Digits_357 {

    public static int countNumbersWithUniqueDigits(int n) {
        return countNumbersWithUniqueDigits_2(n);
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.naive solution。
     * 2.如何判断一个数不包含重复数字？位图法？
     * 3.总结规律？按1位数，2位数，3位数..n位数分别计算。采用排列/组合中的组合进行计算。
     * 1位数[0~9]：10
     * 2位数[10~99]：9*9
     * 3位数[100~999]:9*9*8
     * 4位数[1000~9999]:9*9*8*7
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 39.7 MB Beats 34.56%
     *
     * @param n
     * @return
     */
    public static int countNumbersWithUniqueDigits_2(int n) {
        if (n < 0) return 0;
        if (n == 0) return 1;
        if (n == 1) return 10;
        int res = 10;
        int t = 9;
        for (int i = 1; i < n; i++) {
            t *= (9 - i + 1);
            res += t;
        }
        return res;
    }

    /**
     * 公式为：
     * f[0]=1
     * f[1]=9
     * f[2]=9*9
     * f[3]=9*9*8
     * f[i]=9*9*8*...(10-i-1)
     * 即f[i]=f[i-1]*(10-i-1)
     * 最后把f[0]~f[n]累加就是结果
     *
     * 解释如下：
     * 使用数学中的排列组合中的组合法，因为数字不能重复出现，最高位之中从1~9选择（共9种取法），非最高位可以从0~9选择（共10种取法，但是会依次递减）。
     * 使用组合公式即可得出不重复出现的数字次数。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Count Numbers with Unique Digits.
     * Memory Usage: 35.4 MB, less than 88.25% of Java online submissions for Count Numbers with Unique Digits.
     *
     * @param n
     * @return
     */
    public static int countNumbersWithUniqueDigits_1(int n) {
        if (n == 0) return 1;
        int[] count = new int[n + 1];
        count[0] = 1;
        count[1] = 9;
        for (int i = 2; i <= n; i++) {
            count[i] = count[i - 1] * (10 - i + 1);
        }
        int ret = 0;
        for (int i = 0; i < count.length; i++) {
            ret += count[i];
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(2, 91);
        do_func(0, 1);
        do_func(1, 10);
        do_func(3, 739);
        do_func(8, 2345851);
    }

    private static void do_func(int n, int expected) {
        int ret = countNumbersWithUniqueDigits(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
