package leetcode;

import java.util.*;

/**
 * 399. Evaluate Division
 * Medium
 * --------------------
 * You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.
 *
 * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
 *
 * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
 *
 * Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.
 *
 * Example 1:
 * Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
 * Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
 * Explanation:
 * Given: a / b = 2.0, b / c = 3.0
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
 * return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
 *
 * Example 2:
 * Input: equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]
 * Output: [3.75000,0.40000,5.00000,0.20000]
 *
 * Example 3:
 * Input: equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]
 * Output: [0.50000,2.00000,-1.00000,-1.00000]
 *
 * Constraints:
 * 1 <= equations.length <= 20
 * equations[i].length == 2
 * 1 <= Ai.length, Bi.length <= 5
 * values.length == equations.length
 * 0.0 < values[i] <= 20.0
 * 1 <= queries.length <= 20
 * queries[i].length == 2
 * 1 <= Cj.length, Dj.length <= 5
 * Ai, Bi, Cj, Dj consist of lower case English letters and digits.
 */
public class Evaluate_Division_399 {
    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        return calcEquation_2(equations, values, queries);
    }

    /**
     * round 2
     * 
     * Thinking：
     * 1.不要轻易缩小解空间，可能会把部分解的特征提前排除。比如：a/c=2时，不能简单设a=2和c=1，如果后续出现a/d=23等时，可能导致错误的结果。
     * 2.是否可以根据条件，把计算关系转化成有向图，然后通过遍历图进行查找和计算。
     * 2.1.图中的节点，有两个属性，都是列表。一个子节点列表，一个保存对应的结果（权重）列表。
     * 2.2.采用两个邻接表，一个是节点列表，一个是权重列表。
     * 2.3.采用DFS的方式遍历邻接表。
     *
     * 验证通过：
     * Runtime 7 ms Beats 5.96%
     * Memory 41.6 MB Beats 5.68%
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public static double[] calcEquation_2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] res = new double[queries.size()];
        //构建邻接表，有向图
        Map<String, Set<String>> mapNode = new HashMap<>();
        Map<String, Map<String, Double>> mapWeight = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            List<String> e = equations.get(i);
            mapNode.putIfAbsent(e.get(0), new HashSet<>());
            mapNode.get(e.get(0)).add(e.get(1));
            mapNode.putIfAbsent(e.get(1), new HashSet<>());
            mapNode.get(e.get(1)).add(e.get(0));

            mapWeight.putIfAbsent(e.get(0), new HashMap<>());
            mapWeight.get(e.get(0)).put(e.get(1), values[i]);
            mapWeight.putIfAbsent(e.get(1), new HashMap<>());
            mapWeight.get(e.get(1)).put(e.get(0), 1 / values[i]);
        }

        //根据queries查找领接表
        for (int i = 0; i < queries.size(); i++) {
            res[i] = query(queries.get(i).get(0), queries.get(i).get(1), mapNode, mapWeight, new HashSet<>());
        }

        return res;
    }

    private static double query(String from, String to, Map<String, Set<String>> mapNode, Map<String, Map<String, Double>> mapWeight, Set<String> seen) {
        String seenStr = from + "." + to;
        if (seen.contains(seenStr)) return -1.0;
        if (mapNode.containsKey(from)) {
            if (from.equals(to)) return 1.0;
            if (mapNode.get(from).contains(to)) {
                return mapWeight.get(from).get(to);
            }

            for (String next : mapNode.get(from)) {
                seen.add(seenStr);
                double t = query(next, to, mapNode, mapWeight, seen);
                seen.remove(seenStr);
                if (t == -1.0) {
                    continue;
                } else {
                    return mapWeight.get(from).get(next) * t;
                }
            }
        }

        return -1.0;
    }

    /**
     * 问题转化为图的遍历，采用DFS法
     *
     * 参考思路：
     * https://leetcode.com/problems/evaluate-division/discuss/171649/1ms-DFS-with-Explanations
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 93.56% of Java online submissions for Evaluate Division.
     * Memory Usage: 37.9 MB, less than 65.73% of Java online submissions for Evaluate Division.
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public static double[] calcEquation_1(
            List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] ret = new double[queries.size()];
        //构造领接表
        Map<String, List<Node>> map = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            List<String> e = equations.get(i);
            map.computeIfAbsent(e.get(0), v -> new ArrayList<Node>());
            map.get(e.get(0)).add(new Node(e.get(1), values[i]));

            map.computeIfAbsent(e.get(1), v -> new ArrayList<Node>());
            map.get(e.get(1)).add(new Node(e.get(0), 1 / values[i]));
        }

        for (int i = 0; i < queries.size(); i++) {
            List<String> q = queries.get(i);
            Set<String> existed = new HashSet<>();
            double t = dfs(map, q.get(0), q.get(1), existed);
            ret[i] = (t > 0 ? t : -1.0);
        }

        return ret;
    }

    private static double dfs(Map<String, List<Node>> map, String from, String to, Set<String> existed) {
        double ret = 0.0;
        if (!map.containsKey(from) || !map.containsKey(to)) return ret;
        for (Node node : map.get(from)) {
            if (existed.contains(from)) continue;
            if (node.c.equals(to)) {
                return node.v;
            }
            existed.add(from);
            ret = node.v * dfs(map, node.c, to, existed);
            existed.remove(from);
            if (ret > 0) {
                break;
            }
        }

        return ret;
    }

    //领接表中的节点
    static class Node {
        public String c;//边的尾部字符
        public double v;//边的值

        public Node(String c, double v) {
            this.c = c;
            this.v = v;
        }
    }

    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "b"));
        equations.add(Arrays.asList("b", "c"));
        double[] values = new double[]{2.0, 3.0};
        List<List<String>> queries = new ArrayList<>();
        queries.add(Arrays.asList("a", "c"));
        queries.add(Arrays.asList("b", "a"));
        queries.add(Arrays.asList("a", "e"));
        queries.add(Arrays.asList("a", "a"));
        queries.add(Arrays.asList("x", "x"));
        double[] expected = new double[]{6.00000, 0.50000, -1.00000, 1.00000, -1.00000};
        do_func(equations, values, queries, expected);

        equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "b"));
        equations.add(Arrays.asList("b", "c"));
        equations.add(Arrays.asList("bc", "cd"));
        values = new double[]{1.5, 2.5, 5.0};
        queries = new ArrayList<>();
        queries.add(Arrays.asList("a", "c"));
        queries.add(Arrays.asList("c", "b"));
        queries.add(Arrays.asList("bc", "cd"));
        queries.add(Arrays.asList("cd", "bc"));
        expected = new double[]{3.75000, 0.40000, 5.00000, 0.20000};
        do_func(equations, values, queries, expected);

        equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "b"));
        values = new double[]{0.5};
        queries = new ArrayList<>();
        queries.add(Arrays.asList("a", "b"));
        queries.add(Arrays.asList("b", "a"));
        queries.add(Arrays.asList("a", "c"));
        queries.add(Arrays.asList("x", "y"));
        expected = new double[]{0.50000, 2.00000, -1.00000, -1.00000};
        do_func(equations, values, queries, expected);

        equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "e"));
        equations.add(Arrays.asList("b", "e"));
        values = new double[]{4.0, 3.0};
        queries = new ArrayList<>();
        queries.add(Arrays.asList("a", "b"));
        queries.add(Arrays.asList("e", "e"));
        queries.add(Arrays.asList("x", "x"));
        expected = new double[]{1.33333, 1.00000, -1.00000};
        do_func(equations, values, queries, expected);
    }

    private static void do_func(List<List<String>> equations,
                                double[] values, List<List<String>> queries, double[] expected) {
        double[] ret = calcEquation(equations, values, queries);
        Arrays.stream(ret).forEach(System.out::println);
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
