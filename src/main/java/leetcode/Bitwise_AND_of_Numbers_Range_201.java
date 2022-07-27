package leetcode;

/**
 * 201. Bitwise AND of Numbers Range
 * Medium
 * ---------------------
 * Given two integers left and right that represent the range [left, right], return the bitwise AND of all numbers in this range, inclusive.
 *
 * Example 1:
 * Input: left = 5, right = 7
 * Output: 4
 *
 * Example 2:
 * Input: left = 0, right = 0
 * Output: 0
 *
 * Example 3:
 * Input: left = 1, right = 2147483647
 * Output: 0
 *
 * Constraints:
 * 0 <= left <= right <= 2^31 - 1
 */
public class Bitwise_AND_of_Numbers_Range_201 {
    public static int rangeBitwiseAnd(int left, int right) {
        return rangeBitwiseAnd_2(left, right);
    }

    /**
     * rangeBitwiseAnd_2()的另一个版本
     *
     * 验证通过：
     * Runtime: 8 ms, faster than 78.76% of Java online submissions for Bitwise AND of Numbers Range.
     * Memory Usage: 44.4 MB, less than 32.97% of Java online submissions for Bitwise AND of Numbers Range.
     *
     * @param left
     * @param right
     * @return
     */
    public static int rangeBitwiseAnd_3(int left, int right) {
        int c = 0;
        while (left != right) {
            left = left >> 1;
            right = right >> 1;
            c++;
        }
        return left << c;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/bitwise-and-of-numbers-range/discuss/56729/Bit-operation-solution(JAVA)
     *
     * The idea is very simple:
     * 1.last bit of (odd number & even number) is 0.
     * 2.when m != n, There is at least an odd number and an even number, so the last bit position result is 0.
     * 3.Move m and n right a position.
     * 4.Keep doing step 1,2,3 until m equal to n, use a factor to record the iteration time.
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 100.00% of Java online submissions for Bitwise AND of Numbers Range.
     * Memory Usage: 38.4 MB, less than 32.72% of Java online submissions for Bitwise AND of Numbers Range.
     *
     * @param left
     * @param right
     * @return
     */
    public static int rangeBitwiseAnd_2(int left, int right) {
        int c = 1;
        while (left != right) {
            left = left >> 1;
            right = right >> 1;
            c = c << 1;
        }
        return c * left;
    }

    /**
     * 必然会超时，因为没有利用&的特点
     * @param left
     * @param right
     * @return
     */
    public static int rangeBitwiseAnd_1(int left, int right) {
        int ret = left++;
        while (left <= right) {
            ret &= left++;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(5, 7, 4);
        do_func(0, 0, 0);
        do_func(1, 2147483647, 0);
    }

    private static void do_func(int left, int right, int expected) {
        int ret = rangeBitwiseAnd(left, right);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
