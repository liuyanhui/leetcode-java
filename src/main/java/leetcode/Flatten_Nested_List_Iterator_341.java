package leetcode;

import java.util.*;

/**
 * 341. Flatten Nested List Iterator
 * Medium
 * ----------------------
 * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it.
 *
 * Implement the NestedIterator class:
 *  - NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
 *  - int next() Returns the next integer in the nested list.
 *  - boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
 *
 * Your code will be tested with the following pseudocode:
 *  - initialize iterator with nestedList
 *  - res = []
 *  - while iterator.hasNext()
 *       append iterator.next() to the end of res
 *  - return res
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
     * round 3
     * Score[2] Lower is harder
     * <p>
     * 1. 初始化时，把输入转化成list，然后遍历。NestedIterator_3()
     * 2. 直接遍历。采用Stack。如果下一个时List那么把List中的所有元素依次入栈。每次出栈，并判断是否为Integer即可。
     * 3. 直接遍历。采用Deque。如果下一个时List那么把List中的所有元素依次加入队列头。每次出出队，并判断是否为Integer即可。
     *
     * review 1.较为明显的递归类问题。切记。而递归类问题，又可以采用Stack或Deque方案。
     * 2.很像设计模式中的组合模式，既有单个类对象，又有该类的集合对象。
     * 3.如果用树型结构示意的话，会更加明显。
     */

    /**
     * review
     * NestedIterator_2()中的Thinking中的第一种思路。属于递归法
     * 创建对象时就实现了flat操作，next()和hashNext()只需要遍历链表即可
     *
     * review 更高效和简单的方案
     *
     */
    public class NestedIterator_3 implements Iterator<Integer> {
        List<Integer> arr;
        int index;

        public void pushArray(List<NestedInteger> nestedList) {
            int n = nestedList.size();
            for (int i = 0; i < n; i++) {
                if (nestedList.get(i).isInteger())
                    arr.add(nestedList.get(i).getInteger());
                else
                    pushArray(nestedList.get(i).getList());
            }
        }

        public NestedIterator_3(List<NestedInteger> nestedList) {
            arr = new ArrayList<Integer>();
            index = 0;
            pushArray(nestedList);
        }

        @Override
        public Integer next() {
            return arr.get(index++);
        }

        @Override
        public boolean hasNext() {
            return index < arr.size();
        }
    }

    /**
     * round 2
     *
     * Thinking：
     * 1.一种思路是可以把输入序列化成字符串，然后遍历字符串。然鹅，序列化的过程就是flat的过程，本思路不可行。NestedIterator_3()说明这个思路是可行的。
     * 2.使用stack。
     *
     * 验证通过：
     * Runtime 6 ms Beats 16.48%
     * Memory 44.3 MB Beats 88.14%
     *
     */
    public static class NestedIterator_2 implements Iterator<Integer> {
        Stack<NestedInteger> stack;

        public NestedIterator_2(List<NestedInteger> nestedList) {
            stack = new Stack<>();
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                stack.push(nestedList.get(i));
            }
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            //过滤null
            while (!stack.empty() && stack.peek() == null) {
                stack.pop();
            }
            if (stack.empty()) return false;
            if (!stack.peek().isInteger()) {
                NestedInteger t = stack.pop();
                for (int i = t.getList().size() - 1; i >= 0; i--) {
                    stack.push(t.getList().get(i));
                }
                return hasNext();//review 递归判断。这里很关键也很巧妙。It's the magic of RECURSIVE!
            }
            return true;
        }
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
    public static class NestedIterator implements Iterator<Integer> {
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

    public static void main(String[] args) {
        List<NestedInteger> in = new ArrayList<>();
        in.add(null);
        NestedIterator_2 nestedIterator2 = new NestedIterator_2(in);
        while (nestedIterator2.hasNext()) {
            System.out.println(nestedIterator2.next());
        }
        System.out.println("----------------------------");
    }
}
