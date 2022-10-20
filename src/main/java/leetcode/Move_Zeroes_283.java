package leetcode;

/**
 * 283. Move Zeroes
 * Easy
 * --------------------
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * Note that you must do this in-place without making a copy of the array.
 *
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 *
 * Follow up: Could you minimize the total number of operations done?
 */
public class Move_Zeroes_283 {
    public static void moveZeroes(int[] nums) {
        moveZeroes_2(nums);
    }

    /**
     * round 2
     *
     * 算法：
     * 计算过程中数组分为三部分：[0,left]已经计算好的非零数字部分；(left,right]是全部为0的部分；(right,~]未计算部分。
     * left=0,right=0
     * while (right <= nums.length){
     *     IF nums[right]==0 THEN right++
     *     ELSE swap(nums[left],nums[right]);left++
     * }
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Move Zeroes.
     * Memory Usage: 43.1 MB, less than 17.65% of Java online submissions for Move Zeroes.
     *
     * @param nums
     */
    public static void moveZeroes_3(int[] nums) {
        int l = 0, r = 0;
        while (r < nums.length) {
            if (nums[r] != 0) {
                nums[l] = nums[r];
                l++;
            }
            r++;
        }
        while (l < nums.length) {
            nums[l++] = 0;
        }
    }

    /**
     * 数组元素分为三部分：1最左侧已经非0数字，2中间全部是0的数字，3最右侧未处理的数字
     * 用i,j将数组分割成三部分:[0,i),[i,j),[j,)
     * 初始i=j=0;
     * if nums[j]==0 then j++
     * else swap(i,j), i++,j++
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Move Zeroes.
     * Memory Usage: 39.2 MB, less than 46.85% of Java online submissions for Move Zeroes.
     *
     * @param nums
     */
    public static void moveZeroes_2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int i = 0, j = 0;
        for (; j < nums.length; j++) {
            if (nums[j] != 0) {
                if (j != i) {
                    int t = nums[j];
                    nums[j] = nums[i];
                    nums[i] = t;
                }
                i++;
            }
        }
    }

    /**
     * 遍历数组，遇到0，把后面的数字依次迁移一位
     * 大概率会超时
     * Time Complexity:O(n*n)
     *
     * @param nums
     */
    public static void moveZeroes_1(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int count = 0;
        for (int i = 0; i < nums.length - count; i++) {
            if (nums[i] == 0) {
                //移动
                for (int j = i + 1; j < nums.length - count; j++) {
                    nums[j - 1] = nums[j];
                    if (j == nums.length - count - 1) {
                        nums[j] = 0;
                    }
                }
                count++;
            }
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{0, 1, 0, 3, 12}, new int[]{1, 3, 12, 0, 0});
        do_func(new int[]{0}, new int[]{0});
        do_func(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        do_func(new int[]{0, 1, 2, 3, 0}, new int[]{1, 2, 3, 0, 0});
    }

    private static void do_func(int[] nums, int[] expected) {
        moveZeroes(nums);
        ArrayUtils.printlnIntArray(nums);
        System.out.println(ArrayUtils.isSame(nums, expected));
        System.out.println("--------------");
    }
}
