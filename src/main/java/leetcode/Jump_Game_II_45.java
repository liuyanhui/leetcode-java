package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 45. Jump Game II
 * Medium
 * ----------------------------
 * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 * You can assume that you can always reach the last index.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 */
public class Jump_Game_II_45 {
    public static int jump(int[] nums) {
        return jump_2(nums);
    }

    /**
     * review
     * jump_1的优化版。
     * 1.每层bfs的起止下标为[i,j]，初始化时i=j=0
     * 2.遍历i~j。对每个数字，判断当前数字是否直达末尾。如果直达，直接返回jump；否则更新j。
     *
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.56% of Java online submissions for Jump Game II.
     * Memory Usage: 39.4 MB, less than 81.78% of Java online submissions for Jump Game II.
     *
     * @param nums
     * @return
     */
    public static int jump_2(int[] nums) {
        if (nums.length <= 1) return 0;
        int jump = 0;
        //beg,end为bfs每层的起止下标。nextEnd为当前层最远可以到达的位置。
        int beg = 0, end = 0, nextEnd = 0;
        while (end < nums.length) {
            jump++;
            for (int i = beg; i <= end; i++) {
                if (i + nums[i] >= nums.length - 1) {
                    return jump;
                }
                //计算当前bfs层级的最远到达的位置
                nextEnd = Math.max(nextEnd, i + nums[i]);
            }
            beg = end + 1;
            end = nextEnd;
        }
        return jump;
    }

    /**
     * bfs思路。分层遍历，若干有某层有到达结尾的，那么该层层级为所求
     *
     * 参考思路：
     * https://leetcode.com/problems/jump-game-ii/discuss/18028/O(n)-BFS-solution
     *
     * 验证通过：
     * Runtime: 2238 ms, faster than 5.02% of Java online submissions for Jump Game II.
     * Memory Usage: 46.3 MB, less than 17.81% of Java online submissions for Jump Game II.
     * @param nums
     * @return
     */
    public static int jump_1(int[] nums) {
        if (nums.length <= 1) return 0;
        //set是可以优化的地方。原因：无需两个set，bfs场景下，先被遍历的数字必然已经是当前的最优解，这些数字无需再次计算。
        //set中是下标，不是值
        Set<Integer> cur = new HashSet<>();
        Set<Integer> next = null;
        int jump = 0;
        cur.add(0);
        while (cur.size() > 0) {//这里是可以优化的地方。
            next = new HashSet<>();
            jump++;
            for (int i : cur) {
                if (i + nums[i] >= nums.length - 1) {
                    return jump;
                }
                for (int j = 1; j <= nums[i]; j++) {//这里也可以被优化
                    next.add(i + j);
                }

            }
            cur = next;
        }
        return jump;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 3, 1, 1, 4}, 2);
        do_func(new int[]{2, 3, 0, 1, 4}, 2);
        do_func(new int[]{20, 1, 0, 0, 0, 1, 4}, 1);
        do_func(new int[]{2}, 0);
    }

    private static void do_func(int[] matrix, int expected) {
        int ret = jump(matrix);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
