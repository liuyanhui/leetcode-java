package leetcode;

/**
 * 191. Number of 1 Bits
 * Easy
 * --------------------------
 * Write a function that takes an unsigned integer and returns the number of '1' bits it has (also known as the Hamming weight).
 *
 * Note:
 * Note that in some languages, such as Java, there is no unsigned integer type. In this case, the input will be given as a signed integer type. It should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
 * In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 3, the input represents the signed integer. -3.
 *
 * Example 1:
 * Input: n = 00000000000000000000000000001011
 * Output: 3
 * Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.
 *
 * Example 2:
 * Input: n = 00000000000000000000000010000000
 * Output: 1
 * Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.
 *
 * Example 3:
 * Input: n = 11111111111111111111111111111101
 * Output: 31
 * Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.
 *
 * Constraints:
 * The input must be a binary string of length 32.
 *
 * Follow up: If this function is called many times, how would you optimize it?
 */
public class Number_of_1_Bits_191 {
    public static int hammingWeight(int n) {
        return hammingWeight_1(n);
    }

    /**
     * Bit Manipulation
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 85.79% of Java online submissions for Number of 1 Bits.
     * Memory Usage: 41.3 MB, less than 46.92% of Java online submissions for Number of 1 Bits.
     *
     * @param n
     * @return
     */
    public static int hammingWeight_1(int n) {
        int cnt = 0;
        for (int i = 0; i < 32; i++) {
            if ((n >> i & 1) == 1) cnt++;
        }
        return cnt;
    }

    public static void main(String[] args) {
        do_func(Integer.parseInt("00000000000000000000000000001011", 2), 3);
        do_func(Integer.parseInt("00000000000000000000000010000000", 2), 1);
        do_func(Integer.parseUnsignedInt("11111111111111111111111111111101", 2), 31);//
        do_func(-3, 31);// 11111111111111111111111111111101
    }

    private static void do_func(int n, int expected) {
        int ret = hammingWeight(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
