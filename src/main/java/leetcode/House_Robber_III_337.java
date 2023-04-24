package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 337. House Robber III
 * Medium
 * -----------------------
 * The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root.
 *
 * Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses were broken into on the same night.
 *
 * Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police.
 *
 * Example 1:
 * Input: root = [3,2,3,null,3,null,1]
 * Output: 7
 * Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
 *
 * Example 2:
 * Input: root = [3,4,5,1,3,null,1]
 * Output: 9
 * Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 10^4].
 * 0 <= Node.val <= 10^4
 */
public class House_Robber_III_337 {
    private static Map<TreeNode, Integer> cached = new HashMap<>();

    /**
     * round 2
     *
     * Thinking：
     * 1.本题具有迷惑性，虽然以树的形式出现，但是未必采用树的解法。也有可能是图的解法。并一定从root开始计算，root只是一个节点而已。
     * 2.如果父节点被选中，子节点就无法被选中，反之亦然。所以可以用DFS+后序遍历法。每个子树有两种状态，root选中和root未选中。
     * 每个节点都维护两个值：选中和未选中。
     * 公式为：
     * F(node,选中)=node.val+F(node.left,未选中)+F(node.right,未选中)
     * F(node,未选中)=max(F(node.left,选中),F(node.left,未选中))+max(F(node.right,选中),F(node.right,未选中))
     * 最终结果为：
     * res=max(F(root,选中),F(root,未选中))
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 42.2 MB Beats 72.76%
     *
     * @param root
     * @return
     */
    public int rob_2(TreeNode root) {
        int[] t = dfs(root);
        return Math.max(t[0], t[1]);
    }

    private int[] dfs(TreeNode node) {
        // res[0]未选中；res[1]选中
        int[] res = new int[2];
        if (node == null) return res;
        int[] l_res = dfs(node.left);
        int[] r_res = dfs(node.right);
        //未选中
        res[0] = Math.max(l_res[0], l_res[1]) + Math.max(r_res[0], r_res[1]);
        //选中
        res[1] = node.val + l_res[0] + r_res[0];
        return res;
    }

    /**
     * 递归法。
     * 关键在于总结出公式。将中间结果缓存起来，防止重复计算，导致Time Limit Exceeded。
     *
     * 公式如下：
     * f(r)=max((f(r.left)+f(r.right)),root.val+f(root.left.chlidren)+f(root.right.chlidren))
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 43.80% of Java online submissions for House Robber III.
     * Memory Usage: 39 MB, less than 27.02% of Java online submissions for House Robber III.
     *
     * @param root
     * @return
     */
    public static int rob(TreeNode root) {
        if (root == null) return 0;
        //leaf node
        if (root.left == null && root.right == null) return root.val;
        if (cached.containsKey(root)) return cached.get(root);

        int ll = 0, lr = 0, rl = 0, rr = 0;
        if (root.left != null) {
            ll = rob(root.left.left);
            lr = rob(root.left.right);
        }
        if (root.right != null) {
            rl = rob(root.right.left);
            rr = rob(root.right.right);
        }
        int ret = Math.max(rob(root.left) + rob(root.right), root.val + ll + lr + rl + rr);
        cached.put(root, ret);
        return ret;
    }

}
