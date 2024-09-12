package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * 167. Two Sum II - Input array is sorted
 * Easy
 * --------------
 * Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.
 *
 * Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.
 *
 * The tests are generated such that there is exactly one solution. You may not use the same element twice.
 *
 * Your solution must use only constant extra space.
 *
 * Example 1:
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
 *
 * Example 2:
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
 *
 * Example 3:
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
 *
 * Constraints:
 * 2 <= numbers.length <= 3 * 10^4
 * -1000 <= numbers[i] <= 1000
 * numbers is sorted in non-decreasing order.
 * -1000 <= target <= 1000
 * The tests are generated such that there is exactly one solution.
 */
public class Two_Sum_II_Input_array_is_sorted_167 {
    public static int[] twoSum(int[] numbers, int target) {
        return twoSum_r3_1(numbers, target);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking
     * 1. 左右夹逼法。
     * WHILE left<right THEN
     * IF nums[left]+nums[right]==target THEN return [left,right]
     * ELSE IF nums[left]+nums[right]<target THEN left++
     * ELSE IF nums[left]+nums[right]>target THEN right--
     *
     * 验证通过：
     * Runtime 2 ms Beats 91.05%
     * Memory 47.31 MB Beats 20.42%
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_r3_1(int[] numbers, int target) {
        int[] ret=new int[2];
        int l=0,r=numbers.length-1;
        while(l<r){
            if(numbers[l]+numbers[r]==target){
                ret[0]=l+1;
                ret[1]=r+1;
                break;
            }else if(numbers[l]+numbers[r]>target){
                r--;
            }else{
                l++;
            }
        }
        return ret;
    }

    /**
     * Two Sum中的哈希表方案可以实现，但是空间复杂度不满足要求。
     *
     * 采用双指针夹逼法
     * 算法如下：
     * 0.l=0,r=numbers.length-1
     * 1.当l<r时，循环
     * 1.1 如果[l]+[r]<targeet，那么l++
     * 1.2 如果[l]+[r]>targeet，那么r--
     * 1.3 如果[l]+[r]==targeet，返回[l+1,r+1]
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 75.81% of Java online submissions for Two Sum II - Input Array Is Sorted.
     * Memory Usage: 50.6 MB, less than 10.10% of Java online submissions for Two Sum II - Input Array Is Sorted.
     *
     * @param numbers
     * @param target
     * @return
     */
    public static int[] twoSum_3(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int s = numbers[l] + numbers[r];
            if (s > target) {
                r--;
            } else if (s < target) {
                l++;
            } else {
                break;
            }
        }
        int[] ret = new int[2];
        ret[0] = l + 1;
        ret[1] = r + 1;
        return ret;
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
        ArrayUtils.printlnIntArray(ret);
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
