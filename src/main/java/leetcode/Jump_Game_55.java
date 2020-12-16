package leetcode;

/**
 * https://leetcode.com/problems/jump-game/
 * 55. Jump Game
 * Medium
 * ---------------
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Determine if you are able to reach the last index.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * 0 <= nums[i][j] <= 10^5
 */
public class Jump_Game_55 {
    /**
     * 动态规划思路。
     * 从右向左依次遍历，依次判断i是否能够达到下标最小的可到达终点的元素（该元素记为reachedMin）：
     * 1.当i可以到达终点时，那么只要i左边的元素可以到达i即可，设置reachedMin=i。
     * 2.当i不能到达终点时，reachedMin保持不变。
     * 3.最终reachedMin==0返回结果为true
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Jump Game.
     * Memory Usage: 41.3 MB, less than 25.74% of Java online submissions for Jump Game.
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int reachedMin = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] >= (reachedMin - i)) {
                reachedMin = i;
            }
        }
        return reachedMin == 0;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 3, 1, 1, 4}, true);
        do_func(new int[]{3, 2, 1, 0, 4}, false);
    }

    private static void do_func(int[] matrix, boolean expected) {
        boolean ret = canJump(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
