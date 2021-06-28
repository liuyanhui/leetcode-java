package leetcode;

/**
 * 318. Maximum Product of Word Lengths
 * Medium
 * ---------------------
 * Given a string array words, return the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. If no such two words exist, return 0.
 *
 * Example 1:
 * Input: words = ["abcw","baz","foo","bar","xtfn","abcdef"]
 * Output: 16
 * Explanation: The two words can be "abcw", "xtfn".
 *
 * Example 2:
 * Input: words = ["a","ab","abc","d","cd","bcd","abcd"]
 * Output: 4
 * Explanation: The two words can be "ab", "cd".
 *
 * Example 3:
 * Input: words = ["a","aa","aaa","aaaa"]
 * Output: 0
 * Explanation: No such pair of words.
 *
 * Constraints:
 * 2 <= words.length <= 1000
 * 1 <= words[i].length <= 1000
 * words[i] consists only of lowercase English letters.
 */
public class Maximum_Product_of_Word_Lengths_318 {
    /**
     * 思路如下：
     * 1.是否存在common letters。用bitmap判断两个string是否存在公用字母，按位与即可。只有小写字母，所以int就够用了。
     * 2.计算乘积
     * 3.获取乘积的最大值
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 99.32% of Java online submissions
     * Memory Usage: 38.9 MB, less than 79.49% of Java online submissions.
     *
     * @param words
     * @return
     */
    public static int maxProduct(String[] words) {
        if (words == null || words.length == 0) return 0;
        int[] bitmapArr = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            bitmapArr[i] = convetToInt(words[i]);
        }
        int ret = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((bitmapArr[i] & bitmapArr[j]) == 0) {
                    ret = Math.max(ret, words[i].length() * words[j].length());
                }
            }
        }
        return ret;
    }

    private static int convetToInt(String word) {
        if (word == null || word.trim().equals("")) return 0;
        int ret = 0;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            c = 1 << c;
            ret |= c;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new String[]{"abcw", "baz", "foo", "bar", "xtfn", "abcdef"}, 16);
        do_func(new String[]{"a", "ab", "abc", "d", "cd", "bcd", "abcd"}, 4);
        do_func(new String[]{"a", "aa", "aaa", "aaaa"}, 0);
    }

    private static void do_func(String[] words, int expected) {
        int ret = maxProduct(words);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
