package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 229. Majority Element II
 * Medium
 * ----------------------
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 * <p>
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: [3]
 * <p>
 * Example 2:
 * Input: nums = [1]
 * Output: [1]
 * <p>
 * Example 3:
 * Input: nums = [1,2]
 * Output: [1,2]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 */
public class Majority_Element_II_229 {
    /**
     * round 3
     * Score[2] Lower is harder
     * [group] Majority_Element_169
     *
     * 未实现
     */

    /**
     * round 2
     *
     * 思考：
     * 1.先排序，再查找。时间复杂度：O(NlogN)
     * 2.使用哈希表保存每个数字出现的次数，然后输出大于n/3次数的数字。时间复杂度：O(N)，空间复杂度O(N)。
     * 3.最多有两个数字满足要求。
     * 4.不能采用超过半数Majority题目的思路。【这是错误的结论】
     * 5.从排序方面优化。对于已排序好的数组来说，结果一定出现在1/3、2/3这两个位置。
     *
     * 未实现
     */

    /**
     * review
     * <p>
     * 参考思路：
     * https://leetcode.com/problems/majority-element-ii/discuss/63520/Boyer-Moore-Majority-Vote-algorithm-and-my-elaboration
     * <p>
     * 金矿：与Majority_Element的思路时一致的，只是复杂了一些
     * <p>
     * 验证通过：
     * Runtime: 1 ms, faster than 99.76% of Java online submissions for Majority Element II.
     * Memory Usage: 42.8 MB, less than 49.39% of Java online submissions for Majority Element II.
     *
     * @param nums
     * @return
     */
    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        int n1 = 0, n2 = 0;
        int count1 = 0, count2 = 0;
        //Boyer-Moore Majority Vote algorithm
        for (int n : nums) {
            if (n1 == n) {
                count1++;
            } else if (n2 == n) {
                count2++;
            } else if (count1 == 0) {
                n1 = n;
                count1++;
            } else if (count2 == 0) {
                n2 = n;
                count2++;
            } else {
                //当n1,n2和n 三个数不同时，同时去掉这三个数。跟majority element的同时去掉两个数的思路时一致的。
                //review round3 相当于把数组分为三部分：多数1集合，多数2集合，非多数集合；然后针对[多数1集合，非多数集合/2]和[多数2集合，非多数集合/2]采用Boyer-Morre-Majority Vote algorithm
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int n : nums) {
            if (n1 == n) count1++;
            else if (n2 == n) count2++;
        }
        if (count1 > nums.length / 3) {
            ret.add(n1);
        }
        if (count2 > nums.length / 3) {
            ret.add(n2);
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 2, 3}, new Integer[]{3});
        do_func(new int[]{1}, new Integer[]{1});
        do_func(new int[]{1, 2}, new Integer[]{1, 2});
        do_func(new int[]{2, 2, 1, 1, 1, 2, 2}, new Integer[]{1, 2});
        do_func(new int[]{1, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8}, new Integer[]{1});
    }

    private static void do_func(int[] nums, Integer[] expected) {
        List<Integer> ret = majorityElement(nums);
        ret.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2)
                    return 1;
                else if (o1 == o2)
                    return 0;
                else
                    return -1;
            }
        });
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, Arrays.asList(expected)));
        System.out.println("--------------");
    }
}
