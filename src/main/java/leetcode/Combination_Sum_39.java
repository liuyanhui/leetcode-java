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
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
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
