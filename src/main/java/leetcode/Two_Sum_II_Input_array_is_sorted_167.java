package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * 167. Two Sum II - Input array is sorted
 * Easy
 * --------------
 * Given an array of integers numbers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 *
 * Return the indices of the two numbers (1-indexed) as an integer array answer of size 2, where 1 <= answer[0] < answer[1] <= numbers.length.
 *
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 *
 * Example 1:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.
 *
 * Example 2:
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 *
 * Example 3:
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 *
 * Constraints:
 * 2 <= numbers.length <= 3 * 104
 * -1000 <= numbers[i] <= 1000
 * numbers is sorted in increasing order.
 * -1000 <= target <= 1000
 * Only one valid answer exists.
 */
public class Two_Sum_II_Input_array_is_sorted_167 {
    public static int[] twoSum(int[] numbers, int target) {
        return twoSum_2(numbers, target);
    }

    /**
     * 因为numbers是经过排序的，可以采用夹逼法
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Two Sum II - Input array is sorted.
     * Memory Usage: 39.3 MB, less than 44.80% of Java online submissions for Two Sum II - Input array is sorted.
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_2(int[] numbers, int target) {
        int[] ret = new int[2];
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] < target) {
                l++;
            } else if (numbers[l] + numbers[r] > target) {
                r--;
            } else {
                ret[0] = l + 1;
                ret[1] = r + 1;
                break;
            }
        }
        return ret;
    }

    /**
     * hashtable法
     * Map<int,int>中key是数字，value是数字的下标
     *
     * 验证通过:
     * Runtime: 2 ms, faster than 25.70% of Java online submissions for Two Sum II - Input array is sorted.
     * Memory Usage: 38.9 MB, less than 94.26% of Java online submissions for Two Sum II - Input array is sorted.
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_1(int[] numbers, int target) {
        int[] ret = new int[2];
        Map<Integer, Integer> ht = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            ht.put(numbers[i], i);
        }
        for (int i = 0; i < numbers.length; i++) {
            if (ht.containsKey(target - numbers[i])) {
                // 因为i是从0开始遍历，所以i必然小于ht.get(target - numbers[i])
                ret[0] = i + 1;
                ret[1] = ht.get(target - numbers[i]) + 1;
                return ret;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 7, 11, 15}, 9, new int[]{1, 2});
        do_func(new int[]{2, 3, 4}, 6, new int[]{1, 3});
        do_func(new int[]{-1, 0}, -1, new int[]{1, 2});
    }

    private static void do_func(int[] nums, int target, int[] expected) {
        int[] ret = twoSum(nums, target);
        System.out.println(ret);
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
