package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 102. Binary Tree Level Order Traversal
 * Medium
 * -------------------------------------
 * Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[9,20],[15,7]]
 *
 * Example 2:
 * Input: root = [1]
 * Output: [[1]]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -1000 <= Node.val <= 1000
 */
public class Binary_Tree_Level_Order_Traversal_102 {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        return levelOrder_1(root);
    }

    /**
     * BFS法
     * 双队列，当前遍历队列和下一次遍历队列
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 83.92% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 42.4 MB, less than 57.32% of Java online submissions for Binary Tree Level Order Traversal.
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder_2(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) return ret;
        List<TreeNode> curQueue = new ArrayList<>();
        List<TreeNode> nextQueue = new ArrayList<>();
        curQueue.add(root);
        while (curQueue.size() > 0) {
            ret.add(new ArrayList<>());
            for (TreeNode node : curQueue) {
                ret.get(ret.size() - 1).add(node.val);
                if (node.left != null) nextQueue.add(node.left);
                if (node.right != null) nextQueue.add(node.right);
            }
            curQueue = nextQueue;
            nextQueue = new ArrayList<>();
        }
        return ret;
    }

    /**
     * 递归法
     * pre-order遍历，根据深度执行append()操作
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Level Order Traversal.
     * Memory Usage: 42.3 MB, less than 59.19% of Java online submissions for Binary Tree Level Order Traversal.
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder_1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        preOrderDfs(root, 1, ret);
        return ret;
    }

    private static void preOrderDfs(TreeNode node, int level, List<List<Integer>> ret) {
        if (node == null) return;
        if (level > ret.size()) ret.add(new ArrayList<>());
        ret.get(level - 1).add(node.val);
        preOrderDfs(node.left, level + 1, ret);
        preOrderDfs(node.right, level + 1, ret);
    }
}
