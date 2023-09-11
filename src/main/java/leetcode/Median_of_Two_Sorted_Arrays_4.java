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
 * -10^6 <= nums1[i], nums2[i] <= 10^6
 */
public class Median_of_Two_Sorted_Arrays_4 {
    /**
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return findMedianSortedArrays_4(nums1, nums2);
    }

    /**
     * round3
     *
     * 参考之前的方案，采用递归思路
     *
     * Thinking：
     * 1.属于在两个已排序数组中获取第k小的数的问题。需要考虑总数是奇数还是偶数。
     * 2.naview solution
     * 排序两个数组，然后获取中位数。
     * 3.merge sort solution
     * 3.1.比较两个数组的最小值
     * 3.2.删除最小值，k=k-1
     * 3.3.重复执行【2.1.】，直到k=0
     * 3.4.如果总数的奇数，返回k=0时两个数组的最小值的最小值；如果是偶数，返回k=0时，两个数组最小值的平均数。
     * 4.recursive solution
     * 4.1.数字总数是奇数，计算len/2
     * 4.1.如果k==1，返回min(nums1[beg1],nums[beg2])
     * 4.2.如果k>1，每次从两个数组中截取前k/2个数，比较nums1[beg1+k/2],nums[beg2+k/2]。
     * 4.2.1.如果nums1[beg1+k/2]<nums[beg2+k/2]，beg1=beg1+k/2+1
     * 4.2.2.否则如果nums1[beg1+k/2]<nums[beg2+k/2]，beg2=beg2+k/2+1
     * 4.2.3.如果beg+k/2越界，用极大值替换nums[beg+k/2]进行比较。
     * 4.2.4.递归执行【4.1】
     *
     * 还有一种直接使用binary search的方法。它只使用与求中位数，无法求kth：
     * https://leetcode.com/problems/median-of-two-sorted-arrays/solutions/2471/very-concise-o-log-min-m-n-iterative-solution-with-detailed-explanation/
     *
     * 验证通过：
     * Runtime 1 ms Beats 100%
     * Memory 44.9 MB Beats 13.92%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays_4(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 1) {
            return helper_4(nums1, 0, nums2, 0, len / 2 + 1);
        } else {
            return (helper_4(nums1, 0, nums2, 0, len / 2) + helper_4(nums1, 0, nums2, 0, len / 2 + 1)) / 2;
        }
    }

    /**
     * k 从1开始而不是从0开始
     */
    private static double helper_4(int[] nums1, int beg1, int[] nums2, int beg2, int k) {
        if (beg1 >= nums1.length) return nums2[beg2 + k - 1];
        if (beg2 >= nums2.length) return nums1[beg1 + k - 1];
        if (k == 1) return Math.min(nums1[beg1], nums2[beg2]);

        int mid1 = Integer.MAX_VALUE, mid2 = Integer.MAX_VALUE;
        if (beg1 + k / 2 - 1 < nums1.length) mid1 = nums1[beg1 + k / 2 - 1];
        // review nums2取剩下的偏移量，否则会导致多取。用例({1, 3},{2, 7})无法通过
        if (beg2 + k - k / 2 - 1 < nums2.length) mid2 = nums2[beg2 + k - k / 2 - 1];

        if (mid1 < mid2) {
            // review  最后一个参数取剩下的偏移量，否则会导致多取。用例({1, 3},{2, 7})无法通过
            return helper_4(nums1, beg1 + k / 2, nums2, beg2, k - k / 2);
        } else {
            // review  最后一个参数取剩下的偏移量，否则会导致多取。用例({1, 3},{2, 7})无法通过
            return helper_4(nums1, beg1, nums2, beg2 + k - k / 2, k / 2);
        }
    }

