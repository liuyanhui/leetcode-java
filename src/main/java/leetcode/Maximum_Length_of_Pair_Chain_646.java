package leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 646. Maximum Length of Pair Chain
 * Medium
 * ------------------------------
 * You are given an array of n pairs pairs where pairs[i] = [lefti, righti] and lefti < righti.
 *
 * A pair p2 = [c, d] follows a pair p1 = [a, b] if b < c. A chain of pairs can be formed in this fashion.
 *
 * Return the length longest chain which can be formed.
 *
 * You do not need to use up all the given intervals. You can select pairs in any order.
 *
 * Example 1:
 * Input: pairs = [[1,2],[2,3],[3,4]]
 * Output: 2
 * Explanation: The longest chain is [1,2] -> [3,4].
 *
 * Example 2:
 * Input: pairs = [[1,2],[7,8],[4,5]]
 * Output: 3
 * Explanation: The longest chain is [1,2] -> [4,5] -> [7,8].
 *
 * Constraints:
 * n == pairs.length
 * 1 <= n <= 1000
 * -1000 <= lefti < righti <= 1000
 */
public class Maximum_Length_of_Pair_Chain_646 {
    public static int findLongestChain(int[][] pairs) {
        return findLongestChain_1(pairs);
    }

    /**
     * 另一种更优的解法，
     * 1.先根据right_i排序，在根据left_i判断是否加入chain
     * 2.时间复杂度:O(NlogN)
     *
     */

    /**
     * Thinking：
     * 1.类似求最长最大值等，一般会涉及局部最优解和全局最优解。会采用递归或者DP的思路。
     * 2.naive solution
     * 遍历每个pair，假设必然从这个pair开始，采用递归思路。
     * 时间复杂度：O(N^N)
     * 3.先根据left_i排序，再查找解。
     * 4.DP solution
     * dp[i]表示以第i个pair结尾的局部最优解。
     * IF pairs[j][1]<pairs[i][0] THEN dp[i]=max(dp[j])+1 ， 其中 0<=j<i
     * 返回结果为max(dp[i])
     * 时间复杂度：O(N*N)
     *
     * 验证通过：
     * Runtime 31 ms Beats 34.80%
     * Memory 44 MB Beats 50.19%
     *
     * @param pairs
     * @return
     */
    public static int findLongestChain_1(int[][] pairs) {
        int res = 0;
        int[] dp = new int[pairs.length];
        //先排序
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        for (int i = 0; i < pairs.length; i++) {
            dp[i] = 1;//初始化dp数组
            for (int j = 0; j < i; j++) {
                if (pairs[j][1] < pairs[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 2}, {2, 3}, {3, 4}}, 2);
        do_func(new int[][]{{1, 2}, {7, 8}, {4, 5}}, 3);
        do_func(new int[][]{{1, 5}}, 1);

    }

    private static void do_func(int[][] pairs, int expected) {
        int ret = findLongestChain(pairs);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
