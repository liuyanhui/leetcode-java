package leetcode;

import java.util.HashSet;

/**
 * 128. Longest Consecutive Sequence
 * Medium
 * ------------------------------------
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 * You must write an algorithm that runs in O(n) time.
 *
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 *
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 * Constraints:
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class Longest_Consecutive_Sequence_128 {
    public static int longestConsecutive(int[] nums) {
        return longestConsecutive_1(nums);
    }

    /**
     * review 20220328
     * 思考过程：
     * 思路1：
     * 排序法。
     * 先快速排序，再依次查找最长子连续序列。
     * 时间复杂度：O(N*logN)
     * 结论：不满足条件
     *
     * 思路2：
     * Bitmap法。
     * 第1次遍历，更加数字更新位图；第2次遍历，根据位图计算最大连续子序列。
     * 时间复杂度：O(N)
     * 空间复杂度：O(N/32)=O(62,500,000) ，其中N=10^9*2
     * 结论：太复杂，怪异
     *
     * 思路3：
     * 上面都是完全排序的思路，是否可以不需要排序？
     * 参考思路:
     * https://leetcode.com/problems/longest-consecutive-sequence/solution/
     * 由bruce force思路优化而来。
     * 思路1和思路2都是排序法。没有利用到"连续consecutive"这个特性。不需要排序可以实现的思路。
     *
     * 验证通过：
     * Runtime: 25 ms, faster than 80.37% of Java online submissions for Longest Consecutive Sequence.
     * Memory Usage: 60.6 MB, less than 74.34% of Java online submissions for Longest Consecutive Sequence.
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive_1(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        int maxLen = 0;
        for (int i : set) {//注意这里是set而不是nums，因为nums里可能有重复数据
            if (set.contains(i - 1)) continue;//跳过不必要的计算
            int len = 1;
            while (set.contains(i + len)) {
                len++;
            }
            maxLen = Math.max(maxLen, len);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        do_func(new int[]{100, 4, 200, 1, 3, 2}, 4);
        do_func(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, 9);
        do_func(new int[]{}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = longestConsecutive(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
