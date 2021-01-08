package leetcode;

/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 * 4. Median of Two Sorted Arrays
 * Hard
 * ------------------
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 * Follow up: The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 *
 *  Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 *  Example 3:
 * Input: nums1 = [0,0], nums2 = [0,0]
 * Output: 0.00000
 *
 * Example 4:
 * Input: nums1 = [], nums2 = [1]
 * Output: 1.00000
 *
 * Example 5:
 * Input: nums1 = [2], nums2 = []
 * Output: 2.00000
 *
 * Constraints:
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -106 <= nums1[i], nums2[i] <= 106
 */
public class Median_of_Two_Sorted_Arrays_4 {
    /**
     * 未完成
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return findMedianSortedArrays_2(nums1,nums2);
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2496/Concise-JAVA-solution-based-on-Binary-Search
     * 因为m+n长度是已知的，那么这个问题就可以转化成：在两个sorted的数组中查找第k大的数字，k=(m+n)/2，k从0开始。
     * 当m+n是奇数时，结果为第k个数，k从0开始；
     * 当m+n是偶数时，结果为第k个数和第k-1个数的平均数，k从0开始。
     * 在递归过程中，每次递归的k是递减的，直到k=0.
     *
     * if (mid1 < mid2) keep nums1.right + nums2
     * else keep nums1 + nums2.right
     * @param A
     * @param B
     * @return
     */
    public static double findMedianSortedArrays_2(int[] A, int[] B) {
        int m = A.length, n = B.length;
        int l = (m + n + 1) / 2;
        int r = (m + n + 2) / 2;
        return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
    }

    private static double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
        //A已经耗尽，返回B中的元素
        if (aStart > A.length - 1) return B[bStart + k - 1];
        //B耗尽，返回A中的元素
        if (bStart > B.length - 1) return A[aStart + k - 1];
        //k耗尽
        if (k == 1) return Math.min(A[aStart], B[bStart]);

        int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
        if (aStart + k/2 - 1 < A.length) aMid = A[aStart + k/2 - 1];
        if (bStart + k/2 - 1 < B.length) bMid = B[bStart + k/2 - 1];

        if (aMid < bMid)
            return getkth(A, aStart + k/2, B, bStart,       k - k/2);// Check: aRight + bLeft
        else
            return getkth(A, aStart,       B, bStart + k/2, k - k/2);// Check: bRight + aLeft
    }
    /**
     * 参考思路：
     * https://leetcode.com/problems/median-of-two-sorted-arrays/solution/
     * 中文翻译：
     * https://zhuanlan.zhihu.com/p/70654378
     * 我：因为有两个数组，需要对两个变量进行查找。而i+j=m-i+m-j这个等式，把两个变量简化为1个变量。这样降低了复杂度，并且可以使用binary search进行查找。
     * @param A
     * @param B
     * @return
     */
    public static double findMedianSortedArrays_1(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A; A = B; B = temp;
            int tmp = m; m = n; n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j-1] > A[i]){
                iMin = i + 1; // i is too small
            }
            else if (i > iMin && A[i-1] > B[j]) {
                iMax = i - 1; // i is too big
            }
            else { // i is perfect
                int maxLeft = 0;
                if (i == 0) { maxLeft = B[j-1]; }
                else if (j == 0) { maxLeft = A[i-1]; }
                else { maxLeft = Math.max(A[i-1], B[j-1]); }
                if ( (m + n) % 2 == 1 ) { return maxLeft; }

                int minRight = 0;
                if (i == m) { minRight = B[j]; }
                else if (j == n) { minRight = A[i]; }
                else { minRight = Math.min(B[j], A[i]); }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }


    public static void main(String[] args) {
        do_func(new int[]{1, 3}, new int[]{2}, 2.00000);
        do_func(new int[]{1, 3}, new int[]{9}, 3.00000);
        do_func(new int[]{1, 2}, new int[]{3, 4}, 2.50000);
        do_func(new int[]{0, 0}, new int[]{0, 0}, 0.00000);
        do_func(new int[]{}, new int[]{1}, 1.00000);
        do_func(new int[]{2}, new int[]{}, 2.00000);
    }

    private static void do_func(int[] nums1, int[] nums2, double expected) {
        double ret = findMedianSortedArrays(nums1, nums2);
        System.out.println(ret);

        System.out.println(Double.compare(ret, expected) == 0);
        System.out.println("--------------");
    }

}
