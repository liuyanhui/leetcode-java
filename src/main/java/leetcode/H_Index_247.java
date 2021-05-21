package leetcode;

import java.util.Arrays;

/**
 * 274. H-Index
 * Medium
 * -------------------------
 * Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper, return compute the researcher's h-index.
 *
 * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have at least h citations each, and the other n − h papers have no more than h citations each.
 *
 * If there are several possible values for h, the maximum one is taken as the h-index.
 *
 * Example 1:
 * Input: citations = [3,0,6,1,5]
 * Output: 3
 * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, their h-index is 3.
 *
 * Example 2:
 * Input: citations = [1,3,1]
 * Output: 1
 *
 * Constraints:
 * n == citations.length
 * 1 <= n <= 5000
 * 0 <= citations[i] <= 1000
 */
public class H_Index_247 {
    public static int hIndex(int[] citations) {
        return hIndex_2(citations);
    }

    /**
     * bucket sort 思路
     *
     * 参考思路：
     * https://leetcode.com/problems/h-index/discuss/70768/Java-bucket-sort-O(n)-solution-with-detail-explanation
     *
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
     *
     * Time Complexity：O(NlogN)
     *
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
