package leetcode;

import java.util.Iterator;

/**
 * 284. Peeking Iterator
 * Medium
 * ---------------------------
 * Design an iterator that supports the peek operation on an existing iterator in addition to the hasNext and the next operations.
 * <p>
 * Implement the PeekingIterator class:
 * - PeekingIterator(Iterator<int> nums) Initializes the object with the given integer iterator iterator.
 * - int next() Returns the next element in the array and moves the pointer to the next element.
 * - boolean hasNext() Returns true if there are still elements in the array.
 * - int peek() Returns the next element in the array without moving the pointer.
 * <p>
 * Note: Each language may have a different implementation of the constructor and Iterator, but they all support the int next() and boolean hasNext() functions.
 * <p>
 * Example 1:
 * Input
 * ["PeekingIterator", "next", "peek", "next", "next", "hasNext"]
 * [[[1, 2, 3]], [], [], [], [], []]
 * Output
 * [null, 1, 2, 2, 3, false]
 * <p>
 * Explanation
 * PeekingIterator peekingIterator = new PeekingIterator([1, 2, 3]); // [1,2,3]
 * peekingIterator.next();    // return 1, the pointer moves to the next element [1,2,3].
 * peekingIterator.peek();    // return 2, the pointer does not move [1,2,3].
 * peekingIterator.next();    // return 2, the pointer moves to the next element [1,2,3]
 * peekingIterator.next();    // return 3, the pointer moves to the next element [1,2,3]
 * peekingIterator.hasNext(); // return False
 * <p>
 * Constraints:
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 1000
 * All the calls to next and peek are valid.
 * At most 1000 calls will be made to next, hasNext, and peek.
 * <p>
 * Follow up: How would you extend your design to be generic and work with all types, not just integer?
 */
public class Peeking_Iterator_284 {
    // Java Iterator interface reference:
    // https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking
     * 1. 由于 Iterator 只有三个接口，所以可以认为输入对象是一次性的，无法遍历多次，无法修改。
     * 所以定义一个内部 List 和 current_index 分别用来保存所有数据和当前索引。
     * 2. 使用 cur 保存原本由 next() 返回的元素，每次调用 next() 时更新cur。
     *
     * 【2.】更优雅一些。
     */

    /**
     * round 2
     * 验证通过：
     * Runtime: 8 ms, faster than 52.60% of Java online submissions for Peeking Iterator.
     * Memory Usage: 42.3 MB, less than 81.90% of Java online submissions for Peeking Iterator.
     */
    class PeekingIterator_2 implements Iterator<Integer> {
        Iterator<Integer> it;
        int cur;

        public PeekingIterator_2(Iterator<Integer> iterator) {
            // initialize any member here.
            it = iterator;
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            if (cur == 0) {
                cur = it.next();
            }
            return cur;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            if (cur == 0) {
                return it.next();
            } else {
                int res = cur;
                cur = 0;
                return res;
            }

        }

        @Override
        public boolean hasNext() {
            return cur > 0 || it.hasNext();
        }
    }

    /**
     * 验证通过：
     * Runtime: 4 ms, faster than 97.16% of Java online submissions for Peeking Iterator.
     * Memory Usage: 39 MB, less than 44.20% of Java online submissions for Peeking Iterator.
     */
    class PeekingIterator implements Iterator<Integer> {
        Iterator<Integer> value;
        Integer current = null;

        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            value = iterator;
            if (value.hasNext()) {
                current = value.next();
            }
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return current;
        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            Integer t = current;
            if (value.hasNext()) {
                current = value.next();
            } else {
                current = null;
            }
            return t;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }
    }
}
