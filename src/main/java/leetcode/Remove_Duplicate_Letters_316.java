package leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 316. Remove Duplicate Letters
 * Medium
 * -------------------------
 * Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is the smallest in lexicographical order among all possible results.
 * <p>
 * Example 1:
 * Input: s = "bcabc"
 * Output: "abc"
 * <p>
 * Example 2:
 * Input: s = "cbacdcbc"
 * Output: "acdb"
 * <p>
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of lowercase English letters.
 * <p>
 * Note: This question is the same as 1081: https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
 */
public class Remove_Duplicate_Letters_316 {
    public static String removeDuplicateLetters(String s) {
        return removeDuplicateLetters_r3_1(s);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * [group] 与 Smallest_Subsequence_of_Distinct_Characters_1081 一毛一样
     * https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/description/
     * <p>
     * 参考：
     * removeDuplicateLetters_3 和 removeDuplicateLetters_1
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 94.53%
     * Memory 42.16 MB Beats 85.87%
     */
    public static String removeDuplicateLetters_r3_1(String s) {
        int[] cnt = new int[26];
        int[] seen = new int[26];
        Stack<Character> stack = new Stack<>();
        //统计字母出现次数
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        //遍历字符串s
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (seen[c - 'a'] == 0) {//review 这里很重要。不在stack中的字符才需要进行这些逻辑判断。
                while (!stack.empty() && c < stack.peek() && cnt[stack.peek() - 'a'] > 0) {
                    char pop = stack.pop();
                    seen[pop - 'a']--;
                }
                stack.push(c);
                seen[c - 'a']++;
            }
            cnt[c - 'a']--;
        }

        StringBuilder res = new StringBuilder();
        while (!stack.empty()) {
            res.append(stack.pop());
        }

        return res.reverse().toString();
    }

    /**
     * review round 2
     * 参考资料：
     * https://leetcode.com/problems/remove-duplicate-letters/solutions/1859410/java-c-detailed-visually-explained/
     * removeDuplicateLetters_1()
     * <p>
     * 算法大意：
     * 1.统计所有字母的出现次数，记录到数组count[]中。利用Stack保存最终的结果。利用seen[]保存字母是否已经在stack中出现。
     * 2.遍历字符串，记为c
     * 2.1.当字母未出现过时，
     * 2.1.1.当 c<stack.peek() 时，循环处理栈顶元素，设t=stack.peek()
     * 2.1.1.1.当 count[t]>0 时，count[t]--;stack.pop();seen[t]=0;//如果count[c]>1表示该字母在后面还会出现，根据字母排序的要求，可以临时从stack中删除
     * 2.1.2.当 c>stack.peek() 且 seen[c]==0 时，stack.push(c);seen[c]=1;//这里隐含的前提是，stack已经是全局最优解的子集了。
     * 2.1.3.当 c==stack.peek()时，do nothing;
     * 2.2.count[c]--;
     * <p>
     * TIP:栈顶字母如果后面不会再出现，那么该字母以及它前面入栈的字母都不能变化（出栈）；如果栈顶字母后面还会再出现，那么需要根据该栈顶字母和当前字母进行判断。
     * <p>
     * 验证通过：
     * Runtime 4 ms Beats 41.29%
     * Memory 42.7 MB Beats 22.59%
     *
     * @param s
     * @return
     */
    public static String removeDuplicateLetters_3(String s) {
        StringBuilder res = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int[] cnt = new int[26];
        int[] seen = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (seen[c - 'a'] <= 0) {
                while (!stack.empty() && c < stack.peek() && cnt[stack.peek() - 'a'] > 0) {
                    seen[stack.pop() - 'a']--;
                }
                stack.push(c);
                seen[c - 'a']++;
            }
            cnt[c - 'a']--;
        }
        while (!stack.empty()) {
            res.append(stack.pop());
        }
        return res.reverse().toString();
    }

    /**
     * round 2
     * 思考：
     * 1.分为两个部分：a.字母去重;b.按字母序的最小值排序
     * 2.去重部分。由于只有lowercase English letters，共26个，用数组就可以去重。
     * 3.去重时，记录每个字母出现的位置。如何记录？始终记录最小的允许出现的位置，然后以当前出现的位置比较。即每当字母出现时，更新该字母的当前最优解。
     * 4.分析局部最优解和全局最优解的关系。在局部最优解的基础上对新字母进行计算，算法为：如果新字母不在局部最优中，那么追加到局部最优解后面；否则，需要针对局部最优解和删除新字母且追加新字母之后的局部最优解进行字母序比较，已确定最新的局部最优解。
     * <p>
     * 验证失败：
     * 这个解法中局部最优解的选择会影响全局最优解，全局最优解可能不在局部最优解的解空间中。所以导致最终结果的错误。
     *
     * @param s
     * @return
     */
    public static String removeDuplicateLetters_2(String s) {
        StringBuilder res = new StringBuilder();
        Character[] arr = new Character[26];
        Set<Character> seen = new HashSet<>();

        int idx = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!seen.contains(c)) {
                arr[idx++] = c;
                seen.add(c);
            } else {
                //clone arr并更新局部最优解中的新字母
                Character[] newarr = new Character[26];
                int found = 0;
                for (int j = 0; j < 26 && arr[j] != null; j++) {
                    if (arr[j] != c) {
                        newarr[found++] = arr[j];
                    }
                }
                newarr[found] = c;
                //字母序比较局部最优解数组和局部最优解更新后的数组
                for (int j = 0; j < 26 && arr[j] != null; j++) {
                    if (arr[j] != newarr[j]) {
                        arr = (arr[j] < newarr[j] ? arr : newarr);
                        break;
                    }
                }
            }
        }

        for (Character c : arr) {
            if (c == null) break;
            res.append(c.toString());
        }
        return res.toString();
    }

    /**
     * 参考思路：
     * https://leetcode-cn.com/problems/remove-duplicate-letters/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-4/
     * https://leetcode.com/problems/remove-duplicate-letters/discuss/76769/Java-solution-using-Stack-with-comments
     * <p>
     * 思路：
     * 1.使用单调栈，确保栈中的字符满足局部最优解，即按照字母序+原始串中的先后顺序存放
     * 2.因为栈中已经是局部最优解，所以只需要考虑下一个字母和栈顶字母的情况即可。这样降低了算法复杂度。
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 78.66% of Java online submissions for Remove Duplicate Letters.
     * Memory Usage: 39.4 MB, less than 23.36% of Java online submissions for Remove Duplicate Letters.
     *
     * @param s
     * @return
     */
    public static String removeDuplicateLetters_1(String s) {
        int[] count = new int[26];
        int[] stackCached = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stackCached[c - 'a'] <= 0) {
                while (!stack.empty() && stack.peek() > c && count[stack.peek() - 'a'] > 0) {
                    stackCached[stack.pop() - 'a']--;
                }
                stack.push(c);
                stackCached[c - 'a']++;
            }
            count[c - 'a']--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(String.valueOf(stack.pop()));
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        do_func("bcabc", "abc");
        do_func("cbacdcbc", "acdb");
        do_func("bcabac", "abc");
        do_func("bcabbbbbbbc", "abc");
        do_func("edebbed", "bed");
    }

    private static void do_func(String s, String expected) {
        String ret = removeDuplicateLetters(s);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
