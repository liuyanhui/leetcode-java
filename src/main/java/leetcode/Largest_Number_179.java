package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * tip:套路题
 * 179. Largest Number
 * Medium
 * ---------------
 * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
 * Since the result may be very large, so you need to return a string instead of an integer.
 *
 * Example 1:
 * Input: nums = [10,2]
 * Output: "210"
 *
 * Example 2:
 * Input: nums = [3,30,34,5,9]
 * Output: "9534330"
 *
 * Example 3:
 * Input: nums = [1]
 * Output: "1"
 *
 * Example 4:
 * Input: nums = [10]
 * Output: "10"
 *
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 10^9
 */
public class Largest_Number_179 {
    public static String largestNumber(int[] nums) {
        return largestNumber_3(nums);
    }

    /**
     * 思考过程：
     * 1.根据每个数的最高位数字排序问题。需要考虑[3,30],[3,34],[3,301],[3,33,334,30,301]这样的特殊用例。
     * 2.用例[3,30]，需要在较短数后追加最高位数字，再进行比较，如转化为:[33,30]
     * 3.高位数字大者，优先追加到结果集中；高位数字相同时，比较次高位；缺位时，以最高位进行比较。
     *
     * 方案：
     * 主要分为两个步骤：1.排序；2.连接组合
     * 现根据某种规则排序，再依次把数连接组合成字符串。
     * 排序规则：假设[a,b]，如果ab<ba，那么[b,a]。
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 68.67% of Java online submissions for Largest Number.
     * Memory Usage: 44.1 MB, less than 34.31% of Java online submissions for Largest Number.
     *
     * @param nums
     * @return
     */
    public static String largestNumber_3(int[] nums) {
        String[] in = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            in[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(in, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.equals(o2)) return 0;
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                for (int i = 0; i < s1.length(); i++) {
                    if (s1.charAt(i) > s2.charAt(i)) return 1;
                    else if (s1.charAt(i) < s2.charAt(i)) return -1;
                }
                return 0;
            }
        });
        //处理数组中数字全部是0的特殊情况
        if (in[in.length - 1].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = in.length - 1; i >= 0; i--) {
            sb.append(in[i]);
        }

        return sb.toString();
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/largest-number/discuss/291988/A-Proof-of-the-Concatenation-Comparator's-Transtivity
     * https://leetcode.com/problems/largest-number/solution/
     *
     * 验证通过：
     * Runtime: 4 ms, faster than 98.56% of Java online submissions for Largest Number.
     * Memory Usage: 38.3 MB, less than 66.57% of Java online submissions for Largest Number.
     *
     * @param nums
     * @return
     */
    public static String largestNumber_2(int[] nums) {
        StringBuilder ret = new StringBuilder();
        //转换成string[]
        String[] s_nums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            s_nums[i] = String.valueOf(nums[i]);
        }
        //排序
        Arrays.sort(s_nums, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s2.compareTo(s1);
            }
        });

        //特殊情况处理
        if (s_nums[0].equals("0")) {
            return "0";
        }

        //将排序后的string[]合并并输出
        for (String s : s_nums) {
            ret.append(s);
        }
        return ret.toString();
    }

    /**
     * 思路和步骤如下：
     * 原始：[3,310,34,30,390,9]
     * 补位：[333,310,344,300,390,999] #用较短的数的个位进行补位，补全到输入数组中的最大数字的长度
     * 排序：[999,390,344,333,310,300] #补位后的数字排序
     * 还原：[9,390,34,3,310,30] #把补位的数去掉
     *
     * 验证失败：
     * 无法通过用例[34323, 3432]
     *
     * @param nums
     * @return
     */
    public static String largestNumber_1(int[] nums) {
        StringBuilder ret = new StringBuilder();
        //计算最大长度
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = max > nums[i] ? max : nums[i];
        }
        int maxLength = String.valueOf(max).length();
        //补位
        Map<Integer, Integer> appendLengthMap = new HashMap<>();//记录补位的个数，
        for (int i = 0; i < nums.length; i++) {
            int gap = maxLength - String.valueOf(nums[i]).length();
            for (int j = 0; j < gap; j++) {
                nums[i] = nums[i] * 10 + nums[i] % 10;
            }
            //补位后存在nums[i]重复的情况，不用担心。map只是计数器，记录的是nums[i]的补位总数。
            if (appendLengthMap.containsKey(nums[i])) {
                appendLengthMap.put(nums[i], appendLengthMap.get(nums[i]) + gap);
            } else {
                appendLengthMap.put(nums[i], gap);
            }
        }
        //排序
        Arrays.sort(nums);
        //还原并输入出
        for (int i = nums.length - 1; i >= 0; i--) {
            int tmp = nums[i];
            while (tmp > 0 && appendLengthMap.get(nums[i]) > 0) {
                tmp = tmp / 10;
                appendLengthMap.put(nums[i], appendLengthMap.get(nums[i]) - 1);
            }
            if (tmp > 0) {
                ret.append(String.valueOf(tmp));
            }
        }

        return ret.toString();
    }

    public static void main(String[] args) {
        do_func(new int[]{10, 2}, "210");
        do_func(new int[]{9, 30, 34, 5, 3}, "9534330");
        do_func(new int[]{3, 30, 34, 5, 9}, "9534330");
        do_func(new int[]{1}, "1");
        do_func(new int[]{10}, "10");
        do_func(new int[]{34323, 3432}, "343234323");
        do_func(new int[]{0, 0}, "0");
        do_func(new int[]{30, 34, 3}, "34330");
        do_func(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}, "9876543210");
    }

    private static void do_func(int[] nums, String expected) {
        String ret = largestNumber(nums);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
