package leetcode;

/**
 * 275. H-Index II
 * Medium
 * ---------------------
 * Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper and citations is sorted in ascending order, return the researcher's h-index.
 * <p>
 * According to the definition of h-index on Wikipedia: The h-index is defined as the maximum value of h such that the given researcher has published at least h papers that have each been cited at least h times.
 * <p>
 * You must write an algorithm that runs in logarithmic time.
 * <p>
 * Example 1:
 * Input: citations = [0,1,3,5,6]
 * Output: 3
 * Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had received 0, 1, 3, 5, 6 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, their h-index is 3.
 * <p>
 * Example 2:
 * Input: citations = [1,2,100]
 * Output: 2
 * <p>
 * Constraints:
 * n == citations.length
 * 1 <= n <= 10^5
 * 0 <= citations[i] <= 1000
 * citations is sorted in ascending order.
 */
public class H_Index_II_275 {
    public static int hIndex(int[] citations) {
        return hIndex_r3_1(citations);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * [group] 275. H-Index II
     * <p>
     * Thinking
     * 1. naive solution
     * 穷举每种可能。
     * 2.
     * n = citations.length
     * citations 是[i]已排序的。citations[i] 表示引用数大于等于 citations[i] 的论文有 n-i 个。
     * 当 i 从0~n-1 变化时，citations[i] 是递增的，n-i 是递减的。 根据 h-index 的定义，当 citations[i]==n-i时，就是所求。
     * <p>
     * hIndex_2()的binary search 性能更优。
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 28.19%
     * Analyze Complexity
     * Memory 48.14 MB Beats 24.08%
     *
     * @param citations
     * @return
     */
    public static int hIndex_r3_1(int[] citations) {
        int res = 0;
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            if (n - i <= citations[i]) {
                return n - i;
            }
        }
        return res;
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.跟274是一样的，只是274的输入是未排序的。
     * 2.在排序后的数组中查找某个数，可以使用二分查找法优化时间复杂度。见hIndex_2()
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for H-Index II.
     * Memory Usage: 52.3 MB, less than 51.06% of Java online submissions for H-Index II.
     *
     * @param citations
     * @return
     */
    public static int hIndex_3(int[] citations) {
        int h = 0;
        for (int i = citations.length - 1; i >= 0; i--) {
            if (citations[i] < (citations.length - i)) {
                return citations.length - i - 1;
            } else {
                h++;
            }
        }
        return h;
    }

    /**
     * review round 2
     * 参考思路：
     * https://leetcode.com/problems/h-index-ii/discuss/71063/Standard-binary-search
     * https://leetcode.com/problems/h-index-ii/discuss/71124/Java-binary-search-simple-and-clean
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for H-Index II.
     * Memory Usage: 45.7 MB, less than 79.69% of Java online submissions for H-Index II.
     *
     * @param citations
     * @return
     */
    public static int hIndex_2(int[] citations) {
        int l = 0, r = citations.length - 1;
        int n = citations.length;
        while (l <= r) {//"<="很关键,而不是"<"
            int mid = (l + r) / 2;
            if (citations[mid] < n - mid) {
                l = mid + 1;
            } else if (citations[mid] > n - mid) {
                r = mid - 1;
            } else {
                return n - mid;
            }
        }
        return n - l;
    }

    /**
     * 274. H-Index的方案
     * <p>
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for H-Index II.
     * Memory Usage: 53 MB, less than 6.71% of Java online submissions for H-Index II.
     *
     * @param citations
     * @return
     */
    public static int hIndex_1(int[] citations) {
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
        do_func(new int[]{0, 1, 3, 5, 6}, 3);
        do_func(new int[]{1, 2, 100}, 2);
        do_func(new int[]{1, 2, 99, 100}, 2);
        do_func(new int[]{99, 100}, 2);
        do_func(new int[]{1, 1, 1, 1, 1, 2, 3, 4, 5, 100}, 3);
        do_func(new int[]{0, 0, 4, 4}, 2);
        do_func(new int[]{0, 0, 0, 0}, 0);
        do_func(new int[]{0}, 0);
        do_func(new int[]{10}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = hIndex(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
