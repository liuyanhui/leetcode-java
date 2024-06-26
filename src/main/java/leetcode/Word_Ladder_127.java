package leetcode;

import java.util.*;

/**
 * 127. Word Ladder
 * Hard
 * ---------------------
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 * sk == endWord
 *
 * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
 *
 * Example 1:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
 *
 * Example 2:
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 * Constraints:
 * 1 <= beginWord.length <= 10
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 5000
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English letters.
 * beginWord != endWord
 * All the words in wordList are unique.
 */
public class Word_Ladder_127 {
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        return ladderLength_r3_1(beginWord, endWord, wordList);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. naive solution
     * 1.1. 可以转换成图的遍历问题。先在可以进行transformation的输入参数之间建立连接，得到一个无向图；再遍历图找到从顶点 beginWord 到顶点 endWord 的最短路径。
     * 1.2. 用邻接表存储图；用BFS或DFS法计算最短路径。
     * 2. 优化的点
     * 2.1. 计算邻接表时，避免双向重复计算。计算一次同时写入key和value中。
     * 2.2. 使用延迟计算的方式可以优化【1.】的时间复杂度。即不用提前计算邻接表，从begWord开始计算，计算的同时判断是否adjacent，每计算完一个word就移除它。
     *
     * 验证通过：性能一般
     * Runtime 914 ms Beats 11.94%
     * Memory 50.10 MB Beats 18.04%
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public static int ladderLength_r3_1(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.stream().anyMatch(endWord::equals)) return 0;
        int ret = 0;
        //生成邻接表
        Map<String, Set<String>> adjacencies = new HashMap<>();
        List<String> list = new ArrayList<>(wordList);
        list.add(beginWord);
//        list.add(endWord);//review endWord 不能加入list
        for (String key : list) {
            adjacencies.putIfAbsent(key, new HashSet<>());
            list.stream().filter(s -> checkAjacent(s, key)).forEach(s -> adjacencies.get(key).add(s));
        }
        //遍历找到最短路径
        Set<String> appeared = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        while (!queue.isEmpty()) {
            ret++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String key = queue.poll();
                appeared.add(key);
                if (key.equals(endWord)) return ret;
                for (String value : adjacencies.get(key)) {
                    if (appeared.contains(value)) continue;
                    queue.offer(value);
                }
            }
        }
        return 0;
    }

    private static boolean checkAjacent(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0
                || s1.length() != s2.length())
            return false;
        int cnt = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                cnt++;
            if (cnt > 1) return false;
        }
        return cnt == 1;
    }


    /**
     * ladderLength_1()的优化版
     * 参考文档：
     * https://leetcode.com/problems/word-ladder/discuss/40711/Two-end-BFS-in-Java-31ms.
     * https://leetcode.com/problems/word-ladder/discuss/40707/C%2B%2B-BFS
     *
     * bfs思路
     * 1.从beginWord开始，从wordList中查找相邻的word。（相邻：只有一个字母不同）
     * 2.如果遍历完整个wordList都没找到endWord，返回0
     * 3.如果找到endWord，返回长度
     *
     * 验证通过：
     * Runtime: 901 ms, faster than 15.15% of Java online submissions for Word Ladder.
     * Memory Usage: 44.8 MB, less than 87.30% of Java online submissions for Word Ladder.
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public static int ladderLength_2(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int ladder = 0;
        while (!queue.isEmpty()) {
            ladder++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord))
                    return ladder;
                for (int j = 0; j < wordList.size(); j++) {
                    if (wordList.get(j).length() == 0) continue;
                    if (isNeighbour(wordList.get(j), word)) {
                        queue.offer(wordList.get(j));
                        wordList.set(j, "");
                    }
                }
            }
        }
        return 0;
    }

    private static boolean isNeighbour(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int diffCnt = 1;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diffCnt--;
                if (diffCnt < 0) return false;
            }
        }
        return diffCnt == 0;
    }

    /**
     * 思考过程：最终采用思路3
     *
     * 左开右闭，相邻单词有一个字符不同（偏序关系）
     * 1.找到ladder
     * 2.计算最短ladder
     *
     * 在wordList中查找解空间的过程是单向的？不是单向的，因为单词会重复出现，并且没有顺序。
     * 1.比较两个word是否只有一个字母不同，位运算？HashMap？
     * 2.endWord不存在直接返回0
     * 3.beginWord和endWord都可能重复出现，是否需要去重？
     *
     * 貌似是最短距离问题
     *
     * 思路1：dfs=距离矩阵+递归
     * 1.wordList去重，保存在Set中。
     * 2.如果endWord不在Set中，返回0。
     * 3.beginWord和wordList中的word，计算距离矩阵。
     * 4.递归计算最小路径长度
     *
     * 思路2：bfs=距离矩阵+DP（倒推计算）
     * 1.wordList去重，保存在Set中。
     * 2.如果endWord不在Set中，返回0。
     * 3.beginWord和wordList中的word，计算距离矩阵。
     * 4.dp思路计算最小路径长度，从endWord开始倒推计算最小路径长度
     *
     * 思路3：图计算+bfs
     * 距离矩阵可以转化为图的遍历，所以距离矩阵使用邻接表的方式，只保存举例为1的节点即可。当某个节点的邻接表为空时，表示没有节点可以到达当前节点。直接返回0
     * 1.wordList去重。
     * 2.生成邻接表
     * 3.根据邻接表计算最小路径长度
     *
     * 验证通过：
     * Runtime: 494 ms, faster than 22.13% of Java online submissions for Word Ladder.
     * Memory Usage: 49.5 MB, less than 74.07% of Java online submissions for Word Ladder.
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public static int ladderLength_1(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> map = new HashMap<>();
        map.put(beginWord, new ArrayList<>());//beginWord加入邻接表中
        //去重
        for (String s : wordList)
            map.putIfAbsent(s, new ArrayList<>());

        if (!map.containsKey(endWord))
            return 0;
        //生成邻接表adjacencyList
        Object[] words = map.keySet().toArray();
        for (int i = 0; i < words.length; i++) {
            String s1 = (String) words[i];
            for (int j = i + 1; j < words.length; j++) {
                String s2 = (String) words[j];
                if (isOneStep(s1, s2)) {
                    map.get(s1).add(s2);
                    map.get(s2).add(s1);
                }
            }
        }
        //bfs法检索路径，第一个抵达endWord的就是最短路径
        //从beginWord开始，直到第一个抵达endWord。如果无法抵达endWord直接返回0。
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int ret = 1;
        while (queue.size() > 0) {
            ret++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String src = queue.poll();
                if (!map.containsKey(src) || map.get(src).size() == 0)//如果节点不存在或已经被使用过
                    continue;
                for (String dest : map.get(src)) {
                    if (dest.equals(endWord)) {
                        return ret;
                    }
                    queue.offer(dest);
                }
                map.get(src).clear();//清空已经使用过的节点
            }
        }
        return 0;
    }

    //计算两个字符串是否只有一个字母不同，其他字母顺序一样
    private static boolean isOneStep(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        int diffCnt = 1;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                continue;
            } else {
                diffCnt--;
                if (diffCnt < 0) return false;
            }
        }
        return diffCnt == 0;
    }

    public static void main(String[] args) {
        do_func("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"}, 5);
        do_func("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"}, 0);
        do_func("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog", "cig", "cit"}, 4);
        do_func("hit", "hat", new String[]{"hot", "hit", "hat", "lot", "log", "cog"}, 2);
        System.out.println("------- THE END -------");
    }

    private static void do_func(String beginWord, String endWord, String[] wordList, int expected) {

        int ret = ladderLength(beginWord, endWord, Arrays.asList(wordList));
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
