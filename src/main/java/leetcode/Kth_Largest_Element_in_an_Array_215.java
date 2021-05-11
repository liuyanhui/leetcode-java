package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 215. Kth Largest Element in an Array
 * Medium
 * ----------------------
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 *
 *  Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 *
 * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 * Constraints:
 * 1 <= k <= nums.length <= 10^4
 * -104 <= nums[i] <= 10^4
 */
public class Kth_Largest_Element_in_an_Array_215 {
    public static int findKthLargest(int[] nums, int k) {
        return findKthLargest_2(nums, k);
    }

    /**
     * 验证通过：
     * Runtime: 3 ms, faster than 66.58% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 39.4 MB, less than 41.51% of Java online submissions for Kth Largest Element in an Array.
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest_2(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int n : nums) {
            if (queue.size() < k) {
                queue.add(n);
            } else {
                if (queue.peek() <= n) {
                    queue.poll();
                    queue.add(n);
                }
            }
        }
        return queue.peek();
    }

    /**
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 97.59% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 39.3 MB, less than 41.51% of Java online submissions for Kth Largest Element in an Array.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest_1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 2, 1, 5, 6, 4}, 2, 5);
        do_func(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4);
    }

    private static void do_func(int[] nums, int k, int expected) {
        int ret = findKthLargest(nums, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
