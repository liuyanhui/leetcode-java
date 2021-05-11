package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 219. Contains Duplicate II
 * Easy
 * ---------------
 * Given an integer array nums and an integer k, return true if there are two distinct indices i and j in the array such that nums[i] == nums[j] and abs(i - j) <= k.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,0,1,1], k = 1
 * Output: true
 *
 * Example 3:
 * Input: nums = [1,2,3,1,2,3], k = 2
 * Output: false
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 */
public class Contains_Duplicate_II_219 {

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        return containsNearbyDuplicate_1(nums, k);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/contains-duplicate-ii/discuss/61372/Simple-Java-solution
     * 1.始终保持set的长度是k
     * 2.随着i的增加，set中是里i最近的k个元素
     * 3.当set.add()失败时，表示在k距离内有重复的数字
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate_2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            //remove element if its distance to nums[i] is not lesser than k
            if (i > k) set.remove(nums[i - k - 1]);
            //because all still existed elements is closer than k distance to the num[i], therefore if the add() return false, it means there's a same value element already existed within the distance k, therefore return true.
            if (!set.add(nums[i]))
                return true;
        }
        return false;
    }

    /**
     * 验证通过：
     * Runtime: 5 ms, faster than 90.71% of Java online submissions for Contains Duplicate II.
     * Memory Usage: 44.7 MB, less than 39.24% of Java online submissions for Contains Duplicate II.
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate_1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if ((i - map.get(nums[i])) <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);
        }

        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 1}, 3, true);
        do_func(new int[]{1, 0, 1, 1}, 1, true);
        do_func(new int[]{1, 2, 3, 1, 2, 3}, 2, false);
    }

    private static void do_func(int[] nums, int k, boolean expected) {
        boolean ret = containsNearbyDuplicate(nums, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
