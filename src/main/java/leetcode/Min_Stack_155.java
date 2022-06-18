package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/min-stack/
 * 155. Min Stack
 * Easy
 * -----------------
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * Example 1:
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * Output
 * [null,null,null,null,-3,null,0,-2]
 *
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
 * Methods pop, top and getMin operations will always be called on non-empty stacks.
 */
public class Min_Stack_155 {

    /**
     * round 2 ，关键在于英文理解。
     * 要点是：只要求retrieving需要O(1)的复杂度。
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
