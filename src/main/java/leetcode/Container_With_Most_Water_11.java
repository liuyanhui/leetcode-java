package leetcode;

/**
 * 11. Container With Most Water
 * Medium
 * ---------------------------
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0). Find two lines, which, together with the x-axis forms a container, such that the container contains the most water.
 *
 * Notice that you may not slant the container.
 *
 * Example 1:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 *
 * Example 2:
 * Input: height = [1,1]
 * Output: 1
 *
 * Example 3:
 * Input: height = [4,3,2,1,4]
 * Output: 16
 *
 * Example 4:
 * Input: height = [1,2,1]
 * Output: 2
 *
 * Constraints:
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 */
public class Container_With_Most_Water_11 {
    /**
     * Round 2
     *
     * 参考思路：
     * https://leetcode.com/problems/container-with-most-water/discuss/6091/Easy-Concise-Java-O(N)-Solution-with-Proof-and-Explanation
     * https://leetcode.com/problems/container-with-most-water/discuss/6100/Simple-and-clear-proofexplanation
     * 验证通过：
     * Runtime: 3 ms, faster than 87.40% of Java.
     * Memory Usage: 52.9 MB, less than 57.59% of Java
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int ret = 0;
        int l = 0, r = height.length - 1;
        while (l < r) {
            ret = Math.max(ret, Math.min(height[r], height[l]) * (r - l));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }

        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}, 49);
        do_func(new int[]{1, 1}, 1);
        do_func(new int[]{4, 3, 2, 1, 4}, 16);
        do_func(new int[]{1, 2, 1}, 2);
    }

    private static void do_func(int[] height, int expected) {
        int ret = maxArea(height);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
