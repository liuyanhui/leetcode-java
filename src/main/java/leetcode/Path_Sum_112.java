package leetcode;

/**
 * https://leetcode.com/problems/path-sum/
 * 112. Path Sum
 * Easy
 * --------------------
 * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.
 *
 * A leaf is a node with no children.
 *
 * Example 1:
 * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 * Output: true
 *
 * Example 2:
 * Input: root = [1,2,3], targetSum = 5
 * Output: false
 *
 * Example 3:
 * Input: root = [1,2], targetSum = 0
 * Output: false
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 5000].
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 */
public class Path_Sum_112 {
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSum_3(root, targetSum);
    }

    /**
     *
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1. DFS思路。preorder、inorder和postorder都可以。
     * 2. BFS思路。使用队列。
     * 3. 遇到leaf节点时，计算Sum，并判断是否等于targetSum。
     *
     * DFS思路验证通过：
     *
     */
    public static boolean hasPathSum_3(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) {
            if (root.val == targetSum) return true;
            else return false;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * round 2
     * dfs+preorder
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 53.49% of Java online submissions for Path Sum.
     * Memory Usage: 43.3 MB, less than 58.17% of Java online submissions for Path Sum.
     *
     * @param root
     * @param sum
     * @return
     */
    public static boolean hasPathSum_2(TreeNode root, int sum) {
        if (root == null) return false;

        //TODO 这里可以优化时间复杂度。因为这一步有部分情况是可以直接返回false的。如下面这段代码所示。
//        sum -= root.val;
//        if (root.left == null && root.right == null) {
//            return sum == 0;
//        }
        if (root.left == null && root.right == null && sum == root.val) return true;

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    /**
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Path Sum.
     * Memory Usage: 38.7 MB, less than 81.13% of Java online submissions for Path Sum.
     * @param root
     * @param targetSum
     * @return
     */
    public static boolean hasPathSum_1(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }

    public static boolean dfs(TreeNode node, int targetSum) {
        if (node == null) return false;
        int tmpSum = targetSum - node.val;
        if (node.left == null && node.right == null && tmpSum == 0) {
            return true;
        }
        boolean leftChild = dfs(node.left, tmpSum);
        if (leftChild) {
            return true;
        } else {
            return dfs(node.right, tmpSum);
        }
    }

    public static void main(String[] args) {
        do_func(TreeNode.fromArray(
                new int[]{5, 4, 8, 11, Integer.MIN_VALUE, 13, 4, 7, 2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, 1}),
                22,
                true);
        do_func(TreeNode.fromArray(new int[]{1, 2, 3}), 5, false);
        do_func(TreeNode.fromArray(new int[]{1, 2}), 0, false);
    }

    private static void do_func(TreeNode root, int targetSum, boolean expected) {
        boolean ret = hasPathSum(root, targetSum);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}
