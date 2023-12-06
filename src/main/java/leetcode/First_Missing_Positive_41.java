package leetcode;

/**
 * 41. First Missing Positive
 * Hard
 * ----------------------
 * Given an unsorted integer array nums, return the smallest missing positive integer.
 * You must implement an algorithm that runs in O(n) time and uses O(1) auxiliary space.
 *
 * Example 1:
 * Input: nums = [1,2,0]
 * Output: 3
 * Explanation: The numbers in the range [1,2] are all in the array.
 *
 * Example 2:
 * Input: nums = [3,4,-1,1]
 * Output: 2
 * Explanation: 1 is in the array but 2 is missing.
 *
 * Example 3:
 * Input: nums = [7,8,9,11,12]
 * Output: 1
 * Explanation: The smallest positive integer 1 is missing.
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 */
public class First_Missing_Positive_41 {
    public static int firstMissingPositive(int[] nums) {
        return firstMissingPositive_1(nums);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * 参考：
     * https://leetcode.com/problems/first-missing-positive/solutions/17071/my-short-c-solution-o-1-space-and-o-n-time/
     *
     * Thinking：
     * 1. 先排序，再查找
     * 1.1. 查找从0开始连续出现的最大整数m，返回结果m+1
     * 时间复杂度：O(NlogN)，空间复杂度O(1)
     * 2. 两次遍历+hashtable
     * 2.1. 第一次遍历，把大于0的数加入Hashtable，并计算出大于0的最小值n
     * 2.2. 从n开始递增遍历，判断n+i是否在Hashtable中。如果不存在，返回n+i
     * 时间复杂度：O(N)，空间复杂度O(N)
     * 3. in-place排序思路
     * 3.1. 在nums内进行类似排序的操作，把对应的数据设置在对应的地方，公式为：nums[n-1]=n。即完成后如下：nums[0]=1，nums[1]=2，当nums[i]<1或nums[i]>nums.length时，覆盖或者忽略。
     * 3.2. 遍历nums，当nums[i]!=i+1时，返回i+1
     * 时间复杂度：O(N)，空间复杂度O(1)
     *
     *
     * 验证通过：
     * Runtime 1 ms Beats 99.01% of users with Java
     * Memory 59.40 MB Beats 35.12% of users with Java
     *
     * @param nums
     * @return
     */
    public static int firstMissingPositive_1(int[] nums) {
        //排序swap
        for (int i = 0; i < nums.length; i++) {
            while (1 <= nums[i] && nums[i] <= nums.length && nums[i] != i + 1) {
                //跳过特殊情况，可能引发无限循环
                if (nums[nums[i] - 1] == nums[i]) break;
                //swap(nums,nums[i]-1,i);
                int t = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = t;
            }
        }
        int res = 0;
        for (res = 0; res < nums.length; res++) {
            if (nums[res] != res + 1) break;
        }
        return res + 1;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 0}, 3);
        do_func(new int[]{3, 4, -1, 1}, 2);
        do_func(new int[]{7, 8, 9, 11, 12}, 1);
        do_func(new int[]{1}, 2);
        do_func(new int[]{4}, 1);
        do_func(new int[]{0}, 1);
        do_func(new int[]{-4, 24, 32, 25, 16, -8, 3, -5, -6, 30, 3, 3, 29, -5, 6, -3, 1, 29, -2, 4, 4, 7, 14, 20, 5, 0, 25, 2, 13, 26, -9, 7, 6, 33}, 8);
        do_func(new int[]{3, 3, 3, 3, 3, 3}, 1);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = firstMissingPositive(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
