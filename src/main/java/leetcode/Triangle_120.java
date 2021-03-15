package leetcode;

import java.util.List;

/**
 * 120. Triangle
 * Medium
 * -----------------
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.
 *
 * Example 1:
 * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * Output: 11
 * Explanation: The triangle looks like:
 *    2
 *   3 4
 *  6 5 7
 * 4 1 8 3
 * The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
 *
 * Example 2:
 * Input: triangle = [[-10]]
 * Output: -10
 *
 * Constraints:
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == triangle[i - 1].length + 1
 * -104 <= triangle[i][j] <= 104
 *
 * Follow up: Could you do this using only O(n) extra space, where n is the total number of rows in the triangle?
 */
public class Triangle_120 {
    public static int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotal_3(triangle);
    }

    /**
     * DP思路简介版，与minimumTotal_2的区别是从下往上+从左往右，minimumTotal_2仅仅是从右往左。
     * 公式为：dp[i]= triangle[row][i] + min(dp[i],dp[i+1])
     *
     * 参考思路：
     * https://leetcode.com/problems/triangle/discuss/38730/DP-Solution-for-Triangle
     * https://leetcode.com/problems/triangle/discuss/38724/7-lines-neat-Java-Solution
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 76.39% of Java online submissions for Triangle.
     * Memory Usage: 39.1 MB, less than 62.78% of Java online submissions for Triangle.
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_3(List<List<Integer>> triangle) {
        int dp[] = new int[triangle.size() + 1];
        for (int i = triangle.size() - 1; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }

    /**
     * DP思路：
     * 公式为dp[i]= triangle[row][i] + min(dp[i],dp[i-1])
     *
     * 验证通过:
     * Runtime: 1 ms, faster than 96.81% of Java online submissions for Triangle.
     * Memory Usage: 38.9 MB, less than 76.23% of Java online submissions for Triangle.
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_2(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        int[] dp = new int[triangle.size()];
        for (int i = 0; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        int ret = Integer.MAX_VALUE;
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); i++) {
            for (int j = triangle.get(i).size() - 1; j >= 0; j--) {
                if (j == 0) {
                    dp[j] = triangle.get(i).get(j) + dp[j];
                } else {
                    //这里是公式
                    dp[j] = triangle.get(i).get(j) + Math.min(dp[j - 1], dp[j]);
                }
            }
        }

        for (int i = 0; i < dp.length; i++) {
            ret = ret < dp[i] ? ret : dp[i];
        }
        return ret;
    }

    /**
     * 递归思路，类似于Tree的dfs思路
     *
     * Time Complexity : O(2^level)
     * 验证失败：原因，Time Limit Exceeded
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_1(List<List<Integer>> triangle) {
        return do_recursive(triangle, 0, 0, 0, Integer.MAX_VALUE);
    }

    private static int do_recursive(List<List<Integer>> triangle,
                                    int row, int col, int curValue, int minValue) {
        if (row >= triangle.size()) {
            return curValue > minValue ? minValue : curValue;
        }
        int cur = curValue + triangle.get(row).get(col);
        int l = do_recursive(triangle, row + 1, col, cur, minValue);
        int r = do_recursive(triangle, row + 1, col + 1, cur, minValue);
        return l > r ? r : l;
    }

    public static void main(String[] args) {
        do_func(new Integer[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}}, 11);
        do_func(new Integer[][]{{-10}}, -10);
        do_func(new Integer[][]{{1}, {1, 1}, {1, 1, 1}, {1, 1, 1, 1}}, 4);

    }

    private static void do_func(Integer[][] arr, int expected) {
        int ret = minimumTotal(ArrayListUtils.arrayToList(arr));
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
