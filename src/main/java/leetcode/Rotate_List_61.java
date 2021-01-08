package leetcode;

/**
 * https://leetcode.com/problems/rotate-list/
 * Given the head of a linked list, rotate the list to the right by k places.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [4,5,1,2,3]
 *
 * Example 2:
 * Input: head = [0,1,2], k = 4
 * Output: [2,0,1]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 500].
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 109
 */
public class Rotate_List_61 {
    /**
     * 思路：快慢指针法。需要考虑k大于列表长度count的情况，解决办法k%count即可。还需要考虑k==0的情况。
     * 优化思路：得到count后，不需要快慢指针了，count-k%count 位置的节点就是rotate的节点。
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate List.
     * Memory Usage: 38.2 MB, less than 89.52% of Java online submissions for Rotate List.
     * @param head
     * @param k
     * @return
     */
    public static ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null) {
            return head;
        }
        ListNode ret = null;
        ListNode fast = head, slow = head;
        int total = 0;
        ListNode totalNode = head;
        while (totalNode != null) {
            total++;
            totalNode = totalNode.next;
        }
        int thisK = k % total;
        if (thisK == 0) {
            return head;
        }
        int count = 0;
        while (fast.next != null) {
            if (count < thisK) {
                fast = fast.next;
                count++;
                continue;
            }
            fast = fast.next;
            slow = slow.next;
        }
        ret = slow.next;
        slow.next = null;
        fast.next = head;
        return ret;
    }

    public static void main(String[] args) {
//        do_func(new int[]{}, 0, new int[]{});
        do_func(new int[]{1}, 1, new int[]{1});
        do_func(new int[]{1, 2, 3, 4, 5}, 2, new int[]{4, 5, 1, 2, 3});
        do_func(new int[]{1, 2, 3, 4, 5}, 21, new int[]{5, 1, 2, 3, 4});
        do_func(new int[]{0, 1, 2}, 4, new int[]{2, 0, 1});
    }

    private static void do_func(int[] array, int k, int[] expected) {
        ListNode ret = rotateRight(ListNode.fromArray(array), k);
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println("--------------");
    }
}

