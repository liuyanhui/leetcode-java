package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 290. Word Pattern
 * Easy
 * ---------------
 * Given a pattern and a string s, find if s follows the same pattern.
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in s.
 *
 * Example 1:
 * Input: pattern = "abba", s = "dog cat cat dog"
 * Output: true
 *
 * Example 2:s
 * Input: pattern = "abba", s = "dog cat cat fish"
 * Output: false
 *
 * Example 3:
 * Input: pattern = "aaaa", s = "dog cat cat dog"
 * Output: false
 *
 * Example 4:
 * Input: pattern = "abba", s = "dog dog dog dog"
 * Output: false
 *
 * Constraints:
 * 1 <= pattern.length <= 300
 * pattern contains only lower-case English letters.
 * 1 <= s.length <= 3000
 * s contains only lower-case English letters and spaces ' '.
 * s does not contain any leading or trailing spaces.
 * All the words in s are separated by a single space.
 */
public class Word_Pattern_290 {

    public static boolean wordPattern(String pattern, String s) {
        return wordPattern_3(pattern, s);
    }

    /**
     * round 2
     *
     * 思路：
     * 1.s分解成数组
     * 2.使用Map，分别缓存pattern中字母代表的单词和s中单词代表的字母
     * 3.遍历s和pattern
     *
     * @param pattern
     * @param s
     * @return
     */
    public static boolean wordPattern_3(String pattern, String s) {
        Map<Character, String> char2Word = new HashMap<>();
        Map<String, Character> word2Char = new HashMap<>();
        String[] words = s.split(" ");
        if (pattern.length() != words.length) return false;
        for (int i = 0; i < words.length; i++) {
            char2Word.putIfAbsent(pattern.charAt(i), words[i]);
            word2Char.putIfAbsent(words[i], pattern.charAt(i));
            if (char2Word.get(pattern.charAt(i)).equals(words[i])
                    && word2Char.get(words[i]).equals(pattern.charAt(i))) {

            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 精简版代码，参考思路：
     * https://leetcode.com/problems/word-pattern/discuss/73402/8-lines-simple-Java
     *
     * 1.map中同时保存pattern:index和word:index，使用map.put()的返回值进行判断
     * 2.两次map.put()操作的结果相同表示匹配，不同表示不匹配
     *
     * @param pattern
     * @param s
     * @return
     */
    public static boolean wordPattern_2(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) return false;
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            Character c = pattern.charAt(i);
            if (map.put(c, i) != map.put(words[i], i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 采用map缓存，key:pattern中的字符; value:s中的单词
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 81.53% of Java online submissions for Word Pattern.
     * Memory Usage: 38.9 MB, less than 5.36% of Java online submissions for Word Pattern.
     *
     * @param pattern
     * @param s
     * @return
     */
    public static boolean wordPattern_1(String pattern, String s) {
        String[] sArray = s.split(" ");
        if (sArray.length != pattern.length()) return false;
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            //获取pattern的字母
            String tp = String.valueOf(pattern.charAt(i));
            //获取单词
            String ts = sArray[i];
            //开始校验
            if (map.containsKey(tp) && map.get(tp).equals(ts)) {
                continue;
            } else if (!map.containsKey(tp) && !map.containsValue(ts)) {
                map.put(tp, ts);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("abba", "dog cat cat dog", true);
        do_func("abba", "dog cat cat fish", false);
        do_func("aaaa", "dog cat cat dog", false);
        do_func("abba", "dog dog dog dog", false);
        do_func("aaa", "aa aa aa aa", false);
        do_func("aaaaaa", "aa aa aa aa", false);
        do_func("a", "aaa", true);
    }

    private static void do_func(String pattern, String s, boolean expected) {
        boolean ret = wordPattern(pattern, s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
