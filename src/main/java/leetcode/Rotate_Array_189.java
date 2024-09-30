package leetcode;

/**
 * 套路
 * 189. Rotate Array
 * Medium
 * ----------------------------
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * <p>
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 * <p>
 * Follow up:
 * Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */
public class Rotate_Array_189 {
    public static void rotate(int[] nums, int k) {
        rotate_r3_1(nums, k);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     *
     * Thinking
     * 1. 方案1
     * 把末尾的k个数字缓存，把开头的n-k个数字移到末尾，把缓存的数字复制到开头。
     * 2. 方案2
     * 优化空间复杂度。每次从末尾移动一个到开头，且把前n-1个数字右移。
     * 3. 方案3
     * 先分别反转[0:k-1]和[k:n]为[k-1:0]和[n:k]，反转后的数组为[k-1,k-2,..,0,n,..,k]；再将数组整体反转
     * 过程示意如下：
     * [0,..,k-1,k,..,n] -> [k-1,k-2,..,0,n,..,k] -> [k,..n,,0,..,k-2,k-1]
     * 4. 方案4
     * 先复制到一个临时数组中，再把临时数组复制回输入数组
     *
     * 验证通过：
     *
     * @param nums
     * @param k
     */
    public static void rotate_r3_1(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1 - k);
        reverse(nums, nums.length - k, nums.length - 1);
        reverse(nums, 0, nums.length - 1);
    }

    private static void reverse(int[] nums, int i, int j) {
        while (i < j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
            i++;
            j--;
        }
    }

    /**
     * round 2
     * <p>
     * 更加简介明了的实现
     *
     * @param nums
     * @param k
     */
    public static void rotate_4(int[] nums, int k) {
        k = k % nums.length;
        reverseArray(nums, 0, nums.length - 1);
        reverseArray(nums, 0, k - 1);
        reverseArray(nums, k, nums.length - 1);
    }

    /**
     * round 2
     * <p>
     * 全部复制思路
     * 1.把nums复制到新建数组中，再根据rotate规则将数组复制回nums。
     * 时间复杂度O(N)，空间复杂度O(N)
     * <p>
     * 部分复制思路
     * 1.把需要rotate的数字从nums中移动到一个临时数组中
     * 2.nums中剩下的数字已到数组的末尾
     * 3.把临时数组中的数字移动到nums的头部
     * 时间复杂度O(N)，空间复杂度O(N)
     * <p>
     * 套路思路
     * 1.前半部分反转，不需要rotate的部分
     * 2.后半部分反转，需要rotate的部分
     * 3.整体反转
     * 时间复杂度O(N)，空间复杂度O(1)
     * <p>
     * 本题为套路思路
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 26.36% of Java online submissions for Rotate Array.
     * Memory Usage: 64.4 MB, less than 53.77% of Java online submissions for Rotate Array.
     *
     * @param nums
     * @param k
     */
    public static void rotate_3(int[] nums, int k) {
        int k1 = k % nums.length;//处理k超过数组长度的情况
        int l = 0, r = nums.length - 1 - k1;
        while (l < r) {
            swap(nums, l++, r--);
        }
        l = nums.length - k1;
        r = nums.length - 1;
        while (l < r) {
            swap(nums, l++, r--);
        }
        l = 0;
        r = nums.length - 1;
        while (l < r) {
            swap(nums, l++, r--);
        }
    }

    private static void swap(int[] nums, int l, int r) {
        int t = nums[r];
        nums[r] = nums[l];
        nums[l] = t;
    }

    /**
     * 套路
     * Space Complexity:O(1) in-place的方案
     * 1.把nums的[0,k)和[k:]分别反转
     * 2.把nums反转
     * <p>
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Array.
     * Memory Usage: 56.4 MB, less than 5.23% of Java online submissions for Rotate Array.
     *
     * @param nums
     * @param k
     */
    public static void rotate_2(int[] nums, int k) {
        //注意k可能大于nums.length
        k %= nums.length;
        //反转[0:k)
        int l = 0, r = nums.length - 1 - k;
        reverseArray(nums, l, r);
        //反转[k:）
        l = nums.length - k;
        r = nums.length - 1;
        reverseArray(nums, l, r);
        //反转nums
        l = 0;
        r = nums.length - 1;
        reverseArray(nums, l, r);
    }

    private static void reverseArray(int[] nums, int l, int r) {
        while (l < r) {
            int t = nums[r];
            nums[r] = nums[l];
            nums[l] = t;
            l++;
            r--;
        }
    }

    /**
     * 验证成功：
     * Runtime: 1 ms, faster than 47.83% of Java online submissions for Rotate Array.
     * Memory Usage: 56.2 MB, less than 12.39% of Java online submissions for Rotate Array.
     * <p>
     * Time Complexity:O(n)
     * Space Complexity:O(k)
     *
     * @param nums
     * @param k
     */
    public static void rotate_1(int[] nums, int k) {
        //注意k可能大于nums.length
        k %= nums.length;
        //截取后k个数字
        int[] cached = new int[k];
        int j = 0;
        for (int i = nums.length - k; i < nums.length; i++) {
            cached[j++] = nums[i];
        }
        //移动nums
        for (int i = nums.length - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        //cached中的数字复制到数组的头部
        for (int i = 0; i < k; i++) {
            nums[i] = cached[i];
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7}, 3, new int[]{5, 6, 7, 1, 2, 3, 4});
        do_func(new int[]{-1, -100, 3, 99}, 2, new int[]{3, 99, -1, -100});
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7}, 300, new int[]{2, 3, 4, 5, 6, 7, 1});

    }

    private static void do_func(int[] nums, int k, int[] expected) {
        rotate(nums, k);
        ArrayUtils.printlnIntArray(nums);
        ArrayUtils.isSameThenPrintln(nums, expected);
        System.out.println("--------------");
    }
}
