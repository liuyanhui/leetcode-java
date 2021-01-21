package leetcode;

/**
 * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
 * 108. Convert Sorted Array to Binary Search Tree
 * Easy
 * ------------------
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 *
 * Example:
 * Given the sorted array: [-10,-3,0,5,9],
 * One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 */
public class Convert_Sorted_Array_to_Binary_Search_Tree_108 {
    /**
     * 递归法
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * Memory Usage: 38.5 MB, less than 88.23% of Java online submissions for Convert Sorted Array to Binary Search Tree.
     * @param nums
     * @return
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
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
