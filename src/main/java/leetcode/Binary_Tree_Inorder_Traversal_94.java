package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 94. Binary Tree Inorder Traversal
 * Easy
 * -------------------------
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 *
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [1,3,2]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Example 3:
 * Input: root = [1]
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 *
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class Binary_Tree_Inorder_Traversal_94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        return inorderTraversal_5(root);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. Tree问题的方案可以分为：DFS和BFS，其中DFS分为preorder、inorder和postorder三种。
     * 2. Tree是特殊的Graph。
     * 3. review 采用栈+循环，也可以采用递归。重点是出栈后的逻辑要覆盖各种情况。
     * 3.1. 出栈的节点node，只需要执行两步：当前节点node.val加入结果集；node.right入栈；node.right的所有的左子孙节点都入栈
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 41.51 MB Beats 39.43%
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal_5(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (root.left != null) {
            stack.push(root.left);
            root = root.left;
        }
        while (!stack.empty()) {
            TreeNode t = stack.pop();
            ret.add(t.val);
            //右子节点入栈
            if (t.right != null) {
                stack.push(t.right);
                //右子节点的左子孙节点入栈
                TreeNode tl = t.right;
                while (tl.left != null) {
                    stack.push(tl.left);
                    tl = tl.left;
                }
            }
        }
        return ret;
    }

    /**
     * 金矿：
     * Threaded Binary Tree，or Morris Traversal
     *
     * 参考思路：
     * https://leetcode.com/problems/binary-tree-inorder-traversal/solution/
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.5 MB, less than 13.55% of Java online submissions for Binary Tree Inorder Traversal.
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal_4(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                ret.add(cur.val);
                cur = cur.right;
            } else {
                //找到左子树的最右子节点
                TreeNode rightmost = cur.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }
                //把当前节点挂到左子树的最右子节点的右节点下
                rightmost.right = cur;
                //修改cur的左节点为空
                TreeNode t = cur.left;
                cur.left = null;
                //更新cur
                cur = t;
            }
        }
        return ret;
    }

    /**
     * 迭代
     *
     * 参考资料：
     * https://leetcode.com/problems/binary-tree-inorder-traversal/solution/
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 40.60% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.7 MB, less than 6.76% of Java online submissions for Binary Tree Inorder Traversal.
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal_3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    /**
     * 迭代
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 40.60% of Java online submissions for Binary Tree Inorder Traversal.
     * Memory Usage: 42.2 MB, less than 24.72% of Java online submissions for Binary Tree Inorder Traversal.
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal_2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (stack.peek().left != null) {
            stack.push(stack.peek().left);
        }
        while (!stack.empty()) {
            TreeNode t = stack.pop();
            ret.add(t.val);
            if (t.right != null) {
                stack.push(t.right);
                while (stack.peek().left != null) {
                    stack.push(stack.peek().left);
                }
            }
        }
        return ret;
    }

    /**
     * 递归
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal_1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        dfs(root, ret);
        return ret;
    }

    private void dfs(TreeNode node, List<Integer> ret) {
        if (node == null) return;
        dfs(node.left, ret);
        ret.add(node.val);
        dfs(node.right, ret);
    }
}
