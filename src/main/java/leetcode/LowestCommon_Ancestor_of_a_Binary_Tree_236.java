package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 *236. Lowest Common Ancestor of a Binary Tree
 * Medium
 * -------------------------------
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Example 1:
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 *
 * Example 2:
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
 * Output: 5
 * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 *
 * Example 3:
 * Input: root = [1,2], p = 1, q = 2
 * Output: 1
 *
 * Constraints:
 * The number of nodes in the tree is in the range [2, 10^5].
 * -10^9 <= Node.val <= 10^9
 * All Node.val are unique.
 * p != q
 * p and q will exist in the tree.
 */
public class LowestCommon_Ancestor_of_a_Binary_Tree_236 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return lowestCommonAncestor_1(root, p, q);
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65226/My-Java-Solution-which-is-easy-to-understand
     *
     * 验证通过:
     * Runtime: 7 ms, faster than 90.84% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     * Memory Usage: 43.6 MB, less than 94.10% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_3(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/ 之Approach1
     *
     * 验证通过：
     * Runtime: 15 ms, faster than 31.12% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     * Memory Usage: 47.1 MB, less than 67.38% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_2(TreeNode root, TreeNode p, TreeNode q) {
        ans = null;
        findAncestor(root, p, q);
        return ans;
    }

    private TreeNode ans = null;

    private int findAncestor(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return 0;
        if (ans != null) return 0;
        int left = 0, right = 0, mid = 0;
        if (node == p || node == q) mid = 1;
        left = findAncestor(node.left, p, q);
        right = findAncestor(node.right, p, q);
        if (left + right + mid >= 2) {
            ans = node;
        }
        return left + right + mid > 0 ? 1 : 0;
    }

    /**
     * 思路：
     * 1.先找到节点，并记录路径
     * 2.比较路径找出LCA
     *
     * 验证通过：性能一般
     * Runtime: 19 ms, faster than 13.19% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     * Memory Usage: 47.7 MB, less than 30.98% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = new ArrayList<>();
        List<TreeNode> path2 = new ArrayList<>();
        findNode(root, p, path1);
        findNode(root, q, path2);
        int i = 0;
        for (; i < path1.size() && i < path2.size(); i++) {
            if (path1.get(i) != path2.get(i))
                break;
        }
        return path1.get(i - 1);
    }

    private boolean findNode(TreeNode node, TreeNode target, List<TreeNode> path) {
        if (node == null) return false;
        path.add(node);
        if (node == target) return true;
        if (findNode(node.left, target, path) || findNode(node.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }
}
