package leetcode;

/**
 * https://leetcode.com/problems/reverse-linked-list-ii/
 * 92. Reverse Linked List II
 * Medium
 * ----------------
 * Reverse a linked list from position m to n. Do it in one-pass.
 *
 * Note: 1 ≤ m ≤ n ≤ length of list.
 * Example:
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
 * Output: 1->4->3->2->5->NULL
 */
public class Reverse_Linked_List_II92 {
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        return null;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5}, 2, 4, new int[]{1, 4, 3, 2, 5});
    }

    private static void do_func(int[] l1, int m, int n, int[] expected) {
        ListNode ret = reverseBetween(ListNode.fromArray(l1), m, n);
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println("--------------");
    }
}
