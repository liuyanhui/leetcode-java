package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 199. Binary Tree Right Side View
 * Medium
 * --------------------------
 * Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
 *
 * Example 1:
 * Input: root = [1,2,3,null,5,null,4]
 * Output: [1,3,4]
 *
 * Example 2:
 * Input: root = [1,null,3]
 * Output: [1,3]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class Binary_Tree_Right_Side_View_199 {
    public List<Integer> rightSideView(TreeNode root) {
        return rightSideView_dfs(root);
    }

    /**
     * bfs思路
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 72.84% of Java online submissions for Binary Tree Right Side View.
     * Memory Usage: 37.6 MB, less than 50.79% of Java online submissions for Binary Tree Right Side View.
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView_bfs(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        List<TreeNode> lv1List = new ArrayList<>();
        List<TreeNode> lv2List = null;
        lv1List.add(root);
        while (lv1List.size() > 0) {
            lv2List = new ArrayList<>();
            ret.add(lv1List.get(0).val);
            for (TreeNode n : lv1List) {
                if (n.right != null) lv2List.add(n.right);
                if (n.left != null) lv2List.add(n.left);
            }
            lv1List = lv2List;
        }
        return ret;
    }

    /**
     * dfs思路：
     * 遍历顺序是：root->right->left，记录遍历深度
     * 用list记录最右节点
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Right Side View.
     * Memory Usage: 38 MB, less than 16.24% of Java online submissions for Binary Tree Right Side View.
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView_dfs(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        do_dfs(ret, root, 1);
        return ret;
    }

    private void do_dfs(List<Integer> ret, TreeNode node, int level) {
        if (node == null) return;
        if (ret.size() < level) {
            ret.add(node.val);
        }
        do_dfs(ret, node.right, level + 1);
        do_dfs(ret, node.left, level + 1);
    }
}
