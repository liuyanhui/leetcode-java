package leetcode;

import java.util.TreeMap;

/**
 * 239. Sliding Window Maximum
 * Hard
 * --------------------------
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 *
 * Return the max sliding window.
 *
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class Sliding_Window_Maximum_239 {

    public static int[] maxSlidingWindow(int[] nums, int k) {
        return maxSlidingWindow_1(nums, k);
    }

    /**
     * 思考：
     * 1.直觉思路。窗口滑动时依次计算最大值，维护一个大小为k的MaxHeap。使用Java语言中的TreeMap，免去实现HeapSort。其中，key=数字，value=出现的次数。
     * 时间复杂度为：O((N-K)*logK)，空间复杂度O(K)
     * 2.如何利用sliding window的FIFO特性？
     *
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
