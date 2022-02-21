package leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/subsets-ii/
 * 90. Subsets II
 * Medium
 * -------------------
 * Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 * Example 1:
 * Input: nums = [1,2,2]
 * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 * Constraints:
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 */
public class Subsets_II_90 {
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        return subsetsWithDup_3(nums);
    }

    /**
     * dfs思路
     * 来源：提交方案中的0 ms submission
     * 非常巧妙，无需使用缓存判重
     *
     */
    public static List<List<Integer>> subsetsWithDup_4(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backTrack(0, nums, new ArrayList<Integer>(), result);
        return result;
    }

    public static void backTrack(int start, int[] nums, List<Integer> tempList, List<List<Integer>> result) {
        result.add(new ArrayList<Integer>(tempList));

        for (int i = start; i < nums.length; i++) {
            if (i > start && (nums[i] == nums[i - 1])) continue;//这里很关键，用于排重
            tempList.add(nums[i]);
            backTrack(i + 1, nums, tempList, result);
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * round 2
     *
     * 1.排序数组
     * 2.迭代法+笛卡尔积+去重，思路如下：
     * [1,2,2] (× 表示笛卡尔积)
     * 第一步初始化 {[]}
     * 第二步{[]}×{[],[1]}={[],[1]}
     * 第三步{[],[1]}×{[],[2]}={[],[1],[2],[1,2]}
     * 第四步{[],[1],[2],[1,2]}×{[],[2]}={[],[1],[2],[1,2],[2,2][1,2,2]}
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 12.59% of Java online submissions for Subsets II.
     * Memory Usage: 45.3 MB, less than 5.97% of Java online submissions for Subsets II.
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsetsWithDup_3(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) return ret;
        Arrays.sort(nums);
        Set<String> existed = new HashSet<String>();
        ret.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> curList = new ArrayList<>();
            for (List<Integer> t : ret) {
                List<Integer> itemList = new ArrayList<>(t);
                itemList.add(nums[i]);
                String key = itemList.toString();
                if (!existed.contains(key)) {
                    curList.add(new ArrayList<>(itemList));
                    existed.add(key);
                }
            }
            ret.addAll(curList);
        }
        return ret;
    }

    /**
     * subsetsWithDup_1的改进版，排序操作提前，对输入数组进行排序
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 22.11% of Java online submissions for Subsets II.
     * Memory Usage: 39.4 MB, less than 37.84% of Java online submissions for Subsets II.
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsetsWithDup_2(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Arrays.sort(nums);
        Map<String, String> existedMap = new HashMap<>();
        ret.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            int maxRet = ret.size();
            for (int j = 0; j < maxRet; j++) {
                List<Integer> t = new ArrayList<>(ret.get(j));
                t.add(nums[i]);
                if (!existedMap.containsKey(t.toString())) {
                    ret.add(t);
                    existedMap.put(t.toString(), "1");
                }
            }
        }
        return ret;
    }

    /**
     * 参考Subsets_78的迭代法，增加了排序和map缓存查重的代码。
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 10.09% of Java online submissions for Subsets II.
     * Memory Usage: 42.4 MB, less than 5.20% of Java online submissions for Subsets II.
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsetsWithDup_1(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Map<String, String> existedMap = new HashMap<>();
        ret.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            int maxRet = ret.size();
            for (int j = 0; j < maxRet; j++) {
                List<Integer> t = new ArrayList<>(ret.get(j));
                t.add(nums[i]);
                t.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        if (o1 > o2) return 1;
                        else if (o1 < o2) return -1;
                        else return 0;
                    }
                });
                if (!existedMap.containsKey(t.toString())) {
                    ret.add(t);
                    existedMap.put(t.toString(), "1");
                }
            }
        }
        //
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 2}, new int[][]{{}, {1}, {1, 2}, {1, 2, 2}, {2}, {2, 2}});
        do_func(new int[]{0}, new int[][]{{}, {0}});
        do_func(new int[]{4, 4, 4, 1, 4}, new int[][]{{}, {1}, {1, 4}, {1, 4, 4}, {1, 4, 4, 4}, {1, 4, 4, 4, 4}, {4}, {4, 4}, {4, 4, 4}, {4, 4, 4, 4}});
    }

    private static void do_func(int[] nums, int[][] expected) {
        List<List<Integer>> ret = subsetsWithDup(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
