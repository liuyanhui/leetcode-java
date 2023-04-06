package leetcode;

import java.util.Arrays;

/**
 * 324. Wiggle Sort II
 * Medium
 * ---------------------
 * Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 * You may assume the input array always has a valid answer.
 *
 * Example 1:
 * Input: nums = [1,5,1,1,6,4]
 * Output: [1,6,1,5,1,4]
 * Explanation: [1,4,1,5,1,6] is also accepted.
 *
 * Example 2:
 * Input: nums = [1,3,2,2,3,1]
 * Output: [2,3,1,3,1,2]
 *
 *  Constraints:
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 5000
 * It is guaranteed that there will be an answer for the given input nums.
 *
 * Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
public class Wiggle_Sort_II_324 {
    public static void wiggleSort(int[] nums) {
        wiggleSort_2(nums);
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.cn/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
     *
     * round 2
     * Thinking：
     * 1.穷举各种解法，如：分治法；先排序再查找法；nums由少到多发等。
     * 2.先排序，把排序后的数组平分为两部分，然后把后半部分按顺序穿插到前半部分中。如{a,b,c}+{x,y,z}=>{a,x,b,y,c,z}
     * 3.为了处理[1,1,2,2,3,3]或[1,1,2,2,2,3]类似的用例，需要使穿插后尽可能分开。一种可行的办法是将前后两半部分反序后在穿插，是的重复的数尽可能分开。
     *
     * TIP:
     * 1.步骤3是关键。Step 3 is the key
     * 2.Reversing an array can seem like magic. 数组反转的魔力
     *
     * 验证通过：
     * Runtime 4 ms Beats 95.24%
     * Memory 46.2 MB Beats 52.45%
     *
     * @param nums
     */
    public static void wiggleSort_2(int[] nums) {
        Arrays.sort(nums);
        int frontLen = nums.length / 2 + (nums.length % 2 == 0 ? 0 : 1);
        int[] front = new int[frontLen];
        int[] end = new int[nums.length / 2];
        for (int i = 0; i < frontLen; i++) {
            //反转存储
            front[front.length - 1 - i] = nums[i];
        }

        for (int i = frontLen; i < nums.length; i++) {
            //反转存储
            end[end.length - 1 - (i - frontLen)] = nums[i];

        }
        for (int i = 0; i < end.length; i++) {
            nums[i * 2] = front[i];
            nums[i * 2 + 1] = end[i];
        }
        if (nums.length % 2 != 0) {
            nums[nums.length - 1] = front[front.length - 1];
        }
    }

    /**
     * 这是个复合题，涉及到快速排序(查找中位数，查找第K大的数)、荷兰国旗等算法。
     *
     * 参考思路：
     * https://leetcode-cn.com/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
     *
     * 验证通过：
     * Runtime: 47 ms, faster than 18.45% of Java online submissions for Wiggle Sort II.
     * Memory Usage: 42.1 MB, less than 33.10% of Java online submissions for Wiggle Sort II.
     *
     * @param nums
     */
    public static void wiggleSort_1(int[] nums) {
        //找到中位数，并将小于中位数的放在其左侧，大于等于中位数的放在其右侧
        int medium = findKthMin(nums, (nums.length - 1) / 2, 0, nums.length - 1);

        //把与中位数相同的数字移动到中间的位置，否则无法通过用例[1, 2, 2, 2, 2, 3, 5, 4, 5, 5, 3, 3]
        //因为中位数的位置没有规律，后续的穿插操作总有可能出现连续中位数的情况。
        reverse(nums, medium);

        int[] copy = Arrays.copyOf(nums, nums.length);
        //根据中位数把数组分为前后两部分（中位数在前半部分中），把两部分前后反转后，穿插插入新数组中
        //这里没有执行反转操作，直接根据公式插入新数组中
        for (int i = 0; i < (nums.length + 1) / 2; i++) {
            nums[i * 2] = copy[(nums.length - 1) / 2 - i];
        }

        for (int i = 0; i < nums.length / 2; i++) {
            nums[i * 2 + 1] = copy[nums.length - 1 - i];
        }
    }

    //review
    //荷兰国旗法，移动元素。[0,i)的元素<medium，[i,j)的元素==medium，[j,k]的元素未知，(k:n]的元素>medium
    private static void reverse(int[] nums, int medium) {
        int i = 0, j = 0, k = nums.length - 1;
        while (j < k) {
            if (nums[j] < medium) {
                i++;
                j++;
            } else if (nums[j] > medium) {
                swap(nums, j, k);
                k--;
            } else {
                j++;
            }
        }
    }

    //找到第K小的数
    private static int findKthMin(int[] nums, int kth, int beg, int end) {
        if (beg >= end) return nums[beg];
        int i = beg, j = end;
        int t = nums[beg];
        while (i < j) {
            while (i < j && t <= nums[j]) {
                j--;
            }
            if (t > nums[j]) {
                swap(nums, i, j);
                i++;
            }
            while (i < j && t > nums[i]) {
                i++;
            }
            if (t < nums[i]) {
                swap(nums, i, j);
                j--;
            }
        }
        nums[i] = t;
        if (i > kth) {
            //注意这里一直都是kth，而不是i-kth，因为i和kth都是下标
            return findKthMin(nums, kth, beg, i - 1);
        } else if (i < kth) {
            //注意这里一直都是kth，而不是kth-i，因为i和kth都是下标
            return findKthMin(nums, kth, i + 1, end);
        } else {
            return t;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 5, 1, 1, 6, 4}, new int[]{1, 4, 1, 6, 1, 5});
        do_func(new int[]{1, 3, 2, 2, 3, 1}, new int[]{2, 3, 1, 3, 1, 2});
        do_func(new int[]{4, 5, 5, 6}, new int[]{5, 6, 4, 5});
        do_func(new int[]{1, 3, 2, 2, 3, 1, 7}, new int[]{2, 3, 2, 7, 1, 3, 1});
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 5, 3, 7, 2, 6, 1});
        do_func(new int[]{1, 1, 1, 1, 2, 2, 2, 2}, new int[]{1, 2, 1, 2, 1, 2, 1, 2});
        do_func(new int[]{1, 1, 1, 1, 1, 2, 2, 2, 2}, new int[]{1, 2, 1, 2, 1, 2, 1, 2, 1});
        do_func(new int[]{1, 1, 2, 2, 3, 3}, new int[]{2, 3, 1, 3, 1, 2});
        do_func(new int[]{3, 2, 2, 1, 2, 1}, new int[]{2, 3, 1, 2, 1, 2});
        do_func(new int[]{1, 1, 3, 1, 3, 2, 1, 3, 2, 1}, new int[]{1, 2, 1, 3, 1, 3, 1, 3, 1, 2});
        do_func(new int[]{1, 2, 3, 3, 5, 5, 2, 4, 5, 2, 3, 2}, new int[]{3, 5, 2, 4, 2, 5, 2, 5, 2, 3, 1, 3});
    }

    private static void do_func(int[] nums, int[] expected) {
        wiggleSort(nums);
        ArrayUtils.printIntArray(nums);
        ArrayUtils.isSameThenPrintln(nums, expected);
        System.out.println("--------------");
    }
}
