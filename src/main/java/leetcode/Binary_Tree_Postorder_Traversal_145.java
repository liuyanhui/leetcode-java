package leetcode;

import java.util.*;

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
     *
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking:
     * 1. 递归法很简单
     * 2. 迭代法比较复杂，用到了多个数据结果，Stack和Deque
     *
     */
    public static List<Integer> postorderTraversal_r3_1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        if (root.left != null)
            ret.addAll(postorderTraversal_r3_1(root.left));
        if (root.right != null)
            ret.addAll(postorderTraversal_r3_1(root.right));
        ret.add(root.val);
        return ret;
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * review
     * Thinking：
     * 1. PostInorder
     * 节点可分可以分为2种类型和3种情况：
     * 仅被访问1次：叶子节点，直接处理该节点。
     * 被访问2次：第一次，右子节点入栈（不计入该节点的访问次数），当前节点入栈（计入访问次数），左子节点入栈（不计入该节点的访问次数）；第二次，处理该节点。
     *
     * 把访问访问0次的节点和访问1次的节点分开存储即可。使用两个Stack，Stack1保存访问0次的节点（访问1次的节点用null代替，用来表示需要从Stack2中获取数据），Stack2保存访问1次的节点。
     * 或者使用Deque，头部保存访问1次的节点，尾部保存访问0次的节点（尾部节点为null时，表示需要从头部获取该节点）。
     *
     * postorderTraversal_r3_2()和postorderTraversal_r3_3()是相同的实现思路
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal_r3_2(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Stack<TreeNode> stack0 = new Stack<>();
        Stack<TreeNode> stack1 = new Stack<>();
        stack0.push(root);
        while (!stack0.empty()) {
            TreeNode t = stack0.pop();
            //第2次访问
            if (t == null) {
                ret.add(stack1.pop().val);//从stack2中获取并直接处理当前节点
            } else {//第1次访问
                if (t.left == null && t.right == null) {//叶子节点
                    ret.add(t.val);
                    continue;
                }
                //当前节点第1次访问后入栈
                stack0.push(null);
                stack1.push(t);
                //入栈右子节点
                if (t.right != null) {
                    stack0.push(t.right);
                }
                //入栈左子节点
                if (t.left != null) {
                    stack0.push(t.left);
                }
            }
        }
        return ret;
    }

    public static List<Integer> postorderTraversal_r3_3(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) return ret;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode t = stack.pop();
            if (t == null) {
                ret.add(stack.pollFirst().val);
            } else {
                if (t.left == null && t.right == null) {
                    ret.add(t.val);
                    continue;
                }
                stack.addFirst(t);
                stack.addLast(null);
                if (t.right != null) {
                    stack.push(t.right);
                }
                if (t.left != null) {
                    stack.push(t.left);
                }
            }
        }
        return ret;
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
     * 验证通过：
     * Runtime: 1 ms, faster than 45.01% of Java online submissions for Binary Tree Postorder Traversal.
     * Memory Usage: 40.3 MB, less than 90.69% of Java online submissions for Binary Tree Postorder Traversal.
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
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Postorder Traversal.
     * Memory Usage: 42.5 MB, less than 28.35% of Java online submissions for Binary Tree Postorder Traversal.
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
