package leetcode;

/**
 * 这里有金矿
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 * 82. Remove Duplicates from Sorted List II
 * Medium
 * ------------------
 * Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.
 *
 * Example 1:
 * Input: head = [1,2,3,3,4,4,5]
 * Output: [1,2,5]
 *
 * Example 2:
 * Input: head = [1,1,1,2,3]
 * Output: [2,3]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */
public class Remove_Duplicates_from_Sorted_List_II_82 {

    public static ListNode deleteDuplicates(ListNode head) {
        return deleteDuplicates_4(head);
    }

    /**
     * round 2
     * deleteDuplicates_2
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 64.56% of Java online submissions for Remove Duplicates from Sorted List II.
     * Memory Usage: 44.7 MB, less than 6.25% of Java online submissions for Remove Duplicates from Sorted List II.
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_4(ListNode head) {
        ListNode ret = new ListNode(0, head);
        ListNode pre = ret;
        ListNode tail = pre.next;
        while (tail != null) {
            if (tail.next != null && tail.val == tail.next.val) {
                while (tail.next != null && tail.val == tail.next.val) {
                    tail.next = tail.next.next;
                }
                pre.next = tail.next;
            } else {
                pre = pre.next;
            }
            tail = tail.next;
        }
        return ret.next;
    }


    /**
     * round 2
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 64.56%.
     * Memory Usage: 42.6 MB, less than 15.54%.
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_3(ListNode head) {
        if (head == null) return null;
        ListNode dumb = new ListNode(0, head);
        ListNode pre = dumb;
        pre.next = head;
        ListNode cur = head.next;
        boolean delSignal = false;
        while (cur != null) {
            if (pre.next.val == cur.val) {
                delSignal = true;
            } else {
                if (delSignal) {
                    pre.next = cur;
                    delSignal = false;
                } else {
                    pre = pre.next;
                }
            }
            cur = cur.next;
        }
        if (delSignal) pre.next = null;
        return dumb.next;
    }

    /**
     * 非三色旗思路。
     * deleteDuplicates_1的简化版，每次tail.next.val == current.val时就删除current。
     * 这样可以解决末尾连续元素相等的特殊情况的问题，代码更优雅。
     *
     * 金矿：类似的，在有序列表中删除重复元素（完全删除而不是去重），都可以使用该思路解决。
     *
     * 参考资料：https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/solution/
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_2(ListNode head) {
        if (head == null) return null;
        ListNode ret = new ListNode(0, head);
        ListNode tail = ret;
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                // 金矿：这里很精妙。
                // step1.先去重
                while (head.next != null && head.val == head.next.val) {
                    head = head.next;
                }
                // step2.再把自己删除。
                tail.next = head.next;
            } else {
                tail = tail.next;
            }
            head = head.next;
        }

        return ret.next;
    }

    /**
     * 金矿。类似荷兰三色旗问题。貌似类似的list的问题很多都可以分为三段进行。
     *
     * 可以把列表分为三部分[0:tail],(tail,current],(current:]，分别表示处理完成的/正在处理的节点相等的/未知的。
     *
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Remove Duplicates from Sorted List II.
     * Memory Usage: 38.4 MB, less than 40.64% of Java online submissions for Remove Duplicates from Sorted List II.
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates_1(ListNode head) {
        if (head == null) return null;
        ListNode ret = new ListNode();
        ret.next = head;
        ListNode tail = ret;
        ListNode current = head;
        int count = 0;
        while (current != null) {
            if (tail.next.val == current.val) {
                count++;
                current = current.next;
            } else {
                if (count > 1) {
                    tail.next = current;
                } else {
                    tail = tail.next;
                }
                count = 0;
            }
        }
        // 这里貌似不够优雅，应该可以优化。应该讲逻辑合并到while循环中，是代码更优雅。
        // 处理[x,y,z,m,m,m]这种特殊情况，即末尾元素相等的情况
        if (count > 1) {
            tail.next = null;
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 3, 4, 4, 5}, new int[]{1, 2, 5});
        do_func(new int[]{1, 1, 1, 2, 3}, new int[]{2, 3});
        do_func(new int[]{1, 1}, new int[]{});
//        do_func(new int[]{}, new int[]{});
    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode ret = deleteDuplicates(ListNode.fromArray(l1));
        System.out.println(ret);
//        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
