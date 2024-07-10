package leetcode;

import java.util.Arrays;

/**
 * 135. Candy
 * Hard
 * -------------------
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * You are giving candies to these children subjected to the following requirements:
 *  - Each child must have at least one candy.
 *  - Children with a higher rating get more candies than their neighbors.
 *
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 *
 * Example 1:
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
 *
 * Example 2:
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 *
 * Constraints:
 * n == ratings.length
 * 1 <= n <= 2 * 10^4
 * 0 <= ratings[i] <= 2 * 10^4
 */
public class Candy_135 {
    //review R2
    public static int candy(int[] ratings) {
        return candy_r3_1(ratings);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * Thinking：
     * 1. naive solution
     * 1.1. 极小值给1
     * 1.2. 连续相等rating的child如何分配？首尾高中间低形状：中间为1；首尾低中间高形状：中间为1；一边高一边低阶梯形状：中间为1；直线形状：全部为1。
     * 2. 先找到极小值，并置candy为1；从极小值开始向两边扩散，如果大于上一个的rating置candy加1，如果等于上一个的rating置candy为1，如果小于上一个的rating跳过。
     * 2.1. 先找到极小值，从极小值开始进行双向递增扩散，趋势递减时退出该极小值的计算。
     *
     * candy_1()的方法更优
     *
     * 验证通过：性能一般
     * Runtime 7 ms Beats 17.73%
     * Memory 45.30 MB Beats 72.29%
     *
     * @param ratings
     * @return
     */
    public static int candy_r3_1(int[] ratings) {
        int[] candies = new int[ratings.length];
        for (int k = 0; k < ratings.length; k++) {
            candies[k] = 1;
        }
        int left = 0;
        int i = 0;
        while (i + 1 < ratings.length) {
            //查找极小值
            while (i + 1 < ratings.length && ratings[i] >= ratings[i + 1]) {
                i++;
            }
            int min_idx = i;
            //设置极小值的candy
            candies[min_idx] = 1;
            //向左扩散，并设置candy
            int cnt = 1;
            for (int j = min_idx - 1; j >= left; j--) {
                if (ratings[j] > ratings[j + 1]) {
                    cnt++;
                } else {//连续相等的ratings，candy为1
                    cnt = 1;
                }
                candies[j] = Math.max(candies[j], cnt);
            }
            //向右扩散，并设置candy，直到极大值的拐点
            cnt = 1;
            while (i + 1 < ratings.length && ratings[i] <= ratings[i + 1]) {
                if (ratings[i] < ratings[i + 1]) {
                    cnt++;
                } else {
                    cnt = 1;
                }
                candies[i + 1] = cnt;

                i++;
            }
            left = i;
        }
        return Arrays.stream(candies).sum();
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/candy/discuss/42769/A-simple-solution
     *
     * 通过分析题目可知：
     * 1.如果递增，c[i]=c[i-1]+1
     * 2.如果递减，c[i]=c[i+1]+1
     * 3.如果相等，c[i]=1
     * 4.递减可以通过倒序转化成递增
     *
     * 两次遍历，第一次从左往右，第二次从右往左。每次都计算递增部分，第二次遍历(从右往左)时需要考虑第一次遍历已经计算好的结果。
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 82.72% of Java online submissions for Candy.
     * Memory Usage: 52.2 MB, less than 29.97% of Java online submissions for Candy.
     *
     * @param ratings
     * @return
     */
    public static int candy_1(int[] ratings) {
        int[] candy = new int[ratings.length];
        for (int i = 0; i < ratings.length; i++) {
            candy[i] = 1;
        }
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i - 1] < ratings[i]) {
                candy[i] = candy[i - 1] + 1;
            }
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candy[i] = Math.max(candy[i], candy[i + 1] + 1);
            }
        }

        int ret = 0;
        for (int c : candy) {
            ret += c;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 0, 2}, 5);
        do_func(new int[]{1, 2, 2}, 4);
        do_func(new int[]{12}, 1);
        do_func(new int[]{1, 1, 1}, 3);
        do_func(new int[]{1, 3, 2, 2, 1}, 7);
        do_func(new int[]{3, 2, 1}, 6);
        System.out.println("-------Done-------");
    }

    private static void do_func(int[] nums, int expected) {
        int ret = candy(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
