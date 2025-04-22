package leetcode;

import java.util.*;

/**
 * 321. Create Maximum Number
 * Hard
 * --------------------
 * You are given two integer arrays nums1 and nums2 of lengths m and n respectively. nums1 and nums2 represent the digits of two numbers. You are also given an integer k.
 * <p>
 * Create the maximum number of length k <= m + n from digits of the two numbers. The relative order of the digits from the same array must be preserved.
 * <p>
 * Return an array of the k digits representing the answer.
 * <p>
 * Example 1:
 * Input: nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5
 * Output: [9,8,6,5,3]
 * <p>
 * Example 2:
 * Input: nums1 = [6,7], nums2 = [6,0,4], k = 5
 * Output: [6,7,6,0,4]
 * <p>
 * Example 3:
 * Input: nums1 = [3,9], nums2 = [8,9], k = 3
 * Output: [9,8,9]
 * <p>
 * Constraints:
 * m == nums1.length
 * n == nums2.length
 * 1 <= m, n <= 500
 * 0 <= nums1[i], nums2[i] <= 9
 * 1 <= k <= m + n
 */
public class Create_Maximum_Number_321 {
    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        return maxNumber_r3_1(nums1, nums2, k);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. brute force
     * 穷举所有可能。每次随机挑选满足index条件的数字。
     * Time Complexity: O((m+n)^k)
     * 2. Greedy Algorithm
     * 设 i,j 分别代表 nums1 和 nums2 的当前下标
     * 每次都选 nums1[i:] 和 nums2[j:] 中最大的数，需要满足len(nums1[i:])+len(nums2[j:])-1 >= k-1
     * 当 nums1 和 nums2 中同时存在最大数时，如果 nums1[i-1] < nums2[j-1] 那么选择 nums[i]; 否则选择 nums2[j]
     * 由于k的限制无法选择全局最大数时，较难处理。
     * 3. 当k发生变化时，结果也会随之变化。当k从小到大或者从大到小变化时，是否能总结出k对于结果的影响。
     * 4. 参考maxNumber()
     * 先排除无需参加计算的数字，缩小范围。再查找满足条件的最大值。
     * 4.1. 先分别得出每个数组的长度为k的最大值，记为n1[]和n2[]
     * 4.2. 穷举k再n1[]和n2[]中所有可能，如：n1[]中选择0个数时，n2[]中选k个数；n1[]中选择1个数时，n2[]中选择k-1个数；n1[]中选择2个数时，n2[]中选择k-2个数；...
     * 每次n1[]中选择i个数时，都要得到其最大值，记为m1[]；同理n2[]中选择k-i个数时，也得到其最大值，记为m2[]。
     * 合并m1[]和m2[]得到i的结果。
     * 所有i中的最大值为最终返回结果。
     * 4.3. 这样问题就简化为获取单个数字数组中，不改变其顺序取k个数，使得结果最大。
     * <p>
     * review 降维：两个数组降成一个数组；穷举：穷举k的所有组合
     * <p>
     * 参考：
     * maxNumber_1() 和
     * https://leetcode.com/problems/create-maximum-number/solutions/77285/share-my-greedy-solution/
     * <p>
     * 验证通过：
     * Runtime 6 ms Beats 89.58%
     * Memory 45.04 MB Beats 50.42%
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static int[] maxNumber_r3_1(int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];

        //穷举k在两个输入数组的组合
        for (int i = 0; i <= k; i++) {
            //跳过最小长度限制
            if (i > nums1.length || k - i > nums2.length) continue;
            //获取nums1和nums2的参加计算的数，然后合并为最大值
            int[] n1 = getMaxK_r3_1(nums1, i);
            int[] n2 = getMaxK_r3_1(nums2, k - i);
            //合并，取最大值
            int[] tmpMax = mergeForMax_r3_1(n1, n2);
            //比较，更新返回值
            res = getMaxArray_r3_1(res, tmpMax);
        }

