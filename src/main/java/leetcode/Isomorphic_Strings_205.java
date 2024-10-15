package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 205. Isomorphic Strings
 * Easy
 * --------------------------
 * Given two strings s and t, determine if they are isomorphic.
 *
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 *
 * All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.
 *
 * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true
 *
 * Example 2:
 * Input: s = "foo", t = "bar"
 * Output: false
 *
 * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 5 * 10^4
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */
public class Isomorphic_Strings_205 {
    public static boolean isIsomorphic(String s, String t) {
        return isIsomorphic_r3_1(s, t);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 采用字母映射，把s和t中字母进行映射。当不满足映射条件时，返回false。
     * 映射采用HashMap或int[256]
     *
     * review : isIsomorphic_4()时另一种思路
     *
     * 验证通过：
     * Runtime 11 ms Beats 61.67%
     * Memory 42.16 MB Beats 85.09%
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_r3_1(String s, String t) {
        if(s==null || t==null || s.length()!=t.length())
            return false;
        Map<Character,Character> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char c_s = s.charAt(i);
            char t_s = t.charAt(i);
            if(map.containsKey(c_s)){
                if(!map.get(c_s).equals(t_s)) return false;
            }else {
                if(map.values().contains(t_s)) return false;//这里要注意
                map.put(c_s,t_s);
            }
        }
        return true;
    }
    /**
     * review
     * 更巧妙的方案。
     *
     * 参考思路：
     * https://leetcode.com/problems/isomorphic-strings/discuss/57796/My-6-lines-solution
     *
     * 不是直接比较s和t中的字符，并判断他们是否都已经出现并且相同。（这样就需要判断两个条件：1.已经同时出现过；2.映射相同。
     * 而是通过中间值进行比较，中间值就是上一次字符出现时的位置。这样就只需要判断一个条件：1.上一次出现的位置是否相同。
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_4(String s, String t) {
        int[] arr1 = new int[256], arr2 = new int[256];
        for (int i = 0; i < s.length(); i++) {
            if (arr1[s.charAt(i)] != arr2[t.charAt(i)]) return false;
            //这里不能是i，i存在为0的情况。因为0是数组的初始值。如果非要是i，那么需要初始化数组时，把所有元素的值改为-1
            arr1[s.charAt(i)] = i + 1;
            arr2[t.charAt(i)] = i + 1;
        }
        return true;
    }

    /**
     * round 2
     * 两次校验法。两次校验的方向相反。
     *
     * 验证通过：
     * Runtime: 8 ms, faster than 76.84% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 43 MB, less than 52.16% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_3(String s, String t) {
        return check(s, t) && check(t, s);
    }

    private static boolean check(String s, String t) {
        int[] map = new int[256];
        for (int i = 0; i < map.length; i++) {
            map[i] = -1;
        }
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (map[sc] == -1) {
                map[sc] = tc;
            } else if (map[sc] != tc) {
                return false;
            }
        }
        return true;
    }

    /**
     * round 2
     * 思路：
     * 1.使用int[256]数组保存s和t中对应位置字符的映射关系。
     * 2.arr[i]表示s中字符对应t中的字符。如：s[0]='a'，t[0]='c'，那么arr[a]=c。a和c为ascii码中对应的数字。
     * 3.需要对s->t和t->s分别进行校验
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 89.13% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 43.2 MB, less than 43.51% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_2(String s, String t) {
        int[] mapS = new int[256];
        int[] mapT = new int[256];
        for (int i = 0; i < mapS.length; i++) {
            mapS[i] = -1;
            mapT[i] = -1;
        }
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (mapS[sc] == -1) {
                if (mapT[tc] == -1) {
                    mapS[sc] = tc;
                    mapT[tc] = sc;
                } else
                    return false;
            } else if (mapS[sc] != tc) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 61.69% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 39 MB, less than 53.05% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic_1(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if (map.containsKey(sc)) {
                if (map.get(sc) != tc) {
                    return false;
                }
            } else {
                if (map.containsValue(tc)) {
                    return false;
                } else {
                    map.put(sc, tc);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("egg", "add", true);
        do_func("foo", "bar", false);
        do_func("paper", "title", true);
        do_func("egggghhheff", "addddiiiamm", true);
        do_func("ba", "ab", true);
        do_func("badc", "baba", false);
    }

    private static void do_func(String s, String t, boolean expected) {
        boolean ret = isIsomorphic(s, t);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
