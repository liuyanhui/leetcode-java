package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 385. Mini Parser
 * Medium
 * ---------------------------------
 * Given a string s represents the serialization of a nested list, implement a parser to deserialize it and return the deserialized NestedInteger.
 *
 * Each element is either an integer or a list whose elements may also be integers or other lists.
 *
 * Example 1:
 * Input: s = "324"
 * Output: 324
 * Explanation: You should return a NestedInteger object which contains a single integer 324.
 *
 * Example 2:
 * Input: s = "[123,[456,[789]]]"
 * Output: [123,[456,[789]]]
 * Explanation: Return a NestedInteger object containing a nested list with 2 elements:
 * 1. An integer containing value 123.
 * 2. A nested list containing two elements:
 *     i.  An integer containing value 456.
 *     ii. A nested list with one element:
 *          a. An integer containing value 789
 *
 * Constraints:
 * 1 <= s.length <= 5 * 10^4
 * s consists of digits, square brackets "[]", negative sign '-', and commas ','.
 * s is the serialization of valid NestedInteger.
 * All the values in the input are in the range [-10^6, 10^6].
 */
public class Mini_Parser_385 {

    public static NestedInteger deserialize(String s) {
        return deserialize_2(s);
    }

    /**
     * 与Flatten_Nested_List_Iterator_341相呼应
     * 思路：
     * 针对不同类型的字符进行不同的逻辑处理，
     * 1.遇到"["：new NestedInteger()
     * 2.遇到"]"：有三种情况，重置缓存
     * 3.遇到","：有两种情况，重置缓存
     * 4.遇到"0~9"：继续提取数字
     * 5.遇到"-"：记录sign=-1，提取数字
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 74.31% of Java online submissions for Mini Parser.
     * Memory Usage: 39.8 MB, less than 80.43% of Java online submissions for Mini Parser.
     *
     * @param s
     * @return
     */
    public static NestedInteger deserialize_1(String s) {
        NestedInteger ret = new NestedInteger();
        if (s == null || s.length() == 0) return ret;

        if (s.charAt(0) != '[') return new NestedInteger(Integer.valueOf(s));

        Stack<NestedInteger> stack = new Stack<>();
        int tmpN = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                stack.push(new NestedInteger());
            } else if (c == ']') {
                NestedInteger t;
                if (s.charAt(i - 1) != ']') {
                    if (s.charAt(i - 1) == '[') {
                        //do nothing
                    } else {
                        t = new NestedInteger(sign * tmpN);
                        stack.peek().add(t);
                    }
                }
                if (i < s.length() - 1) {
                    t = stack.pop();
                    stack.peek().add(t);
                }
                tmpN = 0;
                sign = 1;
            } else if (c == ',') {
                if (s.charAt(i - 1) != ']') {
                    NestedInteger t = new NestedInteger(sign * tmpN);
                    stack.peek().add(t);
                }
                tmpN = 0;
                sign = 1;
            } else if (Character.isDigit(c)) {
                tmpN = tmpN * 10 + (c - '0');
            } else if (c == '-') {
                sign = -1;
            }
        }
        return stack.peek();
    }

    /**
     * deserialize_1()的代码优化版
     * 参考思路：
     * https://leetcode.com/problems/mini-parser/discuss/86066/An-Java-Iterative-Solution
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 61.16% of Java online submissions for Mini Parser.
     * Memory Usage: 41 MB, less than 52.29% of Java online submissions for Mini Parser.
     *
     * @param s
     * @return
     */
    public static NestedInteger deserialize_2(String s) {
        if (s.isEmpty())
            return null;
        if (s.charAt(0) != '[') // special case
            return new NestedInteger(Integer.valueOf(s));

        Stack<NestedInteger> stack = new Stack<>();
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {//遇到'['必入栈
                stack.push(new NestedInteger());
                left = i + 1;
            } else if (c == ']') {//遇到']'必出栈，除非是最后一个元素
                if (s.charAt(i - 1) != ']' && s.charAt(i - 1) != '[') {
                    NestedInteger n = new NestedInteger(Integer.valueOf(s.substring(left, i)));
                    stack.peek().add(n);
                }
                //最后一个元素，不出栈
                if (i < s.length() - 1) {
                    NestedInteger n = stack.pop();
                    stack.peek().add(n);
                }
            } else if (c == ',') {
                if (s.charAt(i - 1) != ']') {
                    NestedInteger n = new NestedInteger(Integer.valueOf(s.substring(left, i)));
                    stack.peek().add(n);
                }
                left = i + 1;
            }
        }
        return stack.peek();
    }

    public static void main(String[] args) {
//        do_func("324", "324");
//        do_func("[123,[456,[789]]]", "[123,[456,[789]]]");
        do_func("[123,456,[788,799,833],[[]],10,[]]", "[123,456,[788,799,833],[[]],10,[]]");
//        do_func("[-123,[-456,[-789]]]", "[-123,[-456,[-789]]]");
//        do_func("-324", "-324");
//        do_func("[123,[[456],[789]]]", "[123,[[456],[789]]]");
//        do_func("[324]", "[324]");
//        do_func("[324,1,2]", "[324,1,2]");
//        do_func("[[123],[456],[789]]", "[[123],[456],[789]]");
//        do_func("[[-123],[-456],[-789]]", "[[-123],[-456],[-789]]");
    }

    private static void do_func(String ransomNote, String expected) {
        NestedInteger ret = deserialize(ransomNote);
        System.out.println(ret);
        System.out.println(ret.toString().equals(expected));
        System.out.println("--------------");
    }

    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    static class NestedInteger {
        // Constructor initializes an empty nested list.
        int value = Integer.MAX_VALUE;
        List<NestedInteger> list;

        public NestedInteger() {
            list = new ArrayList<>();
        }

        // Constructor initializes a single integer.
        public NestedInteger(int value) {
            this.value = value;
            list = new ArrayList<>();
        }

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger() {
            return value != Integer.MAX_VALUE;
        }

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger() {
            return value;
        }

        // Set this NestedInteger to hold a single integer.
        public void setInteger(int value) {
            this.value = value;
        }

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni) {
            list.add(ni);
        }

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList() {
            return list;
        }
    }
}
