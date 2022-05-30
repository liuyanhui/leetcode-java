package leetcode;

/**
 * 143. Reorder List
 * Medium
 * ----------------
 *You are given the head of a singly linked-list. The list can be represented as:
 * L0 → L1 → … → L(n-1) → Ln
 * Reorder the list to be on the following form:
 * L0 → Ln → L1 → L(n-1) → L2 → L(n-2) → …
 * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
 *
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [1,4,2,3]
 *
 * Example 2:
 * Input: head = [1,2,3,4,5]
 * Output: [1,5,2,4,3]
 *
 * Constraints:
 * The number of nodes in the list is in the range [1, 5 * 10^4].
 * 1 <= Node.val <= 1000
 */
public class Reorder_List_143 {
    public static void reorderList(ListNode head) {
        reorderList_2(head);
    }

    /**
     * 算法：
     * 1.把list平均分割成前后两部分，分别为first,second
     * 2.把second从后向前反转
     * 3.依次穿插合并first和反转后的second
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.99% of Java online submissions for Reorder List.
     * Memory Usage: 44.9 MB, less than 94.04% of Java online submissions for Reorder List.
     *
     * @param head
     */
    public static void reorderList_2(ListNode head) {
        if(head==null) return;
        ListNode first = head, second = head;
        //list平均分成前后两部分
        while (second.next != null && second.next.next != null) {
            second = second.next.next;
            first = first.next;
        }
        second = first.next;
        first.next = null;
        //反转第二部分
        ListNode head2 = null;
        ListNode t = null;
        while (second != null) {
            t = second.next;
            second.next = head2;
            head2 = second;
            second = t;
        }
        //穿插合并
        first = head;
        second = head2;
        ListNode t2 = null;
        while (second != null) {
            t2 = second.next;
            second.next = first.next;
            first.next = second;
            first = first.next.next;
            second = t2;
        }
    }

    /**
     * 思路如下：
     * 1.通过快慢指针法得到中间节点，将list分割为[0:n/2]和[n/2+1:n]两部分
     * 2.反转后半部分列表为[n:n/2+1]
     * 3.依次结合前半部分[0:n/2]和反转后的后半部分[n:n/2+1]
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 99.68% of Java online submissions for Reorder List.
     * Memory Usage: 41.5 MB, less than 84.01% of Java online submissions for Reorder List.
     *
     * @param head
     */
    public static void reorderList_1(ListNode head) {
        if (head == null) return;
        //获取中间节点
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //反转后半部分列表
        ListNode newHalfHead = null;
        ListNode oldHalfHead = slow.next;
        slow.next = null;
        while (oldHalfHead != null) {
            ListNode tmp = oldHalfHead.next;
            oldHalfHead.next = newHalfHead;
            newHalfHead = oldHalfHead;
            oldHalfHead = tmp;
        }
        //重新结合前后两部分
        ListNode cur = head;
        while (newHalfHead != null) {
            ListNode tmp = cur.next;
            cur.next = newHalfHead;
            newHalfHead = newHalfHead.next;
            cur.next.next = tmp;
            cur = tmp;
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4}, new int[]{1, 4, 2, 3});
        do_func(new int[]{1, 2, 3, 4, 5}, new int[]{1, 5, 2, 4, 3});
        do_func(new int[]{1}, new int[]{1});
        do_func(new int[]{1, 2}, new int[]{1, 2});
        do_func(new int[]{1, 2, 3}, new int[]{1, 3, 2});

    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode head = ListNode.fromArray(l1);
        reorderList(head);
        System.out.println(head);
        System.out.println(ListNode.isSame(head, expected));
        System.out.println("--------------");
    }
}
