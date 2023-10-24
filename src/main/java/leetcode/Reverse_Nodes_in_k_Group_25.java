package leetcode;

/**
 * 这里有金矿。重要的是，手撕解题过程。必须在稿纸上画出列表变化过程中的每个变量的变化过程。
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * 25. Reverse Nodes in k-Group
 * Hard
 * -----------------
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * Follow up:
 * Could you solve the problem in O(1) extra memory space?
 * You may not alter the values in the list's nodes, only nodes itself may be changed.
 *
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 *
 * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 *
 * Example 3:
 * Input: head = [1,2,3,4,5], k = 1
 * Output: [1,2,3,4,5]
 *
 * Example 4:
 * Input: head = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is in the range sz.
 * 1 <= sz <= 5000
 * 0 <= Node.val <= 1000
 * 1 <= k <= sz
 */
public class Reverse_Nodes_in_k_Group_25 {
    public static ListNode reverseKGroup(ListNode head, int k) {
        return reverseKGroup_5(head, k);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * review 不难，但是比较绕
     *
     * Thinking：
     * 1.链表分为3部分：已经计算的(h1,t1)，正在计算的(h2,t2)，未计算的(h3,t3)。
     * 1.1. f(h2,k)负责反转链表，并且执行h2.next=h3，返回值为新的头节点t2。t1.next = t2;
     * 1.2. 持续调用f(h2.next,k)
     * 2.预先判断未计算部分(h3,t3)链表的节点数量是否大于k
     *
     * 验证通过：
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup_5(ListNode head, int k) {
        ListNode h1 = new ListNode();
        ListNode t1 = h1;
        //链表中节点数量是否大于等于k
        int cnt = 1;
        ListNode cur = head;
        while (cur != null && cnt < k) {
            cur = cur.next;
            cnt++;
        }
        //如果数量不满足条件，不执行反转操作
        if (cnt < k || cur == null) return head;
        //执行反转操作
        ListNode h2 = head;
        ListNode t2 = h2;
        ListNode h3 = t2.next;//未处理部分的头节点
        cnt = 1;
        while (cnt < k) {
            ListNode t = h3.next;
            h3.next = h2;
            h2 = h3;
            h3 = t;
            cnt++;
        }
        h1.next = h2;
        t1 = t2;
        t1.next = h3;
        //递归计算剩余的链表
        t1.next = reverseKGroup(h3, k);

        return h2;
    }

    /**
     * round2
     * //迭代思路：
     * 1.提取k个节点，并记录第k+1个节点为下一次的头节点
     * 2.reverse这k个节点的列表
     * 3.将reversed列表合并到总列表中，并记录尾节点
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 35.40%.
     * Memory Usage: 42.6 MB, less than 25.91%.
     *
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup_4(ListNode head, int k) {
        ListNode ret = new ListNode(0, head);
        ListNode newTail = ret;
        int i = 1;
        ListNode beg = head, end = head, n = null;
        while (end != null) {
            //提取beg和end
            while (i < k && end != null) {
                end = end.next;
                i++;
            }
            if (i == k && end != null) {
                n = end.next;
                //反转
                ListNode c = beg, h = null;
                while (c != n) {
                    ListNode tmp = c.next;
                    c.next = h;
                    h = c;
                    c = tmp;
                }
                //合并
                newTail.next = end;
                newTail = beg;
                beg.next = n;
            }
            //重置下一次初始条件
            beg = n;
            end = n;
            n = null;
            i = 1;
        }

        return ret.next;
    }

    /**
     * round2
     * 递归法：每次递归都reverse列表中的前k个节点
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00%.
     * Memory Usage: 42.6 MB, less than 22.32%
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup_3(ListNode head, int k) {
        ListNode ret = new ListNode(0, head);
        int i = 0;
        ListNode tail = ret;
        //提取k个节点
        while (i < k && tail != null) {
            tail = tail.next;
            i++;
        }
        if (tail != null) {
            ListNode nextHead = tail.next;
            //reverse
            ListNode h = null, c = head;
            while (c != nextHead) {
                ListNode t = c.next;
                c.next = h;
                h = c;
                c = t;
            }
            ret.next = tail;
            //recursive
            head.next = reverseKGroup(nextHead, k);
        }
        return ret.next;
    }

    /**
     *
     * reverseKGroup_1的改进版，分为两步：reverse和join
     * 参考思路：
     * https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11440/Non-recursive-Java-solution-and-idea
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Nodes in k-Group.
     * Memory Usage: 39.2 MB, less than 54.03% of Java online submissions for Reverse Nodes in k-Group.
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup_2(ListNode head, int k) {
        if (head == null) return null;
        ListNode ret = new ListNode();
        ret.next = head;
        ListNode beg = head;
        ListNode end = beg;
        ListNode lastEnd = ret;
        int i = 1;
        while (i < k && end != null) {
            end = end.next;
            i++;
            if (i == k && end != null) {
                ListNode tmpBeg = reverse_2(beg, k);
                lastEnd.next = tmpBeg;
                lastEnd = beg;
                end = beg.next;
                beg = beg.next;
                i = 1;
            }
        }

        return ret.next;
    }

    private static ListNode reverse_2(ListNode beg, int k) {
        ListNode head = beg, current = beg;
        int i = 1;
        while (i < k) {
            ListNode tmp = current.next;
            current.next = current.next.next;
            tmp.next = head;
            head = tmp;
            i++;
        }
        return head;
    }

    /**
     * 思路：（每次reverse节点操作包含reverse和join两个动作，即反转和连接）
     * 1.先定位每次reverse的beg和end节点
     * 2.从beg.next开始依次reverse每个节点
     * 2.重置beg和end
     *
     * To be improved：解决问题耗时较长，不应该。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Nodes in k-Group.
     * Memory Usage: 39.5 MB, less than 31.44% of Java online submissions for Reverse Nodes in k-Group.
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup_1(ListNode head, int k) {
        ListNode ret = new ListNode();
        ret.next = head;
        ListNode beg = head;
        ListNode end = head;
        ListNode lastEnd = ret;
        int i = 1;
        while (end != null) {
            if (i < k) {//先定位revers的end
                end = end.next;
                i++;
            } else {//确定end后，执行reverse和join操作
                //reverse
                int j = 1;
                while (j < k) {
                    //节点反转reverse
                    ListNode tmp = beg.next;
                    beg.next = beg.next.next;
                    tmp.next = lastEnd.next;
                    //join回链表
                    lastEnd.next = tmp;
                    j++;
                }

                //reset beg and end
                lastEnd = beg;
                beg = beg.next;
                end = beg;

                i = 1;
            }
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5}, 2, new int[]{2, 1, 4, 3, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 3, new int[]{3, 2, 1, 4, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 9, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{1, 2, 3, 4, 5}, 1, new int[]{1, 2, 3, 4, 5});
        do_func(new int[]{1}, 1, new int[]{1});
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 3, new int[]{3, 2, 1, 6, 5, 4, 7, 8});
    }

    private static void do_func(int[] input, int k, int[] expected) {
        ListNode ret = reverseKGroup(ListNode.fromArray(input), k);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
