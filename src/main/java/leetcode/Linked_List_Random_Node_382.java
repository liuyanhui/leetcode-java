package leetcode;

import java.util.Random;

/**
 * 382. Linked List Random Node
 * Medium
 * ------------------
 * Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.
 *
 * Implement the Solution class:
 * Solution(ListNode head) Initializes the object with the integer array nums.
 * int getRandom() Chooses a node randomly from the list and returns its value. All the nodes of the list should be equally likely to be choosen.
 *
 * Example 1:
 * Input
 * ["Solution", "getRandom", "getRandom", "getRandom", "getRandom", "getRandom"]
 * [[[1, 2, 3]], [], [], [], [], []]
 * Output
 * [null, 1, 3, 2, 2, 3]
 *
 * Explanation
 * Solution solution = new Solution([1, 2, 3]);
 * solution.getRandom(); // return 1
 * solution.getRandom(); // return 3
 * solution.getRandom(); // return 2
 * solution.getRandom(); // return 2
 * solution.getRandom(); // return 3
 * // getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
 *
 * Constraints:
 * The number of nodes in the linked list will be in the range [1, 10^4].
 * -10^4 <= Node.val <= 10^4
 * At most 10^4 calls will be made to getRandom.
 *
 * Follow up:
 * What if the linked list is extremely large and its length is unknown to you?
 * Could you solve this efficiently without using extra space?
 */
public class Linked_List_Random_Node_382 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    /**
     * review round 2
     */

    /**
     * 套路
     * Reservoir Sampling问题。
     * 给定一个数据流，数据流长度N很大，且N直到处理完所有数据之前都不可知，请问如何在只遍历一遍数据（O(N)）的情况下，能够随机选取出m个不重复的数据。
     *
     * 参考思路：
     * https://zhuanlan.zhihu.com/p/29178293
     * https://www.jianshu.com/p/7a9ea6ece2af
     *
     * 验证通过：
     * Runtime: 16 ms, faster than 36.04% of Java online submissions for Linked List Random Node.
     * Memory Usage: 41.5 MB, less than 31.98% of Java online submissions for Linked List Random Node.
     *
     */
    static class Solution_2 {
        ListNode head;

        /** @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node. */
        public Solution_2(ListNode head) {
            this.head = head;
        }

        /** Returns a random node's value. */
        public int getRandom() {
            int idx = 1;
            int ret = head.val;
            ListNode cur = head.next;
            Random random = new Random();
            while (cur != null) {
                if (random.nextInt(idx + 1) < 1) {
                    ret = cur.val;
                }
                idx++;
                cur = cur.next;
            }
            return ret;
        }
    }

    /**
     * 验证成功：
     * Runtime: 16 ms, faster than 36.04% of Java online submissions for Linked List Random Node.
     * Memory Usage: 47.1 MB, less than 20.40% of Java online submissions for Linked List Random Node.
     *
     */
    static class Solution {
        ListNode head;
        int size = 0;
        Random random = new Random();

        /** @param head The linked list's head.
        Note that the head is guaranteed to be not null, so it contains at least one node. */
        public Solution(ListNode head) {
            this.head = head;
            ListNode cur = head;
            while (cur != null) {
                size++;
                cur = cur.next;
            }
        }

        /** Returns a random node's value. */
        public int getRandom() {
            int r = random.nextInt(size);
            ListNode cur = head;
            while (r > 0) {
                cur = cur.next;
                r--;
            }
            return cur.val;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution(ListNode.fromArray(new int[]{1, 2, 3}));
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
        System.out.println(solution.getRandom());
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */

}