package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 87. Scramble String
 * Hard
 * ----------------------
 * We can scramble a string s to get a string t using the following algorithm:
 * If the length of the string is 1, stop.
 * If the length of the string is > 1, do the following:
 * Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y where s = x + y.
 * Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become s = x + y or s = y + x.
 * Apply step 1 recursively on each of the two substrings x and y.
 *
 * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return false.
 *
 * Example 1:
 * Input: s1 = "great", s2 = "rgeat"
 * Output: true
 * Explanation: One possible scenario applied on s1 is:
 * "great" --> "gr/eat" // divide at random index.
 * "gr/eat" --> "gr/eat" // random decision is not to swap the two substrings and keep them in order.
 * "gr/eat" --> "g/r / e/at" // apply the same algorithm recursively on both substrings. divide at ranom index each of them.
 * "g/r / e/at" --> "r/g / e/at" // random decision was to swap the first substring and to keep the second substring in the same order.
 * "r/g / e/at" --> "r/g / e/ a/t" // again apply the algorithm recursively, divide "at" to "a/t".
 * "r/g / e/ a/t" --> "r/g / e/ a/t" // random decision is to keep both substrings in the same order.
 * The algorithm stops now and the result string is "rgeat" which is s2.
 * As there is one possible scenario that led s1 to be scrambled to s2, we return true.
 *
 * Example 2:
 * Input: s1 = "abcde", s2 = "caebd"
 * Output: false
 *
 * Example 3:
 * Input: s1 = "a", s2 = "a"
 * Output: true
 *
 * Constraints:
 * s1.length == s2.length
 * 1 <= s1.length <= 30
 * s1 and s2 consist of lower-case English letters.
 */
public class Scramble_String_87 {
    public static boolean isScramble(String s1, String s2) {
        return isScramble_2(s1, s2);
    }

    private static Map<String, Boolean> cache = new HashMap<>();

    /**
     * 在isScramble_1的基础上，加入cache
     *
     * 验证通过：
     * Runtime: 16 ms, faster than 67.38% of Java online submissions for Scramble String.
     * Memory Usage: 47.5 MB, less than 35.62% of Java online submissions for Scramble String.
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isScramble_2(String s1, String s2) {
        String key = s1 + ":" + s2;
        if (cache.containsKey(key)) return cache.get(key);

        if (s1.equals(s2)) return true;
        if (s1.length() != s2.length()) return false;
        int[] cnt = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            cnt[s1.charAt(i) - 'a']++;
            cnt[s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) return false;
        }

        for (int i = 1; i < s1.length(); i++) {
            String s1Left = s1.substring(0, i);
            String s1Right = s1.substring(i);

            String s2Left1 = s2.substring(0, i);
            String s2Right1 = s2.substring(i);

            String s2Left2 = s2.substring(0, s1.length() - i);
            String s2Right2 = s2.substring(s1.length() - i);

            if ((isScramble_2(s1Left, s2Left1) && isScramble_2(s1Right, s2Right1))
                    || (isScramble_2(s1Left, s2Right2) && isScramble_2(s1Right, s2Left2))) {
                cache.put(key, true);
                return true;
            }
        }
        cache.put(key, false);
        return false;
    }

    /**
     * 基本的操作类型有三种：divide;swap;not swap;
     * divide操作之后，s1分割成两个子串 s1b1和s1b2。
     * 根据s1b1和s1b2的长度，我们可以把s2分割成两个子串，s2b1和s2b2。其中因为可能swap，所以s2b1和s2b2有两种组合，即从左分割和从右分割。
     * 比较s1的子串和s2的子串，如果对应子串包含的字母和字母出现次数是一样的，那么分别对子串进行递归计算。
     *
     * 具体思路：
     * 1.n=len(s1)=len(s2)
     * 2.如果s1==s2，返回true
     * 3.假设根据i分割s1，分割后为s1[0:i]和s1[i:]
     * 4.s2对应分割为s2[0:i]和s2[i:]或s2[0:n-i]和s2[n-i:]
     * 5.比较 （s1[0:i],s2[0:i] 和 s1[i:],s2[i:]） 或 （s1[0:i],s2[n-i:] 和 s1[i:]，s2[0:n-i]）
     * 6.递归
     *
     * 逻辑和思路正确
     * 验证失败：Time Limit Exceeded.
     * 286 / 288 test cases passed.
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isScramble_1(String s1, String s2) {
        if (s1.equals(s2)) return true;
        if (!validate(s1, s2)) return false;
        for (int i = 1; i < s1.length(); i++) {
            String s1Left = s1.substring(0, i);
            String s1Right = s1.substring(i, s1.length());

            String s2Left1 = s2.substring(0, i);
            String s2Right1 = s2.substring(i, s1.length());

            String s2Left2 = s2.substring(0, s1.length() - i);
            String s2Right2 = s2.substring(s1.length() - i, s1.length());

            if ((isScramble_1(s1Left, s2Left1) && isScramble_1(s1Right, s2Right1))
                    || (isScramble_1(s1Left, s2Right2) && isScramble_1(s1Right, s2Left2))) {
                return true;
            }
        }
        return false;
    }

    //判断两个字符串包含的字母和字母出现次数是否是一样的
    private static boolean validate(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        if (s1.equals(s2)) return true;
        //这里可以优化成只使用一个数组，计算时s1的字母加1，s2的字母减一，最终数组全部为0即可。
        int[] a = new int[26], b = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            a[s1.charAt(i) - 'a']++;
            b[s2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("great", "rgeat", true);
        do_func("abcde", "caebd", false);
        do_func("a", "a", true);
        do_func("great", "eatrg", true);
        do_func("gredat", "eatrgd", false);
        do_func("eebaacbcbcadaaedceaaacadccd", "eadcaacabaddaceacbceaabeccd", false);
    }

    private static void do_func(String s1, String s2, boolean expected) {
        boolean ret = isScramble(s1, s2);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}
