package leetcode;

import java.util.*;

/**
 * 187. Repeated DNA Sequences
 * Medium
 * ----------------
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'.
 *
 * For example, "ACGAATTCCG" is a DNA sequence.
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
 * 1 <= s.length <= 105
 * s[i] is either 'A', 'C', 'G', or 'T'.
 */
public class Repeated_DNA_Sequences_187 {

    public static List<String> findRepeatedDnaSequences(String s) {
        return findRepeatedDnaSequences_2(s);
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
    }

    private static void do_func(String s, List<String> expected) {
        List<String> ret = findRepeatedDnaSequences(s);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
