package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 113. Path Sum II
 * Medium
 * -----------------
 * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where each path's sum equals targetSum.
 * A leaf is a node with no children.
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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
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
