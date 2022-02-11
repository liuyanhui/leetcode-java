package leetcode;

/**
 * 84. Largest Rectangle in Histogram
 * Hard
 * ---------------------------
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
 *
 * Example 1:
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The above is a histogram where width of each bar is 1.
 * The largest rectangle is shown in the red area, which has an area = 10 units.
 *
 * Example 2:
 * Input: heights = [2,4]
 * Output: 4
 *
 * Constraints:
 * 1 <= heights.length <= 10^5
 * 0 <= heights[i] <= 10^4
 */
public class Largest_Rectangle_in_Histogram_84 {
    public static int largestRectangleArea(int[] heights) {
        return largestRectangleArea_1(heights);
    }

    /**
     * review 20220211
     *
     * 参考思路：
     * https://leetcode.com/problems/largest-rectangle-in-histogram/discuss/28902/5ms-O(n)-Java-solution-explained-(beats-96)
     *
     * brute force 思路，时间复杂度O(N*N)，大概率不符合条件。
     * 思路很重要
     * 1.max_area[i] = heights[i]*(right-left+1)。right为向右离i最远的满足heights[right]>heights[i]的数字下标；left为向左离i最远的满足heights[left]>heights[i]的数字下标。分别用dp_right[]和dp_left[]保存这些下标。
     * 2.max_area[]的最大值为所求
     * 3.本质上就是brute force的思路，只是在查找左右边界（dp_right[]和dp_left[]）的时候使用了trick。使用了DP思路。
     * 如果 heights[left]>heights[i] 那么 dp_right[i]=dp[left]，直接跳转到left-1再继续进行判断。
     *
     * 验证通过：
     * Runtime: 13 ms, faster than 91.06% of Java online submissions for Largest Rectangle in Histogram.
     * Memory Usage: 81.2 MB, less than 27.72% of Java online submissions for Largest Rectangle in Histogram.
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea_1(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        int maxArea = 0;
        int[] dp_right = new int[heights.length];
        int[] dp_left = new int[heights.length];
        //计算dp_left
        for (int i = 1; i < heights.length; i++) {
            int p = i - 1;
            while (p >= 0 && heights[p] >= heights[i]) {
                p = dp_left[p];
                p--;
            }
            dp_left[i] = p + 1;
        }

        //计算dp_right
        for (int i = heights.length - 1; i >= 0; i--) {
            int q = i + 1;
            while (q <= heights.length - 1 && heights[i] <= heights[q]) {
                q = dp_right[q];
                q++;
            }
            dp_right[i] = q - 1;
        }

        //计算maxArea
        for (int i = 0; i < heights.length; i++) {
            maxArea = Math.max(maxArea, heights[i] * (dp_right[i] - dp_left[i] + 1));
        }

        return maxArea;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 1, 5, 6, 2, 3}, 10);
        do_func(new int[]{2, 4}, 4);
    }

    private static void do_func(int[] heights, int expected) {
        int ret = largestRectangleArea(heights);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
