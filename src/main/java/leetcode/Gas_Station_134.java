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
 * n == gas.length == cost.length
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 */
public class Gas_Station_134 {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        return canCompleteCircuit_r3_1(gas, cost);
    }

    /**
     *
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. tank有两个动作：加油和耗油
     * 2. 结果有两步：能否走完一圈；从哪个station开始出发。
     * 3. 把gas和cost合并成一个数组（设为A[]），使用类似滑动窗口的思路。窗口之和大于等于0时，从右侧扩大窗口；小于0时，从左侧缩小窗口。
     * review mod计算，在使用数组存储的环状数据的场景下的巧妙之处
     *
     * 验证通过：性能一般
     * Runtime 6 ms Beats 12.58%
     * Memory 56.42 MB Beats 90.18%
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit_r3_1(int[] gas, int[] cost) {
        int i = 0, j = 0;
        int remain = 0;
        int n = gas.length;
        while (i < n) {
            remain += (gas[j % n] - cost[j % n]);
            while (remain < 0 && i <= j % n) {
                remain -= (gas[i] - cost[i]);
                i++;
            }
            j++;//review j可以一直累加，通过mod运算符将其转换成0~n-1的数字
            if (i == j % n && i < j && remain >= 0) break;
        }
        return i == n ? -1 : i;
    }

    /**
     * review
     * round 2
     *
     * 思考过程：
     * 思路1：
     * 穷举法：从0~n依次计算，如果第i站满足条件，那么直接返回i。每次都要从i到i-1一次累加计算，如果最终sum>=0，那么返回i。
     * 穷举法中存在的问题：
     * 重复计算：每一个站都重新计算gas的变化，可以提前计算好。防止重复计算。公式为：gas[i]-cost[i]。大于0表示gas盈余，小于0表示gas不足。
     * 如下所示：
     * 例 1
     * gas  = [1,2,3,4,5]
     * cost = [3,4,5,1,2]
     * left = [-2,-2,-2,3,3]
     * 例 2
     * gas  = [2,3,4]
     * cost = [3,4,3]
     * left = [-1,-1,1]
     *
     * 可以看出sum(left[])<0时，无解，直接返回-1；
     * 问题转化为在一维数组中寻找某个满足条件的数字的问题。
     * 转化后的问题描述：在一个首尾连接的一维数组中，找到一个数，从这个数开始沿顺时针方向向前累加，当再次抵达这个数时，和大于等于0。
     *
     * 算法：
     * 1.数组为num[],双指针为slow，fast
     * 2.遍历数组
     * 3.sum+=num[fast]，fast++
     * 4.如果sum>0，continue
     * 5.循环执行：如果sum<0，sum-=num[slow]，slow++
     * 6.当fast到达末尾时，终止遍历
     * 7.返回值为sum>=0?slow:-1
     *
     * 比较清晰的思路：
     * 参考资料：
     * https://leetcode.com/problems/gas-station/discuss/42568/Share-some-of-my-ideas.
     * 如下：
     * I have thought for a long time and got two ideas:
     * 1.If car starts at A and can not reach B. Any station between A and B can not reach B.(B is the first station that A can not reach.)
     * 2.If the total number of gas is bigger than the total number of cost. There must be a solution.
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 88.54% of Java online submissions for Gas Station.
     * Memory Usage: 62.3 MB, less than 87.92% of Java online submissions for Gas Station.
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit_3(int[] gas, int[] cost) {
        int origin = 0;
        int sum = 0;
        int rightSum = 0;
        int[] remain = new int[gas.length];
        for (int i = 0; i < gas.length; i++) {
            remain[i] = gas[i] - cost[i];//TODO 其实不需要remain数组，因为下标是已知的，所以实时计算即可无需保存在数组中。
            sum += remain[i];
            rightSum += remain[i];
            while (rightSum < 0) {//TODO 这里可以把while循环优化为if语句: if (rightSum<0) origin=i+1;rightSum=0;
                rightSum -= remain[origin];//TODO 其实不需要remain数组，因为下标是已知的，所以实时计算即可无需保存在数组中。
                origin++;
            }
        }
        return sum >= 0 ? origin : -1;
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
        do_func(new int[]{5, 1, 2, 3, 4}, new int[]{4, 4, 1, 5, 1}, 4);
    }

    private static void do_func(int[] gas, int[] cost, int expected) {
        int ret = canCompleteCircuit(gas, cost);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
