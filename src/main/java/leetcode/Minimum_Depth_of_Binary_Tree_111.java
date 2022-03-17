package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 111. Minimum Depth of Binary Tree
 * Easy
 * --------------------------
 * Given a binary tree, find its minimum depth.
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 * Note: A leaf is a node with no children.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 2
 *
 * Example 2:
 * Input: root = [2,null,3,null,4,null,5,null,6]
 * Output: 5
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 10^5].
 * -1000 <= Node.val <= 1000
 */
public class Minimum_Depth_of_Binary_Tree_111 {
    public int minDepth(TreeNode root) {
        return minDepth_1(root);
    }

    /**
     * 一种非常简洁的代码
     * dfs是不需要把leve也作为参数输入，类似于minDepth_1()就是把level也输入了。
     *
     * 参考文档：
     * https://leetcode.com/problems/minimum-depth-of-binary-tree/discuss/36045/My-4-Line-java-solution
     *
     * @param root
     * @return
     */
    public int minDepth_3(TreeNode root) {
        if (root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
    }

    /**
     * bfs+queue
     * 使用queue
     * 第一个出现的左右子树为空的节点，该节点的深度就是树的最小深度
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 95.81% of Java online submissions for Minimum Depth of Binary Tree.
     * Memory Usage: 86.5 MB, less than 76.05% of Java online submissions for Minimum Depth of Binary Tree.
     *
     * @param root
     * @return
     */
    public int minDepth_2(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        TreeNode t = null;
        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                t = queue.poll();
                if (t == null) continue;
                if (t.left == null && t.right == null) return depth;
                if (t.left != null) queue.offer(t.left);
                if (t.right != null) queue.offer(t.right);
            }
        }
        return depth;
    }

    /**
     * dfs+preorder
     * 1.如果当前节点node为leaf，当前深度为节点node的最小深度
     * 2.如果当前节点node为不是leaf
     * 2.1 如果node.left为空，返回左子树的最小深度
     * 2.2 如果node.right为空，返回右子树的最小深度
     * 2.3 如果node.left和node.right都不为空，返回左右子树的最小深度的较小值
     * 3.递归1,2
     *
     * 验证通过：
     * Runtime: 18 ms, faster than 12.18% of Java online submissions for Minimum Depth of Binary Tree.
     * Memory Usage: 95.6 MB, less than 19.47% of Java online submissions for Minimum Depth of Binary Tree.
     *
     * @param root
     * @return
     */
    public int minDepth_1(TreeNode root) {
        return helper(root, 1);
    }

    private int helper(TreeNode node, int level) {
        if (node == null) return level - 1;
        if (node.left == null && node.right == null) {
            return level;
        } else if (node.left == null) {
            return helper(node.right, level + 1);
        } else if (node.right == null) {
            return helper(node.left, level + 1);
        } else {
            return Math.min(helper(node.left, level + 1), helper(node.right, level + 1));
        }
    }
}
