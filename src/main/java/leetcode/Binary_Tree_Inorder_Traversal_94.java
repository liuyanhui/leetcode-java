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
        return inorderTraversal_6(root);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * review inorderTraversal_4()
     * https://zhuanlan.zhihu.com/p/102285533
     *
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal_6(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode t = cur.left;
                //获取左子树的最右节点
                while (t.right != null && t.right != cur) {
                    t = t.right;
                }
                if (t.right == null) {//第一次访问，搭建临时路径，生成一个环
                    t.right = cur;
                    cur = cur.left;
                    continue;
                } else {//第二次访问，删除临时路径，破坏环，恢复原状
                    t.right = null;
                }
            }
            ret.add(cur.val);
            cur = cur.right;
        }

        return ret;
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
     * review inorderTraversal_3()是更优雅的迭代实现
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
     * review 金矿：
     * Threaded Binary Tree，or Morris Traversal
     * 1.在O(1)空间复杂度和O(N)时间复杂度约束下，实现binary Tree的遍历
     * 2.临时搭建一个回溯的路径，遍历完成后，再删除临时搭建的回溯路径。每个节点都需要搭建临时的回溯路径。
     * 3.inorder：为节点的左子树的最右子节点搭建临时路径；【可选】如果当前节点有左子节点，删除previous节点的回溯路径（其右子节点路径），把Tree恢复原状。
     * 3.1. 判断是否第一次访问节点的逻辑是：是否存在环。第一次访问时，环是不不存在的。第一次访问且满足条件时，才会产生一个环。
     * 3.2. 第一次访问节点时，恢复原状，打破环。
     *
     * 本实现，没有删除临时路径，把TreeNode恢复原状。
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
     * review 优雅的迭代实现
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
        //下面的判断条件"curr != null"很关键
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
