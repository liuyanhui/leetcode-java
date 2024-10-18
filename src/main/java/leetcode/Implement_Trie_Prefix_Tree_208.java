package leetcode;

/**
 * 208. Implement Trie (Prefix Tree)
 * Medium
 * ----------------------------------------
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
 * <p>
 * Implement the Trie class:
 * - Trie() Initializes the trie object.
 * - void insert(String word) Inserts the string word into the trie.
 * - boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
 * - boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 * <p>
 * Example 1:
 * Input
 * ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 * [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 * Output
 * [null, null, true, false, true, null, true]
 * <p>
 * Explanation
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // return True
 * trie.search("app");     // return False
 * trie.startsWith("app"); // return True
 * trie.insert("app");
 * trie.search("app");     // return True
 * <p>
 * Constraints:
 * 1 <= word.length, prefix.length <= 2000
 * word and prefix consist only of lowercase English letters.
 * At most 3 * 10^4 calls in total will be made to insert, search, and startsWith.
 */
public class Implement_Trie_Prefix_Tree_208 {

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     *
     * 验证通过：
     * Runtime 39 ms Beats 42.82%
     * Memory 54.98 MB Beats 90.82%
     */
    static class Trie {
        Trie[] successors;
        boolean isWord;

        public Trie() {
            successors = new Trie[26];
            isWord = false;
        }

        public void insert(String word) {
            if (word == null || word.length() == 0) return;
            Trie cur = this;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.successors[idx] == null) {
                    cur.successors[idx] = new Trie();
                }
                if (i == word.length() - 1) {
                    cur.successors[idx].isWord = true;
                }
                cur = cur.successors[idx];
            }
        }

        public boolean search(String word) {
            if (word == null || word.length() == 0) return false;
            boolean isWord = false;
            Trie cur = this;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur == null || cur.successors[idx] == null) {
                    return false;
                } else {
                    cur = cur.successors[idx];
                    isWord = cur.isWord;
                }
            }
            return isWord;
        }

        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.length() == 0) return false;
            Trie cur = this;
            for (int i = 0; i < prefix.length(); i++) {
                int idx = prefix.charAt(i) - 'a';
                if (cur == null || cur.successors[idx] == null) {
                    return false;
                } else {
                    cur = cur.successors[idx];
                }
            }
            return true;
        }
    }

    /**
     * review
     * 套路
     * 需要注意Trie的数据结构定义，其他都比较简单。
     * <p>
     * 验证通过：
     * Runtime: 34 ms, faster than 99.90% of Java online submissions for Implement Trie (Prefix Tree).
     * Memory Usage: 50.7 MB, less than 98.15% of Java online submissions for Implement Trie (Prefix Tree).
     */
//    static class Trie {
//        //FIXME: 这两个变量是重点
//        private Trie[] values;
//        private boolean isWord;
//
//        public Trie() {
//            values = new Trie[26];
//        }
//
//        public void insert(String word) {
//            if (word == null || word.length() == 0) return;
//            Trie tmp = this;
//            for (int i = 0; i < word.length(); i++) {
//                int idx = word.charAt(i) - 'a';
//                if (tmp.values[idx] == null) {
//                    tmp.values[idx] = new Trie();
//                }
//                tmp = tmp.values[idx];
//            }
//            tmp.isWord = true;
//        }
//
//        public boolean search(String word) {
//            if (word == null || word.length() == 0) return false;
//            Trie tmp = this;
//            for (int i = 0; i < word.length(); i++) {
//                int idx = word.charAt(i) - 'a';
//                if (tmp.values[idx] == null) return false;
//                tmp = tmp.values[idx];
//            }
//            return tmp.isWord;
//        }
//
//        public boolean startsWith(String prefix) {
//            if (prefix == null || prefix.length() == 0) return false;
//            Trie tmp = this;
//            for (int i = 0; i < prefix.length(); i++) {
//                int idx = prefix.charAt(i) - 'a';
//                if (tmp.values[idx] == null) return false;
//                tmp = tmp.values[idx];
//            }
//            return true;
//        }
//    }
    public static void main(String[] args) {
        boolean ret = false;
        Trie trie = new Trie();
        trie.insert("apple");
        ret = trie.search("apple");   // return True
        System.out.println(ret == true);
        ret = trie.search("app");     // return False
        System.out.println(ret == false);
        ret = trie.startsWith("app"); // return True
        System.out.println(ret == true);
        trie.insert("app");
        ret = trie.search("app");     // return True
        System.out.println(ret == true);
    }


/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
}
