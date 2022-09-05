package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 226. Invert Binary Tree
 * Easy
 * ---------------------
 * Given the root of a binary tree, invert the tree, and return its root.
 *
 * Example 1:
 * Input: root = [4,2,7,1,3,6,9]
 * Output: [4,7,2,9,6,3,1]
 *
 * Example 2:
 * Input: root = [2,1,3]
 * Output: [2,3,1]
 *
 * Example 3:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class Invert_Binary_Tree_226 {
    public static TreeNode invertTree(TreeNode root) {
        return invertTree_bfs_4(root);
    }

    /**
     * round 2
     * invertTree_bfs_2()更简洁
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 10.45% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 41.7 MB, less than 41.16% of Java online submissions for Invert Binary Tree.
     *
     * @param root
     * @return
     */
    public static TreeNode invertTree_bfs_4(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() > 0) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                size--;
                if (node == null) continue;
                TreeNode t = node.left;
                node.left = node.right;
                node.right = t;
                queue.offer(node.left);
                queue.offer(node.right);

            }
        }
        return root;
    }

    /**
     * 思考：
     * 1.递归法。先互换左右子节点，然后对左右子节点分别递归。
     * 2.要注意子节点为空的情况。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 41.8 MB, less than 41.16% of Java online submissions for Invert Binary Tree.
     *
     * @param root
     * @return
     */
    public static TreeNode invertTree_dfs_3(TreeNode root) {
        if (root == null) return null;
        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 金矿：很多场景下只用一个queue就可以实现bfs方案
     *
     * 参考思路：Approach 2
     * https://leetcode.com/problems/invert-binary-tree/solution/
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 36.5 MB, less than 42.41% of Java online submissions for Invert Binary Tree.
     * @param root
     * @return
     */
    public static TreeNode invertTree_bfs_2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() > 0) {
            TreeNode t = queue.poll();
            if (t != null) {
                //子节点入队
                queue.offer(t.right);
                queue.offer(t.left);
                //互换左右子节点
                TreeNode tmp = t.left;
                t.left = t.right;
                t.right = tmp;
            }
        }
        return root;
    }

    /**
     * 绝大多数Binary Tree的问题都可以用dfs方式解决。
     *
     * 参考思路：Approach 1
     * https://leetcode.com/problems/invert-binary-tree/solution/
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 36.3 MB, less than 74.50% of Java online submissions for Invert Binary Tree.
     * @param root
     * @return
     */
    public static TreeNode invertTree_dfs(TreeNode root) {
        if (root == null) return null;

        invertTree_dfs(root.left);
        invertTree_dfs(root.right);

        TreeNode t = root.left;
        root.left = root.right;
        root.right = t;

        return root;
    }

    /**
     * bfs思路，代码太复杂，可以优化
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
     * Memory Usage: 36.4 MB, less than 42.00% of Java online submissions for Invert Binary Tree.
     * @param root
     * @return
     */
    public static TreeNode invertTree_bfs(TreeNode root) {
        if (root == null) return null;
        List<TreeNode> list1 = new ArrayList<>();
        List<TreeNode> list2 = new ArrayList<>();
        List<TreeNode> destList1 = new ArrayList<>();
        List<TreeNode> destList2 = new ArrayList<>();
        list1.add(root);
        TreeNode ret = new TreeNode(root.val);
        destList1.add(ret);
        while (list1 != null && list1.size() > 0) {
            list2 = new ArrayList<>();
            destList2 = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                TreeNode rawNode = list1.get(i);
                if (rawNode == null) continue;
                list2.add(rawNode.left);
                list2.add(rawNode.right);

                TreeNode invertNode = destList1.get(i);
                if (rawNode.left != null) {
                    invertNode.right = new TreeNode(rawNode.left.val);
                }
                if (rawNode.right != null) {
                    invertNode.left = new TreeNode(rawNode.right.val);
                }
                destList2.add(invertNode.right);
                destList2.add(invertNode.left);
            }

            list1 = list2;
            destList1 = destList2;
        }

        return ret;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(1);
        root.left.left = new TreeNode(2);
        root.left.left.left = new TreeNode(3);
        TreeNode ret = invertTree(root);
        System.out.println("-----------");
    }


}
