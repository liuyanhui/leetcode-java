package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 68. Text Justification
 * Hard
 * ------------------------
 * Given an array of strings words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.
 *
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.
 *
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line does not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 *
 * For the last line of text, it should be left-justified and no extra space is inserted between words.
 *
 * Note:
 * A word is defined as a character sequence consisting of non-space characters only.
 * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
 * The input array words contains at least one word.
 *
 * Example 1:
 * Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
 * Output:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 *
 * Example 2:
 * Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
 * Output:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * Explanation: Note that the last line is "shall be    " instead of "shall     be", because the last line must be left-justified instead of fully-justified.
 * Note that the second line is also left-justified becase it contains only one word.
 *
 * Example 3:
 * Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
 * Output:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 *
 * Constraints:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] consists of only English letters and symbols.
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 */
public class Text_Justification_68 {
    /**
     * 金矿：要写出伪代码，尤其是逻辑比较复杂的场景。伪代码pseudocode要详细，内部模块也要用伪代码写出。
     *
     * 思路：先根据maxWidth确定该行共包含哪些words，再填充空格。
     * 伪代码如下：
     * function fullJustify(){
     *     R is a list contains words which represents each row in final result.
     *     WC is the charater count in each row
     *     for(W in words){
     *         if(WC+W.length>=maxWidth){
     *             fill the R with space
     *             reset WC and R
     *         }
     *
     *         W append to R
     *         WC+=word.length+1
     *
     *         if( W is the last one){
     *             fill the R with space
     *         }
     *     }
     *     R to RESULTLIST
     *     return RESULTLIST
     * }
     * function fill_except_last_row(List R,int maxWidth){
     *     //first calc the space count which need to fill
     *     CC = character count in R
     *     SC = maxWidth-CC
     *     //fill the space after the words,except the last word
     *     SC/(R.size()-1) is the space count appended to each words in R except the last one
     *     SC%(R.size()-1) is the words count that append one space in R from left to right
     * }
     * function fill_last_row(List R,int maxWidth){
     *     RET is a String
     *     for(s in R){
     *         s+" " append to RET
     *     }
     *     while (length of RET < maxWidth){
     *         " " append to RET
     *     }
     *     return RET
     * }
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 13.95% of Java online submissions for Text Justification.
     * Memory Usage: 39.6 MB, less than 7.42% of Java online submissions for Text Justification.
     *
     * @param words
     * @param maxWidth
     * @return
     */
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ret = new ArrayList<>();
        int wc = 0;
        List<String> row = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            //确定一行有几个words
            if (wc + words[i].length() > maxWidth) {
                String str = fillExceptLastRow(row, maxWidth);
                ret.add(str);

                //重置中间变量
                row = new ArrayList<>();
                wc = 0;
            }

            row.add(words[i]);
            wc += words[i].length() + 1;

            //最后一行处理
            if (i == words.length - 1) {
                String str = fillLastRow(row, maxWidth);
                ret.add(str);
            }
        }
        return ret;
    }

    private static String fillExceptLastRow(List<String> row, int maxWidth) {
        StringBuilder ret = new StringBuilder();
        int cc = row.stream().mapToInt(String::length).sum();
        int sc = maxWidth - cc;
        //兼容只有一个行的情况。row.size()=1时也需要填充空格
        int divisor = row.size() == 1 ? 1 : row.size() - 1;
        StringBuilder spaceSB = new StringBuilder();
        for (int i = 0; i < sc / divisor; i++) {
            spaceSB.append(" ");
        }
        int remain = sc % divisor;
        //the last item must not append spaces
        for (int i = 0; i < row.size(); i++) {
            ret.append(row.get(i));
            if (i < divisor) ret.append(spaceSB.toString());//注意最后一个word不用加空格
            if (remain-- > 0) ret.append(" ");
        }
        return ret.toString();
    }

    private static String fillLastRow(List<String> row, int maxWidth) {
        //需要考虑最后一行只有一个word，且word.length=maxWidth的情况
        if (row.size() == 1 && row.get(0).length() == maxWidth) return row.get(0);

        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < row.size(); i++) {
            ret.append(row.get(i));
            if (i < row.size() - 1) ret.append(" ");//注意最后一个word不用加空格
        }
        while (ret.length() < maxWidth) {
            ret.append(" ");
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        do_func(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16, new String[]{"This    is    an", "example  of text", "justification.  "});
        do_func(new String[]{"What", "must", "be", "acknowledgment", "shall", "be"}, 16, new String[]{"What   must   be", "acknowledgment  ", "shall be        "});
        do_func(new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"}, 20, new String[]{"Science  is  what we", "understand      well", "enough to explain to", "a  computer.  Art is", "everything  else  we", "do                  "});
        do_func(new String[]{"This"}, 16, new String[]{"This            "});
    }

    private static void do_func(String[] words, int maxWidth, String[] expected) {
        List<String> ret = fullJustify(words, maxWidth);
        System.out.println(ret);
        ArrayUtils.printIntArray(expected);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }

}
