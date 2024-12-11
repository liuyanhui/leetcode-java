package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 235. Lowest Common Ancestor of a Binary Search Tree
 * Medium
 * -----------------------
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 * <p>
 * Example 1:
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * Output: 6
 * Explanation: The LCA of nodes 2 and 8 is 6.
 * <p>
 * Example 2:
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * Output: 2
 * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 * <p>
 * Example 3:
 * Input: root = [2,1], p = 2, q = 1
 * Output: 2
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [2, 10^5].
 * -10^9 <= Node.val <= 10^9
 * All Node.val are unique.
 * p != q
 * p and q will exist in the BST.
 */
public class Lowest_Common_Ancestor_of_a_Binary_Search_Tree_235 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return lowestCommonAncestor_r3_1(root, p, q);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 方案1：先找到root->p的路径，再找到root->q的路径，比较两个路径得到LCA
     * 2. 方案2：
     * 如果 min(p.val,q.val)<root.val AND root.val<max(p.val,q.val) ，表示 p,q在root的两侧，root为LCA
     * 如果 p,q在root的同一侧 ，继续计算f(root.left,p,q) 或 f(root.right,p,q)
     * 如果 p,q其中一个是root，root为LCA
     * <p>
     * 验证通过：
     * Runtime 5 ms Beats 100.00%
     * Memory 44.72 MB Beats 84.46%
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_r3_1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else {
            return root;
        }
    }

    /**
     * review round 2
     * 参考思路：
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/solution/
     * <p>
     * 充分利用BST的特性。
     * 1.如果p,q同时大于root，去右子树查找
     * 2.如果p,q同时小于于root，去左子树查找
     * 3.root为所求
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 100.00% of Java online submissions
     * Memory Usage: 39.7 MB, less than 68.46% of Java online submissions
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return root;
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor_2(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor_2(root.right, p, q);
        } else {
            return root;
        }
    }

    /**
     * round2 思考：
     * 1.树的问题一般分为两种套路，分别是：bfs、dfs或preorder、inorder和postorder。
     * 2.dfs思路：使用preorder查找p和q，并分别记录从root到p或q的路径，比较两个路径就可以得到LCA
     * <p>
     * round1 AC
     * 验证通过：
     * Runtime: 3 ms, faster than 100.00% of Java online submissions
     * Memory Usage: 39.6 MB, less than 81.93% of Java online submissions
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = new ArrayList<>();
        List<TreeNode> path2 = new ArrayList<>();
        findNode(root, path1, p);
        findNode(root, path2, q);
        int i = 0;
        while (i < path1.size() && i < path2.size() && path1.get(i).val == path2.get(i).val) {
            i++;
        }
        return path1.get(i - 1);
    }

    private void findNode(TreeNode node, List<TreeNode> path, TreeNode target) {
        if (node == null) return;
        path.add(node);
        if (target.val < node.val) {
            findNode(node.left, path, target);
        } else if (target.val > node.val) {
            findNode(node.right, path, target);
        } else {
            return;
        }
    }
}
