package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 117. Populating Next Right Pointers in Each Node II
 * Medium
 * ------------------------------
 * Given a binary tree
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
 * Example 1:
 * Input: root = [1,2,3,4,5,null,7]
 * Output: [1,#,2,3,#,4,5,7,#]
 * Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 6000].
 * -100 <= Node.val <= 100
 *
 * Follow-up:
 * You may only use constant extra space.
 * The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
 */
public class Populating_Next_Right_Pointers_in_Each_Node_II_117 {
    public static Node connect(Node root) {
        return connect_3(root);
    }

    /**
     * DFS思路。不能用dfs思路，原因见下面。
     *
     * 验证失败：
     *
     * @param root
     * @return
     *
     * @See Populating_Next_Right_Pointers_in_Each_Node_116
     */
    public static Node connect_4(Node root) {
        if (root == null) return root;
        //找到下一层的头结点
        Node dumb = new Node();//review 头结点存在为空的情况，所以使用dumb节点
        //先从当前节点的子节点开始
        if (root.left != null) {
            dumb.next = root.left;
            dumb = dumb.next;
        }
        if (root.right != null) {
            dumb.next = root.right;
            dumb = dumb.next;
        }
        //再计算当前节点的兄弟节点的子节点
        Node t = root.next;
        //循环查找直到同辈兄弟节点的子节点不为空
        while (t != null && t.left == null && t.right == null) {
            t = t.next;//review 这里逻辑有问题。dfs情况下，t.next可能还没有遍历到，所以同层兄弟是不完全的。
        }
        if (t != null) {
            dumb.next = t.left != null ? t.left : t.right;
        }
        connect(root.left);
        connect(root.right);
        return root;
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * 是Populating_Next_Right_Pointers_in_Each_Node_116的一般情况
     * Populating_Next_Right_Pointers_in_Each_Node_116.connect_4()完全一样
     *
     * BFS思路
     *
     * 验证通过：
     *
     * @see Populating_Next_Right_Pointers_in_Each_Node_116
     */
    public static Node connect_3(Node root) {
        Node curLayer = root;
        while (curLayer != null) {
            Node prev = null;
            Node nextLayer = null;
            //遍历当前层，并修改下一层的next属性
            Node cur = curLayer;
            while (cur != null) {
                //review 题目给定的条件是perfect binary tree。所以下面的代码可以优化
                //把下一层修改为链表
                if (cur.left != null) {
                    if (prev == null) {
                        prev = cur.left;
                        nextLayer = cur.left;
                    } else {
                        prev.next = cur.left;
                        prev = prev.next;
                    }
                }
                if (cur.right != null) {
                    if (prev == null) {
                        prev = cur.right;
                        nextLayer = cur.right;
                    } else {
                        prev.next = cur.right;
                        prev = prev.next;
                    }
                }
                cur = cur.next;
            }
            //把下一层切换为当前层
            curLayer = nextLayer;
        }
        return root;
    }

    /**
     * dfs+preorder
     * 1.用List<level>保存每个level的尾结点
     * 2.preorder时追加到对应level的尾结点后
     *
     * 与"116. Populating Next Right Pointers in Each Node"是类似的，稍微复杂一点点
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java.
     * Memory Usage: 45.5 MB, less than 15.40% of Java .
     *
     * @param root
     * @return
     */
    public static Node connect_2(Node root) {
        helper(root, 0, new ArrayList<>());
        return root;
    }

    private static void helper(Node node, int level, List<Node> list) {
        if (node == null) return;
        if (level >= list.size()) list.add(node);
        else {
            list.get(level).next = node;
            list.set(level, node);
        }
        helper(node.left, level + 1, list);
        helper(node.right, level + 1, list);
    }

    /**
     * bfs
     * 1.用链表保存当前level待遍历的node和下一个level待遍历的node
     *
     * 与"116. Populating Next Right Pointers in Each Node"是类似的，稍微复杂一点点
     *
     * 验证通过:
     * Runtime: 1 ms, faster than 76.81% of Java.
     * Memory Usage: 45.5 MB, less than 11.88% of Java.
     *
     * @param root
     * @return
     */
    public static Node connect_1(Node root) {
        Node cur = root;
        Node next = null;
        while (cur != null) {
            next = null;
            Node t = null;
            while (cur != null) {
                //先把当前level节点的子节点加入下一层级BFS队列
                if (cur.left != null) {
                    if (next == null) {
                        next = cur.left;
                        t = next;
                    } else {
                        t.next = cur.left;
                        t = t.next;
                    }
                }
                if (cur.right != null) {
                    if (next == null) {
                        next = cur.right;
                        t = next;
                    } else {
                        t.next = cur.right;
                        t = t.next;
                    }
                }
                cur = cur.next;
            }

            cur = next;
        }
        return root;
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
//        root.right.left = new Node(6);
        root.right.right = new Node(7);
        Node ret = connect(root);
        System.out.println("sss");
    }
}
