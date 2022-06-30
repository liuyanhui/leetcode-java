package leetcode;

/**
 * tip:套路题
 * 172. Factorial Trailing Zeroes
 * Easy
 * ------------------
 * Given an integer n, return the number of trailing zeroes in n!.
 *
 * Follow up: Could you write a solution that works in logarithmic time complexity?
 *
 * Example 1:
 * Input: n = 3
 * Output: 0
 * Explanation: 3! = 6, no trailing zero.
 *
 * Example 2:
 * Input: n = 5
 * Output: 1
 * Explanation: 5! = 120, one trailing zero.
 *
 * Example 3:
 * Input: n = 0
 * Output: 0
 *
 * Constraints:
 * 0 <= n <= 10000
 */
public class Factorial_Trailing_Zeroes_172 {
    public static int trailingZeroes(int n) {
        return trailingZeroes_3(n);
    }

    /**
     * review round 2
     *
     * 跟乘数中5的数量有关，乘数分解后结果中含有?个5，结果中就多?个0。
     * 如：
     * 5=1*4->1个0
     * 10=2*5->1个0
     * 25=5*5->2个0
     * 50=2*5*5->2个0
     *
     * 有两种方案：递归法和迭代法
     * 递归法参考资料：
     * https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52371/My-one-line-solutions-in-3-languages
     * 迭代法参考资料：
     * https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52373/Simple-CC%2B%2B-Solution-(with-detailed-explaination)
     *
     * @param n
     * @return
     */
    public static int trailingZeroes_3(int n) {
        //迭代法
        int result = 0;
        for (int i = 5; n / i > 0; i *= 5) {
            result += (n / i);
        }
        return result;
        //递归法
//        return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52373/Simple-CC%2B%2B-Solution-(with-detailed-explaination)
     * https://leetcode.com/problems/factorial-trailing-zeroes/discuss/52371/My-one-line-solutions-in-3-languages
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 15.82% of Java online submissions for Factorial Trailing Zeroes.
     * Memory Usage: 35.4 MB, less than 97.34% of Java online submissions for Factorial Trailing Zeroes.
     * @param n
     * @return
     */
    public static int trailingZeroes_2(int n) {
        int ret = 0;
        for (int i = 0; i <= n; i += 5) {
            int t = i;
            while (t > 0 && t % 5 == 0) {
                ret++;
                t /= 5;
            }
        }
        return ret;
    }

    /**
     * 思路错误，无法当i=25时，上一次乘积结果保留一位是时本次乘积为6*25=150。然而如果上一次乘积保留2位时，本次乘积为96*25=2400。
     * 所以尾数为0的个数，只跟因子5的个数有关。
     *
     * 只需要考虑个位数的乘积,所以只保留乘数的非零个位数即可。
     * 1.遍历n
     * 2.f(n) = f(n-1) * n,计算f(n)的从个位开始连续的是0的个数，f(n)修改为f(n)的最低位非0数字。
     * 3.所有f(n)的地位是0的个数总和即为所求
     *
     * Time Complexity:O(n)
     * @param n
     * @return
     */
    public static int trailingZeroes_1(int n) {
        int multi = 1;
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            multi = multi * i;
            System.out.println(i + ":" + multi);
            //计算乘积的尾部为0的个数
            while (multi % 10 == 0) {
                ret++;
                multi /= 10;
                System.out.println("ret=" + ret);
            }
            //保留乘积的个位
            multi %= 10;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, 0);
        do_func(5, 1);
        do_func(0, 0);
        do_func(10, 2);
        do_func(11, 2);
        do_func(25, 6);
        do_func(125, 31);
        do_func(104, 24);
        do_func(600, 148);
        do_func(625, 156);
        do_func(10000, 2499);
    }

    private static void do_func(int n, int expected) {
        int ret = trailingZeroes(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
