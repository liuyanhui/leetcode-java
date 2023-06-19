package leetcode;

/**
 * 390. Elimination Game
 * Medium
 * ---------------------------
 * You have a list arr of all integers in the range [1, n] sorted in a strictly increasing order. Apply the following algorithm on arr:
 * Starting from left to right, remove the first number and every other number afterward until you reach the end of the list.
 * Repeat the previous step again, but this time from right to left, remove the rightmost number and every other number from the remaining numbers.
 * Keep repeating the steps again, alternating left to right and right to left, until a single number remains.
 *
 * Given the integer n, return the last number that remains in arr.
 *
 * Example 1:
 * Input: n = 9
 * Output: 6
 * Explanation:
 * arr = [1-, 2, 3-, 4, 5-, 6, 7-, 8, 9-]
 * arr = [2, 4-, 6, 8-]
 * arr = [2-, 6]
 * arr = [6]
 *
 * Example 2:
 * Input: n = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= n <= 10^9
 */
public class Elimination_Game_390 {
    public static int lastRemaining(int n) {
        return lastRemaining_3(n);
    }

    /**
     * review 
     * round 2
     *
     * Thinking:
     * 1.native solution
     * 直接在num[]上操作，被删除的数字置为0，最后剩下的数字就说所求。
     * 时间复杂度：O(N*N)，空间复杂度：O(N)
     * 2.利用两个stack保存数字，stack1出栈后加入stack2中（反之亦然），最终只剩一个数字。
     * 时间复杂度：O(N)，空间复杂度：O(2*N)
     * 3.https://leetcode.com/problems/elimination-game/solutions/87119/java-easiest-solution-o-logn-with-explanation/
     * lastRemaining_2()
     * 主要思路：设head是最左边的数字，每轮计算都更新head，直到只有一个数字。
     * 更新head的算法如下：
     * 1.从左向右时，更新head
     * 2.从右向左且剩余数字的数量是奇数时，更新head
     * 3.head是递增的，并且head+=step，step是计算轮次*2
     *
     */

    /**
     * 递归法，没有完全理解
     * 参考思路：
     * https://leetcode.com/problems/elimination-game/discuss/87116/Java-Recursion-and-Proof
     * https://leetcode.com/problems/elimination-game/discuss/87128/C-1-line-solution-with-explanation
     * @param n
     * @return
     */
    public static int lastRemaining_3(int n) {
        return leftToRight(n);
    }

    private static int leftToRight(int n) {
        if (n <= 2) return n;
        return 2 * rightToLeft(n / 2);
    }

    private static int rightToLeft(int n) {
        if (n <= 2) return 1;
        if (n % 2 == 1) return 2 * leftToRight(n / 2);
        return 2 * leftToRight(n / 2) - 1;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/elimination-game/discuss/87119/JAVA%3A-Easiest-solution-O(logN)-with-explanation
     *
     * 验证通过：
     * Runtime: 3 ms, faster than 56.79% of Java online submissions for Elimination Game.
     * Memory Usage: 38 MB, less than 66.20% of Java online submissions for Elimination Game.
     *
     */
    public static int lastRemaining_2(int n) {
        boolean left = true;
        int remain = n;
        int head = 1, step = 1;
        while (remain > 1) {
            if (left || remain % 2 == 1) {
                head += step;
            }
            remain /= 2;
            step *= 2;
            left = !left;
        }
        return head;
    }

    /**
     * 思路：朴素思路，每轮去掉一半的数据，共需要logN轮
     *
     * 逻辑正确，但是超时了
     * Time Complexity：O(NlogN)
     *
     * @param n
     * @return
     */
    public static int lastRemaining_1(int n) {
        if (n == 1) return 1;
        int ret = 0;
        int[] arr = new int[n / 2];
        //初始化时，就完成了第一轮的删除任务。去掉所有的奇数
        for (int i = 0; i < n / 2; i++) {
            arr[i] = 2 * i + 2;
        }
        //从第二轮删除开始，即从右向左开始
        int direct = -1;//遍历的方向
        for (int i = 0; i < Math.log(n) / Math.log(2); i++) {
            boolean skip = false;//是否跳过当前数字
            if (direct == -1) {
                int j = n / 2 - 1;
                while (j >= 0) {
                    if (arr[j] > 0) {
                        ret = arr[j];//最后一个被去掉的数字就是所求
                        if (!skip) arr[j] = 0;
                        skip = !skip;
                    }
                    j--;
                }
            } else {
                int j = 0;
                while (j < n / 2) {
                    if (arr[j] > 0) {
                        ret = arr[j];//最后一个被去掉的数字就是所求
                        if (!skip) arr[j] = 0;
                        skip = !skip;
                    }
                    j++;
                }
            }
            direct = -direct;
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(9, 6);
        do_func(1, 1);
        do_func(2, 2);
        do_func(15000, 8158);
        do_func(1000000000, 534765398);
//        System.out.println(Math.log(8518)/Math.log(2));
    }

    private static void do_func(int n, int expected) {
        int ret = lastRemaining(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
