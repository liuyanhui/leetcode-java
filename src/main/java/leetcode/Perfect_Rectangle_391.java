package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 391. Perfect Rectangle
 * Hard
 * --------------------------------
 * Given an array rectangles where rectangles[i] = [xi, yi, ai, bi] represents an axis-aligned rectangle. The bottom-left point of the rectangle is (xi, yi) and the top-right point of it is (ai, bi).
 *
 * Return true if all the rectangles together form an exact cover of a rectangular region.
 *
 * Example 1:
 * Input: rectangles = [[1,1,3,3],[3,1,4,2],[3,2,4,4],[1,3,2,4],[2,3,3,4]]
 * Output: true
 * Explanation: All 5 rectangles together form an exact cover of a rectangular region.
 *
 * Example 2:
 * Input: rectangles = [[1,1,2,3],[1,3,2,4],[3,1,4,2],[3,2,4,4]]
 * Output: false
 * Explanation: Because there is a gap between the two rectangular regions.
 *
 * Example 3:
 * Input: rectangles = [[1,1,3,3],[3,1,4,2],[1,3,2,4],[2,2,4,4]]
 * Output: false
 * Explanation: Because two of the rectangles overlap with each other.
 *
 * Constraints:
 * 1 <= rectangles.length <= 2 * 10^4
 * rectangles[i].length == 4
 * -10^5 <= xi, yi, ai, bi <= 10^5
 */
public class Perfect_Rectangle_391 {
    public static boolean isRectangleCover(int[][] rectangles) {
        return isRectangleCover_1(rectangles);
    }

    /**
     *
     * 参考思路：
     * https://leetcode.com/problems/perfect-rectangle/solutions/87181/really-easy-understanding-solution-o-n-java/
     *
     * 1.用Hashmap实现过于复杂
     * 2.用Set实现会简单。如果顶点不在Set则add()；如果顶点在Set中则remove()。
     * 3.用TreeSet更简单。通过矩形加入TreeSet判断是否重叠，如果重叠直接终止计算，返回false。
     *
     * 验证通过：
     * Runtime 101 ms Beats 6.14%
     * Memory 55.7 MB Beats 5.26%
     *
     * @param rectangles
     * @return
     */
    public static boolean isRectangleCover_1(int[][] rectangles) {
        //大矩形的四个顶点
        int[] rectangleFinal = new int[4];
        rectangleFinal[0] = Integer.MAX_VALUE;
        rectangleFinal[1] = Integer.MAX_VALUE;
        rectangleFinal[2] = Integer.MIN_VALUE;
        rectangleFinal[3] = Integer.MIN_VALUE;

        //小矩形顶点出现次数
        Map<String, Integer> pointCnt = new HashMap<>();
        //小矩形的面积和
        int areaSum = 0;

        //计算面积
        for (int i = 0; i < rectangles.length; i++) {
            //统计小矩形的顶点出现次数
            String p1 = rectangles[i][0] + "," + rectangles[i][1];
            String p2 = rectangles[i][2] + "," + rectangles[i][1];
            String p3 = rectangles[i][2] + "," + rectangles[i][3];
            String p4 = rectangles[i][0] + "," + rectangles[i][3];
            pointCnt.putIfAbsent(p1, 0);
            pointCnt.put(p1, pointCnt.get(p1) + 1);
            pointCnt.putIfAbsent(p2, 0);
            pointCnt.put(p2, pointCnt.get(p2) + 1);
            pointCnt.putIfAbsent(p3, 0);
            pointCnt.put(p3, pointCnt.get(p3) + 1);
            pointCnt.putIfAbsent(p4, 0);
            pointCnt.put(p4, pointCnt.get(p4) + 1);
            //更新大矩形的四个顶点
            rectangleFinal[0] = Math.min(rectangleFinal[0], rectangles[i][0]);
            rectangleFinal[1] = Math.min(rectangleFinal[1], rectangles[i][1]);
            rectangleFinal[2] = Math.max(rectangleFinal[2], rectangles[i][2]);
            rectangleFinal[3] = Math.max(rectangleFinal[3], rectangles[i][3]);

            //计算小矩形的面积，并累加
            areaSum += (rectangles[i][2] - rectangles[i][0]) * (rectangles[i][3] - rectangles[i][1]);
        }
        //根据顶点统计出现次数判断，只有大矩形的四个点出现一次，其余都应该出现偶数次
        for (String key : pointCnt.keySet()) {
            String p1 = rectangleFinal[0] + "," + rectangleFinal[1];
            String p2 = rectangleFinal[2] + "," + rectangleFinal[1];
            String p3 = rectangleFinal[2] + "," + rectangleFinal[3];
            String p4 = rectangleFinal[0] + "," + rectangleFinal[3];
            if (key.equals(p1) || key.equals(p2) || key.equals(p3) || key.equals(p4)) {
                if (pointCnt.get(key) != 1) {
                    return false;
                }
            } else if (pointCnt.get(key) % 2 != 0) {
                return false;
            }
        }
        //根据面积判断
        if (areaSum != (rectangleFinal[2] - rectangleFinal[0]) * (rectangleFinal[3] - rectangleFinal[1])) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 1, 3, 3}, {3, 1, 4, 2}, {3, 2, 4, 4}, {1, 3, 2, 4}, {2, 3, 3, 4}}, true);
        do_func(new int[][]{{1, 1, 2, 3}, {1, 3, 2, 4}, {3, 1, 4, 2}, {3, 2, 4, 4}}, false);
        do_func(new int[][]{{1, 1, 3, 3}, {3, 1, 4, 2}, {1, 3, 2, 4}, {2, 2, 4, 4}}, false);
        do_func(new int[][]{{0, 0, 1, 1}, {0, 0, 2, 1}, {1, 0, 2, 1}, {0, 2, 2, 3}}, false);
        do_func(new int[][]{{0, 0, 1, 1}}, true);
        do_func(new int[][]{{0, 0, 1, 1}, {0, 0, 1, 1}, {1, 1, 2, 2}, {1, 1, 2, 2}}, false);

    }

    private static void do_func(int[][] rectangles, boolean expected) {
        boolean ret = isRectangleCover(rectangles);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }

}
