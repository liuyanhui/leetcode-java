package leetcode;

/**
 * 148. Sort List
 * Medium
 * ---------------------
 * Given the head of a linked list, return the list after sorting it in ascending order.
 *
 * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 *
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 *
 * Example 3:
 * Input: head = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 5 * 104].
 * -105 <= Node.val <= 105
 */
public class Sort_List_148 {
    /**
     * 常规方法见：Insertion_Sort_List_147
     *
     * 验证通过:
     * Runtime: 6 ms, faster than 84.96% of Java online submissions for Sort List.
     * Memory Usage: 47.4 MB, less than 54.85% of Java online submissions for Sort List.
     *
     * @param head
     * @return
     */
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dumb = new ListNode();
        dumb.next = head;
        ListNode p = dumb;
        ListNode q = dumb;
        //获得中间节点
        while (q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        //断开前后两部分
        ListNode halfHead = p.next;
        p.next = null;
        //分别排序
        p = sortList(head);
        q = sortList(halfHead);
        //确定p,q中第一个元素中较小的为p，较大大的为q
        ListNode ret = new ListNode();
        if (q != null && p.val > q.val) {
            ListNode t = q;
            q = p;
            p = t;
        }
        ret.next = p;
        //合并排好序的两个部分
        while (p.next != null && q != null) {
            while (p.next != null && p.next.val < q.val) {
                p = p.next;
            }
            ListNode t = q;
            q = q.next;
            t.next = p.next;
            p.next = t;
            p = t;
        }
        while (p.next != null) {
            p = p.next;
        }
        p.next = q;
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{4, 2, 3}, new int[]{2, 3, 4});
        do_func(new int[]{1, 2, 4}, new int[]{1, 2, 4});
        do_func(new int[]{4, 2, 1, 3}, new int[]{1, 2, 3, 4});
        do_func(new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
        do_func(new int[]{-1, 5, 3, 4, 0}, new int[]{-1, 0, 3, 4, 5});
        do_func(new int[]{}, new int[]{});

    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode ret = sortList(ListNode.fromArray(l1));
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
