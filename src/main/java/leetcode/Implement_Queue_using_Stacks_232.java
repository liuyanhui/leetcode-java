package leetcode;

import java.util.Stack;

/**
 * 232. Implement Queue using Stacks
 * Easy
 * ---------------------
 * Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (push, peek, pop, and empty).
 * <p>
 * Implement the MyQueue class:
 * void push(int x) Pushes element x to the back of the queue.
 * int pop() Removes the element from the front of the queue and returns it.
 * int peek() Returns the element at the front of the queue.
 * boolean empty() Returns true if the queue is empty, false otherwise.
 * <p>
 * Notes:
 * You must use only standard operations of a stack, which means only push to top, peek/pop from top, size, and is empty operations are valid.
 * Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.
 * <p>
 * Example 1:
 * Input
 * ["MyQueue", "push", "push", "peek", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 1, 1, false]
 * Explanation
 * MyQueue myQueue = new MyQueue();
 * myQueue.push(1); // queue is: [1]
 * myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
 * myQueue.peek(); // return 1
 * myQueue.pop(); // return 1, queue is [2]
 * myQueue.empty(); // return false
 * <p>
 * Constraints:
 * 1 <= x <= 9
 * At most 100 calls will be made to push, pop, peek, and empty.
 * All the calls to pop and peek are valid.
 * <p>
 * Follow-up: Can you implement the queue such that each operation is amortized O(1) time complexity? In other words, performing n operations will take overall O(n) time even if one of those operations may take longer.
 */
public class Implement_Queue_using_Stacks_232 {
    /**
     * round 3
     * Score[2] Lower is harder
     *
     * MyQueue_3()很巧妙
     * MyQueue()是普通方案
     *
     * [group] Implement_Stack_using_Queues_225
     *
     */

    /**
     * review stack和queue是可以相互转化的；FIFO和FILO是可以相互转化的。
     */
    /**
     * round 2
     * 参考思路：
     * https://leetcode.com/problems/implement-queue-using-stacks/discuss/64206/Short-O(1)-amortized-C%2B%2B-Java-Ruby
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 71.06% of Java online submissions for Implement Queue using Stacks.
     * Memory Usage: 41.9 MB, less than 40.50% of Java online submissions for Implement Queue using Stacks.
     */
    class MyQueue_3 {
        private Stack<Integer> in = new Stack<>();
        private Stack<Integer> out = new Stack<>();

        // Push element x to the back of queue.
        public void push(int x) {
            in.push(x);
        }

        public int pop() {
            peek();
            return out.pop();
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }

        // Get the front element.
        public int peek() {
            if (out.empty()) {
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            return out.peek();
        }
    }

    /**
     * round 2
     * 与MyQueue_1()相反的实现。但不是amortized O(1) time complexity。
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Implement Queue using Stacks.
     * Memory Usage: 40.4 MB, less than 86.94% of Java online submissions for Implement Queue using Stacks.
     */
    class MyQueue_2 {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        public MyQueue_2() {

        }

        public void push(int x) {
            s1.push(x);
        }

        public int pop() {
            while (s1.size() > 1) {
                s2.push(s1.pop());
            }
            int res = s1.pop();
            while (s2.size() > 0) {
                s1.push(s2.pop());
            }
            return res;
        }

        public int peek() {
            while (s1.size() > 1) {
                s2.push(s1.pop());
            }
            int res = s1.peek();
            while (s2.size() > 0) {
                s1.push(s2.pop());
            }
            return res;
        }

        public boolean empty() {
            return s1.size() == 0;
        }
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Implement Queue using Stacks.
     * Memory Usage: 36.7 MB, less than 74.95% of Java online submissions for Implement Queue using Stacks.
     */
    class MyQueue {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {

        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            while (s1.size() > 0) {
                s2.push(s1.pop());
            }
            s1.push(x);
            while (s2.size() > 0) {
                s1.push(s2.pop());
            }
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            return s1.pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            return s1.peek();
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return s1.size() == 0;
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
