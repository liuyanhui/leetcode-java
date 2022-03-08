package leetcode;

import java.util.Stack;

/**
 * 101. Symmetric Tree
 * Easy
 * --------------------
 * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
 *
 * Example 1:
 * Input: root = [1,2,2,3,4,4,3]
 * Output: true
 *
 * Example 2:
 * Input: root = [1,2,2,null,3,null,3]
 * Output: false
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 1000].
 * -100 <= Node.val <= 100
 *
 * Follow up: Could you solve it both recursively and iteratively?
 */
public class Symmetric_Tree_101 {
    public boolean isSymmetric(TreeNode root) {
        return false;
    }

    /**
     * 迭代法
     * 验证通过：
     * Runtime: 2 ms, faster than 12.58% of Java online submissions for Symmetric Tree.
     * Memory Usage: 43.4 MB, less than 7.16% of Java online submissions for Symmetric Tree.
     *
     * @param root
     * @return
     */
    public boolean isSymmetric_2(TreeNode root) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root.left);
        stack2.push(root.right);
        while (!stack1.empty() && !stack2.empty() && stack1.size() == stack2.size()) {
            TreeNode n1 = stack1.pop();
            TreeNode n2 = stack2.pop();
            if (n1 == null && n2 == null) continue;
            if (n1 == null || n2 == null) return false;
            if (n1.val != n2.val) return false;
            stack1.push(n1.right);
            stack2.push(n2.left);
            stack1.push(n1.left);
            stack2.push(n2.right);
        }
        return true;
    }

    /**
     * 递归法
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Symmetric Tree.
     * Memory Usage: 42.7 MB, less than 12.87% of Java online submissions for Symmetric Tree.
     *
     * @param root
     * @return
     */
    public boolean isSymmetric_1(TreeNode root) {
        return check(root.left, root.right);
    }

    private boolean check(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;
        if (node1.val != node2.val) return false;
        return check(node1.left, node2.right) && check(node1.right, node2.left);
    }
}
