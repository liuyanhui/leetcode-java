package leetcode;

import java.util.*;

/**
 * 310. Minimum Height Trees
 * Medium
 * -------------------
 * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.
 *
 * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
 *
 * Return a list of all MHTs' root labels. You can return the answer in any order.
 *
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 *
 * Example 1:
 * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
 * Output: [1]
 * Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
 *
 * Example 2:
 * Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * Output: [3,4]
 *
 * Example 3:
 * Input: n = 1, edges = []
 * Output: [0]
 *
 * Example 4:
 * Input: n = 2, edges = [[0,1]]
 * Output: [0,1]
 *
 * Constraints:
 * 1 <= n <= 2 * 10^4
 * edges.length == n - 1
 * 0 <= ai, bi < n
 * ai != bi
 * All the pairs (ai, bi) are distinct.
 * The given input is guaranteed to be a tree and there will be no repeated edges.
 */
public class Minimum_Height_Trees_310 {
    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        return findMinHeightTrees_2(n, edges);
    }

    /**
     * 参考方案：
     * https://leetcode.com/problems/minimum-height-trees/solution/
     *
     * 验证通过:
     * Runtime: 13 ms, faster than 83.39% of Java online submissions for Minimum Height Trees.
     * Memory Usage: 40.7 MB, less than 89.93% of Java online submissions for Minimum Height Trees.
     *
     * @param n
     * @param edges
     * @return
     */
    public static List<Integer> findMinHeightTrees_2(int n, int[][] edges) {
        List<Integer> leaves = new ArrayList<>();
        if (n <= 0) return leaves;
        if (n == 1) {
            leaves.add(0);
            return leaves;
        }
        //初始化邻接表
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            neighbors.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            neighbors.get(edges[i][0]).add(edges[i][1]);
            neighbors.get(edges[i][1]).add(edges[i][0]);
        }
        //初始化叶子节点列表
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).size() == 1) {
                leaves.add(i);
            }
        }
        //剪枝，去掉叶子节点
        int remainingNodes = n;
        while (remainingNodes > 2) {
            List<Integer> newLeaves = new ArrayList<>();
            for (int i : leaves) {
                int t = neighbors.get(i).get(0);
                neighbors.get(t).remove((Integer) i);
                neighbors.get(i).clear();
                if (neighbors.get(t).size() == 1) {
                    newLeaves.add(t);
                }
                remainingNodes--;
            }
            leaves = newLeaves;
        }

        return leaves;
    }

    /**
     * 把树转化为图，每轮遍历对图的边缘节点进行剪枝。当下剩下小于等于2个节点时，即为所求。
     *
     * 验证通过：
     * Runtime: 324 ms, faster than 5.00% of Java online submissions for Minimum Height Trees.
     * Memory Usage: 43.1 MB, less than 49.69% of Java online submissions for Minimum Height Trees.
     *
     * @param n
     * @param edges
     * @return
     */
    public static List<Integer> findMinHeightTrees_1(int n, int[][] edges) {
        List<Integer> ret = new ArrayList<>();
        if (n <= 0) return ret;
        //初始化邻接表
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            map.get(edges[i][0]).add(edges[i][1]);
            map.get(edges[i][1]).add(edges[i][0]);
        }
        //剪枝，每次遍历去掉叶子节点
        int c = 0;//记录去掉的节点的个数
        while (map.size() - c > 2) {
            int[] cache = new int[n];//记录非叶子节点的node
            for (int i : map.keySet()) {
                if (cache[i] == 0 && map.get(i) != null && map.get(i).size() == 1) {
                    map.get(map.get(i).get(0)).remove((Integer) i);
                    cache[map.get(i).get(0)] = 1;//叶子节点的关联节点不做剪枝操作
                    map.put(i, null);//叶子节点置为null
                    c++;
                }
            }
        }
        //计算返回结果
        for (int i : map.keySet()) {
            if (map.get(i) != null) {
                ret.add(i);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(4, new int[][]{{1, 0}, {1, 2}, {1, 3}}, new Integer[]{1});
        do_func(6, new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}}, new Integer[]{3, 4});
        do_func(1, new int[][]{}, new Integer[]{0});
        do_func(2, new int[][]{{0, 1}}, new Integer[]{0, 1});
        do_func(6, new int[][]{{0, 1}, {0, 2}, {0, 3}, {3, 4}, {4, 5}}, new Integer[]{3});
    }

    private static void do_func(int n, int[][] edges, Integer[] expected) {
        List<Integer> ret = findMinHeightTrees(n, edges);
        System.out.println(ret);

        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
