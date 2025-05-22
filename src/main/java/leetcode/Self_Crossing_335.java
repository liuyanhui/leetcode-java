package leetcode;

/**
 * 335. Self Crossing
 * Hard
 * ----------------------------------
 * You are given an array of integers distance.
 *
 * You start at the point (0, 0) on an X-Y plane, and you move distance[0] meters to the north, then distance[1] meters to the west, distance[2] meters to the south, distance[3] meters to the east, and so on. In other words, after each move, your direction changes counter-clockwise.
 *
 * Return true if your path crosses itself or false if it does not.
 *
 * Example 1:
 * Input: distance = [2,1,1,2]
 * Output: true
 * Explanation: The path crosses itself at the point (0, 1).
 *
 * Example 2:
 * Input: distance = [1,2,3,4]
 * Output: false
 * Explanation: The path does not cross itself at any point.
 *
 * Example 3:
 * Input: distance = [1,1,1,2,1]
 * Output: true
 * Explanation: The path crosses itself at the point (0, 0).
 *
 * Constraints:
 * 1 <= distance.length <= 10^5
 * 1 <= distance[i] <= 10^5
 */
public class Self_Crossing_335 {
    public static boolean isSelfCrossing(int[] distance) {
        return isSelfCrossing_1(distance);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     *
     * Thinking
     * 1. navie thinking
     * 分为两个子问题：move和crossing check
     * move的结果会产生一系列的线段（两类线段，类内平行但类间垂直的线段）；check基于当前已有的线段进行计算
     * 先计算出所有线段，再计算是否相交；依次计算每个线段的同时，计算是否相交。
     * 难点：如何存储线段，并计算是否相交是关键。
     * 水平的线段单独存储，并按照纵坐标排序；垂直的线段单独存储，按照横坐标排序。
     * 当有两个交叉点时，返回false
     * 2. 分析特征
     * review [0] 采用严密的逻辑思考方式，类似DP思路，即f[i]与f[i-1]~f[i-k]等几个有限的前序状态有关系，此时穷举这几种可能就会得到解决方案。
     * review [0] 如本题：当i由小到大时，f[i]是否为true只与f[i-3],f[i-4],f[i-5]有关。因为f[i]是建立在f[i-1]为true的基础上的，以此类推。
     * 计算是否交叉时，用长度计算代替使用坐标计算。
     *
     */

    /**
     * review
     *
     * 参考资料：
     * https://leetcode.com/problems/self-crossing/solutions/79131/java-oms-with-explanation/
     * https://leetcode.com/problems/self-crossing/solutions/79141/another-python/
     *
     * 主要分三种情况：
     * 1.第i+3条线和第i条线是否交叉
     * 2.第i+4条线和第i条线是否重叠（重叠也是一种交叉）
     * 3.第i+5条线和第i条线是否交叉
     *
     * 都是在跟第i条线进行比较，所以需要遍历数组。
     *
     * 验证通过：
     * Runtime 5 ms Beats 61.47%
     * Memory 48.9 MB Beats 12.84%
     *
     * @param distance
     * @return
     */
    public static boolean isSelfCrossing_1(int[] distance) {
        for (int i = 3; i < distance.length; i++) {
            //第i+3条线和第i条线是否交叉
            if (distance[i] >= distance[i - 2] && distance[i - 1] <= distance[i - 3]) return true;
            //第i+4条线和第i条线是否重叠（重叠也是一种交叉）
            if (i >= 4) {
                if (distance[i-1]==distance[i-3] && distance[i-2]<=distance[i]+distance[i-4]) return true;
            }
            //第i+5条线和第i条线是否交叉
            if (i >= 5) {
                if (distance[i]+distance[i-4]>=distance[i-2]
                        && distance[i-5]+distance[i-1]>=distance[i-3]
                        && distance[i-4]<=distance[i-2]
                        && distance[i-1]<=distance[i-3]) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 1, 1, 2}, true);
        do_func(new int[]{1, 2, 3, 4}, false);
        do_func(new int[]{1, 1, 1, 2, 1}, true);
        do_func(new int[]{1}, false);
        do_func(new int[]{1, 2}, false);
        do_func(new int[]{1, 2, 3}, false);
        do_func(new int[]{1, 1, 2, 1, 1}, true);
        do_func(new int[]{1, 1, 2, 2, 1, 1}, true);
        do_func(new int[]{3, 3, 3, 2, 1, 1}, false);
    }

    private static void do_func(int[] data, boolean expected) {
        boolean ret = isSelfCrossing(data);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
