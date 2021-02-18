package leetcode;

/**
 * 这里有金矿
 * 160. Intersection of Two Linked Lists
 * Easy
 * --------------
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 *
 * For example, the following two linked lists:
 * begin to intersect at node c1.
 *
 * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Reference of the node with value = 8
 * Input Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 *
 * Example 2:
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Reference of the node with value = 2
 * Input Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect). From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
 *
 * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: null
 * Input Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 *
 * Notes:
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Each value on each linked list is in the range [1, 10^9].
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */
public class Intersection_of_Two_Linked_Lists_160 {

    /**
     * getIntersectionNode_1的精简版
     * 参考思路：https://leetcode.com/problems/intersection-of-two-linked-lists/solution/
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode_2(ListNode headA, ListNode headB) {
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
        // Note: In the case lists do not intersect, the pointers for A and B
        // will still line up in the 2nd iteration, just that here won't be
        // a common node down the list and both will reach their respective ends
        // at the same time. So pA will be NULL in that case.
    }

    /**
     * 思路：
     * 1.假设两个链表了l1和l2的长度分别为m和n，其中m>n
     * 2.同时从头开始遍历两个链表，l2必然先遍历到结尾(因为m>n)。l2紧接着从l1的head开始遍历。
     * 3.当l1遍历到结尾时，l1接着从l2开始遍历。
     * 4.遍历过程中节点相同时返回即可。遍历l1和l2第二次均到达结尾时，表示没有交点。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 97.80% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 41.9 MB, less than 60.84% of Java online submissions for Intersection of Two Linked Lists.
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode_1(ListNode headA, ListNode headB) {
        //有一个为空，即返回null
        if (headA == null || headB == null) {
            return null;
        }
        ListNode ha = headA, hb = headB;
        int round = 1;
        while (round < 4) {
            while (ha != null && hb != null) {
                if (ha == hb) {
                    return ha;
                }
                ha = ha.next;
                hb = hb.next;
            }
            //beg
            if (ha == null) {
                ha = headB;
            }
            if (hb == null) {
                hb = headA;
            }
            //end
            //上面的代码可以用下面的替换
            /*if(ha==null && hb==null){
                return null;
            }else{
                ha=ha==null?headB:ha;
                hb=hb==null?headA:hb;
            }*/
            round++;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(null == null);
    }
}
