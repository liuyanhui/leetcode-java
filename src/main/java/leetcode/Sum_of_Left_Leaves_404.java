package leetcode;

import java.util.Stack;

/**
 * 404. Sum of Left Leaves
 * Easy
 * -------------------------
 * Given the root of a binary tree, return the sum of all left leaves.
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 24
 * Explanation: There are two left leaves in the binary tree, with values 9 and 15 respectively.
 *
 * Example 2:
 * Input: root = [1]
 * Output: 0
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 1000].
 * -1000 <= Node.val <= 1000
 */
public class Sum_of_Left_Leaves_404 {
    public static int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeaves_1(root);
    }

    /**
     * sumOfLeftLeaves_2的改进版本
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum of Left Leaves.
     * Memory Usage: 36.8 MB, less than 57.69% of Java online submissions for Sum of Left Leaves.
     * @param root
     * @return
     */
    public static int sumOfLeftLeaves_3(TreeNode root) {
        int ret = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                if (node.left.left == null && node.left.right == null) {
                    ret += node.left.val;
                }
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return ret;
    }

    /**
     * iterative思路，非recursive
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 5.42% of Java online submissions for Sum of Left Leaves.
     * Memory Usage: 38.6 MB, less than 32.55% of Java online submissions for Sum of Left Leaves.
     *
     * @param root
     * @return
     */
    public static int sumOfLeftLeaves_2(TreeNode root) {
        int ret = 0;
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> signal = new Stack<>();
        stack.push(root);
        signal.push(0);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            int s = signal.pop();
            if (node.left == null && node.right == null && s == 1) {
                ret += node.val;
            } else {
                if (node.left != null) {
                    stack.push(node.left);
                    signal.push(1);
                }
                if (node.right != null) {
                    stack.push(node.right);
                    signal.push(-1);
                }
            }
        }
        return ret;
    }

    /**
     * DFS思路，pre-order
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum of Left Leaves.
     * Memory Usage: 36.7 MB, less than 79.37% of Java online submissions for Sum of Left Leaves.
     *
     * @param root
     * @return
     */
    public static int sumOfLeftLeaves_1(TreeNode root) {
        if (root == null) return 0;
        return findLeftLeaf(root.left, true) + findLeftLeaf(root.right, false);
    }

    private static int findLeftLeaf(TreeNode node, boolean isLeft) {
        if (node == null) return 0;
        if (node.left == null && node.right == null && isLeft) {
            return node.val;
        }
        return findLeftLeaf(node.left, true) + findLeftLeaf(node.right, false);
    }


}
