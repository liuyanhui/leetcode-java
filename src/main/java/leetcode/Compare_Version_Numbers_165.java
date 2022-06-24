package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 165. Compare Version Numbers
 * Medium
 * ----------------
 * Given two version numbers, version1 and version2, compare them.
 *
 * Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits and may contain leading zeros. Every revision contains at least one character. Revisions are 0-indexed from left to right, with the leftmost revision being revision 0, the next revision being revision 1, and so on. For example 2.5.33 and 0.1 are valid version numbers.
 *
 * To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using their integer value ignoring any leading zeros. This means that revisions 1 and 001 are considered equal. If a version number does not specify a revision at an index, then treat the revision as 0. For example, version 1.0 is less than version 1.1 because their revision 0s are the same, but their revision 1s are 0 and 1 respectively, and 0 < 1.
 *
 * Return the following:
 * If version1 < version2, return -1.
 * If version1 > version2, return 1.
 * Otherwise, return 0.
 *
 * Example 1:
 * Input: version1 = "1.01", version2 = "1.001"
 * Output: 0
 * Explanation: Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
 *
 * Example 2:
 * Input: version1 = "1.0", version2 = "1.0.0"
 * Output: 0
 * Explanation: version1 does not specify revision 2, which means it is treated as "0".
 *
 * Example 3:
 * Input: version1 = "0.1", version2 = "1.1"
 * Output: -1
 * Explanation: version1's revision 0 is "0", while version2's revision 0 is "1". 0 < 1, so version1 < version2.
 *
 * Example 4:
 * Input: version1 = "1.0.1", version2 = "1"
 * Output: 1
 *
 * Example 5:
 * Input: version1 = "7.5.2.4", version2 = "7.5.3"
 * Output: -1
 *
 * Constraints:
 * 1 <= version1.length, version2.length <= 500
 * version1 and version2 only contain digits and '.'.
 * version1 and version2 are valid version numbers.
 * All the given revisions in version1 and version2 can be stored in a 32-bit integer.
 */
public class Compare_Version_Numbers_165 {
    public static int compareVersion(String version1, String version2) {
        return compareVersion_3(version1, version2);
    }


    /**
     * round 2
     *
     * 直觉思路intuition：
     * 1.split成整形数组，要注意数组中元素对应version中的顺序（大版本号在高位，小版本号在低位）
     * 2.从大版本号到小版本号比较数组，数组长度不够时按0处理
     *
     * 算法：
     * 1.输入转化成整数数组arr1[]和arr2[]，大版本在高位，小版本在低位，数组长度分别记为len1和len2。
     * 2.从高位到低位依次比较两个数组
     * 2.1 n1 = i<len1?arr1[i]:0
     * 2.2 n2 = i<len2?arr2[i]:0
     * 2.3 如果n1>n2，那么 返回1
     * 2.4 如果n1<n2，那么 返回-1
     * 2.5 如果n1==n2，那么 比较下一组数字
     * 2.6 i--
     *
     * fixme split()之后的数组顺序
     *
     * 验证通过:
     * Runtime: 1 ms, faster than 89.06% of Java online submissions for Compare Version Numbers.
     * Memory Usage: 41.8 MB, less than 66.06% of Java online submissions for Compare Version Numbers.
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion_3(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        int len = Math.max(arr1.length, arr2.length);
        for (int i = 0; i < len; i++) {
            int n1 = i < arr1.length ? Integer.valueOf(arr1[i]) : 0;
            int n2 = i < arr2.length ? Integer.valueOf(arr2[i]) : 0;
            if (n1 > n2) return 1;
            else if (n1 < n2) return -1;
        }
        return 0;
    }

    /**
     * 代码简化版，
     * 参考思路：
     * https://leetcode.com/problems/compare-version-numbers/discuss/50774/Accepted-small-Java-solution.
     * 验证通过：
     * Runtime: 1 ms, faster than 89.51% of Java online submissions for Compare Version Numbers.
     * Memory Usage: 37.1 MB, less than 70.37% of Java online submissions for Compare Version Numbers.
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion_2(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        int max = arr1.length > arr2.length ? arr1.length : arr2.length;
        for (int i = 0; i < max; i++) {
            int a1 = i < arr1.length ? Integer.valueOf(arr1[i]) : 0;
            int a2 = i < arr2.length ? Integer.valueOf(arr2[i]) : 0;
            if (a1 > a2) {
                return 1;
            } else if (a1 < a2) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 转换成列表，然后遍历并判断
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 89.51% of Java online submissions for Compare Version Numbers.
     * Memory Usage: 36.9 MB, less than 93.54% of Java online submissions for Compare Version Numbers.
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion_1(String version1, String version2) {
        //分割并转换成数组
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < arr1.length; i++) {
            list1.add(Integer.valueOf(arr1[i]));
        }
        for (int i = 0; i < arr2.length; i++) {
            list2.add(Integer.valueOf(arr2[i]));
        }
        //使两个list长度相同
        int gap = Math.abs(list1.size() - list2.size());
        if (list1.size() > list2.size()) {
            for (int i = 0; i < gap; i++) {
                list2.add(0);
            }
        } else {
            for (int i = 0; i < gap; i++) {
                list1.add(0);
            }
        }
        //依次比较reversion
        for (int i = 0; i < list2.size(); i++) {
            int a1 = list1.get(i);
            int a2 = list2.get(i);
            if (a1 > a2) {
                return 1;
            } else if (a1 < a2) {
                return -1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        do_func("0.0.0", "0.00", 0);
        do_func("1.01", "1.001", 0);
        do_func("1.0", "1.0.0", 0);
        do_func("0.1", "1.1", -1);
        do_func("1.0.1", "1", 1);
        do_func("7.5.2.4", "7.5.3", -1);
        do_func("1.2.3", "1", 1);

        //fixme review java的split()之后的数组中元素的顺序需要注意。
        //fixme 这种情况下，遍历经过split后的数组时，从0到n遍历就是split之前的信息，无需进行反转
        String s = "1,2,3,4,5,6,7,8";
        String[] arr = s.split(",");
        //fixme arr[0]是1，不是8
        System.out.println("arr[0]=" + arr[0]);
        ArrayUtils.printIntArray(arr);
    }

    private static void do_func(String version1, String version2, int expected) {
        int ret = compareVersion(version1, version2);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
