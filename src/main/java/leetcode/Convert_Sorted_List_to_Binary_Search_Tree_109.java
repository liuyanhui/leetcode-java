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
 * The number of nodes in head is in the range [0, 2 * 104].
 * -10^5 <= Node.val <= 10^5
 */
public class Convert_Sorted_List_to_Binary_Search_Tree_109 {

    public TreeNode sortedListToBST(ListNode head) {
        return sortedListToBST_1(head);
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
