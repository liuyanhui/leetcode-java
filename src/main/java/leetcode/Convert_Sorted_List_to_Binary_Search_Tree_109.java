package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 109. Convert Sorted List to Binary Search Tree
 * Medium
 * -----------------
 * Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 *
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 *
 * Example 1:
 * Input: head = [-10,-3,0,5,9]
 * Output: [0,-3,9,-10,null,5]
 * Explanation: One possible answer is [0,-3,9,-10,null,5], which represents the shown height balanced BST.
 *
 * Example 2:
 * Input: head = []
 * Output: []
 *
 * Example 3:
 * Input: head = [0]
 * Output: [0]
 *
 * Example 4:
 * Input: head = [1,3]
 * Output: [3,1]
 *
 * Constraints:
 * The number of nodes in head is in the range [0, 2 * 10^4].
 * -10^5 <= Node.val <= 10^5
 */
public class Convert_Sorted_List_to_Binary_Search_Tree_109 {

    public TreeNode sortedListToBST(ListNode head) {
        return sortedListToBST_1(head);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking：
     * 1. 递归法。通过快慢指针法找到root节点，然后递归。
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 43.98 MB Beats 99.44% of users with Java
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST_5(ListNode head) {
        if (head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        //先找到root节点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //截断左右子树的链表
        ListNode cur = head;
        while (cur != slow && cur.next != slow) {
            cur = cur.next;
        }
        cur.next = null;//截断左子树的链表
        TreeNode root = new TreeNode(slow.val);
        // slow.next = null;//截断右子树的链表 review 这里不需要
        if (head != slow)//review 这里很巧妙
            root.left = sortedListToBST(head);
        root.right = sortedListToBST(slow.next);
        return root;
    }


    /**
     * 套路
     * 一个更巧妙的方案，性能更好，更简洁
     * 巧妙的使用了左闭右开的方式作为递归时的输入参数。免去了边界值和中间遍历的使用。
     * 跟sortedListToBST_3()是一个很好的比较。
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST_4(ListNode head) {
        if (head == null) {
            return null;
        }
        return sort(head, null);//左闭右开
    }

    //输入参数是左闭右开，节省了其他中间变量的使用。
    public TreeNode sort(ListNode head, ListNode tail) {
        ListNode slow = head;
        ListNode fast = head;
        //这里fast跟tail比较，无需跟null比较
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (head == tail) return null;//review 这里很巧妙
        TreeNode newNode = new TreeNode(slow.val);
        newNode.left = sort(head, slow);//左闭右开
        newNode.right = sort(slow.next, tail);//左闭右开
        return newNode;
    }

    /**
     * 跟sortedListToBST_2类似的思路，只不过没有采用把链表转换成数组的方式。而是每次递归直接在链表中查找根节点，并分割左右子树。
     * 验证通过：
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST_3(ListNode head) {
        if (head == null) return null;
        ListNode dumb = new ListNode(Integer.MIN_VALUE, head);
        ListNode fast = dumb;
        ListNode slow = dumb;
        ListNode leftTail = null;
        //先找到中间节点作为根节点
        while (fast.next != null) {
            fast = fast.next;
            if (fast.next != null) fast = fast.next;
            leftTail = slow;
            slow = slow.next;
        }
        //截断链表，防止递归时重复计算右子树
        leftTail.next = null;
        ListNode rightHead = slow.next;
        slow.next = null;
        //构造BST
        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST(dumb.next);
        root.right = sortedListToBST(rightHead);
        return root;
    }

    /**
     * 递归法，
     * 1.先得到中间节点mid，mid=(beg + end) / 2 ，mid节点为root
     * 2.mid左边的节点为左子树，mid右边的节点为右子树
     * 3.递归执行步骤1,2
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 43.98% of Java online submissions for Convert Sorted List to Binary Search Tree.
     * Memory Usage: 40.3 MB, less than 29.08% of Java online submissions for Convert Sorted List to Binary Search Tree.
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST_2(ListNode head) {
        //先把链表转换成数组
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        return do_recursive(list, 0, list.size() - 1);
    }

    private TreeNode do_recursive(List<ListNode> list, int beg, int end) {
        if (beg > end) return null;
        if (beg == end) return new TreeNode(list.get(beg).val);

        int mid = (beg + end) / 2;
        TreeNode root = new TreeNode(list.get(mid).val);
        root.left = do_recursive(list, beg, mid - 1);
        root.right = do_recursive(list, mid + 1, end);

        return root;
    }

    /**
     * 验证失败，用例为[0,1,2,3,4,5]
     * 本来是想用一种投机取巧的办法，然鹅，不满足买个子树也必须是height balanced BST的要求。
     * 还得老老实实得用递归法。
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST_1(ListNode head) {
        if (head == null) return null;
        TreeNode root = null;
        //先左后右
        ListNode slow = head;
        ListNode fast = head;
        TreeNode t = null;
        //先把左半边的元素组成只有左子节点的树
        while (fast != null && fast.next != null) {
            t = new TreeNode(slow.val);
            t.left = root;
            root = t;

            slow = slow.next;
            fast = fast.next.next;
        }
        //处理根节点
        t = new TreeNode(slow.val);
        t.left = root;
        root = t;
        slow = slow.next;
        //再把右半边的元素组成只有右子节点的树
        TreeNode cur = root;
        while (slow != null) {
            t = new TreeNode(slow.val);
            cur.right = t;
            cur = t;

            slow = slow.next;
        }
        return root;
    }
}
