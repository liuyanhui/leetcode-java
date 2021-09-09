package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 287. Find the Duplicate Number
 * Medium
 * ----------------------------
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 *
 * Example 1:
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 *
 * Example 3:
 * Input: nums = [1,1]
 * Output: 1
 *
 * Example 4:
 * Input: nums = [1,1,2]
 * Output: 1
 *
 * Constraints:
 * 1 <= n <= 10^5
 * nums.length == n + 1
 * 1 <= nums[i] <= n
 * All the integers in nums appear only once except for precisely one integer which appears two or more times.
 *
 * Follow up:
 * How can we prove that at least one duplicate number must exist in nums?
 * Can you solve the problem in linear runtime complexity?
 */
public class Find_the_Duplicate_Number_287 {
    public static int findDuplicate(int[] nums) {
        return findDuplicate_4(nums);
    }

    /**
     * 金矿：在未排序的集合中查找某个数，采用基于数字的binary search；在已排序的集合中查找某个数，采用基于下标的binary search
     * 与Find_Minimum_in_Rotated_Sorted_Array_153和Kth_Smallest_Element_in_a_Sorted_Matrix_378可以组成一个系列
     *
     * 参考思路：
     * https://leetcode.com/problems/find-the-duplicate-number/solution/
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code
     *
     * 验证通过：
     * Runtime: 68 ms, faster than 5.42% of Java
     * Memory Usage: 57 MB, less than 30.84% of Java
     *
     * @param nums
     * @return
     */
    public static int findDuplicate_4(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int low = 1, high = nums.length - 1;
        //注意：这里是数字比较而不是下标，可能存在[1,1,1,2,1,1,1]这样的用例，初始时low==high
        while (low <= high) {
            int mid = low + (high - low) / 2;
            //关键点：每次都需要遍历整个数组
            long count = Arrays.stream(nums).filter(v -> v <= mid).count();
            if (count <= mid) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /**
     * 套路
     * 问题转化为 Linked List Cycle II。
     * node.val = nums[i]
     * node.next = nums[nums[i]]
     *
     * 参考思路：
     * https://leetcode.com/problems/find-the-duplicate-number/solution/ 之 Approach 3
     *
     * 验证通过:
     * Runtime: 6 ms, faster than 37.70% of Java online submissions for Find the Duplicate Number.
     * Memory Usage: 73.3 MB, less than 7.29% of Java online submissions for Find the Duplicate Number.
     *
     * @param nums
     * @return
     */
    public static int findDuplicate_3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int slow = 0;
        int fast = 0;
        while (slow == 0 || slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    /**
     * 其他两种办法：
     * 1.可以使用先排序后比较的办法
     * 2.使用Set进行比较
     *
     * @param nums
     * @return
     */
    public static int findDuplicate_2(int[] nums) {
        Set<Integer> existed = new HashSet<>();
        for (int i : nums) {
            if (existed.contains(i)) {
                return i;
            } else {
                existed.add(i);
            }
        }
        return 0;
    }

    /**
     * bitmap思路：
     * 采用byte数组，当byte[i/8][i%8] == 1时 表示i重复出现
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 35.16% of Java online submissions for Find the Duplicate Number.
     * Memory Usage: 74.4 MB, less than 5.01% of Java online submissions for Find the Duplicate Number.
     * @param nums
     * @return
     */
    public static int findDuplicate_1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        byte[] bitmap = new byte[nums.length / 8 + 1];
        for (int i : nums) {
            //按位左移运算
            int t = 1 << (i % 8);
            //按位与
            if ((bitmap[i / 8] & t) == 0) {
                //按位或
                bitmap[i / 8] |= t;
            } else {
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3, 4, 2, 2}, 2);
        do_func(new int[]{3, 1, 3, 4, 2}, 3);
        do_func(new int[]{1, 1}, 1);
        do_func(new int[]{1, 1, 2}, 1);
        do_func(new int[]{1, 3, 2, 2, 2}, 2);
        do_func(new int[]{2, 2, 2, 2, 2}, 2);
        do_func(new int[]{1, 1, 1, 2, 1, 1, 1}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = findDuplicate(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
