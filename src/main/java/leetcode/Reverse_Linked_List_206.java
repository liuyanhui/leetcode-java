package leetcode;

/**
 * 206. Reverse Linked List
 * Easy
 * ----------------------
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * <p>
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 * <p>
 * Example 2:
 * Input: head = [1,2]
 * Output: [2,1]
 * <p>
 * Example 3:
 * Input: head = []
 * Output: []
 * <p>
 * Constraints:
 * The number of nodes in the list is the range [0, 5000].
 * -5000 <= Node.val <= 5000
 * <p>
 * Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
 */
public class Reverse_Linked_List_206 {
    public static ListNode reverseList(ListNode head) {
        return reverseList_r3_1(head);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     *
     * 递归思路1
     * 还存在递归思路2 reverseList_2() 和 迭代思路 reverseList_3()
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 42.58 MB Beats 29.51%
     *
     * @param head
     * @return
     */
    public static ListNode reverseList_r3_1(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode res = reverseList(head.next);
        head.next.next = head;
        head.next = null;//review 打断死循环
        return res;
    }

    public static ListNode reverseList_3(ListNode head) {
        ListNode last = null;
        while (head != null) {
            ListNode t = head.next;
            head.next = last;
            last = head;
            head = t;
        }
        return last;
    }

    /**
     * 递归思路：
     * https://leetcode.com/problems/reverse-linked-list/discuss/58125/In-place-iterative-and-recursive-Java-solution
     *
     * @param head
     * @return
     */
    public static ListNode reverseList_2(ListNode head) {
        //不需要哑节点作为头结点
        return reverse_recursive(null, head);
    }


    private static ListNode reverse_recursive(ListNode newHead, ListNode oldHead) {
        if (oldHead == null) return newHead;
        ListNode next = oldHead.next;
        oldHead.next = newHead;
        return reverse_recursive(oldHead, next);
    }

    /**
     * 迭代思路
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Linked List.
     * Memory Usage: 38.5 MB, less than 86.48% of Java online submissions for Reverse Linked List.
     *
     * @param head
     * @return
     */
    public static ListNode reverseList_1(ListNode head) {
        ListNode dumb = new ListNode(0, head);
        ListNode ret = new ListNode();
        ListNode t = null;
        while (dumb.next != null) {
            t = dumb.next;
            dumb.next = dumb.next.next;
            t.next = ret.next;
            ret.next = t;
        }
        return ret.next;
    }


    public static void main(String[] args) {
        do_func(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), new int[]{5, 4, 3, 2, 1});
        do_func(ListNode.fromArray(new int[]{1, 2}), new int[]{2, 1});
        do_func(ListNode.fromArray(new int[]{}), new int[]{});
    }

    private static void do_func(ListNode head, int[] expected) {
        ListNode ret = reverseList(head);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
