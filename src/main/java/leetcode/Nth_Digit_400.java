package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 400. Nth Digit
 * Medium
 * ---------------------
 * Given an integer n, return the nth digit of the infinite integer sequence [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...].
 *
 * Example 1:
 * Input: n = 3
 * Output: 3
 *
 * Example 2:
 * Input: n = 11
 * Output: 0
 * Explanation: The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 */
public class Nth_Digit_400 {
    public static int findNthDigit(int n) {
        return findNthDigit_4(n);
    }

    /**
     * round 2
     *
     * Thinking：
     * 1.native solution
     * 从1到n开始依次计算每个数字。时间复杂度O(N)
     * 2.总结规律
     * 1~9每个数字都是1位，共9*1=9个digit；10~99每个数字都是2位，共90*2=180个digit；100~999每个数字都是3位，共900*3=2700个digit；..
     * 3.分为3个步骤：
     * 3.1.先找到数字n在几位数的范围内
     * 3.2.再使用binary search定位到具体数字
     * 3.3.最后得到解
     * findNthDigit_4()方法有问题
     *
     * findNthDigit_3()是最优解
     *
     * @param n
     * @return
     */
    public static int findNthDigit_4(int n) {
        if (n < 10) return n;
        //根据n定位slot
        int base = 1;//i位数的起始值。如：1，10，100
        int baseCnt = 0;//所有小于i-1位数字的digit总和
        int width = 1;//数字实际的位数
        while (width < 10) {
            base = (int) Math.pow(10, width - 1);
            int tmpCnt = 9 * base * width;//i位数字的digit总和
            if (baseCnt > n - tmpCnt) {//用减法防止int溢出
                break;
            }
            baseCnt += tmpCnt;
            width++;
        }
        //定位到具体数字
        int number = (n - baseCnt - 1) / width + base;
        //计算digit在number中的序号
        int seq = (n - baseCnt - 1) % width;//mod运算从0开始，所以要-1
        //最后得到解
        return String.valueOf(number).charAt(seq) - '0';
    }

    /**
     * https://leetcode.com/problems/nth-digit/discuss/88363/Java-solution
     * @param n
     * @return
     */
    public static int findNthDigit_3(int n) {
        int len = 1;
        long count = 9;
        int start = 1;

        while (n > len * count) {
            n -= len * count;
            len += 1;
            count *= 10;
            start *= 10;
        }

        start += (n - 1) / len;
        String s = Integer.toString(start);
        return Character.getNumericValue(s.charAt((n - 1) % len));
    }

    /**
     * 数学公式法
     * 参考思路：
     * https://leetcode.com/problems/nth-digit/discuss/88369/0ms-C%2B%2B-Solution-with-Detail-Explanation
     * https://leetcode.com/problems/nth-digit/discuss/88400/Just-explain-no-code
     * https://leetcode.com/problems/nth-digit/discuss/88363/Java-solution
     *
     * 范围规律如下：
     * 1~9：1*9，都是1个数字（1位数）
     * 10~99：2*90=180，都是2个数字（2位数）
     * 100~999：3*900=2700，都是3个数字（3位数）
     * 1000~9999：4*9000=36000，都是4个数字（4位数）
     * ....
     * 步骤如下：
     * 1.找到n的对应上面的范围，个位数范围 or 2位数范围 or 3位数范围 or ...
     * 2.找到n对应的整数数字
     * 3.找到n对应的数字字符
     *
     * 金矿：
     * 1.加法变减法，可以有效避免int超过最大值的情况。如：在循环遍历中，从Integer.MAX_VALUE开始遍历，每次减去一个值。
     * 2.如果有超过2中的指标，需要单独考虑，分别计算。最好从变量名就可以直观的区分开。千万不要搞混。
     *
     * @param n
     * @return
     */
    public static int findNthDigit_2(int n) {
        //1.找到n的对应上面的范围：个位数范围 or 2位数范围 or 3位数范围 or ...
        int base = 1;//数字的位数
        int start = 1;//每个阶段的开始数字
        int count = base * start * 9;//每个阶段的总数字字符数的累计值，每个阶段的累计值
        while (0 < n - base * start * 9) {
            base++;
            start *= 10;
            count += base * start * 9;
        }
        //2.找到n对应的整数数字
        int lastLevelCount = count - base * start * 9;
        int num = (n - lastLevelCount) / base;
        int idx = 0;
        if ((n - lastLevelCount) % base == 0) {
            idx = base - 1;
        } else {
            num += 1;
            idx = (n - lastLevelCount) % base - 1;
        }
        num = num + start - 1;
        //3.找到n对应的数字字符
        return String.valueOf(num).charAt(idx) - '0';
    }

    /**
     * 遍历法，逻辑正确。但是会 Time Limit Exceed
     *
     * @param n
     * @return
     */
    public static int findNthDigit_1(int n) {
        int ret = 1;
        int counter = 0;//计数器
        for (int i = 1; i <= n && counter <= n; i++) {
            int c = i;
            List<Integer> digitList = new ArrayList<>();
            while (c > 0) {
                //提取数字，倒序加入数组，即低位在前
                digitList.add(c - c / 10 * 10);
                c /= 10;
            }

            while (counter < n && digitList.size() > 0) {
                counter++;
                if (counter == n) {
                    return digitList.get(digitList.size() - 1);
                } else {
                    digitList.remove(digitList.size() - 1);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        do_func(3, 3);
        do_func(11, 0);
        do_func(30, 2);
        do_func(2345, 1);
        do_func(Integer.MAX_VALUE, 2);
        do_func(1111111111, 1);
    }

    private static void do_func(int n, int expected) {
        int ret = findNthDigit(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
//        String ss ="0123456789";
//        System.out.println("2="+ss.charAt(2));
    }
}
