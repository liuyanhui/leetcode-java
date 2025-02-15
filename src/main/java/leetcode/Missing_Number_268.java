package leetcode;

/**
 * 268. Missing Number
 * Easy
 * -------------------
 * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
 * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
 * <p>
 * Example 1:
 * Input: nums = [3,0,1]
 * Output: 2
 * Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the range since it does not appear in nums.
 * <p>
 * Example 2:
 * Input: nums = [0,1]
 * Output: 2
 * Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the range since it does not appear in nums.
 * <p>
 * Example 3:
 * Input: nums = [9,6,4,2,3,5,7,0,1]
 * Output: 8
 * Explanation: n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is the missing number in the range since it does not appear in nums.
 * <p>
 * Example 4:
 * Input: nums = [0]
 * Output: 1
 * Explanation: n = 1 since there is 1 number, so all numbers are in the range [0,1]. 1 is the missing number in the range since it does not appear in nums.
 * <p>
 * Constraints:
 * n == nums.length
 * 1 <= n <= 10^4
 * 0 <= nums[i] <= n
 * All the numbers of nums are unique.
 */
public class Missing_Number_268 {
    public static int missingNumber(int[] nums) {
        return missingNumber_r3_1(nums);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 先排序，再查找
     * Time Compleixty: O(NlogN)
     * 2. bucket sort solution
     * 每个bucket大小为 1
     * Time Compleixty: O(N)
     * Space Compleixty: O(N)
     * 3. Bit Manupitation
     * concat(nums[], [0:n+1])之后的数组为arr，对arr的元素进行XOR，最后的结果就是所求。
     * 问题转化为：一个数组中除了一个数出现1次外，其余的数都出现2次，求这个数。
     * n^n=0，一个数跟它自己做XOR运算，结果为0。
     * Time Compleixty: O(2N)
     * Space Compleixty: O(1)
     * 4. 数学法。高斯公式。
     * 先求和 0~n，再依次减去nums
     *
     * <p>
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 45.63 MB Beats 16.02%
     *
     * @param nums
     * @return
     */
    public static int missingNumber_r3_1(int[] nums) {
        int res = 0;
        for (int i = 0; i <= nums.length; i++) {
            res ^= i;
        }
        for (int n : nums) {
            res ^= n;
        }

        return res;
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.数组复制排序去重法。使用新数组，把老数组的数字排序并插入到新数组中，在新数组中查找缺失的数字。时间复杂度：O(N)，空间复杂度：O(N)。
     * 2.排序法。先排序，再查找。时间复杂度：O(NlogN)，空间复杂度：O(1)。
     * 3.数学法。先计算总和，再依次减去数组中的数，剩下的就是所求。时间复杂度：O(N)，空间复杂度：O(1)。
     * 高斯公式
     * 4.Bit Manipulation 见missingNumber_2()
     * <p>
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
     * review round 2 ：XOR可以作为分类器，如：在一个整数集合中只有一个或几个少数的数字不满足普遍特征，这种问题需要用到分类器。XOR是一个很好的数字分类器。
     * Bit Manipulation 思路
     * 使用异或操作符，a^b^c^a^b=c
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Missing Number.
     * Memory Usage: 39.4 MB, less than 55.21% of Java online submissions for Missing Number.
     *
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
     * <p>
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
