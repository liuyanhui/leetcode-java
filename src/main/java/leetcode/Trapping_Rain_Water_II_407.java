package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 407. Trapping Rain Water II
 * Hard
 * -----------------------------
 * Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.
 *
 * Example 1:
 * Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
 * Output: 4
 * Explanation: After the rain, water is trapped between the blocks.
 * We have two small ponds 1 and 3 units trapped.
 * The total volume of water trapped is 4.
 *
 * Example 2:
 * Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
 * Output: 10
 *
 * Constraints:
 * m == heightMap.length
 * n == heightMap[i].length
 * 1 <= m, n <= 200
 * 0 <= heightMap[i][j] <= 2 * 10^4
 */
public class Trapping_Rain_Water_II_407 {
    public static int trapRainWater(int[][] heightMap) {
        return trapRainWater_1(heightMap);
    }

    /**
     * 参考文档：
     * https://leetcode.com/problems/trapping-rain-water-ii/solutions/89461/java-solution-using-priorityqueue/
     *
     * Thinking：
     * 1.分层法+DFS。【这个方法是错误的，无法解决某一层有多个隔离的储水范围的情况】
     * 1.1.从下向上计算每一层的可以存水量(封闭的空白部分的面积)，最后各层存水量相加就是所求。
     * 1.2.问题转化为在二维数组格式的图形中计算封闭的空白部分面积的问题。采用先遍历着色，再统计面积，最后还原着色(如果必需)的思路。
     * 1.2.1.设二维数组为rect[][]，元素值大于h表示不可以储水，小于等于h表示可能可以储水(需要判断其相邻元素后确定)
     * 1.2.2.先计算四条边界上的元素:如果rect[i][j]>h，那么保持不变；如果rect[i][j]<=h，那么rect[i][j]=-1
     * 1.2.3.遍历非边界的每个内部元素
     * 1.2.3.1.再依次计算每个内部元素。如果rect[i][j]>h ，计算下一个元素；如果rect[i][j]<=h，加入到cache中，按顺时针顺序递归执行下一个节点；如果rect[i][j]=-1，cache中所有元素设为-1，清空cache，执行下一个节点。
     * 1.2.3.2.统计rect[][]中元素等于0的数量，作为本层的可以储水的量，累加到返回结果中
     * 2.边界缩小法。（巧妙的思路，2D场景可以使用）review
     * 2.1.先提取边界，并根据height加入SortedQueue排序队列中，加入queue的元素标记为已访问。
     * 2.2.SortedQueue中最小height的元素出队记为cell。
     * 2.3.如果cell被标记为已访问，执行2.6.
     * 2.4.依次计算cell的四个相邻节点是否可以储水，如果相邻节点的高度比cell更大，表示该相邻节点可以储水；
     * 2.5.每个相邻节点都加入到SortedQueue中，入队时该相邻节点的高度为相邻节点和cell的最大值(用来保证缩小边界时，最大高度可以传递)；加入queue的元素标记为已访问。
     * 2.6.执行2.2.直到SortedQueue为空。
     * 3.分层法（改进）。
     * 3.1.先计算每层的储水量，最后相加。
     * 3.2.找到4个边界上漏水的块，并找出与边界漏水的块相连的可以储水的块。统计该层可以储水的快，减去与边界相连的块就是该层的解。BFS或DFS均可。
     *
     * 验证通过：
     * Runtime 23 ms Beats 74.3%
     * Memory 44.9 MB Beats 73.33%
     *
     * @param heightMap
     * @return
     */
    public static int trapRainWater_1(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) return 0;
        PriorityQueue<Cell> queue = new PriorityQueue<>(new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return o1.height - o2.height;
            }
        });
        boolean[][] visited = new boolean[heightMap.length][heightMap[0].length];
        //四个边界加入queue中
        for (int i = 0; i < heightMap.length; i++) {
            visited[i][0] = true;
            visited[i][heightMap[0].length - 1] = true;
            queue.offer(new Cell(i, 0, heightMap[i][0]));
            queue.offer(new Cell(i, heightMap[0].length - 1, heightMap[i][heightMap[0].length - 1]));
        }
        for (int i = 1; i < heightMap[0].length - 1; i++) {
            visited[0][i] = true;
            visited[heightMap.length - 1][i] = true;
            queue.offer(new Cell(0, i, heightMap[0][i]));
            queue.offer(new Cell(heightMap.length - 1, i, heightMap[heightMap.length - 1][i]));
        }

        int res = 0;
        int[][] delta = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            Cell c = queue.poll();
            for (int[] d : delta) {
                int tr = c.row + d[0];
                int tc = c.col + d[1];
                if (tr <= 0 || tc <= 0 || tr >= heightMap.length - 1 || tc >= heightMap[0].length - 1 || visited[tr][tc])
                    continue;
                if (c.height > heightMap[tr][tc]) res += (c.height - heightMap[tr][tc]);
                queue.offer(new Cell(tr, tc, Math.max(c.height, heightMap[tr][tc])));
                visited[tr][tc] = true;
            }
        }
        return res;
    }

    static class Cell {
        int row;
        int col;
        int height;

        public Cell(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}}, 4);
        do_func(new int[][]{{3, 3, 3, 3, 3}, {3, 2, 2, 2, 3}, {3, 2, 1, 2, 3}, {3, 2, 2, 2, 3}, {3, 3, 3, 3, 3}}, 10);

    }

    private static void do_func(int[][] heightMap, int expected) {
        int ret = trapRainWater(heightMap);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
