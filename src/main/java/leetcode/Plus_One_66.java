package leetcode;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/plus-one/
 * 66. Plus One
 * Easy
 * --------------
 * Given a non-empty array of decimal digits representing a non-negative integer, increment one to the integer.
 * The digits are stored such that the most significant digit is at the head of the list, and each element in the array contains a single digit.
 * You may assume the integer does not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: digits = [1,2,3]
 * Output: [1,2,4]
 * Explanation: The array represents the integer 123.
 *
 * Example 2:
 * Input: digits = [4,3,2,1]
 * Output: [4,3,2,2]
 * Explanation: The array represents the integer 4321.
 *
 * Example 3:
 * Input: digits = [0]
 * Output: [1]
 *
 * Constraints:
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 */
public class Plus_One_66 {
    public static int[] plusOne(int[] digits) {
        return plusOne_3(digits);

    }

    /**
     * round 2
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Plus One.
     * Memory Usage: 39.7 MB, less than 6.14% of Java online submissions for Plus One.
     *
     * @param digits
     * @return
     */
    public static int[] plusOne_3(int[] digits) {
        int[] ret = null;
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int t = digits[i] + carry;
            digits[i] = t % 10;
            carry = t / 10;
        }
        if (carry == 1) {
            ret = new int[digits.length + 1];
            ret[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                ret[i + 1] = digits[i];
            }
        } else {
            ret = digits;
        }
        return ret;
    }

    /**
     * 巧妙的方法，参考思路：
     * https://leetcode.com/problems/plus-one/discuss/24082/My-Simple-Java-Solution
     *
     * 遇到第一个非9的数字，该数字+1，返回原数组。
     * 遍历数组。如果数字是9，改为0，计算下一个数字；如果数字小于9，当前数字+1，返回原数组。
     *
     * @param digits
     * @return
     */
    public static int[] plusOne_2(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;//出现非9的数字，+1操作后直接返回原数组
            }
            digits[i] = 0;
        }

        //代码能走到这里，说明digits=[9]or[99]or[99..9]
        int[] newNumber = new int[n + 1];
        newNumber[0] = 1;
        return newNumber;
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Plus One.
     * Memory Usage: 37.8 MB, less than 13.52% of Java online submissions for Plus One.
     * @param digits
     * @return
     */
    public static int[] plusOne_1(int[] digits) {
        int[] ret = new int[digits.length];
        int plus = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int tmp = digits[i] + plus;
            ret[i] = tmp % 10;
            plus = tmp / 10;
        }
        if (plus == 1) {
            int[] ret2 = new int[digits.length + 1];
            ret2[0] = 1;
            Arrays.stream(ret).forEach(i -> ret2[i + 1] = ret[i]);
            return ret2;
        } else {
            return ret;
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, new int[]{1, 2, 4});
        do_func(new int[]{4, 3, 2, 1}, new int[]{4, 3, 2, 2});
        do_func(new int[]{0}, new int[]{1});
        do_func(new int[]{9}, new int[]{1, 0});
        do_func(new int[]{9, 9, 9}, new int[]{1, 0, 0, 0});
        do_func(new int[]{8, 9, 9}, new int[]{9, 0, 0});
    }

    private static void do_func(int[] digits, int[] expected) {
        int[] ret = plusOne(digits);
        ArrayUtils.printIntArray(ret);
        System.out.println("");
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
