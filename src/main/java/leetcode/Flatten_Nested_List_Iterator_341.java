package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * 341. Flatten Nested List Iterator
 * Medium
 * ----------------------
 * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it.
 *
 * Implement the NestedIterator class:
 * NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
 * int next() Returns the next integer in the nested list.
 * boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
 *
 * Your code will be tested with the following pseudocode:
 * initialize iterator with nestedList
 * res = []
 * while iterator.hasNext()
 *     append iterator.next() to the end of res
 * return res
 *
 * If res matches the expected flattened list, then your code will be judged as correct.
 *
 * Example 1:
 * Input: nestedList = [[1,1],2,[1,1]]
 * Output: [1,1,2,1,1]
 * Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].
 *
 * Example 2:
 * Input: nestedList = [1,[4,[6]]]
 * Output: [1,4,6]
 * Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
 *
 * Constraints:
 * 1 <= nestedList.length <= 500
 * The values of the integers in the nested list is in the range [-10^6, 10^6].
 */
public class Flatten_Nested_List_Iterator_341 {

    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/flatten-nested-list-iterator/discuss/80147/Simple-Java-solution-using-a-stack-with-explanation
     *
     * 递归法
     * 1.如果是list，递归调用
     * 2.如果是数字，输出数字
     *
     * 1.用双向队列存储输入数据，队头就是next的元素
     * 2.如果队头为list，把list中的元素依次从队头加入队列
     * 3.如果队头为int，把该数字出队
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 30.75% of Java .
     * Memory Usage: 42 MB, less than 20.42% of Java .
     */
    public class NestedIterator implements Iterator<Integer> {
        //FIXME 这里也可以使用stack代替Deque，因为Deque初始化后只是操作队列头，并没有操作队列尾。
        //FIXME stack初始化时，反向入栈即可。即后面的元素先入栈。
        Deque<NestedInteger> deque = new ArrayDeque<>();

        public NestedIterator(List<NestedInteger> nestedList) {
            if (nestedList != null && !nestedList.isEmpty()) {
                nestedList.stream().forEach(n -> deque.offerLast(n));
            }
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                return deque.pollFirst().getInteger();
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            while (!deque.isEmpty() && deque.peekFirst() == null) {
                deque.pollFirst();
            }
            if (deque.isEmpty()) return false;
            NestedInteger cur = deque.peekFirst();
            if (!cur.isInteger()) {
                deque.pollFirst();
                reverseThenOffer(cur.getList());
                //这里需要再次判断是否为list
                return hasNext();
            } else {
                return true;
            }
        }

        //倒序加入队头
        private void reverseThenOffer(List<NestedInteger> nestedList) {
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                deque.offerFirst(nestedList.get(i));
            }
        }
    }

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
}
