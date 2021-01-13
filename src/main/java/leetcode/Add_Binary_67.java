package leetcode;

/**
 * https://leetcode.com/problems/add-binary/
 * 67. Add Binary
 * Easy
 * ---------------
 * Given two binary strings a and b, return their sum as a binary string.
 *
 *  Example 1:
 * Input: a = "11", b = "1"
 * Output: "100"
 *
 * Example 2:
 * Input: a = "1010", b = "1011"
 * Output: "10101"
 *
 * Constraints:
 * 1 <= a.length, b.length <= 104
 * a and b consist only of '0' or '1' characters.
 * Each string does not contain leading zeros except for the zero itself.
 */
public class Add_Binary_67 {
    public static String addBinary(String a, String b) {
        return addBinary_2(a, b);
    }

    /**
     * 精简版代码。
     * 只进行一次循环，a和b中的一个已经计算完成
     * 验证通过：
     * Runtime: 2 ms, faster than 73.66% of Java online submissions for Add Binary.
     * Memory Usage: 37.9 MB, less than 60.03% of Java online submissions for Add Binary.
     * @param a
     * @param b
     * @return
     */
    public static String addBinary_2(String a, String b) {
        StringBuilder ret = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int carry = 0;
        //通过一次循环的方法，超出的元素取0，参与计算
        while (i >= 0 || j >= 0) {
            int s1, s2;
            //如果a中的所有元素已经参与计算，s1=0
            if (i < 0) s1 = 0;
            else s1 = a.charAt(i) == '1' ? 1 : 0;
            //如果b中的所有元素已经参与计算，s2=0
            if (j < 0) s2 = 0;
            else s2 = b.charAt(j) == '1' ? 1 : 0;

            int sum = s1 + s2 + carry;
            //通过取模计算出低位数字
            ret.append(sum % 2);
            //通过商计算出需要进位的数字
            carry = sum / 2;

            i--;
            j--;
        }
        //处理最后的进位数字
        if (carry == 1) ret.append("1");
        return ret.reverse().toString();
    }

    /**
     * 直观计算思路。通过把数字转换为整数进行处理.
     * a[i]+b[j]+overflow只能有四种结果：0、1、2、3，0、1表示不需要进位，2、3表示需要进位
     * 验证通过：
     * Runtime: 2 ms, faster than 73.66% of Java online submissions for Add Binary.
     * Memory Usage: 39.3 MB, less than 21.46% of Java online submissions for Add Binary.
     * @param a
     * @param b
     * @return
     */
    public static String addBinary_1(String a, String b) {
        StringBuilder ret = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1;
        int overflow = 0;
        while (i >= 0 && j >= 0) {
            int s1 = a.charAt(i) == '1' ? 1 : 0;
            int s2 = b.charAt(j) == '1' ? 1 : 0;
            int tmp = s1 + s2 + overflow;
            overflow = doOverflowAndRet(tmp, ret);
            i--;
            j--;
        }
        while (i >= 0) {
            int tmp = (a.charAt(i) == '1' ? 1 : 0) + overflow;
            overflow = doOverflowAndRet(tmp, ret);
            i--;
        }
        while (j >= 0) {
            int tmp = (b.charAt(j) == '1' ? 1 : 0) + overflow;
            overflow = doOverflowAndRet(tmp, ret);
            j--;
        }
        if (overflow == 1) {
            ret.append("1");
        }
        StringBuilder retFinal = ret.reverse();
        return retFinal.toString();
    }

    private static int doOverflowAndRet(int n, StringBuilder sb) {
        int overflow = 0;
        if (n <= 1) {
            sb.append(String.valueOf(n));
        } else if (n == 2) {
            sb.append("0");
            overflow = 1;
        } else {
            sb.append("1");
            overflow = 1;
        }
        return overflow;

    }

    public static void main(String[] args) {
        do_func("11", "1", "100");
        do_func("1010", "1011", "10101");
        do_func("1111", "1", "10000");
        do_func("1111", "111", "10110");
    }

    private static void do_func(String a, String b, String expected) {
        String ret = addBinary(a, b);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
