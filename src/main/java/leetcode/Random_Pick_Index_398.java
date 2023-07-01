package leetcode;

import java.util.*;

/**
 * 398. Random Pick Index
 * Medium
 * ----------------------------
 * Given an integer array nums with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.
 *
 * Implement the Solution class:
 * Solution(int[] nums) Initializes the object with the array nums.
 * int pick(int target) Picks a random index i from nums where nums[i] == target. If there are multiple valid i's, then each index should have an equal probability of returning.
 *
 * Example 1:
 * Input
 * ["Solution", "pick", "pick", "pick"]
 * [[[1, 2, 3, 3, 3]], [3], [1], [3]]
 * Output
 * [null, 4, 0, 2]
 * Explanation
 * Solution solution = new Solution([1, 2, 3, 3, 3]);
 * solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
 * solution.pick(1); // It should return 0. Since in the array only nums[0] is equal to 1.
 * solution.pick(3); // It should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * target is an integer from nums.
 * At most 10^4 calls will be made to pick.
 */
public class Random_Pick_Index_398 {

    /**
     * round 2
     * reservoir sample法不熟悉
     */

    /**
     * Map缓存法
     * 验证通过：
     * Runtime: 65 ms, faster than 73.91% of Java.
     * Memory Usage: 49.2 MB, less than 62.71% of Java
     */
    static class Solution {

        private Map<Integer, List<Integer>> map;

        public Solution(int[] nums) {
            map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.computeIfAbsent(nums[i], m -> new ArrayList<Integer>());
                map.get(nums[i]).add(i);
            }
        }

        public int pick(int target) {
            if (!map.containsKey(target)) return -1;
            Random r = new Random();
            return map.get(target).get(r.nextInt(map.get(target).size()));
        }
    }

    /**
     * 套路
     * reservoir sample法
     *
     * 验证通过：
     * Runtime: 50 ms, faster than 83.91% of Java .
     * Memory Usage: 47.8 MB, less than 80.03% of Java
     *
     */
    static class Solution_2 {
        int[] nums;
        Random r = new Random();

        public Solution_2(int[] nums) {
            this.nums = nums;
        }

        public int pick(int target) {
            int ret = -1;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                ret = (ret == -1 ? i : ret);
                if (nums[i] == target && r.nextInt(count++ + 1) < 1) {
                    ret = i;
                }
            }
            return ret;
        }
    }

    /**
     * Your Solution object will be instantiated and called as such:
     * Solution obj = new Solution(nums);
     * int param_1 = obj.pick(target);
     */

    public static void main(String[] args) {
        Solution_2 solution = new Solution_2(new int[]{1, 2, 3, 3, 3});
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(1));
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(3));

    }

}
