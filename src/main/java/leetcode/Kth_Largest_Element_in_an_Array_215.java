package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 215. Kth Largest Element in an Array
 * Medium
 * ----------------------
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 *
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * You must solve it in O(n) time complexity.
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
 * -10^4 <= nums[i] <= 10^4
 */
public class Kth_Largest_Element_in_an_Array_215 {
    public static int findKthLargest(int[] nums, int k) {
        return findKthLargest_3(nums, k);
    }

    /**
     * review round 2
     *
     * 思考：
     * 1.先排序，再查找。时间复杂度O(NlogN)
     * 2.如果只是找到kth lagest，那么不用所有的数字有序。
     * 3.使用quick sort的分区办法，在O(KlogN)复杂度下，可以得到解。
     * 4.使用小顶堆的思路。小顶堆使用PriorityQueue即可。时间复杂度O(NlogK)
     * 5.看到一个awsome的方案，先把数组随机打乱，再采用quicksort分区方法，可以实现O9=O(N)复杂度。
     *
     * 验证通过：
     * Runtime: 26 ms, faster than 23.01% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 66.7 MB, less than 16.99% of Java online submissions for Kth Largest Element in an Array.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest_3(int[] nums, int k) {
        shuffle(nums);
        k = nums.length - k;//升序排序需要转换
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int p = partition(nums, l, r);
            if (p < k) {
                l = p + 1;
            } else if (k < p) {
                r = p - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private static int partition(int[] nums, int l, int r) {
        int t = l;
        while (l < r) {
            //先从右边开始
            while (t < r && nums[t] <= nums[r]) {
                r--;
            }
            swap(nums, t, r);
            t = r;
            //再从左边开始
            while (l < t && nums[l] < nums[t]) {
                l++;
            }
            swap(nums, l, t);
            t = l;
        }
        return t;
    }

    private static void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    }

    private static void shuffle(int[] nums) {
        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            int n = random.nextInt(nums.length);
//            System.out.println("random:"+n);
            swap(nums, i, n);
        }
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
        do_func(new int[]{7, 6, 5, 4, 3, 2, 1}, 2, 6);
        do_func(new int[]{-1, 2, 0}, 3, -1);
    }

    private static void do_func(int[] nums, int k, int expected) {
        int ret = findKthLargest(nums, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
