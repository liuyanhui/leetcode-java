package leetcode;

/**
 * 328. Odd Even Linked List
 * Medium
 * ----------------------------
 * Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.
 *
 * The first node is considered odd, and the second node is even, and so on.
 *
 * Note that the relative order inside both the even and odd groups should remain as it was in the input.
 *
 * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,3,5,2,4]
 *
 * Example 2:
 * Input: head = [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 *
 * Constraints:
 * n == number of nodes in the linked list
 * 0 <= n <= 10^4
 * -10^6 <= Node.val <= 10^6
 */
public class Odd_Even_Linked_List_328 {
    public static ListNode oddEvenList(ListNode head) {
        return oddEvenList_3(head);
    }

    /**
     * 金矿
     * 奇偶互为next
     *
     * 更简单的方案：
     * https://leetcode.com/problems/odd-even-linked-list/solution/
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList_3(ListNode head) {
        if (head == null) return null;
        ListNode odd = head, even = head.next, evenHead = even;
        //以even为条件，跟下面的方案不同
        while (even != null && even.next != null) {
            //注意：odd和even的操作顺序不能反
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    /**
     * oddEvenList_1()的简化版
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Odd Even Linked List.
     * Memory Usage: 38.7 MB, less than 54.16% of Java online submissions for Odd Even Linked List.
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList_2(ListNode head) {
        if (head == null) return head;
        ListNode evenHead = new ListNode();
        ListNode oddTail = head, evenTail = evenHead;
        while (oddTail != null && oddTail.next != null) {
            evenTail.next = oddTail.next;
            evenTail = evenTail.next;

            oddTail.next = oddTail.next.next;
            oddTail = oddTail.next == null ? oddTail : oddTail.next;

            evenTail.next = null;
        }
        oddTail.next = evenHead.next;
        return head;
    }

    /**
     * 代码略显复杂
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Odd Even Linked List.
     * Memory Usage: 38.5 MB, less than 66.94% of Java online submissions for Odd Even Linked List.
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList_1(ListNode head) {
        if (head == null) return head;
        ListNode oddTail = head, evenTail = null;
        ListNode evenHead = null;
        //1.先把奇偶节点分拆成两个list
        while (oddTail != null && oddTail.next != null) {
            if (evenHead == null) {
                evenTail = oddTail.next;
                evenHead = oddTail.next;
            } else {
                evenTail.next = oddTail.next;
                evenTail = evenTail.next;
            }
            if (oddTail.next != null && oddTail.next.next != null) {
                oddTail.next = oddTail.next.next;
                oddTail = oddTail.next;
            } else {
                oddTail.next = null;
            }
            evenTail.next = null;
        }
        //2.偶节点的list追加到奇节点的list后面
        oddTail.next = evenHead;
        return head;
    }

    public static void main(String[] args) {
        do_func(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), new int[]{1, 3, 5, 2, 4});
        do_func(ListNode.fromArray(new int[]{2, 1, 3, 5, 6, 4, 7}), new int[]{2, 3, 6, 7, 1, 5, 4});
        do_func(ListNode.fromArray(new int[]{1}), new int[]{1});
        do_func(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}), new int[]{1, 3, 5, 2, 4, 6});
        do_func(ListNode.fromArray(new int[]{1, 2}), new int[]{1, 2});
        do_func(ListNode.fromArray(new int[]{1, 2, 3}), new int[]{1, 3, 2});
        do_func(ListNode.fromArray(new int[]{}), new int[]{});
    }

    private static void do_func(ListNode head, int[] expected) {
        ListNode ret = oddEvenList(head);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
