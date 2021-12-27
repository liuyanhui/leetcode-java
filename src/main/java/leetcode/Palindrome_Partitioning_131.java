package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 131. Palindrome Partitioning
 * Medium
 * -------------------
 * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
 * A palindrome string is a string that reads the same backward as forward.
 *
 * Example 1:
 * Input: s = "aab"
 * Output: [["a","a","b"],["aa","b"]]
 *
 * Example 2:
 * Input: s = "a"
 * Output: [["a"]]
 *
 * Constraints:
 * 1 <= s.length <= 16
 * s contains only lowercase English letters.
 */
public class Palindrome_Partitioning_131 {
    public static List<List<String>> partition(String s) {
        return partition_1(s);
    }

    /**
     * DP+递归
     *
     * 参考思路：
     * https://leetcode.com/problems/palindrome-partitioning/solution/ 之 Approach 2:Backtracking with Dynamic Programming
     * https://leetcode.com/problems/palindrome-partitioning/discuss/41982/Java-DP-%2B-DFS-solution
     * @param s
     * @return
     */
    public static List<List<String>> partition_2(String s) {
        return null;
    }

    /**
     * 递归思路，通过树形分叉可以直观理解
     *
     * 参考思路：
     * https://leetcode.com/problems/palindrome-partitioning/solution/ 之 Approach 1:Backtracking
     *
     * 验证通过：
     * Runtime: 33 ms, faster than 6.15% of Java online submissions for Palindrome Partitioning.
     * Memory Usage: 54.7 MB, less than 26.20% of Java online submissions for Palindrome Partitioning.
     *
     * @param s
     * @return
     */
    public static List<List<String>> partition_1(String s) {
        List<List<String>> ret = new ArrayList<>();
        if (s.equals("")) {
            ret.add(new ArrayList<>());
            return ret;
        }
        for (int i = 1; i <= s.length(); i++) {
            //左半部分作为字符串，并判断该字符串是否为palindrome
            String t = s.substring(0, i);
            if (isPalindrome(t)) {
                //右半部分，递归计算
                List<List<String>> otherRet = partition(s.substring(i, s.length()));
                //合并左右部分的结果
                for (List<String> tor : otherRet) {
                    List<String> tmp = new ArrayList<>();
                    tmp.add(t);
                    tmp.addAll(tor);
                    ret.add(tmp);
                }
            }
        }
        return ret;
    }

    private static boolean isPalindrome(String s) {
        if (s == null || s.equals("")) return false;
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("aab", new String[][]{{"a", "a", "b"}, {"aa", "b"}});
        do_func("aabcd", new String[][]{{"a", "a", "b"}, {"aa", "b"}});
        do_func("a", new String[][]{{"a"}});
        do_func("aa", new String[][]{{"a", "a"}, {"aa"}});
        do_func("aaa", new String[][]{{"a", "a", "a"}, {"a", "aa"}, {"aa", "a"}, {"aaa"}});
        do_func("aaaa", new String[][]{{"a", "a", "a"}, {"a", "aa"}, {"aa", "a"}, {"aaa"}});
//        do_func("aaaaaaaaaaaa", new String[][]{{"a", "a", "a"}, {"a", "aa"}, {"aa", "a"}, {"aaa"}});
    }

    private static void do_func(String s, String[][] expected) {
        List<List<String>> ret = partition(s);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret,expected));
        System.out.println("--------------");
    }
}
