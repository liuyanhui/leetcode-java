package leetcode;

import java.util.*;

/**
 * 332. Reconstruct Itinerary
 * Medium
 * -----------------------
 * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.
 *
 * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.
 *
 * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
 * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
 *
 * Example 1:
 * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
 * Output: ["JFK","MUC","LHR","SFO","SJC"]
 *
 * Example 2:
 * Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
 *
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
        return findItinerary_3(tickets);
    }

    /**
     * 欧拉通路问题，一笔画问题
     * 参考思路：
     * https://leetcode-cn.com/problems/reconstruct-itinerary/solution/zhong-xin-an-pai-xing-cheng-by-leetcode-solution/
     * https://leetcode.com/problems/reconstruct-itinerary/discuss/78768/Short-Ruby-Python-Java-C%2B%2B
     *
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
        while (targets.containsKey(cur) && targets.get(cur).size() > 0) {
            dfs(targets, targets.get(cur).poll(), path);
        }
        path.push(cur);
    }


    /**
     * 正确的思路：
     * 思路：递归回溯法。类似贪心法，每步都获取当前的最优解。用计数器记录某个节点是否还有剩余的出现次数
     * 1.用递归法对图进行遍历，按照最小字母序进行路径遍历。设票数=tc。
     * 2.当路径中节点数==tc+1时，返回该结果
     * 3.当路径中节点数<tc+1且遍历结束时，回退并按照次小字母序生成新路径
     * 4.当路径中节点数<tc+1且遍历未结束时，递归调用
     *
     * 验证失败，有用例未通过。26 / 80 test cases passed.
     *
     * */
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
        if (map.get(curNode) == null) return false;
        String[] nodeArr = new String[map.get(curNode).size()];
        map.get(curNode).toArray(nodeArr);
        for (int i = 0; i < nodeArr.length; i++) {
            String nextNode = nodeArr[i];
            path.add(nextNode);
            map.get(curNode).remove(nextNode);
            if (do_find_recursive(map, tc, path)) {
                return true;
            } else {
                path.remove(nextNode);
                map.get(curNode).offer(nextNode);
            }
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
    }

    private static void do_func(String[][] tickets, String[] expected) {
        List<String> ret = findItinerary(ArrayListUtils.arrayToList(tickets));
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
