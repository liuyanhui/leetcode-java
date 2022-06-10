package leetcode;

/**
 * 148. Sort List
 * Medium
 * ---------------------
 * Given the head of a linked list, return the list after sorting it in ascending order.
 *
 * Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 *
 * Example 1:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 *
 * Example 2:
 * Input: head = [-1,5,3,4,0]
 * Output: [-1,0,3,4,5]
 *
 * Example 3:
 * Input: head = []
 * Output: []
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 5 * 10^4].
 * -10^5 <= Node.val <= 10^5
 */
public class Sort_List_148 {
    public static ListNode sortList(ListNode head) {
        return sortList_2(head);
    }

    /**
     * round 2
     *
     * 思路1
     * 利用辅助存储，1.用把链表转化成数组，2.排序数组，3.把数组还原成链表
     * Time Complexity:O(nlogn), Space Complexity:O(n)
     *
     * 思路2
     * 归并排序。
     * 1.相邻的两两排序
     * 2.相邻的四四排序
     * 3.相邻的八八排序
     * 4.直到全部排序完毕
     * Time Complexity:O(nlogn), Space Complexity:O(1)
     *
     * 思路2算法如下：
     * 1. 先计算链表长度，记为len；设当前归并排序的阶段数量为c。其中c=[2,4,8,16,..]
     * 2. 如果c<len，那么循环
     * 2.1. 每次按照数量c循环执行归并排序，直到链表尾部
     * 2.1.1. 获取待归并排序的链表头(h1和h2)。(下一个h1为上一个合并后的尾节点的next)
     * 2.1.2. 归并排序head1和head2，长度为c
     * 2.2. c=c*2
     *
     * 套路：
     * 归并有两种方式：
     * 1 Top to Bottom ，通常用递归实现，空间复杂度O(N)，时间复杂度O(NlogN)
     * 2 Bottom to Top ，通常用迭代实现，空间复杂度O(1)，时间复杂度O(NlogN)
     *
     * 验证通过：
     * Runtime: 18 ms, faster than 35.39% of Java online submissions for Sort List.
     * Memory Usage: 77.6 MB, less than 23.40% of Java online submissions for Sort List.
     *
     * FIXME: 链表需要有专门的头尾节点
     *
     * @param head
     * @return
     */
    public static ListNode sortList_2(ListNode head) {
        ListNode ret = new ListNode(0, head);
        int len = 0;
        ListNode n = head;
        while (n != null) {
            n = n.next;
            len++;
        }
        int cnt = 1;
        ListNode d1, d2;
        while (cnt < len) {
            d1 = ret;
            d2 = ret;
            while (d1.next != null && d2.next != null) {
                //计算d2，找到归并排序的第二个链表的head。
                int c1 = 0, c2 = 0;
                while (c2 < cnt && d2.next != null) {
                    d2 = d2.next;
                    c2++;
                }
                //归并排序h1和h2
                c2 = 0;
                while (d1.next != null && d2.next != null && c1 < cnt && c2 < cnt) {
                    if (d1.next.val <= d2.next.val) {
                        d1 = d1.next;
                        c1++;
                    } else {
                        //缓存d2的下一轮循环的值
                        ListNode t = d2.next.next;
                        //d2插入到d1中
                        d2.next.next = d1.next;
                        d1.next = d2.next;
                        d2.next = t;

                        //重新计算下一轮的d1和d2
                        d1 = d1.next;
                        c2++;
                    }
                }
                //未排序的部分处理
                while (c1 < cnt && d1.next != null) {
                    d1 = d1.next;
                    c1++;
                }
                while (c2 < cnt && d2.next != null) {
                    d2 = d2.next;
                    c2++;
                }

                //重新计算下一次的h1和h2
                d1 = d2;
            }

            cnt *= 2;
        }
        return ret.next;
    }

    /**
     * 常规方法见：Insertion_Sort_List_147
     *
     * 验证通过:
     * Runtime: 6 ms, faster than 84.96% of Java online submissions for Sort List.
     * Memory Usage: 47.4 MB, less than 54.85% of Java online submissions for Sort List.
     *
     * @param head
     * @return
     */
    public static ListNode sortList_1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dumb = new ListNode();
        dumb.next = head;
        ListNode p = dumb;
        ListNode q = dumb;
        //获得中间节点
        while (q != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        //断开前后两部分
        ListNode halfHead = p.next;
        p.next = null;
        //分别排序
        p = sortList(head);
        q = sortList(halfHead);
        //确定p,q中第一个元素中较小的为p，较大大的为q
        ListNode ret = new ListNode();
        if (q != null && p.val > q.val) {
            ListNode t = q;
            q = p;
            p = t;
        }
        ret.next = p;
        //合并排好序的两个部分
        while (p.next != null && q != null) {
            while (p.next != null && p.next.val < q.val) {
                p = p.next;
            }
            ListNode t = q;
            q = q.next;
            t.next = p.next;
            p.next = t;
            p = t;
        }
        while (p.next != null) {
            p = p.next;
        }
        p.next = q;
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{4, 2, 3}, new int[]{2, 3, 4});
        do_func(new int[]{1, 2, 4}, new int[]{1, 2, 4});
        do_func(new int[]{4, 2, 1, 3}, new int[]{1, 2, 3, 4});
        do_func(new int[]{1, 2, 3, 4}, new int[]{1, 2, 3, 4});
        do_func(new int[]{-1, 5, 3, 4, 0}, new int[]{-1, 0, 3, 4, 5});
        do_func(new int[]{}, new int[]{});

    }

    private static void do_func(int[] l1, int[] expected) {
        ListNode ret = sortList(ListNode.fromArray(l1));
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
