package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 144. Binary Tree Preorder Traversal
 * Medium
 * -----------------
 * Given the root of a binary tree, return the preorder traversal of its nodes' values.
 *
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [1,2,3]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Example 3:
 * Input: root = [1]
 * Output: [1]
 *
 * Example 4:
 * Input: root = [1,2]
 * Output: [1,2]
 *
 * Example 5:
 * Input: root = [1,null,2]
 * Output: [1,2]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 *
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class Binary_Tree_Preorder_Traversal_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        return preorderTraversal_1(root);
    }

    /**
     * 迭代版本
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Preorder Traversal.
     * Memory Usage: 37.2 MB, less than 65.93% of Java online submissions for Binary Tree Preorder Traversal.
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal_2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (stack.size() > 0) {
            TreeNode t = stack.pop();
            if (t == null) continue;
            ret.add(t.val);
            stack.push(t.right);
            stack.push(t.left);
        }
        return ret;
    }

    /**
     * 递归思路
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal_1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        do_recursive(root, ret);
        return ret;
    }

    private void do_recursive(TreeNode node, List<Integer> ret) {
        if (node == null) return;
        ret.add(node.val);
        do_recursive(node.left, ret);
        do_recursive(node.right, ret);
    }


}
