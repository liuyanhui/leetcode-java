package leetcode;

/**
 * 135. Candy
 * Hard
 * -------------------
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * You are giving candies to these children subjected to the following requirements:
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
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
        return candy_1(ratings);
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
    }

    private static void do_func(int[] nums, int expected) {
        int ret = candy(nums);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
