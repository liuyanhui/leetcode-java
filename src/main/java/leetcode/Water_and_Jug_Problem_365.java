package leetcode;

import java.util.*;

/**
 * 365. Water and Jug Problem
 * Medium
 * --------------------------
 * You are given two jugs with capacities jug1Capacity and jug2Capacity liters. There is an infinite amount of water supply available. Determine whether it is possible to measure exactly targetCapacity liters using these two jugs.
 *
 * If targetCapacity liters of water are measurable, you must have targetCapacity liters of water contained within one or both buckets by the end.
 *
 * Operations allowed:
 * Fill any of the jugs with water.
 * Empty any of the jugs.
 * Pour water from one jug into another till the other jug is completely full, or the first jug itself is empty.
 *
 * Example 1:
 * Input: jug1Capacity = 3, jug2Capacity = 5, targetCapacity = 4
 * Output: true
 * Explanation: The famous Die Hard example
 *
 * Example 2:
 * Input: jug1Capacity = 2, jug2Capacity = 6, targetCapacity = 5
 * Output: false
 *
 * Example 3:
 * Input: jug1Capacity = 1, jug2Capacity = 2, targetCapacity = 3
 * Output: true
 *
 * Constraints:
 * 1 <= jug1Capacity, jug2Capacity, targetCapacity <= 10^6
 */
public class Water_and_Jug_Problem_365 {
    public static boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        return canMeasureWater_2(jug1Capacity, jug2Capacity, targetCapacity);
    }
    /**
     * review
     * round 2
     *
     * Thinking：
     * 1.a，b，c三个作为常量组成一个等式，如：ax+by=c，其中：a=jug1，b=jug2，c=target。
     * 在这个等式中，x和y都是整数（可能是负数），只要存在x和y满足等式，那么表示存在解，返回true。
     * 2.等式中有两个变量。可以通过遍历一个变量，使之降维成一个变量求解的问题。那么如何确定变量的范围？
     * 3.使得等式ax+by=c成立，需要满足贝祖定理的条件，即GCD(a,c)能被c整除。
     *
     * Bfs思路：
     * canMeasureWater_2()和canMeasureWater_3()
     * https://leetcode.com/problems/water-and-jug-problem/solutions/83709/breadth-first-search-with-explanation/
     *
     */

    /**
     * canMeasureWater_2()代码精简版，属于dfs法
     *
     * 参考思路：
     * https://leetcode-cn.com/problems/water-and-jug-problem/solution/shui-hu-wen-ti-by-leetcode-solution/
     *
     * 验证通过：
     * Runtime: 1496 ms, faster than 5.04% of Java online submissions for Water and Jug Problem.
     * Memory Usage: 134 MB, less than 8.65% of Java online submissions for Water and Jug Problem.
     *
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    public static boolean canMeasureWater_3(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        Set<String> seen = new HashSet<>();
        while (!queue.isEmpty()) {
            int[] t = queue.poll();
            int a = t[0], b = t[1];
            String key = a + "-" + b;
            if (seen.contains(key)) {
                continue;
            }
            seen.add(key);
            if (a == targetCapacity || b == targetCapacity || a + b == targetCapacity) {
                return true;
            }
            //a倒空
            queue.offer(new int[]{0, b});
            //b倒空
            queue.offer(new int[]{a, 0});
            //a倒满
            queue.offer(new int[]{jug1Capacity, b});
            //b倒满
            queue.offer(new int[]{a, jug2Capacity});
            //a倒给b，直到b满或a空。满足任一条件即可
            queue.offer(new int[]{a - Math.min(a, jug2Capacity - b), b + Math.min(a, jug2Capacity - b)});
            //b倒给a，直到a满或b空。满足任一条件即可
            queue.offer(new int[]{a + Math.min(b, jug1Capacity - a), b - Math.min(b, jug1Capacity - a)});
        }
        return false;
    }

    /**
     * bfs法，需要具备一定的建模能力。本思路，本质上是从一个树型结构上查找结果，是遍历树的过程，该树的出度为8。
     *
     * 参考思路：
     * https://leetcode.com/problems/water-and-jug-problem/discuss/83709/Breadth-First-Search-with-explanation.
     * https://leetcode-cn.com/problems/water-and-jug-problem/solution/shui-hu-wen-ti-by-leetcode-solution/
     *
     * 验证通过：
     * Runtime: 1371 ms, faster than 6.58% of Java online submissions for Water and Jug Problem.
     * Memory Usage: 134 MB, less than 8.91% of Java online submissions for Water and Jug Problem.
     *
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    public static boolean canMeasureWater_2(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] tmp = queue.poll();

            List<int[]> nodes = generateNode(tmp[0], tmp[1], jug1Capacity, jug2Capacity);

            for (int i = 0; i < nodes.size(); i++) {
                int a = nodes.get(i)[0];
                int b = nodes.get(i)[1];
                String key = String.valueOf(a) + "-" + String.valueOf(b);
                if (visited.contains(key)) {
                    continue;
                } else {
                    visited.add(key);
                    if (a == targetCapacity || b == targetCapacity || a + b == targetCapacity) {
                        return true;
                    } else {
                        queue.offer(new int[]{a, b});
                    }
                }
            }
        }
        return false;
    }

    private static List<int[]> generateNode(int a, int b, int aMax, int bMax) {
        List<int[]> ret = new ArrayList<>();
        //a倒空
        if (a > 0) {
            ret.add(new int[]{0, b});
        }
        //b倒空
        if (b > 0) {
            ret.add(new int[]{a, 0});
        }
        //a倒满
        if (a < aMax) {
            ret.add(new int[]{aMax, b});
        }
        //b倒满
        if (b < bMax) {
            ret.add(new int[]{a, bMax});
        }
        //a倒给b
        if (a > 0 && b < bMax) {
            //b满
            if (a + b > bMax) {
                ret.add(new int[]{a - (bMax - b), bMax});
            } else {
                //a空
                ret.add(new int[]{0, a + b});
            }

        }
        //b倒给a
        if (a < aMax && b > 0) {
            if (a + b > aMax) {
                //a满
                ret.add(new int[]{aMax, b - (aMax - a)});
            } else {
                //b空
                ret.add(new int[]{a + b, 0});
            }
        }

        return ret;
    }

    /**
     * 数学问题思路，贝祖定理
     *
     * 参考思路：
     * https://leetcode.com/problems/water-and-jug-problem/discuss/83715/Math-solution-Java-solution
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 48.39% of Java online submissions for Water and Jug Problem.
     * Memory Usage: 35.6 MB, less than 92.65% of Java online submissions for Water and Jug Problem.
     *
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    public static boolean canMeasureWater_1(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if (jug1Capacity + jug2Capacity < targetCapacity) return false;
        int gcd = getGCD_2(jug1Capacity, jug2Capacity);
        return targetCapacity % gcd == 0;
    }

    private static int getGCD_1(int a, int b) {
        int ret = 1;
        for (int i = 1; i <= a && i <= b; i++) {
            if (a % i == 0 && b % i == 0) {
                ret = i;
            }
        }
        return ret;
    }

    private static int getGCD_2(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static void main(String[] args) {
        do_func(3, 5, 4, true);
        do_func(2, 6, 5, false);
        do_func(1, 2, 3, true);
        do_func(6, 100, 71, false);
        do_func(1, 1, 12, false);
        do_func(1, 1000000, 99999, true);
    }

    private static void do_func(int jug1Capacity, int jug2Capacity, int targetCapacity, boolean expected) {
        boolean ret = canMeasureWater(jug1Capacity, jug2Capacity, targetCapacity);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
