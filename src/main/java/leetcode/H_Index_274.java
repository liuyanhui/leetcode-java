package leetcode;

import java.util.Arrays;

/**
 * 274. H-Index
 * Medium
 * -------------------------
 * Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper, return the researcher's h-index.
 * <p>
 * According to the definition of h-index on Wikipedia: The h-index is defined as the maximum value of h such that the given researcher has published at least h papers that have each been cited at least h times.
 * <p>
 * Example 1:
 * Input: citations = [3,0,6,1,5]
 * Output: 3
 * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, their h-index is 3.
 * <p>
 * Example 2:
 * Input: citations = [1,3,1]
 * Output: 1
 * <p>
 * Constraints:
 * n == citations.length
 * 1 <= n <= 5000
 * 0 <= citations[i] <= 1000
 */
public class H_Index_274 {
    public static int hIndex(int[] citations) {
        return hIndex_r3_1(citations);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * [group] 275. H-Index II
     * <p>
     * Thinking
     * 1. naive solution
     * 返回结果 i 在 [0,len(citations)] 之间，可以使用穷举法找到 i 的最大值。
     * 满足以下条件的最大的 i：
     * i >= count([citations[0:-1] > i])
     * Time Complexity: O(N*N)
     * 2. 针对【1.】采用BUD思路优化。
     * 每次计算{count([citations[0:-1] > i])} 重复计算了。
     * --------
     * input citations=[3,0,6,1,5]
     * sort([3,0,6,1,5]) -> [0,1,3,5,6]
     * <p>
     * 排序后的引用   citations=[0,1,3,5,6]
     * 对应的论文数量 paper_cnt=[5,4 3 2 1]
     * h-index=[0,1,3,2,1]
     * 公式为：
     * h-index[i]=min(citations[i],paper_cnt[i])
     * max(h-index[])为所求
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 69.78%
     * Memory 41.78 MB Beats 34.70%
     *
     * @param citations
     * @return
     */
    public static int hIndex_r3_1(int[] citations) {
        Arrays.sort(citations);//review 这里可以采用hIndex_2()的bucket sort的思路
        int res = 0;
        int paperCnt = 0;
        int h_index = 0;
        for (int i = 0; i < citations.length; i++) {
            paperCnt = citations.length - i;
            h_index = Math.min(paperCnt, citations[i]);
            res = Math.max(res, h_index);//review 这里可以优化，提前结束计算。
        }
        return res;
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.先排序，再倒序查找。
     * 2.遍历数组，依次计算h-index。最大的h-index为所求。
     * 3.h-index下行后终止遍历
     * <p>
     * 验证通过：性能一般
     * Runtime: 9 ms, faster than 6.69% of Java online submissions for H-Index.
     * Memory Usage: 42.2 MB, less than 32.76% of Java online submissions for H-Index.
     * <p>
     * hIndex_2()更优
     *
     * @param citations
     * @return
     */
    public static int hIndex_3(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        Arrays.sort(citations);
        int h = 0;
        for (int i = 0; i < citations.length; i++) {
            int t = Math.min(citations[i], citations.length - i);
            if (t >= h) h = t;
            else break;
        }
        return h;
    }

    /**
     * bucket sort 思路
     * review round 2. bucket sort 比较冷门，需要理解适用场景。如：不要求完全有序的情况；range之间有序，但range内部无序；数字较大但slot有限，且不要求完全排序等。
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/h-index/discuss/70768/Java-bucket-sort-O(n)-solution-with-detail-explanation
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for H-Index.
     * Memory Usage: 37 MB, less than 22.09% of Java online submissions for H-Index.
     *
     * @param citations
     * @return
     */
    public static int hIndex_2(int[] citations) {
        int[] bucket = new int[citations.length + 1];
        for (int c : citations) {
            if (c > citations.length) {
                bucket[citations.length]++;
            } else {
                bucket[c]++;
            }
        }
        int count = 0;
        for (int i = bucket.length - 1; i >= 0; i--) {
            count += bucket[i];
            if (count >= i) {
                return i;
            }
        }
        return count;
    }

    /**
     * 先排序，再计算
     * <p>
     * Time Complexity：O(NlogN)
     * <p>
     * 验证通过：
     * Runtime: 2 ms, faster than 11.74% of Java online submissions for H-Index.
     * Memory Usage: 38.8 MB, less than 5.17% of Java online submissions for H-Index.
     *
     * @param citations
     * @return
     */
    public static int hIndex_1(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        Arrays.sort(citations);
        int count = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (citations[i] >= count + 1) {
                count++;
            } else {
                return count;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 0, 6, 1, 5}, 3);
        do_func(new int[]{2, 0, 6, 1, 5}, 2);
        do_func(new int[]{1, 3, 1}, 1);
        do_func(new int[]{19}, 1);
        do_func(new int[]{0}, 0);
        do_func(new int[]{1, 1, 1}, 1);
        do_func(new int[]{3, 3, 3}, 3);
        do_func(new int[]{4, 4, 0, 0}, 2);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = hIndex(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
