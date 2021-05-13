package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 金矿：用整数的除法可以模拟出桶排序的场景
 * 220. Contains Duplicate III
 * Medium
 * -----------------------
 * Given an integer array nums and two integers k and t, return true if there are two distinct indices i and j in the array such that abs(nums[i] - nums[j]) <= t and abs(i - j) <= k.
 *
 * Example 1:
 * Input: nums = [1,2,3,1], k = 3, t = 0
 * Output: true
 *
 * Example 2:
 * Input: nums = [1,0,1,1], k = 1, t = 2
 * Output: true
 *
 * Example 3:
 * Input: nums = [1,5,9,1,5,9], k = 2, t = 3
 * Output: false
 *
 * Constraints:
 * 0 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^4
 * 0 <= t <= 2^31 - 1
 */
public class Contains_Duplicate_III_220 {
    public static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        return containsNearbyAlmostDuplicate_4(nums, k, t);
    }

    /**
     * bucket排序思路
     * 金矿：用整数除法实现桶排序
     * 参考思路：
     * https://leetcode.com/problems/contains-duplicate-iii/discuss/61645/AC-O(N)-solution-in-Java-using-buckets-with-explanation
     * https://leetcode.com/problems/contains-duplicate-iii/discuss/61639/JavaPython-one-pass-solution-O(n)-time-O(n)-space-using-buckets
     *
     * 验证通过：
     * Runtime: 18 ms, faster than 82.33% of Java online submissions for Contains Duplicate III.
     * Memory Usage: 42.1 MB, less than 32.03% of Java online submissions for Contains Duplicate III.
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public static boolean containsNearbyAlmostDuplicate_4(int[] nums, int k, int t) {
        if (k < 1 || t < 0) return false;
        Map<Long, Long> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //这里需要转换成long，否则会出错
            long resizedNum = (long) nums[i] - Integer.MIN_VALUE;//转换成正数
            long bucketIdx = resizedNum / ((long) t + 1);//防止t=0的情况
            //当前bucket和相邻的两个bucket都需要判断
            if (map.get(bucketIdx) != null
                    || (map.get(bucketIdx - 1) != null && resizedNum - map.get(bucketIdx - 1) <= t)
                    || (map.get(bucketIdx + 1) != null && map.get(bucketIdx + 1) - resizedNum <= t)) {
                return true;
            }
            //最早的移出bucket
//            if (map.entrySet().size() >= k) {
            if (i >= k) {
                //这里需要转换成long，否则会出错
                map.remove(((long) nums[i - k] - Integer.MIN_VALUE) / ((long) t + 1));
            }
            //加入bucket
            map.put(bucketIdx, resizedNum);
        }
        return false;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/contains-duplicate-iii/discuss/61655/Java-O(N-lg-K)-solution
     *
     * 类似滑动窗口的思路，但是使用了TreeMap管理窗口内的数据
     * 算是containsNearbyAlmostDuplicate_1的优化版本
     *
     * 验证通过：
     * Runtime: 40 ms, faster than 34.57% of Java online submissions for Contains Duplicate III.
     * Memory Usage: 40.1 MB, less than 86.51% of Java online submissions for Contains Duplicate III.
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public static boolean containsNearbyAlmostDuplicate_3(int[] nums, int k, int t) {
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            //找到最接近nums[i]的两个数
            Integer floor = treeSet.floor(nums[i]);
            Integer ceiling = treeSet.ceiling(nums[i]);
            //防止int超出最大范围，转化为long
            if ((floor != null && Math.abs((long) nums[i] - (long) floor) <= t)
                    || (ceiling != null && Math.abs((long) ceiling - (long) nums[i]) <= t)) {
                return true;
            }
            //加入窗口
            treeSet.add(nums[i]);
            //最早加入的数字从窗口删除
            if (i >= k) {
                treeSet.remove(nums[i - k]);
            }
        }
        return false;
    }

    /**
     * 滑动窗口思路，每次去掉窗口内最早的数字后，新加入窗口的数字从前向后比较
     *
     * 验证失败：Time Limit Exceeded
     * 逻辑正确，超时了
     *
     * Time Complexity:O(k*n)
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public static boolean containsNearbyAlmostDuplicate_2(int[] nums, int k, int t) {
        //防止k>nums.length的情况
        k = nums.length > (k + 1) ? (k + 1) : nums.length;

        for (int i = 1; i < nums.length; i++) {
            //下面两个分支的循环，可以简化成一个循环
            if (i < k) {
                //窗口大小小于k时，窗口不滑动
                for (int j = 0; j < i; j++) {
                    long t1 = nums[i];
                    long t2 = nums[j];
                    if (Math.abs(t1 - t2) <= t) {
                        return true;
                    }
                }
            } else {
                //滑动窗口向右，跳过第i-k个元素
                for (int j = i - k + 1; j < i; j++) {
                    long t1 = nums[i];
                    long t2 = nums[j];
                    if (Math.abs(t1 - t2) <= t) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 验证失败：Time Limit Exceeded
     * 逻辑正确，超时了
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public static boolean containsNearbyAlmostDuplicate_1(int[] nums, int k, int t) {
        //防止k>nums.length的情况
        k = nums.length > (k + 1) ? (k + 1) : nums.length;
        long[] kArray = new long[k];
        for (int i = 0; i <= nums.length - k; i++) {
            //提取k个数
            for (int j = 0; j < k; j++) {
                kArray[j] = nums[i + j];
            }
            //k个数排序
            Arrays.sort(kArray);
            //k个数中，存在abs(nums[i] - nums[j]) <= t
            for (int j = 1; j < kArray.length; j++) {
                if (Math.abs(kArray[j] - kArray[j - 1]) <= t) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3, 1}, 3, 0, true);
        do_func(new int[]{1, 0, 1, 1}, 1, 2, true);
        do_func(new int[]{1, 0, 1, 1}, 10, 2, true);
        do_func(new int[]{1, 5, 9, 1, 5, 9}, 2, 3, false);
        do_func(new int[]{1, 5, 9, 1, 5, 9}, 22, 3, true);
        do_func(new int[]{-2147483648, 2147483647}, 1, 1, false);
        do_func(new int[]{1, 2}, 0, 1, false);

    }

    private static void do_func(int[] nums, int k, int t, boolean expected) {
        boolean ret = containsNearbyAlmostDuplicate(nums, k, t);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
