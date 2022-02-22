package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 91. Decode Ways
 * Medium
 * -------------------
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 *
 * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
 * "AAJF" with the grouping (1 1 10 6)
 * "KJF" with the grouping (11 10 6)
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
 *
 * Given a string s containing only digits, return the number of ways to decode it.
 *
 * The answer is guaranteed to fit in a 32-bit integer.
 *
 * Example 1:
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 *
 * Example 3:
 * Input: s = "0"
 * Output: 0
 * Explanation: There is no character that is mapped to a number starting with 0.
 * The only valid mappings with 0 are 'J' -> "10" and 'T' -> "20", neither of which start with 0.
 * Hence, there are no valid ways to decode this since all digits need to be mapped.
 *
 * Example 4:
 * Input: s = "06"
 * Output: 0
 * Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").
 *
 * Constraints:
 * 1 <= s.length <= 100
 * s contains only digits and may contain leading zero(s).
 */
public class Decode_Ways_91 {

    public static int numDecodings(String s) {
        return numDecodings_4(s);
    }

    /**
     * round 2
     *
     * DP思路，公式为
     * dp[i]=dp[i-1]+dp[i-2]。dp[i]为从0开始到i的组合数
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 95.46% of Java online submissions for Decode Ways.
     * Memory Usage: 42.8 MB, less than 11.09% of Java online submissions for Decode Ways.
     *
     * @param s
     * @return
     */
    public static int numDecodings_4(String s) {
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - '0';
            if (c > 0) {
                dp[i + 1] = dp[i];
            }
            if (i > 0) {
                int t = (s.charAt(i - 1) - '0') * 10 + c;
                if (10 <= t && t <= 26)
                    dp[i + 1] += dp[i - 1];
            }
            if (dp[i + 1] == 0) return 0;
        }
        return dp[dp.length - 1];
    }

    /**
     * dp思路，公式如下：
     * 1.dp[i]表示从i到末尾的Decode Ways数量，dp[0]为返回结果
     * 2.公式：dp[i]=dp[i+1]+dp[i+2]。
     * 3.特殊情况：当s[i]=='0'时dp[i]=0；当s[i,i+2]>26时dp[i]=dp[i+1]
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 95.24% of Java online submissions for Decode Ways.
     * Memory Usage: 37.4 MB, less than 71.44% of Java online submissions for Decode Ways.
     *
     * @param s
     * @return
     */
    public static int numDecodings_3(String s) {
        int[] dp = new int[s.length() + 2];
        //初始化dp
        dp[s.length() + 1] = 0;
        dp[s.length()] = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') dp[i] = 0;
            else {
                dp[i] = dp[i + 1];
                if (i + 2 <= s.length()) {//当心substring()超出范围
                    int tmp = Integer.valueOf(s.substring(i, i + 2));
                    if (tmp <= 26) dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    /**
     * numDecodings_1的改进版，解决 Time Limit Exceeded 问题
     * 使用map缓存应计算过的字符，Map<Integer, Integer> ，其中key是第个字符的下标，value是从这个字符到结尾的Decode Ways数
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 37.22% of Java online submissions for Decode Ways.
     * Memory Usage: 37.7 MB, less than 37.88% of Java online submissions for Decode Ways.
     * @param s
     * @return
     */
    public static int numDecodings_2(String s) {
        Map<Integer, Integer> cache = new HashMap<>();
        return do_decode_recursive_2(s, 0, cache);
    }

    private static int do_decode_recursive_2(String s, int beg, Map<Integer, Integer> cache) {
        if (cache.containsKey(beg)) return cache.get(beg);
        if (beg > s.length()) return 0;
        if (beg == s.length()) return 1;
        int ret = 0;
        char t1 = s.charAt(beg);
        //处理1个数字的情况，需要过滤0
        if (t1 != '0') {
            ret += do_decode_recursive_2(s, beg + 1, cache);
            //处理2个数字的情况，需要过滤0，处理[1,26]之间的数
            if (beg + 1 < s.length()) {
                int t2 = Integer.valueOf(s.substring(beg, beg + 2));
                if (1 < t2 && t2 < 27) {
                    ret += do_decode_recursive_2(s, beg + 2, cache);
                }
            }
        }
        cache.put(beg, ret);
        return ret;
    }


    /**
     * 递归思路：
     * 1.存在1个字符和2个字符两种情况
     * 2.从前向后遍历字符串
     * 3.当遍历到末尾时表示存在一种可能；当没有达到末尾时表示这种可能无效，不计入结果中。
     *
     * 验证失败：Time Limit Exceeded
     *
     * @param s
     * @return
     */
    public static int numDecodings_1(String s) {
        return do_decode_recursive_1(s, 0);
    }

    private static int do_decode_recursive_1(String s, int beg) {
        if (beg > s.length()) return 0;
        if (beg == s.length()) return 1;
        int ret = 0;
        char t1 = s.charAt(beg);
        //处理1个数字的情况，需要过滤0
        if (t1 != '0') {
            ret += do_decode_recursive_1(s, beg + 1);
            //处理2个数字的情况，需要过滤0，处理[1,26]之间的数
            if (beg + 1 < s.length()) {
                int t2 = Integer.valueOf(s.substring(beg, beg + 2));
                if (1 < t2 && t2 < 27) {
                    ret += do_decode_recursive_1(s, beg + 2);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("12", 2);
        do_func("226", 3);
        do_func("0", 0);
        do_func("06", 0);
        do_func("106", 1);
        do_func("1060", 0);
        do_func("60", 0);
        do_func("3000002", 0);
        do_func("111111111111111111111111111111111111111111111", 1836311903);
        do_func("111", 3);
    }

    private static void do_func(String s, int expected) {
        int ret = numDecodings(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
