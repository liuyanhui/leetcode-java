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
 * Example 1:
 * Input: root = [1,2,3,4,5,6,7]
 * Output: [1,#,2,3,#,4,5,6,7,#]
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 *
 * Example 2:
 * Input: root = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 2^12 - 1].
 * -1000 <= Node.val <= 1000
 *
 * Follow-up:
 * You may only use constant extra space.
 * The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
 */
public class Populating_Next_Right_Pointers_in_Each_Node_116 {
    public static Node connect(Node root) {
        return connect_4(root);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1. DFS 方案
     * 1.1. 采用preorder遍历，函数为dfs(Node node,int level,List<Node> prevList)
     * 1.2. 用列表保存每层的上一个节点，记为prev。设置prev.next=node。把prev替换为node。
     * 1.3. 空间复杂度:O(h)，h为树的高度。
     * 2. BFS 方案
     * 2.1. 遍历每层的队列时，把前一个节点的right属性设为下一个节点。
     * 2.2. 空间复杂度:O(N)。
     * 3. 充分利用next这个属性，无论在DFS还是BFS都可以在O(1)空间复杂度下实现。
     * 参考connect_dfs2()和connect_bfs2()
     *
     * connect_dfs2()非常优雅
     *
     * 本方法是bfs的一种实现。
     *
     *
     * 验证通过：
     *
     * @param root
     * @return
     */
    public static Node connect_4(Node root) {
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
     *
     * connect_dfs2()和connect_bfs2()都是非常精妙的方案
     *
     * 前者巧妙利用了父节点的next属性。充分利用了类似数学中的推导法。
     * 后者用两个Node替代了一般场景下使用BFS时用的两个队列。
     */

    /**
     * round 2
     *
     * preorder
     * 按照深度创建链表数组,每个节点根据深度依次追加到对应链表后
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 67.57% of Java
     * Memory Usage: 47.1 MB, less than 50.85% of Java
     *
     * @param root
     * @return
     */
    public static Node connect_3(Node root) {
        helper(root, new ArrayList<>(), 0);
        return root;
    }

    private static void helper(Node node, List<Node> list, int level) {
        if (node == null) return;
        //节点加入链表或追加到链表后
        if (level >= list.size()) list.add(node);
        else list.get(level).next = node;
        //修改list的元素为最后加入的节点
        list.set(level, node);
        //遍历树
        helper(node.left, list, level + 1);
        helper(node.right, list, level + 1);
    }

    /**
     * 套路
     *
     * review 完美的，简洁的方案
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
     * 套路
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

