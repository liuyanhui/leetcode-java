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
        return subsetsWithDup_2(nums);
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
        boolean same = true;
        if (ret.size() == expected.length) {
            for (int i = 0; i < ret.size(); i++) {
                if (ret.get(i).size() != expected[i].length) {
                    same = false;
                    break;
                }
                for (int j = 0; j < ret.get(i).size(); j++) {
                    if (ret.get(i).get(j) != expected[i][j]) {
                        same = false;
                        break;
                    }
                }
                if (!same) break;
            }
        } else {
            same = false;
        }
        System.out.println(same);
        System.out.println("--------------");
    }
}
