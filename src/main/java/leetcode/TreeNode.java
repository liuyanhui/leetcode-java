package leetcode;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode fromArray(int[] input) {
        if (input == null || input.length == 0) return null;
        return null;
    }


    public static boolean isSame(TreeNode node, int[] expected) {
        return false;
    }
}
