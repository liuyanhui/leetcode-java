package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 352. Data Stream as Disjoint Intervals
 * Hard
 * ----------------------------------------
 * Given a data stream input of non-negative integers a1, a2, ..., an, summarize the numbers seen so far as a list of disjoint intervals.
 *
 * Implement the SummaryRanges class:
 * - SummaryRanges() Initializes the object with an empty stream.
 * - void addNum(int value) Adds the integer value to the stream.
 * - int[][] getIntervals() Returns a summary of the integers in the stream currently as a list of disjoint intervals [starti, endi]. The answer should be sorted by starti.
 *
 * Example 1:
 * Input
 * ["SummaryRanges", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals", "addNum", "getIntervals"]
 * [[], [1], [], [3], [], [7], [], [2], [], [6], []]
 * Output
 * [null, null, [[1, 1]], null, [[1, 1], [3, 3]], null, [[1, 1], [3, 3], [7, 7]], null, [[1, 3], [7, 7]], null, [[1, 3], [6, 7]]]
 * Explanation
 * SummaryRanges summaryRanges = new SummaryRanges();
 * summaryRanges.addNum(1);      // arr = [1]
 * summaryRanges.getIntervals(); // return [[1, 1]]
 * summaryRanges.addNum(3);      // arr = [1, 3]
 * summaryRanges.getIntervals(); // return [[1, 1], [3, 3]]
 * summaryRanges.addNum(7);      // arr = [1, 3, 7]
 * summaryRanges.getIntervals(); // return [[1, 1], [3, 3], [7, 7]]
 * summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
 * summaryRanges.getIntervals(); // return [[1, 3], [7, 7]]
 * summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
 * summaryRanges.getIntervals(); // return [[1, 3], [6, 7]]
 *
 * Constraints:
 * 0 <= value <= 10^4
 * At most 3 * 10^4 calls will be made to addNum and getIntervals.
 * At most 10^2 calls will be made to getIntervals.
 *
 * Follow up: What if there are lots of merges and the number of disjoint intervals is small compared to the size of the data stream?
 */
public class Data_Stream_as_Disjoint_Intervals_352 {
    /**
     * Thinking：
     * 1.记录已经add过的数字，已经add过的数字add时直接跳过
     * 2.用排序数组保存一维的interval，是经过合并的interval数组。使用binary search定位add的数字的位置。假设n在interval数组的插入位置为k，如果k是奇数，表示落在现有已经被合并的区间内，无需合并；如果k是偶数，表示落在已合并的区间外，需要合并或新增区间，规则为：
     * 3.如果interval[k-1]+1=n 且 n+1=interval[k]，合并两个区间[k-2,k-1]和[k,k+1]为一个区间[k-2,k+1]；如果interval[k-1]+1=n，左边合并；如果n+1=interval[k]，右侧合并；否则，新增区间。要考虑边界值的插入和更新。
     * 4.连续插入、删除List元素时要注意下标的变化。
     *
     * 验证通过：
     * Runtime 20 ms Beats 95.42%
     * Memory 43 MB Beats 39.79%
     */
    static class SummaryRanges {

        //缓存已经插入过的数字
        Set<Integer> seen;
        List<Integer> intervals;
        boolean pure;//intervalsCache是否经过修改，如果被修改过那么就要重新生产intervalsCache
        int[][] intervalsCache;

        public SummaryRanges() {
            seen = new HashSet<>();
            intervals = new ArrayList<>();
            pure = true;
            intervalsCache = new int[0][];
        }

        public void addNum(int value) {
            if (seen.contains(value)) return;
            if (intervals.size() == 0) {//第一个数字单独处理
                intervals.add(value);
                intervals.add(value);
            } else {
                int pos = findPosition(value);
                merge(pos, value);
            }
            seen.add(value);
            pure = false;
        }

        public int[][] getIntervals() {
            if (pure) return intervalsCache;
            int[][] res = new int[intervals.size() / 2][2];
            //从一维数组中成对提取，并放入结果集中
            for (int i = 0; i < intervals.size(); i += 2) {
                res[i / 2][0] = intervals.get(i);
                res[i / 2][1] = intervals.get(i + 1);
            }
            intervalsCache = res;
            pure = true;
            return res;
        }

        //binary search 找到应对插入的元素的位置
        private int findPosition(int n) {
            int res = 0;
            if (intervals == null || intervals.size() == 0)
                return res;

            int l = 0, r = intervals.size() - 1;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (n < intervals.get(mid)) {
                    r = mid - 1;
                } else if (n >= intervals.get(mid)) {
                    l = mid + 1;
                }
            }
            res = l;
            return res;
        }

        private void merge(int k, int n) {
            if (k == 0) {//插入list的头部
                if (intervals.get(0) == n + 1) {//插入时合并的情况
                    intervals.set(0, n);
                } else {//插入时无法合并的情况
                    intervals.add(0, n);
                    intervals.add(0, n);
                }
            } else if (k == intervals.size()) {//插入list的尾部
                if (intervals.get(intervals.size() - 1) == n - 1) {//插入时合并的情况
                    intervals.set(intervals.size() - 1, n);
                } else {//插入时无法合并的情况
                    intervals.add(n);
                    intervals.add(n);
                }
            } else if (k % 2 == 0) {//插入中间的情况，k为偶数时表示不包含在任何区间范围内
                if (intervals.get(k - 1) + 1 == n && n + 1 == intervals.get(k)) {//直接合并现有的两个区间
                    intervals.remove(k);//删除的顺序不能变
                    intervals.remove(k - 1);
                } else if (intervals.get(k - 1) + 1 == n) {//插入到左边的区间中
                    intervals.set(k - 1, n);
                } else if (n + 1 == intervals.get(k)) {//插入左右边的区间中
                    intervals.set(k, n);
                } else {//仅插入，不合并
                    intervals.add(k, n);
                    intervals.add(k, n);
                }
            } else {//k为奇数时，表示落在已有的区间内
                //pos落在现有区间内，无需合并
            }
        }
    }

    public static void main(String[] args) {
        SummaryRanges summaryRanges = new SummaryRanges();
        summaryRanges.addNum(1);      // arr = [1]
        summaryRanges.getIntervals(); // return [[1, 1]]
        summaryRanges.addNum(3);      // arr = [1, 3]
        summaryRanges.getIntervals(); // return [[1, 1], [3, 3]]
        summaryRanges.addNum(7);      // arr = [1, 3, 7]
        summaryRanges.getIntervals(); // return [[1, 1], [3, 3], [7, 7]]
        summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
        summaryRanges.getIntervals(); // return [[1, 3], [7, 7]]
        summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
        summaryRanges.getIntervals(); // return [[1, 3], [6, 7]]
    }

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(value);
 * int[][] param_2 = obj.getIntervals();
 */
}
