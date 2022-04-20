package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 136. Single Number
 * Easy
 * --------------------------
 * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 * Example 1:
 * Input: nums = [2,2,1]
 * Output: 1
 *
 * Example 2:
 * Input: nums = [4,1,2,1,2]
 * Output: 4
 *
 * Example 3:
 * Input: nums = [1]
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -3 * 10^4 <= nums[i] <= 3 * 10^4
 * Each element in the array appears twice except for one element which appears only once.
 */
public class Single_Number_136 {
    public static int singleNumber(int[] nums) {
        return singleNumber_2(nums);
    }

    /**
     * Bit Manipulate
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 95.27% of Java online submissions for Single Number.
     * Memory Usage: 42.5 MB, less than 84.53% of Java online submissions for Single Number.
     *
     * @param nums
     * @return
     */
    public static int singleNumber_2(int[] nums) {
        int ret = 0;
        for (int n : nums)
            ret ^= n;
        return ret;
    }

    /**
     * 思考过程：
     *
     * brute force：
     * 先排序，再搜索
     * 时间复杂度：O(NlogN)+O(N)
     * 空间复杂度：O(N)
     *
     * 优化1：
     * 使用哈希表。不存在加入，存在则删除，最后留下的就是所求。
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * 验证通过：
     * Runtime: 15 ms, faster than 23.69% of Java online submissions for Single Number.
     * Memory Usage: 54.9 MB, less than 5.12% of Java online submissions for Single Number.
     *
     * @param nums
     * @return
     */
    public static int singleNumber_1(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) set.remove(n);
            else set.add(n);
        }
        return set.stream().findAny().get();
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 2, 1}, 1);
        do_func(new int[]{4, 1, 2, 1, 2}, 4);
        do_func(new int[]{1}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = singleNumber(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
