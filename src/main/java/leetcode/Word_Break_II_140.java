package leetcode;

import java.util.*;

/**
 * 140. Word Break II
 * Hard
 * ----------------------
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.
 *
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 *
 * Example 1:
 * Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * Output: ["cats and dog","cat sand dog"]
 *
 * Example 2:
 * Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: []
 *
 * Constraints:
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 */
public class Word_Break_II_140 {
    /**
     * 思路过程：
     * 1.通过space分割s，表示是有序的，无需考虑结果集的乱序问题。
     *
     * 算法：（与下面的实现由差别，但是思路是一致的。）
     * 1 从左向右匹配s，
     * 2 当s[0:i]在wordDict中时
     * 2.1 s[0:i]加入当前结果
     * 2.2 s=substring(i+1)
     * 2.3 递归1
     * 3 i为末尾时
     * 3.1 如果s[0:i]不在wordDict中，清空当前结果
     * 3.2 如果s[0:i]不在wordDict中，当前结果加入总结果集
     * 4 i++
     *
     * 参考了Word_Break_139中的大神解法。在此基础之上，本题虽然是hard，实际缺失medium。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 97.66% of Java online submissions for Word Break II.
     * Memory Usage: 42.1 MB, less than 61.00% of Java online submissions for Word Break II.
     * @param s
     * @param wordDict
     * @return
     */
    public static List<String> wordBreak(String s, List<String> wordDict) {
        List<String> retList = new ArrayList<>();
        Set<String> wordSet = new HashSet<>(wordDict);
        hepler(s, wordSet, new ArrayList<String>(), retList);
        return retList;
    }

    private static void hepler(String s, Set<String> wordSet, List<String> curList, List<String> retList) {
        if (s == null || s.length() == 0) {
            if (curList.size() > 0) retList.add(toStringWithSpace(curList));
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            String t = s.substring(0, i + 1);
            if (wordSet.contains(t)) {
                curList.add(t);//FIXME 注意递归时，这种入队和出队的操作一定是成对出现的。
                hepler(s.substring(i + 1), wordSet, curList, retList);
                curList.remove(curList.size() - 1);//FIXME 注意递归时，这种入队和出队的操作一定是成对出现的。
            }
        }
    }

    private static String toStringWithSpace(List<String> curList) {
        if (curList == null || curList.size() == 0) return "";
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < curList.size(); i++) {
            ret.append(curList.get(i));
            if (i < curList.size() - 1) ret.append(" ");
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        do_func("catsanddog", new String[]{"cat", "cats", "and", "sand", "dog"}, new String[]{"cats and dog", "cat sand dog"});
        do_func("pineapplepenapple", new String[]{"apple", "pen", "applepen", "pine", "pineapple"}, new String[]{"pine apple pen apple", "pineapple pen apple", "pine applepen apple"});
        do_func("catsandog", new String[]{"cats", "dog", "sand", "and", "cat"}, new String[]{});
    }

    private static void do_func(String s, String[] wordDict, String[] expected) {
        List<String> ret = wordBreak(s, Arrays.asList(wordDict));
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }

}
