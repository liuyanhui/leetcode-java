package leetcode;

/**
 * 24. Swap Nodes in Pairs
 * Medium
 * ----------------------------
 * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
 *
 * Example 1:
 * Input: head = [1,2,3,4]
 * Output: [2,1,4,3]
 *
 * Example 2:
 * Input: head = []
 * Output: []
 *
 * Example 3:
 * Input: head = [1]
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 100].
 * 0 <= Node.val <= 100
 */
public class Swap_Nodes_in_Pairs_24 {
    public static ListNode swapPairs(ListNode head) {
        return swapPairs_3(head);
    }

    /**
     * 递归
     * 参考思路：
     * https://leetcode.com/problems/swap-nodes-in-pairs/discuss/11030/My-accepted-java-code.-used-recursion.
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs_3(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode n = head.next;
        head.next = swapPairs(head.next.next);
        n.next = head;
        return n;
    }

    /**
     * swapPairs_1的代码优化版
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00%.
     * Memory Usage: 38.8 MB, less than 6.15% .
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs_2(ListNode head) {
        ListNode ret = new ListNode();
        ListNode cur = ret;
        while (head != null) {
            //如果为了优化内存使用情况，可以把odd和even变量去掉该位直接使用head变量
            ListNode even = null;
            ListNode odd = head;
            head = head.next;
            if (head != null) {
                even = head;
                head = head.next;
            }
            if (even != null) {
                cur.next = even;
                cur = cur.next;
            }
            cur.next = odd;
            cur = cur.next;
        }
        cur.next = null;
        return ret.next;
    }

    /**
     * 1.分别按奇偶提取节点
     * 2.按照偶奇顺序合并两个列表
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java .
     * Memory Usage: 38.7 MB, less than 16.15% of Java
     * @param head
     * @return
     */
    public static ListNode swapPairs_1(ListNode head) {
        ListNode odd = new ListNode();
        ListNode even = new ListNode();
        ListNode curOdd = odd, curEven = even;
        //分离奇偶节点
        while (head != null) {
            curOdd.next = head;
            curOdd = curOdd.next;
            head = head.next;
            if (head != null) {
                curEven.next = head;
                curEven = curEven.next;
                head = head.next;
            }
        }
        //列表结尾要设置为null，防止下一步死循环
        curOdd.next = null;
        curEven.next = null;
        //去掉哑节点
        odd = odd.next;
        even = even.next;
        ListNode ret = new ListNode();
        ListNode cur = ret;
        //合并
        while (even != null && odd != null) {
            cur.next = even;
            cur = cur.next;
            even = even.next;
            cur.next = odd;
            cur = cur.next;
            odd = odd.next;
        }
        if (odd != null) {
            cur.next = odd;
        }
        return ret.next;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4}, new int[]{2, 1, 4, 3});
        do_func(new int[]{}, new int[]{});
        do_func(new int[]{1}, new int[]{1});
    }

    private static void do_func(int[] head, int[] expected) {
        ListNode ret = swapPairs(ListNode.fromArray(head));
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
