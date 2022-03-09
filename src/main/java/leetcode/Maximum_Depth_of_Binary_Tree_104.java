package leetcode;

/**
 * 104. Maximum Depth of Binary Tree
 * Easy
 * --------------------------
 * Given the root of a binary tree, return its maximum depth.
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 *
 * Example 2:
 * Input: root = [1,null,2]
 * Output: 2
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 10^4].
 * -100 <= Node.val <= 100
 */
public class Maximum_Depth_of_Binary_Tree_104 {
    /**
     * DFS法
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Depth of Binary Tree.
     * Memory Usage: 42.6 MB, less than 57.31% of Java online submissions for Maximum Depth of Binary Tree.
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return helper(root, 1);
    }

    private int helper(TreeNode node, int level) {
        if (node == null) return level - 1;
        int left = helper(node.left, level + 1);
        int right = helper(node.right, level + 1);
        return Math.max(left, right);
    }
}
