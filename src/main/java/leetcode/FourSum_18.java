package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18. 4Sum
 * Medium
 * --------------------
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 * 0 <= a, b, c, d < n
 * a, b, c, and d are distinct.
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 *
 * You may return the answer in any order.
 *
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 * Constraints:
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */
public class FourSum_18 {

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        return fourSum_2(nums, target);
    }

    /**
     * round 3
     *
     * Thinking：
     * 1.naive solution
     * 四层遍历，暴力求解。时间复杂度：O(N*N*N*N)
     * 2.先排序，在降维成2Sum的方案。时间复杂度：O(N*N*N)
     *
     * 验证通过：
     * Runtime 16 ms Beats 74.4%
     * Memory 44.1 MB Beats 39.19%
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum_2(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) return res;

        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;//过滤重复出现的数
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;//过滤重复出现的数
                int l = j + 1, r = nums.length - 1;
                while (l < r) {
                    long sum = (long)nums[i] + (long)nums[j] + (long)nums[l] + (long)nums[r];//防止整型数字溢出
                    if (sum == target) {
                        List<Integer> t = new ArrayList();
                        t.add(nums[i]);
                        t.add(nums[j]);
                        t.add(nums[l]);
                        t.add(nums[r]);
                        res.add(t);
                        l++;
                        r--;
                        //review 下面是关键，不可以挪到if外面。只有满足条件才可以过滤left和right。
                        //过滤重复出现的数
                        while (l < r && nums[l - 1] == nums[l]) {
                            l++;
                        }
                        //过滤重复出现的数
                        while (l < r && nums[r] == nums[r + 1]) {
                            r--;
                        }
                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }

        return res;
    }

    /**
     * round 2
     * 按照KSum问题来解决。
     * 参考资料：
     * https://leetcode.com/problems/4sum/solution/
     *
     * Time Complexity: O(N^3) 或 O(N^(k-1))
     *
     * 验证通过：
     * Runtime: 15 ms, faster than 67.38% of Java online submissions for 4Sum.
     * Memory Usage: 39.7 MB, less than 57.70% of Java online submissions for 4Sum.
     *
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum_1(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, target, 4);
    }

    private static List<List<Integer>> kSum(int[] nums, int start, int target, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        if (start >= nums.length) return ret;
        if (k == 2) {
            return twoSum(nums, start, target);
        }
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            for (List<Integer> kSumRet : kSum(nums, i + 1, target - nums[i], k - 1)) {
                kSumRet.add(nums[i]);
                ret.add(kSumRet);
            }
        }
        return ret;
    }

    private static List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> ret = new ArrayList<>();
        if (start >= nums.length) return ret;
        int l = start, r = nums.length - 1;
        while (l < r) {
            int t = nums[l] + nums[r];
            if (t == target) {
                ret.add(new ArrayList<>());
                ret.get(ret.size() - 1).add(nums[l]);
                ret.get(ret.size() - 1).add(nums[r]);
                l++;
                while (l < r && nums[l - 1] == nums[l]) {
                    l++;
                }
                r--;
                while (l < r && nums[r] == nums[r + 1]) {
                    r--;
                }
            } else if (t < target) {
                l++;
            } else {
                r--;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 0, -1, 0, -2, 2}, 0, new int[][]{{-2, -1, 1, 2}, {-2, 0, 0, 2}, {-1, 0, 0, 1}});
        do_func(new int[]{2, 2, 2, 2, 2}, 8, new int[][]{{2, 2, 2, 2}});
        do_func(new int[]{2, 2, 2, 2, 2}, 88, new int[][]{});
        do_func(new int[]{2, 2, 2, 2, 2}, 2, new int[][]{});
        do_func(new int[]{2, 2, 2, 2, 2}, 6, new int[][]{});
        do_func(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, 8, new int[][]{{2, 2, 2, 2}});
        do_func(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3}, 8, new int[][]{{2, 2, 2, 2}});
        do_func(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296, new int[][]{});
    }

    private static void do_func(int[] nums, int target, int[][] expected) {
        List<List<Integer>> ret = fourSum(nums, target);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
