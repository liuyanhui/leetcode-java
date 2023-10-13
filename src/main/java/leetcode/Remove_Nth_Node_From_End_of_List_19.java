package leetcode;

/**
 * 19. Remove Nth Node From End of List
 * Medium
 * -------------------------
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 *
 * Example 2:
 * Input: head = [1], n = 1
 * Output: []
 *
 * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 * Follow up: Could you do this in one pass?
 */
public class Remove_Nth_Node_From_End_of_List_19 {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        return removeNthFromEnd_2(head, n);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1.双指针法。
     * 双指针法先定位到被删除节点的上一个节点，再执行删除操作。
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 40.5 MB Beats 54.49%
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd_2(ListNode head, int n) {
        if (head == null) return null;
        ListNode res = new ListNode(0, head);
        ListNode prev = res;
        ListNode end = head;
        //先定位被删除的节点的上一个节点
        while (end != null) {
            if (n <= 0) {
                prev = prev.next;
            }
            end = end.next;
            n--;
        }
        //删除节点
        prev.next = prev.next.next;
        return res.next;
    }

    /**
     * 双指针，first在前，second在后。first前进一步，n--；当n==0时，second开始前进；直到first到达尾部。
     * 当first到达尾部时，second删除next节点即可。即：second.next = second.next.next
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java.
     * Memory Usage: 36.9 MB, less than 83.81% of Java
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd_1(ListNode head, int n) {
        ListNode ret = new ListNode(0, head);
        ListNode first = ret, second = ret;
        while (first.next != null) {
            first = first.next;
            if (n > 0) {
                n--;
            } else {
                second = second.next;
            }
        }
        second.next = second.next.next;
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2, new int[]{1, 2, 3, 5});
        do_func(ListNode.fromArray(new int[]{1}), 1, new int[]{});
        do_func(ListNode.fromArray(new int[]{1, 2}), 1, new int[]{1});
    }

    private static void do_func(ListNode head, int n, int[] expected) {
        ListNode ret = removeNthFromEnd(head, n);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
