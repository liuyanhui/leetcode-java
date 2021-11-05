package leetcode;

/**
 * Zigzag Conversion
 * Medium
 * ----------------------------
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 *
 * And then read line by line: "PAHNAPLSIIGYIR"
 *
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string s, int numRows);
 *
 * Example 1:
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 *
 * Example 2:
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * Explanation:
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 *
 * Example 3:
 * Input: s = "A", numRows = 1
 * Output: "A"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consists of English letters (lower-case and upper-case), ',' and '.'.
 * 1 <= numRows <= 1000
 */
public class Zigzag_Conversion_6 {
    /**
     * 验证通过：
     * Runtime: 8 ms, faster than 57.81% of Java .
     * Memory Usage: 40.2 MB, less than 48.42% of .
     *
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {
        if (numRows == 1) return s;
        //初始化
        StringBuilder[] zigzagArray = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            zigzagArray[i] = new StringBuilder();
        }
        //安装zigzag方式处理字符
        boolean down = true;
        int rowIdx = 0;
        for (int i = 0; i < s.length(); i++) {
            zigzagArray[rowIdx].append(String.valueOf(s.charAt(i)));
            if (down) {
                if (rowIdx == numRows - 1) {
                    rowIdx--;
                    down = !down;
                } else {
                    rowIdx++;
                }
            } else {
                if (rowIdx == 0) {
                    rowIdx++;
                    down = !down;
                } else {
                    rowIdx--;
                }
            }
        }
        //合并结果
        for (int i = 1; i < zigzagArray.length; i++) {
            zigzagArray[0].append(zigzagArray[i]);
        }
        return zigzagArray[0].toString();
    }

    public static void main(String[] args) {
        do_func("PAYPALISHIRING", 3, "PAHNAPLSIIGYIR");
        do_func("PAYPALISHIRING", 4, "PINALSIGYAHRPI");
        do_func("A", 1, "A");
        do_func("AB", 1, "AB");
    }

    private static void do_func(String s, int numRows, String expected) {
        String ret = convert(s, numRows);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
