package leetcode;

/**
 * 168. Excel Sheet Column Title
 * Easy
 * -----------------
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 * For example:
 *
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 *     ...
 * Example 1:
 * Input: 1
 * Output: "A"
 *
 * Example 2:
 * Input: 28
 * Output: "AB"
 *
 * Example 3:
 * Input: 701
 * Output: "ZY"
 */
public class Excel_Sheet_Column_Title_168 {
    public static String convertToTitle(int n) {
        return convertToTitle_3(n);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/excel-sheet-column-title/discuss/51398/My-1-lines-code-in-Java-C%2B%2B-and-Python
     * 其中"--n"是精髓
     *
     * @param n
     * @return
     */
    public static String convertToTitle_3(int n) {
        return n == 0 ? "" : convertToTitle(--n / 26) + (char) ('A' + (n % 26));
    }

    /**
     * 非递归迭代法
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Title.
     * Memory Usage: 35.6 MB, less than 99.37% of Java online submissions for Excel Sheet Column Title.
     *
     * @param n
     * @return
     */
    public static String convertToTitle_2(int n) {
        if (n <= 0) return "";
        String[] kv = new String[]{"Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
        String ret = "";
        while (n > 0) {
            ret = kv[n % 26] + ret;
            if (n % 26 == 0) {
                n = n / 26 - 1;
            } else {
                n = n / 26;
            }
        }
        return ret;
    }

    /**
     * 递归法，公式如下：
     * F(n) = F(n/26) + [n%26] , [n%26]={"Z", "A".."Y"}
     * 需要注意的是边界条件，本题不是严格意义上的26进制转换问题，它没有0.
     * 如：52->AZ
     *
     * tip：看过高手的解决方案后，发现可以n--再进行计算，省去了"n % 26 == 0"的判断逻辑
     *
     * 具体分析如下：
     * https://leetcode.com/problems/excel-sheet-column-title/discuss/441430/Detailed-Explanation-Here's-why-we-need-n-at-first-of-every-loop-(JavaPythonC%2B%2B)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Excel Sheet Column Title.
     * Memory Usage: 35.8 MB, less than 97.61% of Java online submissions for Excel Sheet Column Title.
     *
     * @param n
     * @return
     */
    public static String convertToTitle_1(int n) {
        if (n <= 0) return "";
        String[] kv = new String[]{"Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
        int next = n / 26;
        if (n % 26 == 0) {
            next--;
        }

        return convertToTitle(next) + kv[n % 26];
    }

    public static void main(String[] args) {
        do_func(1, "A");
        do_func(26, "Z");
        do_func(28, "AB");
        do_func(52, "AZ");
        do_func(53, "BA");
        do_func(54, "BB");
        do_func(701, "ZY");
    }

    private static void do_func(int n, String expected) {
        String ret = convertToTitle(n);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
