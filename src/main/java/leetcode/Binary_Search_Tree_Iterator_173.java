package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 173. Binary Search Tree Iterator
 * Medium
 * ------------------
 * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST):
 * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
 * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false.
 * int next() Moves the pointer to the right, then returns the number at the pointer.
 * <p>
 * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST.
 * <p>
 * You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order traversal when next() is called.
 * <p>
 * Example 1:
 * Input
 * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
 * Output
 * [null, 3, 7, true, 9, true, 15, true, 20, false]
 * <p>
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
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [1, 10^5].
 * 0 <= Node.val <= 10^6
 * At most 10^5 calls will be made to hasNext, and next.
 * <p>
 * Follow up:
 * Could you implement next() and hasNext() to run in average O(1) time and use O(h) memory, where h is the height of the tree?
 */
public class Binary_Search_Tree_Iterator_173 {

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * 把输入根据in-order转换为排序数组，基于排序数组进行遍历。next()和hasNext()的时间复杂度O(1),空间复杂度为O(N)。初始化的时间复杂度为O(N)。
     * 或
     * 每次遍历都采用dfs或bfs从root开始计算。next()和hasNext()的时间复杂度为O(N)，空间复杂度O(N) 。初始化的时间复杂度为O(1)。
     * 2. 利用Stack缓存in-order遍历时当前子树的下一个节点。当前节点遍历完成时，再计算栈顶元素。
     * 本质上是再空间复杂度为O(h)的情况下遍历binary search tree
     * <p>
     * 验证通过：
     * Runtime 17 ms Beats 63.76%
     * Memory 47.57 MB Beats 98.53%
     *
     */
    class BSTIterator_r3_1 {
        Stack<TreeNode> stack;

        public BSTIterator_r3_1(TreeNode root) {
            stack = new Stack<>();
            op_stack(root);
        }

        public int next() {
            TreeNode n = stack.pop();
            op_stack(n.right);
            return n.val;
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        private void op_stack(TreeNode node) {
            TreeNode t = node;
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }
    }

    /**
     * round 2
     * 方案3：
     * 1.利用栈的原理，栈顶元素为next节点。
     * 2.出栈元素，就是next节点，并且把右子树入栈
     * 时间复杂度O(1)，空间复杂度O(H)
     * <p>
     * 金矿：巧妙的利用的stack进行缓存next的状态。
     */
    class BSTIterator_4 {
        Stack<TreeNode> stack = null;

        public BSTIterator_4(TreeNode root) {
            stack = new Stack<>();
            putStack(root);
        }

        public int next() {
            TreeNode node = stack.pop();
            if (node.right != null) {
                putStack(node.right);
            }
            return node.val;
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        private void putStack(TreeNode node) {
            TreeNode t = node;
            while (t != null) {
                stack.push(t);
                t = t.left;
            }
        }
    }

    /**
     * round 2
     * 思考：
     * 1.需要有缓存记录当前节点，需要有变量存储树中的数据
     * <p>
     * 方案1：
     * 1.next()执行时，先从头执行in-order遍历找到当前节点，然后返回下一个节点。
     * 2.hasNext()执行是，先从头执行in-order遍历找到当前节点，然后判断是否存在下一个节点。
     * 时间复杂度O(N)，空间复杂度O(1)
     * <p>
     * 方案2：
     * 1.初始化时，把BST通过in-order遍历转换成数组存储。
     * 2.这样无论是next()还是hasNext()都是在数组上操作
     * 时间复杂度O(1)，空间复杂度O(N)
     * <p>
     * 验证通过：
     * Runtime: 20 ms, faster than 75.05% of Java online submissions for Binary Search Tree Iterator.
     * Memory Usage: 52 MB, less than 30.96% of Java online submissions for Binary Search Tree Iterator.
     */
    class BSTIterator_3 {
        List<TreeNode> list = null;
        int cur = 0;

        public BSTIterator_3(TreeNode root) {
            list = new ArrayList<>();
            //fill the list
            dfs(root, list);
        }

        public int next() {
            return list.get(cur++).val;
        }

        public boolean hasNext() {
            return cur < list.size();
        }

        public void dfs(TreeNode node, List<TreeNode> list) {
            if (node == null) return;
            dfs(node.left, list);
            list.add(node);
            dfs(node.right, list);
        }
    }

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
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/binary-search-tree-iterator/discuss/52525/My-solutions-in-3-languages-with-Stack
     */
    class BSTIterator2 {
        private Stack<TreeNode> stack = new Stack<TreeNode>();

        public BSTIterator2(TreeNode root) {
            pushAll(root);
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * @return the next smallest number
         */
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
