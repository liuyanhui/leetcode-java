package leetcode;

import java.util.*;

/**
 * 297. Serialize and Deserialize Binary Tree
 * Hard
 * ------------------
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 * <p>
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 * <p>
 * Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 * <p>
 * Example 1:
 * Input: root = [1,2,3,null,null,4,5]
 * Output: [1,2,3,null,null,4,5]
 * <p>
 * Example 2:
 * Input: root = []
 * Output: []
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [0, 10^4].
 * -1000 <= Node.val <= 1000
 */
public class Serialize_and_Deserialize_Binary_Tree_297 {

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * REVIEW : This is the recursive solution, very interesting. deserialize() is smart. Use iterator or Queue is really smart.
     * When you use queue or iterator, you do not need to save the current position of the data, queue or iterator can do itself.
     * But if you choose array or list ,you have to do it. That is difficult for this problem.
     * <p>
     * REVIEW : 迭代器和队列这两种数据结构在遍历时，无需存储当前位置信息。对于本题的deserialize()来说非常适用。
     * REVIEW : 当在数组或列表方式存储的数据结构中无法跟踪位置，但是又需要遍历时，Iterator和Queue是很好的工具。
     * REVIEW : 这也算是高内聚设计的典范了吧。隐藏内部细节，有利有弊。
     * <p>
     * Queue Solution : https://leetcode.com/problems/serialize-and-deserialize-binary-tree/solutions/74259/recursive-preorder-python-and-c-o-n/
     * Iterator Solution : https://leetcode.com/problems/serialize-and-deserialize-binary-tree/solutions/74259/recursive-preorder-python-and-c-o-n/
     * <p>
     * 验证通过：
     * Runtime 12 ms Beats 71.84%
     * Memory 45.58 MB Beats 85.08%
     *
     * @see Codec_r3_1
     */
    static class Codec_r3_2 {
        String SPLITER = ",";
        String NULL = "X";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            StringBuilder res = new StringBuilder();
            if (root == null) {
                res.append(NULL).append(SPLITER);
            } else {
                res.append(root.val).append(SPLITER);
                res.append(serialize(root.left));
                res.append(serialize(root.right));
            }
            return res.toString();
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(SPLITER)));
            return deserHelper(queue);
        }

        private TreeNode deserHelper(Queue<String> queue) {
            if (queue == null || queue.size() == 0) return null;
            String item = queue.poll();
            if (item.equals(NULL)) {
                return null;
            }
            TreeNode node = new TreeNode(Integer.valueOf(item));
            node.left = deserHelper(queue);
            node.right = deserHelper(queue);
            return node;
        }
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. Serailize 分析
     * 1.1. 单独使用三种 DFS 算法 pre-order,post-order,in-order 都无法在 deserialize 时正确还原 Binary Tree 。
     * 1.2. BFS 算法可以 serialize 成 leetcode 格式的字符串。
     * 使用两个 que_cur ， que_next 分别存储当前层的节点和下一层的节点
     * 算法伪代码如下：
     * WHILE que_cur is not empty:
     * node = que_cur.poll()
     * res.append(node==null?"null":node.val)
     * IF node != null THEN
     * que_next.offer(node.left)
     * que_next.offer(node.right)
     * IF que_cur is empty THEN
     * que_cur = que_next
     * 2. Deserialize 分析
     * BFS思路
     * 3. 递归法更简单
     * review 递归法更简单,重点是pre-order可以实现serialize
     * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/solutions/74253/easy-to-understand-java-solution/
     *
     * <p>
     * 验证通过：
     * Runtime 15 ms Beats 57.30%
     * Memory 46.55 MB Beats 17.92%
     */
    static class Codec_r3_1 {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) return "";
            Queue<TreeNode> que = new LinkedList<>();
            que.offer(root);
            StringBuilder res = new StringBuilder();
            boolean allAreNull = false;//是否为最后一层，最后一层的特点时queue中全都为null
            while (!que.isEmpty() && !allAreNull) {
                int size = que.size();
                allAreNull = true;
                for (int i = 0; i < size; i++) {
                    TreeNode node = que.poll();
                    res.append(node == null ? "null" : node.val);
                    res.append(",");
                    if (node != null) {
                        que.offer(node.left);
                        que.offer(node.right);
                        allAreNull = allAreNull && !(node.left != null || node.right != null);
                    }
                }
            }
            res.deleteCharAt(res.length() - 1);
            return res.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) return null;
            String[] arr = data.split(",");
            int idx = 0;
            TreeNode root = new TreeNode(Integer.valueOf(arr[idx++]));
            Queue<TreeNode> que = new LinkedList<>();
            que.offer(root);
            while (!que.isEmpty() && idx < arr.length) {
                TreeNode node = que.poll();
                if (!arr[idx].equals("null")) {
                    node.left = new TreeNode(Integer.valueOf(arr[idx]));
                    que.offer(node.left);
                }
                idx++;
                if (idx < arr.length && !arr[idx].equals("null")) {
                    node.right = new TreeNode(Integer.valueOf(arr[idx]));
                    que.offer(node.right);
                }
                idx++;
            }
            return root;
        }
    }

    /**
     * 思考：
     * 1.先确定序列化后的数据结构，即用什么方式存储数据。如：leecode的默认方式。
     * 2.序列化时，是树的遍历问题。一般分为dfs和bfs，dfs有3种：pre-order、in-order、post-order。
     * 2.1.leecode默认方式，属于bfs。
     * <p>
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
//        do_func("1,2,3,null,null,4,5,null,null,null,null");
//        do_func("1,2,3,null,null,4,5");
//        do_func("1,2,3,null,null,4,5,6,7");
        System.out.println("====================");
        do_func_r3_2();
    }

    private static void do_func(String inputStr) {
        System.out.println("input is : " + inputStr);
//        Codec ser = new Codec();
        Codec_r3_2 ser = new Codec_r3_2();
//        TreeNode inputTree = new TreeNode(1);
//        inputTree.left = new TreeNode(2);
//        inputTree.right = new TreeNode(3);
//        inputTree.right.left = new TreeNode(4);
//        inputTree.right.right = new TreeNode(5);
//
//        String outputStr = ser.serialize(inputTree);
//        System.out.println("----------serialize----------");
//        System.out.println(outputStr);
//        System.out.println(inputStr.equals(outputStr));
//        System.out.println("----------deserialize----------");
//        TreeNode node = ser.deserialize(inputStr);

        System.out.println("----------result----------");
        String recover_str = ser.serialize(ser.deserialize(inputStr));
        System.out.println(recover_str);
        System.out.println(inputStr.equals(recover_str));

        System.out.println(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<");
    }

    private static void do_func_r3_2() {
        Codec_r3_2 ser = new Codec_r3_2();
        TreeNode inputTree = new TreeNode(1);
        inputTree.left = new TreeNode(2);
        inputTree.right = new TreeNode(3);
        inputTree.right.left = new TreeNode(4);
        inputTree.right.right = new TreeNode(5);

        String out1 = ser.serialize(inputTree);
        System.out.println("----------serialize----------");
        System.out.println("out1=" + out1);
        System.out.println("----------deserialize----------");
        TreeNode node = ser.deserialize(out1);
        String out2 = ser.serialize(node);
        System.out.println("out2=" + out2);
        System.out.println(out2.equals(out1));

        System.out.println(">>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<");
    }

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
}
