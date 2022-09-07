package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 230. Kth Smallest Element in a BST
 * Medium
 * -----------------------------------
 * Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed) of all the values of the nodes in the tree.
 *
 * Example 1:
 * Input: root = [3,1,4,null,2], k = 1
 * Output: 1
 *
 * Example 2:
 * Input: root = [5,3,6,2,4,null,null,1], k = 3
 * Output: 3
 *
 * Constraints:
 * The number of nodes in the tree is n.
 * 1 <= k <= n <= 10^4
 * 0 <= Node.val <= 10^4
 *
 * Follow up: If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?
 */
public class Kth_Smallest_Element_in_a_BST_230 {
    /**
     * 思考：
     * 1.BST的inorder遍历的结果是升序的
     * 2.dfs+inorder遍历算法可以解决本题
     *
     * 没理解为啥这么简单的题是medium难度。难道是打开的姿势不对？
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Kth Smallest Element in a BST.
     * Memory Usage: 41.9 MB, less than 97.70% of Java online submissions for Kth Smallest Element in a BST.
     *
     * @param root
     * @param k
     * @return
     */
    public static int kthSmallest(TreeNode root, int k) {
        List<TreeNode> list = new ArrayList<>();
        dfs_inorder(root, k, list);
        return list.get(k - 1).val;
    }

    private static void dfs_inorder(TreeNode node, int k, List<TreeNode> list) {
        if (node == null) return;
        dfs_inorder(node.left, k, list);
        if (list.size() == k) return;
        list.add(node);
        dfs_inorder(node.right, k, list);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        int res1 = kthSmallest(root, 1);
        System.out.println("res1=" + res1);
        System.out.println("----------------");
//        System.out.println("----------------");
    }
}
