package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 225. Implement Stack using Queues
 * Easy
 * ----------------------
 * Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (push, top, pop, and empty).
 *
 * Implement the MyStack class:
 * void push(int x) Pushes element x to the top of the stack.
 * int pop() Removes the element on the top of the stack and returns it.
 * int top() Returns the element on the top of the stack.
 * boolean empty() Returns true if the stack is empty, false otherwise.
 *
 * Notes:
 * You must use only standard operations of a queue, which means that only push to back, peek/pop from front, size and is empty operations are valid.
 * Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.
 *
 * Example 1:
 * Input
 * ["MyStack", "push", "push", "top", "pop", "empty"]
 * [[], [1], [2], [], [], []]
 * Output
 * [null, null, null, 2, 2, false]
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
 * Follow-up: Can you implement the stack using only one queue?
 */
public class Implement_Stack_using_Queues_225 {

    /**
     * round 2
     *
     */
    class MyStack_4 {

        Queue<Integer> queue = new LinkedList<>();

        public MyStack_4() {

        }

        public void push(int x) {
            queue.offer(x);
            reorg();
        }

        public int pop() {
            int res = queue.poll();
            reorg();
            return res;
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.size() == 0;
        }

        private void reorg() {
            if (queue.size() > 1) {
                int size = queue.size();
                while (size > 1) {
                    queue.offer(queue.poll());
                    size--;
                }
            }
        }
    }

    /**
     * round 2
     *
     * 使用两个queue的思路:
     * 1.queue1按入栈顺序保存数据，queue2按出栈顺序保存数据。这样是行不通的，因为queue2的数据无法按要求存储。
     * 2.因为栈每次只能pop出一个元素，那么把queue1除了最后一个元素之外全部复制到queue2。出栈只操作queue2即可。queue2永远都只存储一个元素，栈顶元素。push和pop时都需要重新计算queue2。top时直接读取queue。
     * 3.push时，追加到queue2，重建queue1和queue2，重建后queue2只存储一个（栈顶）元素。
     * 4.pop时，queue2队头出队，重建queue1和queue2，重建后queue2只存储一个（栈顶）元素。
     * 5.top时，从queue队头获取。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 34.10% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 42 MB, less than 36.45% of Java online submissions for Implement Stack using Queues.
     *
     */
    class MyStack_3 {

        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();

        public MyStack_3() {

        }

        public void push(int x) {
            q2.offer(x);
            reorg();
        }

        public int pop() {
            int res = q2.poll();
            reorg();
            return res;
        }

        public int top() {
            return q2.peek();
        }

        public boolean empty() {
            return q2.size() == 0;
        }

        private void reorg() {
            if (q2.size() == 0) {
                if (q1.size() > 0) {
                    while (q1.size() > 1) {
                        q2.offer(q1.poll());
                    }
                    Queue t = q2;
                    q2 = q1;
                    q1 = t;
                }
            } else if (q2.size() > 1) {
                while (q2.size() > 1) {
                    q1.offer(q2.poll());
                }
            }
        }
    }

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
