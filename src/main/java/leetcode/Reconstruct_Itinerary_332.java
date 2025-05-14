package leetcode;

import java.util.*;

/**
 * 332. Reconstruct Itinerary
 * Medium
 * Hard（Change To Hard level Since Round 2）
 * -----------------------
 * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.
 * <p>
 * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.
 * <p>
 * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 * <p>
 * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
 * <p>
 * Example 1:
 * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
 * Output: ["JFK","MUC","LHR","SFO","SJC"]
 * <p>
 * Example 2:
 * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
 * <p>
 * Constraints:
 * 1 <= tickets.length <= 300
 * tickets[i].length == 2
 * fromi.length == 3
 * toi.length == 3
 * fromi and toi consist of uppercase English letters.
 * fromi != toi
 */
public class Reconstruct_Itinerary_332 {

    public static List<String> findItinerary(List<List<String>> tickets) {
        return findItinerary_r3_2(tickets);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * review 一笔画问题 和 逆序入栈
     * 递归和遍历两种实现，https://leetcode.com/problems/reconstruct-itinerary/solutions/78768/short-ruby-python-java-c/
     * 简要思路如下：
     * 1. 邻接表存储数据，邻接表中根据字母序排序
     * 2. 优先找到被卡住无法继续进行查找计算的节点，这个点一定是结果集中最后被访问的节点。
     * 3. 当抵达被卡住的节点时，按原路退回的顺序把节点加入结果集。
     *
     */

    /**
     * review round 2
     * 1.动态排序可以是直接使用PriorityQueue
     * 2.PriorityQueue中存在重复数据时，删除操作无法精准操作。
     */

    /**
     * review 金矿
     * 欧拉通路问题，一笔画问题
     * 参考思路：
     * https://leetcode-cn.com/problems/reconstruct-itinerary/solution/zhong-xin-an-pai-xing-cheng-by-leetcode-solution/
     * https://leetcode.com/problems/reconstruct-itinerary/discuss/78768/Short-Ruby-Python-Java-C%2B%2B
     * <p>
     * 验证通过：
     * Runtime: 7 ms, faster than 44.49% of Java online submissions for Reconstruct Itinerary.
     * Memory Usage: 39.6 MB, less than 70.37% of Java online submissions for Reconstruct Itinerary.
     *
     * @param tickets
     * @return
     */
    public static List<String> findItinerary_3(List<List<String>> tickets) {
        Stack<String> stack = new Stack<>();
        Map<String, PriorityQueue<String>> targets = new HashMap<>();
        for (List<String> t : tickets) {
            targets.computeIfAbsent(t.get(0), v -> new PriorityQueue<>()).offer(t.get(1));
        }
        dfs(targets, "JFK", stack);
        List<String> path = new ArrayList<>();
        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }
        return path;
    }

    private static void dfs(Map<String, PriorityQueue<String>> targets, String cur, Stack<String> path) {
        //round 2 : 逆序入栈。这样就避免了PriorityQueue.remove()操作中重复数据删除的问题。见findItinerary_2()。从一边删除有问题时，那就转变思路从另一边开始删除。
        while (targets.containsKey(cur) && targets.get(cur).size() > 0) {
            dfs(targets, targets.get(cur).poll(), path);
        }
        //review round 2 : 逆序入栈
        path.push(cur);
    }

    /**
     * findItinerary_2的改进版
     * 没有使用PriorityQueue；而是直接使用List，并自己实现排序算法。
     * <p>
     * 验证通过：
     * Runtime: 10 ms, faster than 22.07% of Java online submissions for Reconstruct Itinerary.
     * Memory Usage: 46.7 MB, less than 16.85% of Java online submissions for Reconstruct Itinerary.
     *
     * @param tickets
     * @return
     */
    public static List<String> findItinerary_4(List<List<String>> tickets) {
        //用邻接表保存图，并按字母序排序
        Map<String, List<String>> adjacentMap = new HashMap<>();
        //initial the adjacent list
        for (List<String> t : tickets) {
            List<String> tmp = adjacentMap.computeIfAbsent(t.get(0), v -> new ArrayList<>());
            insertSortedList_4(tmp, t.get(1));
        }
        List<String> ret = new ArrayList<>();
        ret.add("JFK");
        int tc = tickets.size();
        do_find_recursive_4(adjacentMap, tc, ret);
        return ret;
    }

