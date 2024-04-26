package leetcode;

/**
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 * 108. Convert Sorted Array to Binary Search Tree
 * Easy
 * ------------------
 * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
 * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
 *
 * Example 1:
 * Input: nums = [-10,-3,0,5,9]
 * Output: [0,-3,9,-10,null,5]
 * Explanation: [0,-10,5,null,-3,null,9] is also accepted:
 *
 * Example 2:
 * Input: nums = [1,3]
 * Output: [3,1]
 * Explanation: [1,null,3] and [3,1] are both height-balanced BSTs.
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums is sorted in a strictly increasing order.
 */
public class Convert_Sorted_Array_to_Binary_Search_Tree_108 {

    public static TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST_2(nums);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * 递归法。
     * 迭代法会异常复杂，因为需要时刻考虑height-balanced问题。
     *
     */

    /**
     * round 2
     * BST的inorder遍历就是排序后的数组。
     * 递归法
     * 1.数组的中位数为根节点。
     * 2.中位数左边的是左子树；中位数右边的是右子树。
     * 3.递归1,2
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java
     * Memory Usage: 43.1 MB, less than 57.67% of Java
     * @param nums
     * @return
     */
    public static TreeNode sortedArrayToBST_2(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private static TreeNode helper(int[] nums, int beg, int end) {
        if (beg > end) return null;
        int r = (beg + end) / 2;
        TreeNode node = new TreeNode(nums[r]);
        node.left = helper(nums, beg, r - 1);
        node.right = helper(nums, r + 1, end);
        return node;
    }

    /**
     * 递归法
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * Memory Usage: 38.5 MB, less than 88.23% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * @param nums
     * @return
     */
    public static TreeNode sortedArrayToBST_1(int[] nums) {
        return convertRecursive(nums, 0, nums.length - 1);
    }

    private static TreeNode convertRecursive(int[] nums, int beg, int end) {
        if (beg > end) return null;
        if (beg == end) {
            return new TreeNode(nums[beg]);
        }

        int mid = (beg + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        TreeNode left = convertRecursive(nums, beg, mid - 1);
        TreeNode right = convertRecursive(nums, mid + 1, end);
        root.left = left;
        root.right = right;
        return root;
    }

    public static void main(String[] args) {
        do_func(new int[]{-10, -3, 0, 5, 9}, new int[]{0, -3, 9, -10, Integer.MIN_VALUE, 5});
    }

    private static void do_func(int[] input, int[] expected) {
        TreeNode ret = sortedArrayToBST(input);
        System.out.println(ret);
        System.out.println(TreeNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
