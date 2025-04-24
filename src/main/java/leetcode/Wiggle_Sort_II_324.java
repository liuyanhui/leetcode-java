package leetcode;

import java.util.Arrays;

/**
 * 324. Wiggle Sort II
 * Medium
 * ---------------------
 * Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 * You may assume the input array always has a valid answer.
 * <p>
 * Example 1:
 * Input: nums = [1,5,1,1,6,4]
 * Output: [1,6,1,5,1,4]
 * Explanation: [1,4,1,5,1,6] is also accepted.
 * <p>
 * Example 2:
 * Input: nums = [1,3,2,2,3,1]
 * Output: [2,3,1,3,1,2]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 5000
 * It is guaranteed that there will be an answer for the given input nums.
 * <p>
 * Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
public class Wiggle_Sort_II_324 {
    public static void wiggleSort(int[] nums) {
        wiggleSort_r3_1(nums);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * 方案1：先排序，再分别反转前后两部分，最后交叉合并。需要单独处理特殊情况。wiggleSort_2()
     * 方案2：复制到新数组new_arr[]，new_arr[]排序，把new_arr[]分为两部分，按奇偶复制回原数组。wiggleSort_r3_2()
     * 方案3：复制到新数组new_arr[]，用中位数分割new_arr[]为两部分，把两部分交叉复制回原数组。wiggleSort_1()
     */
    public static void wiggleSort_r3_1(int[] nums) {
        Arrays.sort(nums);
        //复制前半部分
        int[] headArr = new int[nums.length - nums.length / 2];
        for (int i = 0; i < headArr.length; i++) {
            headArr[i] = nums[i];
        }
        //复制后半部分
        int[] tailArr = new int[nums.length / 2];
        for (int i = 0; i < tailArr.length; i++) {
            tailArr[i] = nums[i + headArr.length];
        }

        for (int i = 0; i < nums.length; i++) {
            //奇数穿插,[a1,a2,a3,a4,a5]+[b1,b2,b3,b4]-->[a1,b1,a2,b2,a3,b3,a4,b4,a5]
            if (nums.length % 2 == 1) {
                if (i % 2 == 0) {
                    nums[i] = headArr[i / 2];
                } else {
                    nums[i] = tailArr[i / 2];
                }
            } else {//偶数穿插,[a1,a2,a3,a4]+[b1,b2,b3,b4]-->[b1,a1,b2,a2,b3,a3,b4,a4]
                if (i % 2 == 0) {
                    nums[i] = tailArr[i / 2];
                } else {
                    nums[i] = headArr[i / 2];
                }
            }
        }
        //反转
        for (int i = 0; i < nums.length / 2; i++) {
            int t = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = t;
        }
    }

    public static void wiggleSort_r3_2(int[] nums) {
        int n = nums.length - 1;
        int[] newarr = Arrays.copyOf(nums, nums.length);
        Arrays.sort(newarr);
        for (int i = 1; i < nums.length; i += 2)
            nums[i] = newarr[n--];
        for (int i = 0; i < nums.length; i += 2)
            nums[i] = newarr[n--];
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.cn/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
     * <p>
     * round 2
     * Thinking：
     * 1.穷举各种解法，如：分治法；先排序再查找法；nums由少到多法等。
     * 2.先排序，把排序后的数组平分为两部分，然后把后半部分按顺序穿插到前半部分中。如{a,b,c}+{x,y,z}=>{a,x,b,y,c,z}
     * 3.为了处理[1,1,2,2,3,3]或[1,1,2,2,2,3]类似的用例，需要使穿插后尽可能分开。一种可行的办法是将前后两半部分反序后在穿插，是的重复的数尽可能分开。
     * round 3:【3.】的另一种算法是，先穿插(后半部分占第一个数的位置)再反转。
     * TIP:
     * 1.步骤3是关键。Step 3 is the key
     * 2.Reversing an array is magic and powerful. 数组反转的魔力
     * <p>
     * 这个思路与wiggleSort_1()的区别在于wiggleSort_1()没有进行完整的排序，因为只需要根据中位数进行移动和切割数据即可，无需排序。
     * 所以wiggleSort_1()的时间复杂度是O(N)，优于当前的O(N*logN)
     * <p>
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
     * <p>
     * 参考思路：
     * https://leetcode-cn.com/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
     * <p>
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
        ArrayUtils.printlnIntArray(nums);
        ArrayUtils.isSameThenPrintln(nums, expected);
        System.out.println("--------------");
    }
}