    private static boolean do_find_recursive_4(Map<String, List<String>> map, int tc, List<String> path) {
        if (path.size() == tc + 1) return true;
        String curNode = path.get(path.size() - 1);
        List<String> list = map.get(curNode);
        if (list == null || list.size() == 0) return false;
        for (int i = 0; i < list.size(); i++) {
            String t = list.get(i);
            path.add(t);
            list.remove(i);
            if (do_find_recursive_4(map, tc, path)) {
                return true;
            }
            path.remove(path.size() - 1);
            list.add(i, t);
        }
        return false;
    }

    //从小到大排序
    private static void insertSortedList_4(List<String> list, String s) {
        if (list.isEmpty()) {
            list.add(s);
        } else {
            for (int i = 0; i < list.size(); i++) {
                int t = compare(list.get(i), s);
                if (t >= 0) {
                    list.add(i, s);
                    break;
                }
                if (i == list.size() - 1) {
                    list.add(s);
                    break;
                }
            }
        }
    }

    /**
     * 正确的思路：
     * 思路：递归回溯法。类似贪心法，每步都获取当前的最优解。用计数器记录某个节点是否还有剩余的出现次数
     * 1.用递归法对图进行遍历，按照最小字母序进行路径遍历。设票数=tc。
     * 2.当路径中节点数==tc+1时，返回该结果
     * 3.当路径中节点数<tc+1且遍历结束时，回退并按照次小字母序生成新路径
     * 4.当路径中节点数<tc+1且遍历未结束时，递归调用
     * <p>
     * review round2 这个坑round2又踩了，说明这种方式有个很大的问题。PriorityQueue中存在重复数据时，删除操作无法精准操作。
     * <p>
     * 验证失败，有用例未通过。26 / 80 test cases passed.
     * 问题出现在递归方法中PriorityQueue遍历的部分。具体原因见代码注释。findItinerary_4的方法通过了验证。
     */
    public static List<String> findItinerary_2(List<List<String>> tickets) {
        //用邻接表保存图，并按字母序排序
        Map<String, PriorityQueue<String>> adjacentMap = new HashMap<>();
        for (List<String> ticket : tickets) {
            //以下代码可以替换为 [part 2]
            //TODO  [part 1 beg]
            if (adjacentMap.containsKey(ticket.get(0))) {
                adjacentMap.get(ticket.get(0)).offer(ticket.get(1));
            } else {
                adjacentMap.put(ticket.get(0), new PriorityQueue<>());
                adjacentMap.get(ticket.get(0)).offer(ticket.get(1));
            }
            //[part 1 end]
            //以下代码可替换为 [part 1]
            //TODO [part 2 beg]
//            adjacentMap.computeIfAbsent(ticket.get(0), v -> new PriorityQueue<>()).offer(ticket.get(1));
            //[part 2 end]
        }

        List<String> ret = new ArrayList<>();
        ret.add("JFK");
        int tc = tickets.size();
        do_find_recursive(adjacentMap, tc, ret);
        return ret;
    }

    private static boolean do_find_recursive(Map<String, PriorityQueue<String>> map, int tc, List<String> path) {
        if (path.size() == tc + 1) return true;
        String curNode = path.get(path.size() - 1);
        PriorityQueue<String> queue = map.get(curNode);
        if (queue == null || queue.isEmpty()) return false;
        String[] nodeArr = new String[queue.size()];
        queue.toArray(nodeArr);
        for (int i = 0; i < nodeArr.length; i++) {
            path.add(nodeArr[i]);
            //金矿
            //TODO 这里是问题所在，可能remove了错误的元素。可能存在重复的元素。如果是被remove的元素在之前存在重复的值，这个remove会错误的删除前面的哪个相等的值，从而导致计算结果错误。
            //review round2 这个坑round2又踩了，说明这种方式有个很大的问题。PriorityQueue中存在重复数据时，删除操作无法精准操作。
            queue.remove(nodeArr[i]);
            if (do_find_recursive(map, tc, path)) {
                return true;
            }
            //TODO 这里是问题所在，可能remove了错误的元素。可能存在重复的元素
            path.remove(nodeArr[i]);
            queue.offer(nodeArr[i]);
        }
        return false;
    }

