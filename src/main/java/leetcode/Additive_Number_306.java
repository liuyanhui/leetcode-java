package leetcode;

/**
 * 306. Additive Number
 * Medium
 * -------------------------
 * Additive number is a string whose digits can form additive sequence.
 * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number in the sequence must be the sum of the preceding two.
 * Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
 * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
 * <p>
 * Example 1:
 * Input: "112358"
 * Output: true
 * Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
 * 1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 * <p>
 * Example 2:
 * Input: "199100199"
 * Output: true
 * Explanation: The additive sequence is: 1, 99, 100, 199.
 * 1 + 99 = 100, 99 + 100 = 199
 * <p>
 * Constraints:
 * num consists only of digits '0'-'9'.
 * 1 <= num.length <= 35
 * Follow up:
 * How would you handle overflow for very large input integers?
 */
public class Additive_Number_306 {
    public static boolean isAdditiveNumber(String num) {
        return isAdditiveNumber_r3_1(num);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking:
     * 1. naive solution
     * 穷举法+递归。
     * 输入字符串被划分成4部分：calced|a|b|uncalc ，其中: a+b=uncalc[0:i]
     * 递归函数为：
     * f(a,b,num)
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 76.17%
     * Memory 41.97 MB Beats 27.98%
     */
    public static boolean isAdditiveNumber_r3_1(String num) {
        for (int i = 1; i <= num.length() / 2; i++) {
            //计算a
            String a = num.substring(0, i);
            if (a.startsWith("0") && a.length() > 1) break;
            for (int j = i + 1; j < num.length(); j++) {
                //计算b
                String b = num.substring(i, j);
                if (b.startsWith("0") && b.length() > 1) break;
                //计算和
                String c = add_r3_1(a, b);
                String rest = num.substring(j);
                //比较和，递归计算
                if (rest.startsWith(c) && check_r3_1(b, c, rest.substring(c.length()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check_r3_1(String a, String b, String num) {
        if (num == null || num.length() == 0) return true;
        if (a.startsWith("0") && a.length() > 1) return false;
        if (b.startsWith("0") && b.length() > 1) return false;
        String c = add_r3_1(a, b);
        if (num.startsWith(c) && check_r3_1(b, c, num.substring(c.length()))) {
            return true;
        }
        return false;
    }

    private static String add_r3_1(String a, String b) {
        StringBuilder res = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 && j >= 0) {
            int t = (a.charAt(i) - '0') + (b.charAt(j) - '0') + carry;
            res.append(String.valueOf(t % 10));
            carry = t / 10;
            i--;
            j--;
        }
        while (i >= 0) {
            int t = (a.charAt(i) - '0') + carry;
            res.append(String.valueOf(t % 10));
            carry = t / 10;
            i--;
        }
        while (j >= 0) {
            int t = (b.charAt(j) - '0') + carry;
            res.append(String.valueOf(t % 10));
            carry = t / 10;
            j--;
        }
        if (carry > 0) {
            res.append(carry);
        }
        return res.reverse().toString();
    }

    /**
     * round 2
     * 思考：
     * 1.先分割，再计算
     * 2.分析输入"12358"、"123581321349"和"199100199"、"199100200"可以发现，只要不满足条件，那么就必须退回到最开通的两个数相加，重新开始查找和计算。即如果子序列为{a,b,c,d,e,f}被验证为失败，那么必须从重新选择a和b开始从头计算。那么问题就转化为：先修改b，再修改a，即先增加b，如果仍然失败，那么再增加a。可以使用递归法，F(a,b,remain_str)
     * 3.递归算法。
     * <p>
     * 递归算法：
     * 1.设递归函数为F(a,b,num)。
     * 2.如果a+b==int(num)，那么返回true；如果a+b==c存在且remain_str不为空，那么F(b,c,remain_str)；如果a+b==c不存在，返回false。
     * 3.在函数主入口，通过组合不同的a和b，然后调用F(a,b,num)
     * <p>
     * 改进思路：把int改成long进行比较和计算
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 65.19%
     * Memory 41.9 MB Beats 56.10%
     *
     * @param num
     * @return
     */
    public static boolean isAdditiveNumber_2(String num) {
        for (int i = 1; i < num.length(); i++) {
            String a = num.substring(0, i);
            for (int j = i + 1; j < num.length(); j++) {
                String b = num.substring(i, j);
                if (helper(a, b, num.substring(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean helper(String a, String b, String num) {
        //过滤前导0
        if ((a.charAt(0) == '0' && a.length() > 1)
                || (b.charAt(0) == '0' && b.length() > 1)) return false;
        if (num == null || num.length() == 0) return true;
        //查找sum
        for (int i = 1; i <= num.length(); i++) {
            String c = num.substring(0, i);
            String sum = add2Str(a, b);
            if (sum.equals(c) && helper(b, c, num.substring(i))) {
                return true;
            }

        }
        return false;
    }

    private static String add2Str(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 && j >= 0) {
            int t = (a.charAt(i--) - '0') + (b.charAt(j--) - '0') + carry;
            sb.append(t % 10);
            carry = t >= 10 ? 1 : 0;
        }
        while (i >= 0) {
            int t = (a.charAt(i--) - '0') + carry;
            sb.append(t % 10);
            carry = t >= 10 ? 1 : 0;
        }
        while (j >= 0) {
            int t = (b.charAt(j--) - '0') + carry;
            sb.append(t % 10);
            carry = t >= 10 ? 1 : 0;
        }

        if (carry > 0)
            sb.append("1");

        return sb.reverse().toString();
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/additive-number/discuss/75576/0ms-concise-C%2B%2B-solution-(perfectly-handles-the-follow-up-and-leading-0s)
     * https://leetcode.com/problems/additive-number/discuss/75572/*Java*-very-straightforward-solution-with-detailed-explanation
     * https://leetcode.com/problems/additive-number/discuss/75567/Java-Recursive-and-Iterative-Solutions
     * <p>
     * 验证通过：
     * Runtime: 6 ms, faster than 9.58% of Java online submissions for Additive Number.
     * Memory Usage: 38.9 MB, less than 10.54% of Java online submissions for Additive Number.
     *
     * @param num
     * @return
     */
    public static boolean isAdditiveNumber_1(String num) {
        if (num.length() < 3) return false;
        for (int i = 1; i <= num.length() / 2; i++) {
            for (int j = i + 1; j < num.length(); j++) {
                if (check(num.substring(0, i), num.substring(i, j), num.substring(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean check(String a, String b, String num) {
        if (num == null || num.equals("")) return true;
        //去掉b是0开头，位数大于1的情况，即033,0033等情况
        if (b.startsWith("0") && b.length() > 1) return false;
        String c = addTwoNum(a, b);
        if (num.startsWith(c)) {
            return check(b, c, num.substring(c.length()));
        }
        return false;
    }

    private static String addTwoNum(String m, String n) {
        int overflow = 0;
        int i = 0;
        String ret = "";
        while (i < m.length() && i < n.length()) {
            int t = (m.charAt(m.length() - 1 - i) - '0') + (n.charAt(n.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        while (i < m.length()) {
            int t = (m.charAt(m.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        while (i < n.length()) {
            int t = (n.charAt(n.length() - 1 - i) - '0') + overflow;
            overflow = t / 10;
            i++;
            ret = String.valueOf(t % 10) + ret;
        }
        if (overflow == 1) {
            ret = "1" + ret;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("112358", true);
        do_func("112334", true);
        do_func("199100199", true);
        do_func("112358132124", false);
        do_func("112358132134", true);
        do_func("112", true);
        do_func("2", false);
        do_func("199001200", false);
        do_func("101", true);
        do_func("199001990", true);
        do_func("100010", false);
        do_func("12358132134", true);
        do_func("123581321340", false);
        do_func("000", true);
        do_func("000000", true);
    }

    private static void do_func(String num, boolean expected) {
        boolean ret = isAdditiveNumber(num);
        System.out.println(ret);
        System.out.println(ret == expected ? "√" : "X");
        System.out.println("--------------");
    }
}
