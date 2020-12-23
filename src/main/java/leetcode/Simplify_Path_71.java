package leetcode;

/**
 * https://leetcode.com/problems/simplify-path/
 * 71. Simplify Path
 * Medium
 * --------------------
 * Given an absolute path for a file (Unix-style), simplify it. Or in other words, convert it to the canonical path.
 * In a UNIX-style file system, a period '.' refers to the current directory. Furthermore, a double period '..' moves the directory up a level.
 * Note that the returned canonical path must always begin with a slash '/', and there must be only a single slash '/' between two directory names. The last directory name (if it exists) must not end with a trailing '/'. Also, the canonical path must be the shortest string representing the absolute path.
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
        return simplifyPath_2(path);
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
