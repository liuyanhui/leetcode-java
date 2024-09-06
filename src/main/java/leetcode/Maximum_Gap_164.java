package leetcode;

/**
 * 164. Maximum Gap
 * Hard
 * --------------------------------
 * Given an integer array nums, return the maximum difference between two successive elements in its sorted form. If the array contains less than two elements, return 0.
 * <p>
 * You must write an algorithm that runs in linear time and uses linear extra space.
 * <p>
 * Example 1:
 * Input: nums = [3,6,9,1]
 * Output: 3
 * Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.
 * <p>
 * Example 2:
 * Input: nums = [10]
 * Output: 0
 * Explanation: The array contains less than 2 elements, therefore return 0.
 * <p>
 * Constraints:
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
public class Maximum_Gap_164 {
    public static int maximumGap(int[] nums) {
        return maximumGap_r3_1(nums);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking : 详见maximumGap_1()
     *
     * 验证通过：
     * Runtime 9 ms Beats 99.39%
     * Memory 57.90 MB Beats 89.11%
     *
     * @param nums
     * @return
     */
    public static int maximumGap_r3_1(int[] nums) {
        if (nums.length < 2) return 0;
        //查找最大和最小值
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }

        //定义bucket
        int bucket_size = (int) Math.ceil((double)(max - min + 1) / nums.length);//review 防止除数为0的情况。
        int[] min_buckets = new int[nums.length];//review 长度就是nums.length，由计算公式可推导
        int[] max_buckets = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            min_buckets[i] = Integer.MAX_VALUE;
            max_buckets[i] = Integer.MIN_VALUE;
        }
        //bucket sort
        for (int i = 0; i < nums.length; i++) {
            int idx = (nums[i] - min) / bucket_size;
            min_buckets[idx] = Math.min(min_buckets[idx], nums[i]);
            max_buckets[idx] = Math.max(max_buckets[idx], nums[i]);
        }
        //计算maxmium gap
        int ret = 0;
        int last = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (min_buckets[i] == Integer.MAX_VALUE) continue;
            ret = Math.max(ret, min_buckets[i] - last);
            last = max_buckets[i];
        }

        return ret;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/maximum-gap/discuss/50643/bucket-sort-JAVA-solution-with-explanation-O(N)-time-and-space
     * <p>
     * 直觉思路1
     * 1.排序
     * 2.比较计算，找到max gap
     * Time Complexity:O(NlogN)
     * Space Complexity:O(1)
     * <p>
     * bucket sort思路
     * 0.数组有N个元素，最大gap必然大于等于Ceiling((max-min)/(N-1))
     * 1.把数组分成N-1个bucket，然后进行bucket sort。
     * 2.每个bucket只需要存储局部最大值和最小值，即bucket[i]中保存两个值。原因见0.
     * 3.遍历每个bucket并计算最大gap。bucket[i]中的最小值-bucket[i-1]中的最大值就是参与max gap比较的值。
     * <p>
     * 算法分为两大步骤：1.bucket sort；2.traversal the buckets, calc the max gap
     * 具体如下：
     * 1.max=max(nums)，min=min(nums)，N=len(nums)
     * 2.bucket数组的大小为len_b=(max-min)/(N-1)
     * 3.初始化bucket。有两个bucket，为b_max[]和b_min[]，分别为bucket中的最大值和最小值
     * 4.遍历nums，填充bucket
     * 4.1. b_max[idx]=max(b_max[idx],nums[i])，b_min[idx]=min(b_min[idx],nums[i])，其中 idx=(nums[i]-min)/(N-1)
     * 5.遍历bucket计算max gap
     * 5.1 local=b_min[i]-b_max[i]
     * 5.2 ret = max(ret,local)
     * 6. ret为所求
     * <p>
     * 验证通过：
     * Runtime: 8 ms, faster than 100.00% of Java online submissions for Maximum Gap.
     * Memory Usage: 52.3 MB, less than 96.71% of Java online submissions for Maximum Gap.
     *
     * @param nums
     * @return
     */
    public static int maximumGap_1(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        //提取nums中的最大值和最小值
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int n : nums) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }
        //初始化bucket
        //注意必须用ceiling函数
        int gap = (int) Math.ceil((double) (max - min) / (nums.length - 1));
        //gap为0表示所有数字相等，直接返回0
        if (gap == 0) return 0;
        //bucket长度len=Ceiling(max-min)/gap。由gap的计算公式可知，bucket长度len=nums.length
        int[] bucketMax = new int[nums.length];
        int[] bucketMin = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            bucketMax[i] = Integer.MIN_VALUE;
            bucketMin[i] = Integer.MAX_VALUE;
        }
        //填充bucket
        int idx = 0;
        for (int n : nums) {
            idx = (n - min) / gap;
            bucketMax[idx] = Math.max(bucketMax[idx], n);
            bucketMin[idx] = Math.min(bucketMin[idx], n);
        }
        //计算max gap
        //要注意：bucket数组中存在的空白bucket，因为nums中的数字在bucket中不是均由分布的
        int ret = 0;
        int previous = min;
        for (int i = 0; i < nums.length; i++) {
            if (bucketMax[i] == Integer.MIN_VALUE && bucketMin[i] == Integer.MAX_VALUE) continue;
            ret = Math.max(ret, bucketMin[i] - previous);
            previous = bucketMax[i];
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 6, 9, 1}, 3);
        do_func(new int[]{10}, 0);
        do_func(new int[]{0, 333}, 333);
        do_func(new int[]{1, 333}, 332);
        do_func(new int[]{1, 1, 1, 1, 1, 1}, 0);

    }

    private static void do_func(int[] nums, int expected) {
        int ret = maximumGap(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
