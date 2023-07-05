package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 401. Binary Watch
 * Easy
 * --------------------------------------
 * A binary watch has 4 LEDs on the top to represent the hours (0-11), and 6 LEDs on the bottom to represent the minutes (0-59). Each LED represents a zero or one, with the least significant bit on the right.
 * For example, the below binary watch reads "4:51".
 *
 * Given an integer turnedOn which represents the number of LEDs that are currently on (ignoring the PM), return all possible times the watch could represent. You may return the answer in any order.
 * The hour must not contain a leading zero.
 * For example, "01:00" is not valid. It should be "1:00".
 *
 * The minute must be consist of two digits and may contain a leading zero.
 * For example, "10:2" is not valid. It should be "10:02".
 *
 * Example 1:
 * Input: turnedOn = 1
 * Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]
 *
 * Example 2:
 * Input: turnedOn = 9
 * Output: []
 *
 * Constraints:
 * 0 <= turnedOn <= 10
 */
public class Binary_Watch_401 {
    public static List<String> readBinaryWatch(int turnedOn) {
        return readBinaryWatch_1(turnedOn);
    }

    /**
     * review 
     * AC 中的另一种解法。非递归
     *
     * @param turnedOn
     * @return
     */
    public List<String> readBinaryWatch_2(int turnedOn) {
        ArrayList<String> results = new ArrayList<String>();

        for (int hour = 0; hour < 12; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                if (Integer.bitCount(hour) + Integer.bitCount(minute) == turnedOn) {
                    if (minute < 10) {
                        results.add(String.format("%d:0%d", hour, minute));
                    } else {
                        results.add(String.format("%d:%d", hour, minute));
                    }
                }
            }
        }
        return results;
    }

    /**
     * Thinking：
     * 1.两个数组中选择固定个数元素并排列的问题。
     *
     * 验证通过:
     * Runtime 8 ms Beats 55.98%
     * Memory 41 MB Beats 95.15%
     *
     * @param turnedOn
     * @return
     */
    public static List<String> readBinaryWatch_1(int turnedOn) {
        List<String> res = new ArrayList<>();
        if (turnedOn < 0 || 8 < turnedOn) return res;
        int[] hours = {1, 2, 4, 8};
        int[] minutes = {1, 2, 4, 8, 16, 32};

        for (int h = 0; h < 4; h++) {
            //先选择hour
            List<String> res_h = new ArrayList<>();
            getHours(h, 0, hours, 0, res_h);
            //再选择minute
            List<String> res_m = new ArrayList<>();
            getMinites(turnedOn - h, 0, minutes, 0, res_m);
            //组合
            for (String th : res_h) {
                for (String tm : res_m) {
                    res.add(th + ":" + tm);
                }
            }
        }
        return res;
    }

    /**
     * 计算hour的排序
     * @param n 参加计算的数字个数
     * @param sum 当前的数字的sum
     * @param hours 表盘上的hour的数组
     * @param beg hours的起始index
     * @param res 返回结果
     */
    private static void getHours(int n, int sum, int[] hours, int beg, List<String> res) {
        if (n < 0 || sum > 11) return;
        if (n == 0) {
            if (sum == 0) {
                res.add("0");
            } else {
                res.add(String.valueOf(sum));
            }
        } else if (n < 4) {
            for (int i = beg; i < hours.length; i++) {
                getHours(n - 1, sum + hours[i], hours, i + 1, res);
            }
        }
    }

    private static void getMinites(int n, int sum, int[] minites, int beg, List<String> res) {
        if (n < 0 || sum > 59) return;
        if (n == 0) {
            if (sum == 0) {
                res.add("00");
            } else if (sum < 10) {
                res.add("0" + String.valueOf(sum));
            } else {
                res.add(String.valueOf(sum));
            }
        } else if (n < 6) {
            for (int i = beg; i < minites.length; i++) {
                getMinites(n - 1, sum + minites[i], minites, i + 1, res);
            }
        }
    }

    public static void main(String[] args) {
        do_func(1, Arrays.asList("0:01", "0:02", "0:04", "0:08", "0:16", "0:32", "1:00", "2:00", "4:00", "8:00"));
        do_func(9, Arrays.asList(""));
        do_func(2, Arrays.asList("0:03", "0:05", "0:06", "0:09", "0:10", "0:12", "0:17", "0:18", "0:20", "0:24", "0:33", "0:34", "0:36", "0:40", "0:48", "1:01", "1:02", "1:04", "1:08", "1:16", "1:32", "2:01", "2:02", "2:04", "2:08", "2:16", "2:32", "3:00", "4:01", "4:02", "4:04", "4:08", "4:16", "4:32", "5:00", "6:00", "8:01", "8:02", "8:04", "8:08", "8:16", "8:32", "9:00", "10:00"));

    }

    private static void do_func(int turnedOn, List<String> expected) {
        List<String> ret = readBinaryWatch(turnedOn);
        System.out.println(ret);
        ret.sort(null);
        expected.sort(null);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
