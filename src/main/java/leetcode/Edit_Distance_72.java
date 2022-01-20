package leetcode;

/**
 * 72. Edit Distance
 * Hard
 * -----------------------------
 * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
 *
 * You have the following three operations permitted on a word:
 * Insert a character
 * Delete a character
 * Replace a character
 *
 * Example 1:
 * Input: word1 = "horse", word2 = "ros"
 * Output: 3
 * Explanation:
 * horse -> rorse (replace 'h' with 'r')
 * rorse -> rose (remove 'r')
 * rose -> ros (remove 'e')
 *
 * Example 2:
 * Input: word1 = "intention", word2 = "execution"
 * Output: 5
 * Explanation:
 * intention -> inention (remove 't')
 * inention -> enention (replace 'i' with 'e')
 * enention -> exention (replace 'n' with 'x')
 * exention -> exection (replace 'n' with 'c')
 * exection -> execution (insert 'u')
 *
 * Constraints:
 * 0 <= word1.length, word2.length <= 500
 * word1 and word2 consist of lowercase English letters.
 */
public class Edit_Distance_72 {
    public static int minDistance(String word1, String word2) {
        return minDistance_2(word1, word2);
    }

    /**
     * minDistance_1的优化版本，使用迭代法替换递归
     * 从minDistance_1中可以总结出
     * 如果word1[i]!=word2[j] 那么 dp[i][j]=1+min(dp[i],[j+1],dp[i+1],[j],dp[i+1],[j+1])
     * 如果word1[i]==word2[j] 那么 dp[i][j]=dp[i+1],[j+1]
     * dp[i][j] 表示表示word1[i:]和word2[j:]的字符串的操作结果
     * 初始化为：
     * dp[i][word2.length()]=word1.length()-i
     * dp[word1.length()][j]=word2.length()-j
     * dp[word1.length()][word2.length()]=0
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 93.52% of Java online submissions for Edit Distance.
     * Memory Usage: 38.9 MB, less than 77.38% of Java online submissions for Edit Distance.
     *
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance_2(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][word2.length()] = word1.length() - i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[word1.length()][j] = word2.length() - j;
        }
        for (int i = word1.length() - 1; i >= 0; i--) {
            for (int j = word2.length() - 1; j >= 0; j--) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i + 1][j + 1], Math.min(dp[i + 1][j], dp[i][j + 1]));
                }
            }
        }

        return dp[0][0];
    }

    /**
     * review
     *
     * 参考资料：
     * https://leetcode.com/problems/edit-distance/discuss/159295/Python-solutions-and-intuition
     *
     * 基础算法如下：
     * IF word1.length()==0 THEN return word2.length()
     * IF word2.length()==0 THEN return word1.length()
     * IF word1[i]==word2[j] THEN func(word1[i+1:],word2[i+1:])
     * ELSE
     *     insertDistance = 1+func(word1[i:],word2[i+1:])
     *     deleteDistance = 1+func(word1[i+1:],word2[i:])
     *     replaceDistance = 1+func(word1[i+1:],word2[i+1:])
     *     return min(insertDistance,deleteDistance,replaceDistance)
     *
     * 在基础算法的基础上采用递归+DP思路，即用二维数组缓存中间计算结果，防止重复计算。
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 51.53% of Java online submissions for Edit Distance.
     * Memory Usage: 41.9 MB, less than 12.28% of Java online submissions for Edit Distance.
     *
     * @param word1
     * @param word2
     * @return
     */
    public static int minDistance_1(String word1, String word2) {
        //mem[i][j] 表示word1[i:]和word2[j:]的字符串的操作结果
        int[][] mem = new int[word1.length() + 1][word2.length() + 1];
        do_recursive(word1, 0, word2, 0, mem);
        return mem[0][0];
    }

    private static int do_recursive(String word1, int beg1, String word2, int beg2, int[][] mem) {
        if (mem[beg1][beg2] > 0) return mem[beg1][beg2];
        if (beg1 >= word1.length()) {
            mem[beg1][beg2] = word2.length() - beg2;
            return mem[beg1][beg2];
        }
        if (beg2 >= word2.length()) {
            mem[beg1][beg2] = word1.length() - beg1;
            return mem[beg1][beg2];
        }
        if (word1.charAt(beg1) == word2.charAt(beg2)) {
            mem[beg1][beg2] = do_recursive(word1, beg1 + 1, word2, beg2 + 1, mem);
        } else {
            int insert = do_recursive(word1, beg1, word2, beg2 + 1, mem);
            int delete = do_recursive(word1, beg1 + 1, word2, beg2, mem);
            int replace = do_recursive(word1, beg1 + 1, word2, beg2 + 1, mem);

            mem[beg1][beg2] = 1 + Math.min(insert, Math.min(delete, replace));
        }

        return mem[beg1][beg2];
    }

    public static void main(String[] args) {
        do_func("horse", "ros", 3);
        do_func("intention", "execution", 5);
        do_func("", "", 0);
        do_func("horse", "", 5);
        do_func("", "ros", 3);
        do_func("qwertyuio", "asdfghjkl", 9);
        do_func("abcdef", "efghij", 6);
        do_func("abcdef", "xyzab", 6);
        do_func("abc", "abc", 0);
    }

    private static void do_func(String word1, String word2, int expected) {
        int ret = minDistance(word1, word2);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
