package leetcode;

/**
 * 328. Odd Even Linked List
 * Medium
 * ----------------------------
 * Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return the reordered list.
 * <p>
 * The first node is considered odd, and the second node is even, and so on.
 * <p>
 * Note that the relative order inside both the even and odd groups should remain as it was in the input.
 * <p>
 * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
 * <p>
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,3,5,2,4]
 * <p>
 * Example 2:
 * Input: head = [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 * <p>
 * Constraints:
 * The number of nodes in the linked list is in the range [0, 10^4].
 * -10^6 <= Node.val <= 10^6
 */
public class Odd_Even_Linked_List_328 {
    public static ListNode oddEvenList(ListNode head) {
        return oddEvenList_r3_1(head);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * review oddEvenList_3()的思路很巧妙
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 45.10 MB Beats 9.59%
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList_r3_1(ListNode head) {
        if (head == null) return null;
        ListNode evenHead = null;
        ListNode oddTail = new ListNode();
        ListNode evenTail = new ListNode();
        ListNode cur = head;
        while (cur != null) {
            if (evenHead == null) evenHead = cur.next;
            oddTail.next = cur;
            oddTail = oddTail.next;
            evenTail.next = cur.next;
            evenTail = evenTail.next;
            if (cur.next != null) {
                cur = cur.next.next;
            } else {
                cur = null;
            }
        }
        oddTail.next = evenHead;

        return head;
    }

    /**
     * round 2
     * <p>
     * Thinking：
     * 1.思路1：分别把odd和even节点单独提取出来，然后把even链表追加到odd链表后面.
     * 2.思路2：在源链表上直接修改。把odd节点按顺序移动到链表前半部分即可，无需考虑even节点。需要记录odd节点的尾部节点和当前处理的节点。
     * <p>
     * 与oddEvenList_2()类似。但是不如oddEvenList_3()精妙
     * <p>
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 42.2 MB Beats 50.38%
     *
     * @param head
     * @return
     */
    public static ListNode oddEvenList_4(ListNode head) {
        if (head == null) return null;
        ListNode res = new ListNode(0, head);
        ListNode evenHead = head.next;
        ListNode oddTail = res;
        ListNode cur = new ListNode(0, head);
        while (cur != null && cur.next != null) {
            ListNode t = cur.next.next;
            oddTail.next = cur.next;
            oddTail = oddTail.next;
            cur.next = t;
            cur = cur.next;
        }
        oddTail.next = evenHead;
        return res.next;
    }

    /**
     * review round 2
     * 金矿
     * 奇偶互为next
     * <p>
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
     * <p>
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
     * <p>
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
