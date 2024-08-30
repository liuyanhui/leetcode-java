package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/min-stack/
 * 155. Min Stack
 * Medium
 * -----------------
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 *  - MinStack() initializes the stack object.
 *  - void push(int val) pushes the element val onto the stack.
 *  - void pop() removes the element on the top of the stack.
 *  - int top() gets the top element of the stack.
 *  - int getMin() retrieves the minimum element in the stack.
 *
 * You must implement a solution with O(1) time complexity for each function.
 *
 * Example 1:
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * Output
 * [null,null,null,null,-3,null,0,-2]
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 *
 * Constraints:
 * -231 <= val <= 231 - 1
 * Methods pop, top and getMin operations will always be called on non-empty stacks.
 * At most 3 * 104 calls will be made to push, pop, top, and getMin.
 */
public class Min_Stack_155 {

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * 参考Solution中的两个排名最靠前的方案：
     * Stack是先入后出，写入和删除都是在尾部。这表示min在每次入栈的时候就固定不变了，min对于每个栈顶元素都是固定的。
     * 虽然min随着入栈出栈是变化的，但是对于固定的栈顶元素却是不变的。
     *
     * 每次push都插入两个数字，val和min。min存储全局最小值。
     *
     * 验证通过：
     * Runtime 5 ms Beats 36.38%
     * Memory 44.62 MB Beats 71.34%
     *
     */
    class MinStack_r3_1 {
        Stack<Integer> stack;
        int min;

        public MinStack_r3_1() {
            stack = new Stack<>();
            min = Integer.MAX_VALUE;
        }

        public void push(int val) {
            min = Math.min(min, val);
            stack.push(min);
            stack.push(val);
        }

        public void pop() {
            stack.pop();
            min = Math.min(min, stack.pop());
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

    /**
     * round 2 ，关键在于英文理解。
     * 要点是：只要求retrieving需要O(1)的复杂度。
     * round 3: 这是在投机取巧，利用题目漏洞，没有意义
     */
    /**
     * 验证通过：
     * Runtime: 4 ms, faster than 94.32% of Java online submissions for Min Stack.
     * Memory Usage: 40.9 MB, less than 48.21% of Java online submissions for Min Stack.
     */
    private static int min;
    private static List<Integer> value;

    /** initialize your data structure here. */
    public Min_Stack_155() {
        min = Integer.MAX_VALUE;
        value = new ArrayList<>();
    }

    public void push(int x) {
        value.add(x);
        min = x < min ? x : min;
    }

    public void pop() {
        if (value != null && value.size() > 0) {
            int v = value.get(value.size() - 1);
            value.remove(value.size() - 1);
            if (v == min) {
                calcMin();
            }
        }
    }

    public int top() {
        return value.get(value.size() - 1);
    }

    public int getMin() {
        return min;
    }

    private void calcMin() {
        min = Integer.MAX_VALUE;
        if (value.size() > 0) {
            for (int v : value) {
                min = (min < v) ? min : v;
            }
        }
    }
    /**
     * Your MinStack object will be instantiated and called as such:
     * MinStack obj = new MinStack();
     * obj.push(x);
     * obj.pop();
     * int param_3 = obj.top();
     * int param_4 = obj.getMin();
     */
}
