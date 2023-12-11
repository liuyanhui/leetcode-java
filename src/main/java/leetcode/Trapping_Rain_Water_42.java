package leetcode;

import java.util.Stack;

/**
 * 42. Trapping Rain Water
 * Hard
 * -----------------------------
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 *
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 *
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * Constraints:
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 */
public class Trapping_Rain_Water_42 {
    public static int trap(int[] height) {
        return trap_4(height);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * 单调栈思路：参考trap_4()和trap_3()
     * 夹逼思路：参考trap_1()和trap_2()
     *
     * @param height
     * @return
     */

    /**
     * round 2
     *
     * 验证通过：
     * Runtime: 13 ms, faster than 8.30% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 48 MB, less than 5.16% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public static int trap_4(int[] height) {
        int ret = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.empty() && height[stack.peek()] <= height[i]) {
                int bottom = height[stack.pop()];
                if (stack.empty()) break;
                ret += (Math.min(height[stack.peek()], height[i]) - bottom) * (i - stack.peek() - 1);
            }
            stack.push(i);
        }
        return ret;
    }

    /**
     * 还有一种DP思路，
     *
     * https://leetcode.com/problems/trapping-rain-water/solution/ Approach 3
     */

    /**
     * 【单调栈】【Monotone Priority Stack】
     * Stack法
     * 遍历数组，递减时入栈，递增时出栈并计算面积
     *
     * 参考思路：
     * https://leetcode.com/problems/trapping-rain-water/solution/
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 9.48% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 42.3 MB, less than 6.82% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public static int trap_3(int[] height) {
        int area = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.empty() && height[stack.peek()] <= height[i]) {
                int bottomIdx = stack.pop();
                if (stack.empty()) break;
                int curIdx = stack.peek();
                area += (Math.min(height[curIdx], height[i]) - height[bottomIdx]) * (i - curIdx - 1);
            }
            stack.push(i);
        }
        return area;
    }

    /**
     * review
     *
     * two point method，夹逼法
     * 思路如下：
     * 一般的单方向遍历思路，无法解决U型数组(两边大中间小)场景中，末尾比开头数字小的情况。所以在夹逼过程中应该跟对应的最大值比较，而不是跟相邻的数字比较。
     * 夹逼遍历数组
     * if(leftMax<rightMax) 先从左侧计算
     *     if(leftMax<h[l])
     *         leftMax=h[l]
     *     else
     *         累加面积
     *     l++
     * else 从右侧计算，
     *     if(h[r]<rightMax)
     *         累加面积
     *     else
     *         rightMax=h[r]
     *     r--
     *
     * 参考思路：
     * https://leetcode.com/problems/trapping-rain-water/discuss/153992/Java-O(n)-time-and-O(1)-space-(with-explanations).
     * https://leetcode.com/problems/trapping-rain-water/solution/
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 87.79% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 42.1 MB, less than 9.83% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public static int trap_2(int[] height) {
        int area = 0;
        int leftMax = height[0];
        int rightMax = height[height.length - 1];
        int l = 0, r = height.length - 1;
        while (l <= r) {
            //每轮循环都从最大值较小的一侧开始
            if (leftMax <= rightMax) {
                if (leftMax < height[l]) {
                    leftMax = height[l];
                } else {
                    area += leftMax - height[l];
                }
                l++;
            } else {
                if (height[r] < rightMax) {
                    area += rightMax - height[r];
                } else {
                    rightMax = height[r];
                }
                r--;
            }
        }
        return area;
    }

    /**
     * 思路：
     * 1.求面积area，公式为area=min(l,r)*(r-l-1)-sum(height[l+1:r-1])，其中l左边界的点，r为右边界的点
     * 2.计算每个满足条件区间的边界点，方法：左向右遍历+右向左遍历。两个方向的遍历，夹逼法。
     *
     * 验证通过：
     * Runtime: 98 ms, faster than 6.08% of Java
     * Memory Usage: 38.6 MB, less than 74.99% of Java
     *
     * @param height
     * @return
     */
    public static int trap_1(int[] height) {
        int area = 0;
        int l = 0, r = height.length - 1;
        while (l < r) {
            //从左向右计算
            int minus = 0;
            for (int i = l + 1; i <= r; i++) {
                minus += height[i];
                if (height[l] <= height[i]) {
                    minus -= height[i];
                    area += Math.min(height[l], height[i]) * (i - l - 1) - minus;
                    l = i;
                    break;
                }
            }
            //从右向左计算
            minus = 0;
            for (int i = r - 1; i >= l; i--) {
                minus += height[i];
                if (height[i] >= height[r]) {
                    minus -= height[i];
                    area += Math.min(height[i], height[r]) * (r - i - 1) - minus;
                    r = i;
                    break;
                }
            }
        }
        return area;
    }

    public static void main(String[] args) {
        do_func(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 6);
        do_func(new int[]{4, 2, 0, 3, 2, 5}, 9);
        do_func(new int[]{4}, 0);
        do_func(new int[]{4, 4, 4, 4, 4, 4, 4, 4}, 0);
        do_func(new int[]{4, 3, 3, 2, 1}, 0);
        do_func(new int[]{1, 2, 3, 4, 5}, 0);
        do_func(new int[]{5, 5, 1, 7, 1, 1, 5, 2, 7, 6}, 23);
        do_func(new int[]{5, 5, 1, 7, 1, 1, 5, 2, 700, 6}, 23);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = trap(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
