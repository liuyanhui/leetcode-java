package leetcode;

/**
 * 303. Range Sum Query - Immutable
 * Easy
 * -----------------------------
 * Given an integer array nums, handle multiple queries of the following type:
 * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 * Implement the NumArray class:
 * NumArray(int[] nums) Initializes the object with the integer array nums.
 * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 *
 * Example 1:
 * Input
 * ["NumArray", "sumRange", "sumRange", "sumRange"]
 * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
 * Output
 * [null, 1, -1, -3]
 *
 * Explanation
 * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
 * numArray.sumRange(0, 2); // return (-2) + 0 + 3 = 1
 * numArray.sumRange(2, 5); // return 3 + (-5) + 2 + (-1) = -1
 * numArray.sumRange(0, 5); // return (-2) + 0 + 3 + (-5) + 2 + (-1) = -3
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -10^5 <= nums[i] <= 10^5
 * 0 <= left <= right < nums.length
 * At most 10^4 calls will be made to sumRange.
 */
public class Range_Sum_Query_Immutable_303 {
    /**
     * 参考思路：
     * https://leetcode.com/problems/range-sum-query-immutable/solution/ 之 Approach3
     *
     * sums数组中的元素是[0,i)，左闭右开的区间范围的数字之和，公式为：sums[i]=sums[i-1]+nums[i-1]
     * 所求公式为：sums[right+1]-sums[left]
     *
     * Time Complexity:O(1)
     * Space Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 100.00% of Java online submissions for Range Sum Query - Immutable.
     * Memory Usage: 41.7 MB, less than 67.53% of Java online submissions for Range Sum Query - Immutable.
     */
    class NumArray_2 {

        //左闭右开的sum
        int[] sums = null;

        public NumArray_2(int[] nums) {
            sums = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                sums[i + 1] = sums[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return sums[right + 1] - sums[left];
        }
    }

    /**
     * Time Complexity:O(n)
     * Space Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 105 ms, faster than 5.02% of Java online submissions for Range Sum Query - Immutable.
     * Memory Usage: 46.1 MB, less than 8.38% of Java online submissions for Range Sum Query - Immutable.
     */
    class NumArray {

        int[] values = null;

        public NumArray(int[] nums) {
            values = nums;
        }

        public int sumRange(int left, int right) {
            int ret = 0;
            for (int i = left; i <= right; i++) {
                ret += values[i];
            }
            return ret;
        }
    }

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
}
