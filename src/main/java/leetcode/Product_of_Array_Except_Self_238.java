package leetcode;

/**
 * 238. Product of Array Except Self
 * Medium
 * ----------------------
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 *
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 *
 * Example 2:
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 *
 * Constraints:
 * 2 <= nums.length <= 10^5
 * -30 <= nums[i] <= 30
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 *
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */
public class Product_of_Array_Except_Self_238 {
    public static int[] productExceptSelf(int[] nums) {
        return productExceptSelf_2(nums);
    }

    /**
     * Space Complexity:O(1)的方案
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Product of Array Except Self.
     * Memory Usage: 49.4 MB, less than 83.65% of Java online submissions for Product of Array Except Self.
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_2(int[] nums) {
        int[] ret = new int[nums.length];
        //先计算左边的乘积
        ret[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            ret[i] = ret[i - 1] * nums[i - 1];
        }
        //再计算右边的乘积，并乘以左边的乘积
        int t = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            //计算右边的乘积
            t *= nums[i + 1];
            //左边乘以右边
            ret[i] = ret[i] * t;
        }
        return ret;
    }

    /**
     * 思路：
     * 分治法，product[i]=left[i]*right[i]。
     * 其中，left[i] = nums[0]*nums[1]*...*nums[i-1]
     *              = nums[i-1]*left[i-1]
     *      right[i] = nums[i-1]*nums[1-2]*...*nums[i+1]
     *               = nums[i+1]*right[i+1]
     *
     * Time Complexity:O(3n)
     * Space Complexity:O(3n)
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Product of Array Except Self.
     * Memory Usage: 51.8 MB, less than 26.45% of Java online submissions for Product of Array Except Self.
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_1(int[] nums) {
        int[] ret = new int[nums.length];
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        left[0] = 1;
        right[nums.length - 1] = 1;
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            ret[i] = left[i] * right[i];
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4}, new int[]{24, 12, 8, 6});
        do_func(new int[]{-1, 1, 0, -3, 3}, new int[]{0, 0, 9, 0, 0});
    }

    private static void do_func(int[] nums, int[] expected) {
        int[] ret = productExceptSelf(nums);
        ArrayUtils.printIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
