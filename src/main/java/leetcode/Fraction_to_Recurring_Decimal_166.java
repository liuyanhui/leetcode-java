package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 166. Fraction to Recurring Decimal
 * Medium
 * -----------------
 * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
 * If the fractional part is repeating, enclose the repeating part in parentheses.
 * If multiple answers are possible, return any of them.
 * It is guaranteed that the length of the answer string is less than 104 for all the given inputs.
 *
 * Example 1:
 * Input: numerator = 1, denominator = 2
 * Output: "0.5"
 *
 * Example 2:
 * Input: numerator = 2, denominator = 1
 * Output: "2"
 *
 * Example 3:
 * Input: numerator = 2, denominator = 3
 * Output: "0.(6)"
 *
 * Example 4:
 * Input: numerator = 4, denominator = 333
 * Output: "0.(012)"
 *
 * Example 5:
 * Input: numerator = 1, denominator = 5
 * Output: "0.2"
 *
 * Constraints:
 * -2^31 <= numerator, denominator <= 2^31 - 1
 * denominator != 0
 */
public class Fraction_to_Recurring_Decimal_166 {
    /**
     * 待验证
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal(int numerator, int denominator) {
        return fractionToDecimal_2(numerator, denominator);
    }

    /**
     * 改进fractionToDecimal_1()，把int改成long
     * 验证通过，性能一般：
     * Runtime: 8 ms, faster than 14.70% of Java online submissions for Fraction to Recurring Decimal.
     * Memory Usage: 38.7 MB, less than 8.30% of Java online submissions for Fraction to Recurring Decimal.
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal_2(int numerator, int denominator) {
        if (numerator == 0) return "0";
        String ret = "";
        // 用于记录numerator是否出现过,如果出现过表示循环小数存在，并记录出现的起始位置index
        // key:numerator;value:对应的数字的index(从小数点后开始)
        Map<Long, Integer> appeared = new HashMap<>();
        String signal = null;
        //tip：下面这行代码很精妙
        signal = ((numerator > 0) ^ (denominator > 0)) ? "-" : "";
        //下面的代码无法解决int最大值的情况，相乘后会溢出
//        if (numerator * denominator >= 0) {
//            signal = "";
//        } else {
//            signal = "-";
//        }
        long n = Math.abs((long) numerator);
        long d = Math.abs((long) denominator);
        ret += String.valueOf(n / d);
        int index = ret.length();//修改ret的地方，必须增加index
        n = n % d;
        if (n != 0L) {
            ret += ".";
            index++;
            while (n != 0) {
                if (appeared.containsKey(n)) {
                    //判断循环
                    //numerator重复出现时，表示小数循环开始
                    ret = ret.substring(0, appeared.get(n)) + "(" + ret.substring(appeared.get(n)) + ")";
                    break;
                }
                ret += String.valueOf((n * 10) / d);
                appeared.put(n, index++);
                n = (n * 10) % d;
            }
        }
        return signal + ret;
    }

    /**
     * 注意正负
     * 整数部分：a/b是整数部分,a=a%b是小数部分的numerator
     * 小数部分：(a*10)/b 是当前小数位的数字，a=(a*10)%b 就是计算下一位小数的numerator
     * 循环判断：只要a在小数部分出现过，就表示循环出现。用map记录循环出现的位置
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal_1(int numerator, int denominator) {
        String ret = "";
        // 用于记录numerator是否出现过,如果出现过表示循环小数存在，并记录出现的起始位置index
        // key:numerator;value:对应的数字的index(从小数点后开始)
        Map<Integer, Integer> appeared = new HashMap<>();
        String signal = null;
        if (numerator * denominator >= 0) {
            signal = "";
        } else {
            signal = "-";
        }
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
        ret += String.valueOf(numerator / denominator);
        int index = ret.length();//修改ret的地方，必须增加index
        numerator = numerator % denominator;
        if (numerator != 0) {
            ret += ".";
            index++;
            while (numerator != 0) {
                if (appeared.containsKey(numerator)) {
                    //判断循环
                    //numerator重复出现时，表示小数循环开始
                    ret = ret.substring(0, appeared.get(numerator)) + "(" + ret.substring(appeared.get(numerator)) + ")";
                    break;
                }
                ret += String.valueOf((numerator * 10) / denominator);
                appeared.put(numerator, index++);
                numerator = (numerator * 10) % denominator;
            }
        }
        return signal + ret;
    }

    public static void main(String[] args) {
//        do_func(0, 2, "0");
//        do_func(1, 2, "0.5");
//        do_func(-1, 2, "-0.5");
//        do_func(-1, -2, "0.5");
//        do_func(1, -2, "-0.5");
//        do_func(2, 2, "1");
//        do_func(2, 3, "0.(6)");
//        do_func(4, 333, "0.(012)");
//        do_func(1, 5, "0.2");
//        do_func(10, 3, "3.(3)");
//        do_func(1000, 3, "333.(3)");
//        do_func(200, 3, "66.(6)");
//        do_func(1, 9999999, "0.(0000001)");
//        do_func(2147483647, 2147483647, "1");
//        do_func(-2147483648, -2147483648, "1");

        //TODO 计算过程中超过了int的最大长度，导致错误。是否可以转换为long?
        //TODO 用long进行计算后，这条用例通过了
        do_func(-1, -2147483648, "0.0000000004656612873077392578125");
        //todo 下面的用例会无限循环
        do_func(-1, -2147483600, "0.0000000004656612873077392578125");

//        do_func(1, 2147483647, "0.0000000004656612875245797");

//        System.out.println(1/2147483647);
//        System.out.println(1%2147483647);
    }

    private static void do_func(int numerator, int denominator, String expected) {
        String ret = fractionToDecimal(numerator, denominator);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(Integer.MAX_VALUE);
    }
}
