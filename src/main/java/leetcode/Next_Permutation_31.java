package leetcode;

/**
 * 31. Next Permutation
 * Medium
 * -------------------------
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 *
 * If such an arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).
 *
 * The replacement must be in place and use only constant extra memory.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,3,2]
 *
 * Example 2:
 * Input: nums = [3,2,1]
 * Output: [1,2,3]
 *
 * Example 3:
 * Input: nums = [1,1,5]
 * Output: [1,5,1]
 *
 * Example 4:
 * Input: nums = [1]
 * Output: [1]
 *
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 */
public class Next_Permutation_31 {
    /**
     * round2
     * 关键：分析出算法模型
     * 思路：
     * 1.从后向前查找，找到第一个单调减少的元素n[i]，并且与其右边[i+1:]的从右向左第一个大于它的元素互换
     * 2.反转n[i+1:]的元素
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Next Permutation.
     * Memory Usage: 39.1 MB, less than 82.70% of Java online submissions for Next Permutation.
     *
     * @param nums
     */
    public static void nextPermutation(int[] nums) {
        int i = nums.length - 1, j = nums.length - 1;
        //从后向前查找，找到第一个单调减少的元素nums[i]
        while (i > 0 && nums[i - 1] >= nums[i--]) ;
        //在[i+1:]中找到从右向左第一个大于nums[i]的元素互换
        while (j > i && nums[i] >= nums[j]) {
            j--;
        }
        swap(nums, i, j);
        //反转[i+1:]的元素
        int l = (i == j ? 0 : i + 1);//用例[3, 2, 1]的特殊情况
        int r = nums.length - 1;
        while (l < r) {
            swap(nums, l++, r--);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, new int[]{1, 3, 2});
        do_func(new int[]{3, 2, 1}, new int[]{1, 2, 3});
        do_func(new int[]{1, 1, 5}, new int[]{1, 5, 1});
        do_func(new int[]{1}, new int[]{1});
        do_func(new int[]{1, 3, 2, 1}, new int[]{2, 1, 1, 3});
        do_func(new int[]{1, 20, 3}, new int[]{3, 1, 20});
        do_func(new int[]{1, 6, 9, 6, 1, 9, 9, 6}, new int[]{1, 6, 9, 6, 6, 1, 9, 9});
        do_func(new int[]{3, 2, 1, 3, 2, 1}, new int[]{3, 2, 2, 1, 1, 3});
        do_func(new int[]{1, 2, 3, 2, 1}, new int[]{1, 3, 1, 2, 2});
        do_func(new int[]{19, 19, 19, 19, 19, 19}, new int[]{19, 19, 19, 19, 19, 19});
    }

    private static void do_func(int[] nums, int[] expected) {
        nextPermutation(nums);
        ArrayUtils.printIntArray(nums);
        ArrayUtils.isSameThenPrintln(nums, expected);
        System.out.println("--------------");
    }
}
