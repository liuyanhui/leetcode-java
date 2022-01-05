package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 56. Merge Intervals
 * Medium
 * ---------------------------
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 * Example 1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 *
 * Example 2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 * Constraints:
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= start_i <= end_i <= 10^4
 */
public class Merge_Intervals_56 {
    /**
     * 算法如下：
     * 1.排序
     * 2.循环遍历数组
     * 3.比较[s1,e1][s2,e2],并合并。算法如下：
     *     if(s2<=e1)
     *         合并后为[s1,max(e1,e2)]
     *     else
     *         不合并，[s1,e1]=[s2,e2]，计算下一个interval
     * Time Complexity:O(NlogN)
     *
     * 验证通过：
     * Runtime: 8 ms, faster than 29.17% of Java online submissions for Merge Intervals.
     * Memory Usage: 43.8 MB, less than 19.79% of Java online submissions for Merge Intervals.
     *
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList();
        //下面的三种写法可以互换
//        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
//        Arrays.sort(intervals, (o1, o2) -> o1[0]-o2[0]);
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int[] cur = intervals[0];
        int[] next = null;
        for (int i = 1; i < intervals.length; i++) {
            next = intervals[i];
            if (cur[1] >= next[0]) {//merge
                cur[1] = Math.max(cur[1], next[1]);
            } else {//do not merge,only add cur to list
                list.add(cur);
                cur = next;
            }
        }
        list.add(cur);
        //下面的代码可替换
        return list.toArray(new int[list.size()][2]);
//        int[][] ret = new int[list.size()][2];
//        for (int i = 0; i < list.size(); i++) {
//            ret[i] = list.get(i);
//        }
//        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}, new int[][]{{1, 6}, {8, 10}, {15, 18}});
        do_func(new int[][]{{1, 4}, {4, 5}}, new int[][]{{1, 5}});
        do_func(new int[][]{{1, 4}}, new int[][]{{1, 4}});
        do_func(new int[][]{{1, 4}, {1, 4}, {1, 4}}, new int[][]{{1, 4}});
    }

    private static void do_func(int[][] intervals, int[][] expected) {
        int[][] ret = merge(intervals);
        ArrayUtils.printIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("-----------");
    }
}
