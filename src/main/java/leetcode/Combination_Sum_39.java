package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 39. Combination Sum
 * Medium
 * ------------------------
 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
 *
 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.
 *
 * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
 *
 * Example 1:
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 * Explanation:
 * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
 * 7 is a candidate, and 7 = 7.
 * These are the only two combinations.
 *
 * Example 2:
 * Input: candidates = [2,3,5], target = 8
 * Output: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * Example 3:
 * Input: candidates = [2], target = 1
 * Output: []
 *
 * Constraints:
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * All elements of candidates are distinct.
 * 1 <= target <= 500
 */
public class Combination_Sum_39 {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        return combinationSum_2(candidates, target);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * combinationSum_1()和combinationSum_2()是不同的递归实现
     *
     * Thinking：
     * 1. 递归
     * 2. 不可以使用缓存，存储中间结果。因为每个target可能存在多个解。
     *
     * 验证通过：
     * Runtime 2 ms Beats 75.85% of users with Java
     * Memory 44.00 MB Beats 31.71% of users with Java
     *
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum_2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = helper(candidates, 0, target);
        return res;
    }

    private static List<List<Integer>> helper(int[] candidates, int beg, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (target <= 0) return res;
        //review 跳过已经计算的数，可以避免结果集出现重复的解。
        for (int i = beg; i < candidates.length; i++) {
            //跳过重复的数。本题不需要
            // if(i>0 && candidates[i]==candidates[i-1]) continue;
            int gap = target - candidates[i];
            if (gap == 0) {
                List<Integer> cur = new ArrayList<>();
                cur.add(candidates[i]);
                res.add(cur);
            } else if (gap > 0) {
                List<List<Integer>> tmp = helper(candidates, i, gap);//合并
                for (List<Integer> list : tmp) {
                    List<Integer> t = new ArrayList<>(list);
                    t.add(candidates[i]);
                    res.add(t);
                }
            } else {
                //candidate已经排序，跳过更大的数
                break;
            }
        }

        return res;
    }

    /**
     * round2 review
     * 金矿：组合排列的方案
     *
     * 参考材料：
     * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 41.80% of Java online submissions for Combination Sum.
     * Memory Usage: 39 MB, less than 93.75% of Java online submissions for Combination Sum.
     *
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum_1(int[] candidates, int target) {
        List<List<Integer>> ret = new ArrayList<>();
        Arrays.sort(candidates);
        List<Integer> existed = new ArrayList<>();
        backtrack(candidates, target, 0, existed, ret);
        return ret;
    }

    private static void backtrack(int[] candidates, int target, int beg, List<Integer> existed, List<List<Integer>> ret) {
        if (target < 0) return;
        else if (target == 0) {
            ret.add(new ArrayList<>(existed));
        } else {
            //这里是关键
            for (int i = beg; i < candidates.length; i++) {
                existed.add(candidates[i]);
                backtrack(candidates, target - candidates[i], i, existed, ret);
                existed.remove(existed.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 3, 6, 7}, 7, new int[][]{{2, 2, 3}, {7}});
        do_func(new int[]{2, 3, 5}, 8, new int[][]{{2, 2, 2, 2}, {2, 3, 3}, {3, 5}});
        do_func(new int[]{2, 3, 4, 5}, 8, new int[][]{{2, 2, 2, 2}, {2, 2, 4}, {2, 3, 3}, {3, 5}, {4, 4}});
        do_func(new int[]{2}, 1, new int[][]{});
    }

    private static void do_func(int[] candidates, int target, int[][] expected) {
        List<List<Integer>> ret = combinationSum(candidates, target);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
