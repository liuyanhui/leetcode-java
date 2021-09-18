package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 387. First Unique Character in a String
 * Easy
 * ---------------------------
 * Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1.
 *
 * Example 1:
 * Input: s = "leetcode"
 * Output: 0
 *
 * Example 2:
 * Input: s = "loveleetcode"
 * Output: 2
 *
 * Example 3:
 * Input: s = "aabb"
 * Output: -1
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of only lowercase English letters.
 */
public class First_Unique_Character_in_a_String_387 {
    public static int firstUniqChar(String s) {
        return firstUniqChar_1(s);
    }

    /**
     * 思路：遍历两次。第一次遍历记录出现次数，第二次遍历获取恰好出现一次的第一个字符
     *
     * 参考思路：
     * https://leetcode.com/problems/first-unique-character-in-a-string/solution/
     *
     * 验证通过：
     * Runtime: 24 ms, faster than 38.89% of Java
     * Memory Usage: 39.4 MB, less than 69.21% of Java
     *
     * @param s
     * @return
     */
    public static int firstUniqChar_2(String s) {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            count.put(s.charAt(i), count.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (count.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 采用数组记录出现的下标，第一次出现时记为下标，第二次出现时记为负数，第三次出现时忽略。
     * 最后找出数组中大于0的最小值即可。
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 90.06% of Java.
     * Memory Usage: 45.3 MB, less than 25.12% of Java
     * @param s
     * @return
     */
    public static int firstUniqChar_1(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (count[idx] == 0) {//表示第一次出现
                count[idx] = i + 1;//防止第0个元素被误判，故下标都+1，返回时再-1
            } else if (count[idx] > 0) {//表示第二次出现
                count[idx] *= -1;
            }
        }
        int ret = Integer.MAX_VALUE;
        for (int n : count) {
            if (n > 0) {
                ret = Math.min(ret, n);
            }
        }
        return ret == Integer.MAX_VALUE ? -1 : ret - 1;
    }

    public static void main(String[] args) {
        do_func("leetcode", 0);
        do_func("loveleetcode", 2);
        do_func("aabb", -1);
    }

    private static void do_func(String s, int expected) {
        int ret = firstUniqChar(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
