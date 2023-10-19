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
        return mergeTwoLists_4(l1, l2);
    }

    /**
     * round 3
     * Score[5] Lower is harder
     *
     * Thinking：
     * 1.类似插入排序的方式。在计算过程中，有三个部分：已经排序好的部分res，list1未排序部分，list2为排序部分。依次比较list1的head和list2的head，选择较小者追加到res尾部。
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 41.2 MB Beats 53.33%
     *
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode mergeTwoLists_4(ListNode list1, ListNode list2) {
        ListNode res = new ListNode();
        ListNode tail = res;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        if (list1 != null) {
            tail.next = list1;
        }
        if (list2 != null) {
            tail.next = list2;
        }
        return res.next;
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
