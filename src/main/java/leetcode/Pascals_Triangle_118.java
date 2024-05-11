package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/pascals-triangle/
 * 118. Pascal's Triangle
 * Easy
 * ----------------------
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 *
 * Example 1:
 * Input: numRows = 5
 * Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 *
 * Example 2:
 * Input: numRows = 1
 * Output: [[1]]
 *
 * Constraints:
 * 1 <= numRows <= 30
 */
public class Pascals_Triangle_118 {

    public static List<List<Integer>> generate(int numRows) {
        return generate_3(numRows);
    }

    /**
     *
     * round 3
     * Score[3] Lower is harder
     *
     * 验证通过：
     * Runtime 1 ms Beats 85.57%
     * Memory 42.26 MB Beats 7.37%
     *
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate_3(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            List<Integer> curLevel = new ArrayList<>();
            curLevel.add(1);//第一个数字
            if (i > 1) {//第二行才执行
                List<Integer> lastLevel = ret.get(ret.size() - 1);
                //只计算中间的数字
                for (int j = 1; j < i - 1; j++) {
                    curLevel.add(lastLevel.get(j) + lastLevel.get(j - 1));
                }
                curLevel.add(1);//最后一个数字
            }
            ret.add(curLevel);
        }

        return ret;
    }


    /**
     * round 2
     *
     * 公式为：
     * cur[i]=pre[i-1]+pre[i]
     * 需要注意边界值
     *
     * generate_1()的方法更直观，简介，易理解
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 65.53% of Java online submissions for Pascal's Triangle.
     * Memory Usage: 42.3 MB, less than 13.82% of Java online submissions for Pascal's Triangle.
     *
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate_2(int numRows) {
        if (numRows == 0) return new ArrayList<>();
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            ret.add(new ArrayList<>());
            ret.get(i).add(1);//第一列都是1
            for (int j = 1; j <= i; j++) {
                int t = 0;
                if (j < i) t = ret.get(i - 1).get(j);//最后一列在上一行中没有对应的列
                ret.get(i).add(ret.get(i - 1).get(j - 1) + t);
            }
        }
        return ret;
    }

    /**
     * r[i][j]=r[i-1][j-1]+r[i-1][j] ,if i>=1 and j>=1
     * r[i][j]=1 ,if j==0 or j==i
     * 验证通过，
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Pascal's Triangle.
     * Memory Usage: 37.1 MB, less than 26.46% of Java online submissions for Pascal's Triangle.
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate_1(int numRows) {
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
        System.out.println("-------OK-------");
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
