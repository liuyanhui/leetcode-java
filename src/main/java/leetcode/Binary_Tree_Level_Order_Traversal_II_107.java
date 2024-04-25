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
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        return levelOrderBottom_3(root);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * DFS方案
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom_4(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        helper_4(root, 1, ret);
        return ret;
    }

    private static void helper_4(TreeNode node, int level, List<List<Integer>> ret) {
        if (node == null) return;
        if (level > ret.size()) {
            ret.add(0, new ArrayList<>());
        }
        ret.get(ret.size() - level).add(node.val);//review 在列表第0个元素执行插入操作，无需反转
        helper_4(node.left, level + 1, ret);
        helper_4(node.right, level + 1, ret);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * BFS方案
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrderBottom_3(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() > 0) {
            int size = queue.size();
            List<Integer> curList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode t = queue.poll();
                if (t == null) continue;
                curList.add(t.val);
                queue.offer(t.left);
                queue.offer(t.right);
            }
            if (curList.size() > 0)
                ret.add(0, curList);//review 在列表第0个元素执行插入操作，无需反转
        }
        return ret;
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
    public static List<List<Integer>> levelOrderBottom_1(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        helper(root, list, 0);
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            ret.add(list.get(i));
        }
        return ret;
    }

    private static void helper(TreeNode node, List<List<Integer>> ret, int level) {
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
    public static List<List<Integer>> levelOrderBottom_2(TreeNode root) {
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
