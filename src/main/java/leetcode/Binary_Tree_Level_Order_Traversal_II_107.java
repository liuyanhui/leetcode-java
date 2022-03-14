package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 107. Binary Tree Level Order Traversal II
 * Medium
 * --------------------------------
 * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., from left to right, level by level from leaf to root).
 *
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[15,7],[9,20],[3]]
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
 * -1000 <= Node.val <= 1000
 */
public class Binary_Tree_Level_Order_Traversal_II_107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        return levelOrderBottom_1(root);
    }

    /**
     * 递归法
     * 1.先从上到下，依次加入对应level的list
     * 2.再反转List<List>
     * 3.返回反转后的List
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of .
     * Memory Usage: 44 MB, less than 9.40% of Java.
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom_1(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        helper(root, list, 0);
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            ret.add(list.get(i));
        }
        return ret;
    }

    private void helper(TreeNode node, List<List<Integer>> ret, int level) {
        if (node == null) return;
        if (level >= ret.size()) ret.add(new ArrayList<>());
        ret.get(level).add(node.val);
        helper(node.left, ret, level + 1);
        helper(node.right, ret, level + 1);
    }

    /**
     * 1.使用Queue保存待遍历的节点，每个节点依次加入对应level的list
     * 
     * 验证通过：
     * Runtime: 2 ms, faster than 53.51% of Java.
     * Memory Usage: 43.8 MB, less than 26.51% of Java.
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom_2(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode t = null;
        while (!queue.isEmpty()) {
            int len = queue.size();
            List<Integer> curLevelList = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                t = queue.poll();
                if (t == null) continue;
                curLevelList.add(t.val);
                queue.offer(t.left);
                queue.offer(t.right);
            }
            if (!curLevelList.isEmpty())
                //不许需要额外的反转操作
                list.add(0, curLevelList);
        }
        return list;
    }
}
