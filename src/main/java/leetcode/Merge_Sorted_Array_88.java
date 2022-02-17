package leetcode;

/**
 * https://leetcode.com/problems/merge-sorted-array/
 * 88. Merge Sorted Array
 * Easy
 * -----------------------
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 *
 * The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 *
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
 * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
 *
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 * Explanation: The arrays we are merging are [1] and [].
 * The result of the merge is [1].
 *
 * Example 3:
 * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 * Output: [1]
 * Explanation: The arrays we are merging are [] and [1].
 * The result of the merge is [1].
 * Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
 *
 * Constraints:
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -10^9 <= nums1[i], nums2[i] <= 10^9
 */
public class Merge_Sorted_Array_88 {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        merge_3(nums1, m, nums2, n);
    }

    /**
     * round 2
     *
     * 1.先把nums1中的数组顺序不变向右平移n位，即平移到尾部
     * 2.采用类似插入排序的方法合并两个有序数组
     *
     * merge_2的方法更巧妙，直接从尾部开始由大到小排序计算。可以省去上面的步骤1。是one pass方案。
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Sorted Array.
     * Memory Usage: 42.9 MB, less than 16.68% of Java online submissions for Merge Sorted Array.
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge_3(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m - 1; i >= 0; i--) {
            nums1[n + i] = nums1[i];
        }
        int cur = 0;
        int mi = n, ni = 0;
        while (mi < m + n && ni < n) {
            nums1[cur++] = nums1[mi] < nums2[ni] ? nums1[mi++] : nums2[ni++];
        }
        //剩余的nums2数组中的元素直接合并
        for (int i = ni; i < n; i++) {
            nums1[cur++] = nums2[i];
        }
    }

    /**
     * 从后向前计算，从大到小排序。
     * Space complexity:O(m+n)
     * Time Complexity:O(m+n)
     * 参考思路：
     * https://leetcode.com/problems/merge-sorted-array/discuss/29522/This-is-my-AC-code-may-help-you
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Sorted Array.
     * Memory Usage: 39.1 MB, less than 73.13% of Java online submissions for Merge Sorted Array.
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge_2(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1;
        int index = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] < nums2[j]) {
                nums1[index--] = nums2[j--];
            } else {
                nums1[index--] = nums1[i--];
            }
        }
        //下面的代码是多余的，因为在nums1上保存数据，当j耗尽时，剩下的未经计算的nums1中的元素不需要计算。
//        while (i >= 0) {
//            nums1[index--] = nums1[i--];
//        }
        while (j >= 0) {
            nums1[index--] = nums2[j--];
        }
    }

    /**
     * 常规办法，先合并排序，再赋值给nums1
     * Space Complexity:O(2m+2n)
     * Time Complexity:O(2m+2n)
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Sorted Array.
     * Memory Usage: 39.5 MB, less than 22.93% of Java online submissions for Merge Sorted Array.
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge_1(int[] nums1, int m, int[] nums2, int n) {
        int[] tmp = new int[nums1.length];
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                tmp[i + j] = nums1[i];
                i++;
            } else {
                tmp[i + j] = nums2[j];
                j++;
            }
        }
        while (i < m) {
            tmp[i + j] = nums1[i++];
        }
        while (j < n) {
            tmp[i + j] = nums2[j++];
        }
        for (int k = 0; k < nums1.length; k++) {
            nums1[k] = tmp[k];
        }
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3, new int[]{1, 2, 2, 3, 5, 6});
        do_func(new int[]{1}, 1, new int[]{}, 0, new int[]{1});
        do_func(new int[]{0}, 0, new int[]{1}, 1, new int[]{1});
    }

    private static void do_func(int[] nums1, int m, int[] nums2, int n, int[] expected) {
        merge(nums1, m, nums2, n);
        ArrayUtils.printlnIntArray(nums1);
        ArrayUtils.isSameThenPrintln(nums1, expected);
        System.out.println("--------------");
    }
}
