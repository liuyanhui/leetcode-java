package leetcode;

import java.util.*;

/**
 * 187. Repeated DNA Sequences
 * Medium
 * ----------------
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
 * For example, "ACGAATTCCG" is a DNA sequence.
 *
 * When studying DNA, it is useful to identify repeated sequences within the DNA.
 *
 * Given a string s that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule. You may return the answer in any order.
 *
 * Example 1:
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 *
 * Example 2:
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s[i] is either 'A', 'C', 'G', or 'T'.
 */
public class Repeated_DNA_Sequences_187 {

    public static List<String> findRepeatedDnaSequences(String s) {
        return findRepeatedDnaSequences_3(s);
    }

    /**
     * round 2
     * 分析问题：
     * 1.10 letter long
     * 2.more than once
     * 3.只有四种字符
     *
     * 暴力思路：
     * 滑动窗口逐个提取10个字符的子串，判断该子串是否有重复。
     * 时间复杂度：O(N*N)，空间复杂度：O(1)
     *
     * 哈希表思路：
     * 1.用缓存保存已经出现过的子串
     * 2.滑动窗口依次提取10个字符的子串，子串计算hashcode，如果hashcode存在缓存中，表示该子串重复出现，子串计入返回结果集中。
     * 3.结果集去重。缓存可以记录子串重复的次数，出现次数大于1，则不再计入返回结果集中。
     * 时间复杂度：O(N)，空间复杂度：O(N)
     *
     * 验证通过：
     * Runtime: 33 ms, faster than 68.39% of Java online submissions for Repeated DNA Sequences.
     * Memory Usage: 64.1 MB, less than 36.31% of Java online submissions for Repeated DNA Sequences.
     *
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences_3(String s) {
        List<String> ret = new ArrayList<>();
        if (s == null || s.length() <= 10) return ret;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String t = s.substring(i, i + 10);
            if (map.containsKey(t)) {
                if (map.get(t) < 2) {
                    map.put(t, 2);
                    ret.add(t);
                }
            } else {
                map.put(t, 1);
            }
        }
        return ret;
    }

    /**
     * 极简版代码
     * 参考思路：
     * https://leetcode.com/problems/repeated-dna-sequences/discuss/53855/7-lines-simple-Java-O(n)
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences_2(String s) {
        Set seen = new HashSet(), repeated = new HashSet<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String t = s.substring(i, i + 10);
//            if (seen.contains(t)) {
//                repeated.add(t);
//            }
//            seen.add(t);
            if (!seen.add(t))
                repeated.add(t);
        }
        return new ArrayList<>(repeated);
    }

    /**
     * 采用滑动窗口+map的方式
     * 1.遍历字符串，类似滑动窗口，每次读取10个字母。
     * 2.使用Map缓存出现过的字符串<String,Integer> key:字符串，value:出现次数
     *
     * 验证通过：
     * Runtime: 18 ms, faster than 50.46% of Java online submissions for Repeated DNA Sequences.
     * Memory Usage: 48.2 MB, less than 16.01% of Java online submissions for Repeated DNA Sequences.
     * @param s
     * @return
     */
    public static List<String> findRepeatedDnaSequences_1(String s) {
        List<String> ret = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String t = s.substring(i, i + 10);
            if (map.containsKey(t)) {
                if (map.get(t) == 1) {
                    ret.add(t);
                    map.put(t, 2);
                }
            } else {
                map.put(t, 1);
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", Arrays.asList("AAAAACCCCC", "CCCCCAAAAA"));
        do_func("A", new ArrayList<>());
        do_func("AAAAAAAAAA", new ArrayList<>());
        do_func("AAAAAAAAAAA", Arrays.asList("AAAAAAAAAA"));
        do_func("AAAAAAAAAAAAA", Arrays.asList("AAAAAAAAAA"));
        do_func("AAAAACCCCCAAAAACCCCCAAAAAGGGTTT", Arrays.asList("AAAAACCCCC", "AAAACCCCCA", "AAACCCCCAA", "AACCCCCAAA", "ACCCCCAAAA", "CCCCCAAAAA"));
    }

    private static void do_func(String s, List<String> expected) {
        List<String> ret = findRepeatedDnaSequences(s);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