    /**
     * 以下是错误的思路
     * 图的遍历，记录遍历路径，所有遍历路径中找出最小的字典序路径。
     * 不需要对图做校验，输入必然有解。
     * 1.采用邻接矩阵A[]存储，邻接矩阵中的值预先按字典序排序
     * 2.初始化ret=["JFK"]，c="JFK"
     * 3.从A[c]中获取字典序最小的v，ret.add(v)，把v从A[c]中删除，c=v
     * 4.重复步骤3
     * 结论：该思路有问题，无法保证所有的节点都能遍历到。如用例：{{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}}
     *
     * @param tickets
     * @return
     */
    public static List<String> findItinerary_1(List<List<String>> tickets) {
        Map<String, List<String>> adjacentMap = new HashMap<>();
        List<String> ret = new ArrayList<>();

        //initial the adjacent list
        for (List<String> t : tickets) {
            if (adjacentMap.containsKey(t.get(0))) {
                insertSortedList(adjacentMap.get(t.get(0)), t.get(1));
            } else {
                adjacentMap.put(t.get(0), new ArrayList<>());
                adjacentMap.get(t.get(0)).add(t.get(1));
            }
        }
        ret.add("JFK");
        String from = ret.get(ret.size() - 1);
        while (from != null && !from.equals("")) {
            //todo 应该优先选择两点之间的边最多的点，边的数量相等是按字典序选最小的
            //TODO 上面的思路也不对
            List<String> toList = adjacentMap.get(from);
            //toList为空表示from是终止地
            if (toList == null || toList.size() == 0) break;
            //每次提取最小的
            String to = toList.get(toList.size() - 1);
            ret.add(to);
            from = to;
            //删除已提取的
            toList.remove(toList.size() - 1);
        }

        return ret;
    }

    //逆序排序(从大到小)，优化集合List的删除性能
    private static void insertSortedList(List<String> list, String s) {
        if (list.isEmpty()) {
            list.add(s);
        } else {
            for (int i = 0; i < list.size(); i++) {
                int t = compare(list.get(i), s);
                if (t < 0) {
                    list.add(i, s);
                    break;
                }
                if (i == list.size() - 1) {
                    list.add(s);
                    break;
                }
            }
        }
    }

