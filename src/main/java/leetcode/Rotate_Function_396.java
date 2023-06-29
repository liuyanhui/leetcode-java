package leetcode;

/**
 * 396. Rotate Function
 * Medium
 * --------------------------
 * You are given an integer array nums of length n.
 *
 * Assume arrk to be an array obtained by rotating nums by k positions clock-wise. We define the rotation function F on nums as follow:
 * F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1].
 *
 * Return the maximum value of F(0), F(1), ..., F(n-1).
 *
 * The test cases are generated so that the answer fits in a 32-bit integer.
 *
 * Example 1:
 * Input: nums = [4,3,2,6]
 * Output: 26
 * Explanation:
 * F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
 * F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
 * F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
 * F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
 * So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
 *
 * Example 2:
 * Input: nums = [100]
 * Output: 0
 *
 * Constraints:
 * n == nums.length
 * 1 <= n <= 10^5
 * -100 <= nums[i] <= 100
 */
public class Rotate_Function_396 {

    public static int maxRotateFunction(int[] nums) {
        return maxRotateFunction_4(nums);
    }

    /**
     * round 2
     *
     * 详见maxRotateFunction_3()的Thinking:2.
     *
     * 验证通过：
     * Runtime 3 ms Beats 100%
     * Memory 60.8 MB Beats 29.74%
     *
     * @param nums
     * @return
     */
    public static int maxRotateFunction_4(int[] nums) {
        int sum = 0;
        int cache = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            cache += i * nums[i];
        }
        int res = cache;
        for (int i = 1; i < nums.length; i++) {
            cache = cache - nums.length * nums[nums.length - i] + sum;
            res = Math.max(res, cache);
        }
        return res;
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.naive solution
     * 穷举计算，并比较全局最优解。
     * 公式为：
     * F(0)=0*n[0]+1*n[1]+..+(len-2)*n[len-2]	+(len-1)*n[len-1]
     * F(1)=1*n[0]+2*n[1]+..+(len-1)*n[len-2]	+0*n[len-1]
     * F(2)=2*n[0]+3*n[1]+..+		0*n[len-2]	+1*n[len-1]
     * 第0行的乘数因子为{0,1,2,..,len-1}
     * 第1行的乘数因子为{1,2,3,..,len-1,0}
     * 第i行的乘数因子为{(0+i)%len,(1+i)%len,(2+i)%len,..}
     * 2.BUD优化
     * D原则：第i-1次和第i次是有关联的，可以通过提前计算，避免不必要的计算。
     * F(i)=F(i-1)-len*nums[len-i]+sum
     * 那么在naive solution的基础上，可以时间复杂度优化为O(N)
     *
     * @param nums
     * @return
     */
    public static int maxRotateFunction_3(int[] nums) {
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int t = 0;
            for (int j = 0; j < nums.length; j++) {
                t += ((i + j) % nums.length) * nums[j];
            }
            res = Math.max(res, t);
        }
        return res;
    }

    /**
     * 套路
     *
     * 参考思路：
     * https://leetcode.com/problems/rotate-function/discuss/87853/Java-O(n)-solution-with-explanation
     * https://leetcode.com/problems/rotate-function/discuss/1243652/DP-Code-Explained-in-Detail-or-O-(N)-time-or-O-(N)-space
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 97.94% of Java online submissions for Rotate Function.
     * Memory Usage: 50.4 MB, less than 97.35% of Java online submissions for Rotate Function.
     *
     * @param nums
     * @return
     */
    public static int maxRotateFunction_2(int[] nums) {
        int sum = 0;
        int last = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            last += i * nums[i];
        }
        int max = last;
        for (int i = 1; i < nums.length; i++) {
            last = last + sum - nums.length * nums[nums.length - i];
            max = Math.max(last, max);
        }
        return max;
    }

    /**
     * 逻辑正确，但是Time Limit Exceeded
     * 验证结果：45 / 58 test cases passed.
     *
     * @param nums
     * @return
     */
    public static int maxRotateFunction_1(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int t = 0;
            for (int j = 0; j < nums.length; j++) {
                //rotate 逻辑
                int k = 0;
                if (i + j < nums.length) {
                    k = i + j;
                } else {
                    k = i + j - nums.length;
                }
                t += k * nums[j];
            }
            //按提供的公式计算
            max = Math.max(max, t);
        }

        return max;
    }

    public static void main(String[] args) {
        do_func(new int[]{4, 3, 2, 6}, 26);
        do_func(new int[]{100}, 0);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = maxRotateFunction(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