    /**
     * 递归法，
     * 问题转化为寻找第k小的数，
     * 假设nums1.length=m，nums2.length=n
     * 1.k=(m+n)/2
     * 2.k=n/2，当nums1[(m+n)/2]<nums2[(m+n)/2]时，去掉nums1中的[0:k/2]区间的数
     * 3.k=(n-m)/2，当nums1[n/2]<nums2[n/2]时，去掉nums1中的[0:k/2]区间的数
     *
     * round 2 ：2021.11.3
     * 验证通过：
     * Runtime: 2 ms, faster than 99.90% of Java.
     * Memory Usage: 40.1 MB, less than 86.17% of Java
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays_3(int[] nums1, int[] nums2) {
        if ((nums1.length + nums2.length) % 2 == 0) {//偶数个数字
            double d1 = findKthNumber(nums1, 0, nums2, 0, (nums1.length + nums2.length) / 2 - 1);
            double d2 = findKthNumber(nums1, 0, nums2, 0, (nums1.length + nums2.length) / 2);
            return (d1 + d2) / 2;
        } else {//奇数个数字
            return findKthNumber(nums1, 0, nums2, 0, (nums1.length + nums2.length) / 2);
        }
    }

    /**
     *
     * @param nums1
     * @param beg1
     * @param nums2
     * @param beg2
     * @param k k从0开始
     * @return
     */
    private static double findKthNumber(int[] nums1, int beg1, int[] nums2, int beg2, int k) {
        //某个数组已经耗尽时
        if (beg1 >= nums1.length) {
            return nums2[beg2 + k];
        }
        if (beg2 >= nums2.length) {
            return nums1[beg1 + k];
        }
        //k==0时，直接比较两个数组第0个数字
        if (k == 0) {
            return Math.min(nums1[beg1], nums2[beg2]);
        }
        //两个数组较长的放后面
        if (nums1.length - beg1 > nums2.length - beg2) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
            int bt = beg1;
            beg1 = beg2;
            beg2 = bt;
        }
        //防止较短的数组溢出
        int idx1 = ((k / 2 + beg1 < nums1.length) ? k / 2 + beg1 : nums1.length - 1);
        int idx2 = k - (idx1 - beg1) + beg2 - 1;
        if (nums1[idx1] < nums2[idx2]) {
            return findKthNumber(nums1, idx1 + 1, nums2, beg2, k - (idx1 - beg1 + 1));
        } else {
            return findKthNumber(nums1, beg1, nums2, idx2 + 1, k - (idx2 - beg2 + 1));
        }
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
        if (aStart + k / 2 - 1 < A.length) aMid = A[aStart + k / 2 - 1];
        if (bStart + k / 2 - 1 < B.length) bMid = B[bStart + k / 2 - 1];

        if (aMid < bMid)
            return getkth(A, aStart + k / 2, B, bStart, k - k / 2);// Check: aRight + bLeft
        else
            return getkth(A, aStart, B, bStart + k / 2, k - k / 2);// Check: bRight + aLeft
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/median-of-two-sorted-arrays/solution/
     * 中文翻译：
     * https://zhuanlan.zhihu.com/p/70654378
     * 我：因为有两个数组，需要对两个变量进行查找。而i+j=m-i+n-j这个等式，把两个变量简化为1个变量。这样降低了复杂度，并且可以使用binary search进行查找。
     * @param A
     * @param B
     * @return
     */
    public static double findMedianSortedArrays_1(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }

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
        do_func(new int[]{1, 3, 4, 5, 6, 7, 8, 9}, new int[]{2}, 5.00000);
        do_func(new int[]{2, 3, 4, 5, 6, 7, 8, 9}, new int[]{1}, 5.00000);
        do_func(new int[]{1, 3}, new int[]{2, 7}, 2.50000);
        do_func(new int[]{0, 0, 0, 0, 0}, new int[]{-1, 0, 0, 0, 0, 0, 1}, 0.00000);
    }

    private static void do_func(int[] nums1, int[] nums2, double expected) {
        double ret = findMedianSortedArrays(nums1, nums2);
        System.out.println(ret);

        System.out.println(Double.compare(ret, expected) == 0);
        System.out.println("--------------");
    }

}
