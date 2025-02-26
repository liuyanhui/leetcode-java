package leetcode;

/**
 * 307. Range Sum Query - Mutable
 * Medium
 * -------------------------
 * Given an integer array nums, handle multiple queries of the following types:
 * 1.Update the value of an element in nums.
 * 2.Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 * <p>
 * Implement the NumArray class:
 * - NumArray(int[] nums) Initializes the object with the integer array nums.
 * - void update(int index, int val) Updates the value of nums[index] to be val.
 * - int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 * <p>
 * Example 1:
 * Input
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * Output
 * [null, 9, null, 8]
 * <p>
 * Explanation
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1, 2, 5]
 * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 * <p>
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * At most 3 * 10^4 calls will be made to update and sumRange.
 */
public class Range_Sum_Query_Mutable_307 {

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * review NumArray_1() 和 NumArray_2() 两种算法都很巧妙。
     * NumArray_2()中，奇数数组输入时，虽然构造的tree有点奇怪但是不影响使用。即使偶数数组输入时，在构造tree时也会有奇数节点的层。
     * NumArray_r3_2 是 NumArray_2的思路实现
     */
    static class NumArray_r3_2 implements NumArray {
        int[] tree;
        int n;

        public NumArray_r3_2(int[] nums) {
            n = nums.length;
            tree = new int[2 * n];
            // build the tree
            for (int i = 0; i < n; i++) {
                tree[i + n] = nums[i];
            }
            for (int i = n - 1; i > 0; i--) {
                tree[i] = tree[2 * i] + tree[2 * i + 1];
            }
        }

        public void update(int index, int val) {
            int i = index + n;
            tree[i] = val;
            i /= 2;
            while (i > 0) {
                tree[i] = tree[i * 2] + tree[i * 2 + 1];
                i /= 2;
            }
        }
        public int sumRange(int left, int right) {
            int res = 0;
            left += n;
            right += n;
            while (left <= right) {
                if (left % 2 == 1) {
                    res += tree[left++];
                }
                if (right % 2 == 0) {
                    res += tree[right--];
                }
                left /= 2;
                right /= 2;
            }
            return res;
        }
    }

    /**
     * 参考思路：
     * https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
     * https://leetcode.com/problems/range-sum-query-mutable/solution/ 之 Approach3: Segment Tree
     * <p>
     * 采用了一种新的数据结构Segment Tree.
     * Segment tree is a very flexible data structure, because it is used to solve numerous range query problems like finding minimum, maximum, sum, greatest common divisor, least common denominator in array in logarithmic time.
     * <p>
     * 验证通过：
     * Runtime: 96 ms, faster than 31.64% of Java online submissions for Range Sum Query - Mutable.
     * Memory Usage: 70.3 MB, less than 57.81% of Java online submissions for Range Sum Query - Mutable.
     * <p>
     * round 2:review
     */
    static class NumArray_2 implements NumArray {

        int[] tree = null;
        int n = 0;

        public NumArray_2(int[] nums) {
            n = nums.length;
            tree = new int[2 * n];
            buildTree(nums);
        }

        private void buildTree(int[] nums) {
            for (int i = 0, j = n; i < n; i++, j++) {
                tree[j] = nums[i];
            }
            //review ：这里i>0，无需i>=0。手动画一个tree就可以发现，tree[0]是没有用的。
            for (int i = n - 1; i > 0; i--) {
                //注意：不是满二叉树
                tree[i] = tree[2 * i] + tree[2 * i + 1];
            }
        }

        public void update(int index, int val) {
            index += n;
            tree[index] = val;
            while (index > 0) {
                if (index % 2 == 1) {
                    index = (index - 1) / 2;
                } else {
                    index /= 2;
                }
                tree[index] = tree[2 * index] + tree[2 * index + 1];
            }
        }

