package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 145. Binary Tree Postorder Traversal
 * Easy
 * ------------------------
 * Given the root of a binary tree, return the postorder traversal of its nodes' values.
 *
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [3,2,1]
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
 * The number of the nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 *
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class Binary_Tree_Postorder_Traversal_145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        return null;
    }

    /**
     * review
     * 非递归思路，迭代思路
     *
     * 参考网络思路
     *
     * 双stack思路，stack1存储node，stack2存储node在树中的位置（根0、左1、右2）
     * 算法如下：
     * 1.root入栈(stack1.push(root),stack2.push(1))
     * 2.stack1不为空时，循环
     * 3.stack1出栈 (n=stack1.pop(),s=stack2.pop() )
     * 3.1 如果n是叶子节点，那么 n加入ret，跳到步骤2
     * 3.2 如果s==0，那么 n加入ret，跳到步骤2
     * 3.3 如果s!=0，那么 n加入stack1且stack2.push(0)， 右子树加入stack1且stack2.push(2)，左子树加入stack1且stack2.push(2)，跳到步骤2
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal_2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        stack1.push(root);
        stack2.push(2);
        while (!stack1.empty()) {
            TreeNode n = stack1.pop();
            int s = stack2.pop();
            if (n == null) continue;
            if ((n.left == null && n.right == null) || s == 0) {
                ret.add(n.val);
            } else {
                stack1.push(n);
                stack2.push(0);
                stack1.push(n.right);
                stack2.push(2);
                stack1.push(n.left);
                stack2.push(1);
            }
        }

        return ret;
    }

    /**
     * 递归思路
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal_1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        helper(root, ret);
        return ret;
    }

    private void helper(TreeNode node, List<Integer> ret) {
        if (node == null) return;
        helper(node.left, ret);
        helper(node.right, ret);
        ret.add(node.val);
    }
}
