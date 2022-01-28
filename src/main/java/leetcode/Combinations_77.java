package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 77. Combinations
 * Medium
 * -----------------------------
 * Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].
 * You may return the answer in any order.
 *
 * Example 1:
 * Input: n = 4, k = 2
 * Output:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * Example 2:
 * Input: n = 1, k = 1
 * Output: [[1]]
 *
 * Constraints:
 * 1 <= n <= 20
 * 1 <= k <= n
 */
public class Combinations_77 {
    public static List<List<Integer>> combine(int n, int k) {
        return combine_2(n, k);
    }

    /**
     * dfs思路
     * 参考文档：https://leetcode.com/problems/combinations/discuss/27002/Backtracking-Solution-Java
     *
     * 验证通过：
     * Runtime: 30 ms, faster than 40.91% of Java online submissions for Combinations.
     * Memory Usage: 55.1 MB, less than 13.92% of Java online submissions for Combinations.
     * 
     * @param n
     * @param k
     * @return
     */
    public static List<List<Integer>> combine_2(int n, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        dfs(n, k, 1, ret, new ArrayList<>());
        return ret;
    }

    private static void dfs(int n, int k, int beg, List<List<Integer>> ret, List<Integer> cur) {
        if (cur.size() == k) {
            ret.add(new ArrayList<>(cur));
            return;
        }
        for (int i = beg; i <= n; i++) {
            cur.add(i);
            dfs(n, k, i + 1, ret, cur);
            cur.remove(cur.size() - 1);
        }

    }

    /**
     * 递归思路
     * i+combine(int[] arr exclude i, k-1)
     * 举例1：
     * f([1,2,3,4], 2)
     * =>1+f([2,3,4],1) | 2+f([3,4],1) | 3+f([4],1)
     * 举例2：
     * f([1,2,3,4], 3)
     * =>1+f([2,3,4],2) | 2+f([3,4],2) | 3+f([4],2)
     *
     * 验证通过：
     * Runtime: 69 ms, faster than 9.66% of Java online submissions for Combinations.
     * Memory Usage: 78.7 MB, less than 5.28% of Java online submissions for Combinations.
     *
     * @param n
     * @param k
     * @return
     */
    public static List<List<Integer>> combine_1(int n, int k) {
        return do_recursive(n, k, 1);
    }

    private static List<List<Integer>> do_recursive(int n, int k, int beg) {
        List<List<Integer>> ret = new ArrayList<>();
        if (k <= 0) {
            ret.add(new ArrayList<>());
            return ret;
        }
        for (int i = beg; i <= n; i++) {
            List<List<Integer>> successor = do_recursive(n, k - 1, i + 1);
            //combine t and i
            for (List<Integer> t : successor) {
                t.add(i);
                ret.add(t);
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(4, 2, new int[][]{{2, 4}, {3, 4}, {2, 3}, {1, 2}, {1, 3}, {1, 4}});
        do_func(1, 1, new int[][]{{1}});
        do_func(20, 20, new int[][]{{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}});
//        do_func(20, 10, new int[][]{{20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}});

    }

    private static void do_func(int n, int k, int[][] expected) {
        List<List<Integer>> ret = combine(n, k);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
