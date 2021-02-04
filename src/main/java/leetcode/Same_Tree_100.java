package leetcode;

/**
 * https://leetcode.com/problems/same-tree/
 * 100. Same Tree
 * Easy
 * -------------------
 * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
 * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
 *
 * Example 1:
 * Input: p = [1,2,3], q = [1,2,3]
 * Output: true
 *
 * Example 2:
 * Input: p = [1,2], q = [1,null,2]
 * Output: false
 *
 * Example 3:
 * Input: p = [1,2,1], q = [1,1,2]
 * Output: false
 *
 * Constraints:
 * The number of nodes in both trees is in the range [0, 100].
 * -104 <= Node.val <= 104
 */
public class Same_Tree_100 {
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        return isSameTree_1(p, q);
    }

    /**
     * 递归法
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Same Tree.
     * Memory Usage: 38.3 MB, less than 8.04% of Java online submissions for Same Tree.
     * @param p
     * @param q
     * @return
     */
    public static boolean isSameTree_1(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if ((p == null && q != null) || (p != null && q == null)) return false;
        if (p.val != q.val) return false;
        return isSameTree_1(p.left, q.left) && isSameTree_1(p.right, q.right);
    }

//    public static void main(String[] args) {
//        do_func(TreeNode.fromArray(new int[]{1, 2, 3}), TreeNode.fromArray(new int[]{1, 2, 3}), false);
//        do_func(TreeNode.fromArray(new int[]{1, 2}), TreeNode.fromArray(new int[]{1, 2, 3}), false);
//    }
//
//    private static void do_func(TreeNode p, TreeNode q, boolean expected) {
//        boolean ret = isSameTree(p, q);
//        System.out.println(ret);
//        System.out.println(ret == expected);
//        System.out.println("--------------");
//    }
}
