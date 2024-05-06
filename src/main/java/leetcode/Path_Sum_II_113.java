package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 113. Path Sum II
 * Medium
 * -----------------
 * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.
 *
 * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
 *
 * Example 1:
 * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * Output: [[5,4,11,2],[5,8,4,5]]
 *
 * Example 2:
 * Input: root = [1,2,3], targetSum = 5
 * Output: []
 *
 * Example 3:
 * Input: root = [1,2], targetSum = 0
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 5000].
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 */
public class Path_Sum_II_113 {

    /**
     *
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking：
     * 1. DFS思路。
     * 2. BFS思路。不适用，因为需要保存所有的路径。这样会使得算法很复杂，需要很多额外的存储空间。
     * 3. leaf节点是没有子节点的。
     *
     * 验证通过：
     *
     */
    public static List<List<Integer>> pathSum_3(TreeNode root, int targetSum) {
        List<List<Integer>> ret = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), ret);
        return ret;
    }

    private static void dfs(TreeNode node, int target, List<Integer> path, List<List<Integer>> ret) {
        if (node == null) return;//review 这里很关键，见Path_Sum_112.hasPathSum_3()
        if (node.left == null && node.right == null) {
            if (node.val == target) {
                List<Integer> t = new ArrayList<>(path);
                t.add(node.val);
                ret.add(t);
            }
            return;
        }
        path.add(node.val);
        dfs(node.left, target - node.val, path, ret);
        dfs(node.right, target - node.val, path, ret);
        path.remove(path.size() - 1);
    }


    /**
     * round 2
     * dfs+preorder
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 77.51% of Java online submissions for Path Sum II.
     * Memory Usage: 44.9 MB, less than 29.20% of Java online submissions for Path Sum II.
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum_2(TreeNode root, int targetSum) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> appeared = new ArrayList<>();
        helper(root, targetSum, appeared, ret);
        return ret;
    }

    private void helper(TreeNode node, int target, List<Integer> appeared, List<List<Integer>> ret) {
        if (node == null) return;
        target -= node.val;
        appeared.add(node.val);
        //下面这段代码可以优化，详见"112. Path Sum"
        if (node.left == null && node.right == null && target == 0) {
            ret.add(new ArrayList<>(appeared));
            appeared.remove(appeared.size() - 1);
            return;
        }
        helper(node.left, target, appeared, ret);
        helper(node.right, target, appeared, ret);
        appeared.remove(appeared.size() - 1);
    }

    /**
     * 递归思路，比较简单
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.97% of Java online submissions for Path Sum II.
     * Memory Usage: 39.5 MB, less than 58.49% of Java online submissions for Path Sum II.
     *
     * @param root
     * @param targetSum
     * @return
     */
    public List<List<Integer>> pathSum_1(TreeNode root, int targetSum) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> cached = new ArrayList<>();
        do_recursive(root, targetSum, 0, ret, cached);
        return ret;
    }

    private void do_recursive(TreeNode node, int target, int sum, List<List<Integer>> ret, List<Integer> cached) {
        if (node == null) return;

        cached.add(node.val);
        if (node.left == null && node.right == null) {
            if (sum + node.val == target) {
                ret.add(new ArrayList(cached));
            }
        } else {
            //left child
            do_recursive(node.left, target, sum + node.val, ret, cached);
            //right chlid
            do_recursive(node.right, target, sum + node.val, ret, cached);
        }
        cached.remove(cached.size() - 1);
    }
}
