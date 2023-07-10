package leetcode;

import java.util.*;

/**
 * 403. Frog Jump
 * Hard
 * -----------------------------------------
 * A frog is crossing a river. The river is divided into some number of units, and at each unit, there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
 *
 * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog can cross the river by landing on the last stone. Initially, the frog is on the first stone and assumes the first jump must be 1 unit.
 *
 * If the frog's last jump was k units, its next jump must be either k - 1, k, or k + 1 units. The frog can only jump in the forward direction.
 *
 * Example 1:
 * Input: stones = [0,1,3,5,6,8,12,17]
 * Output: true
 * Explanation: The frog can jump to the last stone by jumping 1 unit to the 2nd stone, then 2 units to the 3rd stone, then 2 units to the 4th stone, then 3 units to the 6th stone, 4 units to the 7th stone, and 5 units to the 8th stone.
 *
 * Example 2:
 * Input: stones = [0,1,2,3,4,8,9,11]
 * Output: false
 * Explanation: There is no way to jump to the last stone as the gap between the 5th and 6th stone is too large.
 *
 * Constraints:
 * 2 <= stones.length <= 2000
 * 0 <= stones[i] <= 2^31 - 1
 * stones[0] == 0
 * stones is sorted in a strictly increasing order.
 */
public class Frog_Jump_403 {
    public static boolean canCross(int[] stones) {
        return canCross_1(stones);
    }

    /**
     * Thinking：
     * 1.除了第一个和最后一个之外，每个stone都存在三种走法，即(k-1 or k or k+1)。
     * 2.naive solution。
     * 每个stone都存在三种可能，采用递归法。
     * 时间复杂度：O(3^N)。
     * 3.比较典型的DP问题。
     * 3.1.每个stone[i]维护一个k[]列表，用来计算从[i]出发可能到达的stone。
     * 3.2.遍历stone[i]的k[]列表，计算目标stone的k[]。
     * 3.3.初始化时，stone[0]的k[]列表=[1]
     *
     * 验证通过
     * Runtime 128 ms Beats 17.34%
     * Memory 54.4 MB Beats 27.68%
     *
     * @param stones
     * @return
     */
    public static boolean canCross_1(int[] stones) {
        //初始化缓存
        Map<Integer, Set<Integer>> dp = new HashMap<>();
        dp.put(1, new HashSet<>());
        dp.get(1).add(1);
        dp.get(1).add(2);

        //遍历stones并计算计算k的三种情况
        for (int i = 1; i < stones.length - 1; i++) {
            //不在stones中的直接跳过
            if (!dp.containsKey(stones[i])) continue;
            for (int k : dp.get(stones[i])) {
                if (k <= 0) continue;
                int nextStone = stones[i] + k;
                dp.putIfAbsent(nextStone, new TreeSet<>());
                dp.get(nextStone).add(k - 1);
                dp.get(nextStone).add(k);
                dp.get(nextStone).add(k + 1);
            }
        }
        return dp.containsKey(stones[stones.length - 1]);
    }

    public static void main(String[] args) {
        do_func(new int[]{0, 1, 3, 5, 6, 8, 12, 17}, true);
        do_func(new int[]{0, 1, 2, 3, 4, 8, 9, 11}, false);
    }

    private static void do_func(int[] stones, boolean expected) {
        boolean ret = canCross(stones);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
