package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 40. Combination Sum II
 * Medium
 * --------------------------
 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.
 *
 * Each number in candidates may only be used once in the combination.
 *
 * Note: The solution set must not contain duplicate combinations.
 *
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 *
 * Example 2:
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * Constraints:
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 */
public class Combination_Sum_II_40 {
    /**
     * round2 review
     *
     * 参考：
     * https://leetcode.com/problems/combination-sum-ii/discuss/16861/Java-solution-using-dfs-easy-understand
     *
     * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 65.96% of Java online submissions for Combination Sum II.
     * Memory Usage: 40.1 MB, less than 26.63% of Java online submissions for Combination Sum II.
     *
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> existed = new ArrayList<>();
        backtrack(candidates, target, 0, existed, ret);
        return ret;
    }

    private static void backtrack(int[] candidates, int target, int beg, List<Integer> existed, List<List<Integer>> ret) {
        if (target < 0) return;
        if (target == 0) {
            ret.add(new ArrayList<>(existed));
        } else if (target < 0) {
            return;
        } else {
            //这里很关键
            for (int i = beg; i < candidates.length; i++) {
                //这里很巧妙，用来去重
                if (i > beg && candidates[i] == candidates[i - 1]) continue;
                //如果当前已经超出target范围，那么跳出后续不必要的循环
                if (target - candidates[i] < 0) break;
                existed.add(candidates[i]);
                backtrack(candidates, target - candidates[i], i + 1, existed, ret);
                existed.remove(existed.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        //1,1,2,5,6,7,10
        do_func(new int[]{10, 1, 2, 7, 6, 1, 5}, 8, new int[][]{{1, 1, 6}, {1, 2, 5}, {1, 7}, {2, 6}});
        do_func(new int[]{2, 5, 2, 1, 2}, 5, new int[][]{{1, 2, 2}, {5}});
        do_func(new int[]{5}, 5, new int[][]{{5}});
        do_func(new int[]{5, 5, 5, 5}, 5, new int[][]{{5}});
        do_func(new int[]{2, 2, 2, 2}, 6, new int[][]{{2, 2, 2}});
    }

    private static void do_func(int[] candidates, int target, int[][] expected) {
        List<List<Integer>> ret = combinationSum2(candidates, target);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
