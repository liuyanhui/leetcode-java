package leetcode;

/**
 * 124. Binary Tree Maximum Path Sum
 * Hard
 * --------------------------
 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
 *
 * The path sum of a path is the sum of the node's values in the path.
 *
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * Example 1:
 * Input: root = [1,2,3]
 * Output: 6
 * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
 *
 * Example 2:
 * Input: root = [-10,9,20,null,null,15,7]
 * Output: 42
 * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 3 * 10^4].
 * -1000 <= Node.val <= 1000
 */
public class Binary_Tree_Maximum_Path_Sum_124 {
    static int maxSum = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode root) {
        return maxPathSum_r3_1(root);
    }

    /**
     *
     * Thinking：
     * 1. 貌似是图的问题，但其实不是，因为图是无序的，树是有序的（只能从上到下遍历）。
     * 2. 某个node的计算结果分为4种情况：
     * 2.1. 仅node ，需返回给父节点
     * 2.2. node+node.left ，需返回给父节点
     * 2.3. node+node.right ，需返回给父节点
     * 2.4. node.left+node+node.right ，无需返回给父节点。直接比较/更新全局最优解。
     * 2.5. 不包含node，无法返回给父节点。maxPathSum仅在子孙节点中。
     * 3. 某个node是否被其父节点采用分为2种情况：
     * 3.1. 被丢弃，path<0
     * 3.2. 被采用，path>0
     * 4. 采用递归思路实现
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 44.04 MB Beats 70.25%
     *
     * @param root
     * @return
     */
    public static int maxPathSum_r3_1(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        helper(root);
        return maxSum;
    }

    private static int helper(TreeNode node) {
        if (node == null) return 0;
        //先单独计算当前节点
        maxSum = Math.max(maxSum, node.val);
        //再计算子节点
        int left = helper(node.left);
        int right = helper(node.right);
        //比较node+左节点+右节点
        maxSum = Math.max(maxSum, node.val + left + right);
        //node+node.left 或 node+node.right
        int oneSide = Math.max(left, right) + node.val;
        maxSum = Math.max(maxSum, oneSide);
        return oneSide > 0 ? oneSide : 0;//小于等于0时，返回0。简化父节点的运算逻辑
    }

    /**
     * maxPathSum_1()的代码优化版
     *
     * 参考思路：
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java
     *
     * 更好理解，充分利用了n+0=n的思想。
     * 递归时子节点作为root的局部最优解已经更新全局最优解了，所以父节点计算时，不需要重复计算。
     *
     * @param root
     * @return
     */
    public static int maxPathSum_2(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxSum;
    }

    private static int maxPathDown(TreeNode node) {
        if (node == null) return 0;
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        //递归时子节点作为root的局部最优解已经更新全局最优解了，所以父节点计算时，不需要重复计算。
        maxSum = Math.max(maxSum, left + right + node.val);
        return Math.max(left, right) + node.val;
    }

    /**
     * 思考过程如下：
     * 一般来说树的问题，一般是dfs或者bfs。dfs无非是preorder、inorder、postorder三种。
     * 本题应该用dfs，因为组成一条路径，一般是多个节点。超过1个节点时，肯定包含某个节点的父节点。
     * 类似于图的最大路径和，但是访问节点的顺序受到树的特点的影响。
     *
     * 子树的路径和分为以下几种情况：根节点，左子节点，右子节点，根+左子结点，根+右子节点，左子结点+根+右子节点，取最大值就是所求。
     * 另外，上述6种情况中有3种需要参加到父节点的计算中，如：max(P(根+左子结点)，P(根+右子节点)，P(根节点))可以加入到父节点的最大路径和的计算中。
     *
     * 采用postorder，算法如下：
     * 0.设P(node)为node参与父节点最大路径和的值
     * 1.计算当前节点的最大路径和
     *     curMax = max(node.val,P(node.left),P(node.right),node.val+P(node.left),node.val+P(node.right),P(node.left)+node.val+P(node.right))
     *     ret = max(ret,curMax)
     * 2.计算当前节点参与父节点的最大路径和
     *     P(node)=max(node.val,node.val+P(node.left),node.val+P(node.right))
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 83.12% of Java online submissions for Binary Tree Maximum Path Sum.
     * Memory Usage: 48.8 MB, less than 8.32% of Java online submissions for Binary Tree Maximum Path Sum.
     *
     * @param root
     * @return
     */
    public static int maxPathSum_1(TreeNode root) {
        postorder(root);
        return maxSum;
    }

    private static int postorder(TreeNode node) {
        if (node == null) return Integer.MIN_VALUE;
        int leftChildMax = postorder(node.left);
        int rightChildMax = postorder(node.right);

        int lrMax = Math.max(leftChildMax, rightChildMax);

        //计算当前节点参与父节点最大路径和的值，必须包含当前的根节点
        //如果lrMax是负数，不用参加后续的计算。任意数+0，值不变。
        //因为是取和的最大值，所以加法计算时，要把负数替换成0
        int ret = node.val + Math.max(0, lrMax);
        //计算当前节点为止的最大路径和
        //比较计算时，不能把负数替换成0
        //FIXME 1.下面这一行代码是多余的，因为子节点计算时已经更新过全局最大值maxSum了。父节点计算时，两个子节点的局部最大值无需再参与比较。
        //FIXME 2.所以虽然全局最大值有6种情况，但是每个节点需要计算的只有4中。可以优化。详见maxPathSum_1()
        int t = Math.max(node.val, lrMax);
        //因为是取和的最大值，所以加法计算时，要把负数替换成0
        t = Math.max(t, node.val + Math.max(0, leftChildMax) + Math.max(0, rightChildMax));
        t = Math.max(t, ret);
        maxSum = Math.max(maxSum, t);
        return ret;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(-1);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(3);
        int max = maxPathSum(root);
        System.out.println(max);
        System.out.println(max == 6);
    }
}
