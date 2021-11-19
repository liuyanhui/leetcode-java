package leetcode;

/**
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * 21. Merge Two Sorted Lists
 * Easy
 * ------------------
 * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.
 *
 * Example 1:
 * Input: l1 = [1,2,4], l2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 *
 * Example 2:
 * Input: l1 = [], l2 = []
 * Output: []
 *
 * Example 3:
 * Input: l1 = [], l2 = [0]
 * Output: [0]
 *
 * Constraints:
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both l1 and l2 are sorted in non-decreasing order.
 */
public class Merge_Two_Sorted_Lists_21 {
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return mergeTwoLists_3(l1, l2);
    }

    /**
     * round2
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Two Sorted Lists.
     * Memory Usage: 38.1 MB, less than 96.84% of Java online submissions for Merge Two Sorted Lists.
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists_3(ListNode l1, ListNode l2) {
        ListNode newHead = new ListNode();
        ListNode cur = newHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                cur = cur.next;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next;
                l2 = l2.next;
            }
        }
        if (l1 != null) {
            cur.next = l1;
        }
        if (l2 != null) {
            cur.next = l2;
        }
        return newHead.next;
    }

    /**
     * 递归思路，简洁的代码
     * 参考思路：
     * https://leetcode.com/problems/merge-two-sorted-lists/discuss/9715/Java-1-ms-4-lines-codes-using-recursion
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists_2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 很简单没什么好说的
     * 验证通过:
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Two Sorted Lists.
     * Memory Usage: 38.6 MB, less than 33.63% of Java online submissions for Merge Two Sorted Lists.
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists_1(ListNode l1, ListNode l2) {
        ListNode ret = new ListNode();
        ListNode cur = ret;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                cur.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            cur = cur.next;
        }
        while (l1 != null) {
            cur.next = new ListNode(l1.val);
            cur = cur.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            cur.next = new ListNode(l2.val);
            cur = cur.next;
            l2 = l2.next;
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 4}, new int[]{1, 3, 4}, new int[]{1, 1, 2, 3, 4, 4});
//        do_func(new int[]{}, new int[]{}, new int[]{});
        do_func(new int[]{}, new int[]{0}, new int[]{0});
    }

    private static void do_func(int[] l1, int[] l2, int[] expected) {
        ListNode ret = mergeTwoLists(ListNode.fromArray(l1), ListNode.fromArray(l2));
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println("--------------");
    }

}
