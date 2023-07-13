package leetcode;

/**
 * 405. Convert a Number to Hexadecimal
 * Easy
 * -----------------------------
 * Given an integer num, return a string representing its hexadecimal representation. For negative integers, two’s complement method is used.
 *
 * All the letters in the answer string should be lowercase characters, and there should not be any leading zeros in the answer except for the zero itself.
 *
 * Note: You are not allowed to use any built-in library method to directly solve this problem.
 *
 * Example 1:
 * Input: num = 26
 * Output: "1a"
 *
 * Example 2:
 * Input: num = -1
 * Output: "ffffffff"
 *
 * Constraints:
 * -2^31 <= num <= 2^31 - 1
 */
public class Convert_a_Number_to_Hexadecimal_405 {
    public static String toHex(int num) {
        return toHex_2(num);
    }

    /**
     * 与toHex_1()的区别在于使用了">>>"
     *
     * @param num
     * @return
     */
    public static String toHex_2(int num) {
        if (num == 0) return "0";
        StringBuilder res = new StringBuilder();
        String[] dict = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int mask = 15;
        while (num != 0) {
            int t = num & mask;
            res.insert(0, dict[t]);
            num = num >>> 4;//使用>>>无符号右移
        }
        return res.toString();
    }

    /**
     * Thinking：
     * 1.方案1：把num转换成二进制，然后每4位计算为一个16进制的数字。
     * 2.方案2：数学公式法。
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 39.9 MB Beats 58.21%
     *
     * @param num
     * @return
     */
    public static String toHex_1(int num) {
        if (num == 0) return "0";
        StringBuilder res = new StringBuilder();
        String[] dict = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int mask = 15;
        for (int i = 0; i < 8 && num != 0; i++) {
            int t = num & mask;
            res.insert(0, dict[t]);
            num = num >> 4;
        }
        return res.toString();
    }

    public static void main(String[] args) {
        do_func(26, "1a");
        do_func(-1, "ffffffff");
        do_func(16, "10");
        do_func(Integer.MAX_VALUE, "7fffffff");
        do_func(Integer.MIN_VALUE, "80000000");
        do_func(-2, "fffffffe");
        do_func(0, "0");

    }

    private static void do_func(int n, String expected) {
        String ret = toHex(n);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
