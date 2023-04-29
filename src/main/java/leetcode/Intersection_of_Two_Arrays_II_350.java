package leetcode;

import java.util.*;

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
    public static int[] intersect(int[] nums1, int[] nums2) {
        return intersect_3(nums1, nums2);
    }

    /**
     * round 2
     *
     * Follow up :
     * What if the given array is already sorted? How would you optimize your algorithm?
     *
     * 验证通过：
     * Runtime 2 ms Beats 95.82%
     * Memory 42.7 MB Beats 69.37%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect_3(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        List<Integer> list = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[list.size()];
        for (int k = 0; k < res.length; k++)
            res[k] = list.get(k);
        return res;
    }

    /**
     * round 2
     *
     * 验证通过：
     * Runtime 3 ms Beats 52.61%
     * Memory 43.1 MB Beats 9.33%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect_2(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int n : nums1) {
            map.computeIfAbsent(n, v -> 0);
            map.put(n, map.get(n) + 1);
        }
        for (int n : nums2) {
            if (map.containsKey(n) && map.get(n) > 0) {
                list.add(n);
                map.put(n, map.get(n) - 1);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = list.get(i);
        return res;
    }

    /**
     * 验证通过：
     * Runtime: 2 ms, faster than 93.70% of Java online submissions for Intersection of Two Arrays II.
     * Memory Usage: 39.3 MB, less than 38.19% of Java online submissions for Intersection of Two Arrays II.
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect_1(int[] nums1, int[] nums2) {
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
        ArrayUtils.printlnIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
