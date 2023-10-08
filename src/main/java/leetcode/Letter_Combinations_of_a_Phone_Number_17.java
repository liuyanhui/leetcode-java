package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 17. Letter Combinations of a Phone Number
 * Medium
 * ------------------------------
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Example 1:
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 * Input: digits = ""
 * Output: []
 *
 * Example 3:
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 * Constraints:
 * 0 <= digits.length <= 4
 * digits[i] is a digit in the range ['2', '9'].
 */
public class Letter_Combinations_of_a_Phone_Number_17 {
    public static List<String> letterCombinations(String digits) {
        return letterCombinations_2(digits);
    }

    static List<String> panel = new ArrayList<String>() {{
        add("");
        add("");
        add("abc");
        add("def");
        add("ghi");
        add("jkl");
        add("mno");
        add("pqrs");
        add("tuv");
        add("wxyz");
    }};

    /**
     * round 2
     *
     * 验证通过：
     * Runtime 1 ms Beats 79.56%
     * Memory 41.3 MB Beats 29.25%
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations_2(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0)
            return res;
        //遍历当前按钮的字母
        String root = panel.get(digits.charAt(0) - '0');
        for (int i = 0; i < root.length(); i++) {
            //提取按钮的字母作为前缀
            String prefix = String.valueOf(root.charAt(i));
            //递归计算剩余按钮
            List<String> t = letterCombinations_2(digits.substring(1));
            //合并
            if (t.size() > 0) {
                for (String suffix : t) {
                    res.add(prefix + suffix);
                }
            } else {
                res.add(prefix);
            }
        }
        return res;
    }

    /**
     * round 2
     * 简单粗暴的思路，依次合并两个list
     * 验证通过：
     * Runtime: 2 ms, faster than 52.34% of Java .
     * Memory Usage: 37.7 MB, less than 83.68% of Java
     * @param digits
     * @return
     */
    public static List<String> letterCombinations_1(String digits) {
        List<String> ret = new ArrayList<>();
        if (digits == null || digits.length() == 0) return ret;
        List<String>[] numToLetter = new List[10];
        numToLetter[0] = new ArrayList<>();
        numToLetter[1] = new ArrayList<>();
        numToLetter[2] = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};
        numToLetter[3] = new ArrayList<String>() {{
            add("d");
            add("e");
            add("f");
        }};
        numToLetter[4] = new ArrayList<String>() {{
            add("g");
            add("h");
            add("i");
        }};
        numToLetter[5] = new ArrayList<String>() {{
            add("j");
            add("k");
            add("l");
        }};
        numToLetter[6] = new ArrayList<String>() {{
            add("m");
            add("n");
            add("o");
        }};
        numToLetter[7] = new ArrayList<String>() {{
            add("p");
            add("q");
            add("r");
            add("s");
        }};
        numToLetter[8] = new ArrayList<String>() {{
            add("t");
            add("u");
            add("v");
        }};
        numToLetter[9] = new ArrayList<String>() {{
            add("w");
            add("x");
            add("y");
            add("z");
        }};
        for (int i = 0; i < digits.length(); i++) {
            if (i == 0) {
                ret = numToLetter[digits.charAt(i) - '0'];
            } else {
                ret = combinate(ret, numToLetter[digits.charAt(i) - '0']);
            }
        }
        return ret;
    }

    private static List<String> combinate(List<String> list1, List<String> list2) {
        List<String> ret = new ArrayList<>();
        for (String s1 : list1) {
            for (String s2 : list2) {
                ret.add(s1 + s2);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func("23", new String[]{"ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"});
        do_func("2", new String[]{"a", "b", "c"});
        do_func("1", new String[]{});
        do_func("0", new String[]{});
        do_func("4", new String[]{"g", "h", "i"});
        do_func("2398", new String[]{"adwt", "adwu", "adwv", "adxt", "adxu", "adxv", "adyt", "adyu", "adyv", "adzt", "adzu", "adzv", "aewt", "aewu", "aewv", "aext", "aexu", "aexv", "aeyt", "aeyu", "aeyv", "aezt", "aezu", "aezv", "afwt", "afwu", "afwv", "afxt", "afxu", "afxv", "afyt", "afyu", "afyv", "afzt", "afzu", "afzv", "bdwt", "bdwu", "bdwv", "bdxt", "bdxu", "bdxv", "bdyt", "bdyu", "bdyv", "bdzt", "bdzu", "bdzv", "bewt", "bewu", "bewv", "bext", "bexu", "bexv", "beyt", "beyu", "beyv", "bezt", "bezu", "bezv", "bfwt", "bfwu", "bfwv", "bfxt", "bfxu", "bfxv", "bfyt", "bfyu", "bfyv", "bfzt", "bfzu", "bfzv", "cdwt", "cdwu", "cdwv", "cdxt", "cdxu", "cdxv", "cdyt", "cdyu", "cdyv", "cdzt", "cdzu", "cdzv", "cewt", "cewu", "cewv", "cext", "cexu", "cexv", "ceyt", "ceyu", "ceyv", "cezt", "cezu", "cezv", "cfwt", "cfwu", "cfwv", "cfxt", "cfxu", "cfxv", "cfyt", "cfyu", "cfyv", "cfzt", "cfzu", "cfzv"});
    }

    private static void do_func(String digits, String[] expected) {
        List<String> ret = letterCombinations(digits);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
