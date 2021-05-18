package leetcode;

/**
 * 234. Palindrome Linked List
 * Easy
 * -----------------------
 * Given the head of a singly linked list, return true if it is a palindrome.
 *
 * Example 1:
 * Input: head = [1,2,2,1]
 * Output: true
 *
 * Example 2:
 * Input: head = [1,2]
 * Output: false
 *
 * Constraints:
 * The number of nodes in the list is in the range [1, 10^5].
 * 0 <= Node.val <= 9
 *
 * Follow up: Could you do it in O(n) time and O(1) space?
 */
public class Palindrome_Linked_List_234 {
    /**
     * 验证通过：
     * Runtime: 4 ms, faster than 96.87% of Java online submissions for Palindrome Linked List.
     * Memory Usage: 48.8 MB, less than 89.66% of Java online submissions for Palindrome Linked List.
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head;
        //定位中间node
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //反转后半部分列表
        ListNode h2 = null;
        while (slow != null) {
            ListNode t = slow;
            slow = slow.next;
            t.next = h2;
            h2 = t;
        }
        //比较
        while (h2 != null) {
            if (head.val == h2.val) {
                head = head.next;
                h2 = h2.next;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func(ListNode.fromArray(new int[]{1, 2, 2, 1}), true);
        do_func(ListNode.fromArray(new int[]{1, 2}), false);
        do_func(ListNode.fromArray(new int[]{1, 2, 1}), true);
        do_func(ListNode.fromArray(new int[]{1}), true);
    }

    private static void do_func(ListNode head, boolean expected) {
        boolean ret = isPalindrome(head);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
