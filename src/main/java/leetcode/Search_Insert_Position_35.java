package leetcode;

/**
 * https://leetcode.com/problems/search-insert-position/
 * 35. Search Insert Position
 * Easy
 * ------------
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 * You must write an algorithm with O(log n) runtime complexity.
 *
 * Example 1:
 * Input: nums = [1,3,5,6], target = 5
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,3,5,6], target = 2
 * Output: 1
 *
 * Example 3:
 * Input: nums = [1,3,5,6], target = 7
 * Output: 4
 *
 * Example 4:
 * Input: nums = [1,3,5,6], target = 0
 * Output: 0
 *
 * Example 5:
 * Input: nums = [1], target = 0
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums contains distinct values sorted in ascending order.
 * -10^4 <= target <= 10^4
 */
public class Search_Insert_Position_35 {
    public static int searchInsert(int[] nums, int target) {
        return searchInsert_5(nums, target);
    }

    /**
     * round2，采用思路2
     * 思路1：遍历，if nums[i]<target then i++
     *           if nums[i]>=target then return i;
     * 思路2：binary search ，数组有序使用下标，数组无序使用数字
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00%
     * Memory Usage: 38.3 MB, less than 98.19% o
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_5(int[] nums, int target) {
        //这里 r=nums.length的原因：因为是插入一个元素，数组中的元素数量会增加一个。
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = (l + r) / 2;
            //下面的代码可以防止整数超过最大值导致的溢出
            //int mid = l + (r-l)/2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }

    /**
     * 简化版binary search，参考思路：
     * https://leetcode.com/problems/search-insert-position/discuss/15080/My-8-line-Java-solution
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_4(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] > target) high = mid - 1;
            else low = mid + 1;
        }
        return low;
    }

    /**
     * binary search 思路
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search Insert Position.
     * Memory Usage: 40.1 MB, less than 9.73% of Java online submissions for Search Insert Position.
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_3(int[] nums, int target) {
        int mid = 0;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return nums[left] >= target ? left : left + 1;
    }

    /**
     * searchInsert_1的精简版本
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_2(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Search Insert Position.
     * Memory Usage: 38.5 MB, less than 85.60% of Java online submissions for Search Insert Position.
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert_1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < target) {
                continue;
            } else if (nums[i] == target) {
                return i;
            } else {
                return i;
            }
        }
        return nums.length;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3, 5, 6}, 5, 2);
        do_func(new int[]{1, 3, 5, 6}, 2, 1);
        do_func(new int[]{1, 3, 5, 6}, 7, 4);
        do_func(new int[]{1, 3, 5, 6}, 0, 0);
        do_func(new int[]{1}, 0, 0);
        do_func(new int[]{1}, 1, 0);
        do_func(new int[]{1}, 4, 1);
    }

    private static void do_func(int[] nums, int target, int expected) {
        int ret = searchInsert(nums, target);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
