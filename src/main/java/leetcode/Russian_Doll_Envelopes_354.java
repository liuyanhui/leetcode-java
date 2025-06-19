package leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 354. Russian Doll Envelopes
 * Hard
 * --------------------------------
 * You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and the height of an envelope.
 *
 * One envelope can fit into another if and only if both the width and height of one envelope are greater than the other envelope's width and height.
 *
 * Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).
 *
 * Note: You cannot rotate an envelope.
 *
 * Example 1:
 * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 *
 * Example 2:
 * Input: envelopes = [[1,1],[1,1],[1,1]]
 * Output: 1
 *
 * Constraints:
 * 1 <= envelopes.length <= 10^5
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 10^5
 */
public class Russian_Doll_Envelopes_354 {
    public static int maxEnvelopes(int[][] envelopes) {
        return maxEnvelopes_r3_1(envelopes);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. think deeply
     * 满足Russian Doll时的约束：wi<wj AND hi<hj
     * 根据wi和hi排序，再查找。查找时采用greedy算法，每次获取最小的满足条件的envelope。即查找最小的大于wi的元素集合，再查找大于hi的最小元素，这一过程貌似需要对穷举递归每种可能。
     * 排序时，先根据w排序，再根据h排序。数据结构为sorted[w][h]，每一行为w相等的集合，并按h从小到大排序。
     * 递归时，采用缓存保存中间结果。
     * 2. 几乎所有的递归问题都可以采用DP思路。
     * 2.1. 分别根据w和h排序，数据结构为数据结构为sorted[w][h]。
     * dp[i][j]为sorted[i][j]最为最外层Russian Doll的最优解。
     * FOR k from 0 to len(sorted[i])
     *     IF sorted[i-1][k] < sorted[i][j]
     *     THEN dp[i][j]=max(dp[i][j],d[i][k]+1)
     * 返回 max(dp[-1][-1])
     * 2.2. dp的长度不是二位数组，而是二维列表。类似List<List<int>>
     * 3. 直接在envolope上排序，并查找，使用一维dp数组。
     *
     * 验证失败：Time Limit Exceeded, 逻辑正确。需要优化查找部分的逻辑。如maxEnvelopes_1()
     *
     * @param envelopes
     * @return
     */
    public static int maxEnvelopes_r3_1(int[][] envelopes){
        //先排序
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[0] - o2[0];
                }
            }
        });
        int[] dp = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            if (dp[i] == 0) dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (envelopes[j][0] < envelopes[i][0]) {
                    if (envelopes[j][1] < envelopes[i][1]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                } else {
                    break;
                }
            }
        }
        int res = 1;
        for (int t : dp) {
            res = Math.max(res, t);
        }
        return res;
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/russian-doll-envelopes/solutions/82763/java-nlogn-solution-with-explanation/
     *
     * 问题分解为：
     * 1.排序，width升序+height降序
     * 2.LIS(Longest Increasing Subsequence)问题
     *
     * LIS问题参考Longest_Increasing_Subsequence_300
     *
     * 验证通过：
     * Runtime 68 ms Beats 29.69%
     * Memory 87.3 MB Beats 40.16%
     *
     * @param envelopes
     * @return
     */
    public static int maxEnvelopes_1(int[][] envelopes) {
        //1.先排序
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //注意这里的排序规则
                if (o1[0] == o2[0]) {
                    //descending
                    return o2[1] - o1[1];
                } else {
                    //ascending
                    return o1[0] - o2[0];
                }
            }
        });
        //LIS问题求解
        int[] piles = new int[envelopes.length];
        piles[0] = envelopes[0][1];
        int idx = 0;
        for (int[] e : envelopes) {
            int l = 0, r = idx;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (piles[mid] < e[1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            idx = Math.max(idx, l);
            piles[l] = e[1];
        }

        return idx + 1;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}}, 3);
        do_func(new int[][]{{1, 1}, {1, 1}, {1, 1}}, 1);

    }

    private static void do_func(int[][] envelopes, int expected) {
        int ret = maxEnvelopes(envelopes);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
