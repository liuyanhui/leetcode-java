package leetcode;

/**
 * 143. Reorder List
 * Medium
 * ----------------
 *You are given the head of a singly linked-list. The list can be represented as:
 * L0 → L1 → … → Ln - 1 → Ln
 * Reorder the list to be on the following form:
 * L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …
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
 * The number of nodes in the list is in the range [1, 5 * 104].
 * 1 <= Node.val <= 1000
 */
public class Reorder_List_143 {
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
    public static void reorderList(ListNode head) {
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
