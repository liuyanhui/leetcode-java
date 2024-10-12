package leetcode;

/**
 * 203. Remove Linked List Elements
 * Easy
 * ------------------
 * Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.
 * <p>
 * Example 1:
 * Input: head = [1,2,6,3,4,5,6], val = 6
 * Output: [1,2,3,4,5]
 * <p>
 * Example 2:
 * Input: head = [], val = 1
 * Output: []
 * <p>
 * Example 3:
 * Input: head = [7,7,7,7], val = 7
 * Output: []
 * <p>
 * Constraints:
 * The number of nodes in the list is in the range [0, 10^4].
 * 1 <= Node.val <= 50
 * 0 <= k <= 50
 */
public class Remove_Linked_List_Elements_203 {

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 因为要删除当前节点，使用dummy节点指向当前节点的上一个节点。
     *
     * 验证通过：
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements_r3_1(ListNode head, int val) {
        ListNode dummy = new ListNode(0, head);
        ListNode res = dummy;
        while (dummy.next != null) {
            if (dummy.next.val == val) {
                dummy.next = dummy.next.next;
            } else {
                dummy = dummy.next;
            }
        }
        return res.next;
    }

    /**
     * round 2
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 99.08% of Java online submissions for Remove Linked List Elements.
     * Memory Usage: 43.1 MB, less than 93.52% of Java online submissions for Remove Linked List Elements.
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements_2(ListNode head, int val) {
        ListNode dumb = new ListNode(0, head);
        ListNode prev = dumb;
        while (prev.next != null) {
            if (prev.next.val == val) {
                prev.next = prev.next.next;
            } else {
                prev = prev.next;
            }
            // head = head.next;
        }
        return dumb.next;
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 78.37% of Java online submissions for Remove Linked List Elements.
     * Memory Usage: 39.7 MB, less than 79.01% of Java online submissions for Remove Linked List Elements.
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements_1(ListNode head, int val) {
        ListNode ret = new ListNode();
        ret.next = head;
        ListNode prevNode = ret;
        while (prevNode.next != null) {
            if (prevNode.next.val == val) {
                prevNode.next = prevNode.next.next;
            } else {
                prevNode = prevNode.next;
            }
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 6, 3, 4, 5, 6}, 6, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{}, 1, new int[]{});
        do_func(new int[]{7, 7, 7, 7}, 7, new int[]{});
    }

    private static void do_func(int[] arr, int val, int[] expected) {
        ListNode ret = removeElements_2(ListNode.fromArray(arr), val);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
