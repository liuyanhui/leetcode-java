package leetcode;

import java.util.*;

/**
 * 133. Clone Graph
 * Medium
 * ----------------------
 * Given a reference of a node in a connected undirected graph.
 * Return a deep copy (clone) of the graph.
 * Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 * }
 *
 * Test case format:
 * For simplicity sake, each node's value is the same as the node's index (1-indexed). For example, the first node with val = 1, the second node with val = 2, and so on. The graph is represented in the test case using an adjacency list.
 * Adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
 * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
 *
 * Example 1:
 * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
 * Output: [[2,4],[1,3],[2,4],[1,3]]
 * Explanation: There are 4 nodes in the graph.
 * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 *
 * Example 2:
 * Input: adjList = [[]]
 * Output: [[]]
 * Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
 *
 * Example 3:
 * Input: adjList = []
 * Output: []
 * Explanation: This an empty graph, it does not have any nodes.
 *
 * Example 4:
 * Input: adjList = [[2],[1]]
 * Output: [[2],[1]]
 *
 * Constraints:
 * 1 <= Node.val <= 100
 * Node.val is unique for each node.
 * Number of Nodes will not exceed 100.
 * There is no repeated edges and no self-loops in the graph.
 * The Graph is connected and all nodes can be visited starting from the given node.
 */
public class Clone_Graph_133 {
    public Node cloneGraph(Node node) {
        return cloneGraph_r3_1(node);
    }

    /**
     *
     * round 3
     * Score[2] Lower is harder
     *
     *
     * Thinking：
     * 1. Graph problems 的一般方法为DFS或BFS。为了防止无法终止，把已经计算过的node保存在缓存中。
     * 1.1. 对于edge来说，每个node有两种情况：node作为起点，node作为终点。为了防止无法结束整个计算，要么在起点查重，要么在终点查重。对于邻接表来说，以终点查重较难实现。
     * 2. 转化成数组再clone
     * 3. Gragh由顶点node和边egde组成，所以clone时需要同时clone 顶点和边。边的clone要稍复杂一些：边必须有两个顶点；边可能有方向。
     * 3.1. two round 法：先clone 顶点，再clone 边。把顶点保存在集合中(Map或List)，只需要遍历集合即可，这样就无需担心死循环了。
     * 3.2. 遍历和复制隔离（解耦）法。把遍历图和复制图解耦；把复制的顶点保存在HashMap中，这样在只需要遍历源图即可，无需遍历复制图。
     * 3.2.1. 充分利用了node.val唯一的特性。
     *
     * 本方法采用DFS，同时实现遍历和复制。
     * cloneGraph_1()实现更简单
     *
     * 验证通过:
     * Runtime 27 ms Beats 43.26%
     * Memory 42.53 MB Beats 62.57%
     *
     * @param node
     * @return
     */
    public Node cloneGraph_r3_1(Node node) {
        if (node == null) return null;
        Set<Integer> existed = new HashSet<Integer>();
        Map<Integer, Node> copiedNodes = new HashMap<Integer, Node>();
        Node ret = new Node(node.val);
        copiedNodes.put(ret.val, ret);
        helper_r3_1(ret, node, existed, copiedNodes);
        return ret;
    }

    private void helper_r3_1(Node copy, Node raw, Set<Integer> existed, Map<Integer, Node> copiedNodes) {
        if (existed.contains(raw.val)) return;
        existed.add(raw.val);
        for (Node n : raw.neighbors) {
            Node c = copiedNodes.computeIfAbsent(n.val, v -> new Node(n.val));
            copy.neighbors.add(c);
            helper_r3_1(c, n, existed, copiedNodes);
        }
    }


    /**
     * round 2
     *
     * bfs方案：
     * 1.出队queue，得到当前节点为old，其clone节点为new
     * 2.如果new已经在cached中，跳过节点new。
     * 3.如果new不在cached中，创建new，new加入cached。
     * 3.1.old的neigbhor list对应的clone节点全部加入到新节点new的neigbhor list中，并全部入队queue
     *
     * 验证通过：
     * Runtime: 27 ms, faster than 82.09% of Java online submissions for Clone Graph.
     * Memory Usage: 42.6 MB, less than 80.75% of Java online submissions for Clone Graph.
     *
     * @param node
     * @return
     */
    public Node cloneGraph_3(Node node) {
        if (node == null) return null;
        Set<Integer> set = new HashSet<>();//已经处理过的节点
        Map<Integer, Node> cached = new HashMap<>();//缓存所有已创建的节点
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        while (queue.size() > 0) {
            Node old = queue.poll();
            if (set.contains(old.val)) continue;
            cached.putIfAbsent(old.val, new Node(old.val));
            Node clone = cached.get(old.val);
            for (Node o : old.neighbors) {
                cached.putIfAbsent(o.val, new Node(o.val));
                clone.neighbors.add(cached.get(o.val));
                queue.offer(o);
            }
            set.add(old.val);
        }
        return cached.get(node.val);
    }

