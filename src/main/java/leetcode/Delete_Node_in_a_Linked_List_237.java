package leetcode;

/**
 * 237. Delete Node in a Linked List
 * Medium
 * --------------------------
 * There is a singly-linked list head and we want to delete a node node in it.
 *
 * You are given the node to be deleted node. You will not be given access to the first node of head.
 *
 * All the values of the linked list are unique, and it is guaranteed that the given node node is not the last node in the linked list.
 *
 * Delete the given node. Note that by deleting the node, we do not mean removing it from memory. We mean:
 * The value of the given node should not exist in the linked list.
 * The number of nodes in the linked list should decrease by one.
 * All the values before node should be in the same order.
 * All the values after node should be in the same order.
 *
 * Custom testing:
 * For the input, you should provide the entire linked list head and the node to be given node. node should not be the last node of the list and should be an actual node in the list.
 * We will build the linked list and pass the node to your function.
 * The output will be the entire list after calling your function.
 *
 * Example 1:
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling your function.
 *
 * Example 2:
 * Input: head = [4,5,1,9], node = 1
 * Output: [4,5,9]
 * Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function.
 *
 * Constraints:
 * The number of the nodes in the given list is in the range [2, 1000].
 * -1000 <= Node.val <= 1000
 * The value of each node in the list is unique.
 * The node to be deleted is in the list and is not a tail node.
 */
public class Delete_Node_in_a_Linked_List_237 {
    public void deleteNode(ListNode node) {
        deleteNode_1(node);
    }

    /**
     * round 2
     * 思考：
     * 1.无法获取node的上游节点
     * 2.直接改变node的value，而不删除node。其后继所有节点的值前移，并删除尾节点。
     * 3.题目明确说明了，The value of the given node should not exsit inthe linked list ，就是说只要node.val不存在就行了。这也说明了"The value of each node in the list is unique"的意义所在。
     *
     * 验证通过：不如deleteNode_1()优雅
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Delete Node in a Linked List.
     * Memory Usage: 44 MB, less than 44.99% of Java online submissions for Delete Node in a Linked List.
     *
     * @param node
     */
    public void deleteNode_2(ListNode node) {
        ListNode t = null;
        while (node.next != null) {
            t = node;
            node.val = node.next.val;
            node = node.next;
        }
        t.next = null;
    }

    /**
     * review
     * 参考思路：
     * https://leetcode.com/problems/delete-node-in-a-linked-list/discuss/65455/1-3-lines-C%2B%2BJavaPythonCCJavaScriptRuby
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Delete Node in a Linked List.
     * Memory Usage: 38.5 MB, less than 35.72% of Java online submissions for Delete Node in a Linked List.
     *
     * @param node
     */
    public static void deleteNode_1(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
