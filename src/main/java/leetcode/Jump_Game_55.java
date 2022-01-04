package leetcode;

import java.util.HashSet;
import java.util.Set;

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
     * review round 2
     *
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        return canJump_4(nums);
    }

    /**
     * round 2
     * DP思路：能到达i必然能到达i-1
     * 0.设touch为达到结尾必须要到达的点，初始时touch=nums.length-1;
     * 1.从后向前遍历，如果i能到达touch，touch=i
     * 2.如果touch==0 返回true；否则，返回false
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 47.02% of Java online submissions for Jump Game.
     * Memory Usage: 64.5 MB, less than 7.57% of Java online submissions for Jump Game.
     *
     * @param nums
     * @return
     */
    public static boolean canJump_4(int[] nums) {
        int touch = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= touch) touch = i;
        }
        return touch == 0;
    }

    /**
     * round 2
     * Greedy思路
     * 遍历nums，记fast为每个元素可以到达的最远元素
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 39.20% of Java online submissions for Jump Game.
     * Memory Usage: 63.5 MB, less than 22.21% of Java online submissions for Jump Game.
     *
     * @param nums
     * @return
     */
    public static boolean canJump_3(int[] nums) {
        int fast = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > fast) return false;//如果i超过fast，表示有
            if (i + nums[i] > fast) {
                fast = i + nums[i];
                if (fast >= nums.length - 1) return true;
            }
        }
        return true;
    }

    /**
     * round 2
     * bfs，是n叉树问题，采用广度搜索思路
     * 1.使用两个list，cur和next，分别代表当前层队列和下一层队列
     * 2.当到达数组末尾时，返回true；当队列为空且未到达队尾时，返回false。
     *
     * 验证成功
     * Runtime: 706 ms, faster than 8.49% of Java online submissions for Jump Game.
     * Memory Usage: 41.2 MB, less than 26.31% of Java online submissions for Jump Game.
     *
     * @param nums
     * @return
     */
    public static boolean canJump_2(int[] nums) {
        if (nums.length <= 1) return true;
        Set<Integer> cur = new HashSet<>();
        Set<Integer> next = new HashSet<>();
        cur.add(0);
        while (!cur.isEmpty()) {
            next = new HashSet<>();
            for (int c : cur) {
                for (int i = 1; i <= nums[c]; i++) {
                    if (cur.contains(c + i)) continue;//这里很关键，没有这个逻辑会Time limit exceeded
                    next.add(c + i);
                    if (c + i >= nums.length - 1) return true;
                }
            }
            cur = next;
        }
        return false;
    }

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
    public static boolean canJump_1(int[] nums) {
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
        do_func(new int[]{0}, true);
        do_func(new int[]{2, 0, 0}, true);
    }

    private static void do_func(int[] matrix, boolean expected) {
        boolean ret = canJump(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
