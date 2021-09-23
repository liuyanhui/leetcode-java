package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 386. Lexicographical Numbers
 * Medium
 * --------------------------
 * Given an integer n, return all the numbers in the range [1, n] sorted in lexicographical order.
 * You must write an algorithm that runs in O(n) time and uses O(1) extra space.
 *
 * Example 1:
 * Input: n = 13
 * Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
 *
 * Example 2:
 * Input: n = 2
 * Output: [1,2]
 *
 * Constraints:
 * 1 <= n <= 5 * 10^4
 */
public class Lexicographical_Numbers_386 {
    public static List<Integer> lexicalOrder(int n) {
        return lexicalOrder_2(n);
    }

    /**
     * 套路。数学分析法。
     * 
     * The basic idea is to find the next number to add.
     * Take 45 for example: if the current number is 45, the next one will be 450 (450 == 45 * 10)(if 450 <= n), or 46 (46 == 45 + 1) (if 46 <= n) or 5 (5 == 45 / 10 + 1)(5 is less than 45 so it is for sure less than n).
     * We should also consider n = 600, and the current number = 499, the next number is 5 because there are all "9"s after "4" in "499" so we should divide 499 by 10 until the last digit is not "9".
     * It is like a tree, and we are easy to get a sibling, a left most child and the parent of any node.
     *
     * 参考资料：
     * https://leetcode.com/problems/lexicographical-numbers/discuss/86242/Java-O(n)-time-O(1)-space-iterative-solution-130ms
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 32.22% of Java online submissions for Lexicographical Numbers.
     * Memory Usage: 45 MB, less than 50.42% of Java online submissions for Lexicographical Numbers.
     *
     * @param n
     * @return
     */
    public static List<Integer> lexicalOrder_2(int n) {
        if (n <= 0) return new ArrayList<>();
        List<Integer> ret = new ArrayList<>();
        ret.add(1);
        for (int i = 2; i <= n; i++) {
            int cur = ret.get(ret.size() - 1);
            if (cur * 10 <= n) {
                ret.add(cur * 10);
            } else if (cur + 1 <= n && (cur + 1) % 10 != 0) {
                ret.add(cur + 1);
            } else {
                int t = cur / 10 + 1;
                while (t % 10 == 0) {
                    t /= 10;
                }
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * 树的遍历，最大出度为10的数，每个子节点是父节点append而来（从0~9）。求解过程就是树的先序遍历过程。
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Lexicographical Numbers.
     * Memory Usage: 44.9 MB, less than 58.44% of Java online submissions for Lexicographical Numbers.
     * @param n
     * @return
     */
    public static List<Integer> lexicalOrder_1(int n) {
        if (n <= 0) return new ArrayList<>();
        List<Integer> ret = new ArrayList<>();
        dfs(0, n, ret);
        return ret;
    }

    private static void dfs(int root, int n, List<Integer> list) {
        if (root > n) return;
        for (int i = 0; i <= 9; i++) {
            int t = root * 10 + i;
            if (t > n) break;
            if (t == 0) continue;
            list.add(t);
            dfs(t, n, list);
        }
    }

    public static void main(String[] args) {
        do_func(13, Arrays.asList(new Integer[]{1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9}));
        do_func(2, Arrays.asList(new Integer[]{1, 2}));
        do_func(1, Arrays.asList(new Integer[]{1}));
        do_func(8, Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8}));
        do_func(100, Arrays.asList(new Integer[]{1, 10, 100, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 3, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 4, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 5, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 6, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 7, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 8, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 9, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99}));
    }

    private static void do_func(int n, List<Integer> expected) {
        List<Integer> ret = lexicalOrder(n);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
