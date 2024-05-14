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
 * -10^4 <= triangle[i][j] <= 10^4
 *
 * Follow up: Could you do this using only O(n) extra space, where n is the total number of rows in the triangle?
 */
public class Triangle_120 {
    public static int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotal_6(triangle);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1. native solution
     * DFS思路，遍历所有的可能，计算出最小和路径。
     * Time Complexty: O(N!) N是triangle的长度
     * Space Complexty: O(N)
     * 2. 通过分析可知，局部最优解与全局最优解相关性不大。即 [0~i+1] 层的最优解依赖 [0~i] 的最优解，但是不依赖 [0~i-1] 层的最优解没有相关性。
     * 3. 如果全局最优解必然会在每一层选择一个元素。设在i层选择了j元素，记为 T[i,j]。那么T[i,j]在[0~i]层的局部最优解，必然在全局最优解路径中。所以计算局部最优解是有用处的。
     * 4. DP思路，参考【3.】。
     * 4.1. dp[i][j]为自定向下第i层第j个元素的最优解，n=len(triangle)
     * 4.2. dp[i][j]=min(dp[i-1][j-1],dp[i-1][j])+triangle[i][j]
     * 4.3. dp[n-1][:]的最小值为全局最优解
     * 5. 可以把dp数组优化为以为数组
     *
     * 本方法性能：
     * Time Complexty: O(N*N)
     * Space Complexty: O(N*N)
     *
     * 验证通过：
     * Runtime 3 ms Beats 65.90%
     * Memory 43.89 MB Beats 72.03%
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_6(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        int[][] dp = new int[triangle.size()][triangle.size()];
        dp[0][0] = triangle.get(0).get(0);//提前处理第0行
        for (int i = 1; i < triangle.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i - 1][j];
                } else if (j < i) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                } else {//与dp数组的默认值比较
                    dp[i][j] = dp[i - 1][j - 1];
                }
                dp[i][j] += triangle.get(i).get(j);
            }
        }
        int ret = Integer.MAX_VALUE;
        for (int n : dp[triangle.size() - 1]) {
            ret = Math.min(ret, n);
        }
        return ret;
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * 验证失败，dp数组优化为一位数组时，自定向下的方案由于无法正确回溯上一层的计算过程，所以无法通过某些案例。
     * 需要采用自底向上的实现才行。
     * 详见：minimumTotal_4()
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_5(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        int[] dp = new int[triangle.size()];
        for (int i = 0; i < triangle.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    //do nothing
                } else if (j < i) {
                    dp[j] = Math.min(dp[j - 1] - triangle.get(i).get(j - 1), dp[j]);
                } else {//与dp数组的默认值比较
                    dp[j] = dp[j - 1] - triangle.get(i).get(j - 1);
                }
                dp[j] += triangle.get(i).get(j);
            }
        }
        int ret = Integer.MAX_VALUE;
        for (int n : dp) {
            ret = Math.min(ret, n);
        }
        return ret;
    }


    /**
     * round 2
     *
     * 如果按照从上到下的方式，采用暴力法，那么时间复杂度是O(2^(n-1))。显然不可取，因为解空间是发散的而不是收敛的。
     * 如果按照从下到上的方式，解空间会最终收敛到顶部。时间复杂度是O(n*m)
     * 公式如下：
     * ret[i][j]是以[i,j]为起点的从下到上路径和(path sum)的最小值
     * ret[i][j]=triangle[i][j]+min(ret[i+1][j],+ret[i+1][j+1])
     * 返回结果为ret[0][0]
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 51.43% of Java online submissions for Triangle.
     * Memory Usage: 44 MB, less than 46.23% of Java online submissions for Triangle.
     *
     * @param triangle
     * @return
     */
    public static int minimumTotal_4(List<List<Integer>> triangle) {
        int[] ret = new int[triangle.size() + 1];
        for (int i = triangle.size() - 1; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                ret[j] = triangle.get(i).get(j) + Math.min(ret[j], ret[j + 1]);
            }
        }
        return ret[0];
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
        do_func(new Integer[][]{{-1}, {2, 3}, {1, -1, -3}}, -1);
        System.out.println("-------- OK ------");
    }

    private static void do_func(Integer[][] arr, int expected) {
        int ret = minimumTotal(ArrayListUtils.arrayToList(arr));
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
