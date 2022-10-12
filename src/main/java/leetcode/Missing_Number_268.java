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
        return missingNumber_3(nums);
    }

    /**
     * round 2
     *
     * 思考：
     * 1.数组复制排序去重法。使用新数组，把老数组的数字排序并插入到新数组中，在新数组中查找缺失的数字。时间复杂度：O(N)，空间复杂度：O(N)。
     * 2.排序法。先排序，再查找。时间复杂度：O(NlogN)，空间复杂度：O(1)。
     * 3.数学法。先计算总和，再依次减去数组中的数，剩下的就是所求。时间复杂度：O(N)，空间复杂度：O(1)。
     * 高斯公式
     * 4.Bit Manipulation 见missingNumber_2()
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 84.98% of Java online submissions for Missing Number.
     * Memory Usage: 50.4 MB, less than 82.71% of Java online submissions for Missing Number.
     *
     * @param nums
     * @return
     */
    public static int missingNumber_3(int[] nums) {
        int total = (nums.length + 1) * nums.length / 2;
        for (int n : nums) {
            total -= n;
        }
        return total;
    }

    /**
     * review round 2
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
