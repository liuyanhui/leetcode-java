package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 417. Pacific Atlantic Water Flow
 * Medium
 * -----------------------------
 * There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean. The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.
 *
 * The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c).
 *
 * The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.
 *
 * Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.
 *
 * Example 1:
 * Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
 * Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
 * Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
 * [0,4]: [0,4] -> Pacific Ocean
 *        [0,4] -> Atlantic Ocean
 * [1,3]: [1,3] -> [0,3] -> Pacific Ocean
 *        [1,3] -> [1,4] -> Atlantic Ocean
 * [1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean
 *        [1,4] -> Atlantic Ocean
 * [2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean
 *        [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
 * [3,0]: [3,0] -> Pacific Ocean
 *        [3,0] -> [4,0] -> Atlantic Ocean
 * [3,1]: [3,1] -> [3,0] -> Pacific Ocean
 *        [3,1] -> [4,1] -> Atlantic Ocean
 * [4,0]: [4,0] -> Pacific Ocean
 *        [4,0] -> Atlantic Ocean
 * Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.
 *
 * Example 2:
 * Input: heights = [[1]]
 * Output: [[0,0]]
 * Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
 *
 * Constraints:
 * m == heights.length
 * n == heights[r].length
 * 1 <= m, n <= 200
 * 0 <= heights[r][c] <= 10^5
 */
public class Pacific_Atlantic_Water_Flow_417 {
    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        return pacificAtlantic_1(heights);
    }

    /**
     * Thinking：
     * 1.naive solution
     * 依次计算每个cell，通过bfs或dfs的方式遍历。
     * 时间复杂度:O(M*N*M*N)
     * 2.BFS+分别着色法。
     * 2.1.top-left边界上的cell，设pac[][]=p
     * 2.2.bottom-right边界上的cell，设atl[][]=a
     * 2.3.依次从top-left的边界的每个cell出发，分别计算可以流到pacific的cell，并将pac[][]=p
     * 2.4.依次从bottom-right边界的cell出发，分别计算可以流到atlantic的cell，并将atl[][]=a
     * 2.5.如果 pac[i][j]=p 且 atl[i][j]=a，把这个cell加入到结果集中。
     * 2.6.遍历cell[][]时使用BFS思路。队列出队（记为[i][j]）。依次按照顺时针计算相邻cell（记为[m][n]）。当height[i][j]<=height[m][n]时，cell[m][n]加入队列，并将对应的pac[m][n]=p或atl[m][n]=a
     * 
     * 验证通过：
     * Runtime 11 ms Beats 30.15%
     * Memory 45.1 MB Beats 27.93%
     *
     * @param heights
     * @return
     */
    public static List<List<Integer>> pacificAtlantic_1(int[][] heights) {
        //初始化cache和queue
        int[][] pac_cache = new int[heights.length][heights[0].length];
        int[][] atl_cache = new int[heights.length][heights[0].length];
        Queue<int[]> pac_queue = new LinkedList<>();
        Queue<int[]> atl_queue = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            pac_cache[i][0] = 1;
            pac_queue.offer(new int[]{i, 0});
            atl_cache[i][heights[i].length - 1] = 2;
            atl_queue.offer(new int[]{i, heights[i].length - 1});
        }
        for (int j = 0; j < heights[0].length; j++) {
            pac_cache[0][j] = 1;
            pac_queue.offer(new int[]{0, j});
            atl_cache[heights.length - 1][j] = 2;
            atl_queue.offer(new int[]{heights.length - 1, j});
        }

        //分别计算可以流到pacific和atlantic的cell，并记录在cache中
        bfs(heights, pac_cache, pac_queue, 1);
        bfs(heights, atl_cache, atl_queue, 2);

        //查找可以同时流到pacific和atlantic的cell
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < heights.length; i++) {
            for (int j = 0; j < heights[0].length; j++) {
                if (pac_cache[i][j] == 1 && atl_cache[i][j] == 2) {
                    List<Integer> t = new ArrayList<>();
                    t.add(i);
                    t.add(j);
                    res.add(t);
                }
            }
        }

        return res;
    }

    private static void bfs(int[][] heights, int[][] cache, Queue<int[]> queue, int type) {
        int[][] delta = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};//clockwise
        while (queue.size() > 0) {
            int[] cell = queue.poll();
            for (int[] step : delta) {
                int r = cell[0] + step[0];
                int c = cell[1] + step[1];
                if (0 <= r && r < heights.length && 0 <= c && c < heights[0].length) {
                    if (cache[r][c] == type) continue;
                    if (heights[cell[0]][cell[1]] <= heights[r][c]) {
                        cache[r][c] = type;
                        queue.offer(new int[]{r, c});
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}}, new int[][]{{0, 4}, {1, 3}, {1, 4}, {2, 2}, {3, 0}, {3, 1}, {4, 0}});
        do_func(new int[][]{{1}}, new int[][]{{0, 0}});
        do_func(new int[][]{{1, 1}, {1, 1}}, new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});
    }

    private static void do_func(int[][] heights, int[][] expected) {
        List<List<Integer>> ret = pacificAtlantic(heights);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
