package leetcode;

/**
 * Add Two Numbers
 * Medium
 * ----------------------
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 * Constraints:
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading zeros.
 */
public class Add_Two_Numbers_2 {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers_2(l1, l2);
    }

    /**
     * round 3
     *
     * 验证通过：
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers_2(ListNode l1, ListNode l2) {
        ListNode res = new ListNode();
        ListNode cur = res;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int t = carry;
            if (l1 != null) {
                t += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                t += l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode(t % 10);
            carry = t / 10;
            cur = cur.next;
        }
        if (carry > 0) {
            cur.next = new ListNode(carry);
        }
        return res.next;
    }

    /**
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Add Two Numbers.
     * Memory Usage: 39.5 MB, less than 54.53% of Java online submissions for Add Two Numbers.
     *
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers_1(ListNode l1, ListNode l2) {
        ListNode ret = new ListNode();
        int carry = 0;
        ListNode cur = ret;
        while (l1 != null || l2 != null) {
            cur.next = new ListNode();
            cur = cur.next;
            if (l1 == null) {
                cur.val = (l2.val + carry) % 10;
                carry = (l2.val + carry) / 10;
                l2 = l2.next;
            } else if (l2 == null) {
                cur.val = (l1.val + carry) % 10;
                carry = (l1.val + carry) / 10;
                l1 = l1.next;
            } else {
                cur.val = (l1.val + l2.val + carry) % 10;
                carry = (l1.val + l2.val + carry) / 10;
                l1 = l1.next;
                l2 = l2.next;
            }
        }
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(ListNode.fromArray(new int[]{2, 4, 3}), ListNode.fromArray(new int[]{5, 6, 4}), new int[]{7, 0, 8});
        do_func(ListNode.fromArray(new int[]{0}), ListNode.fromArray(new int[]{0}), new int[]{0});
        do_func(ListNode.fromArray(new int[]{9, 9, 9, 9, 9, 9, 9}), ListNode.fromArray(new int[]{9, 9, 9, 9}), new int[]{8, 9, 9, 9, 0, 0, 0, 1});
        do_func(ListNode.fromArray(new int[]{1}), ListNode.fromArray(new int[]{2}), new int[]{3});
    }

    private static void do_func(ListNode l1, ListNode l2, int[] expected) {
        ListNode ret = addTwoNumbers(l1, l2);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
