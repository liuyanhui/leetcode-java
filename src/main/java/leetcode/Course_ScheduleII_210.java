package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 210. Course Schedule II
 * Medium
 * ---------------------------------
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 *
 * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.
 *
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: [0,1]
 * Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
 *
 * Example 2:
 * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * Output: [0,2,1,3]
 * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
 * So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
 *
 * Example 3:
 * Input: numCourses = 1, prerequisites = []
 * Output: [0]
 *
 * Constraints:
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= numCourses * (numCourses - 1)
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * ai != bi
 * All the pairs [ai, bi] are distinct.
 */
public class Course_ScheduleII_210 {
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        return findOrder_1(numCourses, prerequisites);
    }

    /**
     * 思考：
     * 1.貌似跟Course_Schedule_207一样是Topological sorting问题
     *
     *
     * 算法：
     * 1.转化为邻接表neighours，计算每个节点的indegree[]
     * 2.把indegree==0的点，并加入queue中
     * 3.当queue不为空时，循环
     * 3.1.node从queue中出队，node追加到ret[]中
     * 3.2.遍历node的后继节点，记为s
     * 3.2.1.indegree[s]--
     * 3.2.2.如果indegree[s]==0，那么s加入到queue中
     * 4.如果indegree[]中存在大于0的情况，那么说明DAG中存在环，返回空[]
     * 5.把不在ret[]中的节点编号补全
     * 6.返回ret[]
     *
     * 验证通过：
     * Runtime: 6 ms, faster than 84.63% of Java online submissions for Course Schedule II.
     * Memory Usage: 48.8 MB, less than 66.82% of Java online submissions for Course Schedule II.
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public static int[] findOrder_1(int numCourses, int[][] prerequisites) {
        List<Integer> list = new ArrayList<>();
        int[] indegree = new int[numCourses];
        //转化为邻接表，并计算indegree[]
        List<List<Integer>> neighours = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            neighours.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            neighours.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegree[prerequisites[i][0]]++;
        }
        //初始化queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            //由于下面的逻辑，无需在结果集中单独补全不在图中的节点
            if (indegree[i] == 0) queue.offer(i);
        }
        //Topological sorting
        while (!queue.isEmpty()) {
            int node = queue.poll();
            list.add(node);
            if (neighours.get(node).size() == 0) {
                continue;
            }
            for (int i = 0; i < neighours.get(node).size(); i++) {
                int idx = neighours.get(node).get(i);
                indegree[idx]--;
                if (indegree[idx] == 0) {
                    queue.offer(idx);
                }
            }
        }
        //判断DAG中是否存在环
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] > 0) return new int[0];
        }
        int[] ret = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(2, new int[][]{{1, 0}}, new int[]{0, 1});
        do_func(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}, new int[]{0, 1, 2, 3});
        do_func(1, new int[][]{}, new int[]{0});
    }

    private static void do_func(int numCourses, int[][] prerequisites, int[] expected) {
        int[] ret = findOrder(numCourses, prerequisites);
        ArrayUtils.printlnIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("-----------");
    }
}
