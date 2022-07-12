package leetcode;

/**
 * 174. Dungeon Game
 * Hard
 * ---------------------------------
 * The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of m x n rooms laid out in a 2D grid. Our valiant knight was initially positioned in the top-left room and must fight his way through dungeon to rescue the princess.
 *
 * The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.
 *
 * Some of the rooms are guarded by demons (represented by negative integers), so the knight loses health upon entering these rooms; other rooms are either empty (represented as 0) or contain magic orbs that increase the knight's health (represented by positive integers).
 *
 * To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
 *
 * Return the knight's minimum initial health so that he can rescue the princess.
 *
 * Note that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the princess is imprisoned.
 *
 * Example 1:
 * Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * Output: 7
 * Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: RIGHT-> RIGHT -> DOWN -> DOWN.
 *
 * Example 2:
 * Input: dungeon = [[0]]
 * Output: 1
 *
 * Constraints:
 * m == dungeon.length
 * n == dungeon[i].length
 * 1 <= m, n <= 200
 * -1000 <= dungeon[i][j] <= 1000
 */
public class Dungeon_Game_174 {
    public static int calculateMinimumHP(int[][] dungeon) {
        return calculateMinimumHP_2(dungeon);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/dungeon-game/discuss/52774/C%2B%2B-DP-solution
     * https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start
     *
     * DP思路
     * dp[i,j]表示[i,j]的最小必须血量，所以dp[i,j]永远大于0。血量小于0，hero就挂了。
     * 从右下到左上的方向计算。
     * 这个方案不会存在全局最优解被局部最优解误伤的情况。因为dungeon[i,j]的前导元素dungeon[i+1,j]和dungeon[i,j+1]是已经确认的到终点的最优解。所以[i,j]在[i+1,j]和[i,j+1]基础上二选一，也必然是最优解。
     *
     * @param dungeon
     * @return
     */
    public static int calculateMinimumHP_2(int[][] dungeon) {
        int M = dungeon.length;
        int N = dungeon[0].length;
        int[][] dp = new int[M + 1][N + 1];
        dp[M][N - 1] = 1;
        dp[M - 1][N] = 1;
        for (int i = M - 1; i >= 0; i--) {
            for (int j = N - 1; j >= 0; j--) {
                //dp[i,j]永远大于0，所以无需考虑dp[]小于0的情况，所以用min()
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = need <= 0 ? 1 : need;//dp[i,j]永远大于0
            }
        }
        return dp[0][0];
    }

    /**
     * 这是错误的办法，没有解决局部最优解覆盖全局最优解的情况。
     *
     * DP思路：
     * 需要两个二维数组，分别记录移动到该房间时最优血量v[][]和移动到该房间时的最优解m[][]
     * 1.以从左上到右下的方向计算
     * 2.先根据m[][]计算来源方向，再计算血量v[][]，最后计算路径的最小血量m[][]
     * 步骤如下：
     * 1.确定来源方向。
     * m[i-1][j]和m[i][j-1]中较大的为来源方向，记为m[a][b]
     * 2.计算血量。
     * v[i][j]=v[a][b]+dungeon[i][j];
     * 3.计算路径最小血量
     * m[i][j]=min(m[a][b],v[i][j])
     *
     * @param dungeon
     * @return
     */
    public static int calculateMinimumHP_1(int[][] dungeon) {
        int[][] point = new int[dungeon.length][dungeon[0].length];
        int[][] minPoint = new int[dungeon.length][dungeon[0].length];
        //设置左上起点的值
        point[0][0] = dungeon[0][0];
        minPoint[0][0] = dungeon[0][0];
        //处理第一行
        for (int j = 1; j < dungeon[0].length; j++) {
            point[0][j] = point[0][j - 1] + dungeon[0][j];
            minPoint[0][j] = Math.min(point[0][j], minPoint[0][j - 1]);
        }
        //处理第一列
        for (int i = 1; i < dungeon.length; i++) {
            point[i][0] = point[i - 1][0] + dungeon[i][0];
            minPoint[i][0] = Math.min(point[i][0], minPoint[i - 1][0]);
        }
        int a = 0, b = 0;
        for (int i = 1; i < dungeon.length; i++) {
            for (int j = 1; j < dungeon[0].length; j++) {
                if (minPoint[i - 1][j] > minPoint[i][j - 1]) {
                    a = i - 1;
                    b = j;
                } else {
                    a = i;
                    b = j - 1;
                }

                point[i][j] = point[a][b] + dungeon[i][j];
                minPoint[i][j] = Math.min(minPoint[a][b], point[i][j]);
            }
        }
        int finalInit = minPoint[dungeon.length - 1][dungeon[0].length - 1];
        return finalInit >= 0 ? 1 : Math.abs(finalInit) + 1;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}, 7);
        do_func(new int[][]{{2, 3, 3}, {5, 10, 1}, {10, 30, 5}}, 1);
        do_func(new int[][]{{-2, -3, -3}, {-5, -10, -1}, {-10, -30, -5}}, 15);
        do_func(new int[][]{{0}}, 1);
        do_func(new int[][]{{1}}, 1);
        do_func(new int[][]{{-11}}, 12);
        do_func(new int[][]{{1, -3, 3}, {0, -2, 0}, {-3, -3, -3}}, 3);
    }

    private static void do_func(int[][] obstacleGrid, int expected) {
        int ret = calculateMinimumHP(obstacleGrid);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
