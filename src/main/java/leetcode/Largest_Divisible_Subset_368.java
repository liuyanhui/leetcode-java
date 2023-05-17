package leetcode;

import java.util.*;

/**
 * 368. Largest Divisible Subset
 * Medium
 * ---------------------------
 * Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i], answer[j]) of elements in this subset satisfies:
 * answer[i] % answer[j] == 0, or
 * answer[j] % answer[i] == 0
 * If there are multiple solutions, return any of them.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,2]
 * Explanation: [1,3] is also accepted.
 *
 * Example 2:
 * Input: nums = [1,2,4,8]
 * Output: [1,2,4,8]
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 2 * 10^9
 * All the integers in nums are unique.
 */
public class Largest_Divisible_Subset_368 {

    public static List<Integer> largestDivisibleSubset(int[] nums) {
        return largestDivisibleSubset_3(nums);
    }

    /**
     * round 2
     *
     * Thinking：
     * 1.naive solution
     * 遍历数组，两两计算每个数字。Time Complexity:O(N*N)
     * 2.先排序，再计算。因为a%b=0时，必然存在a>=b。
     * 类似naive solution。采用暴力法遍历数组，依次计算每种可能。Time Complexity:O(N*N)
     *
     * 与largestDivisibleSubset_1()方案一样
     * largestDivisibleSubset_2()是更优解
     *
     * 验证通过：
     * Runtime 30 ms Beats 14.91%
     * Memory 43.7 MB Beats 6.96%
     *
     * @param nums
     * @return
     */
    public static List<Integer> largestDivisibleSubset_3(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        Arrays.sort(nums);//排序

        //FIXME：下面这行代码是可以优化的，用其他更巧妙的思路。见largestDivisibleSubset_2()
        Map<Integer, List<Integer>> cache = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //初始化nums[i]
            cache.put(nums[i], new ArrayList<>());
            cache.get(nums[i]).add(nums[i]);
            //查找nums[i]的最优解
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    if (cache.get(nums[j]).size() + 1 > cache.get(nums[i]).size()) {
                        List<Integer> list = new ArrayList<>();
                        list.addAll(cache.get(nums[j]));
                        list.add(nums[i]);
                        cache.put(nums[i], list);
                    }
                }
            }
            if (res.size() < cache.get(nums[i]).size()) {
                res = cache.get(nums[i]);
            }
        }
        return res;
    }

    /**
     * dp思路+递推思路
     * 参考思路：
     * https://leetcode.com/problems/largest-divisible-subset/discuss/684677/3-STEPS-c%2B%2B-or-python-or-java-dp-PEN-PAPER-DIAGRAM
     *
     * 验证通过：
     * Runtime: 14 ms, faster than 98.88% of Java .
     * Memory Usage: 39.5 MB, less than 37.01% of Java .
     *
     * round 2
     * 该方法是更优解。用prev[]数组记录了最优解。
     *
     * @param nums
     * @return
     */
    public static List<Integer> largestDivisibleSubset_2(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) return ret;

        Arrays.sort(nums);

        int[] dp = new int[nums.length];//以nums[i]为结尾的最优解
        int[] prev = new int[nums.length];//在dp[i]局部最优解的条件下的上一个数字的下标，用这个思路组成最优解的链路

        int maxIdx = -1;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;//初始化
            prev[i] = -1;//[round 2]这里也比较巧妙，初始化为-1而不是0，因为它是prev，值为-1时表示没有prev
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }

            //缓存最大长度，记录全局最大值
            if (maxLength < dp[i]) {
                maxIdx = i;
                maxLength = dp[i];
            }
        }

        while (maxIdx >= 0) {
            ret.add(nums[maxIdx]);
            maxIdx = prev[maxIdx];
        }

        return ret;
    }

    /**
     *
     * 存在如下递推公式：如果 a%b==0 且 b%c==0，那么 a%c==0。即整除操作具备传递性。
     * 思路：先排序，然后利用上面的公式从小到大遍历计算。如果存在n[j]%n[i]==0那么lds[j]={j,lds[i]}，（其中i<j，lds[i]是下标i对应数字符合结果的数据集）
     * 验证通过：
     * Runtime: 26 ms, faster than 20.25% of Java.
     * Memory Usage: 39.1 MB, less than 71.37% of Java.
     *
     * @param nums
     * @return
     */
    public static List<Integer> largestDivisibleSubset_1(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) return ret;

        //排序
        Arrays.sort(nums);

        Map<Integer, List<Integer>> cache = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //初始化
            int n = nums[i];
            cache.put(n, new ArrayList<Integer>() {{
                add(n);
            }});
            //递推
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    List<Integer> t = new ArrayList<>();
                    t.addAll(cache.get(nums[j]));
                    t.add(nums[i]);
                    //选择最长的
                    if (cache.get(nums[i]).size() < t.size()) {
                        cache.put(nums[i], t);
                    }
                }
            }
        }

        for (int k : cache.keySet()) {
            if (ret.size() < cache.get(k).size()) {
                ret = cache.get(k);
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, Arrays.asList(new Integer[]{1, 2}));
        do_func(new int[]{1, 2, 4, 8}, Arrays.asList(new Integer[]{1, 2, 4, 8}));
        do_func(new int[]{1}, Arrays.asList(new Integer[]{1}));
        do_func(new int[]{4, 5, 3}, Arrays.asList(new Integer[]{3}));
        do_func(new int[]{4, 8, 10, 240}, Arrays.asList(new Integer[]{4, 8, 240}));
    }

    private static void do_func(int[] nums, List<Integer> expected) {
        List<Integer> ret = largestDivisibleSubset(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
