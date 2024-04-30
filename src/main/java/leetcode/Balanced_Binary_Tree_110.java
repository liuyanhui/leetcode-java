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
    public static boolean isBalanced(TreeNode root) {
        return isBalanced_1(root);
    }

    public static boolean isBalanced_3(TreeNode root) {
        return helper_3(root) >= 0;
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * 见isBalanced_2()
     *
     * 验证通过：
     *
     * @param node
     * @return
     */
    private static int helper_3(TreeNode node) {
        if (node == null) return 0;
        int left = helper_3(node.left);
        int right = helper_3(node.right);
        if (left >= 0 && right >= 0 && Math.abs(left - right) <= 1)
            return Math.max(right, left) + 1;
        return -1;
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. 递归。先判断每个子树是否为balanced tree，再判断当前节点的树是否为balanced tree。
     * 2. 把深度计算和是否平衡树的逻辑分离开。
     *
     * isBalanced_1()和isBalanced_2()是把深度计算和是否为balanced tree合二为一的方案。如果返回值小于0，表示不是balance tree；否则，返回深度。
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static boolean isBalanced_2(TreeNode root) {
        if (root == null) return true;
        if (isBalanced(root.left) && isBalanced(root.right)) {
            int left = helper_2(root.left);
            int right = helper_2(root.right);
            if (Math.abs(left - right) <= 1) {
                return true;
            }
        }
        return false;
    }

    private static int helper_2(TreeNode node) {
        if (node == null) return 0;
        return Math.max(helper_2(node.left), helper_2(node.right)) + 1;
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
    public static boolean isBalanced_1(TreeNode root) {
        int sig = helper(root);
        return sig >= 0 ? true : false;
    }

    private static int helper(TreeNode node) {
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
