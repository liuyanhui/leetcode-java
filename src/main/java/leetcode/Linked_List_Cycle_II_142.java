package leetcode;

/**
 * 142. Linked List Cycle II
 * Medium
 * -------------
 * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter.
 *
 * Do not modify the linked list.
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
 * The number of the nodes in the list is in the range [0, 10^4].
 * -10^5 <= Node.val <= 10^5
 * pos is -1 or a valid index in the linked-list.
 *
 * //已被删除。Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class Linked_List_Cycle_II_142 {

    /**
     * round 2:review
     * FIXME：相遇的条件是，每轮slow和fast都移动后才能进行比较。类似一个时间片都执行完毕之后再进行比较。
     * FIXME：fast移动第一步或fast移动2步但slow未移动时，不可以进行比较。
     *
     * 思路：
     * slow和fast相遇时满足一下条件：
     * 1.slow移动的步数为a1+a2，a1为head到环起点joint的步数
     * 2.fast移动的步数为a1+a2+c，c为环的步数
     * 3.存在等式(a1+a2)*2=a1+a2+c，那么a1+a2=c
     * 4.相遇是fast已经从环起点joint移动了a2步了，那么只需要在继续移动a1步就可以抵达环起点joint了。
     *
     * 算法：
     * 1.先证明存在环
     * 2.再找到环的起点
     *
     * @param head
     * @return
     */
    public ListNode detectCycle_3(ListNode head) {
        ListNode ret = null;
        ListNode fast = head, slow = head;
        boolean hasCircle = false;
        //round 1 : check if there has a circle
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                slow = head;
                hasCircle = true;
                break;
            }
        }
        //round 2 : find begin node
        while (hasCircle && fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return hasCircle ? slow : null;
    }

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
