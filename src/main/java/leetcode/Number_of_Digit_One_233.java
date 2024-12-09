package leetcode;

/**
 * 233. Number of Digit One
 * Hard
 * -------------------------
 * Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.
 * <p>
 * Example 1:
 * Input: n = 13
 * Output: 6
 * <p>
 * Example 2:
 * Input: n = 0
 * Output: 0
 * <p>
 * Constraints:
 * 0 <= n <= 10^9
 */
public class Number_of_Digit_One_233 {
    public static int countDigitOne(int n) {
        return countDigitOne_1(n);
    }

    /**
     * review
     * round 3
     * Score[1] Lower is harder
     *
     * 参考 countDigitOne_1()
     *
     */

    /**
     * review
     * 参考文档：
     * https://leetcode.com/problems/number-of-digit-one/discuss/64381/4%2B-lines-O(log-n)-C%2B%2BJavaPython
     * Basic idea: count number of combination of 1 at each digit in two cases: prefix appears or not
     * Eg.3101592:
     * 1) one at hundreds:      1 (< 5): [1~3101]1[0~99]. So # = 3101 * 100 + 100 (Safe since 31011XX never be greater than 31015XX)
     * 2) one at thousands:     1 (= 1): [1~310]1[0~592]. So # = 310 * 1000 + (592 + 1) (Unsafe if prefix is 0, it must be <= 1592)
     * 3) one at ten thousands: 1 (> 0): [1~30]1[0~9999]. So # = 30 * 10000 (If prefix is 0, no 1 digit number exist)
     * <p>
     * 思路：
     * 1.按位(个、十、百、千等)总结数字1出现的规律。【是结构化思维的一种】
     * 2.根据该位的数字有分为三种情况，=1，>1和<1。并整理出公式。
     * 3.round 3
     * 3.1. 先计算???1000场景下当前位为1的情况。（即，高位变化，当前位保持为1，低位不参与的情况）
     * 3.2. 再计算aaa1???的情况中当前位为1的情况。（即，高位不参与，当前位保持为1，低位变化的情况）
     * 3.3. 需要考虑当前为 =1，>1和=0 三种情况
     * 3.4. 结果相加
     * <p>
     * 思路整理：
     * 假设n=3101592
     * 个位：[310159]1    310159*1+1      该位数字>1
     * 十位：[31015]1[2]  31015*10+10     该位数字>1
     * 百位：[3101]1[92]  3101*100+100    该位数字>1
     * 千位：[310]1[592]  310*1000+592+1  该位数字=1
     * 万位：[31]1[1592]  31*10000+0      该位数字<1
     * <p>
     * 公式：
     * k=1,10,100...
     * m=k*10
     * c=n/k%10
     * if c>1 then cnt=n/m*k+k
     * if c=1 then cnt=n/m*k+n%k+1
     * if c<1 then cnt=n/m*k
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Number of Digit One.
     * Memory Usage: 39.1 MB, less than 93.32% of Java online submissions for Number of Digit One.
     *
     * @param n
     * @return
     */
    public static int countDigitOne_1(int n) {
        int cnt = 0;
        for (int k = 1; k <= n; k *= 10) {
            int m = k * 10;
            int c = n / k % 10;
            if (c < 1) {
                cnt += n / m * k;
            } else if (c == 1) {
                cnt += n / m * k + n % k + 1;
            } else if (c > 1) {
                cnt += n / m * k + k;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        do_func(13, 6);
        do_func(0, 0);
        do_func(1234, 689);
    }

    private static void do_func(int n, int expected) {
        int ret = countDigitOne(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
