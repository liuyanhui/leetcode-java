package leetcode;

/**
 * https://leetcode.com/problems/reverse-linked-list-ii/
 * 92. Reverse Linked List II
 * Medium
 * ----------------
 * Reverse a linked list from position m to n. Do it in one-pass.
 *
 * Note: 1 ≤ m ≤ n ≤ length of list.
 * Example:
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
 * Output: 1->4->3->2->5->NULL
 */
public class Reverse_Linked_List_II_92 {
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        return reverseBetween_2(head, m, n);
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
    }

    private static void do_func(int[] l1, int m, int n, int[] expected) {
        ListNode ret = reverseBetween(ListNode.fromArray(l1), m, n);
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println("--------------");
    }
}
