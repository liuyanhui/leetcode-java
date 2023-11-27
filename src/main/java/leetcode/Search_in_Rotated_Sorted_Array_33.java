package leetcode;

/**
 * 33. Search in Rotated Sorted Array
 * Medium
 * -------------------------
 * There is an integer array nums sorted in ascending order (with distinct values).
 *
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 *
 * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 *
 * Example 2:
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 *
 * Example 3:
 * Input: nums = [1], target = 0
 * Output: -1
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * All values of nums are unique.
 * nums is an ascending array that is possibly rotated.
 * -10^4 <= target <= 10^4
 */
public class Search_in_Rotated_Sorted_Array_33 {
    public static int search(int[] nums, int target) {
        return search_3(nums, target);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1.naive solution
     * 遍历nums查找target。时间复杂度O(N)
     * 2.binary search
     * 2.1.把nums从中间切割成2个部分，target要么在已排序的部分里，要么在仍然rotate的部分里。一共有4种情况。
     * 2.2.二分之后有4种情况
     * IF target==nums[m] THEN return m
     * IF nums[l]<=nums[m] THEN
     * 	IF nums[l]<=target<nums[m] THEN r=m-1
     * 	ELSE l=m+1
     * ELSE
     * 	IF nums[m]<target<=nums[r] THEN l=m+1
     * 	ELSE r=m-1
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00% of users with Java
     * Memory 41.24 MB Beats 18.37% of users with Java
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search_3(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (target == nums[m]) return m;
            if (nums[l] <= nums[m]) {//m在左边已排序部分
                if (nums[l] <= target && target < nums[m]) {//target只可能在m的左侧
                    r = m - 1;
                } else {//target只可能在m的右侧
                    l = m + 1;
                }
            } else {//m在右边已排序部分
                if (nums[m] < target && target <= nums[r]) {//target只可能在m的右侧
                    l = m + 1;
                } else {//target只可能在m的左侧
                    r = m - 1;
                }
            }
        }
        return -1;
    }

    /**
     * round2
     * search_1 的代码优化版
     * @param nums
     * @param target
     * @return
     */
    public static int search_2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) return mid;
            if (nums[l] <= target) {//表示target落在左半部分
                if (nums[l] <= nums[mid] && nums[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else if (target <= nums[r]) {//表示target落在右半部分
                if (target < nums[mid] && nums[mid] <= nums[r]) {
                    r = mid - 1;
                } else {//表示mid落在左半部分
                    l = mid + 1;
                }
            } else {//target不在数组内
                break;
            }
        }
        return -1;
    }

    /**
     * round2
     * 在数组中找到某个数的套路：
     * 1.已排序数组，通过下标
     * 2.未排序数组，通过值而不是下标
     * 本题是二者的结合
     *
     * 夹逼法
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java
     * Memory Usage: 38.1 MB, less than 96.16%
     * @param nums
     * @param target
     * @return
     */
    public static int search_1(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[l] <= target) {//表示target落在左半部分
                if (nums[l] <= nums[mid]) {//表示mid落在左半部分
                    if (nums[mid] < target) {
                        l = mid + 1;
                    } else if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        return mid;
                    }
                } else if (nums[mid] <= nums[r]) {//表示mid落在右半部分
                    r = mid - 1;
                }
            } else if (target <= nums[r]) {//表示target落在右半部分
                if (nums[l] <= nums[mid]) {//表示mid落在左半部分
                    l = mid + 1;
                } else if (nums[mid] <= nums[r]) {//表示mid落在右半部分
                    if (nums[mid] < target) {
                        l = mid + 1;
                    } else if (nums[mid] > target) {
                        r = mid - 1;
                    } else {
                        return mid;
                    }
                }
            } else {//target不在数组内
                break;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        do_func(new int[]{4, 5, 6, 7, 0, 1, 2}, 0, 4);
        do_func(new int[]{4, 5, 6, 7, 0, 1, 2}, 3, -1);
        do_func(new int[]{1}, 0, -1);
        do_func(new int[]{0, 1, 2, 4, 5, 6, 7}, 0, 0);
        do_func(new int[]{0, 1, 2, 4, 5, 6, 7}, 2, 2);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = search(nums, target);
        System.out.println(ret);
        System.out.println((ret == expected));
        System.out.println("--------------");
    }
}
