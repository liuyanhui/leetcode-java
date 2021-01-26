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
        return hasPathSum_1(root, targetSum);
    }

    /**
     * hasPathSum_1的精简版代码
     * 参考思路:
     * https://leetcode.com/problems/path-sum/discuss/36378/AcceptedMy-recursive-solution-in-Java
     * @param root
     * @param sum
     * @return
     */
    public static boolean hasPathSum_2(TreeNode root, int sum) {
        if (root == null) return false;

        if (root.left == null && root.right == null && sum - root.val == 0) return true;

        return hasPathSum_2(root.left, sum - root.val) || hasPathSum_2(root.right, sum - root.val);
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
