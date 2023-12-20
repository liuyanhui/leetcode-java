package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 46. Permutations
 * Medium
 * -------------------
 * Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * Example 2:
 * Input: nums = [0,1]
 * Output: [[0,1],[1,0]]
 *
 * Example 3:
 * Input: nums = [1]
 * Output: [[1]]
 *
 * Constraints:
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * All the integers of nums are unique.
 */
public class Permutations_46 {
    public static List<List<Integer>> permute(int[] nums) {
        return permute_2(nums);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking：
     * 1. 递归思路
     * 1.1. 公式为：f(int[] nums,Set<Integer> seen,List<Integer> head,List<List<Integer>> ret)
     *
     * 验证通过：
     * Runtime 2 ms Beats 43.49% of users with Java
     * Memory 44.15 MB Beats 22.78% of users with Java
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute_2(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        List<Integer> head = new ArrayList<>();
        hepler(nums, seen, head, ret);
        return ret;
    }

    private static void hepler(int[] nums, Set<Integer> seen, List<Integer> head, List<List<Integer>> ret) {
        if (head.size() == nums.length) {
            ret.add(new ArrayList<>(head));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (seen.contains(nums[i])) continue;
            seen.add(nums[i]);
            head.add(nums[i]);
            hepler(nums, seen, head, ret);
            head.remove(head.size() - 1);
            seen.remove(nums[i]);
        }
    }

    /**
     * review
     * 递归
     *
     * 可以优化的地方：引入缓存，消除重复递归
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 52.34% of Java online submissions for Permutations.
     * Memory Usage: 39.2 MB, less than 64.62% of Java online submissions for Permutations.
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute_1(int[] nums) {
        return backtrack(nums, new HashSet<Integer>());
    }

    private static List<List<Integer>> backtrack(int[] nums, Set<Integer> appeared) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!appeared.contains(nums[i])) {
                //只剩最后一个元素
                if (appeared.size() + 1 == nums.length) {//这个分支的代码用来防止合并时，backtrack返回结果为空的情况。这样就不用判空了。
                    List<Integer> t = new ArrayList<>();
                    t.add(nums[i]);
                    ret.add(t);
                } else {
                    appeared.add(nums[i]);
                    //递归
                    List<List<Integer>> btRet = backtrack(nums, appeared);
                    //合并
                    for (List<Integer> lst : btRet) {//这里不需要判空了
                        lst.add(nums[i]);
                        ret.add(lst);
                    }
                    appeared.remove(nums[i]);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, new int[][]{{1, 2, 3}, {1, 3, 2}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}, {3, 2, 1}});
        do_func(new int[]{0, 1}, new int[][]{{0, 1}, {1, 0}});
        do_func(new int[]{1}, new int[][]{{1}});
        do_func(new int[]{1, 2, 3, 4, 5, 6}, new int[][]{{1}});
    }

    private static void do_func(int[] nums, int[][] expected) {
        List<List<Integer>> ret = permute(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
