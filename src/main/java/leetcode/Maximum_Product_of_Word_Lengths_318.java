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
    public static int maxProduct(String[] words) {
        return maxProduct_2(words);
    }

    /**
     * round 2
     * 思考：
     * 1.暴力法。遍历words，过滤出不包含当前word中letter的单词，然后依次求长度乘积，并记录最大值。时间复杂度：O(N*N)
     * 2.使用bitmap保存word中字母是否出现，进而通过bit manipulation判断是否有common letters
     *
     * 验证通过：
     * Runtime 12 ms Beats 76.8%
     * Memory 43.9 MB Beats 72.44%
     *
     * @param words
     * @return
     */
    public static int maxProduct_2(String[] words) {
        //用位图法记录特征值
        int[] features = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                int idx = words[i].charAt(j) - 'a';
                features[i] = features[i] | (1 << idx);
            }
        }
        //遍历、过滤、计算长度乘积、记录最大值
        int res = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((features[i] & features[j]) == 0) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        return res;
    }

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
    public static int maxProduct_1(String[] words) {
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