        return res;
    }

    //参考：https://leetcode.com/problems/create-maximum-number/solutions/77285/share-my-greedy-solution/
    //参考：finkMaxK()
    //获取数组的最大的k个数，不改变原有顺序
    private static int[] getMaxK_r3_1(int[] nums, int k) {
        int[] res = new int[k];
        if (k == 0) return res;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            while (j > 0 && res[j - 1] < nums[i] && nums.length - i > k - j) {
                j--;
            }
            if (j < k) {
                res[j++] = nums[i];
            }
        }
        return res;
    }

    //合并两个已排序好的数组，不改变原有顺序，返回数组使得其表示的数为最大值
    private static int[] mergeForMax_r3_1(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length + nums2.length];
        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (greater(nums1, i, nums2, j)) {
                res[i + j] = nums1[i++];
            } else {
                res[i + j] = nums2[j++];
            }
        }
        while (i < nums1.length) {
            res[i + j] = nums1[i];
            i++;
        }
        while (j < nums2.length) {
            res[i + j] = nums2[j];
            j++;
        }
        return res;
    }

    //review: round3 这里很重要。当前导数字相等时，比较后面的数字的大小；需要注意边界值情况，如：2个数组完全相同或一个数组是另一个数组的子集。
    public static boolean greater(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    //比较两个数组，返回其表示的较大值
    private static int[] getMaxArray_r3_1(int[] nums1, int[] nums2) {
        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] > nums2[i]) return nums1;
            else if (nums1[i] < nums2[i]) return nums2;
        }
        return nums1;
    }

    /**
     * review
     * round 2
     *
     * 错误的思路：
     * 1.本题是两个数组。是否可以先考虑单个数组的问题。单个数组是通过递归法可以解决。那么2个数据组是否可以使用递归法？
     * 2.递归思路。
     * 3.2个数组先排序，排序的结果需记录数字在排序前的位置。
     * 4.依次从两个排序后数组中的获取最大数字，并进行判断（剩余数字是否满足存在解）。如果满足条件则把当前数据记入结果集，并取出该数字。
     * 5.递归执行步骤4
     *
     * review TIP 这种题目很少有直接计算出解的。大多数情况都是先缩小解的搜索空间（计算出满足一定条件的搜索空间），然后再在解空间中查找满足条件的结果。
     *
     */

    /**
     * 分治思路。把两个数组分解为每个数组单独计算，然后合并。
     * 1.问题可以分解为单个数组nums中k个数字组成的最大值。
     * 2.nums1如果出i个数字，那么nums2需要出k-i个数字。那么nums1的i个数字和nums2的n-i个数字合并后就是解空间中的一个。
     * 3.把步骤2中的数字组成了解空间，共可以得到k+1个数字(0:k,1:k-1,2:k-2,...,k:0)。
     * 4.比较这k+1个数字，可以得到最大值。
     * <p>
     * compare部分需要关注一下。
     * <p>
     * 验证通过：
     * Runtime: 38 ms, faster than 19.29% .
     * Memory Usage: 39.9 MB, less than 19.29%
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static int[] maxNumber_1(int[] nums1, int[] nums2, int k) {
        List<int[]> candidats = new ArrayList<>();

        //计算解空间
        for (int i = 0; i <= nums1.length; i++) {
            if (k - i <= nums2.length) {
                int[] c1 = finkMaxK(nums1, i);
                int[] c2 = finkMaxK(nums2, k - i);
                candidats.add(merge(c1, c2));
                //FIXME round 2 可以在加入candidats的时候就比较大小。
            }
        }

        //FIXME round 2 : 下面的代码可以放在上面的循环中。并且可以复用compare_3()或compare_2()
        //查找解空间，找出candidats中的最大值
        int[] ret = candidats.get(0);
        for (int i = 1; i < candidats.size(); i++) {
            for (int j = 0; j < k; j++) {
                if (candidats.get(i)[j] != ret[j]) {
                    if (candidats.get(i)[j] > ret[j]) {
                        ret = candidats.get(i);
                    }
                    break;
                }
            }
        }

        return ret;
    }

    //合并两个sorted数组的基础上，找出最大值
    private static int[] merge(int[] n1, int[] n2) {
        if (n1 == null || n2 == null) {
            return n1 == null ? n2 : n1;
        }
        int[] ret = new int[n1.length + n2.length];
        int i = 0, j = 0;
        while (i < n1.length && j < n2.length) {
            if (compare_3(n1, i, n2, j)) {
                ret[i + j] = n1[i];
                i++;
            } else {
                ret[i + j] = n2[j];
                j++;
            }
        }
        while (i < n1.length) {
            ret[i + j] = n1[i];
            i++;
        }
        while (j < n2.length) {
            ret[i + j] = n2[j];
            j++;
        }
        return ret;
    }

    /**
     * 比较两个数组，
     * 这个方法有问题，无法通过全部用例
     *
     * @param n1
     * @param idx1
     * @param n2
     * @param idx2
     * @return
     */
    private static boolean compare(int[] n1, int idx1, int[] n2, int idx2) {
        int x = idx1, y = idx2;
        while (x < n1.length && y < n2.length) {
            if (n1[x] == n2[y]) {
                x++;
                y++;
            } else {
                return n1[x] > n2[y];
            }
        }
        //处理[2, 3, 4],[2, 3, 4, 1]和[2, 3, 4],[2, 3, 4, 5]这样的用例
        //前导数字相等时，
        if (y < n2.length) {
            return n2[y] < n2[idx2];
        } else if (x < n1.length) {
            return n1[x] > n1[idx1];
        }
        return false;
    }

    /**
     * 参考文档：
     * https://leetcode.com/problems/create-maximum-number/discuss/77285/Share-my-greedy-solution
     * <p>
     * round2 :这里compare的目的是提取数字，并不是比较完整的数。与一般意义上的两个数的比较不同
     * 前序数字完全相同时，取最长的那个数组的第一个元素。
     */
    private static boolean compare_2(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        //round2 :前序数字完全相同时，取最长的那个数组的第一个元素。
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    /**
     * 需要注意compare的逻辑，前导数字相等时，较长的数组胜出
     * round2 ：这里compare的目的是提取数字，并不是比较完整的数。与一般意义上的两个数的比较不同。
     * 前序数字完全相同时，取最长的那个数组的第一个元素。
     */
    private static boolean compare_3(int[] n1, int idx1, int[] n2, int idx2) {
        while (idx1 < n1.length && idx2 < n2.length) {
            if (n1[idx1] == n2[idx2]) {
                idx1++;
                idx2++;
            } else {
                return n1[idx1] > n2[idx2];
            }
        }
        //超出后根据长度判断。
        // round2 :前序数字完全相同时，取最长的那个数组的第一个元素。
        return n1.length - idx1 > n2.length - idx2;
    }

    //找到数组中的，长度为k的最大值，不改变数字在数组中的顺序
    private static int[] finkMaxK(int[] nums, int k) {
        if (k <= 0 || nums.length < k) return null;
        int[] ret = new int[k];
        //利用单调栈处理,栈中的数字是根据nums中的顺序单调递增的。
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            //保持单调栈的数据完整性
            while (!stack.empty() && stack.peek() < nums[i] && k - stack.size() + 1 <= nums.length - i) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(nums[i]);
            }
        }

        while (!stack.empty()) {
            ret[--k] = stack.pop();
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{3, 4, 6, 5}, new int[]{9, 1, 2, 5, 8, 3}, 5, new int[]{9, 8, 6, 5, 3});
        do_func(new int[]{6, 7}, new int[]{6, 0, 4}, 5, new int[]{6, 7, 6, 0, 4});
        do_func(new int[]{3, 9}, new int[]{8, 9}, 3, new int[]{9, 8, 9});
        do_func(new int[]{3, 3}, new int[]{3, 3}, 3, new int[]{3, 3, 3});
        do_func(new int[]{3, 3}, new int[]{3, 3, 3}, 5, new int[]{3, 3, 3, 3, 3});
        do_func(new int[]{2, 3, 4}, new int[]{2, 3, 4, 1}, 7, new int[]{2, 3, 4, 2, 3, 4, 1});
        do_func(new int[]{2, 3, 4}, new int[]{2, 3, 4, 5}, 7, new int[]{2, 3, 4, 5, 2, 3, 4});
        do_func(new int[]{2, 5, 6, 4, 4, 0}, new int[]{7, 3, 8, 0, 6, 5, 7, 6, 2}, 15, new int[]{7, 3, 8, 2, 5, 6, 4, 4, 0, 6, 5, 7, 6, 2, 0});
        //TODO : 下面是compare()方法无法通过的用例
        do_func(new int[]{2, 1, 2, 0, 2, 1, 1, 2, 0, 1, 0, 0, 1, 1, 1, 0, 1, 2, 0, 0, 1, 2, 2, 1, 2, 2, 2, 0, 1, 1, 0, 0, 0, 2, 0, 0, 1, 0, 0, 2, 2, 1, 1, 1, 1, 2, 0, 2, 0, 2, 2, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 2, 1, 2, 2, 0, 2, 0, 2, 2, 2, 2, 0, 0, 2, 1, 2, 0, 0, 1, 1, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 2, 1, 0, 0, 0, 1, 0, 2, 0, 1, 1, 1, 2, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 2, 2, 1, 0, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2, 1, 2, 1, 0, 1, 1, 2, 1, 1, 1, 0, 2, 1, 0, 0, 0, 2, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 0, 0, 1, 1, 0, 2, 2, 2, 1, 2, 2, 0, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 1, 0, 1, 0, 1}, new int[]{1, 2, 1, 2, 2, 0, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 0, 0, 0, 2, 2, 0, 2, 0, 0, 1, 0, 1, 1, 1, 0, 2, 2, 2, 0, 1, 1, 1, 0, 2, 2, 1, 2, 0, 0, 2, 0, 1, 1, 0, 1, 0, 0, 0, 2, 0, 1, 0, 1, 2, 1, 1, 0, 2, 2, 0, 2, 0, 0, 0, 1, 0, 2, 2, 0, 2, 0, 0, 2, 1, 0, 2, 1, 2, 2, 1, 2, 0, 1, 1, 0, 2, 0, 0, 1, 1, 2, 0, 2, 1, 0, 2, 1, 0, 0, 0, 1, 1, 1, 2, 2, 1, 1, 0, 1, 1, 2, 1, 0, 2, 0, 1, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0, 0, 0, 0, 2, 1, 1, 1, 0, 2, 2, 0, 1, 2, 2, 2, 1, 2, 1, 0, 2, 2, 0, 1, 0, 2, 1, 2, 2, 1, 0, 1, 1, 0, 2, 0, 1, 1, 2, 0, 0, 0, 2, 0, 1, 0, 1, 1, 2, 0, 1, 2, 1, 2, 0}, 400, new int[]{2, 1, 2, 1, 2, 2, 1, 2, 0, 2, 1, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 0, 1, 0, 0, 1, 1, 1, 0, 1, 2, 0, 0, 1, 2, 2, 1, 2, 2, 2, 0, 1, 1, 0, 0, 0, 2, 2, 0, 2, 0, 0, 1, 0, 1, 1, 1, 0, 2, 2, 2, 0, 1, 1, 1, 0, 2, 2, 1, 2, 0, 0, 2, 0, 1, 1, 0, 1, 0, 0, 0, 2, 0, 1, 0, 1, 2, 1, 1, 0, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0, 1, 0, 0, 2, 2, 1, 1, 1, 1, 2, 0, 2, 0, 2, 2, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 2, 2, 0, 2, 0, 0, 2, 1, 0, 2, 1, 2, 2, 1, 2, 0, 1, 1, 0, 2, 0, 0, 1, 1, 2, 0, 2, 1, 0, 2, 1, 0, 0, 0, 1, 1, 1, 2, 2, 1, 1, 0, 1, 1, 2, 1, 0, 2, 0, 1, 1, 2, 0, 1, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0, 0, 0, 0, 2, 1, 1, 1, 0, 2, 2, 0, 1, 2, 2, 2, 1, 2, 1, 0, 2, 2, 0, 1, 0, 2, 1, 2, 2, 1, 0, 1, 1, 0, 2, 0, 1, 1, 2, 0, 0, 0, 2, 0, 1, 0, 1, 1, 2, 0, 1, 2, 1, 2, 0, 0, 0, 0, 1, 0, 2, 1, 2, 2, 0, 2, 0, 2, 2, 2, 2, 0, 0, 2, 1, 2, 0, 0, 1, 1, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 2, 1, 0, 0, 0, 1, 0, 2, 0, 1, 1, 1, 2, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 2, 2, 1, 0, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2, 1, 2, 1, 0, 1, 1, 2, 1, 1, 1, 0, 2, 1, 0, 0, 0, 2, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 0, 0, 1, 1, 0, 2, 2, 2, 1, 2, 2, 0, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 1, 0, 1, 0, 1, 0});

        do_func(new int[]{7, 7, 7}, new int[]{9, 9, 9, 9, 9, 9}, 6, new int[]{9, 9, 9, 9, 9, 9});

        System.out.println(compare_3(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 1}, 0));
        System.out.println(compare_3(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 5}, 0));

        System.out.println(compare_2(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 1}, 0));
        System.out.println(compare_2(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 5}, 0));

        System.out.println(compare(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 1}, 0));
        System.out.println(compare(new int[]{2, 3, 4}, 0, new int[]{2, 3, 4, 5}, 0));
    }

    private static void do_func(int[] nums1, int[] nums2, int k, int[] expected) {
        int[] ret = maxNumber(nums1, nums2, k);
        ArrayUtils.printlnIntArray(ret);
        System.out.println(Arrays.equals(ret, expected));
        System.out.println("--------------");
    }
}
