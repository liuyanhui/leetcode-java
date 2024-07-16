package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 138. Copy List with Random Pointer
 * Medium
 * ---------------------------------
 * A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
 *
 * Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.
 *
 * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.
 *
 * Return the head of the copied linked list.
 *
 * The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
 * val: an integer representing Node.val
 * random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
 *
 * Your code will only be given the head of the original linked list.
 *
 * Example 1:
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 *
 * Example 2:
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 *
 * Example 3:
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 *
 * Constraints:
 * 0 <= n <= 1000
 * -10^4 <= Node.val <= 10^4
 * Node.random is null or is pointing to some node in the linked list.
 */
public class Copy_List_with_Random_Pointer_138 {

    /**
     *
     * Thinking：
     * 1. naive solution
     * 先复制链表，再依次遍历复制random属性。
     * 时间复杂度：O(N*N)
     * 2. 先复制next，再复制random。缓存链表的index。
     * 2.1. 把原始Node按顺序转化成数组arr1
     * 2.2. 用map1缓存Node在原始链表中的顺序.Map<Node,Integer>key为Node对象，value为顺序。
     * 2.3. 根据arr1复制arr2，并生成new_node
     * 2.4. 根据map1的value和arr1，复制new_node的random属性。
     * 3. 复制Node时同时复制next和random。无法做到，因为next和random没有关联，在复制当下需要有顺序指明后操作的属性指向的位置。
     *
     *
     * @param head
     * @return
     */
    public Node copyRandomList_r3_1(Node head) {
        return  null;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/copy-list-with-random-pointer/discuss/43491/A-solution-with-constant-space-complexity-O(1)-and-linear-time-complexity-O(N)
     *
     * 三次遍历法：
     * 1.第一次遍历，把new节点作为old节点的next节点。此时链表长度为原来的2倍。
     * 2.第二次遍历，计算new节点的random属性
     * 3.第三次遍历，拆分链表。恢复就链表，生成新链表。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Copy List with Random Pointer.
     * Memory Usage: 45.6 MB, less than 42.46% of Java online submissions for Copy List with Random Pointer.
     *
     * @param head
     * @return
     */
    public Node copyRandomList_2(Node head) {
        if (head == null) return null;
        Node old = head;
        //新的被复制的node链接到旧old的后面
        while (old != null) {
            Node t = old.next;
            old.next = new Node(old.val);
            old.next.next = t;
            old = t;
        }
        //计算random
        old = head;
        while (old != null) {
            //计算random
            old.next.random = old.random == null ? null : old.random.next;
            old = old.next.next;
        }
        //根据old1->new1->old2->new2的链表结构，生成待返回的链表
        old = head;
        Node ret = new Node(0);
        Node newn = ret;
        while (old != null) {
            Node t = old.next;

            old.next = old.next.next;
            old = old.next;

            newn.next = t;
            newn = newn.next;
        }

        return ret.next;
    }

    /**
     * 要点在于新复制的node不会指向被复制的node
     * 用map缓存已经复制过的node。如果不存在，那么新建node并加入map。
     *
     * 算法：
     * 0.初始化oldnode.next=head，newnode=new Node()
     * 1.如果oldnode.next不为空，循环2,3,4
     * 2.复制oldnode.next为newnode.next
     * 3.复制oldnode.random为newnode.random
     * 4.oldnode=oldnode.next，newnode=newnode.next
     * 5.函数"复制"中使用Map判断是新建newnode还是从map中返回newnode
     *
     * 要点：next和random是不同的，要分开计算。
     * 还可以通过两次遍历的思路，第一次只计算next和并加入缓存，第二次只计算random。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 34.55% of Java online submissions for Copy List with Random Pointer.
     * Memory Usage: 44.5 MB, less than 75.14% of Java online submissions for Copy List with Random Pointer.
     *
     * @param head
     * @return
     */
    public Node copyRandomList_1(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node ret = new Node(0);
        Node newnode = ret;
        Node oldnode = new Node(0);
        oldnode.next = head;
        while (oldnode != null) {
            newnode.next = copy(oldnode.next, map);
            newnode.random = copy(oldnode.random, map);
            oldnode = oldnode.next;
            newnode = newnode.next;
        }
        return ret.next;
    }

    private Node copy(Node old, Map<Node, Node> map) {
        if (old == null) return null;
        //TODO 不能用map.putIfAbsent()，输入不在map中是返回null。
        Node n = map.computeIfAbsent(old, v -> new Node(old.val));
        return n;
    }
}

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}