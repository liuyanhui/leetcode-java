package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 47. Permutations II
 * Medium
 * -------------------------
 * Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in any order.
 *
 * Example 1:
 * Input: nums = [1,1,2]
 * Output:
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 *
 * Example 2:
 * Input: nums = [1,2,3]
 * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * Constraints:
 * 1 <= nums.length <= 8
 * -10 <= nums[i] <= 10
 */
public class Permutations_II_47 {
    /**
     * 金矿
     * review R2 ：手动人肉调试代码时，使用树形结构的方式，模拟递归调用。递归本质上就是树形结构的方式。
     * 思路：
     * 1.先排序
     * 2.本质上可以理解成多叉树的遍历
     * 3.在某个子树的（亲）兄弟节点间去重，如果一个子树的（亲）兄弟节点已经出现过该数字，那么跳过。主意是判重的范围是同一子树，不能把范围扩大化。
     * 4.利用已排序数组的特性，可以方便在兄弟节点间去重
     *
     * 另一种思路：先计算permutation，再去重
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 66.57% of Java online submissions for Permutations II.
     * Memory Usage: 39.2 MB, less than 99.49% of Java online submissions for Permutations II.
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        return backtrack(nums, 0, used);
    }

    private static List<List<Integer>> backtrack(int[] nums, int beg, boolean[] used) {
        List<List<Integer>> ret = new ArrayList<>();
        if (beg >= nums.length) {
            ret.add(new ArrayList<>());
            return ret;
        }
        int lastBloodBrother = Integer.MAX_VALUE;//记录（亲）兄弟节点的上一个出现的数字。用来判断是否重复出现过该数字。必须是已排序数组才行。
        for (int i = 0; i < nums.length; i++) {
            if (!used[i] && nums[i] != lastBloodBrother) {//用来判断是否被使用过，该数字是否在（亲）兄弟节点已经出现过
                lastBloodBrother = nums[i];
                used[i] = true;

                List<List<Integer>> tmp = backtrack(nums, beg + 1, used);
                //合并
                for (List<Integer> lst : tmp) {
                    lst.add(nums[i]);
                }
                ret.addAll(tmp);

                used[i] = false;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 1, 2}, new int[][]{{1, 1, 2}, {1, 2, 1}, {2, 1, 1}});
        do_func(new int[]{1, 2, 3}, new int[][]{{1, 2, 3}, {1, 3, 2}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}, {3, 2, 1}});
        do_func(new int[]{2, 1, 1}, new int[][]{{1, 1, 2}, {1, 2, 1}, {2, 1, 1}});
        do_func(new int[]{1, 2, 1}, new int[][]{{1, 1, 2}, {1, 2, 1}, {2, 1, 1}});
        do_func(new int[]{1, 1, 1, 1, 1, 1}, new int[][]{{1, 1, 1, 1, 1, 1}});
        do_func(new int[]{11}, new int[][]{{11}});


    }

    private static void do_func(int[] nums, int[][] expected) {
        List<List<Integer>> ret = permuteUnique(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
