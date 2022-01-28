package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里有金矿
 * https://leetcode.com/problems/subsets/
 * 78. Subsets
 * Medium
 * -----------------------
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [[],[0]]
 *
 * Constraints:
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * All the numbers of nums are unique.
 */
public class Subsets_78 {

    public static List<List<Integer>> subsets(int[] nums) {
        return subsets_5(nums);
    }

    /**
     * round 2
     *
     * 思路：每个元素一次加入集合，即先计算第一个元素，然后每轮都加入一个元素。
     *
     * 公式如下：x 表示笛卡尔积
     * f([])={[]}
     * f([1])=f([])x{[],[1]}={[]}x{[],[1]}={[],[1]}
     * f([2])=f([1])x{[],[2]}={[],[1]}x{[],[2]}={[],[1],[2],[1,2]}
     * f([n])=f([n-1])x{[],[n]}
     *
     * 验证通过：
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Subsets.
     * Memory Usage: 42.6 MB, less than 7.09% of Java online submissions for Subsets.
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets_5(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            //深度cloneList，注意不能用tmp = new ArrayList<>(ret)，因为子List是浅复制。
            List<List<Integer>> tmp = new ArrayList<>();
            for (List<Integer> t : ret) {
                tmp.add(new ArrayList<>(t));
            }
            for (List<Integer> t : tmp) {
                t.add(nums[i]);
            }
            ret.addAll(tmp);
        }

        return ret;
    }

    /**
     * Bit manipulation思路。
     * 使用bit map。
     * 1.位图的长度就是数组的长度，位图中每个bit表示数组中的数字，1表示出现，0表示不出现。、
     * 2.假设nums=[1,2,3,4],nums.length=4，那么位图的范围是0~1111，转化为十进制为0~15
     * 3.遍历0~15，并根据i中的二进制中1出现的位置，即可确定每一个subset
     *
     * 参考资料：
     * https://leetcode.com/problems/subsets/solution/
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 64.88% of Java online submissions for Subsets.
     * Memory Usage: 39.4 MB, less than 50.36% of Java online submissions for Subsets.
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets_4(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        int max = (int) Math.pow(2, nums.length) - 1;
        for (int i = 0; i <= max; i++) {
            List<Integer> tmp = new ArrayList<>();
            String binaryStr = Integer.toBinaryString(i);
            for (int j = 0; j < binaryStr.length(); j++) {
                // 这里有金矿
                // 其中"binaryStr.length() - 1 - j"是关键
                // String类中value存储方式跟预想的不同，如："123"，在String内部变量value中存储为[3,2,1]，而不是[1,2,3]
                // "123".charAt(0)==1而不是3，因为它要跟substring()方法保持一致，如：'123'.substring(0,1)==1
                // 即"123".charAt(0)==1，'123'.substring(0,1)==1，两个函数是一致的。
                if (binaryStr.charAt(j) == '1') tmp.add(nums[binaryStr.length() - 1 - j]);
            }
            ret.add(tmp);
        }
        return ret;
    }

    /**
     * subsets_2的简化版，使用jdk自带的方案实现list深拷贝;使用匿名内部类在List初始化时实现add()操作
     * 参考思路：https://leetcode.com/problems/subsets/solution/ 中的Approach 1
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets_3(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            List<List<Integer>> cache = new ArrayList<>();
            for (int j = 0; j < ret.size(); j++) {
                // 匿名内部类，双大括号初始化（double brace initialization）或者匿名内部类初始化法。
                // 参考资料：https://www.cnblogs.com/dengyungao/p/7524981.html
                List<Integer> tmp = new ArrayList<Integer>(ret.get(j)) {{
                    add(num);
                }};
                cache.add(tmp);
            }
            ret.addAll(cache);
        }
        return ret;
    }

    /**
     * do_recursive_1的优化版，从后向前计算，避免重复计算。循环遍历即可，无需递归。
     * 公式如下：
     * f(n) = {[],[n]}
     * f(n-1)= [n-1] × f(n) + f(n) = [n-1] × {[],[n]} + {[],[n]} = {[n-1],[n-1,n]} + {[],[n]} = {[n-1],[n-1,n],[],[n]}
     * f(n-2)= [n-2] × f(n-1) + f(n-1) = ...
     * 其中，×表示笛卡尔积,+表示串联连接
     *
     * 补充：其实从后向前和从前向后是一样的。
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 15.73% of Java online submissions for Subsets.
     * Memory Usage: 40.4 MB, less than 6.01% of Java online submissions for Subsets.
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets_2(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        for (int i = nums.length - 1; i >= 0; i--) {
            int num = nums[i];
            List<List<Integer>> cache = new ArrayList<>();
            for (int j = 0; j < ret.size(); j++) {
                List<Integer> tmp = new ArrayList<>();
                //clone ret
                for (int k = 0; k < ret.get(j).size(); k++) {
                    tmp.add(ret.get(j).get(k).intValue());
                }
                //join nums[i] and tmp
                tmp.add(nums[i]);
                cache.add(tmp);
            }
            ret.addAll(cache);
        }
        return ret;
    }


    public static List<List<Integer>> subsets_1(int[] nums) {
        return do_recursive_3(nums, 0);
    }

    /**
     * do_recursive_2的简化版
     * 验证通过，性能较差：
     * Runtime: 2 ms, faster than 16.24% of Java online submissions for Subsets.
     * Memory Usage: 40.3 MB, less than 6.23% of Java online submissions for Subsets.
     * @param input
     * @param index
     * @return
     */
    private static List<List<Integer>> do_recursive_3(int[] input, int index) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        //从index开始，表示[0:index)的数字已经在本次递归之前包含了。这样可以避免对返回结果去重。
        for (int i = index; i < input.length; i++) {
            List<List<Integer>> recursiveRet = do_recursive_3(input, i + 1);
            //合并input[i]和tmpRetList
            for (List<Integer> recurItem : recursiveRet) {
                recurItem.add(input[i]);
            }
            ret.addAll(recursiveRet);
        }
        return ret;
    }

    /**
     * do_recursive_1的简化版
     * 验证通过，性能较差：
     * Runtime: 4 ms, faster than 6.01% of Java online submissions for Subsets.
     * Memory Usage: 40.6 MB, less than 5.90% of Java online submissions for Subsets.
     * @param input
     * @param index
     * @return
     */
    private static List<List<Integer>> do_recursive_2(int[] input, int index) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        //从index开始，表示[0:index)的数字已经在本次递归之前包含了。这样可以避免对返回结果去重。
        for (int i = index; i < input.length; i++) {
            List<List<Integer>> recursiveRet = do_recursive_2(input, i + 1);
            //合并input[i]和tmpRetList
            List<List<Integer>> joinedList = new ArrayList<>();
            for (List<Integer> recurItem : recursiveRet) {
                recurItem.add(input[i]);
                joinedList.add(recurItem);
            }
            ret.addAll(joinedList);
        }
        return ret;
    }

    /**
     * 递归思路
     * 1.公式如下：f(1..n)=[1] × f(2..n) + f(2..n) + f(3..n) + .. + f(n)
     * 2.         f(1..n)=[1] × f(2..n) + [2] × f(3..n) + .. + f(n)。其中f(n)=[n],×表示笛卡尔积,+表示串联连接
     *            f(i..n) = [i] * f(i+1..n) + [i+1] * f(i+2..n) +..+ f(n)
     *            f(n) = [n]
     *
     * 这个思路有重复计算的问题，即：从前向后遍历时 f(i..n)或被重复计算
     *
     * 验证通过，解决过程耗时较长，性能一般：
     * Runtime: 3 ms, faster than 5.57% of Java online submissions for Subsets.
     * Memory Usage: 40.5 MB, less than 5.10% of Java online submissions for Subsets.
     * @param input
     * @param index
     * @return
     */
    private static List<List<Integer>> do_recursive_1(int[] input, int index) {
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        //从index开始，表示[0:index)的数字已经在本次递归之前包含了。这样可以避免对返回结果去重。
        for (int i = index; i < input.length; i++) {
            if (input[i] == 999) break;
            int tmp = input[i];
            input[i] = 999;
            List<List<Integer>> recursiveRet = do_recursive_1(input, index + 1);
            input[i] = tmp;
            //合并input[i]和tmpRetList
            List<List<Integer>> joinedList = new ArrayList<>();
            for (List<Integer> recurItem : recursiveRet) {
                recurItem.add(tmp);
                joinedList.add(recurItem);
            }
            ret.addAll(joinedList);
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(new int[]{1, 2, 3}, new int[][]{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}});
        do_func(new int[]{0}, new int[][]{{}, {0}});
//        do_func(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[][]{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}});
    }

    private static void do_func(int[] nums, int[][] expected) {
        List<List<Integer>> ret = subsets(nums);
        System.out.println(ret);
        System.out.println(ArrayListUtils.isSame(ret, expected));
        System.out.println("--------------");
    }
}
