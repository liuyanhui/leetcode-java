package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 173. Binary Search Tree Iterator
 * Medium
 * ------------------
 * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):
 *
 * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
 * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
 * int next() Moves the pointer to the right, then returns the number at the pointer.
 * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST.
 *
 * You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order traversal when next() is called.
 *
 * Example 1:
 * Input
 * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
 * Output
 * [null, 3, 7, true, 9, true, 15, true, 20, false]
 *
 * Explanation
 * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
 * bSTIterator.next();    // return 3
 * bSTIterator.next();    // return 7
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 9
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 15
 * bSTIterator.hasNext(); // return True
 * bSTIterator.next();    // return 20
 * bSTIterator.hasNext(); // return False
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 105].
 * 0 <= Node.val <= 106
 * At most 105 calls will be made to hasNext, and next.
 *
 * Follow up:
 * Could you implement next() and hasNext() to run in average O(1) time and use O(h) memory, where h is the height of the tree?
 */
public class Binary_Search_Tree_Iterator_173 {
    /**
     * 验证通过。
     * Runtime: 14 ms, faster than 99.14% of Java online submissions for Binary Search Tree Iterator.
     * Memory Usage: 42.7 MB, less than 33.24% of Java online submissions for Binary Search Tree Iterator.
     */
    class BSTIterator {

        //TODO 不太明白"in average O(1) time and use O(h) memory"是啥意思
        //TODO 应该是要充分利用BST的特性，下面的这个实现没有使用BST的特性
        private List<TreeNode> list = new ArrayList<>();
        private int cur = 0;

        public BSTIterator(TreeNode root) {
            convertInOrder(root);
        }

        public int next() {
            return list.get(cur++).val;
        }

        public boolean hasNext() {
            return cur < list.size();
        }

        private void convertInOrder(TreeNode node) {
            if (node == null) return;
            convertInOrder(node.left);
            list.add(node);
            convertInOrder(node.right);
        }
    }

    /**
     * 这里有金矿
     * 很巧妙的利用了stack，对tree遍历：（这里是先序遍历思路，本质上是用stack实现了递归过程）
     * 1.初始化stack，n和n.left入栈。
     * 2.next()方法，循环操作stack直到stack为空
     * 2.1. n=pop()，操作n 。注：这里的隐含条件是n.left为空或者已经被操作过了，所以无需考虑n.left，只需操作n即可。
     * 2.2. push(n.right)
     *
     * 参考思路：
     * https://leetcode.com/problems/binary-search-tree-iterator/discuss/52525/My-solutions-in-3-languages-with-Stack
     */
    class BSTIterator2 {
        private Stack<TreeNode> stack = new Stack<TreeNode>();

        public BSTIterator2(TreeNode root) {
            pushAll(root);
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /** @return the next smallest number */
        public int next() {
            TreeNode tmpNode = stack.pop();
            pushAll(tmpNode.right);
            return tmpNode.val;
        }

        private void pushAll(TreeNode node) {
            for (; node != null; stack.push(node), node = node.left) ;
        }
    }

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
}