    private static int compare(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) > s2.charAt(i)) {
                return 1;
            } else if (s1.charAt(i) < s2.charAt(i)) {
                return -1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func(new String[][]{{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}}, new String[]{"JFK", "MUC", "LHR", "SFO", "SJC"});
        do_func(new String[][]{{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"ATL", "JFK"}, {"ATL", "SFO"}}, new String[]{"JFK", "ATL", "JFK", "SFO", "ATL", "SFO"});
        do_func(new String[][]{{"JFK", "SFO"}, {"SFO", "JFK"}, {"JFK", "SFO"}, {"SFO", "JFK"}}, new String[]{"JFK", "SFO", "JFK", "SFO", "JFK"});
        do_func(new String[][]{{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}}, new String[]{"JFK", "NRT", "JFK", "KUL"});
        do_func(new String[][]{{"EZE", "TIA"}, {"EZE", "HBA"}, {"AXA", "TIA"}, {"JFK", "AXA"}, {"ANU", "JFK"}, {"ADL", "ANU"}, {"TIA", "AUA"}, {"ANU", "AUA"}, {"ADL", "EZE"}, {"ADL", "EZE"}, {"EZE", "ADL"}, {"AXA", "EZE"}, {"AUA", "AXA"}, {"JFK", "AXA"}, {"AXA", "AUA"}, {"AUA", "ADL"}, {"ANU", "EZE"}, {"TIA", "ADL"}, {"EZE", "ANU"}, {"AUA", "ANU"}}, new String[]{"JFK", "AXA", "AUA", "ADL", "ANU", "AUA", "ANU", "EZE", "ADL", "EZE", "ANU", "JFK", "AXA", "EZE", "TIA", "AUA", "AXA", "TIA", "ADL", "EZE", "HBA"});
        do_func(new String[][]{{"JFK", "KUL"}}, new String[]{"JFK", "KUL"});
        do_func(new String[][]{{"AXA", "EZE"}, {"EZE", "AUA"}, {"ADL", "JFK"}, {"ADL", "TIA"}, {"AUA", "AXA"}, {"EZE", "TIA"}, {"EZE", "TIA"}, {"AXA", "EZE"}, {"EZE", "ADL"}, {"ANU", "EZE"}, {"TIA", "EZE"}, {"JFK", "ADL"}, {"AUA", "JFK"}, {"JFK", "EZE"}, {"EZE", "ANU"}, {"ADL", "AUA"}, {"ANU", "AXA"}, {"AXA", "ADL"}, {"AUA", "JFK"}, {"EZE", "ADL"}, {"ANU", "TIA"}, {"AUA", "JFK"}, {"TIA", "JFK"}, {"EZE", "AUA"}, {"AXA", "EZE"}, {"AUA", "ANU"}, {"ADL", "AXA"}, {"EZE", "ADL"}, {"AUA", "ANU"}, {"AXA", "EZE"}, {"TIA", "AUA"}, {"AXA", "EZE"}, {"AUA", "SYD"}, {"ADL", "JFK"}, {"EZE", "AUA"}, {"ADL", "ANU"}, {"AUA", "TIA"}, {"ADL", "EZE"}, {"TIA", "JFK"}, {"AXA", "ANU"}, {"JFK", "AXA"}, {"JFK", "ADL"}, {"ADL", "EZE"}, {"AXA", "TIA"}, {"JFK", "AUA"}, {"ADL", "EZE"}, {"JFK", "ADL"}, {"ADL", "AXA"}, {"TIA", "AUA"}, {"AXA", "JFK"}, {"ADL", "AUA"}, {"TIA", "JFK"}, {"JFK", "ADL"}, {"JFK", "ADL"}, {"ANU", "AXA"}, {"TIA", "AXA"}, {"EZE", "JFK"}, {"EZE", "AXA"}, {"ADL", "TIA"}, {"JFK", "AUA"}, {"TIA", "EZE"}, {"EZE", "ADL"}, {"JFK", "ANU"}, {"TIA", "AUA"}, {"EZE", "ADL"}, {"ADL", "JFK"}, {"ANU", "AXA"}, {"AUA", "AXA"}, {"ANU", "EZE"}, {"ADL", "AXA"}, {"ANU", "AXA"}, {"TIA", "ADL"}, {"JFK", "ADL"}, {"JFK", "TIA"}, {"AUA", "ADL"}, {"AUA", "TIA"}, {"TIA", "JFK"}, {"EZE", "JFK"}, {"AUA", "ADL"}, {"ADL", "AUA"}, {"EZE", "ANU"}, {"ADL", "ANU"}, {"AUA", "AXA"}, {"AXA", "TIA"}, {"AXA", "TIA"}, {"ADL", "AXA"}, {"EZE", "AXA"}, {"AXA", "JFK"}, {"JFK", "AUA"}, {"ANU", "ADL"}, {"AXA", "TIA"}, {"ANU", "AUA"}, {"JFK", "EZE"}, {"AXA", "ADL"}, {"TIA", "EZE"}, {"JFK", "AXA"}, {"AXA", "ADL"}, {"EZE", "AUA"}, {"AXA", "ANU"}, {"ADL", "EZE"}, {"AUA", "EZE"}}, new String[]{"JFK", "ADL", "ANU", "ADL", "ANU", "AUA", "ADL", "AUA", "ADL", "AUA", "ANU", "AXA", "ADL", "AUA", "ANU", "AXA", "ADL", "AXA", "ADL", "AXA", "ANU", "AXA", "ANU", "AXA", "EZE", "ADL", "AXA", "EZE", "ADL", "AXA", "EZE", "ADL", "EZE", "ADL", "EZE", "ADL", "EZE", "ANU", "EZE", "ANU", "EZE", "AUA", "AXA", "EZE", "AUA", "AXA", "EZE", "AUA", "AXA", "JFK", "ADL", "EZE", "AUA", "EZE", "AXA", "JFK", "ADL", "JFK", "ADL", "JFK", "ADL", "JFK", "ADL", "TIA", "ADL", "TIA", "AUA", "JFK", "ANU", "TIA", "AUA", "JFK", "AUA", "JFK", "AUA", "TIA", "AUA", "TIA", "AXA", "TIA", "EZE", "AXA", "TIA", "EZE", "JFK", "AXA", "TIA", "EZE", "JFK", "AXA", "TIA", "JFK", "EZE", "TIA", "JFK", "EZE", "TIA", "JFK", "TIA", "JFK", "AUA", "SYD"});


    }

    private static void do_func(String[][] tickets, String[] expected) {
        List<String> ret = findItinerary(ArrayListUtils.arrayToList(tickets));
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
