package leetcode;

//Definition for singly-linked list.
class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode fromArray(int[] input) {
        if (input == null || input.length == 0) return null;
        ListNode head = new ListNode();
        ListNode node = head;
        for (int i = 0; i < input.length; i++) {
            node.next = new ListNode(input[i]);
            node = node.next;
        }
        return head.next;
    }

    public boolean equalsTo(ListNode input) {
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

    public static boolean isSame(ListNode node, int[] expected) {
        if (node != null) {
            return node.toString().equals(ListNode.fromArray(expected).toString());
        } else if (node == null && expected.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ListNode t = this;
        while (t != null) {
            ret.append(t.val);
            t = t.next;
            if (t != null) {
                ret.append("->");
            }
        }
        return ret.toString();
    }
}
