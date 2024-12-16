package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

/**
 * 239. Sliding Window Maximum
 * Hard
 * --------------------------
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 * <p>
 * Return the max sliding window.
 * <p>
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class Sliding_Window_Maximum_239 {

    public static int[] maxSlidingWindow(int[] nums, int k) {
        return maxSlidingWindow_2(nums, k);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * brute force 暴力法。
     * 在窗口滑动的同时，计算每个窗口的最大值。
     * Time Complexity: O(N*K)
     * 2. 关于时间复杂度的优化路线为：
     * N基本不可能被优化，只能优化K
     * O(N*K) -> O(N*logK) -> O(N)
     * 时间复杂度为：O(N*logK) 时，采用TreeMap缓存数字和其出现次数。
     * maxSlidingWindow_1()就是这种思路
     *
     * maxSlidingWindow_2 和 maxSlidingWindow_3 较难理解
     *
     */
    /**
     * review
     * AC中更优的耗时13ms的实现
     * https://leetcode.com/problems/sliding-window-maximum/discuss/65881/O(n)-solution-in-Java-with-two-simple-pass-in-the-array
     * 太笨了没理解
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_3(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];
        if (k == 1) return nums;

        int[] left = new int[n];
        left[0] = nums[0];
        int[] right = new int[n];
        right[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            // from left to right
            if (i % k == 0) left[i] = nums[i];  // block_start
            else left[i] = Math.max(left[i - 1], nums[i]);

            // from right to left
            int j = n - i - 1;
            if ((j + 1) % k == 0) right[j] = nums[j];  // block_end
            else right[j] = Math.max(right[j + 1], nums[j]);
        }

        int[] output = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++)
            output[i] = Math.max(left[i + k - 1], right[i]);

        return output;
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation
     * <p>
     * 非常巧妙的思路。
     * 1.通过使用提前去掉不满足条件的数字，使得Deque中的数字一定是局部最优(到i为止)解空间中的。
     * 2.使用了排除法，先去掉一定不在解空间中的数字。
     * <p>
     * 思路：
     * 1.不在滑动窗口内的要排除
     * 2.如果n[i]<n[a]并且i<a，那么n[i]一定不满足条件
     * 3.用Dequeue或者LinkedList保存窗口数据的下标
     * 4.Dequeue中的第一个数字就是当前窗口的最大值
     * <p>
     * 验证通过：
     * Runtime: 98 ms, faster than 20.96% of Java online submissions for Sliding Window Maximum.
     * Memory Usage: 143.7 MB, less than 72.97% of Java online submissions for Sliding Window Maximum.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_2(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        int idx = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while (!queue.isEmpty() && queue.peekFirst() <= i - k) {
                queue.pollFirst();
            }
            while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
                queue.pollLast();
            }
            queue.offer(i);
            if (k - 1 <= i) {
                res[idx++] = nums[queue.peek()];
            }
        }
        return res;
    }

    /**
     * 思考：
     * 1.直觉思路。窗口滑动时依次计算最大值，维护一个大小为k的MaxHeap。使用Java语言中的TreeMap，免去实现HeapSort。其中，key=数字，value=出现的次数。
     * 时间复杂度为：O((N-K)*logK)，空间复杂度O(K)
     * 2.如何利用sliding window的FIFO特性？
     * <p>
     * 验证通过：
     * Runtime: 1001 ms, faster than 5.00% of Java online submissions for Sliding Window Maximum.
     * Memory Usage: 195.7 MB, less than 5.02% of Java online submissions for Sliding Window Maximum.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_1(int[] nums, int k) {
        int[] res = new int[nums.length - k + 1];
        TreeMap<Integer, Integer> map = new TreeMap<>();
        //初始化窗口
        for (int i = 0; i < k - 1; i++) {
            map.putIfAbsent(nums[i], 0);
            map.put(nums[i], map.get(nums[i]) + 1);
        }
        for (int i = k - 1; i < nums.length; i++) {
            int idx = i - k + 1;
            //最新的数字加入窗口
            map.putIfAbsent(nums[i], 0);
            map.put(nums[i], map.get(nums[i]) + 1);
            //计算最大值
            res[idx] = map.lastKey();
            //删除最早进入窗口的数字
            map.put(nums[idx], map.get(nums[idx]) - 1);
            if (map.get(nums[idx]) == 0) {
                map.remove(nums[idx]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3, new int[]{3, 3, 5, 5, 6, 7});
        do_func(new int[]{1}, 1, new int[]{1});
        do_func(new int[]{1, -1}, 1, new int[]{1, -1});
    }

    private static void do_func(int[] nums, int target, int[] expected) {
        int[] ret = maxSlidingWindow(nums, target);
        ArrayUtils.printlnIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
