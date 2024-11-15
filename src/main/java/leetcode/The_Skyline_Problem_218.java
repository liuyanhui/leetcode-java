package leetcode;

import java.util.*;

/**
 * 218. The Skyline Problem
 * Hard
 * ----------------------------------
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.
 * <p>
 * The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti, heighti]:
 * - lefti is the x coordinate of the left edge of the ith building.
 * - righti is the x coordinate of the right edge of the ith building.
 * - heighti is the height of the ith building.
 * <p>
 * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 * <p>
 * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x1,y1],[x2,y2],...]. Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.
 * <p>
 * Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...,[2 3],[4 5],[12 7],...]
 * <p>
 * Example 1:
 * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * Explanation:
 * Figure A shows the buildings of the input.
 * Figure B shows the skyline formed by those buildings. The red points in figure B represent the key points in the output list.
 * <p>
 * Example 2:
 * Input: buildings = [[0,2,3],[2,5,3]]
 * Output: [[0,3],[5,0]]
 * <p>
 * Constraints:
 * 1 <= buildings.length <= 10^4
 * 0 <= lefti < righti <= 2^31 - 1
 * 1 <= heighti <= 2^31 - 1
 * buildings is sorted by lefti in non-decreasing order.
 */
public class The_Skyline_Problem_218 {
    public static List<List<Integer>> getSkyline(int[][] buildings) {
        return getSkyline_1(buildings);
    }

    /**
     * round 3
     * Score[1] Lower is harder
     *
     * 仔细观察，总结规律。
     *
     * 参考资料
     * https://leetcode.com/problems/the-skyline-problem/solutions/61193/short-java-solution/
     */

    /**
     * 参考资料：
     * https://leetcode.com/problems/the-skyline-problem/solution/ Approach1
     * <p>
     * 思路：
     * 1.通过对example中的例子进行分析，结果集由skyline的横线开始的点组成。
     * 2.把x-coordinate进行去重和排序
     * 3.计算每个x-coordinate的height
     * 4.输出结果集
     * <p>
     * 验证通过：
     * Runtime: 1183 ms, faster than 5.09% of Java online submissions for The Skyline Problem.
     * Memory Usage: 184 MB, less than 5.20% of Java online submissions for The Skyline Problem.
     *
     * @param buildings
     * @return
     */
    public static List<List<Integer>> getSkyline_1(int[][] buildings) {
        //1.把x-coordinate进行去重和排序
        SortedSet<Integer> sortedSet = new TreeSet<Integer>();
        for (int i = 0; i < buildings.length; i++) {
            sortedSet.add(buildings[i][0]);
            sortedSet.add(buildings[i][1]);
        }
        List<Integer> xList = new ArrayList<>(sortedSet);
        //提前计算好xList中每个item在数组中的下标。key=position，value=index
        //如果不提前计算，下面的计算最大height时，每次循环都会重复在xList中查找下标。
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < xList.size(); i++) {
            indexMap.put(xList.get(i), i);
        }
        //2.计算每个x-coordinate的最大height
        int[] heights = new int[xList.size()];
        for (int i = 0; i < buildings.length; i++) {
            int left = buildings[i][0], right = buildings[i][1];
            int h = buildings[i][2];
            //注意：这里是左闭右开
            for (int j = indexMap.get(left); j < indexMap.get(right); j++) {
                heights[j] = Math.max(heights[j], h);
            }
        }
        //3.计算结果集
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < xList.size(); i++) {
            if (i == 0 || heights[i - 1] != heights[i]) {
                List<Integer> t = new ArrayList<>();
                t.add(xList.get(i));
                t.add(heights[i]);
                res.add(t);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}}, new int[][]{{2, 10}, {3, 15}, {7, 12}, {12, 0}, {15, 10}, {20, 8}, {24, 0}});
        do_func(new int[][]{{0, 2, 3}, {2, 5, 3}}, new int[][]{{0, 3}, {5, 0}});
    }

    private static void do_func(int[][] buildings, int[][] expected) {
        List<List<Integer>> ret = getSkyline(buildings);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
