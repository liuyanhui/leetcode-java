package leetcode;

/**
 * 376. Wiggle Subsequence
 * Medium
 * -----------------------------
 * A wiggle sequence is a sequence where the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with one element and a sequence with two non-equal elements are trivially wiggle sequences.
 *
 * - For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) alternate between positive and negative.
 * - In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences. The first is not because its first two differences are positive, and the second is not because its last difference is zero.
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
        return wiggleMaxLength_4(nums);
    }

    /**
     * review
     * round 2
     *
     * 验证失败：无法通过楼梯状的用例，如：nums={5, 5, 5, 4, 4, 4, 3, 3, 3}
     *
     * 正确结果参考以下三个方案
     * wiggleMaxLength_1 wiggleMaxLength_2 wiggleMaxLength_3
     *
     * @param nums
     * @return
     */
    public static int wiggleMaxLength_4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            //计算单调增的区间数
            if (nums[i - 1] < nums[i]) {
                if (i == 1 || nums[i - 2] >= nums[i - 1])
                    res++;
            }
            //计算单调减的区间数
            if (nums[i - 1] > nums[i]) {
                if (i == 1 || nums[i - 2] <= nums[i - 1])
                    res++;
            }
        }
        return res;
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
        do_func(new int[]{3, 3, 3, 2, 5}, 3);
        do_func(new int[]{372, 492, 288, 399, 81, 2, 320, 94, 416, 469, 427, 117, 265, 357, 399, 456, 496, 337, 355, 219, 475, 295, 457, 350, 490, 470, 281, 127, 131, 36, 430, 412, 442, 174, 128, 253, 1, 56, 306, 295, 340, 73, 253, 130, 259, 223, 14, 79, 409, 384, 209, 151, 317, 441, 156, 275, 140, 224, 128, 250, 290, 191, 161, 472, 477, 125, 470, 230, 321, 5, 311, 23, 27, 248, 138, 284, 215, 356, 320, 194, 434, 136, 221, 273, 450, 440, 28, 179, 36, 386, 482, 203, 24, 8, 391, 21, 500, 484, 135, 348, 292, 396, 145, 443, 406, 61, 212, 480, 455, 78, 309, 318, 84, 474, 209, 225, 177, 356, 227, 263, 181, 476, 478, 151, 494, 395, 23, 114, 395, 429, 450, 247, 245, 150, 354, 230, 100, 172, 454, 155, 189, 33, 290, 187, 443, 123, 59, 358, 241, 141, 39, 196, 491, 381, 157, 157, 134, 431, 295, 20, 123, 118, 207, 199, 317, 188, 267, 335, 315, 308, 115, 321, 56, 52, 253, 492, 97, 374, 398, 272, 74, 206, 109, 172, 471, 55, 452, 452, 329, 367, 372, 252, 99, 62, 122, 287, 320, 325, 307, 481, 316, 378, 87, 97, 457, 21, 312, 249, 354, 286, 196, 43, 170, 500, 265, 253, 19, 480, 438, 113, 473, 247, 257, 33, 395, 456, 246, 310, 469, 408, 112, 385, 53, 449, 117, 122, 210, 286, 149, 20, 364, 372, 71, 26, 155, 292, 16, 72, 384, 160, 79, 241, 346, 230, 15, 427, 96, 95, 59, 151, 325, 490, 223, 131, 81, 294, 18, 70, 171, 339, 14, 40, 463, 421, 355, 123, 408, 357, 202, 235, 390, 344, 198, 98, 361, 434, 174, 216, 197, 274, 231, 85, 494, 57, 136, 258, 134, 441, 477, 456, 318, 155, 138, 461, 65, 426, 162, 90, 342, 284, 374, 204, 464, 9, 280, 391, 491, 231, 298, 284, 82, 417, 355, 356, 207, 367, 262, 244, 283, 489, 477, 143, 495, 472, 372, 447, 322, 399, 239, 450, 168, 202, 89, 333, 276, 199, 416, 490, 494, 488, 137, 327, 113, 189, 430, 320, 197, 120, 71, 262, 31, 295, 218, 74, 238, 169, 489, 308, 300, 260, 397, 308, 328, 267, 419, 84, 357, 486, 289, 312, 230, 64, 468, 227, 268, 28, 243, 267, 254, 153, 407, 399, 346, 385, 77, 297, 273, 484, 366, 482, 491, 368, 221, 423, 107, 272, 98, 309, 426, 181, 320, 77, 185, 382, 478, 398, 476, 22, 328, 450, 299, 211, 285, 62, 344, 484, 395, 466, 291, 487, 301, 407, 28, 295, 36, 429, 99, 462, 240, 124, 261, 387, 30, 362, 161, 156, 184, 188, 99, 377, 392, 442, 300, 98, 285, 312, 312, 365, 415, 367, 105, 81, 378, 413, 43, 326, 490, 320, 266, 390, 53, 327, 75, 332, 454, 29, 370, 392, 360, 1, 335, 355, 344, 120, 417, 455, 93, 60, 256, 451, 188, 161, 388, 338, 238, 26, 275, 340, 109, 185}, 334);
        do_func(new int[]{5, 5, 5, 4, 4, 4, 3, 3, 3}, 2);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = wiggleMaxLength(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
