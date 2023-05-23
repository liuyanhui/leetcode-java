package leetcode;

/**
 * 374. Guess Number Higher or Lower
 * Easy
 * ----------------------
 * We are playing the Guess Game. The game is as follows:
 * I pick a number from 1 to n. You have to guess which number I picked.
 * Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.
 *
 * You call a pre-defined API int guess(int num), which returns 3 possible results:
 * -1: The number I picked is lower than your guess (i.e. pick < num).
 * 1: The number I picked is higher than your guess (i.e. pick > num).
 * 0: The number I picked is equal to your guess (i.e. pick == num).
 *
 * Return the number that I picked.
 *
 * Example 1:
 * Input: n = 10, pick = 6
 * Output: 6
 *
 * Example 2:
 * Input: n = 1, pick = 1
 * Output: 1
 *
 * Example 3:
 * Input: n = 2, pick = 1
 * Output: 1
 *
 * Example 4:
 * Input: n = 2, pick = 2
 * Output: 2
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 * 1 <= pick <= n
 */
public class Guess_Number_Higher_or_Lower_374 {

    static int pick = 0;

    public static int guessNumber(int n) {
        return guessNumber_4(n);
    }

    /**
     * round 2
     *
     * Thinking:
     * 1.二分查找法。
     * 假设解空间为[i,j]，每次选择中间的数字m作为guess(m)的输入。
     * 时间复杂度：O(logN)
     *
     * 验证通过：
     * Runtime 0 ms Beats 100%
     * Memory 39.4 MB Beats 69.39%
     *
     * @param n
     * @return
     */
    public static int guessNumber_4(int n) {
        int l = 1, r = n;
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (guess(m) == -1) {
                r = m - 1;
            } else if (guess(m) == 1) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return l;
    }

    /**
     * review round 2 这是一种新思路
     *
     * 参考思路：
     * https://leetcode.com/problems/guess-number-higher-or-lower/solution/ 之Approach 2
     *
     * Time Complexity:O(log3(N))
     *
     * 验证通过
     *
     * @param n
     * @return
     */
    public static int guessNumber_3(int n) {
        int l = 1, r = n;
        while (l <= r) {
            //防止int超长溢出
            int mid1 = l + (r - l) / 3;
            int mid2 = r - (r - l) / 3;
            int g1 = guess(mid1);
            int g2 = guess(mid2);
            if (g1 == 0) {
                return mid1;
            } else if (g2 == 0) {
                return mid2;
            } else if (g1 < 0) {
                r = mid1 - 1;
            } else if (g2 > 0) {
                l = mid2 + 1;
            } else {
                l = mid1 + 1;
                r = mid2 - 1;
            }
        }
        return 0;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/guess-number-higher-or-lower/solution/ 之Approach 2
     *
     * Time Complexity:O(log2(N))
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java .
     * Memory Usage: 37.8 MB, less than 5.89% of Java .
     *
     * @param n
     * @return
     */
    public static int guessNumber_2(int n) {
        if (n < 1) return 0;
        int l = 1, r = n;
        while (l <= r) {
            //防止int过大而溢出
            int mid = l + (r + l) / 2;
            int g = guess(mid);
            if (g == -1) {
                r = mid - 1;
            } else if (g == 1) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * Time Complexity:O(log2(N))
     *
     * 验证失败：Time Limit Exceeded
     *
     * @param n
     * @return
     */
    public static int guessNumber_1(int n) {
        if (n < 1) return 0;
        int l = 1, r = n;
        while (l <= r) {
            //注意 这里可能会造成int溢出
            int mid = (l + r) / 2;
            int g = guess(mid);
            if (g == -1) {
                r = mid - 1;
            } else if (g == 1) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return 0;
    }

    /**
     * Forward declaration of guess API.
     * @param  num   your guess
     * @return         -1 if num is lower than the guess number
     *			      1 if num is higher than the guess number
     *               otherwise return 0
     * int guess(int num);
     */
    private static int guess(int num) {
        if (num > pick) return -1;
        else if (num < pick) return 1;
        else return 0;
    }

    public static void main(String[] args) {
        do_func(10, 6);
        do_func(1, 1);
        do_func(2, 1);
        do_func(2, 2);
        do_func(2126753390, 1702766719);
    }

    private static void do_func(int n, int expected) {
        pick = expected;
        int ret = guessNumber(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
