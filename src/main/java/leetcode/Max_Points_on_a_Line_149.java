package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 149. Max Points on a Line
 * Hard
 * ----------------------
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.
 *
 * Example 1:
 * Input: points = [[1,1],[2,2],[3,3]]
 * Output: 3
 *
 * Example 2:
 * Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 *
 * Constraints:
 * 1 <= points.length <= 300
 * points[i].length == 2
 * -10^4 <= xi, yi <= 10^4
 * All the points are unique.
 */
public class Max_Points_on_a_Line_149 {
    public static int maxPoints(int[][] points) {
        return maxPoints_1(points);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. navie solution
     * 穷举出任意2个点之间的直线公式，保存在Hashtable中（key:直线公式,value:点的数量）；计算过程中，合并相同的直线，并记录直线中点的数量。
     * Time Complexity:O(N*N)
     * Space Complexity:O(N)
     * 2. 等比公式法。
     * 有三个点[x1,y1],[x2,y2],[x3,y3]，当它们在一天直线上时，存在以下等式成立：
     * (y2-y1)/(y3-y1) == (x2-x1)/(x3-x1) 转换成乘法方式为：(y2-y1)*(x3-x1) == (x3-x1)*(y3-y1)
     * 用一个三层的循环穷举计算即可。
     *
     *
     * 本题采用【1.】
     *
     * 验证通过：
     * Runtime 73 ms Beats 9.14%
     * Memory 64.73 MB Beats 5.18%
     *
     * @param points
     * @return
     */
    public static int maxPoints_1(int[][] points) {
        if (points == null || points.length == 0) return 0;
        if (points.length == 1) return 1;
        Map<String, Set<String>> map = new HashMap<>();//key:直线的特征向量；value：直线上的点
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                String key = getStraightLine(points[i], points[j]);
                map.computeIfAbsent(key, v -> new HashSet<>());
                map.get(key).add(points[i][0] + "-" + points[i][1]);
                map.get(key).add(points[j][0] + "-" + points[j][1]);
            }
        }

        int ret = 0;
        for (String key : map.keySet()) {
            ret = Math.max(ret, map.get(key).size());
        }
        return ret;
    }

    //直线至少有两种表示方式：
    //1.直线的公式为y=k*x+b，其中k和b都用分数，不要用浮点数。
    //2.用直线在x轴和y轴的点表示直线。
    private static String getStraightLine(int[] p1, int[] p2) {
        String k1, k2, b;//k2*y=K1*x+b
        //计算k
        int B = p2[1] - p1[1];
        int A = p2[0] - p1[0];
        //直线公式k2*y=K1*x+b
        if (B == 0) {//y=a
            k1 = "0";
            k2 = "1";
            b = String.valueOf(p2[1]);
        } else if (A == 0) {//x=a
            k1 = "1";
            k2 = "0";
            b = String.valueOf(-p2[0]);
        } else {//k2*y=K1*x+b
            int t = getMaxCommonDevisor(A, B);
            k2 = "1";
            // k=B/A
            k1 = String.valueOf(B / t) + "/" + String.valueOf(A / t);//review 因为小数可能存在误差，所以用分数的形式。分数转化成字符串的方式。所有的数字都是整数。
            //计算b,[y=kx+b --> y=(B/A)*x+b --> b=(Ay-Bx)/A ]
            t = getMaxCommonDevisor(A * p2[1] - B * p2[0], A);
            b = String.valueOf((A * p2[1] - B * p2[0]) / t) + "/" + String.valueOf(A / t);
        }
        return k1 + ":" + k2 + ":" + b;
    }

    //review 计算最大公约数
    private static int getMaxCommonDevisor(int a, int b) {
        int r = a % b;
        while (r != 0) {
            return getMaxCommonDevisor(b, r);
        }
        return b;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 1}, {2, 2}, {3, 3}}, 3);
        do_func(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}, 4);
        do_func(new int[][]{{0, 0}}, 1);
        do_func(new int[][]{{2, 2}}, 1);
        System.out.println("-------Done-------");
    }

    private static void do_func(int[][] obstacleGrid, int expected) {
        int ret = maxPoints(obstacleGrid);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
