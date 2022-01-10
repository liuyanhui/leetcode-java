package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 60. Permutation Sequence
 * Hard
 * -----------------------
 * The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
 *
 * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * Given n and k, return the kth permutation sequence.
 *
 * Example 1:
 * Input: n = 3, k = 3
 * Output: "213"
 *
 * Example 2:
 * Input: n = 4, k = 9
 * Output: "2314"
 *
 * Example 3:
 * Input: n = 3, k = 1
 * Output: "123"
 *
 * Constraints:
 * 1 <= n <= 9
 * 1 <= k <= n!
 */
public class Permutation_Sequence_60 {
    public static String getPermutation(int n, int k) {
        return getPermutation_3(n, k);
    }

    /**
     * getPermutation_2的代码优化版
     * 参考资料:
     * https://leetcode.com/problems/permutation-sequence/discuss/22507/%22Explain-like-I'm-five%22-Java-Solution-in-O(n)
     * https://leetcode.com/submissions/api/detail/60/java/1/
     * @param n
     * @param k
     * @return
     */
    public static String getPermutation_3(int n, int k) {
        String ret = "";
        //初始化
        List<Integer> nums = new ArrayList<>();
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            nums.add(i);
            factorial[i] = factorial[i - 1] * i;
        }
        k = k - 1;//套路：当除数和商存在边界条件时，可以考虑把被除数+1或者-1。详见getPermutation_2
        //计算
        for (int i = n - 1; i >= 0; i--) {
            int quotient = k / factorial[i];//商
            int remainder = k % factorial[i];//余数
            ret += String.valueOf(nums.get(quotient));
            k = remainder;
            nums.remove(quotient);
        }

        return ret;
    }

    /**
     * 除法思路
     * 示例如下：
     * n=9, k=278893, result="793416258"
     * 阶乘如下：
     * 1! = 1
     * 2! = 2
     * 3! = 6
     * 4! = 24
     * 5! = 120
     * 6! = 720
     * 7! = 5040
     * 8! = 40320
     * 9! = 362880
     * 计算步骤如下：
     * #当余数大于0时，取第商位数字。
     * 278893/40320=6...36973 [123456789]-->7[7]+[12345689]
     * 36973/5040=7...1693 [12345689]-->8[9]+[1234568]
     * 1693/720=2...253 [1234568]-->3[3]+[124568]
     * 253/120=2...13 [124568]-->3[4]+[12568]
     * 13/24=0...13 [12568]-->0[1]+[2568]
     * 13/6=2...1 [2568]-->3[6]+[258]
     * 1/2=0...1 [258]-->0[2]+[58]
     * #当余数为0时，取第0位数字，剩下的数字反转后依次获取：
     * 1/1=1...0 [58]-->0[5]+[8]
     * #剩下的数字，反转后一次获取
     * 0 [8]-->0[8]
     * #结果为793416258
     *
     * 伪代码如下：
     * nums=[1~n]
     * for(i from n-1 to 0){
     *     quotient = k/(i!)
     *     remainder=k%(i!)
     *     if(remainder==0){
     *         quotient--
     *     }
     *     ret=ret+nums[quotient]
     *     k=remainder
     *     nums.remove(quotient)
     *     if(remainder==0) break;
     * }
     * for(reverse(nums){
     *     ret=ret+nums[i]
     * }
     * reverse nums then add to ret
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 32.82% of Java online submissions for Permutation Sequence.
     * Memory Usage: 38.2 MB, less than 32.97% of Java online submissions for Permutation Sequence.
     *
     * @param n
     * @param k
     * @return
     */
    public static String getPermutation_2(int n, int k) {
        String ret = "";
        //初始化
        List<Integer> nums = new ArrayList<>();
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            nums.add(i);
            factorial[i] = factorial[i - 1] * i;
        }
        //计算
        for (int i = n - 1; i >= 0; i--) {
            int quotient = k / factorial[i];//商
            int remainder = k % factorial[i];//余数
            if (remainder == 0) {
                quotient--;
            }
            ret += String.valueOf(nums.get(quotient));
            k = remainder;
            nums.remove(quotient);
            if (remainder == 0) break;//套路：当除数和商存在边界条件时，可以考虑把被除数+1或者-1。详见getPermutation_3
        }
//        反转剩下的数字
        for (int i = nums.size() - 1; i >= 0; i--) {
            ret += String.valueOf(nums.get(i));
        }

        return ret;
    }

    /**
     * 逻辑正确，但是 Time Limit Exceeded
     * 1.先从小到大计算出所有的permutation，计算完第n个后，终止。
     * 2.递归计算permutation
     *
     * @param n
     * @param k
     * @return
     */
    public static String getPermutation_1(int n, int k) {
        List<String> ret = new ArrayList<>();
        int[] existed = new int[n + 1];
        backtrack("", existed, n, k, ret);
        return ret.get(ret.size() - 1);
    }

    private static boolean backtrack(String pre, int[] existed, int n, int k, List<String> ret) {
        if (pre.length() == n) {
            ret.add(pre);
            return ret.size() == k;
        }
        for (int i = 1; i <= n; i++) {
            if (existed[i] > 0) continue;
            existed[i] = 1;
            if (backtrack(pre + String.valueOf(i), existed, n, k, ret)) {
                return true;
            }
            existed[i] = 0;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func(3, 3, "213");
        do_func(4, 9, "2314");
        do_func(3, 1, "123");
        do_func(9, 1, "123456789");
        do_func(9, 278893, "793416258");
        do_func(9, 278892, "793415862");
        do_func(9, 278894, "793416285");
        do_func(3, 2, "132");
        do_func(3, 6, "321");
    }

    private static void do_func(int n, int k, String expected) {
        String ret = getPermutation(n, k);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
