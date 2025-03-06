package leetcode;

import java.util.*;

/**
 * 310. Minimum Height Trees
 * Medium
 * -------------------
 * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.
 * <p>
 * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
 * <p>
 * Return a list of all MHTs' root labels. You can return the answer in any order.
 * <p>
 * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
 * <p>
 * Example 1:
 * Input: n = 4, edges = [[1,0],[1,2],[1,3]]
 * Output: [1]
 * Explanation: As shown, the height of the tree is 1 when the root is the node with label 1 which is the only MHT.
 * <p>
 * Example 2:
 * Input: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]]
 * Output: [3,4]
 * <p>
 * Example 3:
 * Input: n = 1, edges = []
 * Output: [0]
 * <p>
 * Example 4:
 * Input: n = 2, edges = [[0,1]]
 * Output: [0,1]
 * <p>
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
        return findMinHeightTrees_r3_1(n, edges);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. naive solution
     * brute force.
     * 计算每个 node 作为 root 的 MHT
     * Time Complexity: O(N*N)
     * 2. 裁剪法
     * 2.1. reasoning
     * 由于 Tree 中任意两个 node 有且仅有一条路径；
     * MHT 中选择 root 时，不会选择叶子节点，除非树的高度小于等于2。
     * 所以逐步移除叶子节点，最后剩下的叶子节点就是所求。
     * 2.2. algorithm
     * 统计每个节点的边的数量，由于是无向无环图。每个节点有几条边，就等于几。
     * 然后每轮移除边的数量为1的节点，最后一轮当所有节点的 边的数量<=1 时就是所求。
     * 初始化时用两个HashMap分别保存{edgeCnt:node}和{node:edgeCnt}
     * 3.
     * 参考：
     * https://leetcode.com/problems/minimum-height-trees/solutions/76055/share-some-thoughts/
     * review : 图的计算还是要规规矩矩的用邻接表或邻接矩阵。其中邻接表可以是Map<int,list>也可以是Map<int,set>
     * <p>
     * 验证通过：
     * Runtime 31 ms Beat 37.88%
     * Memory 55.68 MB Beats 79.28%
     */
    public static List<Integer> findMinHeightTrees_r3_1(int n, int[][] edges) {
        List<Integer> leaves = new ArrayList<>();
        if (n < 1) return leaves;
        if (n == 1) {
            leaves.add(0);
            return leaves;
        }
        //初始化邻接表
        List<Set<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new HashSet<>());
        }
        for (int i = 0; i < edges.length; i++) {
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        //收集叶子节点
        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i).size() == 1) leaves.add(i);
        }
        //移除叶子节点
        int cntRemainNodes = n;
        while (cntRemainNodes > 2) {
            List<Integer> newLeaves = new ArrayList<>();
            for (Integer leaf : leaves) {
                //更新相邻节点
                for (Integer neighbor : adj.get(leaf)) {
                    adj.get(neighbor).remove(leaf);
                    //收集叶子节点
                    if (adj.get(neighbor).size() == 1) {
                        newLeaves.add(neighbor);
                    }
                }
                //清空叶子节点的邻居
                adj.get(leaf).clear();
            }
            cntRemainNodes -= leaves.size();
            leaves = newLeaves;
        }
        return leaves;
    }

    /**
     * round 2 : review
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @param edges
     * @return
     */
    public static List<Integer> findMinHeightTrees_3(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, List<Integer>> adjacency1 = new HashMap<>();
        Map<Integer, List<Integer>> adjacency2 = new HashMap<>();
        //构造邻接表
        for (int i = 0; i < n; i++) {
            adjacency1.put(i, new ArrayList<>());
            adjacency2.put(i, new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            adjacency1.get(edges[i][0]).add(edges[i][1]);
            adjacency1.get(edges[i][1]).add(edges[i][0]);
            adjacency2.get(edges[i][0]).add(edges[i][1]);
            adjacency2.get(edges[i][1]).add(edges[i][0]);
        }

        // Review round2 : 下面的逻辑过于复杂。findMinHeightTrees_2()中使用leaves和remainingNodes两个变量使得计算逻辑简化。
        //循环计算邻接表，去掉边缘节点
        while (adjacency1.size() > 2) {
            //查找并收集边缘节点
            for (int key : adjacency1.keySet()) {
                if (adjacency1.get(key).size() == 1) {
                    //把当前节点重关联节点中删除
                    List<Integer> list = adjacency2.get(adjacency1.get(key).get(0));
                    for (int i = 0; i < list.size(); i++) {
                        if (key == list.get(i)) {
                            list.remove(i);
                            break;
                        }
                    }
                    //删除边缘节点
                    adjacency2.remove(key);
                }
            }
            //刷新adjacency1为adjacency2
            adjacency1 = new HashMap<>();
            for (int key : adjacency2.keySet()) {
                adjacency1.put(key, new ArrayList<>(adjacency2.get(key)));
            }
        }

        for (int key : adjacency1.keySet()) {
            res.add(key);
        }

        return res;
    }

    /**
     * 参考方案：
     * https://leetcode.com/problems/minimum-height-trees/solution/
     * <p>
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
        // Review round2 : 这个记录节点数量的变量很巧妙
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
     * <p>
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
        System.out.println("======================");
    }

    private static void do_func(int n, int[][] edges, Integer[] expected) {
        List<Integer> ret = findMinHeightTrees(n, edges);
        System.out.println(ret);

        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
