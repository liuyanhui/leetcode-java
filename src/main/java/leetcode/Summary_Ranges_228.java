package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 228. Summary Ranges
 * Easy
 * ----------------------
 * You are given a sorted unique integer array nums.
 *
 * Return the smallest sorted list of ranges that cover all the numbers in the array exactly. That is, each element of nums is covered by exactly one of the ranges, and there is no integer x such that x is in one of the ranges but not in nums.
 *
 * Each range [a,b] in the list should be output as:
 * "a->b" if a != b
 * "a" if a == b
 *
 * Example 1:
 * Input: nums = [0,1,2,4,5,7]
 * Output: ["0->2","4->5","7"]
 * Explanation: The ranges are:
 * [0,2] --> "0->2"
 * [4,5] --> "4->5"
 * [7,7] --> "7"
 *
 * Example 2:
 * Input: nums = [0,2,3,4,6,8,9]
 * Output: ["0","2->4","6","8->9"]
 * Explanation: The ranges are:
 * [0,0] --> "0"
 * [2,4] --> "2->4"
 * [6,6] --> "6"
 * [8,9] --> "8->9"
 *
 * Example 3:
 * Input: nums = []
 * Output: []
 *
 * Example 4:
 * Input: nums = [-1]
 * Output: ["-1"]
 *
 * Example 5:
 * Input: nums = [0]
 * Output: ["0"]
 *
 * Constraints:
 * 0 <= nums.length <= 20
 * -2^31 <= nums[i] <= 2^31 - 1
 * All the values of nums are unique.
 * nums is sorted in ascending order.
 */
public class Summary_Ranges_228 {
    public static List<String> summaryRanges(int[] nums) {
        return summaryRanges_2(nums);
    }

    /**
     * 精简版方案
     *
     * 参考思路：
     * https://leetcode.com/problems/summary-ranges/discuss/63219/Accepted-JAVA-solution-easy-to-understand
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 69.56% of Java online submissions for Summary Ranges.
     * Memory Usage: 37.1 MB, less than 90.14% of Java online submissions for Summary Ranges.
     *
     * @param nums
     * @return
     */
    public static List<String> summaryRanges_2(int[] nums) {
        List<String> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) return ret;
        int beg;
        for (int i = 0; i < nums.length; i++) {
            beg = nums[i];
            //金矿：这里即考虑了最后一个数字(有效防止后面的nums[i + 1]数组溢出)，又考虑了题目逻辑。2合一
            while (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) {
                i++;
            }
            if (beg != nums[i]) {
                ret.add(beg + "->" + nums[i]);
            } else {
                ret.add(beg + "");
            }
        }
        return ret;
    }

    /**
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 28.37% of Java online submissions for Summary Ranges.
     * Memory Usage: 37.7 MB, less than 21.06% of Java online submissions for Summary Ranges.
     * @param nums
     * @return
     */
    public static List<String> summaryRanges_1(int[] nums) {
        List<String> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) return ret;
        int beg = nums[0], lastEnd = nums[0];
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (beg + count != nums[i]) {
                if (beg < lastEnd) {
                    ret.add(beg + "->" + lastEnd);
                } else {
                    ret.add(beg + "");
                }
                beg = nums[i];
                count = 0;
            }
            count++;
            lastEnd = nums[i];
            //最后一个数字
            if (i == nums.length - 1) {
                if (beg < lastEnd) {
                    ret.add(beg + "->" + lastEnd);
                } else {
                    ret.add(beg + "");
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{0, 1, 2, 4, 5, 7}, new String[]{"0->2", "4->5", "7"});
        do_func(new int[]{0, 2, 3, 4, 6, 8, 9}, new String[]{"0", "2->4", "6", "8->9"});
        do_func(new int[]{}, new String[]{});
        do_func(new int[]{-1}, new String[]{"-1"});
        do_func(new int[]{0}, new String[]{"0"});

    }

    private static void do_func(int[] nums, String[] expected) {
        List<String> ret = summaryRanges(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
