package leetcode;

/**
 * 319. Bulb Switcher
 * Medium
 * ----------------------
 * There are n bulbs that are initially off. You first turn on all the bulbs, then you turn off every second bulb.
 * On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb.
 * Return the number of bulbs that are on after n rounds.
 *
 * Example 1:
 * Input: n = 3
 * Output: 1
 * Explanation: At first, the three bulbs are [off, off, off].
 * After the first round, the three bulbs are [on, on, on].
 * After the second round, the three bulbs are [on, off, on].
 * After the third round, the three bulbs are [on, off, off].
 * So you should return 1 because there is only one bulb is on.
 *
 * Example 2:
 * Input: n = 0
 * Output: 0
 *
 * Example 3:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 * 0 <= n <= 10^9
 */
public class Bulb_Switcher_319 {
    /**
     * 这题做的酣畅淋漓，虽然耗时稍长，但是能够体现出整个思考过程。从bulbSwitch_1到bulbSwitch_3中的注释就是思考过程。
     * @param n
     * @return
     */
    public static int bulbSwitch(int n) {
        return bulbSwitch_4(n);
    }
    /**
     * review round 2
     * 思考：
     * 1.暴力法。从1~n依次计算，最后再统计结果。时间复杂度O(2N)，空间复杂度O(N)
     * 2.寻找规律。
     * 每个数字能被整除时才会toggle->被操作奇数次时，灯是亮的->质数一定是灭的->每个数的能被整数的数的集合是奇数时，灯是亮的，如：9,25,49
     *
     * bulbSwitch_4()的思路
     * 1.每个数的能被整数的数的集合是奇数个数时，灯是亮的。
     * 2.每个数被整除的数都是成对的，如：5:{[1,5]}，4:{[1,4],[2,2]}，8:{[1,8],[2,4]}，9:{[1,9],[3,3]}。
     * 3.可以得出以下规律，只有某个数的平方才能满足上面的“奇数个数”条件。
     *
     */

    /**
     * 参考思路：https://leetcode.com/problems/bulb-switcher/discuss/77104/Math-solution..
     *
     * @param n
     * @return
     */
    public static int bulbSwitch_4(int n) {
        return (int) Math.sqrt(n);
    }

    /**
     * bulbSwitch_2()的改进版。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Bulb Switcher.
     * Memory Usage: 35.6 MB, less than 61.17% of Java online submissions for Bulb Switcher.
     *
     * @param n
     * @return
     */
    public static int bulbSwitch_3(int n) {
        int ret = 0;
        for (int i = 1; i * i <= n; i++) {
            ret++;
        }
        return ret;
    }

    /**
     * 采用bulbSwitch_1()中提到的思路3，数学公式法。思路如下：
     * 1.计算出每个i被命中的次数，命中偶数表示灭，奇数表示亮。
     * 2.每个i会被自己的约数命中，约数是成对出现的。如：12会被1/2/3/4/6/12命中。
     * 3.进一步，偶数的约数个数是偶数，除了4/16这些能被开方的数。这些灯灭。
     * 4.进一步，质数的约数个数是偶数。所以质数灯灭。
     * 5.进一步，合数的约数个数时偶数，除了4/16这些能被开方的数。这些灯灭。
     * 6.结论，只有1和能被开方的灯亮。因为约数是成对出现的，除了能被开方的数。
     *
     * n=1000000000的用例执行结果正确，但是Time Limit Exceeded。
     *
     * @param n
     * @return
     */
    public static int bulbSwitch_2(int n) {
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            int t = (int) Math.sqrt(i);
            if (t * t == i) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * 思路如下：
     * 从1~n遍历，用数组保存bulb状态
     * 每次遍历公式：while(i*t<=n){改变bulb状态;t++;}。0<i<=n,t从1开始每次加*
     *
     * 逻辑正确，但是n=1000000000的用例报错了，java.lang.OutOfMemoryError: Java heap space。
     *
     * 需要优化bulb数组，降低空间复杂度。
     * 有以下几种思路：
     * 1.可以使用bitmap代替数组，空间复杂度降低32倍。（不知能否通过测试）
     * 2.只保存亮bulb或者只保存灭bulb。（貌似不可行，仍然需要大量存储空间）
     * 3.直接使用数学公式，计算出每个i被命中的次数，命中偶数表示灭，奇数表示亮。每个i会被自己的约数命中。如：12会被1/2/3/4/6/12命中。(可行)
     *
     * @param n
     * @return
     */
    public static int bulbSwitch_1(int n) {
        int[] bulb = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int t = 1;
            while (i * t <= n) {
                bulb[i * t] ^= 1;
                t++;
            }
        }
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            ret += bulb[i];
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, 1);
        do_func(0, 0);
        do_func(1, 1);
        do_func(5, 2);
        do_func(10, 3);
        do_func(9898899, 3146);
        do_func(1000000000, 31622);
    }

    private static void do_func(int n, int expected) {
        int ret = bulbSwitch(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
