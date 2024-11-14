package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 216. Combination Sum III
 * Medium
 * -----------------------
 * Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
 * - Only numbers 1 through 9 are used.
 * - Each number is used at most once.
 * <p>
 * Return a list of all possible valid combinations. The list must not contain the same combination twice, and the combinations may be returned in any order.
 * <p>
 * Example 1:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * Explanation:
 * 1 + 2 + 4 = 7
 * There are no other valid combinations.
 * <p>
 * Example 2:
 * Input: k = 3, n = 9
 * Output: [[1,2,6],[1,3,5],[2,3,4]]
 * Explanation:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 * There are no other valid combinations.
 * <p>
 * Example 3:
 * Input: k = 4, n = 1
 * Output: []
 * Explanation: There are no valid combinations.
 * Using 4 different numbers in the range [1,9], the smallest sum we can get is 1+2+3+4 = 10 and since 10 > 1, there are no valid combination.
 * <p>
 * Constraints:
 * 2 <= k <= 9
 * 1 <= n <= 60
 */
public class Combination_Sum_III_216 {
    public static List<List<Integer>> combinationSum3(int k, int n) {
        return combinationSum3_r3_1(k, n);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. recursive solution
     * 从小到大依次计算，通过有序性保证结果的唯一性
     * <p>
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 41.10 MB Beats 33.88%
     *
     * @param k
     * @param n
     * @return
     */
    public static List<List<Integer>> combinationSum3_r3_1(int k, int n) {
        return helper_r3_1(k, n, 1);
    }

    private static List<List<Integer>> helper_r3_1(int k, int n, int beg) {
        List<List<Integer>> res = new ArrayList<>();
        if (k == 0 && n == 0) {
            res.add(new ArrayList<>());
            return res;
        }
        if (k < 0 || n < 0 || n < beg) {
            return res;
        }
        for (int i = beg; i <= 9; i++) {
            //递归计算剩余部分
            List<List<Integer>> tmpList = helper_r3_1(k - 1, n - i, i + 1);
            //合并结果
            if (tmpList != null && tmpList.size() > 0) {
                for (List<Integer> itemList : tmpList) {
                    List<Integer> newItemList = new ArrayList<>();
                    newItemList.add(i);
                    newItemList.addAll(itemList);
                    res.add(newItemList);
                }
            }
        }
        return res;
    }


    /**
     * 思考：
     * 1.n>54时，没有解。因为1+2+..+9=55，2<=k<=9。
     * 2.递归法，先挑选一个数字。如果sum<n，那么执行递归；如果sum>n，那么退出本次递归；如果sum=n，那么加入结果集。需要记录已经挑选过的数字，防止重复计入。
     * 3.递归一般有两种实现方式：
     * 3.1.返回值为true或false。
     * 3.2.返回值为结果集。
     * 4.通过顺序遍历的方式，可以避免重复计算。
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 85.65% of Java online submissions for Combination Sum III.
     * Memory Usage: 41.9 MB, less than 34.55% of Java online submissions for Combination Sum III.
     *
     * @param k
     * @param n
     * @return
     */
    public static List<List<Integer>> combinationSum3_1(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        helper(k, n, 1, res, path);
        return res;
    }

    private static void helper(int cnt, int sum, int beg, List<List<Integer>> res, List<Integer> path) {
        if (cnt < 0 || sum < 0) return;
        if (cnt == 0 && sum == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = beg; i <= 9; i++) {
            if (cnt == 0 || sum - i < 0) break;
            path.add(i);
            helper(cnt - 1, sum - i, i + 1, res, path);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        do_func(3, 7, new int[][]{{1, 2, 4}});
        do_func(3, 9, new int[][]{{1, 2, 6}, {1, 3, 5}, {2, 3, 4}});
        do_func(4, 1, new int[][]{});
        do_func(6, 30, new int[][]{});
    }

    private static void do_func(int k, int n, int[][] expected) {
        List<List<Integer>> ret = combinationSum3(k, n);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
