package leetcode;

/**
 * https://leetcode.com/problems/reverse-linked-list-ii/
 * 92. Reverse Linked List II
 * Medium
 * ----------------
 * Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes of the list from position left to position right, and return the reversed list.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], left = 2, right = 4
 * Output: [1,4,3,2,5]
 *
 * Example 2:
 * Input: head = [5], left = 1, right = 1
 * Output: [5]
 *
 * Constraints:
 * The number of nodes in the list is n.
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 *
 * Follow up: Could you do it in one pass?
 */
public class Reverse_Linked_List_II_92 {
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        return reverseBetween_4(head, m, n);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. 先用双指针法把链表截为三部分；reverse中间部分；把三部分重新连接起来。
     * 1.1. 过程为
     * [:]
     * -> [:one_tail]+[two_head,two_tail]+[three_head:]
     * -> [:one_tail]+[two_tail,two_head]+[three_head:]
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 40.76 MB Beats 93.90%
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseBetween_4(ListNode head, int m, int n) {
        //review 头尾节点，如果存在为null的可能，那么采用dumb节点。
        //双指针分割链表
        ListNode one_dumb = new ListNode(0, head), one_tail = null;
        ListNode two_head = null, two_tail = null;
        ListNode three_dumb = new ListNode();
        int i = 1;
        while (i <= n) {
            if (i < m) {
                one_tail = head;
            } else {
                if (i == m) {
                    two_head = head;
                }
                two_tail = head;
            }
            head = head.next;
            i++;
        }
        three_dumb.next = head;

        //reverse 中间过程为：[head_old,head_new]+[cur,tail_old]
        ListNode cur = two_head;
        ListNode two_new_dumb = new ListNode();
        while (cur != three_dumb.next) {
            ListNode t = cur.next;
            cur.next = two_new_dumb.next;
            two_new_dumb.next = cur;
            cur = t;
        }
        //合并
        //头结点的特殊情况
        if (m == 1) {
            one_dumb.next = two_tail;
        } else {
            one_tail.next = two_tail;
        }
        two_head.next = three_dumb.next;

        return one_dumb.next;
    }

    /**
     * round 2
     * one pass
     *
     * 1.list分割为三部分，[0:left)，[left,right]，(right:~]
     * 2.先找到left节点的父节点记为tail1
     * 3.从left节点开始reverse，直到right节点；并记录right节点的子节点为head3
     * 4.left节点记为tail2，right节点记为head2
     * 5.把三部分重新连接[head:tail1]，[head2,tail2]，[head3:~]
     *
     * 金矿：链表操作切记要使用dumb作为头结点。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List II.
     * Memory Usage: 41.9 MB, less than 22.04% of Java online submissions for Reverse Linked List II.
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseBetween_3(ListNode head, int m, int n) {
        ListNode dumb = new ListNode(0, head);
        ListNode tail1 = dumb, head2 = null, tail2, head3;
        ListNode cur = head;
        // find node[left] and node[tail1]
        while (m > 1 && cur != null) {
            cur = cur.next;
            tail1 = tail1.next;
            m--;
            n--;
        }
        tail1.next = null;
        tail2 = cur;
        //reverse
        while (n > 0 && cur != null) {
            ListNode t = cur.next;
            cur.next = head2;
            head2 = cur;
            cur = t;
            n--;
        }
        head3 = cur;
        //combine
        tail1.next = head2;
        tail2.next = head3;
        return dumb.next;
    }

    /**
     * one-pass方案
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List II.
     * Memory Usage: 36.4 MB, less than 80.81% of Java online submissions for Reverse Linked List II.
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseBetween_2(ListNode head, int m, int n) {
        ListNode ret = new ListNode(0, head);
        if (m == n) {
            return head;
        }
        // n1~n5分别表示每次reverse参与的5个节点，从前向后
        ListNode n1 = ret, n2 = head, n3 = head, n4 = head, n5 = head.next;
        int i = 1;
        //find pre first node which will be reversed
        while (i < m) {
            n1 = n1.next;
            n2 = n2.next;
            i++;
        }
        //intial first reversed node
        n3 = n2;
        n4 = n3.next;
        n5 = n4.next;
        i++;

        //reverse
        while (i <= n) {
            n4.next = n2;
            n3.next = n5;
            n1.next = n4;
            n2 = n4;
            n4 = n5;
            if (n5 != null) {//防止处理最后一个节点时抛出空指针，即n=len(head)的情况
                n5 = n5.next;
            }
            i++;
        }

        return ret.next;
    }

    /**
     * two-pass 方案
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List II.
     * Memory Usage: 36.4 MB, less than 80.81% of Java online submissions for Reverse Linked List II.
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public static ListNode reverseBetween_1(ListNode head, int m, int n) {
        ListNode ret = new ListNode(0, head);
        //n1是head[m-1]，n2是head[m]，n3是head[n]，n4是head[n+1]
        ListNode n1 = ret, n2 = head, n3 = ret, n4 = ret;
        int i = 1;
        //先定位到四个边界节点n1,n2,n3,n4
        while (head != null && i <= n) {
            if (i == m - 1) {
                n1 = head;
                n2 = head.next;
            } else if (i == n) {
                n3 = head;
                n4 = head.next;
            }
            head = head.next;
            i++;
        }
        //reverse head[m:n]
        ListNode tLast = n2;
        ListNode tCur = n2.next;
        while (tCur != n4) {
            ListNode tNext = tCur.next;
            tCur.next = tLast;
            tLast = tCur;
            tCur = tNext;
        }
        //join reversed list
        n1.next = n3;
        n2.next = n4;

        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5}, 2, 4, new int[]{1, 4, 3, 2, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 2, 2, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 1, 5, new int[]{5, 4, 3, 2, 1});
        do_func(new int[]{1, 2, 3, 4, 5}, 1, 1, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 5, 5, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{5}, 1, 1, new int[]{5});
        System.out.println("-------Done-------");
    }

    private static void do_func(int[] l1, int m, int n, int[] expected) {
        ListNode ret = reverseBetween(ListNode.fromArray(l1), m, n);
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        assert ret.equalsTo(ListNode.fromArray(expected));
        System.out.println("--------------");
    }
}
