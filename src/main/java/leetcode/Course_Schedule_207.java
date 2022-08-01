package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 207. Course Schedule
 * Medium
 * -----------------------------
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return true if you can finish all courses. Otherwise, return false.
 *
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 *
 * Example 2:
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 *
 * Constraints:
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * All the pairs prerequisites[i] are unique.
 */
public class Course_Schedule_207 {
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        return canFinish_1(numCourses, prerequisites);
    }

    /**
     * 思考过程：
     * 1.有向图问题，判断有向图是否存在环？存在环表示有循环依赖。
     * 2.图的遍历，有两种方法：dfs和bfs。
     * 3.单个节点为起始遍历过程中，如果遇到已经遍历的边（不是点），则表示图中存在环。注：以单个节点为粒度。
     * 4.图的数据结构一般有两种：邻接表和邻接矩阵
     * 5.先排序，再查找。现根据先导课程排序，在依次查找以某个先导课程开始是否存在环。如果遇到已经计算过的课程，可以直接返回该课程的结果。
     * 6.先转化为邻接表，再遍历查找是否存在环。可以直接使用已经计算过的节点的结果。
     *
     * 算法：
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
     *
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
    }

    private static void do_func(int numCourses, int[][] prerequisites, boolean expected) {
        boolean ret = canFinish(numCourses, prerequisites);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
