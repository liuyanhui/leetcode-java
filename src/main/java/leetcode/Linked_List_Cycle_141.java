package leetcode;

/**
 * https://leetcode.com/problems/linked-list-cycle/
 * 141. Linked List Cycle
 * ---------------------------
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 *
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 *
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class Linked_List_Cycle_141 {
    public static boolean hasCycle(ListNode head) {
        return hasCycle_r3_1(head);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1. naive solution
     * Hashtable 缓存已经遍历过的结果。如果hashtable中已经存在某个节点，表示存在环。
     * 2. 快慢指针法
     *
     * 验证通过：
     *
     * @param head
     * @return
     */
    public static boolean hasCycle_r3_1(ListNode head) {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;//review 这里比较关键
        while (fast != null && fast.next != null) {
            if (fast == slow || fast.next == slow) return true;
            fast = fast.next.next;
            slow = slow.next;
        }

        return false;
    }

    /**
     * round 2
     * 快慢指针法，需要注意head节点在环上的情况
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Linked List Cycle.
     * Memory Usage: 45.6 MB, less than 60.65% of Java online submissions for Linked List Cycle.
     *
     * @param head
     * @return
     */
    public boolean hasCycle_2(ListNode head) {
        ListNode fast = head, slow = head;
        boolean virgin = true;
        while (fast != null && fast.next != null) {
            if (fast == slow && !virgin) return true;
            virgin = false;
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    /**
     * 快慢指正法，fast每次前进2步，slow每次前进一步。当fast==slow时，表示右环；当fast为空，表示无环。
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Linked List Cycle.
     * Memory Usage: 40.3 MB, less than 30.36% of Java online submissions for Linked List Cycle.
     * @param head
     * @return
     */
    public static boolean hasCycle_1(ListNode head) {
        if (head == null) return false;
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            if (fast == slow) return true;
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    public static void main(String[] args) {
    }

}
