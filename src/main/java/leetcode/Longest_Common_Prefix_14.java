package leetcode;

/**
 * https://leetcode.com/problems/longest-common-prefix/
 * 14. Longest Common Prefix
 * Easy
 * ----------------------------
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 *  Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 *
 * Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 *
 * Constraints:
 * 0 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] consists of only lower-case English letters.
 */
public class Longest_Common_Prefix_14 {

    public static String longestCommonPrefix(String[] strs) {
        return longestCommonPrefix_4(strs);
    }

    /**
     * round 3
     *
     * 验证通过：
     * untime 1 ms Beats 82.83%
     * Memory 40.1 MB Beats 77.16%
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_4(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int i = 0;
        while (i < strs[0].length()) {
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[0].charAt(i) != strs[j].charAt(i)) {
                    return strs[0].substring(0, i);
                }
            }
            i++;
        }
        return i == 0 ? "" : strs[0].substring(0, i);
    }

    /**
     * round 2
     * 1.初始时把第0个字符串作为最长公共子串lcp
     * 2.遍历数组，lcp依次跟每个字符串比较，得到最新的lcp
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 68.45% of Java
     * Memory Usage: 36.9 MB, less than 87.04% of Java
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_3(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String lcp = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            while (j < lcp.length() && j < strs[i].length()
                    && lcp.charAt(j) == strs[i].charAt(j)) {
                j++;
            }
            lcp = lcp.substring(0, j);
        }
        return lcp;
    }

    /**
     * longestCommonPrefix_1的精简版
     * 参考思路：https://leetcode.com/problems/longest-common-prefix/solution/中的Approach 2: Vertical scanning
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int index = 0;
        while (index < strs[0].length()) {
            for (int i = 0; i < strs.length; i++) {
                if (index == strs[i].length() || strs[0].charAt(index) != strs[i].charAt(index)) {
                    return strs[0].substring(0, index);
                }
            }
            index++;
        }
        return strs[0];
    }

    /**
     * 验证通过，>25mins
     * Runtime: 8 ms, faster than 18.45% of Java online submissions for Longest Common Prefix.
     * Memory Usage: 39.1 MB, less than 14.64% of Java online submissions for Longest Common Prefix.
     * @param strs
     * @return
     */
    public static String longestCommonPrefix_1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String ret = "";
        int index = 0;
        boolean finish = false;
        while (index < strs[0].length()) {
            for (int i = 0; i < strs.length; i++) {
                if (index >= strs[i].length()) {
                    finish = true;
                    break;
                }
                if (strs[0].charAt(index) != strs[i].charAt(index)) {
                    finish = true;
                    break;
                }
                if (i == strs.length - 1) {
                    ret += strs[0].charAt(index);
                }
            }
            index++;
            if (finish) {
                break;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new String[]{"flower", "flow", "flight"}, "fl");
        do_func(new String[]{"dog", "racecar", "car"}, "");
        do_func(new String[]{"flower", " ", "flight"}, "");
        do_func(new String[]{"flower", "", "flight"}, "");
        do_func(new String[]{"flower", "flow", "flowtyuijhghj"}, "flow");
        do_func(new String[]{"a"}, "a");
    }

    private static void do_func(String[] strs, String expected) {
        String ret = longestCommonPrefix(strs);
        System.out.println(ret);
        System.out.println(ret.equals(expected));
        System.out.println("--------------");
    }
}
