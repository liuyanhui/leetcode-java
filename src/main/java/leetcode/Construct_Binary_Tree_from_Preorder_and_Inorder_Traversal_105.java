package leetcode;

/**
 * 105. Construct Binary Tree from Preorder and Inorder Traversal
 * Medium
 * -------------------
 * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.
 *
 * Example 1:
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 *
 * Example 2:
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 *
 * Constraints:
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder and inorder consist of unique values.
 * Each value of inorder also appears in preorder.
 * preorder is guaranteed to be the preorder traversal of the tree.
 * inorder is guaranteed to be the inorder traversal of the tree.
 */
public class Construct_Binary_Tree_from_Preorder_and_Inorder_Traversal_105 {
    /**
     * 递归思路：
     * 1.preorder的第一个元素是root节点，inorder中root节点是第count个元素。
     * 2.在inorder中,root节点左边的是左子树(即inorder[0:count-1])，root节点右边的右子树(即inorder[count+1:])；
     * 3.在preorder中，左子树为preorder[1:count],右子树为preorder[count+1:]
     * 4.递归上面的步骤即可。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 56.03% .
     * Memory Usage: 38.9 MB, less than 69.15% .
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return do_recursive(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    private TreeNode do_recursive(int[] pre, int begPre, int endPre,
                                  int[] in, int begIn, int endIn) {
        if (begPre > endPre) return null;
        if (begPre == endPre) return new TreeNode(pre[begPre]);
        TreeNode root = new TreeNode(pre[begPre]);
        //获取inorder中的root位置
        int count = 0;
        for (int i = begIn; i <= endIn; i++) {
            if (in[i] == pre[begPre]) {
                count = i - begIn;
                break;
            }
        }

        root.left = do_recursive(pre, begPre + 1, begPre + count, in, begIn, begIn + count - 1);
        root.right = do_recursive(pre, begPre + count + 1, endPre, in, begIn + count + 1, endIn);
        return root;
    }
}