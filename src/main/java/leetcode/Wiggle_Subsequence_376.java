package leetcode;

/**
 * 376. Wiggle Subsequence
 * Medium
 * -----------------------------
 * A wiggle sequence is a sequence where the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with one element and a sequence with two non-equal elements are trivially wiggle sequences.
 *
 * For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) alternate between positive and negative.
 * In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences. The first is not because its first two differences are positive, and the second is not because its last difference is zero.
 *
 * A subsequence is obtained by deleting some elements (possibly zero) from the original sequence, leaving the remaining elements in their original order.
 *
 * Given an integer array nums, return the length of the longest wiggle subsequence of nums.
 *
 * Example 1:
 * Input: nums = [1,7,4,9,2,5]
 * Output: 6
 * Explanation: The entire sequence is a wiggle sequence with differences (6, -3, 5, -7, 3).
 *
 * Example 2:
 * Input: nums = [1,17,5,10,13,15,10,5,16,8]
 * Output: 7
 * Explanation: There are several subsequences that achieve this length.
 * One is [1, 17, 10, 13, 10, 16, 8] with differences (16, -7, 3, -3, 6, -8).
 *
 * Example 3:
 * Input: nums = [1,2,3,4,5,6,7,8,9]
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 1000
 *
 *  Follow up: Could you solve this in O(n) time?
 */
public class Wiggle_Subsequence_376 {
    public static int wiggleMaxLength(int[] nums) {
        return wiggleMaxLength_3(nums);
    }

    /**
     * wiggleMaxLength_2的空间复杂度优化版，但是没有wiggleMaxLength_2直观
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Wiggle Subsequence.
     * Memory Usage: 36.5 MB, less than 71.73% of Java online submissions for Wiggle Subsequence.
     *
     * @param nums
     * @return
     */
    public static int wiggleMaxLength_3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int up = 0, down = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                up = down + 1;
            } else if (nums[i - 1] > nums[i]) {
                down = up + 1;
            }
        }
        return 1 + Math.max(down, up);
    }

    /**
     * DP思路
     * 参考思路：
     * https://leetcode.com/problems/wiggle-subsequence/discuss/84843/Easy-understanding-DP-solution-with-O(n)-Java-version
     * https://leetcode.com/problems/wiggle-subsequence/solution/ 之Approach 2 和 3
     *
     * For every position in the array, there are only three possible statuses for it.
     * 1.up position, it means nums[i] > nums[i-1]
     * 2.down position, it means nums[i] < nums[i-1]
     * 3.equals to position, nums[i] == nums[i-1]
     *
     * So we can use two arrays up[] and down[] to record the max wiggle sequence length so far at index i.
     * If nums[i] > nums[i-1], that means it wiggles up. the element before it must be a down position. so up[i] = down[i-1] + 1; down[i] keeps the same with before.
     * If nums[i] < nums[i-1], that means it wiggles down. the element before it must be a up position. so down[i] = up[i-1] + 1; up[i] keeps the same with before.
     * If nums[i] == nums[i-1], that means it will not change anything becasue it didn't wiggle at all. so both down[i] and up[i] keep the same.
     *
     * @param nums
     * @return
     */
    public static int wiggleMaxLength_2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                up[i] = down[i - 1] + 1;
                down[i] = down[i - 1];
            } else if (nums[i - 1] > nums[i]) {
                up[i] = up[i - 1];
                down[i] = up[i - 1] + 1;
            } else {
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return 1 + Math.max(down[nums.length - 1], up[nums.length - 1]);
    }

    /**
     * 贪心法 Greedy Approach
     *
     * 从前向后依次遍历，根据两个数字的趋势(direct)和当前两个数字的趋势判断，如果趋势改变结果加1。
     * direct>0表示单调增，direct<0表示单调减，direct==0表示趋势不变
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Wiggle Subsequence.
     * Memory Usage: 36.6 MB, less than 57.09% of Java online submissions for Wiggle Subsequence.
     *
     * @param nums
     * @return
     */
    public static int wiggleMaxLength_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int directLast = 0;// >0递增，<0递减
        int ret = 1;
        for (int i = 1; i < nums.length; i++) {
            int directNow = nums[i] - nums[i - 1];
            if (directNow != 0) {
                if (directLast == 0 || directNow * directLast < 0) {
                    ret++;
                }
                directLast = directNow;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 7, 4, 9, 2, 5}, 6);
        do_func(new int[]{1, 17, 5, 10, 13, 15, 10, 5, 16, 8}, 7);
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 2);
        do_func(new int[]{1, 2}, 2);
        do_func(new int[]{1, 2, 2, 2, 2, 2}, 2);
        do_func(new int[]{2, 2, 2, 2, 2}, 1);
        do_func(new int[]{1, 2, 2, 2, 2, 2}, 2);
        do_func(new int[]{1}, 1);
        do_func(new int[]{}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = wiggleMaxLength(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}