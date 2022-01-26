package leetcode;

import java.util.Arrays;

/**
 * 这里有金矿
 * https://leetcode.com/problems/sort-colors/
 * 75. Sort Colors
 * Medium
 * -------------------------
 * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
 * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
 * You must solve this problem without using the library's sort function.
 *
 *  Example 1:
 * Input: nums = [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 *
 * Example 2:
 * Input: nums = [2,0,1]
 * Output: [0,1,2]
 *
 * Example 3:
 * Input: nums = [0]
 * Output: [0]
 *
 * Example 4:
 * Input: nums = [1]
 * Output: [1]
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] is 0, 1, or 2.
 */
public class Sort_Colors_75 {
    public static void sortColors(int[] nums) {
        sortColors_3(nums);
    }

    /**
     * round 2
     *
     * 荷兰三色旗法
     * 设[:i)为red区间，[i:k)为white区间，[k,j]为未知区间，(j:]为blue区间
     * 初始时：i=0,k=0,j=len(nums)-1
     * 遍历nums
     * IF nums[k]==0 THEN swap(i,k);i++;k++;
     * ELSE IF nums[k]==1 THEN k++;
     * ELSE IF nums[k]==2 THEN swap(k,j);j--;//此时通过swap后k是未知状态，所以k不变
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 26.45% of Java online submissions for Sort Colors.
     * Memory Usage: 39.5 MB, less than 9.82% of Java online submissions for Sort Colors.
     *
     * @param nums
     */
    public static void sortColors_3(int[] nums) {
        int i = 0, k = 0, j = nums.length - 1;
        while (k <= j) {
            if (nums[k] == 0) {
                swap(nums, i, k);
                i++;
                k++;
            } else if (nums[k] == 1) {
                k++;
            } else if (nums[k] == 2) {
                swap(nums, k, j);
                j--;
            }
        }
    }

    /**
     * two pass 方案
     * 问题转化为只有三个数字的数组的排序问题
     * first pass先统计每个数字出现次数；second pass根据出现次数重写数组
     *
     * 参考思路：
     * https://leetcode.com/problems/sort-colors/discuss/26500/Four-different-solutions
     *
     * @param nums
     */
    public static void sortColors_2(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int num0 = 0, num1 = 0, num2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) num0++;
            else if (nums[i] == 1) num1++;
            else if (nums[i] == 2) num2++;
        }
        for (int i = 0; i < num0; i++) nums[i] = 0;
        for (int i = 0; i < num1; i++) nums[i + num0] = 1;
        for (int i = 0; i < num2; i++) nums[i + num0 + num1] = 2;
    }

    /**
     * if n[cur]==0 then swap(l,cur)  l++ cur++
     * else if n[cur]==2 then swap(cur,r)  r--
     * else if n[cur]==1 then cur++
     * 我：通过一次上面的操作之后num[l]只能是0或1.nums[0:l)全部是0，nums[l:cur)全部是1，nums(r:]全部是2，nums(curl:r]是待计算部分，并且nums[l]只能是0或1
     *
     * 关键字：Dutch National Flag Problem
     *
     * 参考思路：
     * https://leetcode.com/problems/sort-colors/discuss/26481/Python-O(n)-1-pass-in-place-solution-with-explanation
     * https://leetcode.com/problems/sort-colors/discuss/26474/Sharing-C%2B%2B-solution-with-Good-Explanation
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 38.7 MB, less than 13.40% of Java online submissions for Sort Colors.
     * @param nums
     */
    public static void sortColors_1(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int l = 0, r = nums.length - 1;
        int current = l;
        while (current <= r && l <= r) {
            if (nums[current] == 0) {
                // tip:这里nums[l]只能是0或1，即cur遍历过的元素只能是0或1，即nums[0:cur]只能是0或1
                swap(nums, l, current);
                //下面的代码用于替换swap()
                // beg
//                nums[current]=nums[l];
//                nums[l] = 0;
                // end

                l++;
                current++;
            } else if (nums[current] == 1) {
                current++;
            } else if (nums[current] == 2) {
                swap(nums, current, r);
                //下面的代码用于替换swap()
                // beg
//                nums[current]=nums[r];
//                nums[r] = 2;
                // end
                r--;
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }


    public static void main(String[] args) {
        do_func(new int[]{2, 0, 2, 1, 1, 0}, new int[]{0, 0, 1, 1, 2, 2});
        do_func(new int[]{2, 0, 1}, new int[]{0, 1, 2});
        do_func(new int[]{0}, new int[]{0});
        do_func(new int[]{1}, new int[]{1});
        do_func(new int[]{2, 1, 1}, new int[]{1, 1, 2});
        do_func(new int[]{2, 2, 0, 2, 1, 1, 0, 2, 2}, new int[]{0, 0, 1, 1, 2, 2, 2, 2, 2});
        do_func(new int[]{2, 2, 1, 2, 1, 1, 1, 2, 2}, new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2});
        do_func(new int[]{0, 0, 2, 2, 1, 2, 1, 1, 1, 2, 2}, new int[]{0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2});
    }

    private static void do_func(int[] nums, int[] expected) {
        sortColors(nums);
        ArrayUtils.printIntArray(nums);
        System.out.println("");
        System.out.println(Arrays.equals(nums, expected));
        System.out.println("--------------");
    }
}
