package leetcode;

import java.util.Stack;

/**
 * 331. Verify Preorder Serialization of a Binary Tree
 * Medium
 * -----------------------
 * One way to serialize a binary tree is to use preorder traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as '#'.
 * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where '#' represents a null node.
 * Given a string of comma-separated values preorder, return true if it is a correct preorder traversal serialization of a binary tree.
 * It is guaranteed that each comma-separated value in the string must be either an integer or a character '#' representing null pointer.
 * <p>
 * You may assume that the input format is always valid.
 * <p>
 * For example, it could never contain two consecutive commas, such as "1,,3".
 * Note: You are not allowed to reconstruct the tree.
 * <p>
 * Example 1:
 * Input: preorder = "9,3,4,#,#,1,#,#,2,#,6,#,#"
 * Output: true
 * <p>
 * Example 2:
 * Input: preorder = "1,#"
 * Output: false
 * <p>
 * Example 3:
 * Input: preorder = "9,#,#,1"
 * Output: false
 * <p>
 * Constraints:
 * 1 <= preorder.length <= 10^4
 * preoder consist of integers in the range [0, 100] and '#' separated by commas ','.
 */
public class Verify_Preorder_Serialization_of_a_Binary_Tree_331 {
    public static boolean isValidSerialization(String preorder) {
        return isValidSerialization_2(preorder);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Think:
     * 1. review 假设输入字符串是二叉树的正确序列化结果。基于这个假设去计算，而不是考虑各种不正确的情况。
     * review 用假设输入时正确的计算，而不是考虑所有正确和错误的可能。只要输入时正确的，那么最终的结果是：数组遍历完成，栈为空，并且两者同时达成。
     * isValidSerialization_3()的思路（注释）错误，但是结果正确
     * review : 非常巧妙的思路 isValidSerialization_2()
     *
     * review 引申问题：How to deserialize a tree from a string?
     *
     */

    /**
     * round 2
     * 错误的思路（注释），但是结果确实正确的。
     * <p>
     * Thinking：
     * 1.使用Stack模拟preorder，如果最后stack不为空表示不是Binary Tree
     * 2.有两个主要变量：stack和当前下标
     * <p>
     * isValidSerialization_2()的方法更巧妙。没有要求输出Binary Tree所以可以用这个巧妙的办法。
     * <p>
     * 验证通过：
     * Runtime 7 ms Beats 20.25%
     * Memory 42.1 MB Beats 78.52%
     *
     * @param preorder
     * @return
     */
    public static boolean isValidSerialization_3(String preorder) {
        if (preorder == null || preorder.length() == 0) return false;
        String[] arr = preorder.split(",");
        Stack<String> stack = new Stack<>();
        stack.push(arr[0]);
        int i = 1;
        while (!stack.empty()) {
            String t = stack.pop();
            if (t.equals("#")) continue;
            if (i + 1 < arr.length) {
                //右子树先入栈 review 这里的注释或者思路是错误的
                stack.push(arr[i + 1]);
                //左子树后入栈 review 这里的注释或者思路是错误的
                stack.push(arr[i]);
                i += 2;
            } else {
                return false;
            }
        }
        return stack.empty() && i == arr.length;
    }

    /**
     * review round2 It is a smart resolution.
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 91.45% of Java.
     * Memory Usage: 39.1 MB, less than 50.84% of Java.
     *
     * @param preorder
     * @return
     */
    public static boolean isValidSerialization_2(String preorder) {
        String[] node = preorder.split(",");
        int count = 1;
        for (String n : node) {
            if (count <= 0) return false;
            if (n.equals("#")) {
                count--;
            } else {
                count++;
            }
        }
        return count == 0;
    }

    /**
     * 规律是：
     * 1.使用计数器count表示#出现次数，遇到根节点count=2,之后遇到数字-1+2，遇到#-1，最终count=0返回true
     * 2.因为一个节点表示有两个#，当存在左子节点时#的数量先-1再+2.
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 93.89% of Java .
     * Memory Usage: 37.4 MB, less than 94.96% of Java.
     *
     * @param preorder
     * @return
     */
    public static boolean isValidSerialization_1(String preorder) {
        int count = 1;
        int n = -1;
        for (int i = 0; i < preorder.length(); i++) {
            char c = preorder.charAt(i);
            if (c == ',') {
                if (n > -1) {
                    count++;
                }
                n = -1;
                continue;
            } else if (c == '#') {
                count--;
            } else {
                n = c - '0';
            }
            if ((i < preorder.length() - 1) && (count <= 0)) {
                return false;
            }
        }
        return count == 0;
    }

    public static void main(String[] args) {
        do_func("9,3,4,#,#,1,#,#,2,#,6,#,#", true);
        do_func("1,#", false);
        do_func("9,#,#,1", false);
        do_func("9,8,7,6,#,#,#,#,#", true);
        do_func("9,#,8,#,7,#,6,#,#", true);
        do_func("#,#,1", false);
        do_func("1,#,#", true);
        do_func("1,#,1,#,1,#,1,#", false);
        do_func("#", true);
        do_func("9,#,92,#,#", true);
        do_func("9,3,0,6,#,#,5,#,4,#,2,#,#,6,0,#,#,#,#", true);
        do_func("1", false);
        do_func("9,3,0,6,5,4,2,6,0,#,#,#,#,#,#,#,#,#,#", true);
    }

    private static void do_func(String preorder, boolean expected) {
        boolean ret = isValidSerialization(preorder);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
