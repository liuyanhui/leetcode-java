package leetcode;

/**
 * https://leetcode.com/problems/partition-list/
 * 86. Partition List
 * Medium
 * ------------------
 * Given the head of a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
 *
 * You should preserve the original relative order of the nodes in each of the two partitions.
 * Example 1:
 * Input: head = [1,4,3,2,5,2], x = 3
 * Output: [1,2,2,4,3,5]
 *
 * Example 2:
 * Input: head = [2,1], x = 2
 * Output: [1,2]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 200].
 * -100 <= Node.val <= 100
 * -200 <= x <= 200
 */
public class Partition_List_86 {
    public static ListNode partition(ListNode head, int x) {
        return partition_3(head, x);
    }

    /**
     * round 2
     * 思路：
     *  0.遍历输入的list
     *  1.用两个list分别保存小于x的节点和大于等于x的节点。
     *  2.合并两个list。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Partition List.
     * Memory Usage: 42.4 MB, less than 22.60% of Java online submissions for Partition List.
     *
     * @param head
     * @param x
     * @return
     */
    public static ListNode partition_3(ListNode head, int x) {
        ListNode head1 = new ListNode(), tail1 = head1;
        ListNode head2 = new ListNode(), tail2 = head2;
        while (head != null) {
            if (head.val < x) {
                tail1.next = head;
                tail1 = tail1.next;
            } else {
                tail2.next = head;
                tail2 = tail2.next;
            }
            head = head.next;
        }
        tail1.next = head2.next;
        tail2.next = null;
        return head1.next;
    }

    /**
     * 思路：
     * 1.遍历列表
     * 2.如果val<x，加到list1后面；如果val>=x，加到list2后面
     * 3.合并list1和list2
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Partition List.
     * Memory Usage: 38.4 MB, less than 43.20% of Java online submissions for Partition List.
     *
     * @param head
     * @param x
     * @return
     */
    public static ListNode partition_2(ListNode head, int x) {
        if (head == null) return head;
        ListNode list1 = new ListNode();
        ListNode list2 = new ListNode();
        ListNode less = list1, notLess = list2;
        ListNode c = head;
        while (c != null) {
            if (c.val >= x) {
                notLess.next = c;
                notLess = notLess.next;
            } else {
                less.next = c;
                less = less.next;
            }
            c = c.next;
        }
        notLess.next = null;//这里很重要
        less.next = list2.next;
        return list1.next;
    }

    /**
     * 荷兰三色旗问题。
     * 思路：
     * 1.存在遍历s和c，其中：[0,s]之间为小于x的元素，(s,c]之间为大于等于x的元素，(c,n]之间为未确定的元素。
     * 2.如果 c.next>=x 那么 c=c.next
     * 3.如果 c.next<x 那么 将c.next取出，让后放在s的后面
     * 4.约束条件：s<=c
     *
     * 验证通过，"特殊情况"这个地方的代码不够优雅：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Partition List.
     * Memory Usage: 38.3 MB, less than 69.24% of Java online submissions for Partition List.
     *
     * @param head
     * @param x
     * @return
     */
    public static ListNode partition_1(ListNode head, int x) {
        if (head == null) return head;
        ListNode f = new ListNode();
        f.next = head;
        ListNode s = f, c = f;
        while (c.next != null) {
            if (c.next.val >= x) {
                c = c.next;
            } else {
                //特殊情况处理，不够优雅
                if (s == c) {
                    s = s.next;
                    c = c.next;
                    continue;
                }
                //提取c.next，将其从列表中移除
                ListNode t = c.next;
                c.next = c.next.next;
                //把t移到s的后面
                t.next = s.next;
                s.next = t;
                //s前移
                s = t;
            }
        }
        return f.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 4, 3, 2, 5, 2}, 3, new int[]{1, 2, 2, 4, 3, 5});
        do_func(new int[]{2, 1}, 2, new int[]{1, 2});
        do_func(new int[]{1, 4, 3, 2, 5, 2}, -13, new int[]{1, 4, 3, 2, 5, 2});
        do_func(new int[]{1, 4, 3, 2, 5, 2}, 13, new int[]{1, 4, 3, 2, 5, 2});
        do_func(new int[]{1, 4, 3, 2, 5, 2}, 1, new int[]{1, 4, 3, 2, 5, 2});
        do_func(new int[]{1, 4, 6, 2, 5, 2}, 3, new int[]{1, 2, 2, 4, 6, 5});
    }

    private static void do_func(int[] l1, int x, int[] expected) {
        ListNode ret = partition(ListNode.fromArray(l1), x);
        System.out.println(ret);
        System.out.println(ret.equalsTo(ListNode.fromArray(expected)));
        System.out.println("--------------");
    }
}
