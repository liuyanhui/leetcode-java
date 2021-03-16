package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 129. Sum Root to Leaf Numbers
 * Medium
 * ----------------
 * You are given the root of a binary tree containing digits from 0 to 9 only.
 * Each root-to-leaf path in the tree represents a number.
 *
 * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
 * Return the total sum of all root-to-leaf numbers.
 *
 * A leaf node is a node with no children.
 *
 * Example 1:
 * Input: root = [1,2,3]
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 *
 * Example 2:
 * Input: root = [4,9,0,5,1]
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 1000].
 * 0 <= Node.val <= 9
 * The depth of the tree will not exceed 10.
 */
public class Sum_Root_to_Leaf_Numbers_129 {
    public int sumNumbers(TreeNode root) {
        return sumNumbers_dfs_1(root);
    }

    /**
     * bfs思路，巧妙的利用node.val保存当前节点的sum值。
     *
     * 参考文档：
     * https://leetcode.com/problems/sum-root-to-leaf-numbers/discuss/41383/Python-solutions-(dfs%2Bstack-bfs%2Bqueue-dfs-recursively).
     *
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 36.6 MB, less than 74.47% of Java online submissions for Sum Root to Leaf Numbers.
     *
     * @param root
     * @return
     */
    public int sumNumbers_bfs(TreeNode root) {
        if (root == null) return 0;
        int sum = 0;
        Queue queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() > 0) {
            TreeNode n = (TreeNode) queue.poll();
            if (n.left == null && n.right == null) {
                sum += n.val;
            }
            if (n.left != null) {
                n.left.val = n.left.val + n.val * 10;
                queue.offer(n.left);
            }
            if (n.right != null) {
                n.right.val = n.right.val + n.val * 10;
                queue.offer(n.right);
            }
        }
        return sum;
    }

    /**
     * sumNumbers_dfs_1的精简版
     * 参考文档：
     * https://leetcode.com/problems/sum-root-to-leaf-numbers/discuss/41363/Short-Java-solution.-Recursion.
     *
     * @param root
     * @return
     */
    public int sumNumbers_dfs_2(TreeNode root) {
        return sum(root, 0);
    }

    /**
     * 如果用node.val存储中间结果，可以省略参数s
     * @param n
     * @param s
     * @return
     */
    public int sum(TreeNode n, int s) {
        if (n == null) return 0;
        if (n.right == null && n.left == null) return s * 10 + n.val;
        return sum(n.left, s * 10 + n.val) + sum(n.right, s * 10 + n.val);
    }

    /**
     * dfs思路，比较简单。
     * sum = left_child_sum + right_child_sum 递归即可
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 36.3 MB, less than 92.55% of Java online submissions for Sum Root to Leaf Numbers.
     * @param root
     * @return
     */
    public int sumNumbers_dfs_1(TreeNode root) {
        return do_recursive(root, 0);
    }

    private int do_recursive(TreeNode node, int curSum) {
        if (node == null) return 0;
        int s = curSum * 10 + node.val;
        int sl = 0, sr = 0;
        if (node.left == null && node.right == null) return s;
        if (node.left != null) {
            sl = do_recursive(node.left, s);
        }
        if (node.right != null) {
            sr = do_recursive(node.right, s);
        }
        return sl + sr;
    }


}
