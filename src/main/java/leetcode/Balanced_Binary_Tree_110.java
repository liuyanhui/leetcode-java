package leetcode;

/**
 * 110. Balanced Binary Tree
 * Easy
 * --------------------------
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as:
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: true
 *
 * Example 2:
 * Input: root = [1,2,2,3,3,null,null,4,4]
 * Output: false
 *
 * Example 3:
 * Input: root = []
 * Output: true
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 5000].
 * -10^4 <= Node.val <= 10^4
 */
public class Balanced_Binary_Tree_110 {
    public boolean isBalanced(TreeNode root) {
        return isBalanced_1(root);
    }

    /**
     * 递归法：postorder
     * 1.左子树和右子树都是平衡树
     * 2.如果左子树深度和右子树深度相差1，那么已当前节点为根的树也是平衡树
     * 3.递归1,2
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 86.95% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 44.9 MB, less than 12.37% of Java online submissions for Balanced Binary Tree.
     *
     * @param root
     * @return
     */
    public boolean isBalanced_1(TreeNode root) {
        int sig = helper(root);
        return sig >= 0 ? true : false;
    }

    private int helper(TreeNode node) {
        if (node == null) return 0;
        int depth = 0;
        int left = helper(node.left);
        int right = helper(node.right);
        if ((left >= 0 && right >= 0) && (left == right || left + 1 == right || left - 1 == right)) {
            depth = Math.max(right, left) + 1;
        } else {
            depth = -1;
        }
        return depth;
    }
}
