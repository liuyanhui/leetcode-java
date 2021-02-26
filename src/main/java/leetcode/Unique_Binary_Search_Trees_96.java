package leetcode;

/**
 * 96. Unique Binary Search Trees
 * Medium
 * -------------------
 * Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.
 *
 * Example 1:
 * Input: n = 3
 * Output: 5
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= n <= 19
 */
public class Unique_Binary_Search_Trees_96 {
    public static int numTrees(int n) {
        return numTrees_2(n);
    }

    /**
     * dp思路，
     * 公式如下：
     * f(n) = ∑(f(i-1)*f(n-i))，其中1<=i<=n。初始化：f(0)=1,f(1)=1
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
     * Memory Usage: 36.2 MB, less than 16.74% of Java online submissions for Unique Binary Search Trees.
     * @param n
     * @return
     */
    public static int numTrees_2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    /**
     * 递归思路，
     * 返回结果只跟数字大小有关，结果是左子树的个数 乘以 右子树的个数，需要遍历1~n的所有可能。
     * 公式如下：
     * f(n) = ∑(f(i-1)*f(n-i))，其中1<=i<=n。初始化：f(0)=1,f(1)=1
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Binary Search Trees.
     * Memory Usage: 36.1 MB, less than 22.14% of Java online submissions for Unique Binary Search Trees.
     *
     * @param n
     * @return
     */
    public static int numTrees_1(int n) {
        int[] cached = new int[n + 1];
        cached[0] = 1;
        cached[1] = 1;
        return do_find(n, cached);
    }

    private static int do_find(int n, int[] cached) {
        if (cached[n] > 0) {
            return cached[n];
        }
        int ret = 0;
        for (int i = 1; i <= n; i++) {
            ret += do_find(i - 1, cached) * do_find(n - i, cached);
        }
        cached[n] = ret;
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, 5);
        do_func(1, 1);
        do_func(19, 1767263190);
    }

    private static void do_func(int x, int expected) {
        int ret = numTrees(x);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
