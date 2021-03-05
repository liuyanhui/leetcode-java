package leetcode;

/**
 * 106. Construct Binary Tree from Inorder and Postorder Traversal
 * Medium
 * -------------
 * Given two integer arrays inorder and postorder where inorder is the inorder traversal of a binary tree and postorder is the postorder traversal of the same tree, construct and return the binary tree.
 *
 * Example 1:
 * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
 * Output: [3,9,20,null,null,15,7]
 *
 * Example 2:
 * Input: inorder = [-1], postorder = [-1]
 * Output: []
 *
 * Constraints:
 * 1 <= inorder.length <= 3000
 * postorder.length == inorder.length
 * -3000 <= inorder[i], postorder[i] <= 3000
 * inorder and postorder consist of unique values.
 * Each value of postorder also appears in inorder.
 * inorder is guaranteed to be the inorder traversal of the tree.
 * postorder is guaranteed to be the postorder traversal of the tree.
 */
public class Construct_Binary_Tree_from_Inorder_and_Postorder_Traversal_106 {
    /**
     * 与105的思路一样。
     * 1.postorder的末尾元素是root；在inorder中root左边的是左子树，root右边的右子树
     * 2.递归上面的步骤即可。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 49.29% .
     * Memory Usage: 39.1 MB, less than 58.88% .
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return do_recursive(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode do_recursive(int[] postorder, int postBeg, int postEnd,
                                  int[] inorder, int inBeg, int inEnd) {
        if (postBeg > postEnd) return null;
        if (postBeg == postEnd) return new TreeNode(postorder[postEnd]);
        TreeNode root = new TreeNode(postorder[postEnd]);

        //定位root节点在inorder中的位置
        int offset = 0;
        for (int i = inBeg; i <= inEnd; i++) {
            if (postorder[postEnd] == inorder[i]) {
                offset = i - inBeg;
                break;
            }
        }

        root.left = do_recursive(postorder, postBeg, postBeg + offset - 1,
                inorder, inBeg, inBeg + offset - 1);
        root.right = do_recursive(postorder, postBeg + offset, postEnd - 1,
                inorder, inBeg + offset + 1, inEnd);
        return root;
    }
}
