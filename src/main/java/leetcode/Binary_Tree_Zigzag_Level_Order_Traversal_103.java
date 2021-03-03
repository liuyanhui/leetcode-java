package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 103. Binary Tree Zigzag Level Order Traversal
 * Medium
 * ----------------
 * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[20,9],[15,7]]
 *
 * Example 2:
 * Input: root = [1]
 * Output: [[1]]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2000].
 * -100 <= Node.val <= 100
 */
public class Binary_Tree_Zigzag_Level_Order_Traversal_103 {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        return zigzagLevelOrder_2(root);
    }

    /**
     * 深度搜索DFS思路。
     * 参考资料：https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/discuss/33815/My-accepted-JAVA-solution
     *
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 38.9 MB, less than 77.28% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder_2(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        do_recursive(ret, root, 1);
        return ret;
    }

    private static void do_recursive(List<List<Integer>> ret, TreeNode node, int level) {
        if (node == null) return;
        if (ret.size() < level) {
            ret.add(new ArrayList<>());
        }

        if (level % 2 == 1) {
            ret.get(level - 1).add(node.val);
        } else {
            ret.get(level - 1).add(0, node.val);
        }
        do_recursive(ret, node.left, level + 1);
        do_recursive(ret, node.right, level + 1);
    }

    /**
     * 类似广度搜索BFS的思路。
     * 1.使用两个数组left和right分表记录向左遍历的node和向右遍历的node。
     * 2.清空right，然后遍历left。遍历left时，把每个node的子节点存储到right中，左子节点先入。
     * 3.清空left，然后遍历right。遍历right时，把每个node的子节点存储到left中，右子节点先入。
     * 4.遍历时需要注意都从右向左遍历。
     * 5.循环2，3，4，知道left和right同时为空
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 38.9 MB, less than 86.75% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder_1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) return ret;
        List<TreeNode> left = new ArrayList<TreeNode>() {{
            add(root);
        }};
        List<TreeNode> right = new ArrayList<>();
        while (left.size() > 0 || right.size() > 0) {
            if (left.size() > 0) {
                List<Integer> tl = new ArrayList<>();
                for (int i = left.size() - 1; i >= 0; i--) {
                    TreeNode l = left.get(i);
                    tl.add(l.val);
                    if (l.left != null) right.add(l.left);
                    if (l.right != null) right.add(l.right);
                }
                ret.add(tl);
            }
            left.clear();

            if (right.size() > 0) {
                List<Integer> tr = new ArrayList<>();
                for (int i = right.size() - 1; i >= 0; i--) {
                    TreeNode r = right.get(i);
                    tr.add(r.val);
                    if (r.right != null) left.add(r.right);
                    if (r.left != null) left.add(r.left);
                }
                ret.add(tr);
            }
            right.clear();
        }

        return ret;
    }
}
