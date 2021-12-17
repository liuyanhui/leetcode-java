package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 43. Multiply Strings
 * Medium
 * ----------------------
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also represented as a string.
 * Note: You must not use any built-in BigInteger library or convert the inputs to integer directly.
 *
 * Example 1:
 * Input: num1 = "2", num2 = "3"
 * Output: "6"
 *
 * Example 2:
 * Input: num1 = "123", num2 = "456"
 * Output: "56088"
 *
 * Constraints:
 * 1 <= num1.length, num2.length <= 200
 * num1 and num2 consist of digits only.
 * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
 */
public class Multiply_Strings_43 {
    public static String multiply(String num1, String num2) {
        return multiply_1(num1,num2);
    }

    /**
     * 奇妙的思路
     * 参考思路：
     * https://leetcode.com/problems/multiply-strings/discuss/17605/Easiest-JAVA-Solution-with-Graph-Explanation
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply_2(String num1, String num2) {
        return "";
    }

    /**
     * 1.反转num1和num2
     * 2.计算num1整体和num2中的每个数字的乘积，然后乘积相加
     * 3.反转结果
     *
     * 验证通过：
     * Runtime: 32 ms, faster than 11.69%
     * Memory Usage: 51.7 MB, less than 5.03% .
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply_1(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        String n1 = new StringBuilder(num1).reverse().toString();
        String n2 = new StringBuilder(num2).reverse().toString();
        List<Integer> retList = new ArrayList<>();
        for (int i = 0; i < n1.length(); i++) {
            //字符串和个位数相乘
            List<Integer> list = multiplyStrAndNum(n2, (n1.charAt(i) - '0'));
            for (int j = 0; j < i; j++) {
                list.add(0, 0);
            }
            //相加合并两个数字列表
            retList = addTwoList(retList, list);
        }

        StringBuilder ret = new StringBuilder();
        for (int i = retList.size() - 1; i >= 0; i--) {
            ret.append(retList.get(i));
        }
        return ret.toString();
    }

    private static List<Integer> multiplyStrAndNum(String s1, int n) {
        List<Integer> list = new ArrayList<>();
        int carry = 0;
        for (int i = 0; i < s1.length(); i++) {
            int v = (s1.charAt(i) - '0') * n + carry;
            list.add(v % 10);
            carry = v / 10;
        }
        if (carry > 0) list.add(carry);
        return list;
    }

    private static List<Integer> addTwoList(List<Integer> list1, List<Integer> list2) {
        List<Integer> ret = new ArrayList<>();
        int carry = 0;
        int i = 0;
        while (i < list1.size() || i < list2.size()) {
            int v = carry;
            if (i < list1.size()) {
                v += list1.get(i);
            }
            if (i < list2.size()) {
                v += list2.get(i);
            }
            ret.add(v % 10);
            carry = v / 10;
            i++;
        }
        if (carry > 0) ret.add(carry);
        return ret;
    }

    public static void main(String[] args) {
        do_func("2", "3", "6");
        do_func("123", "456", "56088");
        do_func("123", "1", "123");
        do_func("1", "123", "123");
        do_func("123", "0", "0");
    }

    private static void do_func(String num1, String num2, String expected) {
        String ret = multiply(num1, num2);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
