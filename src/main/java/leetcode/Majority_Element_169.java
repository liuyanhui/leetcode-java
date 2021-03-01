package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里有金矿
 * 169. Majority Element
 * Easy
 * ---------------------
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
 *
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 5 * 104
 * -231 <= nums[i] <= 231 - 1
 *
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 */
public class Majority_Element_169 {
    public static int majorityElement(int[] nums) {
        return majorityElement_3(nums);
    }

    /**
     * 金矿
     * 两个元素不同，删除两个元素；两个元素相等，删除一个元素。
     * 参考思路：
     * https://leetcode.com/problems/majority-element/solution/ 之Approach 6: Boyer-Moore Voting Algorithm
     *
     * 时间复杂度为O(n)，空间复杂度为O(1)
     *
     * @param nums
     * @return
     */
    public static int majorityElement_3(int[] nums) {
        int count = 0;
        int candidate = 0;
        for (int n : nums) {
            if (count == 0) candidate = n;
            count += (n == candidate) ? 1 : -1;
        }
        return candidate;
    }

    /**
     * 使用hashmap
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 35.09% of Java online submissions for Majority Element.
     * Memory Usage: 44.6 MB, less than 28.65% of Java online submissions for Majority Element.
     *
     * @param nums
     * @return
     */
    public static int majorityElement_2(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], 0);
            }
            map.put(nums[i], map.get(nums[i]) + 1);
        }
        //下面的代码可以优化，即：在hashmap初始化的过程中，如果出现value>nums.length/2的情况就直接返回
        for (int k : map.keySet()) {
            if (map.get(k) > nums.length / 2) return k;
        }
        return 0;
    }

    /**
     * 先排序，再统计
     * @param nums
     * @return
     */
    public static int majorityElement_1(int[] nums) {
        Arrays.sort(nums);
        //排序后无需进行遍历，直接读取中间的值即可
        //参考思路：https://leetcode.com/problems/majority-element/solution/ 之Approach 3
        return nums[nums.length / 2];
        //一下为比较繁琐的代码。
        /*int count = 1;
        int ret = Integer.MAX_VALUE;
        for (int i = 1; i < nums.length; i++) {
            if (count > nums.length / 2) {
                ret = nums[i - 1];
                break;
            }
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }
            if (i == nums.length - 1) {
                ret = nums[i];
            }
        }
        return ret;*/
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 2, 3}, 3);
        do_func(new int[]{2, 2, 1, 1, 1, 2, 2}, 2);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = majorityElement(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
