package leetcode;

import java.util.Stack;

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
        return largestRectangleArea_2(heights);
    }

    /**
     * 金矿
     * 【单调栈】【Monotone Priority Stack】
     *
     * 思路如下：
     * 1.单调栈分为单调递增栈和单调递减栈
     * 11. 单调递增栈即栈内元素保持单调递增的栈
     * 12. 同理单调递减栈即栈内元素保持单调递减的栈
     *
     * 2.操作规则（下面都以单调递增栈为例）
     * 21. 如果新的元素比栈顶元素大，就入栈
     * 22. 如果新的元素较小，那就一直把栈内元素弹出来，直到栈顶比新元素小
     *
     * 3.加入这样一个规则之后，会有什么效果
     * 31. 栈内的元素是递增的
     * 32. 当元素出栈时，说明这个新元素是出栈元素向后找第一个比其小的元素
     *     以“Example 1”为例，当索引在 6 时，栈里是 1 5 6 。
     *     接下来新元素是 2 ，那么 6 需要出栈。
     *     当 6 出栈时，右边 2 代表是 6 右边第一个比 6 小的元素。
     * 33.当元素出栈后，说明新栈顶元素是出栈元素向前找第一个比其小的元素
     *     当 6 出栈时，5 成为新的栈顶，那么 5 就是 6 左边第一个比 6 小的元素。
     *
     * 4.每当元素出栈时，根据上面的规则可以计算包含该元素的最大面积。
     *
     * 验证通过：
     * Runtime: 191 ms, faster than 18.04% of Java online submissions for Largest Rectangle in Histogram.
     * Memory Usage: 90.4 MB, less than 11.99% of Java online submissions for Largest Rectangle in Histogram.
     *
     * @param heights
     * @return
     */
    public static int largestRectangleArea_2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();

        int maxArea = 0;
        for (int i = 0; i <= heights.length; i++) {
            while (!stack.empty() && (i == heights.length || heights[stack.peek()] > heights[i])) {
                int t = stack.pop();
                int leftIdx = stack.empty() ? -1 : stack.peek();
                maxArea = Math.max(maxArea, heights[t] * (i - leftIdx - 1));
            }
            stack.push(i);
        }

        return maxArea;
    }

    /**
     * review R2 20220211
     *
     * 参考思路：
     * https://leetcode.com/problems/largest-rectangle-in-histogram/discuss/28902/5ms-O(n)-Java-solution-explained-(beats-96)
     *
     * brute force 思路，时间复杂度O(N*N)，大概率不符合条件。
     * 思路很重要
     * 0.max_area[i]表示包括i的最大面积。
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
        do_func(new int[]{20}, 20);
    }

    private static void do_func(int[] heights, int expected) {
        int ret = largestRectangleArea(heights);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
