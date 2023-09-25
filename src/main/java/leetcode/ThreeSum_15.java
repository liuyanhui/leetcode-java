package leetcode;

import java.util.*;

/**
 * 15. 3Sum
 * Medium
 * -----------------------
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *
 * Notice that the solution set must not contain duplicate triplets.
 *
 * Example 1:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 *
 * Example 2:
 * Input: nums = []
 * Output: []
 *
 * Example 3:
 * Input: nums = [0]
 * Output: []
 *
 * Constraints:
 * 0 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 */
public class ThreeSum_15 {
    public static List<List<Integer>> threeSum(int[] num) {
        return threeSum_2(num);
    }

    /**
     * round 3
     *
     * threeSum_2()中注意下面的代码
     * if (i > 0 && num[i] == num[i - 1]) continue;//这里很重要，避免重复的nums[i]
     *
     * @param nums
     * @return
     */

    /**
     * round 2
     * 参考思路：
     * https://leetcode.com/problems/3sum/discuss/7380/Concise-O(N2)-Java-solution
     *
     * 套路：先排序，转化成2Sum问题，采用夹逼法。
     *
     * 验证通过：
     * Runtime: 24 ms, faster than 60.62% of Java online submissions for 3Sum.
     * Memory Usage: 42.8 MB, less than 88.64% of Java online submissions for 3Sum.
     *
     * @param num
     * @return
     */
    public static List<List<Integer>> threeSum_2(int[] num) {
        List<List<Integer>> ret = new ArrayList<>();
        Arrays.sort(num);
        for (int i = 0; i < num.length; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;//这里很重要，避免重复的nums[i]
            int j = i + 1, k = num.length - 1;
            while (j < k) {
                int target = num[i] + num[j] + num[k];
                if (target == 0) {
                    ret.add(Arrays.asList(new Integer[]{num[i], num[j], num[k]}));
                    while (j < k && num[j] == num[++j]) {
                    }
                    while (j < k && num[k] == num[--k]) {
                    }
                } else if (target < 0) {
                    while (j < k && num[j] == num[++j]) {
                    }
                } else {
                    while (j < k && num[k] == num[--k]) {
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 转化成TwoSum问题
     * Time Complexity:O(N*N)
     *
     * 验证成功：
     * Runtime: 2082 ms, faster than 5.00% of Java online submissions for 3Sum.
     * Memory Usage: 46.8 MB, less than 30.02% of Java online submissions for 3Sum.
     *
     * @param num
     * @return
     */
    public static List<List<Integer>> threeSum_1(int[] num) {
        List<List<Integer>> ret = new ArrayList<>();
        Set<String> existedSet = new HashSet<>();
        Map<Integer, Set<Integer>> cache = new HashMap<>();
        for (int i = 0; i < num.length; i++) {
            cache.computeIfAbsent(num[i], v -> new HashSet<>());
            cache.get(num[i]).add(i);
        }
        for (int i = 0; i < num.length; i++) {
            int target = num[i];
            cache.get(target).remove(new Integer(i));
            //TwoSum方案
            //下面如果初始时j=0，会验证不通过，原因：Time Limit Exceeded
            for (int j = i + 1; j < num.length; j++) {
                int v = -target - num[j];
                cache.get(num[j]).remove(new Integer(j));
                if (cache.containsKey(v) && cache.get(v).size() > 0) {
                    Integer[] tArr = new Integer[]{target, num[j], v};
                    Arrays.sort(tArr);
                    String tKey = tArr[0] + "." + tArr[1] + "." + tArr[2];
                    if (existedSet.contains(tKey)) {
                        continue;
                    }
                    existedSet.add(tKey);
                    ret.add(Arrays.asList(tArr));
                }
                cache.get(num[j]).add(new Integer(j));
            }
            cache.get(target).add(new Integer(i));
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{-1, 0, 1, 2, -1, -4}, new int[][]{{-1, -1, 2}, {-1, 0, 1}});
        do_func(new int[]{}, new int[][]{});
        do_func(new int[]{0}, new int[][]{});
        do_func(new int[]{0, 0}, new int[][]{});
        do_func(new int[]{0, 0, 0}, new int[][]{{0, 0, 0}});
        do_func(new int[]{0, 0, 0, 0}, new int[][]{{0, 0, 0}});
        do_func(new int[]{1, 2, 3, 4, 5}, new int[][]{});
    }

    private static void do_func(int[] num, int[][] expected) {
        List<List<Integer>> ret = threeSum(num);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
