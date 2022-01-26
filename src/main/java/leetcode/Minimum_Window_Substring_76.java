package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring
 * Hard
 * -----------------------------
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".
 * The testcases will be generated such that the answer is unique.
 * A substring is a contiguous sequence of characters within the string.
 *
 * Example 1:
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 *
 * Example 2:
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 *
 * Example 3:
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 *
 * Constraints:
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10^5
 * s and t consist of uppercase and lowercase English letters.
 *
 * Follow up: Could you find an algorithm that runs in O(m + n) time?
 */
public class Minimum_Window_Substring_76 {
    public static String minWindow(String s, String t) {
        return minWindow_1(s, t);
    }

    /**
     * review
     *
     * 滑动窗口思路
     * 大概思路，算法如下：
     * 滑动窗口[left,right]
     * 如果 left节点不在t中，left节点左移
     * 如果 left节点在t中，
     *     如果 right节点在t中，
     *         如果[left,right]匹配t，计算长度，left节点左移，right节点不动
     *         如果[left,right]匹配t，right节点右移
     *     如果 right节点不在t中，right节点右移
     *
     * 伪代码如下：
     * 滑动窗口[l,r]，要确保s[l]一定包含在t中
     * IF s[l]不在t中 THEN
     *     l++;
     *     r=max(l,r);
     * ELSE IF s[l]在t中
     *     IF s[r]在t中 THEN
     *         cnt--;
     *         IF cnt>0 THEN
     *             r++;
     *         ELSE IF cnt==0
     *             ret=min(ret,r-l);
     *             l++;
     *             cnt++;
     *     ELSE IF s[r]不在t中
     *         r++;
     *
     * 验证通过：
     * Runtime: 47 ms, faster than 18.11% of Java online submissions for Minimum Window Substring.
     * Memory Usage: 48 MB, less than 13.24% of Java online submissions for Minimum Window Substring.
     *
     * @param s
     * @param t
     * @return
     */
    public static String minWindow_1(String s, String t) {
        String ret = "";
        int l = 0, r = 0;
        //Map初始化时记录t中每个字符出现的次数
        //map中的value表示初始次数-在[l,r]范围内出现的次数
        Map<Character, Integer> map = new HashMap<>();
        //cnt记录t中的字符还剩几个未出现在[l,r]中
        int cnt = 0;
        //初始化map和cnt
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            map.putIfAbsent(c, 0);
            map.put(c, map.get(c) + 1);
            cnt++;
        }
        if (cnt == 0) return "";
        while (l < s.length() && r < s.length()) {
            char lc = s.charAt(l);
            if (map.containsKey(lc)) {
                char rc = s.charAt(r);
                if (map.containsKey(rc)) {
                    //right节点处理
                    map.put(rc, map.get(rc) - 1);
                    if (map.get(rc) >= 0) {
                        cnt--;
                    }

                    if (cnt == 0) {
                        if (ret.length() == 0 || ret.length() > r - l + 1) {
                            ret = s.substring(l, r + 1);
                        }
                        //处理left节点
                        map.put(lc, map.get(lc) + 1);
                        if (map.get(lc) > 0) {
                            cnt++;
                        }
                        l++;

                        //已经处理过right节点回退
                        if (map.get(rc) >= 0) {
                            cnt++;
                        }
                        map.put(rc, map.get(rc) + 1);
                        continue;//right节点保持不动
                    }
                }
                r++;
            } else {
                l++;
                r = r > l ? r : l;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("ADOBECODEBANC", "ABC", "BANC");
        do_func("a", "a", "a");
        do_func("a", "aa", "");
        do_func("zxcv", "zxcv", "zxcv");
        do_func("a", "b", "");
        do_func("AAAAAAADOBECODEBANC", "AAABC", "BANC");
    }

    private static void do_func(String a, String b, String expected) {
        String ret = minWindow(a, b);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
