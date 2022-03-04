package leetcode;

import java.util.Stack;

/**
 * 99. Recover Binary Search Tree
 * Medium
 * ------------------------
 * You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.
 *
 * Example 1:
 * Input: root = [1,3,null,null,2]
 * Output: [3,1,null,null,2]
 * Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.
 *
 * Example 2:
 * Input: root = [3,1,4,null,null,2]
 * Output: [2,1,4,null,null,3]
 * Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [2, 1000].
 * -2^31 <= Node.val <= 2^31 - 1
 *
 * Follow up: A solution using O(n) space is pretty straight-forward. Could you devise a constant O(1) space solution?
 */
public class Recover_Binary_Search_Tree_99 {
    public void recoverTree(TreeNode root) {
        recoverTree_1(root);
    }

    /**
     * 本题是Validate_Binary_Search_Tree_98的延伸，参考了它的isValidBST_2()方法
     *
     * 思路如下：
     * 0.BST在in-order过程中就是有序的
     * 1.in-order过程中，不满足条件时，必然会识别出错误节点。因为正确的顺序是从小到大的，如果出错必然是较大节点先出现。所以第一次记录较大节点为node1，第二次记录较小节点node2。
     * 2.特殊情况：当两个节点是父子关系。
     * 3.swap(node1,node2)。
     * 4.第一步中，首次发现异常时，设置node1=较大节点，node2=较小节点。后续再发现异常，只更新node2即可。
     *
     * Time Complexity:O(n)
     * Space Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 19.24% of Java online submissions for Recover Binary Search Tree.
     * Memory Usage: 48 MB, less than 7.10% of Java online submissions for Recover Binary Search Tree.
     *
     * @param root
     */
    public void recoverTree_1(TreeNode root) {
        TreeNode node1 = null, node2 = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        while (root != null || !stack.empty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && pre.val > root.val) {
                if (node1 == null) node1 = pre;
                node2 = root;
            }
            pre = root;
            root = root.right;
        }
        if (node1 != null) {
            int t = node1.val;
            node1.val = node2.val;
            node2.val = t;
        }
    }
}
