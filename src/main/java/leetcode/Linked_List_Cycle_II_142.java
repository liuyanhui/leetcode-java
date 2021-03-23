package leetcode;

/**
 * 142. Linked List Cycle II
 * Medium
 * -------------
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.
 *
 *  Notice that you should not modify the linked list.
 *
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 * Output: tail connects to node index 1
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *
 * Example 2:
 * Input: head = [1,2], pos = 0
 * Output: tail connects to node index 0
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 * Example 3:
 * Input: head = [1], pos = -1
 * Output: no cycle
 * Explanation: There is no cycle in the linked list.
 *
 * Constraints:
 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 *
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class Linked_List_Cycle_II_142 {

    /**
     * detectCycle_1()的另一种写法，优化了isStarted变量
     * @param head
     * @return
     */
    public ListNode detectCycle_2(ListNode head) {
        if (head == null) return head;
        ListNode slow = head, fast = head;
        boolean hasCircle = false;
        while (slow != null && fast != null && fast.next != null) {
            //tip:先移动再判断slow==fast就无需额外判断是否是初始化时的slow==fast的情况了
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCircle = true;
                break;
            }
        }
        if (hasCircle) {
            ListNode joint = head;
            while (joint != fast) {
                joint = joint.next;
                fast = fast.next;
            }
            return joint;
        } else {
            return null;
        }
    }

    /**
     * 两次遍历思路：
     * 1.第一次，使用快慢节点法找到快慢节点的汇合点node1。如果存在node1表示存在circle，否则不存在circle
     * 2.第二次，当存在circle时，分别从head和node1前进，当前进时的节点相同时，即为所求。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Linked List Cycle II.
     * Memory Usage: 38.7 MB, less than 93.04% of Java online submissions for Linked List Cycle II.
     * @param head
     * @return
     */
    public ListNode detectCycle_1(ListNode head) {
        if (head == null) return head;
        ListNode slow = head, fast = head;
        boolean hasCircle = false;
        boolean isStarted = true;//用来判断是否杠杠开始
        while (slow != null && fast != null && fast.next != null) {
            if (slow == fast && isStarted) {
                hasCircle = true;
                break;
            } else {
                slow = slow.next;
                fast = fast.next.next;
            }
            isStarted = false;
        }
        if (hasCircle) {
            ListNode joint = head;
            while (joint != fast) {
                joint = joint.next;
                fast = fast.next;
            }
            return joint;
        } else {
            return null;
        }
    }
}
