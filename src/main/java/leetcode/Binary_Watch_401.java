package leetcode;

import java.util.*;

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

    public static List<String> readBinaryWatch_1(int turnedOn) {
        List<String> res = new ArrayList<>();
        if (turnedOn < 0 || 8 < turnedOn) return res;
        int[] hours = {0, 1, 2, 4, 8};
        int[] minutes = {0, 1, 2, 4, 8, 16, 32};

        for (int h = 0; h < 4; h++) {
            //先选择hour
            List<String> res_h = new ArrayList<>();
            getHours(h, 0, hours, new HashSet<>(), res_h);
            //再选择minute
            List<String> res_m = new ArrayList<>();
            getMinites(turnedOn - h, 0, minutes, new HashSet<>(), res_m);
            //组合
            for (String th : res_h) {
                for (String tm : res_m) {
                    res.add(th + ":" + tm);
                }
            }
        }
        return res;
    }

    private static void getHours(int n, int sum, int[] hours, Set<Integer> seen, List<String> res) {
        if (n < 0 || sum > 11) return;
        if (n == 0) {
            if (sum == 0) {
                res.add("0");
            } else {
                res.add(String.valueOf(sum));
            }
        } else if (n < 4) {
            for (int i = 1; i < hours.length; i++) {
                if (seen.contains(hours[i])) continue;
                seen.add(hours[i]);
                getHours(n - 1, sum + hours[i], hours, seen, res);
                seen.remove(hours[i]);
            }
        }
    }

    private static void getMinites(int n, int sum, int[] minites, Set<Integer> seen, List<String> res) {
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
            for (int i = 1; i < minites.length; i++) {
                if (seen.contains(minites[i])) continue;
                seen.add(minites[i]);
                getMinites(n - 1, sum + minites[i], minites, seen, res);
                seen.remove(minites[i]);
            }
        }
    }

    public static void main(String[] args) {
        do_func(1, Arrays.asList("0:01", "0:02", "0:04", "0:08", "0:16", "0:32", "1:00", "2:00", "4:00", "8:00"));
        do_func(9, Arrays.asList(""));

    }

    private static void do_func(int turnedOn, List<String> expected) {
        List<String> ret = readBinaryWatch(turnedOn);
        System.out.println(ret);
        boolean same = ArrayListUtils.isSame(ret, expected);
        System.out.println(same);
        System.out.println("--------------");
    }
}
