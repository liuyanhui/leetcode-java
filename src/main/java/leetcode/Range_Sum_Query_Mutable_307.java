package leetcode;

/**
 * 307. Range Sum Query - Mutable
 * Medium
 * -------------------------
 * Given an integer array nums, handle multiple queries of the following types:
 * Update the value of an element in nums.
 * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 *
 * Implement the NumArray class:
 * NumArray(int[] nums) Initializes the object with the integer array nums.
 * void update(int index, int val) Updates the value of nums[index] to be val.
 * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 *
 * Example 1:
 * Input
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * Output
 * [null, 9, null, 8]
 *
 * Explanation
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1, 2, 5]
 * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 *
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
     * 参考思路：
     * https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
     * https://leetcode.com/problems/range-sum-query-mutable/solution/ 之 Approach3: Segment Tree
     *
     * 采用了一种新的数据结构Segment Tree.
     * Segment tree is a very flexible data structure, because it is used to solve numerous range query problems like finding minimum, maximum, sum, greatest common divisor, least common denominator in array in logarithmic time.
     *
     * 验证通过：
     * Runtime: 96 ms, faster than 31.64% of Java online submissions for Range Sum Query - Mutable.
     * Memory Usage: 70.3 MB, less than 57.81% of Java online submissions for Range Sum Query - Mutable.
     */
    class NumArray_2 {

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
            //注意：这里i>0，无需i>=0。手动画一个tree就可以发现，tree[0]是没有用的。
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
     *
     * 验证通过：
     * Runtime: 87 ms, faster than 65.98% of Java online submissions for Range Sum Query - Mutable.
     * Memory Usage: 70.6 MB, less than 47.80% of Java online submissions for Range Sum Query - Mutable.
     */
    class NumArray {

        int[] values = null;
        int[] bucket = null;
        int bucketLength = 0;

        public NumArray(int[] nums) {
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

    /**
     * Your NumArray object will be instantiated and called as such:
     * NumArray obj = new NumArray(nums);
     * obj.update(index,val);
     * int param_2 = obj.sumRange(left,right);
     */
    public static void main(String[] args) {
        System.out.println((int) Math.ceil(1.2));
        int i = 100;
        i -= 10 + 3;
        System.out.println(i);
    }
}
