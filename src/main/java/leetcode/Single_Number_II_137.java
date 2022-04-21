package leetcode;

/**
 * 137. Single Number II
 * Medium
 * ----------------------
 * Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.
 *
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 * Example 1:
 * Input: nums = [2,2,3,2]
 * Output: 3
 *
 * Example 2:
 * Input: nums = [0,1,0,1,0,1,99]
 * Output: 99
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Each element in nums appears exactly three times except for one element which appears once.
 */
public class Single_Number_II_137 {
    public static int singleNumber(int[] nums) {
        return singleNumber_1(nums);
    }


    /**
     * 另一种思路
     * 思路:https://leetcode-cn.com/problems/single-number-ii/solution/single-number-ii-mo-ni-san-jin-zhi-fa-by-jin407891/
     * 异或运算的另一个含义是“二进制下不考虑进位的加法”：0+0 = 0， 1+1=0……
     * 我们联想到，是否可以通过某种运算$，使a $ a $ a = 0，0 $ a = a，即创建“三进制下不考虑进位的加法”，这样将整个arr遍历加和，留下来的就是那个只出现一次的数字（其余各位都出现了3x次，一定为0）。
     */

    /**
     * 思路1：先排序，再搜索
     * 思路2：使用哈希表记录出现次数
     * 都不满足要求
     * 思路3：用int[32]记录数字的二进制格式1出现的次数，最后每个位置1出现的次数%3即可。
     * 无需记录所有数字出现过的次数，只需要计算二进制中对应bit等于1出现的次数即可。
     *
     * 参考Discuss区的一种方法思路,时间复杂度O(32*n),空间复杂度O(1).思路如下:
     * 大神使用了一种统计的方法，不过不是我等平常思维的统计每个数出现了几次，而是开了一个长度为32的数组，统计每个二进制位出现了几次，
     * 最后对3取模（如果是出现了K次就对K取模），取模完哪一位不是3的整倍数，就说明只出现了一次的那个数，在这个位上为1，最终可以求出最后的结果。
     *
     * 参考文档:
     * https://leetcode.com/problems/single-number-ii/discuss/43297/Java-O(n)-easy-to-understand-solution-easily-extended-to-any-times-of-occurance
     * https://leetcode.com/problems/single-number-ii/discuss/43295/Detailed-explanation-and-generalization-of-the-bitwise-operation-method-for-single-numbers
     * https://cloud.tencent.com/developer/article/1131946
     * https://blog.csdn.net/jiadebin890724/article/details/23306837
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 60.72% of Java online submissions for Single Number II.
     * Memory Usage: 43.7 MB, less than 67.12% of Java online submissions for Single Number II.
     *
     * @param nums
     * @return
     */
    public static int singleNumber_1(int[] nums) {
        int[] ones = new int[32];
        for (int i = 0; i < 32; i++) {
            for (int n : nums) {
                if (((n >> i) & 1) == 1) ones[i]++;
            }
        }
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if (ones[i] % 3 == 1) ret |= 1 << i;
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{2, 2, 3, 2}, 3);
        do_func(new int[]{0, 1, 0, 1, 0, 1, 99}, 99);
    }

    private static void do_func(int[] nums, int expected) {
        int ret = singleNumber(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
