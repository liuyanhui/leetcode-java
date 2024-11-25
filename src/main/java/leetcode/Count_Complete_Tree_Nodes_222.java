package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 222. Count Complete Tree Nodes
 * Medium
 * -------------------------------
 * Given the root of a complete binary tree, return the number of the nodes in the tree.
 * <p>
 * According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between 1 and 2^h nodes inclusive at the last level h.
 * <p>
 * Design an algorithm that runs in less than O(n) time complexity.
 * <p>
 * Example 1:
 * Input: root = [1,2,3,4,5,6]
 * Output: 6
 * <p>
 * Example 2:
 * Input: root = []
 * Output: 0
 * <p>
 * Example 3:
 * Input: root = [1]
 * Output: 1
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [0, 5 * 10^4].
 * 0 <= Node.val <= 5 * 10^4
 * The tree is guaranteed to be complete.
 */
public class Count_Complete_Tree_Nodes_222 {
    public int countNodes(TreeNode root) {
        return 0;
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * review 巧妙的思路和实现
     * <p>
     * 验证通过：
     *
     * @param root
     * @return
     */
    public int countNodes_r3_1(TreeNode root) {
        if (root == null) return 0;
        TreeNode left = root, right = root;
        int height = 0;
        while (left != null && right != null) {
            left = left.left;
            right = right.right;
            height++;
        }
        if (left == null && right == null) return (1 << height) - 1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * 更巧妙的办法
     * <p>
     * 参考资料：
     * https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Count Complete Tree Nodes.
     * Memory Usage: 49.5 MB, less than 78.76% of Java online submissions for Count Complete Tree Nodes.
     *
     * @param root
     * @return
     */
    public int countNodes_2(TreeNode root) {
        TreeNode left = root, right = root;
        int height = 0;
        while (left != null && right != null) {
            left = left.left;
            right = right.right;
            height++;
        }
        return (left == right) ? (1 << height) - 1 : 1 + countNodes(root.left) + countNodes(root.right);
//        return (left == right) ? (int) Math.pow(2, height) - 1 : 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * 思考：
     * 1.正常的dfs或bfs遍历都是O(N)复杂度
     * 2.Morris Tranverse好像可以在平均小于O(N)的情况下完成目标。最坏情况下也需要O(N)
     * 3.如果能得到最下层节点的数量和最大深度，也可以计算出结果。
     * 4.树的遍历dfs有三种：pre-order,post-order,in-order。本题是否可以采用post-order？
     * 5.采用bfs方法，最后一层的节点无需遍历，只需要计算其数量即可。
     * <p>
     * bfs算法：
     * 1.使用maxRank记录最大的深度，深度从1开始计
     * 2.使用queue保存待遍历的节点
     * 2.当某层的第一个节点为叶子节点时，可以计算出节点数，并结束计算。公式为2^(maxRank-1)-1+queue.size()
     * The Time Complexity is less than O(N). It's about O(N/2)
     * <p>
     * 验证通过：
     * Runtime: 10 ms, faster than 5.84% of Java online submissions for Count Complete Tree Nodes.
     * Memory Usage: 51.3 MB, less than 5.12% of Java online submissions for Count Complete Tree Nodes.
     *
     * @param root
     * @return
     */
    public int countNodes_1(TreeNode root) {
        if (root == null) return 0;
        int rank = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() > 0) {
            rank++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == 0 && node.left == null && node.right == null)
                    return (int) Math.pow(2, rank - 1) - 1 + size;
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
            }
        }
        return 0;
    }
}
