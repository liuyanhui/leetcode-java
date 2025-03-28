package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 315. Count of Smaller Numbers After Self
 * Hard
 * --------------------------------
 * Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements to the right of nums[i].
 *
 * Example 1:
 * Input: nums = [5,2,6,1]
 * Output: [2,1,1,0]
 * Explanation:
 * To the right of 5 there are 2 smaller elements (2 and 1).
 * To the right of 2 there is only 1 smaller element (1).
 * To the right of 6 there is 1 smaller element (1).
 * To the right of 1 there is 0 smaller element.
 *
 * Example 2:
 * Input: nums = [-1]
 * Output: [0]
 *
 * Example 3:
 * Input: nums = [-1,-1]
 * Output: [0,0]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Count_of_Smaller_Numbers_After_Self_315 {
    public static List<Integer> countSmaller(int[] nums) {
        return countSmaller_1(nums);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     * ----------------
     * Thinking
     * 1. naive solution
     * brute force
     * 依次计算每个元素
     * Time Complexity: O(N*N)
     * 2.从后向前遍历+排序
     * 从后向前遍历，遍历时对nums[i]排序，再根据排序后的下标计算结果
     * Time Complexity: O(N*logN)
     *
     * [20250328] It might be cost a lot of time to finish this problem. So I give up solving it .
     */

    static class ArrayWithOriginalIdx {
        int val;
        int orginalIdx;

        public ArrayWithOriginalIdx(int val, int orginalIdx) {
            this.val = val;
            this.orginalIdx = orginalIdx;
        }
    }

    /**
     * review
     * 参考资料：
     * https://leetcode.com/problems/count-of-smaller-numbers-after-self/solutions/445769/merge-sort-clear-simple-explanation-with-examples-o-n-lg-n/
     *
     * 思路简介：
     * 合并排序merge sort法。在排序过程中，如果发生顺序变化，表示存在右边小于左边数字的情况，这样只需要记录每个位置在排序过程中的变更次数即可。
     * 先把数字原本的序号记录下来，然后排序，最后在根据排序后的序号和排序前的序号计算最终解。
     *
     * @param nums
     * @return
     */
    public static List<Integer> countSmaller_1(int[] nums) {
        List<Integer> res = new ArrayList<>(nums.length);
        ArrayWithOriginalIdx[] arr = new ArrayWithOriginalIdx[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res.add(0);
            arr[i] = new ArrayWithOriginalIdx(nums[i], i);
        }

        mergeSort(arr, res, 0, nums.length - 1);
        return res;
    }

    private static void mergeSort(ArrayWithOriginalIdx[] nums, List<Integer> res, int beg, int end) {
        if (beg >= end)
            return;
        int mid = (beg + end) / 2;
        mergeSort(nums, res, beg, mid);
        mergeSort(nums, res, mid + 1, end);
        //merge the two sorted sub arrays and record the counts array
        int i = beg;
        int j = mid + 1;
        ArrayWithOriginalIdx[] sorted = new ArrayWithOriginalIdx[end - beg + 1];//save the sorted result array
        int signal = 0;//这个变量很关键。记录了从右半部分数字在归并排序过程中大于左半部分数字的次数。
        int cur = 0;
        while (i <= mid && j <= end) {
            if (nums[i].val <= nums[j].val) {
                res.set(nums[i].orginalIdx, res.get(nums[i].orginalIdx) + signal);//这个变量很关键。
                sorted[cur] = nums[i];
                i++;
            } else if (nums[i].val > nums[j].val) {
                sorted[cur] = nums[j];
                signal++;//这个变量很关键。记录了从右半部分数字在归并排序过程中大于左半部分数字的次数。
                j++;
            }
            cur++;
        }
        while (i <= mid) {
            res.set(nums[i].orginalIdx, res.get(nums[i].orginalIdx) + signal);//这个变量很关键。
            sorted[cur++] = nums[i++];
        }
        while (j <= end) {
            sorted[cur++] = nums[j++];
        }
        //flush the sorted array to the source array
        for (int k = beg; k <= end; k++) {
            nums[k] = sorted[k - beg];
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{5, 2, 6, 1}, Arrays.asList(new Integer[]{2, 1, 1, 0}));
        do_func(new int[]{-1}, Arrays.asList(new Integer[]{0}));
        do_func(new int[]{-1, -1}, Arrays.asList(new Integer[]{0, 0}));
        do_func(new int[]{2, 0, 1}, Arrays.asList(new Integer[]{2, 0, 0}));
    }

    private static void do_func(int[] nums, List<Integer> expected) {
        List<Integer> ret = countSmaller(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
