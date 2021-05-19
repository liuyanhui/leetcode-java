package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 257. Binary Tree Paths
 * Easy
 * -------------------------
 * Given the root of a binary tree, return all root-to-leaf paths in any order.
 * A leaf is a node with no children.
 *
 * Example 1:
 * Input: root = [1,2,3,null,5]
 * Output: ["1->2->5","1->3"]
 *
 * Example 2:
 * Input: root = [1]
 * Output: ["1"]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 100].
 * -100 <= Node.val <= 100
 */
public class Binary_Tree_Paths_257 {
    /**
     * 验证通过：
     * Runtime: 10 ms, faster than 9.74% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 39.1 MB, less than 69.02% of Java online submissions for Binary Tree Paths.
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
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
