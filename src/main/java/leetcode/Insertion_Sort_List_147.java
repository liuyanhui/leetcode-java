package leetcode;

/**
 * 147. Insertion Sort List
 * Medium
 * ------------------
 * Given the head of a singly linked list, sort the list using insertion sort, and return the sorted list's head.
 *
 * The steps of the insertion sort algorithm:
 * 1.Insertion sort iterates, consuming one input element each repetition and growing a sorted output list.
 * 2.At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list and inserts it there.
 * 3.It repeats until no input elements remain.
 *
 * The following is a graphical example of the insertion sort algorithm. The partially sorted list (black) initially contains only the first element in the list. One element (red) is removed from the input data and inserted in-place into the sorted list with each iteration.
 *
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 *
 * Constraints:
 * The number of nodes in the list is in the range [1, 5000].
 * -5000 <= Node.val <= 5000
 */
public class Insertion_Sort_List_147 {
    public static ListNode insertionSortList(ListNode head) {
        return insertionSortList_1(head);
    }

    /**
     * 分为三步：
     * 1.根据大小定位
     * 2.执行insert操作
     * 3.节点后移
     *
     * 验证通过：
     * Runtime: 29 ms, faster than 55.94% of Java online submissions for Insertion Sort List.
     * Memory Usage: 39.1 MB, less than 15.61% of Java online submissions for Insertion Sort List.
     *
     * @param head
     * @return
     */
    public static ListNode insertionSortList_1(ListNode head) {
        ListNode ret = new ListNode(Integer.MIN_VALUE);
        ret.next = head;
        ListNode c = ret;
        while (c.next != null) {
            ListNode t = ret;
            //1.根据大小定位
            while (t.next.val < c.next.val) {
                t = t.next;
            }
            if (t.next != c.next) {
                //2.执行insert操作
                ListNode tmp = c.next;
                c.next = c.next.next;
                tmp.next = t.next;
                t.next = tmp;
            } else {
                //3.节点后移
                c = c.next;
            }
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{4, 2, 1, 3}, new int[]{1, 2, 3, 4});
        do_func(new int[]{-1, 5, 3, 4, 0}, new int[]{-1, 0, 3, 4, 5});
        do_func(new int[]{1, 1, 1, 1, 1}, new int[]{1, 1, 1, 1, 1});
    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode head = ListNode.fromArray(l1);
        ListNode ret = insertionSortList(head);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
