package leetcode;

/**
 * 371. Sum of Two Integers
 * Medium
 * -----------------
 * Given two integers a and b, return the sum of the two integers without using the operators + and -.
 *
 * Example 1:
 * Input: a = 1, b = 2
 * Output: 3
 *
 * Example 2:
 * Input: a = 2, b = 3
 * Output: 5
 *
 * Constraints:
 * -1000 <= a, b <= 1000
 */
public class Sum_of_Two_Integers_371 {
    public static int getSum(int a, int b) {
        return getSum_2(a, b);
    }

    /**
     * round 2
     * review
     * 参考：getSum_2()
     *
     * 巧妙的办法。直接计算所有bit的当前位和进位，并把它们赋值给a和b，循环直到b为0为止。
     */

    /**
     * 位运算思路.
     * 重复计算，直到carry==0为止。
     *
     * 参考思路：
     * https://www.jianshu.com/p/157cc4c12181
     * https://leetcode.com/problems/sum-of-two-integers/discuss/84290/Java-simple-easy-understand-solution-with-explanation
     *
     * @param a
     * @param b
     * @return
     */
    public static int getSum_2(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;//round 2 : 计算所有bit的carry
            a = a ^ b;//round 2 : 计算所有bit的当前bit
            b = carry;
        }
        return a;
    }

    /**
     * 思路如下：
     * bit 运算，有四种情况1+1=10,1+0=1,0+1=1,0+0=0
     * int是32bit长度，需要比较32次（可能存在进位）。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum of Two Integers.
     * Memory Usage: 35.5 MB, less than 81.25% of Java online submissions for Sum of Two Integers.
     *
     * @param a
     * @param b
     * @return
     */
    public static int getSum_1(int a, int b) {
        int sum = 0;
        int carry = 0;
        for (int i = 0; i < 32; i++) {
            int a1 = (a >> i) & 1;
            int b1 = (b >> i) & 1;
            if (a1 == 1 && b1 == 1) {
                if (carry == 1) {//11
                    sum |= 1 << i;
                } else {//10

                }
                carry = 1;
            } else if (a1 == 0 && b1 == 0) {
                if (carry == 1) {//01
                    sum |= 1 << i;
                    carry = 0;
                } else {//00

                }
            } else {
                if (carry == 1) {//10
                    carry = 1;
                } else {//01
                    sum |= 1 << i;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
//        do_func(1, 2, 3);
//        do_func(2, 3, 5);
//        do_func(0, 3, 3);
//        do_func(0, 0, 0);
//        do_func(-2, 5, 3);
        do_func(-2, -5, -7);
//        do_func(1000, 1000, 2000);
//        do_func(-1000, 1000, 0);
//        do_func(-1000, -1000, -2000);
    }

    private static void do_func(int a, int b, int expected) {
        int ret = getSum(a, b);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
