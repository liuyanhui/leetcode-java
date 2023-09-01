package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Longest Substring Without Repeating Characters
 * Medium
 * --------------------------------
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Example 4:
 * Input: s = ""
 * Output: 0
 *
 * Constraints:
 * 0 <= s.length <= 5 * 10^4
 * s consists of English letters, digits, symbols and spaces.
 */
public class Longest_Substring_Without_Repeating_Characters_3 {
    public static int lengthOfLongestSubstring(String s) {
        return lengthOfLongestSubstring_5(s);
    }

    /**
     * round 3
     *
     * Thinking：
     * 1.滑动窗口思路。
     * 用Map保存已经出现的字母，key是字母，value是位置。
     * 当右侧字母在缓存中已经存在时，计算substring长度并判断是否需要更新结果，窗口左边界移到map中该字母的下一位；否则，窗口右边界右移一位，字母加入map。
     *
     * 验证通过：
     * Runtime 6 ms Beats 75.99%
     * Memory 43 MB Beats  91.43%
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_5(String s) {
        int res = 0;
        Map<Character, Integer> cache = new HashMap<>();
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //当前字母是否已经在窗口中
            if (cache.containsKey(c) && left <= cache.get(c)) {
                //计算窗口大小，并判断是否更新返回结果
                res = Math.max(res, i - left);
                //更新
                left = cache.get(c) + 1;
            }
            //更新缓存
            cache.put(c, i);
        }
        return Math.max(res, s.length() - left);//最后一个字符的特殊情况
    }

    /**
     * 参考材料：
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/1729/11-line-simple-Java-solution-O(n)-with-explanation
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_4(String s) {
        int len = 0;
        int left = 0;
        HashMap<Character, Integer> cached = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (cached.containsKey(c)) {
                left = Math.max(left, cached.get(c) + 1);
            }
            len = Math.max(len, i - left + 1);
            cached.put(c, i);
        }
        return len;
    }

    /**
     * lengthOfLongestSubstring_2类似思路，但是是另一个实现版本。
     * 缓存中key是字符，value是key出现的下标（lengthOfLongestSubstring_2中value是出现次数）
     *
     * 思路如下：
     * 1.滑动窗口。双指针+缓存，缓存存储字符的下标。
     * 2.如果当前字符c出现过，计算最大长度，清理缓存，l跳转到缓存c小标的下一个位置，c加入缓存，r++
     * 3.如果当前字符c为出现过，c加入缓存，r++
     *
     * 验证通过：
     * Runtime: 14 ms, faster than 44.71%.
     * Memory Usage: 46 MB, less than 26.72%.
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_3(String s) {
        int max = 0;
        if (s == null || s.length() == 0) return max;
        int l = 0, r = 0;
        HashMap<Character, Integer> cached = new HashMap<>();
        while (r < s.length()) {
            char c = s.charAt(r);
            if (cached.containsKey(c)) {
                int t = cached.get(c);
                while (l <= t) {
                    cached.remove(s.charAt(l++));
                }
            }
            cached.put(c, r);
            max = Math.max(max, r - l + 1);
            r++;
        }
        return max;
    }

    /**
     * round 2
     * 思路：
     * 1.滑动窗口。双指针+缓存，缓存存储在滑动窗口内字符的出现次数
     * 2.如果当前字符c未出现过，缓存中出现次数加1，计算长度，r++
     * 3.如果当前字符c已出现过，l左移直到缓存中字符c出现次数为0（l左移过程中每个字符出现次数-1），c的出现次数加1，r++
     *
     * 验证通过：
     * Runtime: 14 ms, faster than 44.69% of Java online submissions for Longest Substring Without Repeating Characters.
     * Memory Usage: 44.3 MB, less than 44.62% of Java online submissions for Longest Substring Without Repeating Characters.
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_2(String s) {
        int max = 0;
        if (s == null || s.length() == 0) return max;
        int l = 0, r = 0;
        HashMap<Character, Integer> cached = new HashMap<>();
        while (r < s.length()) {
            char c = s.charAt(r);
            cached.computeIfAbsent(c, v -> 0);
            while (cached.get(c) > 0) {
                cached.put(s.charAt(l), cached.get(s.charAt(l)) - 1);
                l++;
            }
            cached.put(c, cached.get(c) + 1);
            max = max > r - l + 1 ? max : r - l + 1;
            r++;
        }
        return max;
    }

    /**
     * 双指针法，两个指针i,j把s分割成三部分，[0,i)是已经遍历过的，[i,j)是当前的substring,[j,s.length)是未遍历的部分。
     * 用数组cache缓存当前的substring部分，即cache保存[i,j)部分。
     * 当cache中存在s[j]时：1.计算最长子串长度；2.循环执行i++直到s[i]==s[j]。
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 85.21% of Java .
     * Memory Usage: 38.9 MB, less than 93.58% of Java .
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = 0;
        Set<Character> cache = new HashSet<>();
        int i = 0, j = 0;
        for (; j < s.length(); j++) {
            Character c = s.charAt(j);
            if (cache.contains(c)) {
                len = Math.max(len, j - i);
                while (i < j) {
                    Character t = s.charAt(i++);
                    if (t == c) {
                        break;
                    } else {
                        cache.remove(t);
                    }
                }
            }
            cache.add(c);
        }

        return Math.max(len, j - i);
    }

    public static void main(String[] args) {
        do_func("abcabcbb", 3);
        do_func("bbbbb", 1);
        do_func("pwwkew", 3);
        do_func("", 0);
        do_func("a", 1);
        do_func(" ", 1);
        do_func("abc", 3);
        do_func("tmmzuxt", 5);
    }

    private static void do_func(String s, int expected) {
        int ret = lengthOfLongestSubstring(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
