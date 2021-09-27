package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 388. Longest Absolute File Path
 * Medium
 * --------------------------
 * Suppose we have a file system that stores both files and directories. An example of one system is represented in the following picture:
 *
 * Here, we have dir as the only directory in the root. dir contains two subdirectories, subdir1 and subdir2. subdir1 contains a file file1.ext and subdirectory subsubdir1. subdir2 contains a subdirectory subsubdir2, which contains a file file2.ext.
 *
 * In text form, it looks like this (with ⟶ representing the tab character):
 *
 * dir
 * ⟶ subdir1
 * ⟶ ⟶ file1.ext
 * ⟶ ⟶ subsubdir1
 * ⟶ subdir2
 * ⟶ ⟶ subsubdir2
 * ⟶ ⟶ ⟶ file2.ext
 * If we were to write this representation in code, it will look like this: "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext". Note that the '\n' and '\t' are the new-line and tab characters.
 *
 * Every file and directory has a unique absolute path in the file system, which is the order of directories that must be opened to reach the file/directory itself, all concatenated by '/'s. Using the above example, the absolute path to file2.ext is "dir/subdir2/subsubdir2/file2.ext". Each directory name consists of letters, digits, and/or spaces. Each file name is of the form name.extension, where name and extension consist of letters, digits, and/or spaces.
 *
 * Given a string input representing the file system in the explained format, return the length of the longest absolute path to a file in the abstracted file system. If there is no file in the system, return 0.
 *
 * Example 1:
 * Input: input = "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"
 * Output: 20
 * Explanation: We have only one file, and the absolute path is "dir/subdir2/file.ext" of length 20.
 *
 * Example 2:
 * Input: input = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"
 * Output: 32
 * Explanation: We have two files:
 * "dir/subdir1/file1.ext" of length 21
 * "dir/subdir2/subsubdir2/file2.ext" of length 32.
 * We return 32 since it is the longest absolute path to a file.
 *
 * Example 3:
 * Input: input = "a"
 * Output: 0
 * Explanation: We do not have any files, just a single directory named "a".
 *
 * Example 4:
 * Input: input = "file1.txt\nfile2.txt\nlongfile.txt"
 * Output: 12
 * Explanation: There are 3 files at the root directory.
 * Since the absolute path for anything at the root directory is just the name itself, the answer is "longfile.txt" with length 12.
 *
 * Constraints:
 * 1 <= input.length <= 10^4
 * input may contain lowercase or uppercase English letters, a new line character '\n', a tab character '\t', a dot '.', a space ' ', and digits.
 */
public class Longest_Absolute_File_Path_388 {
    public static int lengthLongestPath(String input) {
        return lengthLongestPath_3(input);
    }

    /**
     * lengthLongestPath_2的简化版
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Longest Absolute File Path.
     * Memory Usage: 37 MB, less than 70.46% of Java online submissions for Longest Absolute File Path.
     *
     * @param input
     * @return
     */
    public static int lengthLongestPath_3(String input) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int maxLength = 0;
        String[] arr = input.split("\n");
        for (String s : arr) {
            //简化了这里
            int level = s.lastIndexOf('\t') + 1;
            while (level < stack.size() - 1) stack.pop();
            stack.push(stack.peek() + s.length() - level + 1);
            if (s.indexOf('.') > 0) {
                maxLength = Math.max(maxLength, stack.peek() - 1);
            }
        }
        return maxLength;
    }

    /**
     * 代码简化版，
     * 参考思路：
     * https://leetcode.com/problems/longest-absolute-file-path/discuss/86615/9-lines-4ms-Java-solution
     * https://leetcode.com/problems/longest-absolute-file-path/discuss/86666/Java-O(n)-Solution-Using-Stack
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 87.54% of Java online submissions for Longest Absolute File Path.
     * Memory Usage: 37.2 MB, less than 53.32% of Java online submissions for Longest Absolute File Path.
     *
     * @param input
     * @return
     */
    public static int lengthLongestPath_2(String input) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int maxLength = 0;
        String[] arr = input.split("\n");
        for (String s : arr) {
            String t = s.replace("\t", "");
            int level = s.length() - t.length();
            while (level < stack.size() - 1) stack.pop();
            stack.push(stack.peek() + t.length() + 1);
            if (t.indexOf('.') > 0) {
                maxLength = Math.max(maxLength, stack.peek() - 1);
            }
        }
        return maxLength;
    }

    /**
     * 这类题与四则运算字符串的题目类似，都是根据当前的字符的类型，决定上一个类型字符串的操作。
     * 如：Basic_Calculator_224，Basic_Calculator_227
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 87.54% of Java online submissions for Longest Absolute File Path.
     * Memory Usage: 36.7 MB, less than 96.08% of Java online submissions for Longest Absolute File Path
     *
     * @param input
     * @return
     */
    public static int lengthLongestPath_1(String input) {
        if (input == null || input.trim().equals("")) return 0;
        List<String> path = new ArrayList<>();
        int pathLength = 0;
        int maxLength = 0;
        int tCount = 0;
        int left = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\n' || i == input.length() - 1) {
                //判断路径是否需要退栈。当'\t'的数量小于路径数量时，表示目录深度减小了，需要出栈。
                while (tCount < path.size()) {
                    String t = path.remove(path.size() - 1);
                    pathLength -= (t.length() + 1);
                }
                //提取路径字符串
                int right = (i == input.length() - 1 ? input.length() : i);//注意边界条件：最后一个字符
                String cur = input.substring(left, right);
                if (cur.indexOf('.') > 0) {//如果是文件，计算路径长度
                    maxLength = Math.max(maxLength, pathLength + cur.length());
                } else {//如果不是文件，入栈路径
                    pathLength += (cur.length() + 1);
                    path.add(cur);
                }

                tCount = 0;
                left = i + 1;
            } else if (c == '\t') {//只需要记录'\t'的出现次数
                tCount++;
                left = i + 1;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        do_func("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext", 20);
        do_func("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext", 32);
        do_func("a", 0);
        do_func("file1.txt\nfile2.txt\nlongfile.txt", 12);
        do_func("dir\n        file.txt", 16);

    }

    private static void do_func(String input, int expected) {
        int ret = lengthLongestPath(input);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
