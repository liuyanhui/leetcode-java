package leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * 85. Maximal Rectangle
 * Hard
 * ---------------------------
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 *
 * Example 1:
 * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 6
 * Arrays: The maximal rectangle is shown in the above picture.
 *
 * Example 2:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Example 3:
 * Input: matrix = [["1"]]
 * Output: 1
 *
 * Constraints:
 * rows == matrix.length
 * cols == matrix[i].length
 * 1 <= row, cols <= 200
 * matrix[i][j] is '0' or '1'.
 */
public class Maximal_Rectangle_85 {
    public static int maximalRectangle(char[][] matrix) {
        return maximalRectangle_2(matrix);
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/maximal-rectangle/discuss/29054/Share-my-DP-solution
     *
     * 思路：DP思路
     * 跟maximalRectangle_1的思路大体一样，都是按行转化成histogram然后再计算。
     * 是"84. Largest Rectangle in Histogram"问题的复杂版，本质上是一样的。
     * 本解法不是用单调栈的方案，而是"84. Largest Rectangle in Histogram"的另一种解法，详见largestRectangleArea_1()
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 91.74% of Java online submissions for Maximal Rectangle.
     * Memory Usage: 54.9 MB, less than 11.73% of Java online submissions for Maximal Rectangle.
     *
     * @param matrix
     * @return
     */
    public static int maximalRectangle_2(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int maxArea = 0;
        int[] height = new int[matrix[0].length];//dp数组
        int[] left = new int[matrix[0].length];//dp数组
        int[] right = new int[matrix[0].length];//dp数组
        Arrays.fill(right, matrix[0].length - 1);
        for (int i = 0; i < matrix.length; i++) {
            //计算histogram
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') height[j]++;
                else height[j] = 0;
            }
            //计算left。即从matrix[i][j]左侧离matrix[i][j]最远的连续为1的坐标
            int minLeft = 0;//当前行matrix[i]中距离matrix[i][j]最远的连续为1的坐标
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], minLeft);
                } else {
                    minLeft = j + 1;
                    left[j] = 0;//这里要置为0，不可以是j，否则会影响向上第3行代码"left[j] = Math.max(left[j], minLeft);"的判断
                }
            }
            //计算right。即从matrix[i][j]右侧离matrix[i][j]最远的连续为1的坐标
            int maxRight = matrix[i].length - 1;//当前行matrix[i]中距离matrix[i][j]最远的连续为1的坐标
            for (int j = matrix[i].length - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], maxRight);
                } else {
                    maxRight = j - 1;
                    right[j] = matrix[i].length - 1;//这里不可以是j，否则会影响向上第3行代码"right[j] = Math.min(right[j], maxRight);"的判断
                }
            }
            //计算本行的最大histogram的最大矩形面积
            for (int j = 0; j < matrix[i].length; j++) {
                maxArea = Math.max(maxArea, (right[j] - left[j] + 1) * height[j]);
            }

        }
        return maxArea;
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/maximal-rectangle/discuss/29064/A-O(n2)-solution-based-on-Largest-Rectangle-in-Histogram
     *
     * 【单调栈】【Monotone Priority Stack】
     * 思路：
     * 转化为"84. Largest Rectangle in Histogram"问题。
     * 即从0~i行的matrix可以构造成histogram，问题就转化为求histogram中的最大矩形面积。
     * 对应"84. Largest Rectangle in Histogram"问题的解法largestRectangleArea_2()
     *
     * 验证通过：
     * Runtime: 29 ms, faster than 34.14% of Java online submissions for Maximal Rectangle.
     * Memory Usage: 46 MB, less than 68.27% of Java online submissions for Maximal Rectangle.
     *
     * @param matrix
     * @return
     */
    public static int maximalRectangle_1(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int maxArea = 0;
        int[] height = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            //初始化histogram
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') height[j]++;
                else height[j] = 0;
            }

            //计算以当前行为底的直方图的最大rectangle面积
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j <= height.length; j++) {
                while (!stack.empty() && (j == height.length || height[stack.peek()] > height[j])) {
                    int h = height[stack.pop()];
                    int leftIdx = stack.empty() ? -1 : stack.peek();
                    maxArea = Math.max(maxArea, h * (j - leftIdx - 1));
                }
                stack.push(j);
            }

        }
        return maxArea;
    }

    public static void main(String[] args) {
        do_func(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}, 6);
        do_func(new char[][]{{'0'}}, 0);
        do_func(new char[][]{{'1'}}, 1);
        do_func(new char[][]{{'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '1', '1', '1', '1'}}, 20);
        do_func(new char[][]{{'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}, {'0', '0', '0', '0', '0'}}, 0);
    }

    private static void do_func(char[][] matrix, int expected) {
        int ret = maximalRectangle(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}