    /**
     * round 2
     *
     * 要点：
     * 1.主要是防止节点重复创建，通过hashmap可以解决
     * 2.节点都是有编号的
     * 3.如何终止计算
     * 4.图的遍历无非是两种方式：dfs和bfs
     *
     * 分两步：1.clone node和neigbhor list ;2.递归neigbhor list
     *
     * dfs方案：
     * 1.遍历node的neighbor list，加入都新node的neighbor list中（如果在cache中，直接加入；如果不在cache中，新建node并加入cached，再加入）
     * 2.遍历时，如果已经出现的node，只加入到neighbor list中，不递归该node
     *
     * 验证通过：
     * Runtime: 25 ms, faster than 82.34% of Java online submissions for Clone Graph.
     * Memory Usage: 42.5 MB, less than 82.57% of Java online submissions for Clone Graph.
     *
     * @param node
     * @return
     */
    public Node cloneGraph_2(Node node) {
        if (node == null) return null;
        Map<Integer, Node> cached = new HashMap<>();
        dfs_2(node, cached);
        return cached.get(node.val);
    }

    private void dfs_2(Node node, Map<Integer, Node> cached) {
        if (node == null) return;
        cached.putIfAbsent(node.val, new Node(node.val));
        Node cp = cached.get(node.val);
        for (Node n : node.neighbors) {
            if (cached.containsKey(n.val)) {//如果node已经处理过，直接加入的neighbor list中
                cp.neighbors.add(cached.get(n.val));
            } else {//如果node没有被处理过，创建node，并递归该node对应的原neighbor list，并递归该node
                Node t = new Node(n.val);
                cached.put(t.val, t);
                cp.neighbors.add(t);
                dfs(n, cached);
            }
        }
    }

    /**
     * cloneGraph_bfs()的优化版本，去掉一个queue，使用map
     *
     * 验证通过：
     * Runtime: 26 ms, faster than 49.02% of Java online submissions for Clone Graph.
     * Memory Usage: 39.3 MB, less than 53.97% of Java online submissions for Clone Graph.
     *
     * @param node
     * @return
     */
    private Node cloneGraph_bfs_2(Node node) {
        if (node == null) return null;
        Node ret = null;
        Map<Integer, Node> existed = new HashMap<>();
        Queue<Node> srcQueue = new LinkedList<>();//源node队列
        //初始化队列
        srcQueue.offer(node);
        ret = new Node(node.val);
        existed.put(node.val, ret);
        //开始bfs
        while (srcQueue.size() > 0) {
            Node s = srcQueue.poll();
            for (Node n : s.neighbors) {
                Node t = null;
                //已经处理过的node不再入队列
                if (existed.containsKey(n.val)) {
                    t = existed.get(n.val);
                } else {
                    t = new Node(n.val);
                    srcQueue.offer(n);
                    existed.put(t.val, t);
                }
                //源node中的neighbors追加到目标node中
                existed.get(s.val).neighbors.add(t);
            }
        }
        return ret;
    }

    /**
     * bfs方案
     *
     * 验证通过：
     * Runtime: 25 ms, faster than 90.62% of Java online submissions for Clone Graph.
     * Memory Usage: 39.4 MB, less than 41.36% of Java online submissions for Clone Graph.
     *
     * @param node
     * @return
     */
    private Node cloneGraph_bfs(Node node) {
        if (node == null) return null;
        Node ret = null;
        Map<Integer, Node> existed = new HashMap<>();
        Queue<Node> srcQueue = new LinkedList<>();//源node队列
        Queue<Node> destQueue = new LinkedList<>();//目标node队列
        //初始化队列
        srcQueue.offer(node);
        ret = new Node(node.val);
        destQueue.offer(ret);
        existed.put(node.val, ret);
        //开始bfs
        while (srcQueue.size() > 0) {
            Node s = srcQueue.poll();
            Node d = destQueue.poll();
            for (Node n : s.neighbors) {
                Node t = null;
                //已经处理过的node不再入队列
                if (existed.containsKey(n.val)) {
                    t = existed.get(n.val);
                } else {
                    t = new Node(n.val);
                    srcQueue.offer(n);
                    destQueue.offer(t);
                    existed.put(t.val, t);
                }
                //源node中的neighbors追加到目标node中
                d.neighbors.add(t);
            }
        }
        return ret;
    }

    /**
     * dfs思路
     *
     * Time Complexity : O(n)
     * Space Complexity: O(n)
     *
     * 验证通过:
     * Runtime: 26 ms, faster than 49.02% of Java online submissions for Clone Graph.
     * Memory Usage: 38.7 MB, less than 97.96% of Java online submissions for Clone Graph.
     *
     * @param node
     * @return
     */
    public Node cloneGraph_1(Node node) {
        Map<Integer, Node> existed = new HashMap<>();
        return dfs(node, existed);
    }

    private Node dfs(Node src, Map<Integer, Node> existed) {
        if (src == null) return null;
        if (existed.containsKey(src.val)) {
            return existed.get(src.val);
        }
        Node ret = new Node(src.val);
        existed.put(ret.val, ret);
        for (Node n : src.neighbors) {
            ret.neighbors.add(dfs(n, existed));
        }

        return ret;
    }

    public static void main(String[] args) {
        Clone_Graph_133 solution = new Clone_Graph_133();
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);
        Node expect = solution.cloneGraph(n1);
        System.out.println("---------done----------");
    }

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
