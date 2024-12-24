package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 257. Binary Tree Paths
 * Easy
 * -------------------------
 * Given the root of a binary tree, return all root-to-leaf paths in any order.
 * A leaf is a node with no children.
 * <p>
 * Example 1:
 * Input: root = [1,2,3,null,5]
 * Output: ["1->2->5","1->3"]
 * <p>
 * Example 2:
 * Input: root = [1]
 * Output: ["1"]
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [1, 100].
 * -100 <= Node.val <= 100
 */
public class Binary_Tree_Paths_257 {
    public List<String> binaryTreePaths(TreeNode root) {
        return binaryTreePaths_r3_1(root);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 79.17%
     * Memory 42.66 MB Beats 52.05%
     *
     * @param root
     * @return
     */
    public static List<String> binaryTreePaths_r3_1(TreeNode root) {
        List<String> res = new ArrayList<>();
        preorder_r3_1(root, "", res);
        return res;
    }

    private static void preorder_r3_1(TreeNode node, String path, List<String> res) {
        if (node == null) {
            return;
        }
        String newPath = null;
        if (path == null || path.length() == 0) {
            newPath = String.valueOf(node.val);
        } else {
            newPath = path + "->" + String.valueOf(node.val);
        }
        if (node.left == null && node.right == null) {
            res.add(newPath);
            return;
        }
        preorder_r3_1(node.left, newPath, res);
        preorder_r3_1(node.right, newPath, res);
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.一般来说，Tree的问题无非是DFS或BFS。DFS有三种：preorder,inorder,postorder。
     * 2.本题采用DFS更合理。
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 94.57% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 42.7 MB, less than 86.64% of Java online submissions for Binary Tree Paths.
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths_2(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs_inorder(root, res, new ArrayList<>());
        return res;
    }

    private void dfs_inorder(TreeNode node, List<String> res, List<TreeNode> path) {
        if (node == null) return;
        path.add(node);
        if (node.left == null && node.right == null) {
            res.add(list2String(path));
        }
        dfs_inorder(node.left, res, path);
        dfs_inorder(node.right, res, path);
        path.remove(path.size() - 1);
    }

    private String list2String(List<TreeNode> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).val);
            if (i < list.size() - 1) sb.append("->");
        }
        return sb.toString();
    }

    /**
     * 验证通过：
     * Runtime: 10 ms, faster than 9.74% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 39.1 MB, less than 69.02% of Java online submissions for Binary Tree Paths.
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths_1(TreeNode root) {
        List<String> ret = new ArrayList<>();
        if (root == null) return ret;
        if (root.left == null && root.right == null) {
            ret.add(root.val + "");
            return ret;
        }
        List<String> l1 = binaryTreePaths(root.left);
        if (l1 != null && l1.size() > 0) {
            for (String s : l1) {
                ret.add(String.valueOf(root.val) + "->" + s);
            }
        }
        List<String> l2 = binaryTreePaths(root.right);
        if (l2 != null && l2.size() > 0) {
            for (String s : l2) {
                ret.add(String.valueOf(root.val) + "->" + s);
            }
        }
        return ret;
    }
}
