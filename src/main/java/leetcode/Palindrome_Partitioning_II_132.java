package leetcode;

/**
 * 132. Palindrome Partitioning II
 * Hard
 * -------------------------
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * Return the minimum cuts needed for a palindrome partitioning of s.
 *
 * Example 1:
 * Input: s = "aab"
 * Output: 1
 * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
 *
 * Example 2:
 * Input: s = "a"
 * Output: 0
 *
 * Example 3:
 * Input: s = "ab"
 * Output: 1
 *
 * Constraints:
 * 1 <= s.length <= 2000
 * s consists of lowercase English letters only.
 */
public class Palindrome_Partitioning_II_132 {
    public static int minCut(String s) {
        return minCut_2(s);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/palindrome-partitioning-ii/discuss/42213/Easiest-Java-DP-Solution-(97.36)
     *
     * 思路如下：
     * dp[i] is the minCut for s[0:i]
     * This can be solved by two points:
     * dp[i] is the minimum of dp[j - 1] + 1 (j <= i), if s[j, i] is palindrome.
     * If s[j:i] is palindrome, Then s[j + 1:i - 1] is palindrome, and s[j] == s[i].
     *
     * 验证通过：
     * Runtime: 38 ms, faster than 66.49% of Java online submissions for Palindrome Partitioning II.
     * Memory Usage: 48.5 MB, less than 49.74% of Java online submissions for Palindrome Partitioning II.
     *
     * @param s
     * @return
     */
    public static int minCut_2(String s) {
        int[] dp = new int[s.length()];
        boolean[][] pal = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i] = i;
            int min = i;
            for (int j = 0; j <= i; j++) {
                //下面的"j + 1 > i - 1"是为了限制后面的pal[j + 1][i - 1]，防止出现j+1>i-1的情况。
                if (s.charAt(j) == s.charAt(i) && (j + 1 > i - 1 || pal[j + 1][i - 1])) {
                    //计算子串是否为palindrome
                    pal[j][i] = true;
                    //j=0时，j-1小于0，需要单独判断
                    min = j == 0 ? 0 : Math.min(min, dp[j - 1] + 1);
                }
            }
            dp[i] = min;
        }
        return dp[s.length() - 1];
    }

    /**
     * 思路：
     * 因为只需要计算最小值，不需要输出所有的palindrome
     * 递归+DP
     * dp[i][j]为从i到j的minCut
     * dp[k][k]=0
     * if s[i:j] is palindrome
     *     dp[i][j]=0
     * else
     *     dp[i][j]=min(dp[i][k]+dp[k+1][j])+1, i<=k<j
     * 沿对角线遍历，并计算dp[][]
     *
     * 验证失败： 28 / 36 test cases passed.
     * Time Limit Exceeded
     *
     * @param s
     * @return
     */
    public static int minCut_1(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j + i < s.length(); j++) {
                if (!isPalindrome(s, j, j + i)) {
                    int min = Integer.MAX_VALUE;
                    //计算dp[j][j+i]
                    for (int k = j; k < j + i; k++) {
                        min = Math.min(min, dp[j][k] + dp[k + 1][j + i]);
                    }
                    dp[j][j + i] = min + 1;
                }
            }
        }
        return dp[0][s.length() - 1];
    }

    private static boolean isPalindrome(String s, int beg, int end) {
        if (beg > end) return false;
        while (beg < end) {
            if (s.charAt(beg) != s.charAt(end)) return false;
            beg++;
            end--;
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("aab", 1);
        do_func("a", 0);
        do_func("ab", 1);
        do_func("aaaaaaa", 0);
        do_func("every", 2);
        do_func("cdd", 1);
        do_func("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 1);
    }

    private static void do_func(String s, int expected) {
        int ret = minCut(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
