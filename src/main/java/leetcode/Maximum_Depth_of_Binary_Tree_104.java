package leetcode;

import java.util.ArrayList;
import java.util.List;

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

    public static int maxDepth(TreeNode root) {
        return maxDepth_2(root);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * BFS方案
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static int maxDepth_3(TreeNode root) {
        if (root == null) return 0;
        List<TreeNode> list1 = new ArrayList<>();
        List<TreeNode> list2 = null;
        list1.add(root);
        int ret = 0;
        while (list1.size() > 0) {
            list2 = new ArrayList<>();
            for (TreeNode node : list1) {
                if (node.left != null) list2.add(node.left);
                if (node.right != null) list2.add(node.right);
            }
            list1 = list2;
            ret++;
        }
        return ret;
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * DFS方案
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static int maxDepth_2(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * DFS法
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Depth of Binary Tree.
     * Memory Usage: 42.6 MB, less than 57.31% of Java online submissions for Maximum Depth of Binary Tree.
     * @param root
     * @return
     */
    public static int maxDepth_1(TreeNode root) {
        if (root == null) return 0;
        return helper(root, 1);
    }

    private static int helper(TreeNode node, int level) {
        if (node == null) return level - 1;
        int left = helper(node.left, level + 1);
        int right = helper(node.right, level + 1);
        return Math.max(left, right);
    }
}
