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
        return maxEnvelopes_1(envelopes);
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
