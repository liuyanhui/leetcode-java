package leetcode;

import java.util.*;

/**
 * 349. Intersection of Two Arrays
 * Easy
 * ----------------------
 * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.
 *
 * Example 1:
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2]
 *
 * Example 2:
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [9,4]
 * Explanation: [4,9] is also accepted.
 *
 * Constraints:
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 1000
 */
public class Intersection_of_Two_Arrays_349 {
    public static int[] intersection(int[] nums1, int[] nums2) {
        return intersection_r3_1(nums1, nums2);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * review 一定要先做350
     *
     * 验证通过：
     * Runtime 0 ms Beats 100.00%
     * Memory 42.99 MB Beats 94.67%
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection_r3_1(int[] nums1, int[] nums2) {
        int[] hash = new int[1001];//review 变量的命名很优雅
        for (int n: nums1){
            hash[n]=1;
        }
        int index = 0;
        for(int n: nums2){
            if(hash[n]==1){
                nums1[index++]=n;
                hash[n]=0;
            }
        }
        return Arrays.copyOf(nums1,index);//review 变量的命名很优雅
    }
    /**
     * round 2
     * review 一定要先做350
     *
     * 可以把Map优化为buf，nums1和nums2长度和大小的上限都是1000
     *
     * 验证通过：
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection_2(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int n : nums1) {
            map.computeIfAbsent(n, v -> 1);
        }
        for (int n : nums2) {
            if (map.containsKey(n) && map.get(n) == 1) {
                list.add(n);
                map.put(n, -1);
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = list.get(i);
        return res;
    }

    /**
     * 验证通过：
     * Runtime: 2 ms, faster than 95.31% of Java online submissions for Intersection of Two Arrays.
     * Memory Usage: 39.4 MB, less than 37.01% of Java online submissions for Intersection of Two Arrays.
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection_1(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        for (int n : nums1) {
            set1.add(n);
        }
        Set<Integer> intersection = new HashSet<>();
        for (int n : nums2) {
            if (set1.contains(n)) {
                intersection.add(n);
            }
        }
        int[] ret = new int[intersection.size()];
        int i = 0;
        //approach 1
        for (int n : intersection) {
            ret[i++] = n;
        }

        //approach 2
//        Iterator<Integer> it = intersection.iterator();
//        while (it.hasNext()) {
//            ret[i++] = it.next();
//        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 2, 1}, new int[]{2, 2}, new int[]{2});
        do_func(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}, new int[]{9, 4});
    }

    private static void do_func(int[] nums1, int[] nums2, int[] expected) {
        int[] ret = intersection(nums1, nums2);
        ArrayUtils.printlnIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
