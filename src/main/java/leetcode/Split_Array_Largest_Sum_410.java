package leetcode;

/**
 * 410. Split Array Largest Sum
 * Hard
 * ------------------------------
 * Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest sum of any subarray is minimized.
 *
 * Return the minimized largest sum of the split.
 *
 * A subarray is a contiguous part of the array.
 *
 * Example 1:
 * Input: nums = [7,2,5,10,8], k = 2
 * Output: 18
 * Explanation: There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.
 *
 * Example 2:
 * Input: nums = [1,2,3,4,5], k = 2
 * Output: 9
 * Explanation: There are four ways to split nums into two subarrays.
 * The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 10^6
 * 1 <= k <= min(50, nums.length)
 */
public class Split_Array_Largest_Sum_410 {
    public static int splitArray(int[] nums, int k) {
        return splitArray_2(nums, k);
    }

    /**
     * 参考资料：
     * https://leetcode.com/problems/split-array-largest-sum/solutions/89817/clear-explanation-8ms-binary-search-java/
     *
     * Thinking：
     * 1.根据题意，结果在nums中的最大值max和nums的所有数之和之间，即max<=res<=sum。
     * 2.使用binary search在max和sum之间查找结果。这其中的关键就是如何确定v是正确结果，max<=v<=sum。
     * 3.详见代码
     *
     * 验证通过：
     * Runtime 1 ms Beats 56.83%
     * Memory 40.1 MB Beats 66.58%
     *
     * @param nums
     * @param k
     * @return
     */
    public static int splitArray_2(int[] nums, int k) {
        int max = 0, sum = 0;
        for (int n : nums) {
            max = Math.max(n, max);
            sum += n;
        }
        int l = max, r = sum;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (check(nums, mid, k)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    private static boolean check(int[] nums, int target, int k) {
        int t_sum = 0;
        int cnt = 1;
        for (int n : nums) {
            //计算每个subarray的和
            t_sum += n;
            //每个subarray的和都不能大于target。如果大于target表示当前数字属于下一个subarray。
            if (t_sum > target) {
                t_sum = n;
                cnt++;
            }
        }
        //cnt>k表示subarray数量太多，或target太小。
        return cnt > k;
    }

    /**
     * Thinking：
     * 1.分为三个步骤：分解为k个subarray；计算每个subarray最大值；计算全局最小值。
     * 1.1.第1个步骤采用递归法。本层递归从nums[0]~nums[0:len-k]循环取数作为当前层选取的subarray参与计算。
     *
     * 验证失败：Time Limit Exceed
     *
     * @param nums
     * @param k
     * @return
     */
    public static int splitArray_1(int[] nums, int k) {
        return helper(nums, k, 0, 0);
    }

    private static int helper(int[] nums, int k, int beg, int cur_max) {
        if (beg >= nums.length || k <= 0)
            return cur_max;
        if (k == 1) {
            int t = sum(nums, beg, nums.length - 1);
            return Math.max(t, cur_max);
        }
        int res = Integer.MAX_VALUE;
        for (int i = beg; i <= nums.length - k; i++) {
            int t_sum = sum(nums, beg, i);
            int t_res = helper(nums, k - 1, i + 1, Math.max(t_sum, cur_max));
            res = Math.min(res, t_res);

        }
        return res;
    }

    private static int sum(int[] nums, int beg, int end) {
        int t = 0;
        for (int i = beg; i <= end; i++) {
            t += nums[i];
        }
        return t;
    }

    public static void main(String[] args) {
        do_func(new int[]{7, 2, 5, 10, 8}, 2, 18);
        do_func(new int[]{1, 2, 3, 4, 5}, 2, 9);
        do_func(new int[]{7, 2, 5, 10, 8, 7, 2, 5, 10, 8}, 5, 17);
        do_func(new int[]{7, 2, 5}, 1, 14);
        do_func(new int[]{7, 2, 5, 10, 8}, 3, 14);
        do_func(new int[]{10, 5, 13, 4, 8, 4, 5, 11, 14, 9, 16, 10, 20, 8}, 8, 25);
        do_func(new int[]{5334, 6299, 4199, 9663, 8945, 3566, 9509, 3124, 6026, 6250, 7475, 5420, 9201, 9501, 38, 5897, 4411, 6638, 9845, 161, 9563, 8854, 3731, 5564, 5331, 4294, 3275, 1972, 1521, 2377, 3701, 6462, 6778, 187, 9778, 758, 550, 7510, 6225, 8691, 3666, 4622, 9722, 8011, 7247, 575, 5431, 4777, 4032, 8682, 5888, 8047, 3562, 9462, 6501, 7855, 505, 4675, 6973, 493, 1374, 3227, 1244, 7364, 2298, 3244, 8627, 5102, 6375, 8653, 1820, 3857, 7195, 7830, 4461, 7821, 5037, 2918, 4279, 2791, 1500, 9858, 6915, 5156, 970, 1471, 5296, 1688, 578, 7266, 4182, 1430, 4985, 5730, 7941, 3880, 607, 8776, 1348, 2974, 1094, 6733, 5177, 4975, 5421, 8190, 8255, 9112, 8651, 2797, 335, 8677, 3754, 893, 1818, 8479, 5875, 1695, 8295, 7993, 7037, 8546, 7906, 4102, 7279, 1407, 2462, 4425, 2148, 2925, 3903, 5447, 5893, 3534, 3663, 8307, 8679, 8474, 1202, 3474, 2961, 1149, 7451, 4279, 7875, 5692, 6186, 8109, 7763, 7798, 2250, 2969, 7974, 9781, 7741, 4914, 5446, 1861, 8914, 2544, 5683, 8952, 6745, 4870, 1848, 7887, 6448, 7873, 128, 3281, 794, 1965, 7036, 8094, 1211, 9450, 6981, 4244, 2418, 8610, 8681, 2402, 2904, 7712, 3252, 5029, 3004, 5526, 6965, 8866, 2764, 600, 631, 9075, 2631, 3411, 2737, 2328, 652, 494, 6556, 9391, 4517, 8934, 8892, 4561, 9331, 1386, 4636, 9627, 5435, 9272, 110, 413, 9706, 5470, 5008, 1706, 7045, 9648, 7505, 6968, 7509, 3120, 7869, 6776, 6434, 7994, 5441, 288, 492, 1617, 3274, 7019, 5575, 6664, 6056, 7069, 1996, 9581, 3103, 9266, 2554, 7471, 4251, 4320, 4749, 649, 2617, 3018, 4332, 415, 2243, 1924, 69, 5902, 3602, 2925, 6542, 345, 4657, 9034, 8977, 6799, 8397, 1187, 3678, 4921, 6518, 851, 6941, 6920, 259, 4503, 2637, 7438, 3893, 5042, 8552, 6661, 5043, 9555, 9095, 4123, 142, 1446, 8047, 6234, 1199, 8848, 5656, 1910, 3430, 2843, 8043, 9156, 7838, 2332, 9634, 2410, 2958, 3431, 4270, 1420, 4227, 7712, 6648, 1607, 1575, 3741, 1493, 7770, 3018, 5398, 6215, 8601, 6244, 7551, 2587, 2254, 3607, 1147, 5184, 9173, 8680, 8610, 1597, 1763, 7914, 3441, 7006, 1318, 7044, 7267, 8206, 9684, 4814, 9748, 4497, 2239}, 9, 194890);
    }

    private static void do_func(int[] nums, int k, int expected) {
        int ret = splitArray(nums, k);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
