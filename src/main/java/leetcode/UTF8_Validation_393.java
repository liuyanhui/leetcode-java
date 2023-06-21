package leetcode;

/**
 * 393. UTF-8 Validation
 * Medium
 * ----------------------------
 * Given an integer array data representing the data, return whether it is a valid UTF-8 encoding (i.e. it translates to a sequence of valid UTF-8 encoded characters).
 *
 * A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
 *
 * For a 1-byte character, the first bit is a 0, followed by its Unicode code.
 * For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
 *
 * This is how the UTF-8 encoding would work:
 *      Number of Bytes   |        UTF-8 Octet Sequence
 *                        |              (binary)
 *    --------------------+-----------------------------------------
 *             1          |   0xxxxxxx
 *             2          |   110xxxxx 10xxxxxx
 *             3          |   1110xxxx 10xxxxxx 10xxxxxx
 *             4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 *
 * x denotes a bit in the binary form of a byte that may be either 0 or 1.
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
        return validUtf8_3(data);
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.字符类型分为3种：单字符，多字符head，多字符body。
     * 2.用数字变量缓存多字符，并判断是否满足utf8的格式。
     *
     * review 8进制的数字写法，有些忘记了。0b??二进制，0??八进制，0x??十六进制
     *
     * 验证通过：
     * Runtime 1 ms Beats 100%
     * Memory 44.3 MB Beats 24.9%
     *
     * @param data
     * @return
     */
    public static boolean validUtf8_3(int[] data) {
        int remain = 0;
        for (int n : data) {
            //提取高位的byte类型
            int type = 0;
            if ((n >> 7) == 0) {//0xxxxxxx
                type = 0;
            } else if ((n >> 6) == 0b10) {//10xxxxxx; 2=="10" (Integer.valueOf("10", 2))
                type = 1;
            } else if ((n >> 5) == 0b110) {//110xxxxx; 6=="110" (Integer.valueOf("110", 2))
                type = 2;
            } else if ((n >> 4) == 0b1110) {//1110xxxx; 14=="1110" (Integer.valueOf("1110", 2))
                type = 3;
            } else if ((n >> 3) == 0b11110) {//11110xxx; 30=="11110" (Integer.valueOf("11110", 2))
                type = 4;
            } else {
                return false;
            }

            //跟remain进行比较，判断是否满足规则，并更新remain
            if (type == 0 && remain == 0) {
                continue;
            } else if (type == 1 && remain > 0) {
                remain--;
            } else if (type > 1 && remain == 0) {
                remain = type - 1;
            } else {
                return false;
            }
        }
        return remain == 0;
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
        do_func(new int[]{255}, false);
    }

    private static void do_func(int[] data, boolean expected) {
        boolean ret = validUtf8(data);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
