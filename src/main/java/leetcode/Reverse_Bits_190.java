package leetcode;

/**
 * 190. Reverse Bits
 * Easy
 * ----------------------
 * Reverse bits of a given 32 bits unsigned integer.
 * <p>
 * Note:
 * Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and output will be given as a signed integer type. They should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
 * In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2 above, the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 * <p>
 * Follow up:
 * If this function is called many times, how would you optimize it?
 * <p>
 * Example 1:
 * Input: n = 00000010100101000001111010011100
 * Output:    964176192 (00111001011110000010100101000000)
 * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.
 * <p>
 * Example 2:
 * Input: n = 11111111111111111111111111111101
 * Output:   3221225471 (10111111111111111111111111111111)
 * Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293, so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 * <p>
 * Constraints:
 * The input must be a binary string of length 32
 */
public class Reverse_Bits_190 {
    // you need treat n as an unsigned value
    public static int reverseBits(int n) {
        return reverseBits_r3_1(n);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 按bit依次reverse。
     * Time Complexity:O(32)
     * <p>
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 41.47 MB Beats 86.64%
     *
     * @param n
     * @return
     */
    public static int reverseBits_r3_1(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res = (res << 1) | ((n >> i) & 1);
        }
        return res;
    }

    /**
     * round 2
     * <p>
     * 方案1：位运算 bit manipulate
     * 按位提取相应的0/1值，然后按相反的顺序合并到结果中。
     * 时间复杂度：O(32)，空间复杂度：O(1)
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 98.63% of Java online submissions for Reverse Bits.
     * Memory Usage: 42.3 MB, less than 67.45% of Java online submissions for Reverse Bits.
     *
     * @param n
     * @return
     */
    public static int reverseBits_2(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            int t = (n >> i) & 1;
            ret |= t << (31 - i);
        }
        return ret;
    }

    /**
     * Bit Manipulation
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 98.03% of Java online submissions for Reverse Bits.
     * Memory Usage: 38.4 MB, less than 90.16% of Java online submissions for Reverse Bits.
     *
     * @param n
     * @return
     */
    public static int reverseBits_1(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            int t = (n >> i) & 1;
            ret = ret << 1;
            ret |= t;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(43261596, 964176192);// 00000010100101000001111010011100
//        do_func(4294967293,3221225471);//11111111111111111111111111111101
//        do_func(12345678901234567890,964176192);
        do_func(3, -1073741824);// 00000010100101000001111010011100
    }

    private static void do_func(int n, int expected) {
        int ret = reverseBits(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
