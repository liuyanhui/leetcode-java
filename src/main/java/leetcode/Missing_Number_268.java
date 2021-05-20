package leetcode;

/**
 * 268. Missing Number
 * Easy
 * -------------------
 * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
 * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
 *
 *  Example 1:
 * Input: nums = [3,0,1]
 * Output: 2
 * Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the range since it does not appear in nums.
 *
 * Example 2:
 * Input: nums = [0,1]
 * Output: 2
 * Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the range since it does not appear in nums.
 *
 * Example 3:
 * Input: nums = [9,6,4,2,3,5,7,0,1]
 * Output: 8
 * Explanation: n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is the missing number in the range since it does not appear in nums.
 *
 * Example 4:
 * Input: nums = [0]
 * Output: 1
 * Explanation: n = 1 since there is 1 number, so all numbers are in the range [0,1]. 1 is the missing number in the range since it does not appear in nums.
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 10^4
 * 0 <= nums[i] <= n
 * All the numbers of nums are unique.
 */
public class Missing_Number_268 {
    public static int missingNumber(int[] nums) {
        return missingNumber_2(nums);
    }

    /**
     * Bit Manipulation 思路
     * 使用异或操作符，a^b^c^a^b=c
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Missing Number.
     * Memory Usage: 39.4 MB, less than 55.21% of Java online submissions for Missing Number.
     * @param nums
     * @return
     */
    public static int missingNumber_2(int[] nums) {
        //方案1
//        int t = 0;
//        for (int n : nums) {
//            t ^= n;
//        }
//        for (int i = 0; i <= nums.length; i++) {
//            t ^= i;
//        }
        //方案2
        int t = nums.length;
        for (int i = 0; i < nums.length; i++) {
            t = t ^ i ^ nums[i];
        }
        return t;
    }

    /**
     * 高斯公式思路
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Missing Number.
     * Memory Usage: 39.2 MB, less than 69.05% of Java online submissions for Missing Number.
     *
     * @param nums
     * @return
     */
    public static int missingNumber_1(int[] nums) {
        int t = nums.length * (nums.length + 1) / 2;
        for (int n : nums) {
            t -= n;
        }
        return t;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 0, 1}, 2);
        do_func(new int[]{0, 1}, 2);
        do_func(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}, 8);
        do_func(new int[]{0}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = missingNumber(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
