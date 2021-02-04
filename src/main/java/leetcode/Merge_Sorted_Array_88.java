package leetcode;

/**
 * https://leetcode.com/problems/merge-sorted-array/
 * 88. Merge Sorted Array
 * Easy
 * -----------------------
 *Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * The number of elements initialized in nums1 and nums2 are m and n respectively. You may assume that nums1 has a size equal to m + n such that it has enough space to hold additional elements from nums2.
 *
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 *
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 *
 * Constraints:
 * nums1.length == m + n
 * nums2.length == n
 * 0 <= m, n <= 200
 * 1 <= m + n <= 200
 * -109 <= nums1[i], nums2[i] <= 109
 */
public class Merge_Sorted_Array_88 {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        merge_2(nums1, m, nums2, n);
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
    }

    private static void do_func(int[] nums1, int m, int[] nums2, int n, int[] expected) {
        merge(nums1, m, nums2, n);
        ArrayUtils.printIntArray(nums1);
        ArrayUtils.isSameThenPrintln(nums1, expected);
        System.out.println("--------------");
    }
}
