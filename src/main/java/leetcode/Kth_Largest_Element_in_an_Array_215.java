package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 215. Kth Largest Element in an Array
 * Medium
 * ----------------------
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * <p>
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * <p>
 * You must solve it in O(n) time complexity.
 * <p>
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * <p>
 * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 * <p>
 * Constraints:
 * 1 <= k <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 */
public class Kth_Largest_Element_in_an_Array_215 {
    public static int findKthLargest(int[] nums, int k) {
        return findKthLargest_r3_2(nums, k);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 先排序再查找。
     * Time Complexity:O(N*logN)
     * 2. 即使不全部排序数组也能找到结果。尝试不排序或部分排序的方案。
     * 2.1. bucket sort
     * 每个bucket的大小为k，进行bucket sort；对最大的bucket进行排序；对排序后的bucket查找kth最大数。
     * mn=min(nums),mx=max(nums)
     * size=(mx-mn)/k+1
     * List[] bucket=new List[size]
     * bucket[nums[i]/k].add(nums[i])
     * Time Complexity:O(3N+klogk),最坏O(NlogN),最好O(N)
     * 3. k次最大值查找（冒泡排序的单次查找）
     * Time Complexity:O(N*k)
     * 4. 属于求kth分位数的问题。
     * 6. 堆排序。大小为k的heap。
     * 7. 基于 quick sort partion 的 Selection sort。此方案时间复杂度可以达到O(N)，为了避免最坏情况，可以对nums执行shuffle操作。
     * partion(l,r,k)为快速排序的一次partion
     * l=0,r=nums.length-1
     * WHILE l<r THEN
     *     m = partion(l,r,k)
     *     IF m==k THEN return nums[k]
     *     ELSE IF m>k THEN l=m+1
     *     ELSE IF m<k THEN r=m-1
     * 5. 参考
     * https://leetcode.com/problems/kth-largest-element-in-an-array/solutions/60294/solution-explained/
     */
    public static int findKthLargest_r3_2(int[] nums, int k) {
//        shuffle(nums);
        int l = 0, r = nums.length - 1;
        k = nums.length-k;
        int m = 0;
        while (l <= r) {
            m = partion_r3_2(nums, l, r);
            if (m > k) {
                r = m - 1;
            } else if (m < k) {
                l = m + 1;
            } else {
                return nums[k];
            }
        }
        return nums[k];
    }

    private static int partion_r3_2(int[] nums, int l, int r) {
        int t = l;
        while (l < r) {
            while (l < r && nums[t] < nums[r]) {
                r--;
            }
            swap(nums, t, r);
            t = r;
            while (l < r && nums[l] <= nums[t]) {
                l++;
            }
            swap(nums, l, t);
            t = l;
        }
        return l;
    }

    /**
     * 验证通过：
     * Runtime 61 ms Beats 50.14%
     * Memory 62.14 MB Beats 5.24%
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest_r3_1(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            heap.offer(nums[i]);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.peek();
    }


    /**
     * review round 2
     * <p>
     * 思考：
     * 1.先排序，再查找。时间复杂度O(NlogN)
     * 2.如果只是找到 kth largest，那么不用所有的数字有序。
     * 3.使用 quick sort 的分区办法，在O(KlogN)复杂度下，可以得到解。
     * 4.使用小顶堆的思路。小顶堆使用PriorityQueue即可。时间复杂度O(NlogK)
     * 5.看到一个 awesome 的方案，先把数组随机打乱，再采用quicksort分区方法，可以实现O9=O(N)复杂度。
     * <p>
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
     *
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
