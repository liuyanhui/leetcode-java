package leetcode;

/**
 * 134. Gas Station
 * Medium
 * --------------------
 * There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
 *
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.
 *
 * Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique
 *
 * Example 1:
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 * Explanation:
 * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 4. Your tank = 4 - 1 + 5 = 8
 * Travel to station 0. Your tank = 8 - 2 + 1 = 7
 * Travel to station 1. Your tank = 7 - 3 + 2 = 6
 * Travel to station 2. Your tank = 6 - 4 + 3 = 5
 * Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
 * Therefore, return 3 as the starting index.
 *
 * Example 2:
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 * Explanation:
 * You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
 * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
 * Travel to station 0. Your tank = 4 - 3 + 2 = 3
 * Travel to station 1. Your tank = 3 - 3 + 3 = 3
 * You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
 * Therefore, you can't travel around the circuit once no matter where you start.
 *
 * Constraints:
 * gas.length == n
 * cost.length == n
 * 1 <= n <= 104
 * 0 <= gas[i], cost[i] <= 104
 */
public class Gas_Station_134 {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        return canCompleteCircuit_2(gas, cost);
    }

    /**
     * canCompleteCircuit_1()的代码简化版本，降低了时间和空间复杂度
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Gas Station.
     * Memory Usage: 39.2 MB, less than 71.59% of Java online submissions for Gas Station.
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit_2(int[] gas, int[] cost) {
        int left = 0;
        int min = Integer.MAX_VALUE, cur = 0, index = 0;
        for (int i = 0; i < gas.length; i++) {
            left = gas[i] - cost[i];
            cur += left;
            if (min > cur) {
                min = cur;
                index = i + 1;
            }
        }
        if (cur >= 0) return index % gas.length;
        else return -1;
    }

    /**
     * 关键在于思路：把2个数组降低成1个数组，以简化复杂度。
     * 具体如下：
     * 1.gas-cost后的数组surplus[]代表了每个station的前进到下一个station的gas盈余情况
     * 2.只需要计算gas-cost后的数组surplus[]即可
     * 3.从前向后累加surplus[]，记为sum[i]。当sum[i]全局最小时，i+1即为所求。
     * 4.如果sum[last]<0，表示没有解；如果sum[last]>=0,i+1即为所求。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Gas Station.
     * Memory Usage: 39.4 MB, less than 44.53% of Java online submissions for Gas Station.
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit_1(int[] gas, int[] cost) {
        int[] surplus = new int[gas.length];
        for (int i = 0; i < gas.length; i++) {
            surplus[i] = gas[i] - cost[i];
        }
        int min = Integer.MAX_VALUE, cur = 0, index = 0;
        for (int i = 0; i < surplus.length; i++) {
            cur += surplus[i];
            if (min > cur) {
                min = cur;
                index = i + 1;
            }
        }
        if (cur >= 0) return index % gas.length;
        else return -1;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}, 3);
        do_func(new int[]{2, 3, 4}, new int[]{3, 4, 3}, -1);
    }

    private static void do_func(int[] gas, int[] cost, int expected) {
        int ret = canCompleteCircuit(gas, cost);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
