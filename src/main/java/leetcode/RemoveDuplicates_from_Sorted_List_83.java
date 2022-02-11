package leetcode;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * 83. Remove Duplicates from Sorted List
 * Easy
 * ----------------------------
 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once. Return the linked list sorted as well.
 *
 * Example 1:
 * Input: head = [1,1,2]
 * Output: [1,2]
 *
 * Example 2:
 * Input: head = [1,1,2,3,3]
 * Output: [1,2,3]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */
public class RemoveDuplicates_from_Sorted_List_83 {
    public static ListNode deleteDuplicates(ListNode head) {
        return deleteDuplicates_3(head);
    }

    /**
     * round 2
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Remove Duplicates from Sorted List.
     * Memory Usage: 41.5 MB, less than 21.19% of Java online submissions for Remove Duplicates from Sorted List.
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_3(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val)
                cur.next = cur.next.next;
            else
                cur = cur.next;
        }
        return head;
    }

    /**
     * deleteDuplicates_1的简化版，只用一个额外的变量
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_2(ListNode head) {
        ListNode point = head;
        while (point != null && point.next != null) {
            if (point.val == point.next.val) {
                point.next = point.next.next;
            } else {
                point = point.next;
            }
        }
        return head;
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Remove Duplicates from Sorted List.
     * Memory Usage: 38.5 MB, less than 49.87% of Java online submissions for Remove Duplicates from Sorted List.
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_1(ListNode head) {
        if (head == null) return null;
        ListNode last = head;
        ListNode point = head.next;
        while (point != null) {
            if (last.val == point.val) {
                last.next = last.next.next;
            } else {
                last = last.next;
            }
            point = point.next;
        }
        return head;
    }

    public static void main(String[] args) {
        do_func(new int[]{}, new int[]{});
        do_func(new int[]{1, 1, 2}, new int[]{1, 2});
        do_func(new int[]{1, 1, 2, 3, 3}, new int[]{1, 2, 3});
        do_func(new int[]{1, 1, 1, 1, 1}, new int[]{1});
    }

    private static void do_func(int[] intput, int[] expected) {
        ListNode ret = deleteDuplicates(ListNode.fromArray(intput));
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
