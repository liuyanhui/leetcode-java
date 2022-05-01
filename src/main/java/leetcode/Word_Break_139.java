package leetcode;

import java.util.*;

/**
 * 139. Word Break
 * Medium
 * -----------------
 * Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 *
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet","code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple","pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 * Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: false
 *
 * Constraints:
 * 1 <= s.length <= 300
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 20
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 */
public class Word_Break_139 {

    public static boolean wordBreak(String s, List<String> wordDict) {
        return wordBreak_5(s, wordDict);
    }

    static Map<String, Boolean> map = new HashMap<>();

    /**
     * round 2
     * wordBreak_4()的优化版，增加了缓存防止重复计算
     *
     * 验证通过：
     * Runtime: 10 ms, faster than 54.52% of Java online submissions for Word Break.
     * Memory Usage: 44.7 MB, less than 51.36% of Java online submissions for Word Break.
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak_5(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) return true;
        if (map.containsKey(s)) return map.get(s);
        List<String> candidate = new ArrayList<>(wordDict);
        for (int i = 0; i < s.length(); i++) {
            Iterator<String> it = candidate.iterator();
            while (it.hasNext()) {
                String c = it.next();
                if (i < c.length() && s.charAt(i) != c.charAt(i)) {
                    it.remove();
                    continue;
                }
                if (i + 1 == c.length()) {
                    map.put(s, true);
                    if(wordBreak(s.substring(i + 1), wordDict)){
                        return true;
                    }
                }
            }
        }
        map.put(s, false);
        return false;
    }

    /**
     * round 2
     *
     * 递归思路：
     * 1.s中根据字符依次匹配dict。前缀匹配dict中的元素，如果匹配加入list，不满足匹配的移出list（或跳过list的该数据项）。
     * 2.当dict中的单词完全匹配时，递归执行1。
     * 3.s到达末尾时，终止计算
     *
     * 验证失败：逻辑无误
     * Time Limit Exceeded
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak_4(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) return true;
        List<String> candidate = new ArrayList<>(wordDict);
        for (int i = 0; i < s.length(); i++) {
            Iterator<String> it = candidate.iterator();
            while (it.hasNext()) {
                String c = it.next();
                if (i < c.length() && s.charAt(i) != c.charAt(i)) {
                    it.remove();
                    continue;
                }
                if (i + 1 == c.length() && wordBreak(s.substring(i + 1), wordDict)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * DP 思路，公式如下：
     * 1.DP[i]表示从0到i是否满足wordBreak条件，则DP[s.length()-1]为所求
     * 2.DP[i]的计算可以分割为两部分，[0:j)和[j:i+1)，如果两部分都为true那么DP[i]=true。其中[0:j)的值是dp[j]，是已经计算过的。所以每个dp[i]可以分为已计算和未计算两个部分。
     * 3.DP[i] = { 遍历 j 从 0 到 i-1，只要存在 j 使得 DP[j] && dict.contains(s.substring(j,i)) 为true,则DP[i]==true }
     * 4.返回DP[s.length()-1]
     *
     * 参考思路：
     * https://leetcode.com/problems/word-break/discuss/44054/Java-DP-solution
     * https://leetcode.com/problems/word-break/discuss/43790/Java-implementation-using-DP-in-two-ways
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 64.86% of Java online submissions for Word Break.
     * Memory Usage: 39.8 MB, less than 21.66% of Java online submissions for Word Break.
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak_3(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) return false;

        Set<String> dict = new HashSet<>(wordDict);

        // dp[i] represents whether s[0...i] can be formed by dict
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i + 1))) {
                    dp[i + 1] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/word-break/discuss/169383/solved-The-Time-Complexity-of-The-Brute-Force-Method-Should-Be-O(2n)-and-Prove-It-Below
     *
     * 验证结果：Time Limit Exceeded
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak_2(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        return do_recursive_2(s, set);
    }

    private static boolean do_recursive_2(String s, Set<String> dict) {
        if (s == null || s.length() == 0) return true;
        for (int i = 1; i <= s.length(); i++) {
            String t = s.substring(0, i);
            //这里有问题，导致部分用例失败
            if (dict.contains(t) && do_recursive_2(s.substring(i), dict)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 递归思路,
     * 验证失败，无法验证以下用例：
     * s="aaaaaaa" wordDict = ["aaaa","aaa"]
     *
     * @param s
     * @param wordDict
     * @return
     */
    public static boolean wordBreak_1(String s, List<String> wordDict) {
        Set<String> dictSet = new HashSet<>(wordDict);
        return do_recursive(s, dictSet);
    }

    private static boolean do_recursive(String s, Set<String> dict) {
        if (s == null || s.length() == 0) return true;
        for (int i = 1; i <= s.length(); i++) {
            String t = s.substring(0, i);
            //这里有问题，导致部分用例失败
            if (dict.contains(t)) {
                return do_recursive(s.substring(i), dict);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func("leetcode", new String[]{"leet", "code"}, true);
        do_func("applepenapple", new String[]{"apple", "pen"}, true);
        do_func("catsandog", new String[]{"cats", "dog", "sand", "and", "cat"}, false);
        do_func("aaaaaaa", new String[]{"aaaa", "aaa"}, true);
        do_func("a", new String[]{"b"}, false);
        do_func("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab", new String[]{"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"}, false);

    }

    private static void do_func(String s, String[] wordDict, boolean expected) {
        boolean ret = wordBreak(s, Arrays.asList(wordDict));
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
