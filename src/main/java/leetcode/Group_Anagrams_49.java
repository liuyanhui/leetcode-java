package leetcode;

import java.util.*;

/**
 * 49. Group Anagrams
 * Medium
 * -------------------------------
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 *
 * Example 1:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * Example 2:
 * Input: strs = [""]
 * Output: [[""]]
 *
 * Example 3:
 * Input: strs = ["a"]
 * Output: [["a"]]
 *
 * Constraints:
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 */
public class Group_Anagrams_49 {
    /**
     * 1.提取每个字符串的特征值。特征值是int[26]，表示26个字母出现的次数。或者特征值是字母序的字符串。
     * 2.根据特征值分组即可。
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 49.12% of Java online submissions for Group Anagrams.
     * Memory Usage: 42.8 MB, less than 41.81% of Java online submissions for Group Anagrams.
     *
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> featureMap = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            //特征值是字典序重排后的字符串
            String sorted = sort_3(strs[i]);
            //根据特征值分组
            featureMap.computeIfAbsent(sorted, v -> new ArrayList<String>());
            featureMap.get(sorted).add(strs[i]);
        }
        return new ArrayList<>(featureMap.values());
    }

    /**
     * 这个排序更优
     * @param s
     * @return
     */
    private static String sort_3(String s) {
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        return String.valueOf(ch);
    }

    /**
     * 这个排序更优
     * 验证通过：
     * Runtime: 6 ms, faster than 81.15% of Java online submissions for Group Anagrams.
     * Memory Usage: 42.8 MB, less than 43.74% of Java online submissions for Group Anagrams.
     *
     * @param s
     * @return
     */
    private static String sort_2(String s) {
        char[] ch = new char[26];
        for (int i = 0; i < s.length(); i++) {
            ch[s.charAt(i) - 'a']++;
        }
        return new String(ch);
    }

    /**
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 49.12% of Java online submissions for Group Anagrams.
     * Memory Usage: 42.8 MB, less than 41.81% of Java online submissions for Group Anagrams.
     *
     * @param s
     * @return
     */
    private static String sort(String s) {
        StringBuilder ret = new StringBuilder();
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            while (count[i] > 0) {
                ret.append(String.valueOf((char) ('a' + i)));
                count[i]--;
            }
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        do_func(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}, new String[][]{{"bat"}, {"nat", "tan"}, {"ate", "eat", "tea"}});
        do_func(new String[]{""}, new String[][]{{""}});
        do_func(new String[]{"a"}, new String[][]{{"a"}});
        do_func(new String[]{"hhaa", "ahha", "haah", "see"}, new String[][]{{"hhaa", "ahha", "haah"}, {"see"}});
    }

    private static void do_func(String[] strs, String[][] expected) {
        List<List<String>> ret = groupAnagrams(strs);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret,expected));
        System.out.println("--------------");
    }
}
