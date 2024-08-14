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
        return insertionSortList_r3_1(head);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * AC的提交记录中有个递归+合并排序的性能最好（先把链表分为前后两部分；然后分别排序这两部分；最后合并排序），只需要0ms即可通过全部案例。
     *
     * 验证通过：
     * Runtime 19 ms Beats 52.19%
     * Memory 44.56 MB Beats 29.24%
     *
     * @param head
     * @return
     */
    public static ListNode insertionSortList_r3_1(ListNode head) {
        if (head == null) return null;
        //链表分成已排序和未排序两部分
        ListNode dumb = new ListNode(0, head);
        ListNode unsortHead = head.next;
        head.next = null;//已排序和未排序断开连接
        while (unsortHead != null) {
            //从已排序的链表中查找待插入的位置
            ListNode cur = dumb;
            while (cur.next != null) {
                if (cur.next.val >= unsortHead.val) {
                    break;
                }
                cur = cur.next;
            }
            //插入待排序节点
            ListNode t = cur.next;
            ListNode nextUnsortHead = unsortHead.next;
            cur.next = unsortHead;
            unsortHead.next = t;
            //未排序链表Head后移
            unsortHead = nextUnsortHead;
        }
        return dumb.next;
    }

    /**
     * round 2
     *
     * 原始list分为已排序和未排序两部分
     * 从未排序的list中获取第一个node，把他插入到已排序好的list中
     *
     * 验证通过：
     * Runtime: 22 ms, faster than 53.65% of Java online submissions for Insertion Sort List.
     * Memory Usage: 45.2 MB, less than 14.50% of Java online submissions for Insertion Sort List.
     *
     * TODO 在第一步摘除head的步骤中，增加是否已经有序的判断，可以进一步缩短算法执行的时间。详见leetcode提交。
     *
     * @param head
     * @return
     */
    public static ListNode insertionSortList_2(ListNode head) {
        ListNode ret = new ListNode(0);
        while (head != null) {
            //1.摘除head，提取待插入排序的元素
            ListNode r = head;
            head = head.next;
            r.next = null;
            //2.把t插入到已排序好的list中
            //FIXME: review round 2
            //FIXME: 使用结构化思维，插入排序的核心逻辑分为两步，1.查到应该插入的位置，2.插入元素
            ListNode s = ret;
            //2.1.先找到应该插入的位置
            while (s.next != null && s.next.val < r.val) {
                s = s.next;
            }
            //2.2.再插入元素
            r.next = s.next;
            s.next = r;
        }
        return ret.next;
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
        do_func(new int[]{1}, new int[]{1});
    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode head = ListNode.fromArray(l1);
        ListNode ret = insertionSortList(head);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
