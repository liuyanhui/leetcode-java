package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 295. Find Median from Data Stream
 * Hard
 * -----------------
 * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value and the median is the mean of the two middle values.
 *  - For example, for arr = [2,3,4], the median is 3.
 *  - For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 *
 * Implement the MedianFinder class:
 *  - MedianFinder() initializes the MedianFinder object.
 *  - void addNum(int num) adds the integer num from the data stream to the data structure.
 *  - double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 *
 * Example 1:
 * Input
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * Output
 * [null, null, null, 1.5, null, 2.0]
 * Explanation
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
 * medianFinder.addNum(3);    // arr[1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 *
 * Constraints:
 * -10^5 <= num <= 10^5
 * There will be at least one element in the data structure before calling findMedian.
 * At most 5 * 10^4 calls will be made to addNum and findMedian.
 *
 * Follow up:
 * If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 * If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 */
public class Find_Median_from_Data_Stream_295 {

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking
     * 1. 维护一个 List 用来保存输入的数，采用 binary search 定位并插入新的数，利用 List 支持随机访问的特性获取 median 。
     * 这种方式再插入 List 时性能较差，因为要移动较多元素。
     * 2. 采用 Heap 存储输入的数，并实时计算median。
     * add(num)
     * IF num < median THEN median = {binary search heap中小于median的最大值}
     * ELSE IF num > median THEN median = {binary search heap中大于median的最小值}
     * ELSE IF num == median THEN do nothing
     * 此方案需要自己实现支持重复数据的 TreeSet .
     * 3. 采用两个 heap ，分别保存较大的一半和较小的一半，这个方案更有可行性。
     *
     *
     */

    /**
     * review
     * 金矿
     * 参考思路：
     * https://leetcode.com/problems/find-median-from-data-stream/solutions/1330646/c-java-python-minheap-maxheap-solution-picture-explain-clean-concise/
     * https://leetcode.com/problems/find-median-from-data-stream/discuss/74062/Short-simple-JavaC%2B%2BPython-O(log-n)-%2B-O(1)
     *
     * 验证通过：Runtime 287 ms Beats 33.75%
     *          Memory 139 MB Beats 5.6%
     *
     * 说明：
     * 1.用数用两个Heap存储，较大的一半在小顶堆small中，较小的一半在大顶堆large中
     * 2.保持large的长度大于等于small的长度
     * 3.如果总数量是奇数，median就是large的堆顶数字；如果是偶数，median就是(large堆顶数字+small堆顶数字)/2
     *
     */
    static class MedianFinder {
        Queue<Long> largerHalf, smallerHalf;

        public MedianFinder() {
            largerHalf = new PriorityQueue<>();
            smallerHalf = new PriorityQueue<>();
        }

        public void addNum(int num) {
            largerHalf.offer((long) num);
            smallerHalf.offer(-largerHalf.poll());
            if (largerHalf.size() < smallerHalf.size()) {
                largerHalf.offer(-smallerHalf.poll());
            }
        }

        public double findMedian() {
            if (largerHalf.size() > smallerHalf.size()) {
                return largerHalf.peek();
            } else {
                return (largerHalf.peek() - smallerHalf.peek()) / 2;
            }
        }

    }

    /**
     * 思考：
     * 1.加入时先排序，再查找并计算
     *
     * 验证失败：Time Limit Exceeded
     */
    static class MedianFinder_error {
        List<Integer> values;

        public MedianFinder_error() {
            values = new ArrayList<>();
        }

        public void addNum(int num) {
            values.add(num);
            for (int i = values.size() - 2; i >= 0; i--) {
                if (values.get(i) < values.get(i + 1)) {
                    break;
                } else {
                    int t = values.get(i);
                    values.set(i, values.get(i + 1));
                    values.set(i + 1, t);
                }
            }
        }

        public double findMedian() {
            double res;
            int len = values.size();
            if (len % 2 == 0) {
                res = (double) (values.get(len / 2) + values.get(len / 2 - 1)) / 2;
            } else {
                res = values.get(len / 2);
            }
            return res;
        }

    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);    // arr = [1]
        medianFinder.addNum(2);    // arr = [1, 2]
        medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);    // arr[1, 2, 3]
        medianFinder.findMedian(); // return 2.0
        System.out.println(medianFinder.findMedian());

        System.out.println("------------------");

        medianFinder = new MedianFinder();
        medianFinder.addNum(-1);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(-2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(-3);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(-4);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(-5);
        System.out.println(medianFinder.findMedian());

        System.out.println("------------------");

        medianFinder = new MedianFinder();
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(10);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(5);
        System.out.println(medianFinder.findMedian());
    }

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}
