package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 116. Populating Next Right Pointers in Each Node
 * Medium
 * -----------------
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 *
 * Follow up:
 * You may only use constant extra space.
 * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 *
 * Example 1:
 * Input: root = [1,2,3,4,5,6,7]
 * Output: [1,#,2,3,#,4,5,6,7,#]
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 *
 * Constraints:
 * The number of nodes in the given tree is less than 4096.
 * -1000 <= node.val <= 1000
 */
public class Populating_Next_Right_Pointers_in_Each_Node_116 {
    public static Node connect(Node root) {
        return connect_dfs2(root);
    }

    /**
     * 完美的，简介的方案
     * 充分利用.next这个属性
     *
     * 参考思路：
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37473/My-recursive-solution(Java)
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 39.5 MB, less than 26.62% of Java online submissions for Populating Next Right Pointers in Each Node.
     *
     * @param root
     * @return
     */
    public static Node connect_dfs2(Node root) {
        if (root == null) return root;
        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null) {
                root.right.next = root.next.left;
            }

            connect_dfs2(root.left);
            connect_dfs2(root.right);
        }
        return root;
    }

    /**
     * 代码精简版，利用node.next属性，不需要list进行缓存数据，直接使用node即可。
     *
     * 参考思路：
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37472/A-simple-accepted-solution
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37461/Java-solution-with-O(1)-memory%2B-O(n)-time
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 39.2 MB, less than 70.19% of Java online submissions for Populating Next Right Pointers in Each Node.
     *
     * @param root
     * @return
     */
    public static Node connect_bfs2(Node root) {
        if (root == null) return root;
        Node prev = root;
        while (prev != null) {
            Node cur = prev;
            Node nextHead = null;
            Node nextCur = new Node();
            while (cur != null) {
                if (cur.left == null) {
                    break;
                }
                if (nextHead == null) {
                    nextHead = cur.left;
                }
                cur.left.next = cur.right;
                nextCur.next = cur.left;
                nextCur = cur.right;

                cur = cur.next;
            }
            prev = nextHead;

        }
        return root;
    }

    /**
     * bfs思路，用两个队列进行计算
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 16.61% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 38.9 MB, less than 89.31% of Java online submissions for Populating Next Right Pointers in Each Node.
     *
     * @param root
     * @return
     */
    public static Node connect_bfs(Node root) {
        if (root == null) return root;
        List<Node> first = new ArrayList<Node>() {{
            add(root);
        }};
        List<Node> second = new ArrayList<>();
        while (first.size() > 0) {
            for (int i = 0; i < first.size(); i++) {
                Node tmp = first.get(i);
                if (i < first.size() - 1) {
                    tmp.next = first.get(i + 1);
                }
                if (tmp.left != null) {
                    second.add(tmp.left);
                }
                if (tmp.right != null) {
                    second.add(tmp.right);
                }
            }
            first = second;
            second = new ArrayList<>();
        }
        return root;
    }

    /**
     * dfs递归思路，比较简单
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Populating Next Right Pointers in Each Node.
     * Memory Usage: 39.6 MB, less than 21.03% of Java online submissions for Populating Next Right Pointers in Each Node.
     *
     * @param root
     * @return
     */
    public static Node connect_dfs(Node root) {
        List<Node> ret = new ArrayList<>();
        do_dfs(root, 0, ret);
        return root;
    }

    private static void do_dfs(Node node, int level, List<Node> ret) {
        if (node == null) return;
        if (ret.size() <= level) {
            ret.add(node);
        } else {
            ret.get(level).next = node;
            ret.set(level, node);
        }
        do_dfs(node.left, level + 1, ret);
        do_dfs(node.right, level + 1, ret);
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }

    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
        Node ret = connect(root);
        System.out.println("sss");
    }
}