        public int sumRange(int left, int right) {
            left += n;
            right += n;
            int sum = 0;
            while (left <= right) {
                //left是奇数，先累加tree[left]，然后left--
                if (left % 2 == 1) {
                    sum += tree[left++];
                }
                //right是偶数，先累加tree[right]，然后right++
                if (right % 2 == 0) {
                    sum += tree[right--];
                }
                left /= 2;
                right /= 2;
            }
            return sum;
        }
    }

    /**
     * 金矿：range操作一般都可以用Bucket思路解决
     * 参考思路：
     * https://leetcode.com/problems/range-sum-query-mutable/solution/ 之 Approach2: Sqrt Decomposition
     * 利用Bucket的思路，降低算法复杂度
     * <p>
     * 验证通过：
     * Runtime: 87 ms, faster than 65.98% of Java online submissions for Range Sum Query - Mutable.
     * Memory Usage: 70.6 MB, less than 47.80% of Java online submissions for Range Sum Query - Mutable.
     * <p>
     * round 2:review
     * 验证通过：
     * Runtime 127 ms Beats 75.56%
     * Memory 71.9 MB Beats 91.19%
     */
    static class NumArray_1 implements NumArray {

        int[] values = null;
        int[] bucket = null;
        int bucketLength = 0;

        public NumArray_1(int[] nums) {
            values = nums;
            bucketLength = (int) Math.ceil(Math.sqrt(nums.length));
            bucket = new int[bucketLength];
            for (int i = 0; i < nums.length; i++) {
                bucket[i / bucketLength] += nums[i];
            }
        }

        public void update(int index, int val) {
            //TIP：注意下面的代码与"bucket[index / bucketLength] -=  values[index] + val"的执行结果不同
            bucket[index / bucketLength] = bucket[index / bucketLength] - values[index] + val;
            values[index] = val;
        }

        public int sumRange(int left, int right) {
            int sum = 0;
            int startBlock = left / bucketLength;
            int endBlock = right / bucketLength;
            if (startBlock == endBlock) {
                for (int i = left; i <= right; i++) {
                    sum += values[i];
                }
            } else {
                for (int i = left; i < (startBlock + 1) * bucketLength; i++) {
                    sum += values[i];
                }
                for (int i = startBlock + 1; i < endBlock; i++) {
                    sum += bucket[i];
                }
                for (int i = endBlock * bucketLength; i <= right; i++) {
                    sum += values[i];
                }
            }
            return sum;
        }
    }

    interface NumArray {

        void update(int index, int val);

        int sumRange(int left, int right);
    }

    /**
     * Your NumArray object will be instantiated and called as such:
     * NumArray obj = new NumArray(nums);
     * obj.update(index,val);
     * int param_2 = obj.sumRange(left,right);
     */
    public static void main(String[] args) {
        NumArray numArray1 = new NumArray_r3_2(new int[]{1, 3, 5});
        do_func_1(numArray1);

        NumArray numArray2 = new NumArray_r3_2(new int[]{-1});
        do_func_2(numArray2);
    }

    private static void do_func_1(NumArray numArray) {
        int r1, r2;
        //[[1, 3, 5]], [0, 2], [1, 2], [0, 2]
//        numArray = new NumArray_1(new int[]{1, 3, 5});
        r1 = numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
        System.out.print(r1);
        System.out.println(r1 == 9 ? "√" : "X");
        numArray.update(1, 2);   // nums = [1, 2, 5]
        r2 = numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
        System.out.print(r2);
        System.out.println(r2 == 8 ? "√" : "X");
        System.out.println("---------------------");
    }

    private static void do_func_2(NumArray numArray) {
        //[[-1]],[0,0],[0,1],[0,0]
        int r1 = numArray.sumRange(0, 0);
        System.out.print(r1);
        System.out.println(r1 == -1 ? "√" : "X");
        numArray.update(0, 1);
        int r2 = numArray.sumRange(0, 0);
        System.out.print(r2);
        System.out.println(r2 == 1 ? "√" : "X");
        System.out.println("---------------------");
    }
}
