package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/insert-interval/
 * 57. Insert Interval
 * Medium
 * -----------------------
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 *
 * You may assume that the intervals were initially sorted according to their start times.
 *
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 *
 *  Example 3:
 * Input: intervals = [], newInterval = [5,7]
 * Output: [[5,7]]
 *
 *  Example 4:
 * Input: intervals = [[1,5]], newInterval = [2,3]
 * Output: [[1,5]]
 *
 *  Example 5:
 * Input: intervals = [[1,5]], newInterval = [2,7]
 * Output: [[1,7]]
 *
 * Constraints:
 * 0 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= intervals[i][0] <= intervals[i][1] <= 105
 * intervals is sorted by intervals[i][0] in ascending order.
 * newInterval.length == 2
 * 0 <= newInterval[0] <= newInterval[1] <= 105
 */
public class Insert_Interval_57 {

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        return insert_1(intervals, newInterval);
    }

    /**
     * 参考思路：https://leetcode.com/problems/insert-interval/discuss/21600/Short-java-code
     *
     *  验证通过：
     * Runtime: 1 ms, faster than 98.41% of Java online submissions for Insert Interval.
     * Memory Usage: 41.4 MB, less than 41.54% of Java online submissions for Insert Interval.
     * @param intervals
     * @param newInterval
     * @return
     */
    public static int[][] insert_2(int[][] intervals, int[] newInterval) {
        List<int[]> retList = new ArrayList<>();
        int min = newInterval[0], max = newInterval[1];
        int i = 0;
        //先处理完全在newInterval左边的，不相交的
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            retList.add(intervals[i++]);
        }
        //再处理相交的
        while (i < intervals.length && newInterval[0] <= intervals[i][1] && intervals[i][0] <= newInterval[1]) {
            min = Math.min(min, intervals[i][0]);
            max = Math.max(max, intervals[i][1]);
            i++;
        }
        retList.add(new int[]{min, max});
        while (i < intervals.length) {
            retList.add(intervals[i++]);
        }

        int[][] retArray = new int[retList.size()][2];
        for (int j = 0; j < retList.size(); j++) {
            retArray[j] = retList.get(j);
        }
        return retArray;
    }

    /**
     * 思路：先全部排序，在根据类似（和)匹配的方式进行合并。
     * 验证失败，实现较为复杂
     * @param intervals
     * @param newInterval
     * @return
     */
    public static int[][] insert_1(int[][] intervals, int[] newInterval) {
        List<MyInterval> sortedList = new ArrayList<>();
        //所有元素大排序
        for (int i = 0; i < intervals.length; i++) {
            if (intervals[i] == null) {
                break;
            }
            if (newInterval[0] <= intervals[i][0]) {
                sortedList.add(new MyInterval(newInterval[0], 1));
                newInterval[0] = Integer.MAX_VALUE;
            }
            if (newInterval[1] <= intervals[i][0]) {
                sortedList.add(new MyInterval(newInterval[1], 2));
                newInterval[1] = Integer.MAX_VALUE;
            }
            sortedList.add(new MyInterval(intervals[i][0], 1));

            if (newInterval[0] <= intervals[i][1]) {
                sortedList.add(new MyInterval(newInterval[0], 1));
                newInterval[0] = Integer.MAX_VALUE;
            }
            if (newInterval[1] <= intervals[i][1]) {
                sortedList.add(new MyInterval(newInterval[1], 2));
                newInterval[1] = Integer.MAX_VALUE;
            }
            sortedList.add(new MyInterval(intervals[i][1], 2));
        }
        if (newInterval[0] < Integer.MAX_VALUE) {
            sortedList.add(new MyInterval(newInterval[0], 1));
        }
        if (newInterval[1] < Integer.MAX_VALUE) {
            sortedList.add(new MyInterval(newInterval[1], 2));
        }
        //类似于(和)的匹配方法
        List<int[]> retList = new ArrayList<>();
        int left = 0;
        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).signal == 1) {
                if (retList.size() > 0 && retList.get(retList.size() - 1)[1] == sortedList.get(i).value) {
                    left--;
                    continue;
                }
                if (left == 0) {
                    int[] tmp = new int[2];
                    tmp[0] = sortedList.get(i).value;
                    retList.add(tmp);
                }
                left++;
            }
            if (sortedList.get(i).signal == 2) {
                left--;
                if (left == 0) {
                    retList.get(retList.size() - 1)[1] = sortedList.get(i).value;
                }
            }
        }
        int[][] retArray = new int[retList.size()][2];
        for (int i = 0; i < retList.size(); i++) {
            retArray[i] = retList.get(i);
        }
        return retArray;
    }

    public static class MyInterval {
        public int value = 0;
        public int signal = 0;//1表示左；2表示右

        public MyInterval(int value, int signal) {
            this.value = value;
            this.signal = signal;
        }
    }

    /**
     * 采用类似于括号匹配的思路。
     * 1.把数组的左边数字看作(，右边数字看作)
     * 2.把输入的两个数组进行排序
     * 3.按照括号匹配的方式进行合并。如：连续的(和)作为一个区间；连续出现(取最小，连续出现)取最大。
     * -------------
     * 这个思路有问题，逻辑也比较复杂，代码实现很复杂。耗费2h多都没有完成。
     * @param intervals
     * @param newInterval
     * @return
     */
    public static int[][] insert_error(int[][] intervals, int[] newInterval) {
        int[][] ret = new int[intervals.length + 1][2];
        int[] signalArray = new int[intervals.length * 2 + 2];
        int[] midArray = new int[intervals.length * 2 + 2];
        boolean leftUsed = false;
        boolean rigthUsed = false;
        //整合重排序
        int index = 0;
        for (int i = 0; i < intervals.length; i++) {
            if (!leftUsed && newInterval[0] <= intervals[i][0]) {
                midArray[index] = newInterval[0];
                signalArray[index] = 1;
                index++;
                leftUsed = true;
            }
            midArray[index] = intervals[i][0];
            signalArray[index] = 1;
            index++;

            if (!rigthUsed && newInterval[1] <= intervals[i][1]) {
                midArray[index] = newInterval[1];
                signalArray[index] = 2;
                index++;
                rigthUsed = true;
            }
            midArray[index] = intervals[i][1];
            signalArray[index] = 2;
            index++;
        }
        //根据括号匹配的方式合并数据
        int left = 0;
        int retIndex = 0;
        for (int i = 0; i < midArray.length; i++) {
            if (signalArray[i] == 1) {
                left++;
                if (left == 1) {
                    ret[retIndex][0] = midArray[i];
                } else {
                    ret[retIndex][0] = ret[retIndex][0] > midArray[i] ? midArray[i] : ret[retIndex][0];
                }
            } else {
                left--;
                if (left == 0) {
                    ret[retIndex][1] = midArray[i];
                    retIndex++;
                } else {

                }
            }
        }
        int[][] finalArray = new int[retIndex][2];
        for (int i = 0; i < retIndex; i++) {
            if (ret[i][0] == 0) {
                break;
            }
            finalArray[i][0] = ret[i][0];
            finalArray[i][1] = ret[i][1];
        }
        return finalArray;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}, new int[][]{{1, 5}, {6, 9}});
        do_func(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}, new int[][]{{1, 2}, {3, 10}, {12, 16}});
        do_func(new int[][]{}, new int[]{5, 7}, new int[][]{{5, 7}});
        do_func(new int[][]{{1, 5}}, new int[]{2, 3}, new int[][]{{1, 5}});
        do_func(new int[][]{{1, 5}}, new int[]{2, 7}, new int[][]{{1, 7}});
        do_func(new int[][]{}, new int[]{2, 7}, new int[][]{{2, 7}});
        do_func(new int[][]{{1, 5}}, new int[]{5, 7}, new int[][]{{1, 7}});
    }

    private static void do_func(int[][] intervals, int[] newInterval, int[][] expected) {
        int[][] ret = insert(intervals, newInterval);
        boolean same = true;
        if (ret.length == expected.length) {
            for (int i = 0; i < ret.length; i++) {
                for (int j = 0; j < ret[0].length; j++) {
                    if (ret[i][j] != expected[i][j]) {
                        same = false;
                        break;
                    }
                }
            }
        } else {
            same = false;
        }
        System.out.println(same);
        System.out.println("-----------");
    }
}
