package leetcode;

/**
 * 套路 Trie tree
 * 211. Design Add and Search Words Data Structure
 * Medium
 * ----------------------------
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 *
 * Implement the WordDictionary class:
 *
 * WordDictionary() Initializes the object.
 * void addWord(word) Adds word to the data structure, it can be matched later.
 * bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 *
 * Example:
 * Input
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output
 * [null,null,null,null,false,true,true,true]
 *
 * Explanation
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 *
 * Constraints:
 * 1 <= word.length <= 500
 * word in addWord consists lower-case English letters.
 * word in search consist of  '.' or lower-case English letters.
 * At most 50000 calls will be made to addWord and search.
 */
public class Design_Add_and_Search_Words_Data_Structure_211 {
    /**
     * 参考思路：
     * https://leetcode.com/problems/design-add-and-search-words-data-structure/discuss/59554/My-simple-and-clean-Java-code
     *
     * 验证通过：
     * Runtime: 36 ms, faster than 93.75% of Java online submissions for Design Add and Search Words Data Structure.
     * Memory Usage: 49.8 MB, less than 57.31% of Java online submissions for Design Add and Search Words Data Structure.
     *
     */
    static class WordDictionary {
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
        System.out.println(wordDictionary.search("a") == true);
        System.out.println(wordDictionary.search(".a") == false);
        System.out.println(wordDictionary.search("a.") == false);
        System.out.println("------------------------");
    }
}