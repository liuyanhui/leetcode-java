package leetcode;

/**
 * 套路 Trie tree
 * 211. Design Add and Search Words Data Structure
 * Medium
 * ----------------------------
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 * <p>
 * Implement the WordDictionary class:
 * - WordDictionary() Initializes the object.
 * - void addWord(word) Adds word to the data structure, it can be matched later.
 * - bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 * <p>
 * Example:
 * Input
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output
 * [null,null,null,null,false,true,true,true]
 * <p>
 * Explanation
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 * <p>
 * Constraints:
 * 1 <= word.length <= 25
 * word in addWord consists of lowercase English letters.
 * word in search consist of '.' or lowercase English letters.
 * There will be at most 2 dots in word for search queries.
 * At most 10^4 calls will be made to addWord and search.
 */
public class Design_Add_and_Search_Words_Data_Structure_211 {

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 使用Trie 存储word，然后查找。遇到字符'.'需要遍历该层所有的字母。
     * 2. 使用Hashtable存储word。search时替换'.'为字母，每个'.'共有26种可能。最多有两个'.'的情况下，最多有26*26种情况需要计算。
     *
     * 验证通过：
     * Runtime 203 ms Beats 47.95%
     * Memory 87.88 MB Beats 88.36%
     *
     */
    static class WordDictionary {
        boolean isWord;
        WordDictionary[] successors;
        public WordDictionary() {
            successors = new WordDictionary[26];
        }

        public void addWord(String word) {
            WordDictionary cur = this;
            for (int i = 0; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (cur.successors[idx] == null) {
                    cur.successors[idx] = new WordDictionary();
                }
                if (i == word.length() - 1) {
                    cur.successors[idx].isWord = true;
                }
                cur = cur.successors[idx];
            }
        }

        public boolean search(String word) {
            return match(word, 0, this);
        }

        private boolean match(String word, int beg, WordDictionary dict) {
            if (beg > word.length()) return false;
            if (beg == word.length()) return dict.isWord;
            if (word.charAt(beg) == '.') {//模糊匹配
                for (int i = 0; i < dict.successors.length; i++) {
                    if (dict.successors[i] == null) continue;
                    if (match(word, beg + 1, dict.successors[i])) return true;
                }
            } else {
                int idx = word.charAt(beg) - 'a';
                if (dict.successors[idx] == null) return false;
                if (match(word, beg + 1, dict.successors[idx])) return true;
            }
            return false;
        }
    }

    /**
     * 思考：
     * 1.Trie Tree + 模糊查找
     * 2.如果Trie Tree的节点不为空，就可以跳过该节点的匹配
     * <p>
     * 验证通过：
     * Runtime: 483 ms, faster than 94.12% of Java online submissions for Design Add and Search Words Data Structure.
     * Memory Usage: 104.7 MB, less than 83.71% of Java online submissions for Design Add and Search Words Data Structure.
     */
    static class WordDictionary_2 {
        private WordDictionary_2[] successor = new WordDictionary_2[26];
        private boolean isWord = false;

        public WordDictionary_2() {

        }

        public void addWord(String word) {
            WordDictionary_2 cur = this;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.successor[c - 'a'] == null) {
                    cur.successor[c - 'a'] = new WordDictionary_2();
                }
                cur = cur.successor[c - 'a'];
                if (i == word.length() - 1) cur.isWord = true;
            }
        }

        public boolean search(String word) {
            return do_search(word, 0, this);
        }

        private boolean do_search(String word, int beg, WordDictionary_2 dict) {
            WordDictionary_2 cur = dict;
            for (int i = beg; i < word.length(); i++) {
                if (cur == null) return false;
                char c = word.charAt(i);
                if (c == '.') {
                    for (int j = 0; j < cur.successor.length; j++) {
                        if (cur.successor[j] == null) continue;
                        if (do_search(word, i + 1, cur.successor[j]))
                            return true;
                    }
                    return false;//这里比较关键
                } else {
                    cur = cur.successor[c - 'a'];
                }
            }
            return cur == null ? false : cur.isWord;
        }

    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/design-add-and-search-words-data-structure/discuss/59554/My-simple-and-clean-Java-code
     * <p>
     * 验证通过：
     * Runtime: 36 ms, faster than 93.75% of Java online submissions for Design Add and Search Words Data Structure.
     * Memory Usage: 49.8 MB, less than 57.31% of Java online submissions for Design Add and Search Words Data Structure.
     */
    static class WordDictionary_1 {
        class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isWord = false;
        }

        private TrieNode root = new TrieNode();

        public void addWord(String word) {
            if (word == null) return;
            TrieNode n = root;
            for (char c : word.toCharArray()) {
                if (n.children[c - 'a'] == null) {
                    n.children[c - 'a'] = new TrieNode();
                }
                n = n.children[c - 'a'];
            }
            n.isWord = true;
        }

        public boolean search(String word) {
            if (word == null) return false;
            return match(word, 0, root);
        }

        private boolean match(String word, int offset, TrieNode node) {
            if (offset >= word.length()) {
                return node.isWord;
            }
            if (word.charAt(offset) == '.') {
                for (TrieNode t : node.children) {
                    if (t != null && match(word, offset + 1, t)) {
                        return true;
                    }
                }
            } else {
                for (int i = offset; i < word.length(); i++) {
                    char c = word.charAt(i);
                    if (node.children[c - 'a'] == null) {
                        return false;
                    } else {
                        return match(word, i + 1, node.children[c - 'a']);
                    }

                }
            }
            return false;
        }
    }

    /**
     * Your WordDictionary object will be instantiated and called as such:
     * WordDictionary obj = new WordDictionary();
     * obj.addWord(word);
     * boolean param_2 = obj.search(word);
     */

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        System.out.println(wordDictionary.search("pad") == false);
        System.out.println(wordDictionary.search("bad") == true);
        System.out.println(wordDictionary.search(".ad") == true);
        System.out.println(wordDictionary.search("b..") == true);
        System.out.println("------------------------");
    }

    private static void test2() {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("a");
        wordDictionary.addWord("a");
        System.out.println(wordDictionary.search(".") == true);
        System.out.println(wordDictionary.search("a") == true);
        System.out.println(wordDictionary.search("aa") == false);
        System.out.println(wordDictionary.search(".a") == false);
        System.out.println(wordDictionary.search("a.") == false);
        System.out.println("------------------------");
    }
}