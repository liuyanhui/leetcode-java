package leetcode;

import java.util.*;

/**
 * 30. Substring with Concatenation of All Words
 * Hard
 * -----------------------------
 * You are given a string s and an array of strings words. All the strings of words are of the same length.
 *
 * A concatenated substring in s is a substring that contains all the strings of any permutation of words concatenated.
 * - For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated strings. "acdbef" is not a concatenated substring because it is not the concatenation of any permutation of words.
 *
 * Return the starting indices of all the concatenated substrings in s. You can return the answer in any order.
 *
 * Example 1:
 * Input: s = "barfoothefoobarman", words = ["foo","bar"]
 * Output: [0,9]
 * Explanation: Since words.length == 2 and words[i].length == 3, the concatenated substring has to be of length 6.
 * The substring starting at 0 is "barfoo". It is the concatenation of ["bar","foo"] which is a permutation of words.
 * The substring starting at 9 is "foobar". It is the concatenation of ["foo","bar"] which is a permutation of words.
 * The output order does not matter. Returning [9,0] is fine too.
 *
 * Example 2:
 * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * Output: []
 * Explanation: Since words.length == 4 and words[i].length == 4, the concatenated substring has to be of length 16.
 * There is no substring of length 16 in s that is equal to the concatenation of any permutation of words.
 * We return an empty array.
 *
 * Example 3:
 * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * Output: [6,9,12]
 * Explanation: Since words.length == 3 and words[i].length == 3, the concatenated substring has to be of length 9.
 * The substring starting at 6 is "foobarthe". It is the concatenation of ["foo","bar","the"] which is a permutation of words.
 * The substring starting at 9 is "barthefoo". It is the concatenation of ["bar","the","foo"] which is a permutation of words.
 * The substring starting at 12 is "thefoobar". It is the concatenation of ["the","foo","bar"] which is a permutation of words.
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * s and words[i] consist of lowercase English letters.
 */
public class Substring_with_Concatenation_of_All_Words_30 {
    public static List<Integer> findSubstring(String s, String[] words) {
        return findSubstring_1(s, words);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1.navie solution
     * 先穷举words的所有组合，然后依次在s中查找。
     * 穷举后的集合过大，并且会超时。
     * 2.问题简化：words中的每个元素是字母，s中也是字母的组合。
     * 2.1.先计算出s中的每个字母代表的word集合，然后遍历s中的word集合。
     * 3.更优解
     * https://leetcode.com/problems/substring-with-concatenation-of-all-words/solutions/13658/easy-two-map-solution-c-java/
     *
     * 验证通过：
     * Runtime 1869ms Beats 41.12%of users with Java
     * Memory 45.01MB Beats 25.16%of users with Java
     *
     * @param s
     * @param words
     * @return
     */
    public static List<Integer> findSubstring_1(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        //初始化word出现的次数
        Map<String, Integer> cnts = new HashMap<>();
        for (String w : words) {
            cnts.put(w, cnts.getOrDefault(w, 0) + 1);
        }
        //从头开始遍历s
        int step = words[0].length();
        //注意i的范围
        for (int i = 0; i <= s.length() - step * words.length; i++) {
            int j = 0;
            Map<String, Integer> seen = new HashMap<>();
            while (j < words.length) {
                String w = s.substring(i + j * step, i + j * step + step);
                if (seen.getOrDefault(w, 0) >= cnts.getOrDefault(w, 0)) {
                    break;
                }
                seen.put(w, seen.getOrDefault(w, 0) + 1);
                j++;
            }
            if (j == words.length) {
                res.add(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        do_func("barfoothefoobarman", new String[]{"foo", "bar"}, new Integer[]{0, 9});
        do_func("wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}, new Integer[]{});
        do_func("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}, new Integer[]{6, 9, 12});
        do_func("aaaaaaaaaaaaaaaaa", new String[]{"a", "a", "a"}, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});
        do_func("aaaaaaaaaaaaaaaaa", new String[]{"aaa", "aaa", "aaa"}, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8});

    }

    private static void do_func(String s, String[] words, Integer[] expected) {
        List<Integer> ret = findSubstring(s, words);
        System.out.println(ret);
        Arrays.sort(expected);
        ret.sort(Comparator.comparingInt(o -> o));
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}