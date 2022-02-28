package leetcode;

/**
 * 97. Interleaving String
 * Medium
 * ------------------------------
 * Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.
 *
 * An interleaving of two strings s and t is a configuration where they are divided into non-empty substrings such that:
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
 *
 * Note: a + b is the concatenation of strings a and b.
 *
 * Example 1:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * Output: true
 *
 * Example 2:
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * Output: false
 *
 * Example 3:
 * Input: s1 = "", s2 = "", s3 = ""
 * Output: true
 *
 * Constraints:
 * 0 <= s1.length, s2.length <= 100
 * 0 <= s3.length <= 200
 * s1, s2, and s3 consist of lowercase English letters.
 *
 * Follow up: Could you solve it using only O(s2.length) additional memory space?
 */
public class Interleaving_String_97 {

    public static boolean isInterleave(String s1, String s2, String s3) {
        return isInterleave_3(s1, s2, s3);
    }

    /**
     * 1维DP数组法，在二维DP基础上优化
     *
     * Space Complexity:O(s2.length)
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 61.97% of Java online submissions for Interleaving String.
     * Memory Usage: 42.4 MB, less than 29.02% of Java online submissions for Interleaving String.
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static boolean isInterleave_3(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        int[] dp = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) {
                    dp[0] = 1;
                } else if (j > 0 && dp[j - 1] == 1 && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                    dp[j] = 1;
                } else if (i > 0 && dp[j] == 1 && s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
                    dp[j] = 1;
                } else {
                    dp[j] = 0;
                }
            }
        }
        return dp[s2.length()] == 1;
    }

    /**
     * 2维DP数组法
     *
     * 公式如下：
     * 初始化：dp[m+1][n+1]
     * 初始化：dp[0][0]=1
     * dp[i][j]=if dp[i-1][j]==1 AND s1[i]==s3[i+j] then dp[i][j]=1
     *          if dp[i][j-1]==1 AND s2[j]==s3[i+j] then dp[i][j]=1
     *
     * dp[i][j]=if dp[i-1][j] AND s1[i-1]==s3[i+j-1] then dp[i][j]=1
     *          if dp[i][j-1] AND s2[j-1]==s3[i+j-1] then dp[i][j]=1
     *
     * 参考思路：
     * https://leetcode.com/problems/interleaving-string/solution/
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 68.93% of Java online submissions for Interleaving String.
     * Memory Usage: 42.8 MB, less than 16.16% of Java online submissions for Interleaving String.
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static boolean isInterleave_2(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        dp[0][0] = 1;
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i > 0 && dp[i - 1][j] == 1 && s1.charAt(i - 1) == s3.charAt(i + j - 1)) {
                    dp[i][j] = 1;
                } else if (j > 0 && dp[i][j - 1] == 1 && s2.charAt(j - 1) == s3.charAt(i + j - 1)) {
                    dp[i][j] = 1;
                }
            }
        }
        return dp[s1.length()][s2.length()] == 1;
    }

    /**
     * 递归法+缓存法：每次比较一个字符，然后递归。
     * 思路如下：
     * 如果s1[0]==s3[0] , 递归s1[1:],s2,s3[1:]
     * 如果s2[0]==s3[0] , 递归s1,s2[1:],s3[1:]
     *
     * 伪代码如下：
     * func(s1,s2,s3){
     *     if(s1 is empty && s2 is empty && s3 is empty) return true
     *     if (s1[0]==s3[0]) {
     *         if(func(s1[1:],s2,s3[1:])){
     *             return true
     *         }
     *     }
     *     if(s2[0]==s3[0]){
     *         if(func(s1,s2[1:],s3[1:])){
     *             return true
     *         }
     *     }
     *     return false;
     * }
     * 需要使用缓存保存已经计算过的部分，否则会超时。
     *
     * Time Complexity:O(M*N)
     * Space Complexity:O(M*N)
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 96.93% of Java online submissions for Interleaving String.
     * Memory Usage: 42.2 MB, less than 36.69% of Java online submissions for Interleaving String.
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public static boolean isInterleave_1(String s1, String s2, String s3) {
        int[][] cached = new int[s1.length() + 1][s2.length() + 1];

        return do_isInterleave_1(s1, s1.length(), s2, s2.length(), s3, cached);
    }

    private static boolean do_isInterleave_1(String s1, int l1, String s2, int l2, String s3, int[][] cached) {
        if (s1 == null && s2 == null && s3 == null) return true;
        if (s1.length() == 0 && s2.length() == 0 && s3.length() == 0) return true;
        if (s1.length() + s2.length() != s3.length()) return false;

        int r = l1 - s1.length(), c = l2 - s2.length();
        //如果已经计算过，直接返回结果
        if (cached[r][c] != 0) return cached[r][c] == 1;

        if (s1.length() != 0) {
            if (s1.charAt(0) == s3.charAt(0) && do_isInterleave_1(s1.substring(1), l1, s2, l2, s3.substring(1), cached)) {
                cached[r][c] = 1;
                return true;
            }
        }
        if (s2.length() != 0) {
            if (s2.charAt(0) == s3.charAt(0) && do_isInterleave_1(s1, l1, s2.substring(1), l2, s3.substring(1), cached)) {
                cached[r][c] = 1;
                return true;
            }
        }
        cached[r][c] = -1;
        return false;
    }

    public static void main(String[] args) {
        do_func("aabcc", "dbbca", "aadbbcbcac", true);
        do_func("aabcc", "dbbca", "aadbbbaccc", false);
        do_func("", "", "", true);
        do_func("aaa", "aaa", "aaaaaa", true);
        do_func("aabaac", "aadaaeaaf", "aadaaeaabaafaac", true);
        do_func("", "aaa", "aaa", true);
        do_func("bbbbbabbbbabaababaaaabbababbaaabbabbaaabaaaaababbbababbbbbabbbbababbabaabababbbaabababababbbaaababaa", "babaaaabbababbbabbbbaabaabbaabbbbaabaaabaababaaaabaaabbaaabaaaabaabaabbbbbbbbbbbabaaabbababbabbabaab", "babbbabbbaaabbababbbbababaabbabaabaaabbbbabbbaaabbbaaaaabbbbaabbaaabababbaaaaaabababbababaababbababbbababbbbaaaabaabbabbaaaaabbabbaaaabbbaabaaabaababaababbaaabbbbbabbbbaabbabaabbbbabaaabbababbabbabbab", false);
        do_func("", "", "a", false);
    }

    private static void do_func(String s1, String s2, String s3, boolean expected) {
        boolean ret = isInterleave(s1, s2, s3);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
