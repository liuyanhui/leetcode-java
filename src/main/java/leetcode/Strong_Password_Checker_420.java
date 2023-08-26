package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 420. Strong Password Checker
 * Hard
 * ----------------------------
 * A password is considered strong if the below conditions are all met:
 * - It has at least 6 characters and at most 20 characters.
 * - It contains at least one lowercase letter, at least one uppercase letter, and at least one digit.
 * - It does not contain three repeating characters in a row (i.e., "Baaabb0" is weak, but "Baaba0" is strong).
 *
 * Given a string password, return the minimum number of steps required to make password strong. if password is already strong, return 0.
 *
 * In one step, you can:
 * - Insert one character to password,
 * - Delete one character from password, or
 * - Replace one character of password with another character.
 *
 * Example 1:
 * Input: password = "a"
 * Output: 5
 *
 * Example 2:
 * Input: password = "aA1"
 * Output: 3
 *
 * Example 3:
 * Input: password = "1337C0d3"
 * Output: 0
 *
 * Constraints:
 * 1 <= password.length <= 50
 * password consists of letters, digits, dot '.' or exclamation mark '!'.
 */
public class Strong_Password_Checker_420 {
    public static int strongPasswordChecker(String password) {
        return strongPasswordChecker_1(password);
    }

    /**
     * 理解起来太费劲，性价比低。放弃
     *
     * Thinking：
     * 0.先判断是否为strong password，并统计不满足的原因；再计算转换成strong password时的最小步骤数。
     * 1.难点在于计算出把not strong的密码转成strong密码的最小步骤数。
     * 1.1.根据三种转换策略，如何组合三种策略是重点。有两种情况：
     * 1.1.1.字符种类的缺失，可以通过replace和insert解决；
     * 1.1.2.连续字符超长，可以通过replace和delete解决。
     * 1.2.根据密码长度限制。设输入的密码长度为len。有三种情况：
     * 1.2.1.len<6时。先用replace操作补全三种字符类型，再用replace消除连续相同字符长度，最后用insert补全字符长度到6。
     * 1.2.2.len==6时。先用replace操作补全三种字符类型，再用replace消除连续相同字符长度。
     * 1.2.3.len>6。先用replace操作补全三种字符类型，再用replace消除连续相同字符长度。
     * 2.算法如下：
     * 2.1.one pass 搞定是否为strong password。同时记录是否有大小写字母和数字。如果存在小写字母，lower=1；如果存在大写字母，upper=1；如果存在数字，digit=1。记录连续重复超过3的字符情况，保存在array中，每个元素为连续次数超过3次的次数。
     * 2.1.1.已经是strong password，返回0。
     * 2.1.2.不是strong password时，转换密码，计算最小转换步数。设输入的密码长度为len。
     * 2.2.1.如果 len<6 ，替换连续出现的字符，并补全字符直到长度为6；
     * 2.2.2.如果 len==6，替换连续出现的字符。
     * 2.2.3.如果 len>6，删除多余的字符知道长度为6，替换字符。
     * 3.连续出现的字符的约束，将第{3,6,9..}个字符替换即可实现最小步骤数满足该约束。
     * 4.先统计缺失的字符类型，和连续相同字符的情况；再计算哪些位置可以执行操作满足约束。
     * 5.输入长度大于20时，需要单独考虑
     *
     * @param password
     * @return
     */
    public static int strongPasswordChecker_1(String password) {
        //预处理，统计字符类型和连续重复字符的情况
        int lower = 0, upper = 0, digit = 0;
        List<Integer> repeating = new ArrayList<>();
        int beg = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            //计算是否出现过小写字母，大写字母，数字
            if ('a' <= c && c <= 'z')
                lower++;
            else if ('A' <= c && c <= 'Z')
                upper++;
            else if ('0' <= c && c <= '9')
                digit++;
            //统计连续出现三次的相同字符的情况
            if (c != password.charAt(beg)) {
                if (i - beg >= 3) {
                    repeating.add(i - beg);
                }
                beg = i;
            } else if (i == password.length() - 1) {//最后一个字符也是连续相同字符的情况。
                repeating.add(i - beg + 1);
            }
        }
        //计算由于缺失字符类型需要操作的次数
        int miss = 0;
        if (lower == 0) miss++;
        if (upper == 0) miss++;
        if (digit == 0) miss++;
        //计算由于连续重复相同字符需要操作的次数。在第{3,6,9..}位置需要执行替换操作。
        int replaceCnt = 0;
        for (int r : repeating) {
            replaceCnt += r / 3;
        }
        //计算转换次数
        int res = 0;
        //密码长度小于6时，insert优先可以有效减少操作步数
        if (password.length() < 6) {//密码长度小于等于6
            res = Math.max(miss, 6 - password.length());
        } else if (password.length() <= 20) {//密码长度大于6
            res = Math.max(replaceCnt, miss);
        } else {//超过的最大长度20
            res = password.length() - 20;
        }

        return res;
    }

    public static void main(String[] args) {
        do_func("a", 5);
        do_func("aA1", 3);
        do_func("1337C0d3", 0);
        do_func("aaaa1W", 1);
        do_func("......", 3);
        do_func("....", 3);
        do_func("....!!", 3);
        do_func("aaaaa", 2);
        do_func("aaa111", 2);
        do_func("aaaB1", 1);
    }

    private static void do_func(String s, int expected) {
        int ret = strongPasswordChecker(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
