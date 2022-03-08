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
    public static void recoverTree(TreeNode root) {
        recoverTree_2(root);
    }

    /**
     * 金矿
     * Threaded Binary Tree，or Morris Traversal
     *
     * 参考资料：
     * https://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
     * https://leetcode.com/problems/recover-binary-search-tree/discuss/32559/Detail-Explain-about-How-Morris-Traversal-Finds-two-Incorrect-Pointer
     *
     * 在O(1)空间复杂度下，实现BST的遍历。
     * Morris Traversal的核心思想是：遍历前，查找当前节点的前驱结点；遍历后，删除前驱节点，恢复BST。
     * in-order遍历的算法如下：
     * 1.如果当前节点cur的左子树为空，输出当前节点cur，cur=cur.right
     * 2.如果当前节点cur的左子树不为空，在左子树中查找当前节点cur的前驱节点。
     *  2.1.如果前驱节点pre的右子树为空，那么，前驱节点pre.right=cur，cur=cur.left
     *  2.2.如果前驱节点pre的右子树等于cur，那么，pre.right=null，输出cur，cur=cur.right
     * 3.重复步骤1,2，知道cur为空
     *
     * 以上是Morris Traversal的算法。
     * 本题再次基础上新增逻辑见recoverTree_1()
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 47.64% of Java online submissions for Recover Binary Search Tree.
     * Memory Usage: 47.3 MB, less than 33.58% of Java online submissions for Recover Binary Search Tree.
     *
     * @param root
     */
    public static void recoverTree_2(TreeNode root) {
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode first = null, second = null;
        TreeNode tmp;
        while (cur != null) {
            if (cur.left == null) {
                //查找错误的节点
                if (pre != null && pre.val > cur.val) {
                    if (first == null) first = pre;
                    second = cur;
                }
                pre = cur;
                cur = cur.right;
            } else {
                //查找前驱节点
                tmp = cur.left;
                while (tmp.right != null && tmp.right != cur) {
                    tmp = tmp.right;
                }
                //判断前驱节点是否为空
                if (tmp.right == null) {
                    tmp.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    //查找错误的节点
                    if (pre != null && pre.val > cur.val) {
                        if (first == null) first = pre;
                        second = cur;
                    }
                    pre = cur;
                    cur = cur.right;
                }

            }
        }
        if (first != null && second != null) {
            //重置错误的节点恢复BST，swap节点的值
            int t = first.val;
            first.val = second.val;
            second.val = t;
        }
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
     * Space Complexity:O(logN)
     *
     * 还有一种递归解法：（需要借助递归函数之外的外部变量）
     * https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 19.24% of Java online submissions for Recover Binary Search Tree.
     * Memory Usage: 48 MB, less than 7.10% of Java online submissions for Recover Binary Search Tree.
     *
     * @param root
     */
    public static void recoverTree_1(TreeNode root) {
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

    public static void main(String[] args) {
        //[1,3,null,null,2]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        recoverTree(root);
        System.out.println("----------------");
        //[1,3,null,null,2]
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(3);
        root2.left.right = new TreeNode(2);
        recoverTree(root2);
        System.out.println("----------------");
    }



}
