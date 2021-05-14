package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 225. Implement Stack using Queues
 * Easy
 * ----------------------
 * Implement a last in first out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal queue (push, top, pop, and empty).
 *
 * Implement the MyStack class:
 *
 * void push(int x) Pushes element x to the top of the stack.
 * int pop() Removes the element on the top of the stack and returns it.
 * int top() Returns the element on the top of the stack.
 * boolean empty() Returns true if the stack is empty, false otherwise.
 * Notes:
 *
 * You must use only standard operations of a queue, which means only push to back, peek/pop from front, size, and is empty operations are valid.
 * Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue), as long as you use only a queue's standard operations.
 *
 * Example 1:
 * Input
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 2, 2, false]
 *
 * Explanation
 * MyStack myStack = new MyStack();
 * myStack.push(1);
 * myStack.push(2);
 * myStack.top(); // return 2
 * myStack.pop(); // return 2
 * myStack.empty(); // return False
 *
 * Constraints:
 * 1 <= x <= 9
 * At most 100 calls will be made to push, pop, top, and empty.
 * All the calls to pop and top are valid.
 *
 * Follow-up: Can you implement the stack such that each operation is amortized O(1) time complexity? In other words, performing n operations will take overall O(n) time even if one of those operations may take longer. You can use more than two queues.
 */
public class Implement_Stack_using_Queues_225 {

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 36.7 MB, less than 53.65% of Java online submissions for Implement Stack using Queues.
     */
    class MyStack_2 {
        Queue<Integer> queue = new LinkedList<>();

        /** Initialize your data structure here. */
        public MyStack_2() {

        }

        /** Push element x onto stack. */
        public void push(int x) {
            Queue<Integer> tq = new LinkedList<>();
            tq.offer(x);
            while (queue.size() > 0) {
                tq.offer(queue.poll());
            }
            queue = tq;
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            return queue.poll();
        }

        /** Get the top element. */
        public int top() {
            return queue.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue.size() == 0;
        }
    }

    /**
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 36.9 MB, less than 25.79% of Java online submissions for Implement Stack using Queues.
     */
    class MyStack {
        List<Integer> list = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

        /** Initialize your data structure here. */
        public MyStack() {

        }

        /** Push element x onto stack. */
        public void push(int x) {
            list.add(x);
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            int t = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            return t;
        }

        /** Get the top element. */
        public int top() {
            return list.get(list.size() - 1);
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return list.size() == 0;
        }
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
}
