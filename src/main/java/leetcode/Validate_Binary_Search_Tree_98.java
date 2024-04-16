package leetcode;

import java.util.Stack;

/**
 * 98. Validate Binary Search Tree
 * Medium
 * -----------------------------
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 * Example 1:
 * Input: root = [2,1,3]
 * Output: true
 *
 * Example 2:
 * Input: root = [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 10^4].
 * -2^31 <= Node.val <= 2^31 - 1
 */
public class Validate_Binary_Search_Tree_98 {
    /**
     * round 3
     * Score[1] Lower is harder
     *
     * review R2 20220302
     *
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBST_1(root);
    }

    /**
     * 递归法
     * 参考思路：
     * https://leetcode.com/problems/validate-binary-search-tree/discuss/32109/My-simple-Java-solution-in-3-lines
     *
     * 说明：
     * 如果一个Tree数BST，需要满足以下条件：
     * 1.左、右子树分别都是BST
     * 2.左子树的最右节点小于父节点
     * 3.右子树的最左节点大于父节点
     *
     * 思路1：
     * in-order递归时把父节点作为参数传入。
     *这个思路中父节点会承担两中角色，左子树的最大值边界和右子树的最小值边界。这种方式会使一个变量承担两种职能，并且还需要一个变量left_or_right说明是左子树还是右子树，增加了实现的复杂度。
     *
     * 思路2：在思路1的基础上进行优化。
     * 把父节点作为边界值，在两个子树中的作用不同。父节点分别作为左子树的最大值边界和右子树的最小值边界，那么就可以把父节点拆分成最大值和最小值递归函数中。
     *
     * 本函数就是采用了【思路2】。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 43.4 MB, less than 39.97% of Java online submissions for Validate Binary Search Tree.
     *
     * @param root
     * @return
     */
    public static boolean isValidBST_1(TreeNode root) {
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    //如果用int类型,[2147483647]用例无法通过
    private static boolean check(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (min >= node.val || node.val >= max) return false;
        return check(node.left, min, node.val) && check(node.right, node.val, max);
    }

    /**
     * in-order 迭代遍历法
     * 参考思路：
     * https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)
     *
     * 说明：
     * BST如果使用in-order遍历的过程就是把整个树序列化成有序数组的过程。那么在in-order遍历的过程就可以用来判断tree是否为BST
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 21.91% of Java online submissions for Validate Binary Search Tree.
     * Memory Usage: 44.2 MB, less than 19.45% of Java online submissions for Validate Binary Search Tree.
     *
     * @param root
     * @return
     */
    public static boolean isValidBST_2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.empty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && root.val < pre.val) return false;
            pre = root;
            root = root.right;
        }
        return true;
    }

}
