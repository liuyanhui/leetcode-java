package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 297. Serialize and Deserialize Binary Tree
 * Hard
 * ------------------
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 *
 * Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 *
 * Example 1:
 * Input: root = [1,2,3,null,null,4,5]
 * Output: [1,2,3,null,null,4,5]
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 10^4].
 * -1000 <= Node.val <= 1000
 */
public class Serialize_and_Deserialize_Binary_Tree_297 {
    /**
     * 思考：
     * 1.先确定序列化后的数据结构，即用什么方式存储数据。如：leecode的默认方式。
     * 2.序列化时，是树的遍历问题。一般分为dfs和bfs，dfs有3种：pre-order、in-order、post-order。
     * 2.1.leecode默认方式，属于bfs。
     *
     * 验证通过：
     * Runtime: 68 ms, faster than 20.62% of Java online submissions for Serialize and Deserialize Binary Tree.
     * Memory Usage: 54.7 MB, less than 26.15% of Java online submissions for Serialize and Deserialize Binary Tree.
     */
    static class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            List<String> list = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int size = 0;
            TreeNode node = null;
            while (queue.size() > 0) {
                size = queue.size();
                for (int i = 0; i < size; i++) {
                    node = queue.poll();
                    list.add(node == null ? null : String.valueOf(node.val));
                    if (node != null) {
                        queue.offer(node.left);
                        queue.offer(node.right);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) return null;
            String[] arr = data.split(",");
            int index = 0;
            TreeNode root = new TreeNode(Integer.valueOf(arr[index]));
            index++;
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (queue.size() > 0) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    if (node == null) continue;
                    node.left = arr[index].equals("null") ? null : new TreeNode(Integer.valueOf(arr[index]));
                    index++;
                    queue.offer(node.left);
                    node.right = arr[index].equals("null") ? null : new TreeNode(Integer.valueOf(arr[index]));
                    index++;
                    queue.offer(node.right);
                }
            }
            return root;
        }

    }

    public static void main(String[] args) {
        Codec ser = new Codec();
        String inputStr = "1,2,3,null,null,4,5,null,null,null,null";
        TreeNode inputTree = new TreeNode(1);
        inputTree.left = new TreeNode(2);
        inputTree.right = new TreeNode(3);
        inputTree.right.left = new TreeNode(4);
        inputTree.right.right = new TreeNode(5);

        String outputStr = ser.serialize(inputTree);
        System.out.println("----------deserialize----------");
        System.out.println(outputStr);
        System.out.println(inputStr.equals(outputStr));
        System.out.println("----------serialize----------");
        TreeNode node = ser.deserialize(inputStr);

    }

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
}
