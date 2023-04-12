package leetcode;

/**
 * 330. Patching Array
 * Hard
 * ------------------------------
 * Given a sorted integer array nums and an integer n, add/patch elements to the array such that any number in the range [1, n] inclusive can be formed by the sum of some elements in the array.
 *
 * Return the minimum number of patches required.
 *
 * Example 1:
 * Input: nums = [1,3], n = 6
 * Output: 1
 * Explanation:
 * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 * So we only need 1 patch.
 *
 * Example 2:
 * Input: nums = [1,5,10], n = 20
 * Output: 2
 * Explanation: The two patches can be [2, 4].
 *
 * Example 3:
 * Input: nums = [1,2,2], n = 5
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10^4
 * nums is sorted in ascending order.
 * 1 <= n <= 2^31 - 1
 */
public class Patching_Array_330 {
    public static int minPatches(int[] nums, int n) {
        return minPatches_1(nums, n);
    }

    /**
     * review
     *
     * 参考资料：
     * https://leetcode.com/problems/patching-array/solutions/78488/solution-explanation/
     * https://leetcode.com/problems/patching-array/solutions/78492/c-8ms-greedy-solution-with-explanation/
     *
     * Greedy法
     * 解法简单，但是思考过程复杂。下面是两个例子和对应的推导过程。
     *
     * 举例1：
     * 输入：nums = [1, 2, 4, 13, 43] and n = 100
     * 过程：
     * [1]	1+1=2	=>1+2
     * [3]	3+1=4	=>3+4
     * [7]	7+1<13	=>7+8	=>add 8
     * [15]	15+1>13	=>15+13
     * [28]	28+1<43	=>28+29	=>add 29
     * [57]	57+1>43	=>57+43
     * [101]
     *
     * 举例2：
     * 输入：nums=[1 2 5 6 20], n = 50
     * 过程：
     * [1]		1+1=2 		=>1+2
     * [3]		3+1=4<5		=>3+4	=>add 4
     * [7]		7+1>5		=>5+7
     * [12]	12+1=13>6	=>6+12
     * [18]	18+1=19<20	=>19+18	=>add 19
     * [37]	37+1=38>20	=>20+37
     * [57]
     *
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 42.5 MB Beats 26.77%
     *
     * @param nums
     * @param n
     * @return
     */
    public static int minPatches_1(int[] nums, int n) {
        int cnt = 0;
        long max = 0;
        int i = 0;
        while (max < n) {
            if (i < nums.length && max + 1 >= nums[i]) {
                max += nums[i++];
            } else {
                max += (max + 1);
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 3}, 6, 1);
        do_func(new int[]{1, 5, 10}, 20, 2);
        do_func(new int[]{1, 2, 2}, 5, 0);
        do_func(new int[]{1, 2, 2}, 5000, 10);
        do_func(new int[]{1, 2, 2}, 0, 0);
        do_func(new int[]{1, 2, 31, 33}, 2147483647, 28);
    }

    private static void do_func(int[] nums, int n, int expected) {
        int ret = minPatches(nums, n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
