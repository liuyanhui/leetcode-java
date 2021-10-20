package leetcode;

/**
 * 393. UTF-8 Validation
 * Medium
 * ----------------------------
 * Given an integer array data representing the data, return whether it is a valid UTF-8 encoding.
 *
 * A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
 * For a 1-byte character, the first bit is a 0, followed by its Unicode code.
 * For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
 *
 * This is how the UTF-8 encoding would work:
 *    Char. number range  |        UTF-8 octet sequence
 *       (hexadecimal)    |              (binary)
 *    --------------------+---------------------------------------------
 *    0000 0000-0000 007F | 0xxxxxxx
 *    0000 0080-0000 07FF | 110xxxxx 10xxxxxx
 *    0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
 *    0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 *
 * Note: The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.
 *
 * Example 1:
 * Input: data = [197,130,1]
 * Output: true
 * Explanation: data represents the octet sequence: 11000101 10000010 00000001.
 * It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
 *
 * Example 2:
 * Input: data = [235,140,4]
 * Output: false
 * Explanation: data represented the octet sequence: 11101011 10001100 00000100.
 * The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
 * The next byte is a continuation byte which starts with 10 and that's correct.
 * But the second continuation byte does not start with 10, so it is invalid.
 *
 * Constraints:
 * 1 <= data.length <= 2 * 10^4
 * 0 <= data[i] <= 255
 */
public class UTF8_Validation_393 {
    public static boolean validUtf8(int[] data) {
        return validUtf8_2(data);
    }

    /**
     * validUtf8_1()的代码优化版，仅优化代码可读性。
     *
     * 验证通过
     *
     * @param data
     * @return
     */
    public static boolean validUtf8_2(int[] data) {
        int mask = 128;//这里是优化部分
        int c = 0;//当前字符由几个byte组成，计数器
        for (int i = 0; i < data.length; i++) {
            if (c < 0) return false;
            if (c == 0) {//字符的第一个byte
                if ((data[i] & mask) == 0) {
                    continue;
                } else {
                    //计数器，计算字符的占用的byte数
                    while ((data[i] & (mask >> c)) > 0) {
                        c++;
                    }
                    if (c == 2 || c == 3 || c == 4) {
                        //第一个数字也占用计数器，所以计数器要减1
                        c -= 1;
                    } else {//计数器值错误
                        return false;
                    }
                }
            } else {//字符的非第一个byte
                if ((data[i] & mask) == 0 || (data[i] & mask) != mask) {//第一个bit校验错误
                    return false;
                }
                c--;
            }
        }
        return c == 0;//防止单个字符，形如：['11100110']
    }

    /**
     * 思路：
     * 1.如果第一个字符时1，那么第一个0是分隔符
     * 2.先确定是几个byte的字符，在判断是否满足条件
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 56.05% of Java online submissions for UTF-8 Validation.
     * Memory Usage: 45.2 MB, less than 13.06% of Java online submissions for UTF-8 Validation.
     *
     * @param data
     * @return
     */
    public static boolean validUtf8_1(int[] data) {
        int c = 0;//当前字符由几个byte组成，计数器
        for (int i = 0; i < data.length; i++) {
            if (c < 0) return false;
            if (c == 0) {//字符的第一个byte
                if ((data[i] & 128) == 0) {
                    continue;
                } else {
                    //计数器，计算字符的占用的byte数
                    while ((data[i] & (128 >> c)) > 0) {
                        c++;
                    }
                    if (c == 2 || c == 3 || c == 4) {
                        //第一个数字也占用计数器，所以计数器要减1
                        c -= 1;
                    } else {//计数器值错误
                        return false;
                    }
                }
            } else {//字符的非第一个byte
                if ((data[i] & 128) == 0) {//第一个bit校验错误
                    return false;
                } else {
                    int t = 1 << 7;
                    if ((data[i] & t) != t) {
                        return false;
                    }
                }
                c--;
            }
        }
        return c == 0;//防止单个字符，形如：['11100110']
    }

    public static void main(String[] args) {
        do_func(new int[]{197, 130, 1}, true);
        do_func(new int[]{235, 140, 4}, false);
        do_func(new int[]{1}, true);
        do_func(new int[]{100}, true);
        do_func(new int[]{197}, false);
        do_func(new int[]{230, 136, 145}, true);
    }

    private static void do_func(int[] data, boolean expected) {
        boolean ret = validUtf8(data);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
