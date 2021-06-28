package leetcode;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 * 23. Merge k Sorted Lists
 * Hard
 * ---------------
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 *
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 * Input: lists = [[]]
 * Output: []
 *
 * Constraints:
 * k == lists.length
 * 0 <= k <= 10^4
 * 0 <= lists[i].length <= 500
 * -10^4 <= lists[i][j] <= 10^4
 * lists[i] is sorted in ascending order.
 * The sum of lists[i].length won't exceed 10^4.
 */
public class Merge_k_Sorted_Lists_23 {
    public static ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists_3(lists);
    }

    /**
     * 直接使用PriorityQueue
     * 参考：https://leetcode.com/problems/merge-k-sorted-lists/solution/ 之Approach 3
     * 验证通过：
     * Runtime: 5 ms, faster than 44.64% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 40.5 MB, less than 78.80% of Java online submissions for Merge k Sorted Lists.
     * @param lists
     * @return
     */
    public static ListNode mergeKLists_3(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        ListNode head = new ListNode();
        ListNode point = head;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < lists.length; i++) {
            ListNode node = lists[i];
            while (node != null) {
                priorityQueue.add(node.val);
                node = node.next;
            }
        }
        while (priorityQueue.size() > 0) {
            point.next = new ListNode(priorityQueue.poll());
            point = point.next;
        }

        return head.next;
    }

    /**
     * mergeKLists_1的优化版，问题转化为两个有序列表排序合并问题
     * 验证通过：
     * Runtime: 104 ms, faster than 14.58% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 40.7 MB, less than 59.54% of Java online submissions for Merge k Sorted Lists.
     * @param lists
     * @return
     */
    public static ListNode mergeKLists_2(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        ListNode merged = null;
        for (int i = 0; i < lists.length; i++) {
            ListNode newMergedHead = new ListNode();
            ListNode newMergedPoint = newMergedHead;
            ListNode tmpNode = lists[i];
            while (merged != null && tmpNode != null) {
                if (merged.val < tmpNode.val) {
                    newMergedPoint.next = merged;
                    merged = merged.next;
                } else {
                    newMergedPoint.next = tmpNode;
                    tmpNode = tmpNode.next;
                }
                newMergedPoint = newMergedPoint.next;
            }
            if (tmpNode != null) {
                newMergedPoint.next = tmpNode;
            }
            if (merged != null) {
                newMergedPoint.next = merged;
            }
            merged = newMergedHead.next;
        }
        return merged;
    }

    /**
     * 思路：依次把第0行和第i行进行合并，遍历结束后返回第0行。
     * 注意：实现有些复杂，列表中的节点是可以复用的，无需考虑合并到第0行的情况。直接合并到新的一行实现更简单。
     * Time Complexity:O(kN)
     * 验证通过：
     * Runtime: 99 ms, faster than 15.88% of Java online submissions for Merge k Sorted Lists.
     * Memory Usage: 41 MB, less than 25.38% of Java online submissions for Merge k Sorted Lists.
     * @param lists
     * @return
     */
    public static ListNode mergeKLists_1(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        //从第1个listNode开始依次与第0个listNode合并
        for (int i = 1; i < lists.length; i++) {
            //第0行的节点
            ListNode zeroLinePoint = new ListNode();
            zeroLinePoint.next = lists[0];
            //这里是必须的，否则list[0]会是错误的。因为存在lists[0]的第一个节点被替换的情况。
            ListNode zeroHead = zeroLinePoint;
            //第i行的头结点,这个节点是固定不变的。因为节点最后都会在第0行中
            ListNode otherLineHead = new ListNode();
            otherLineHead.next = lists[i];
            while (zeroLinePoint.next != null && otherLineHead.next != null) {
                if (zeroLinePoint.next.val < otherLineHead.next.val) {
                    //第0行的元素比较小时，只后移，无需插入节点
                    zeroLinePoint = zeroLinePoint.next;
                } else {
                    //先断开第i行的节点
                    ListNode tmp = otherLineHead.next;
                    //节点删除
                    otherLineHead.next = otherLineHead.next.next;
                    //把断开的节点插入第0行中
                    tmp.next = zeroLinePoint.next;
                    zeroLinePoint.next = tmp;
                    //第0行节点后移。注意：这里只能后移一位，而不是两位
                    zeroLinePoint = zeroLinePoint.next;
                }
            }
            if (otherLineHead.next != null) {
                zeroLinePoint.next = otherLineHead.next;
            }
            //这里是必须的，否则list[0]会是错误的。因为存在lists[0]的第一个节点被替换的情况。
            lists[0] = zeroHead.next;
        }
        return lists[0];
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 4, 5}, {1, 3, 4}, {2, 6}}, new int[]{1, 1, 2, 3, 4, 4, 5, 6});
        do_func(new int[][]{}, new int[]{});
        do_func(new int[][]{{}}, new int[]{});
        do_func(new int[][]{{-1, 5, 11}, {6, 10}}, new int[]{-1, 5, 6, 10, 11});
        do_func(new int[][]{{}, {-1, 5, 11}, {}, {6, 10}}, new int[]{-1, 5, 6, 10, 11});
    }

    private static void do_func(int[][] intput, int[] expected) {
        ListNode[] listNodeArray = new ListNode[intput.length];
        for (int i = 0; i < intput.length; i++) {
            ListNode head = new ListNode();
            ListNode curNode = head;
            for (int j = 0; j < intput[i].length; j++) {
                curNode.next = new ListNode(intput[i][j]);
                curNode = curNode.next;
            }
            listNodeArray[i] = head.next;
        }
        ListNode ret = mergeKLists(listNodeArray);
        System.out.println(ret);
        System.out.println(ListNode.isSame(ret, expected));
        System.out.println("--------------");
    }
}
