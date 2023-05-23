package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 373. Find K Pairs with Smallest Sums
 * Medium
 * -------------------
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 * Define a pair (u, v) which consists of one element from the first array and one element from the second array.
 * Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.
 *
 * Example 1:
 * Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
 * Output: [[1,2],[1,4],[1,6]]
 * Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 *
 * Example 2:
 * Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
 * Output: [[1,1],[1,1]]
 * Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 *
 * Example 3:
 * Input: nums1 = [1,2], nums2 = [3], k = 3
 * Output: [[1,3],[2,3]]
 * Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]
 *
 * Constraints:
 * 1 <= nums1.length, nums2.length <= 10^5
 * -10^9 <= nums1[i], nums2[i] <= 10^9
 * nums1 and nums2 both are sorted in ascending order.
 * 1 <= k <= 10000
 */
public class Find_K_Pairs_with_Smallest_Sums_373 {
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        return kSmallestPairs_2(nums1, nums2, k);
    }

    /**
     * review
     * round 2
     * 参考：
     * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/solutions/84551/simple-java-o-klogk-solution-with-explanation/
     * kSmallestPairs_2()
     *
     * 思路总结：
     * 1.问题可以转化为m个排序数组的合并排序，然后在排序结果中取k个最小值的问题。我们把它看成二维数组对待。
     * 2.假设有两个已排序数组n1和n2，长度分别为len1和len2。
     * 3.转化后的二维数组为s[len1][len2]，其中s[i][j]=n1[i]+n2[j]。
     * 4.问题转化为在行和列分别升序排序的二维数组中获取k个最小值问题。
     * 5.基于"行和列分别升序排序"存在以下约束：当s[i][j]为第m个最小值时，第m+1个最小值为s[i][j+1]或s[i+1][last_seleted_j]，其中last_seleted_j要么是0，要么是上一次已经被选中的第i+1行的j+1。
     *
     * 算法梗概：
     * 1.用小顶堆保存k个最小值数字组，然后从小顶堆中选择最小值出队，加入结果集。
     * 2.新的数字加入heap时，因为下一行对应的数字组已经在heap里了，所以只需要新增当前行右侧的数字组，即{nums1[i],nums2[i+1]}，详见代码中的"精华3："
     *
     * review 扩展：m个已排序数组合并排序的问题。
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static List<List<Integer>> kSmallestPairs_3(int[] nums1, int[] nums2, int k) {
        return null;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/discuss/84551/simple-Java-O(KlogK)-solution-with-explanation
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 75.60% of Java online submissions for Find K Pairs with Smallest Sums.
     * Memory Usage: 50.2 MB, less than 41.30% of Java online submissions for Find K Pairs with Smallest Sums.
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static List<List<Integer>> kSmallestPairs_2(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return ret;
        }
        //使用PriorityQueue存储k个最小值，并且是排序好的。从小到大排序
        PriorityQueue<Integer[]> queue = new PriorityQueue<>((v1, v2) -> v1[0] + v1[1] - v2[0] - v2[1]);
        //i>k时，nums1[i]已经没必要加入queue了，必然不符合条件了
        for (int i = 0; i < Math.min(nums1.length, k); i++) {
            // 精华1：第三个元素是nums2中的数字的下标
            // offer initial pairs {num1, num2, index_of_num2}
            queue.offer(new Integer[]{nums1[i], nums2[0], 0});
        }

        // get 1st k elem into result, each time, offer potential better pairs into queue
        // if there r not enough pair, just return all pairs
        while (k-- > 0 && !queue.isEmpty()) {
            // 精华2：
            // get the best pair and put into res
            Integer[] t = queue.poll();
            ret.add(new ArrayList<Integer>() {{
                add(t[0]);
                add(t[1]);
            }});

            // 精华3：
            // next better pair could with be A: {after(num1), num2} or B: {num1. after(num2)}
            // for A, we've already added top possible k into queue, so A is either in the queue already, or not qualified
            // for B, it might be a better choice, so we offer it into queue
            // round 2 : 在二维数组中向右向下查找最优解的过程中，只需要考虑向右一个维度。因为向下的查找部分已经做过了。这也算是降维的一种？
            int idx = t[2];
            if (idx + 1 < nums2.length) {// still at least one elem after num2 in array nums2
                queue.offer(new Integer[]{t[0], nums2[idx + 1], idx + 1});
            }
        }

        return ret;
    }

    /**
     * brute force思路
     * 验证失败，Time Limit Exceeded
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static List<List<Integer>> kSmallestPairs_1(int[] nums1, int[] nums2, int k) {
        //使用PriorityQueue存储k个最小值，并且是排序好的.从大到小排序
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(k, (v1, v2) -> v2[0] + v2[1] - v1[0] - v1[1]);

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                queue.add(new Integer[]{nums1[i], nums2[j]});
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }

        List<List<Integer>> ret = new ArrayList<>();
        while (!queue.isEmpty()) {
            ret.add(0, Arrays.asList(queue.poll()));
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3, new int[][]{{1, 2}, {1, 4}, {1, 6}});
        do_func(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2, new int[][]{{1, 1}, {1, 1}});
        do_func(new int[]{1, 2}, new int[]{3}, 3, new int[][]{{1, 3}, {2, 3}});
        do_func(new int[]{}, new int[]{}, 3, new int[][]{});
    }

    private static void do_func(int[] nums1, int[] nums2, int k, int[][] expected) {
        List<List<Integer>> ret = kSmallestPairs(nums1, nums2, k);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }

}
