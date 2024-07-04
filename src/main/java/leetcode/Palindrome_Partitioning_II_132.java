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
        return minCut_r3_3(s);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * 本方法是minCut_2()的思路
     *
     * 验证通过：
     * Runtime 25 ms Beats 92.72%
     * Memory 46.77 MB Beats 29.92%
     *
     * @param s
     * @return
     */
    public static int minCut_r3_3(String s) {
        if (s == null || s.length() <= 1) return 0;
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i] = i - 1;
        }
        boolean[][] pal = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i) && (j + 1 >= i - 1 || pal[j + 1][i - 1])) {
                    pal[j][i] = true;
                    dp[i + 1] = Math.min(dp[i + 1], dp[j] + 1);
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. DFS+递归+缓存。
     * 2. DP。递归是自顶向下，DP是自底向上。
     * dp[i]是s[i:~]的最优解
     * dp[i]= 算法如下：
     * BEG
     * set n = len(s)
     * FOR j = [i to n]:
     * 	IF s[i:j] is palindrome THEN dp[i]=min(dp[i],1+dp[j+1])
     * END
     *
     * minCut_r3_1() 的耗时优化版，但是没有优化
     *
     * minCut_2()和minCut_3()的实现更优
     *
     * 验证通过：性能和minCut_r3_1()差别不大
     *
     * @param s
     * @return
     */
    public static int minCut_r3_2(String s) {
        if (s == null || s.length() <= 1) return 0;
        int[][] palindrome_cache = new int[s.length()][s.length()];
        int dp[] = new int[s.length() + 1];
        dp[s.length()] = -1;
        for (int i = s.length() - 2; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;//review 可以优化为：初始化为i-1
            for (int j = s.length() - 1; j >= i; j--) {
                if (palindrome_cache[i][j] == -1) continue;
                if (palindrome_cache[i][j] == 1 || isPalindrome_r3_1(s, i, j)) {//review 这里可以优化 minCut_2()
                    palindrome_cache[i][j] = 1;
                    dp[i] = Math.min(dp[i], 1 + dp[j + 1]);
                } else {
                    palindrome_cache[i][j] = -1;
                }
            }
        }
        return dp[0];
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking:
     * 1. DFS+递归+缓存。
     *
     * 验证通过：时间性能较差
     * Runtime 2741 ms Beats 8.40%
     * Memory 40.96 MB Beats 94.57%
     *
     * @param s
     * @return
     */
    public static int minCut_r3_1(String s) {
        if (s == null || s.length() <= 1) return 0;
        int[] cache = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            cache[i] = -1;
        }
        helper_r3_1(s, cache, 0);
        return cache[0];
    }

    private static int helper_r3_1(String s, int[] cache, int beg) {
        if (beg >= s.length()) return -1;//如果已经到了末尾，回退cut
        if (cache[beg] >= 0) return cache[beg];

        int ret = Integer.MAX_VALUE;//review 可以优化为：初始化为i-1
        for (int i = beg; i < s.length(); i++) {
            if (isPalindrome_r3_1(s, beg, i)) {//遇到palindrome,cut加1；末尾是特殊情况
                ret = Math.min(ret, 1 + helper_r3_1(s, cache, i + 1));
            }
        }
        cache[beg] = ret;
        return ret;
    }

    private static boolean isPalindrome_r3_1(String s, int beg, int end) {
        if (beg > end) return false;
        while (beg < end) {
            if (s.charAt(beg) != s.charAt(end)) return false;
            beg++;
            end--;
        }
        return true;
    }


    /**
     * 参考思路：
     * https://leetcode.com/problems/palindrome-partitioning-ii/discuss/42198/My-solution-does-not-need-a-table-for-palindrome-is-it-right-It-uses-only-O(n)-space.
     *
     * 很难理解
     * DP思路：
     * 1.dp[i]为s[0:i]的最优解
     * 2.奇数字符的palindrome情况。以s[i]为中心的palindrome为s[i-j:i+j]，那么dp[i]=dp[i-j-1]+1，dp[i+j+1]=dp[i-j-1]+1
     * 3.偶数字符的palindrome情况。以s[i]和s[i+1]为中心的palindrome为s[i-j:i+1+j]，那么dp[i]=dp[i-j-1]+1
     *
     * @param s
     * @return
     */
    public static int minCut_3(String s) {
        int[] dp = new int[s.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i] = i - 1;
        }
        for (int i = 0; i < s.length(); i++) {
            //奇数palindrome
            for (int j = 0; 0 <= i - j && i + j < s.length() && s.charAt(i - j) == s.charAt(i + j); j++) {
                //只能更新palindrome最右端的dp元素，因为最左侧的元素的值已经确定，如果更新会发生错误。
                //每次j的循环都会重新计算右端的满足条件的dp中的值。因为随着参加计算的字符增加dp的值是变化的。
                dp[i + j + 1] = Math.min(dp[i + j + 1], dp[i - j] + 1);
            }
            //偶数palindrome
            for (int j = 1; 0 <= i - j + 1 && i + j < s.length() && s.charAt(i - j + 1) == s.charAt(i + j); j++) {
                //只能更新palindrome最右端的dp元素，因为最左侧的元素的值已经确定，如果更新会发生错误。
                dp[i + j + 1] = Math.min(dp[i + j + 1], dp[i - j + 1] + 1);
            }
        }
        return dp[s.length()];
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/palindrome-partitioning-ii/discuss/42213/Easiest-Java-DP-Solution-(97.36)
     * https://leetcode.com/problems/palindrome-partitioning-ii/discuss/42199/My-DP-Solution-(-explanation-and-code)
     *
     * 思路如下：
     * dp[i] is the minCut for s[0:i]
     * This can be solved by two points:
     * 1.dp[i] is the minimum of dp[j - 1] + 1 (j <= i), if s[j, i] is palindrome.
     * 2.If s[j:i] is palindrome, Then s[j + 1:i - 1] is palindrome, and s[j] == s[i].
     *
     * ME:论思路清晰的重要性。
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
        boolean[][] pal = new boolean[s.length()][s.length()];//review 保存是否两两元素是否为palindrome
        for (int i = 0; i < s.length(); i++) {
            dp[i] = i;
            int min = i;
            for (int j = 0; j <= i; j++) {
                //下面的if语句和第一行语句是注释思路中的第1步
                //下面的"j + 1 > i - 1"是为了限制后面的pal[j + 1][i - 1]，防止出现j+1>i-1的情况。
                if (s.charAt(j) == s.charAt(i) && (j + 1 > i - 1 || pal[j + 1][i - 1])) {
                    //计算子串是否为palindrome
                    pal[j][i] = true;
                    //下面是注释思路中的第2步
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
        do_func("aaaaaaab", 1);
        do_func("caaaaaaa", 1);
        do_func("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 1);
    }

    private static void do_func(String s, int expected) {
        int ret = minCut(s);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
