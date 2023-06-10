package leetcode;

import java.util.Arrays;
import java.util.Random;

/**
 * 384. Shuffle an Array
 * Medium
 * -------------------
 * Given an integer array nums, design an algorithm to randomly shuffle the array. All permutations of the array should be equally likely as a result of the shuffling.
 *
 * Implement the Solution class:
 * Solution(int[] nums) Initializes the object with the integer array nums.
 * int[] reset() Resets the array to its original configuration and returns it.
 * int[] shuffle() Returns a random shuffling of the array.
 *
 * Example 1:
 * Input
 * ["Solution", "shuffle", "reset", "shuffle"]
 * [[[1, 2, 3]], [], [], []]
 * Output
 * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
 * Explanation
 * Solution solution = new Solution([1, 2, 3]);
 * solution.shuffle();    // Shuffle the array [1,2,3] and return its result.
 *                        // Any permutation of [1,2,3] must be equally likely to be returned.
 *                        // Example: return [3, 1, 2]
 * solution.reset();      // Resets the array back to its original configuration [1,2,3]. Return [1, 2, 3]
 * solution.shuffle();    // Returns the random shuffling of array [1,2,3]. Example: return [1, 3, 2]
 *
 * Constraints:
 * 1 <= nums.length <= 50
 * -10^6 <= nums[i] <= 10^6
 * All the elements of nums are unique.
 * At most 10^4 calls in total will be made to reset and shuffle.
 */
public class Shuffle_an_Array_384 {
    /**
     * round 2
     * Thinking:
     *  1.问题转化为如何把一个整型数组的数字随机打乱，并转换成一个随机的数组。还要保证性能。
     *  2.从源数组随机获取一个数字，然后追加到新数组的末尾。
     *  3.为了保证性能。源数组获取数字后，该数字与原数组的末尾数字互换，并把该数字追加到新数组末尾，最后删除源数组的末尾数字。
     *  4.源数组和目标数据可以是一个数组，通过巧妙的swap节省内存空间
     *
     */

    /**
     * 思路：
     * 1.每次随机从源数组中取出一个数字，然后从原数组中去掉这个数字，直到原数组为空。
     * 2.从数组中去掉一个随机数字，复杂度较高，可以使用swap的方式解决。
     * 3.数组分为两段[0,i],[i+1,n],前一段是已经随机取出的数字，后一段是未取出的数字。
     *
     * 验证通过：
     * Runtime: 76 ms, faster than 52.01% of Java online submissions for Shuffle an Array.
     * Memory Usage: 47.2 MB, less than 82.46% of Java online submissions for Shuffle an Array.
     */
    static class Solution {
        int[] values;

        public Solution(int[] nums) {
            values = nums;
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            return values;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            int[] shuffled = Arrays.copyOf(values, values.length);
            Random random = new Random();
            for (int i = 0; i < shuffled.length; i++) {
                int r = random.nextInt(shuffled.length - i) + i;
                //swap
                int t = shuffled[r];
                shuffled[r] = shuffled[i];
                shuffled[i] = t;
            }
            return shuffled;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution(new int[]{1, 2, 3});
        ArrayUtils.printIntArray(solution.shuffle());
        ArrayUtils.printIntArray(solution.reset());
        ArrayUtils.printIntArray(solution.shuffle());
        ArrayUtils.printIntArray(solution.shuffle());
        ArrayUtils.printIntArray(solution.shuffle());
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
}
