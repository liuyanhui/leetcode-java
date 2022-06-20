package leetcode;

/**
 * 这里有金矿
 * 160. Intersection of Two Linked Lists
 * Easy
 * --------------
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.
 *
 * For example, the following two linked lists begin to intersect at node c1:
 * ...
 * The test cases are generated such that there are no cycles anywhere in the entire linked structure.
 *
 * Note that the linked lists must retain their original structure after the function returns.
 * Custom Judge:
 * The inputs to the judge are given as follows (your program is not given these inputs):
 * intersectVal - The value of the node where the intersection occurs. This is 0 if there is no intersected node.
 * listA - The first linked list.
 * listB - The second linked list.
 * skipA - The number of nodes to skip ahead in listA (starting from the head) to get to the intersected node.
 * skipB - The number of nodes to skip ahead in listB (starting from the head) to get to the intersected node.
 * The judge will then create the linked structure based on these inputs and pass the two heads, headA and headB to your program. If you correctly return the intersected node, then your solution will be accepted.
 *
 * Example 1:
 * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * Output: Intersected at '8'
 * Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
 *
 * Example 2:
 * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * Output: Intersected at '2'
 * Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect).
 * From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
 *
 * Example 3:
 * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * Output: No intersection
 * Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
 * Explanation: The two lists do not intersect, so return null.
 *
 * Constraints:
 * The number of nodes of listA is in the m.
 * The number of nodes of listB is in the n.
 * 1 <= m, n <= 3 * 10^4
 * 1 <= Node.val <= 10^5
 * 0 <= skipA < m
 * 0 <= skipB < n
 * intersectVal is 0 if listA and listB do not intersect.
 * intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.
 *
 * Follow up: Could you write a solution that runs in O(m + n) time and use only O(1) memory?
 */
public class Intersection_of_Two_Linked_Lists_160 {

    /**
     * round 2
     *
     * 思路1
     * 因为要用到判断节点是否相同，所以可以利用哈希表。
     * 时间复杂度O(M+N)，空间复杂度O(N)
     *
     * 思路2
     * 遍历2次，交叉遍历。第2次遍历时遇到相等的节点，就是交叉节点。
     * 时间复杂度O(M+N)，空间复杂度O(1)
     *
     * 采用思路2
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 53.44% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 56.2 MB, less than 6.78% of Java online submissions for Intersection of Two Linked Lists.
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode_3(ListNode headA, ListNode headB) {
        int roundA = 1, roundB = 1;
        ListNode ta = headA, tb = headB;
        while (ta != null && tb != null && roundA * roundB <= 4) {
            if (ta == tb) {
                return ta;
            }
            ta = ta.next;
            tb = tb.next;
            if (ta == null) {
                ta = headB;
                roundA++;
            }
            if (tb == null) {
                tb = headA;
                roundB++;
            }
        }
        return null;
    }

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
