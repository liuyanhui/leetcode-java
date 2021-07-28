package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 350. Intersection of Two Arrays II
 * Easy
 * ---------------------------
 * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must appear as many times as it shows in both arrays and you may return the result in any order.
 *
 * Example 1:
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2,2]
 *
 * Example 2:
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [4,9]
 * Explanation: [9,4] is also accepted.
 *
 * Constraints:
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 1000
 *
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
 */
public class Intersection_of_Two_Arrays_II_350 {
    /**
     * 验证通过：
     * Runtime: 2 ms, faster than 93.70% of Java online submissions for Intersection of Two Arrays II.
     * Memory Usage: 39.3 MB, less than 38.19% of Java online submissions for Intersection of Two Arrays II.
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map1 = new HashMap<>();
        for (int n : nums1) {
            map1.put(n, map1.getOrDefault(n, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int n : nums2) {
            if (map1.containsKey(n) && map1.get(n) > 0) {
                list.add(n);
                map1.put(n, map1.get(n) - 1);
            }
        }
        int[] ret = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 2, 1}, new int[]{2, 2}, new int[]{2, 2});
        do_func(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}, new int[]{9, 4});
        do_func(new int[]{4, 9, 5, 4}, new int[]{9, 4, 9, 8, 4}, new int[]{9, 4, 4});
    }

    private static void do_func(int[] nums1, int[] nums2, int[] expected) {
        int[] ret = intersect(nums1, nums2);
        ArrayUtils.printIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
