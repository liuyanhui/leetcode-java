package leetcode;

//Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    static ListNode fromArray(int[] input) {
        if (input == null) return null;
        ListNode head = new ListNode();
        ListNode node = head;
        for (int i = 0; i < input.length; i++) {
            node.next = new ListNode(input[i]);
            node = node.next;
        }
        return head.next;
    }

    boolean equalsTo(ListNode input) {
        if (this == null || input == null) {
            return false;
        }
        ListNode self = this;
        ListNode in = input;
        while (self != null && in != null) {
            if (self.val == in.val) {
                self = self.next;
                in = in.next;
            } else {
                return false;
            }
        }
        if (self == null && in == null) {
            return true;
        } else {
            return false;
        }
    }
}
