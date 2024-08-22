package leetcode;

/**
 * 151. Reverse Words in a String
 * Medium
 * -------------------------------
 * Given an input string s, reverse the order of the words.
 * A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
 * Return a string of the words in reverse order concatenated by a single space.
 * Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.
 *
 * Example 1:
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 *
 * Example 2:
 * Input: s = "  hello world  "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 *
 * Example 3:
 * Input: s = "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * s contains English letters (upper-case and lower-case), digits, and spaces ' '.
 * There is at least one word in s.
 *
 * Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?
 */
public class Reverse_Words_in_a_String_151 {
    public static String reverseWords(String s) {
        return reverseWords_r3_1(s);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. naive solution
     * 利用split(" ")函数把输入切割成数组，过滤数组中的空元素和数字元素，反转和补空格把数组输出为字符串即可。
     * Time Complexity:O(N)
     * Space Complexity:O(N)
     * 2. 当String为mutable时，即输入为char数组时的情况。
     * 2.1. 数组分为三个部分：左—>已计算；中->未计算；右->已计算。
     * 2.2. 先过滤（空格和数字）、压缩（多余空格）、左移（左对齐）；再反转。
     * 2.3. 过滤+压缩算法：
     * 设tail为已计算部分的末尾下标+1
     * 遍历char数组c，游标为i
     * IF c[i] is letter THEN swap(i,tail),i++,tail++
     * ELSE IF c[i] is digit THEN i++
     * ELSE IF c[i] is space THEN
     * 	IF c[tail-1] is space THEN i++
     * 	ELSE swap(i,tail),tail++,i++
     * 2.4. 为了不增加额外的存储空间，反转时需要移动字符，故反转的时间复杂度为O(N*N)
     * 2.5. 过于复杂，放弃
     * Time Complexity:O(N*N)
     * Space Complexity:O(1)
     * 3.review 参考Leetcode中的提交
     * 先反转字符串，再反转每个单词，最后删除空格和数字并压缩
     * Time Complexity:O(N)
     * Space Complexity:O(1)
     *
     * 【3.】才是最优方案
     * 题目中word的定义是连续不为空的序列，可以包含数字
     *
     * 验证通过：
     * Runtime 3 ms Beats 98.76%
     * Memory 42.07 MB Beats 99.55%
     *
     * @param s
     * @return
     */
    public static String reverseWords_r3_1(String s) {
        if (s == null || s.length() == 0) return s;
        char[] arr = s.toCharArray();

        //反转字符串
        reverse(arr, 0, arr.length - 1);
        //反转每个word
        int i = 0, j = 0;
        while (j < arr.length) {
            //截取word
            while (j < arr.length && arr[j] != ' ') {
                j++;
            }
            //反转word
            if (i < j - 1 || j == arr.length) {
                reverse(arr, i, j - 1);
            }
            j++;
            i = j;
        }
        //删除空格，并压缩
        int end = 0;
        int cur = 0;
        while (cur < arr.length) {
            if (arr[cur] == ' ') {
                if (end > 0 && arr[end - 1] != ' ') {
                    arr[end++] = arr[cur];
                }
            } else {
                arr[end++] = arr[cur];
            }
            cur++;
        }
        //结尾有多个space的特殊情况处理
        if (end > 0 && arr[end - 1] == ' ') {
            end -= 1;
        }

        return String.copyValueOf(arr, 0, end);
    }

    private static void reverse(char[] str, int beg, int end) {
        if (beg >= end) return;
        int i = beg, j = end;
        while (i < j) {
            char t = str[i];
            str[i] = str[j];
            str[j] = t;
            i++;
            j--;
        }
    }

    /**
     * 直观思路：
     * 1.根据空格把s切割成数组
     * 2.从后向前遍历数组，过滤空字符串，把字符串加入结果集中
     * 这个思路空间复杂度为O(N)，时间复杂读O(N)
     *
     * 指针截取word+从后向前遍历思路
     * 算法：
     * 1.end=s.length
     * 2.从后向前遍历s
     * 2.1.如果s[i]!=' '，那么i--
     * 2.2.如果s[i]==' '，
     * 2.2.1.如果s[i+1,end]为空，那么end=i,i--
     * 2.2.2.如果s[i+1,end]不为空，那么s[i+1,end]加入结果集，end=i,i--
     *
     * 这个思路空间复杂度O(1)，时间复杂读O(N)
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 97.82% of Java online submissions for Reverse Words in a String.
     * Memory Usage: 43.7 MB, less than 45.99% of Java online submissions for Reverse Words in a String.
     *
     * @param s
     * @return
     */
    public static String reverseWords_1(String s) {
        StringBuilder sb = new StringBuilder();
        int end = s.length();
        boolean needSpace = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (i + 1 < end) {
                    if (needSpace) {
                        sb.append(" ");
                    } else {
                        needSpace = true;
                    }
                    sb.append(s.substring(i + 1, end));

                }
                end = i;
            }
        }
        if (end > 0) {
            if (needSpace) {
                sb.append(" ");
            } else {
                needSpace = true;
            }
            sb.append(s.substring(0, end));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        do_func("the sky is blue", "blue is sky the");
        do_func("  hello world  ", "world hello");
        do_func("a good   example", "example good a");
        do_func("  ", "");
        do_func("good", "good");
    }

    private static void do_func(String s, String expected) {
        String ret = reverseWords(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
