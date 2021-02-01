package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/pascals-triangle-ii/
 * 119. Pascal's Triangle II
 * Easy
 * ------------------
 * Given an integer rowIndex, return the rowIndexth row of the Pascal's triangle.
 * Notice that the row index starts from 0.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * Follow up:
 * Could you optimize your algorithm to use only O(k) extra space?
 *
 * Example 1:
 * Input: rowIndex = 3
 * Output: [1,3,3,1]
 *
 * Example 2:
 * Input: rowIndex = 0
 * Output: [1]
 *
 * Example 3:
 * Input: rowIndex = 1
 * Output: [1,1]
 *
 * Constraints:
 * 0 <= rowIndex <= 33
 */
public class Pascals_Triangle_II_119 {
    public static List<Integer> getRow(int rowIndex) {
        return getRow_2(rowIndex);
    }

    /**
     * 参考思路
     * https://leetcode.com/problems/pascals-triangle-ii/discuss/38420/Here-is-my-brief-O(k)-solution
     * @param rowIndex
     * @return
     */
    public static List<Integer> getRow_2(int rowIndex) {
        List<Integer> ret = new ArrayList<>();
        if (rowIndex < 0) return ret;
        ret.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = i - 1; j > 0; j--) {
                int tmp = ret.get(j) + ret.get(j - 1);
                ret.set(j, tmp);
            }
            ret.add(1);
        }
        return ret;
    }

    /**
     * 验证通过，
     * Runtime: 1 ms, faster than 76.37% of Java online submissions for Pascal's Triangle II.
     * Memory Usage: 36.8 MB, less than 62.15% of Java online submissions for Pascal's Triangle II.
     * @param rowIndex
     * @return
     */
    public static List<Integer> getRow_1(int rowIndex) {
        List<Integer> ret = new ArrayList<>();
        if (rowIndex < 0) return ret;
        ret.add(1);
        for (int i = 0; i <= rowIndex; i++) {
            int cache = 1;
            for (int j = 1; j <= i; j++) {
                if (j == i) ret.add(1);
                else {
                    int tmp = cache;
                    cache = ret.get(j);
                    ret.set(j, tmp + ret.get(j));
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        List<Integer> expected = new ArrayList<>();

        do_func(3, Arrays.asList(new Integer[]{1, 3, 3, 1}));
        do_func(0, Arrays.asList(new Integer[]{1}));
        do_func(1, Arrays.asList(new Integer[]{1, 1}));
        do_func(10, expected);
    }

    private static void do_func(int numRows, List<Integer> expected) {
        List<Integer> ret = getRow(numRows);
        System.out.println(ret);
        System.out.println("--------------");
    }
}
