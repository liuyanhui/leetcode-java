package leetcode;

import java.util.Stack;

/**
 * https://leetcode.com/problems/simplify-path/
 * 71. Simplify Path
 * Medium
 * --------------------
 * Given a string path, which is an absolute path (starting with a slash '/') to a file or directory in a Unix-style file system, convert it to the simplified canonical path.
 *
 * In a Unix-style file system, a period '.' refers to the current directory, a double period '..' refers to the directory up a level, and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'. For this problem, any other format of periods such as '...' are treated as file/directory names.
 *
 * The canonical path should have the following format:
 * The path starts with a single slash '/'.
 * Any two directories are separated by a single slash '/'.
 * The path does not end with a trailing '/'.
 * The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period '.' or double period '..')
 *
 * Return the simplified canonical path.
 *
 * Example 1:
 * Input: path = "/home/"
 * Output: "/home"
 * Explanation: Note that there is no trailing slash after the last directory name.
 *
 *  Example 2:
 * Input: path = "/../"
 * Output: "/"
 * Explanation: Going one level up from the root directory is a no-op, as the root level is the highest level you can go.
 *
 *  Example 3:
 * Input: path = "/home//foo/"
 * Output: "/home/foo"
 * Explanation: In the canonical path, multiple consecutive slashes are replaced by a single one.
 *
 *  Example 4:
 * Input: path = "/a/./b/../../c/"
 * Output: "/c"
 *
 *  Constraints:
 * 1 <= path.length <= 3000
 * path consists of English letters, digits, period '.', slash '/' or '_'.
 * path is a valid Unix path.
 */
public class Simplify_Path_71 {
    public static String simplifyPath(String path) {
        return simplifyPath_3(path);
    }

    /**
     * round 2
     *
     * 1.如果path为空，或不是以/开头，返回null
     * 2.将path根据/分割成数组。并遍历数组
     * 3.IF directory=="." or directory=="" THEN ignore
     *   ELSE IF directory==".." THEN delete last directory from return list
     *   ELSE directory add to the return list
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 33.76% of Java online submissions for Simplify Path.
     * Memory Usage: 40.9 MB, less than 24.83% of Java online submissions for Simplify Path.
     *
     * @param path
     * @return
     */
    public static String simplifyPath_3(String path) {
        if (path.indexOf("/") > 0) return null;
        String[] arr = path.split("/");
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("") || arr[i].equals(".")) {
                continue;
            } else if (arr[i].equals("..")) {
                if (!stack.empty()) {
                    stack.pop();
                }
            } else {
                stack.push(arr[i]);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.insert(0, stack.pop());
            sb.insert(0, "/");
        }
        if (sb.length() == 0) {
            sb.append("/");
        }
        return sb.toString();
    }

    /**
     * simplifyPath_1的简化版
     * @param path
     * @return
     */
    public static String simplifyPath_2(String path) {
        if (path == null || path.trim() == "") {
            return "";
        }
        String ret = "";
        String[] pathArray = path.split("/");
        int cur = 0;
        for (int i = 0; i < pathArray.length; i++) {
            String tmpPath = pathArray[i];
            if (tmpPath.equals("..")) {
                cur = cur <= 0 ? 0 : cur - 1;
            } else if (null != tmpPath && !"".equals(tmpPath) && !".".equals(tmpPath)) {
                pathArray[cur++] = pathArray[i];
            }
        }
        cur = cur == 0 ? 1 : cur;
        for (int i = 0; i < cur; i++) {
            ret += "/" + pathArray[i];
        }

        return ret;
    }

    /**
     * 利用栈的原理
     * 验证通过：
     * Runtime: 7 ms, faster than 35.26% of Java online submissions for Simplify Path.
     * Memory Usage: 39 MB, less than 80.95% of Java online submissions for Simplify Path.
     * @param path
     * @return
     */
    public static String simplifyPath_1(String path) {
        if (path == null || path.trim() == "") {
            return "";
        }
        String ret = "";
        String[] pathArray = path.split("/");
        int cur = 0;
        for (int i = 0; i < pathArray.length; i++) {
            String tmpPath = pathArray[i];
            if (tmpPath == null || "".equals(tmpPath.trim())) {
                continue;
            } else if (tmpPath.equals(".")) {
                continue;
            } else if (tmpPath.equals("..")) {
                cur = cur <= 0 ? 0 : cur - 1;
            } else {
                pathArray[cur++] = pathArray[i];
            }
        }
        if (cur == 0) {
            ret = "/";
        } else {
            for (int i = 0; i < cur; i++) {
                ret += "/" + pathArray[i];
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("/home/", "/home");
        do_func("/../", "/");
        do_func("/home//foo/", "/home/foo");
        do_func("/a/./b/../../c/", "/c");
    }

    private static void do_func(String path, String expected) {
        String ret = simplifyPath(path);
        System.out.println(ret);
        System.out.println(ret.equals(expected));
        System.out.println("--------------");
    }
}
