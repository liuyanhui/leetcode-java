package leetcode;

import java.util.*;

/**
 * 207. Course Schedule
 * Medium
 * -----------------------------
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * <p>
 * Return true if you can finish all courses. Otherwise, return false.
 * <p>
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 * <p>
 * Example 2:
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 * <p>
 * Constraints:
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * All the pairs prerequisites[i] are unique.
 */
public class Course_Schedule_207 {
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        return canFinish_r3_2(numCourses, prerequisites);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     *     
     *  bfs canFinish_2()思路
     * 验证通过：
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public static boolean canFinish_r3_2(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        //定义邻接表
        Set<Integer>[] neighbours = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            neighbours[i] = new HashSet<Integer>();
        }
        //初始化邻接表和indegree[]
        for (int i = 0; i < prerequisites.length; i++) {
            neighbours[prerequisites[i][0]].add(prerequisites[i][1]);
            indegree[prerequisites[i][1]]++;
        }
        //初始化indegree==0的队列
        List<Integer> zeroIndegreeList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) zeroIndegreeList.add(i);
        }
        //从indegree==0开始
        while (zeroIndegreeList.size() > 0) {
            List<Integer> nextList = new ArrayList<>();
            for (int prev : zeroIndegreeList) {
                for (int next : neighbours[prev]) {
                    indegree[next]--;
                    if(indegree[next]==0){
                        nextList.add(next);
                    }
                }
            }
            //重置indegree==0的队列队列
            zeroIndegreeList = nextList;
        }
        //统计结果
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] > 0) return false;
        }
        return true;
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 貌似是图的问题，判断有向图是否为无环有向图。
     * 使用邻接表存储有向图，然后使用dfs或bfs遍历。
     * <p>
     * 验证失败：原因见下面的review。dfs canFinish_1() 或者 bfs canFinish_2()才是正确方案。
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public static boolean canFinish_r3_1(int numCourses, int[][] prerequisites) {
        //定义邻接表
        Set<Integer>[] neighbours = new HashSet[numCourses];
        for (int i = 0; i < numCourses; i++) {
            neighbours[i] = new HashSet<>();
        }
        //初始化邻接表
        for (int i = 0; i < prerequisites.length; i++) {
            int c = prerequisites[i][0];
            if (neighbours[c] == null) {
                neighbours[c] = new HashSet<Integer>();
            }
            neighbours[c].add(prerequisites[i][1]);
        }
        //遍历，判断是否存在环
        for (int i = 0; i < numCourses; i++) {
            if (neighbours[i].size() == 0) continue;
            Set<Integer> seen = new HashSet<>();
            //初始化队列
            List<Integer> cur_queue = new ArrayList<>();
            //起点加入seen，表示起点已经计算过
            seen.add(i);
            for (int j : neighbours[i]) {
                cur_queue.add(j);
            }
            //从邻接表中删除已经遍历过的作为起点的顶点
            neighbours[i].clear();
            //BFS遍历开始
            while (cur_queue.size() > 0) {
                List<Integer> next_queue = new ArrayList<>();
                for (int j : cur_queue) {
                    //review: 1.这里的逻辑有问题，通过bfs判断是否存在环时，有问题。
                    //review: 2.因为有向图的遍历时单向，存在一个节点被多次计算的情况，如：多条边汇聚到某个节点，或聚合在某个节点。
                    //review: 3.所以BFS时多次进入一个顶点无法确定已存在环。
                    //起点已经计算过表示存在环
                    if (seen.contains(j)) return false;
                    //起点的依赖节点加入队列
                    for (int k : neighbours[j]) {
                        next_queue.add(k);
                    }
                    //起点加入seen，表示起点已经计算过
                    seen.add(j);
                    //从邻接表中删除已经遍历过的作为起点的顶点
                    neighbours[j].clear();
                }

                cur_queue = next_queue;
            }
        }
        return true;
    }

    /**
     * 金矿：Topological Sorting
     * review round2
     * <p>
     * Topological sorting算法思路：入度为零的节点加入队列中。以队列的某个节点开始，去掉该节点为起点的边，并把新产生的入度为零的节点加入到队列中。最终如果图中还存在边，那么表示有向图中是存在环的。
     * 详细算法：
     * 1.预先计算每个节点的indegree，并且indegree=0的节点加入到队列中
     * 2.遍历队列
     * 3.队列结点出队，并修改该节点的后继节点的indegree为indegree-1。如果后继节点的indegree==0，那么该后继节点加入队列中。
     * 4.最后如果图中还存在indegree不为0的节点，那么表示图中存在环。
     * <p>
     * <p>
     * BFS+Topological sorting算法：
     * 1.转化为邻接表
     * 2.初始化indegree[]
     * 3.入度为0的节点加入到queue中
     * 4.遍历queue
     * 4.1.node = queue.poll()
     * 4.2.遍历node的后继节点，后继节点序号为i
     * 4.2.1.indegree[i]=indegree[i]-1
     * 4.2.2.如果indegree[i]==0，那么i加入queue
     * 5.如果indegree[]全部为0，表示没有环；否则存在环
     * <p>
     * 验证通过：
     * Runtime: 8 ms, faster than 60.66% of Java online submissions for Course Schedule.
     * Memory Usage: 47.8 MB, less than 62.47% of Java online submissions for Course Schedule.
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public static boolean canFinish_2(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        //转化为邻接表，并初始化indegree[]
        List<List<Integer>> neighours = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            neighours.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            neighours.get(prerequisites[i][0]).add(prerequisites[i][1]);
            indegree[prerequisites[i][1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //入度为0的点加入队列
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        //遍历队列
        while (!queue.isEmpty()) {
            int node = queue.poll();
            //过滤不在图中的课程
            if (neighours.get(node).size() == 0) continue;
            for (int i : neighours.get(node)) {
                indegree[i]--;//入度减一
                if (indegree[i] == 0) queue.offer(i);//入度发生变化，如果入度为0，那么加入队列
            }
        }
        //计算入度为0的节点
        int zeroCnt = 0;
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) zeroCnt++;
        }
        //入度为0的节点数量等于课程数量时，表示DAG中没有环
        return zeroCnt == numCourses;
    }

    /**
     * 思考过程：
     * 1.有向图问题，判断有向图是否存在环？存在环表示有循环依赖。
     * 2.图的遍历，有两种方法：dfs和bfs。
     * 3.单个节点为起始遍历过程中，如果遇到已经遍历的边（不是点），则表示图中存在环。注：以单个节点为粒度。
     * 4.图的数据结构一般有两种：邻接表和邻接矩阵
     * 5.先排序，再查找。现根据先导课程排序，在依次查找以某个先导课程开始是否存在环。如果遇到已经计算过的课程，可以直接返回该课程的结果。
     * 6.先转化为邻接表，再遍历查找是否存在环。可以直接使用已经计算过的节点的结果。
     * <p>
     * DFS算法：
     * 1.转化为邻接表
     * 2.遍历邻接表+bfs。使用缓存set记录已遍历过的节点。记录当前遍历路径pathSet。节点记为cur
     * 2.1.如果cur在set中，那么跳过当前节点
     * 2.2.如果cur不在set，那么遍历当前节点的后继节点
     * 2.2.1.如果cur没有后继节点，那么cur加入到set中，跳出循环。
     * 2.2.2.遍历cur的后继节点，后继节点记为ct
     * 2.2.2.1.如果ct在pathSet中，那么返回false，程序结束。
     * 2.2.2.2.如果ct不在pathSet中，那么ct加入pathSet中，递归执行ct。
     * 2.2.2.3.ct移出pathSet
     * 3.返回true
     * <p>
     * review 套路：遇到图相关的问题。基本需要转换成邻接表或邻接矩阵来处理。
     * <p>
     * 验证通过：
     * Runtime: 14 ms, faster than 30.49% of Java online submissions for Course Schedule.
     * Memory Usage: 48.4 MB, less than 44.65% of Java online submissions for Course Schedule.
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public static boolean canFinish_1(int numCourses, int[][] prerequisites) {
        //转化为邻接表
        List<List<Integer>> neighours = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            neighours.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            neighours.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        //遍历邻接表
        Set<Integer> set = new HashSet<>();
        Set<Integer> path = new HashSet<>();
        for (int i = 0; i < neighours.size(); i++) {
            boolean signal = dfs(neighours, set, path, i);
            if (!signal) return false;
        }
        return true;
    }

    private static boolean dfs(List<List<Integer>> neighours, Set<Integer> set, Set<Integer> path, int cur) {
        if (set.contains(cur)) return true;
        if (neighours.get(cur) != null && neighours.get(cur).size() > 0) {
            path.add(cur);
            for (int j = 0; j < neighours.get(cur).size(); j++) {
                int ct = neighours.get(cur).get(j);
                if (path.contains(ct)) return false;
                path.add(ct);
                boolean signal = dfs(neighours, set, path, ct);
                if (!signal) return false;
                path.remove(ct);
            }
            path.remove(cur);
        }
        set.add(cur);
        return true;
    }

    public static void main(String[] args) {
        do_func(2, new int[][]{{1, 0}}, true);
        do_func(2, new int[][]{{1, 0}, {0, 1}}, false);
        do_func(1, new int[][]{{0, 0}}, false);
        do_func(20, new int[][]{{0, 10}, {3, 18}, {5, 5}, {6, 11}, {11, 14}, {13, 1}, {15, 1}, {17, 4}}, false);
        do_func(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}, true);
        do_func(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, true);
        do_func(3, new int[][]{{0, 1}, {2, 1}, {0, 2}}, true);
    }

    private static void do_func(int numCourses, int[][] prerequisites, boolean expected) {
        boolean ret = canFinish(numCourses, prerequisites);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
