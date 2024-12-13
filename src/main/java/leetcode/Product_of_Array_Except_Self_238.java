package leetcode;

/**
 * 238. Product of Array Except Self
 * Medium
 * ----------------------
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * <p>
 * Example 2:
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 * <p>
 * Constraints:
 * 2 <= nums.length <= 10^5
 * -30 <= nums[i] <= 30
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * <p>
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */
public class Product_of_Array_Except_Self_238 {
    public static int[] productExceptSelf(int[] nums) {
        return productExceptSelf_r3_1(nums);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * brute force 暴力法。
     * 一次计算每个i的值。
     * Time Complexity: O(N*N)
     * Space Complexity: O(1)
     * 2. 两个数组方案
     * 使用两个数组保存中间数据 arr1 arr2
     * arr1[i] = nums[0]*nums[1]*..*nums[i-1]
     * arr2[i] = nums[i+1]*..*nums[-2]*nums[-1]
     * res[i] = arr1[i]*arr2[i]就是所求
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     * 3. 方案[2.]的空间复杂度优化方案
     * arr2[i] = nums[i+1]*..*nums[-2]*nums[-1]
     * tmp = nums[0]*nums[1]*..*nums[i-1]
     * arr2[i] = tmp * arr2[i]
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 89.35%
     * Memory 55.44 MB Beats 50.05%
     *
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_r3_1(int[] nums) {
        int[] res = new int[nums.length];
        //计算从右向左的累乘，并缓存
        res[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            res[i] = nums[i + 1] * res[i + 1];
        }
        //从左向右计算结果
        int t = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i] * t;
            t *= nums[i];
        }
        return res;
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.暴力法。不满足题目要求，要么必须使用除法，要么O(N*N)复杂度。
     * 2.分治法。分别计算nums[0:i-1]和nums[i+1:]的乘积，两者相乘就是结果集中第i个的结果。公式为：F(i)=Product[0:i-1]*Product[i+1:]。
     * 时间复杂度O(3N)，空间复杂度O(2N)
     * 3.在"2.分治法"的基础上可以优化空间复杂度。分两轮计算：第一轮从前往后，计算[0:i-1]，结果保存在结果集；第二轮，在第一轮的基础上，从后往前计算[i+1:]，直接在结果集上计算。
     * 时间复杂度O(2N)，空间复杂度O(N)
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Product of Array Except Self.
     * Memory Usage: 50.2 MB, less than 96.45% of Java online submissions for Product of Array Except Self.
     *
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_3(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int t = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            t *= nums[i + 1];
            res[i] = res[i] * t;
        }
        return res;
    }

    /**
     * Space Complexity:O(1)的方案
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Product of Array Except Self.
     * Memory Usage: 49.4 MB, less than 83.65% of Java online submissions for Product of Array Except Self.
     *
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
     * = nums[i-1]*left[i-1]
     * right[i] = nums[i-1]*nums[1-2]*...*nums[i+1]
     * = nums[i+1]*right[i+1]
     * <p>
     * Time Complexity:O(3n)
     * Space Complexity:O(3n)
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Product of Array Except Self.
     * Memory Usage: 51.8 MB, less than 26.45% of Java online submissions for Product of Array Except Self.
     *
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
        ArrayUtils.printlnIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
