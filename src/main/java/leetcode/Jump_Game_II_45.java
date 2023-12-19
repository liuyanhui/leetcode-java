package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 45. Jump Game II
 * Medium
 * ----------------------------
 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].
 * Each element nums[i] represents the maximum length of a forward jump from index i. In other words, if you are at nums[i], you can jump to any nums[i + j] where:
 *  0 <= j <= nums[i] and
 *  i + j < n
 *
 * Return the minimum number of jumps to reach nums[n - 1]. The test cases are generated such that you can reach nums[n - 1].
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
 * It's guaranteed that you can reach nums[n - 1].
 */
public class Jump_Game_II_45 {
    public static int jump(int[] nums) {
        return jump_3(nums);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1. naive solution 【本方法采用了这个方案】
     * 暴力法，设dp[]为nums中每个数的最优解，dp[0]=0,dp[1:]=MAX_VALUE
     * 1.1.从小到大依次计算nums[i]移动的情况，针对每个i循环执行dp[j]=min(dp[i]+1,dp[j])，i<j<=i+nums[i]
     * 时间复杂度：O(N*N)
     * 2.jump_2()的思路
     * 2.1. 由于无需记录过程中的最优解，所以可以优化算法。
     * 2.2. 只需记录次数jump和每次可以达到的最远i即可。当i>=len(nums)-1时，jump为结果。
     *
     * 3.本质上都是BFS思路
     *
     *
     * 验证通过：
     * Runtime 38 ms Beats 20.81% of users with Java
     * Memory 45.16 MB Beats 11.94% of users with Java
     *
     * @param nums
     * @return
     */
    public static int jump_3(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < nums.length; i++) {
            //注意i+j超出最大长度
            for (int j = 1; j <= nums[i] && i + j < nums.length; j++) {
                dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
            }
        }
        return dp[nums.length - 1];
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
            beg = end + 1;//review 这里很巧妙
            end = nextEnd;//review 这里很巧妙
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
