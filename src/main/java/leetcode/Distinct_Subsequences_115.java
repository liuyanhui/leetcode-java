package leetcode;

/**
 * 115. Distinct Subsequences
 * Hard
 * -------------------
 * Given two strings s and t, return the number of distinct subsequences of s which equals t.
 *
 * A string's subsequence is a new string formed from the original string by deleting some (can be none) of the characters without disturbing the remaining characters' relative positions. (i.e., "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 *
 * The test cases are generated so that the answer fits on a 32-bit signed integer.
 *
 * Example 1:
 * Input: s = "rabbbit", t = "rabbit"
 * Output: 3
 * Explanation:
 * As shown below, there are 3 ways you can generate "rabbit" from S.
 * rabbbit
 * rabbbit
 * rabbbit
 *
 * Example 2:
 * Input: s = "babgbag", t = "bag"
 * Output: 5
 * Explanation:
 * As shown below, there are 5 ways you can generate "bag" from S.
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 * babgbag
 *
 * Constraints:
 * 1 <= s.length, t.length <= 1000
 * s and t consist of English letters.
 */
public class Distinct_Subsequences_115 {
    public static int numDistinct(String s, String t) {
        return numDistinct_4(s, t);
    }

    /**
     * numDistinct_3()的优化版
     * 验证失败：
     * 第一个用例未通过
     *
     * @param s
     * @param t
     * @return
     */
    public static int numDistinct_4(String s, String t) {
        int[] dp = new int[t.length() + 1];
        dp[t.length()] = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = t.length() - 1; j >= 0; j--) {
                if (s.charAt(i) == t.charAt(j))
                    dp[j] += dp[j + 1];
            }
        }
        return dp[0];
    }

    /**
     * DP思路，其实是DP+递归
     * dp[i,j]表示字符串s[i:]和t[j:]的结果，dp[i,j]的计算存在两种情况，如下：
     * 如果s[i]==t[j] ，那么 dp[i,j]=dp[i+1,j+1]+dp[i+1,j]
     * 如果s[i]!=t[j] ，那么 dp[i,j]=dp[i+1,j]
     * 说明：
     * dp[i+1,j+1]表示s[i+1:]和t[j+1]的结果。当第一个字符相同时，两个字符串出去第一个字符的结果。
     * dp[i+1,j]表示s[i+1:]和t[j]的结果。当第一个字符不同时，s除去第一个字符，再跟t进行匹配的结果。
     *
     * 验证通过：
     * Runtime: 27 ms, faster than 55.28% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 50.4 MB, less than 25.91% of Java online submissions for Distinct Subsequences.
     *
     * @param s
     * @param t
     * @return
     */
    public static int numDistinct_3(String s, String t) {
        int[][] dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i][t.length()] = 1;
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = t.length() - 1; j >= 0; j--) {
                if (s.charAt(i) == t.charAt(j))
                    dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j];
                else
                    dp[i][j] = dp[i + 1][j];
            }
        }
        return dp[0][0];
    }

    /**
     * numDistinct_1()的优化版，加入了缓存
     *
     * 验证通过：
     * Runtime: 2374 ms, faster than 5.03% of Java online submissions for Distinct Subsequences.
     * Memory Usage: 49.3 MB, less than 64.29% of Java online submissions for Distinct Subsequences.
     *
     * @param s
     * @param t
     * @return
     */
    public static int numDistinct_2(String s, String t) {
        int[][] cached = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {
                cached[i][j] = -1;
            }
        }
        int rs = helper(s, 0, t, 0, cached);
        return rs;
    }

    public static int helper(String s, int begs, String t, int begt, int[][] cached) {
        if (begt >= t.length()) {
            return 1;
        }
        if (begs >= s.length()) {
            return 0;
        }
        if (cached[begs][begt] >= 0) return cached[begs][begt];
        int ret = 0;
        for (int i = begs; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(begt)) {
                ret += helper(s, i + 1, t, begt + 1, cached);
            }
        }
        cached[begs][begt] = ret;
        return ret;
    }

    /**
     * 递归思路
     * 因为字符在字符串的位置是不变的，所以每个字符的匹配是单向的。
     * 1.从左到右匹配，如果第一个字符已经匹配，那么对剩下的字符进行递归匹配
     *
     * 逻辑正确，验证失败。原因：Time Limit Exceeded
     *
     * @param s
     * @param t
     * @return
     */
    public static int numDistinct_1(String s, String t) {
        if (s.length() < t.length()) return 0;
        if (s.equals(t) || t.equals("")) return 1;
        int ret = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == t.charAt(0))
                ret += numDistinct(s.substring(i + 1), t.substring(1));
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("rabbbit", "rabbit", 3);
//        do_func("babgbag", "bag", 5);
//        do_func("b", "b", 1);
//        do_func("bbb", "b", 3);
//        do_func("zxc", "ujm", 0);
    }

    private static void do_func(String word1, String word2, int expected) {
        int ret = numDistinct(word1, word2);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
