package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 336. Palindrome Pairs
 * Hard
 * -------------------------------
 * You are given a 0-indexed array of unique strings words.
 *
 * A palindrome pair is a pair of integers (i, j) such that:
 *  0 <= i, j < words.length,
 *  i != j, and
 *  words[i] + words[j] (the concatenation of the two strings) is a palindrome.
 *
 * Return an array of all the palindrome pairs of words.
 *
 * Example 1:
 * Input: words = ["abcd","dcba","lls","s","sssll"]
 * Output: [[0,1}, {1,0}, {3,2}, {2,4]]
 * Explanation: The palindromes are ["abcddcba","dcbaabcd","slls","llssssll"]
 *
 * Example 2:
 * Input: words = ["bat","tab","cat"]
 * Output: [[0,1}, {1,0]]
 * Explanation: The palindromes are ["battab","tabbat"]
 *
 * Example 3:
 * Input: words = ["a",""]
 * Output: [[0,1}, {1,0]]
 * Explanation: The palindromes are ["a","a"]
 *
 * Constraints:
 * 1 <= words.length <= 5000
 * 0 <= words[i].length <= 300
 * words[i] consists of lowercase English letters.
 */
public class Palindrome_Pairs_336 {
    public static List<List<Integer>> palindromePairs(String[] words) {
        return palindromePairs_1(words);
    }

    /**
     * review
     * 有两种不同的思路：
     * 1.字符串截取、反转、hashtable比较思路
     * https://leetcode.com/problems/palindrome-pairs/solutions/79199/150-ms-45-lines-java-solution/
     * 2.Trie思路
     * https://leetcode.com/problems/palindrome-pairs/solutions/79195/o-n-k-2-java-solution-with-trie-structure/
     *
     */

    /**
     *
     * 1.字符串截取、反转、hashtable比较思路
     * https://leetcode.com/problems/palindrome-pairs/solutions/79199/150-ms-45-lines-java-solution/
     *
     * 1.采用hashtable保存：key为反转后的word；value为word的index，便于后续的查找。
     * 2.遍历words中的每个word。
     * 3.针对每个word分割成两部分，word[:i]和word[i:]。如果word[:i]是palindrome，且word[i:]在hashtable中存在，那么表示存在一个满足条件的结果；如果如果word[i:]是palindrome，且word[:i]在hashtable中存在，那么表示存在一个满足条件的结果
     *
     * 验证通过：
     * Runtime 1598 ms Beats 43.61%
     * Memory 59.1 MB Beats 33.4%
     *
     * @param words
     * @return
     */
    public static List<List<Integer>> palindromePairs_1(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        if (words == null || words.length < 2)
            return res;
        //题目中已经明确words中的word是不重复的unique
        Map<String, Integer> reverseMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            reverseMap.put(reverse(words[i]), i);
        }

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j <= words[i].length(); j++) {
                String headHalf = words[i].substring(0, j);
                String tailHalf = words[i].substring(j);

                if (isPalindrome(headHalf) && reverseMap.containsKey(tailHalf) && i != reverseMap.get(tailHalf)) {
                    List<Integer> t = new ArrayList<>();
                    t.add(reverseMap.get(tailHalf));
                    t.add(i);
                    res.add(t);
                }
                //tailHalf.length() != 0 是为了防止重复计算
                if (isPalindrome(tailHalf) && tailHalf.length() != 0
                        && reverseMap.containsKey(headHalf) && i != reverseMap.get(headHalf)) {
                    List<Integer> t = new ArrayList<>();
                    t.add(i);
                    t.add(reverseMap.get(headHalf));
                    res.add(t);
                }
            }
        }

        return res;
    }

    private static String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    private static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func(new String[]{"abcd", "dcba", "lls", "s", "sssll"}, new int[][]{{0, 1}, {1, 0}, {3, 2}, {2, 4}});
        do_func(new String[]{"bat", "tab", "cat"}, new int[][]{{0, 1}, {1, 0}});
        do_func(new String[]{"a", ""}, new int[][]{{0, 1}, {1, 0}});
        do_func(new String[]{"abc", ""}, new int[][]{});
        do_func(new String[]{"a"}, new int[][]{});
        do_func(new String[]{""}, new int[][]{});
        do_func(new String[]{"a", "aa"}, new int[][]{{0, 1}, {1, 0}});
        do_func(new String[]{"a", "b", "c", "ab", "ac", "aa"}, new int[][]{{3, 0}, {1, 3}, {4, 0}, {2, 4}, {5, 0}, {0, 5}});
    }

    private static void do_func(String[] words, int[][] expected) {
        List<List<Integer>> ret = palindromePairs(words);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
