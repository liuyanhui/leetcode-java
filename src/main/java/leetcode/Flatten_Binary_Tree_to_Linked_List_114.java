package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 114. Flatten Binary Tree to Linked List
 * Medium
 * -----------------
 * Given the root of a binary tree, flatten the tree into a "linked list":
 * The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
 * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 *
 * Example 1:
 * Input: root = [1,2,5,3,4,null,6]
 * Output: [1,null,2,null,3,null,4,null,5,null,6]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Example 3:
 * Input: root = [0]
 * Output: [0]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -100 <= Node.val <= 100
 *
 * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
 */
public class Flatten_Binary_Tree_to_Linked_List_114 {
    public static void flatten(TreeNode root) {
        flatten_2(root);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking：
     * 1. DFS思路。先把node的右子树挂到node的左子树的最右子节点的右子节点，然后递归node的右子节点。
     * 1.1. 设 t 为 node.left 的最右节点。
     * 1.2. t.right = node.right
     * 1.3. node.right = node.left
     * 1.4. 递归步骤【1.1】输入为node.right
     * 2. BFS思路。
     * 2.1. 设 cur = root
     * 2.2. root.left 和 root.right 依次加入 queue
     * 2.2. 当 queue 不为空时循环
     * 2.2.1. 队列头出队记为 t
     * 2.2.2. t 为空时 continue
     * 2.2.3. cur.right = t
     * 2.2.4. t.left 和 t.right 依次加入 queue
     *
     * 验证通过：
     *
     * @param root
     */
    public static void flatten_5(TreeNode root) {
        if (root == null) return;
        if (root.left != null) {
            TreeNode t = root.left;
            //查找左子树的最右节点
            while (t.right != null) {
                t = t.right;
            }
            t.right = root.right;
            root.right = root.left;
            root.left = null;
        }

        flatten(root.right);
    }

    /**
     * round 2
     * Space Complexity:O(1)
     *
     * 验证通过：
     * 与flatten_3()相同
     *
     * @param root
     */
    public static void flatten_4(TreeNode root) {
        if (root == null) return;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                //右子树作为左子树的最右子孙
                TreeNode r = cur.left;
                while (r.right != null) {
                    r = r.right;
                }
                //原右子树改成原左子树的最子孙的右子树
                r.right = cur.right;
                //原左子树改成右子树
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    /**
     * round 2
     *
     * preorder
     * 1.左子树不为空，原右子树改成原左子树的最子孙的右子树，原左子树改成右子树。
     * 2.左子树为空，跳过左子树，处理右子树
     * 3.递归1,2
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 73.42% of Java.
     * Memory Usage: 42.1 MB, less than 62.83% of Java
     * @param root
     */
    public static void flatten_3(TreeNode root) {
        if (root == null) return;
        if (root.left != null) {
            //右子树作为左子树的最右子孙
            TreeNode r = root.left;
            while (r.right != null) {
                r = r.right;
            }
            //原右子树改成原左子树的最子孙的右子树
            r.right = root.right;
            //原左子树改成右子树
            root.right = root.left;
            root.left = null;
        }
        flatten(root.right);
    }

    /**
     * Time Complexity为O(n)，且Space Complexity为O(1) 的思路
     * 思路：
     * 1.采用postorder遍历的方式，并且顺序为right->left->root的方式，即先右后左的postorder遍历法
     * 2.使用用哑节点
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 38.4 MB, less than 59.23% of Java online submissions for Flatten Binary Tree to Linked List.
     *
     * @param root
     */
    public static void flatten_2(TreeNode root) {
        TreeNode head = new TreeNode();
        do_recursive(root, head);
    }

    private static void do_recursive(TreeNode node, TreeNode head) {
        if (node == null) return;
        if (node.right != null) {
            do_recursive(node.right, head);
        }
        if (node.left != null) {
            do_recursive(node.right, head);
        }
        node.right = head.right;
        node.left = null;
        head.right = node;
    }

    /**
     * 思路：
     * 1.先用preorder将tree转化成list
     * 2.遍历list生产只有右子树的tree
     *
     *  Time Complexity:O(2n)
     *  Space Complexity:O(n)
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 33.44% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 38.5 MB, less than 59.23% of Java online submissions for Flatten Binary Tree to Linked List.
     * @param root
     */
    public static void flatten_1(TreeNode root) {
        if (root == null) return;
        TreeNode node = root;
        List<TreeNode> list = do_recursive(root);
        for (int i = 1; i < list.size(); i++) {
            node.left = null;
            node.right = list.get(i);
            node = node.right;
        }
    }

    private static List<TreeNode> do_recursive(TreeNode node) {
        if (node == null) return null;
        List<TreeNode> ret = new ArrayList<>();
        ret.add(node);
        if (node.left != null) {
            ret.addAll(do_recursive(node.left));
        }
        if (node.right != null) {
            ret.addAll(do_recursive(node.right));
        }
        return ret;
    }

    public static void main(String[] args) {
        //[1,2,5,3,4,null,6]
        TreeNode t = new TreeNode(1);
        t.left = new TreeNode(2);
        t.right = new TreeNode(5);
        t.left.left = new TreeNode(3);
        t.left.right = new TreeNode(4);
        t.right.right = new TreeNode(6);
        flatten(t);

        System.out.println("222");
    }
}
