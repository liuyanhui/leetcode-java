package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 299. Bulls and Cows
 * Medium
 * ------------------
 * You are playing the Bulls and Cows game with your friend.
 *
 * You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess, you provide a hint with the following info:
 * The number of "bulls", which are digits in the guess that are in the correct position.
 * The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
 * Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.
 *
 * The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret and guess may contain duplicate digits.
 *
 * Example 1:
 * Input: secret = "1807", guess = "7810"
 * Output: "1A3B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1807"
 *   |
 * "7810"
 *
 * Example 2:
 * Input: secret = "1123", guess = "0111"
 * Output: "1A1B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1123"        "1123"
 *   |      or     |
 * "0111"        "0111"
 * Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged to allow one 1 to be a bull.
 *
 * Example 3:
 * Input: secret = "1", guess = "0"
 * Output: "0A0B"
 *
 * Example 4:
 * Input: secret = "1", guess = "1"
 * Output: "1A0B"
 *
 * Constraints:
 * 1 <= secret.length, guess.length <= 1000
 * secret.length == guess.length
 * secret and guess consist of digits only.
 */
public class Bulls_and_Cows_299 {
    public static String getHint(String secret, String guess) {
        return getHint_3(secret, guess);
    }

    /**
     * 有点套路
     * 参考思路：
     * https://leetcode.com/problems/bulls-and-cows/discuss/74621/One-pass-Java-solution
     *
     * 验证通过：
     * Runtime: 10 ms, faster than 28.38% of Java online submissions for Bulls and Cows.
     * Memory Usage: 39.7 MB, less than 11.26% of Java online submissions for Bulls and Cows.
     *
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_3(String secret, String guess) {
        int a = 0, b = 0;
        int[] cows = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            Character s = secret.charAt(i);
            Character g = guess.charAt(i);
            if (g == s) {
                a++;
            } else {
                if (cows[s - '0'] < 0) b++;
                if (cows[g - '0'] > 0) b++;
                cows[s - '0']++;
                cows[g - '0']--;
            }
        }
        return a + "A" + b + "B";
    }

    /**
     * getHint_1的优化代码
     * 验证通过：
     * Runtime: 7 ms, faster than 47.74% of Java online submissions for Bulls and Cows.
     * Memory Usage: 39.8 MB, less than 8.89% of Java online submissions for Bulls and Cows.
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_2(String secret, String guess) {
        int[] cowsAppearedCount = new int[10];//技术secret中数字出现的次数
        int[] bullsAppeardIndex = new int[secret.length()];//记录bulls出现的index
        int a = 0, b = 0;
        //从前向后遍历，先找到xA，缓存非bulls的数字，并记录数字出现的次数。
        for (int i = 0; i < secret.length(); i++) {
            Character sc = secret.charAt(i);
            if (sc == guess.charAt(i)) {
                bullsAppeardIndex[i] = 1;
                a++;
            } else {
                cowsAppearedCount[sc - '0']++;
            }
        }
        //再次遍历，计算yB
        for (int i = 0; i < guess.length(); i++) {
            if (bullsAppeardIndex[i] == 1) continue;
            Character gc = guess.charAt(i);
            if (cowsAppearedCount[gc - '0'] > 0) {
                b++;
                cowsAppearedCount[gc - '0']--;
            }
        }
        return a + "A" + b + "B";
    }

    /**
     *
     * 验证通过：
     * Runtime: 16 ms, faster than 6.15% of Java online submissions for Bulls and Cows.
     * Memory Usage: 40.1 MB, less than 5.57% of Java online submissions for Bulls and Cows.
     *
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_1(String secret, String guess) {
        Map<Character, Integer> cached = new HashMap<>();
        int a = 0, b = 0;
        int[] aAppeardIndex = new int[secret.length()];
        //从前向后遍历，先找到xA，缓存非bulls的数字，并记录数字出现的次数。
        for (int i = 0; i < secret.length(); i++) {
            Character sc = secret.charAt(i);
            if (sc == guess.charAt(i)) {
                aAppeardIndex[i] = 1;
                a++;
            } else {
                int t = 1;
                if (cached.containsKey(sc)) {
                    t = cached.get(sc) + 1;
                }
                cached.put(sc, t);
            }
        }
        //再次遍历，计算yB
        for (int i = 0; i < guess.length(); i++) {
            if (aAppeardIndex[i] == 1) continue;
            Character gc = guess.charAt(i);
            if (cached.containsKey(gc) && cached.get(gc) > 0) {
                b++;
                cached.put(gc, cached.get(gc) - 1);
            }
        }
        return a + "A" + b + "B";
    }

    public static void main(String[] args) {
        do_func("1807", "7810", "1A3B");
        do_func("1123", "0111", "1A1B");
        do_func("1", "0", "0A0B");
        do_func("1", "1", "1A0B");
    }

    private static void do_func(String secret, String guess, String expected) {
        String ret = getHint(secret, guess);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
