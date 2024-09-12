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
 * <p>
 * Example 1:
 * Input: numerator = 1, denominator = 2
 * Output: "0.5"
 * <p>
 * Example 2:
 * Input: numerator = 2, denominator = 1
 * Output: "2"
 * <p>
 * Example 3:
 * Input: numerator = 2, denominator = 3
 * Output: "0.(6)"
 * <p>
 * Example 4:
 * Input: numerator = 4, denominator = 333
 * Output: "0.(012)"
 * <p>
 * Example 5:
 * Input: numerator = 1, denominator = 5
 * Output: "0.2"
 * <p>
 * Constraints:
 * -2^31 <= numerator, denominator <= 2^31 - 1
 * denominator != 0
 */
public class Fraction_to_Recurring_Decimal_166 {
    public static String fractionToDecimal(int numerator, int denominator) {
        return fractionToDecimal_r3_1(numerator, denominator);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 只有小数部分存在无限循环的可能。整数部分不会无线循环。
     * 2. 递归法。先计算整数部分，然后递归计算小数部分。递归时记录被除数，用来判断是否无限循环。
     * <p>
     * fractionToDecimal_3()等都是非递归迭代法。思路一样，迭代法更优。
     * <p>
     * 验证通过：
     * Runtime 8 ms Beats 7.72%
     * Memory 41.32 MB Beats 14.25%
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal_r3_1(int numerator, int denominator) {
        StringBuilder ret = new StringBuilder();
        //处理符号
        if ((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0)) {
            ret.append("-");
        }
        long n = Math.abs((long) numerator);
        long d = Math.abs((long) denominator);
        //先计算整数部分
        ret.append(n / d);
        //计算小数点
        if (n % d != 0) {
            ret.append(".");
            Map<Long, Integer> appeared = new HashMap<>();
            ret.append(helper_r3_1(n % d, d, "", appeared));
        }
        return ret.toString();
    }

    private static String helper_r3_1(long numerator, long denominator, String prev, Map<Long, Integer> appeared) {
        if (appeared.containsKey(numerator)) {
            //表明是无限不循环小数，补全parentheses
            String ret = prev.substring(0, appeared.get(numerator));
            ret = ret + "(" + prev.substring(appeared.get(numerator)) + ")";
            return ret;
        }
        long n = numerator * 10;
        if (n % denominator == 0) {
            String t = prev + String.valueOf(n / denominator);
            appeared.put(numerator, prev.length());
            return t;
        } else {
            String t = prev + String.valueOf(n / denominator);
            appeared.put(numerator, prev.length());
            return helper_r3_1(n % denominator, denominator, t, appeared);
        }
    }


    /**
     * round 2
     * <p>
     * 思路：
     * 1.先计算出整数部分，公式为 n/d
     * 2.再计算小数部分，公式为 n-n/d
     * 3.小数部分字符串判断是否是循环小数，或者根据被除数是否重复判断
     * <p>
     * 算法：
     * 0. cached=Map<Integer,Integer> 用来缓存被除数和对应的商的起始位置
     * 1. 计算商的整数部分 integer=n/d
     * 2. 被除数变更 n=n-integer*d
     * 3. 如果 n!=0 循环计算小数部分
     * 3.1 n扩大十倍
     * 3.2 如果 cached中存在n，那么 表示是循环小数，
     * 3.2.1 根据n在cached中的位置修改返回结果集（主要是添加圆括号）
     * 3.2.2 跳出循环
     * 3.3 如果 cached中不存在n，那么
     * 3.3.1 计算商为t
     * 3.3.2 t追加到返回结果集字符串中
     * 3.3.3 n和t加入到cached中
     * 3.3.4 重新计算n n=n-t*d
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Fraction to Recurring Decimal.
     * Memory Usage: 39.9 MB, less than 87.23% of Java online submissions for Fraction to Recurring Decimal.
     *
     * @param numerator
     * @param denominator
     * @return
     */
    public static String fractionToDecimal_3(int numerator, int denominator) {
        if (numerator == denominator) return "1";
        StringBuilder s1 = new StringBuilder();//存储整数部分和小数点部分
        StringBuilder s2 = new StringBuilder();//存储小数部分，不包含小数点
        //转化成long很关键，为了避免无法计算的边界值
        long n = numerator;
        long d = denominator;
        //下面的代码可以用精妙代码替代，详见fractionToDecimal_2()
        if ((n < 0 && d > 0) || (n > 0 && d < 0)) {
            s1.append("-");
            n = Math.abs(n);
            d = Math.abs(d);
        }
        Map<Long, Integer> cached = new HashMap<>();
        //计算整数部分
        long i = n / d;
        s1.append(i);
        n -= i * d;
        //追加小数点
        if (n != 0) s1.append(".");
        //计算小数部分
        int pos = 0;
        while (n != 0) {
            n *= 10;
            //计算循环部分
            if (cached.containsKey(n)) {
                s2.insert(cached.get(n), "(");
                s2.append(")");
                break;
            }
            long t = n / d;
            s2.append(t);
            cached.put(n, pos);
            n -= t * d;
            pos++;
        }

        return s1.append(s2).toString();
    }

    /**
     * 改进fractionToDecimal_1()，把int改成long
     * 验证通过，性能一般：
     * Runtime: 8 ms, faster than 14.70% of Java online submissions for Fraction to Recurring Decimal.
     * Memory Usage: 38.7 MB, less than 8.30% of Java online submissions for Fraction to Recurring Decimal.
     *
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
     *
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
//        do_func(-10, 1, "-10");
//        do_func(-1, 10, "-0.1");

        //TODO 计算过程中超过了int的最大长度，导致错误。是否可以转换为long?
        //TODO 用long进行计算后，这条用例通过了
//        do_func(-1, -2147483648, "0.0000000004656612873077392578125");
        //todo fractionToDecimal_2()下面的用例会无限循环
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
