package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/pascals-triangle/
 *118. Pascal's Triangle
 * Easy
 * ----------------------
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 *
 * Example:
 * Input: 5
 * Output:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 */
public class Pascals_Triangle_118 {
    /**
     * r[i][j]=r[i-1][j-1]+r[i-1][j] ,if i>=1 and j>=1
     * r[i][j]=1 ,if j==0 or j==i
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Pascal's Triangle.
     * Memory Usage: 37.1 MB, less than 26.46% of Java online submissions for Pascal's Triangle.
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        if (numRows <= 0) return ret;
        for (int i = 0; i < numRows; i++) {
            List<Integer> tmp = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == i || j == 0) {
                    tmp.add(1);
                } else {
                    tmp.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(tmp);
        }
        return ret;
    }

    public static void main(String[] args) {
        List<List<Integer>> expected = new ArrayList<>();
        do_func(5, expected);
        do_func(1, expected);
        do_func(0, expected);
        do_func(10, expected);
    }

    private static void do_func(int numRows, List<List<Integer>> expected) {
        List<List<Integer>> ret = generate(numRows);
        for (int i = 0; i < ret.size(); i++) {
            System.out.print("[");
            for (int j = 0; j < ret.get(i).size(); j++) {
                System.out.print(ret.get(i).get(j));
                if (j < ret.get(i).size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
        System.out.println(ret);
        System.out.println("--------------");
    }
}
