package leetcode;

import java.util.HashMap;
import java.util.Map;

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
    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        return buildTree_2(inorder, postorder);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * 类似Construct_Binary_Tree_from_Preorder_and_Inorder_Traversal_105
     *
     * 验证通过：
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public static TreeNode buildTree_3(int[] inorder, int[] postorder) {
        return helper_3(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private static TreeNode helper_3(int[] inorder, int inBeg, int inEnd, int[] postorder, int postBeg, int postEnd) {
        if (inBeg > inEnd || postBeg > postEnd) return null;
        TreeNode node = new TreeNode(postorder[postEnd]);
        //在inorder中找到node节点
        int cnt = 0;
        for (int i = inBeg; i <= inEnd; i++) {
            if (inorder[i] == node.val) break;
            cnt++;
        }
        // 递归生成左右子树
        // inorder中[inBeg,inBeg+cnt-1]为左子树，[inBeg+cnt+1,inEnd]为右子树
        // postorder中[postBeg,postBeg+cnt-1]为左子树,[postBeg+cnt,postEnd-1]为右子树
        node.left = helper_3(inorder, inBeg, inBeg + cnt - 1, postorder, postBeg, postBeg + cnt - 1);
        node.right = helper_3(inorder, inBeg + cnt + 1, inEnd, postorder, postBeg + cnt, postEnd - 1);
        return node;
    }

    /**
     * round 2 :
     *
     * postorder的倒序类似于preorder（区别是：先右子树后左子树）
     * 0.假设postorder.length=inorder.length=len
     * 1.postorder[len-1]是根节点root。根据postorder[len-1]=inorder[i]找到i
     * 2.根据i，inorder被分割成两部分，i边的是左子树[0:i-1]，i右边的有子树[i+1:~]，
     * 3.根据i，postorder也被分割成两部分。[0:i-1]是左子树，[i:len-2]是右子树
     * 4.递归 1,2,3
     * 5.需要注意上面的下标的用法。postorder的下标不能直接用，左右子树的区间是相对值，不是绝对值。inorder的下标可以直接用。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 83.28% of Java
     * Memory Usage: 45.2 MB, less than 12.06% of Java
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public static TreeNode buildTree_2(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return helper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
    }

    private static TreeNode helper(int[] inorder, int begi, int endi, int[] postorder, int begp, int endp, Map<Integer, Integer> map) {
        if (begi > endi) return null;
        TreeNode root = new TreeNode(postorder[endp]);
        if (begi == endi) return root;
        //find root index in the array "inorder"
        int idx = map.get(postorder[endp]);

        root.left = helper(inorder, begi, idx - 1, postorder, begp, begp + idx - begi - 1, map);
        root.right = helper(inorder, idx + 1, endi, postorder, begp + idx - begi, endp - 1, map);
        return root;
    }

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
    public static TreeNode buildTree_1(int[] inorder, int[] postorder) {
        return do_recursive(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode do_recursive(int[] postorder, int postBeg, int postEnd,
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
