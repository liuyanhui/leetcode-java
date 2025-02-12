package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 299. Bulls and Cows
 * Medium
 * ------------------
 * You are playing the Bulls and Cows game with your friend.
 * <p>
 * You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess, you provide a hint with the following info:
 * - The number of "bulls", which are digits in the guess that are in the correct position.
 * - The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
 * <p>
 * Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.
 * <p>
 * The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret and guess may contain duplicate digits.
 * <p>
 * Example 1:
 * Input: secret = "1807", guess = "7810"
 * Output: "1A3B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1807"
 * |
 * "7810"
 * <p>
 * Example 2:
 * Input: secret = "1123", guess = "0111"
 * Output: "1A1B"
 * Explanation: Bulls are connected with a '|' and cows are underlined:
 * "1123"        "1123"
 * |      or     |
 * "0111"        "0111"
 * Note that only one of the two unmatched 1s is counted as a cow since the non-bull digits can only be rearranged to allow one 1 to be a bull.
 * <p>
 * Example 3:
 * Input: secret = "1", guess = "0"
 * Output: "0A0B"
 * <p>
 * Example 4:
 * Input: secret = "1", guess = "1"
 * Output: "1A0B"
 * <p>
 * Constraints:
 * 1 <= secret.length, guess.length <= 1000
 * secret.length == guess.length
 * secret and guess consist of digits only.
 */
public class Bulls_and_Cows_299 {
    public static String getHint(String secret, String guess) {
        return getHint_r3_1(secret, guess);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 计数器思路
     * 分别用两个 Hashtable 保存 secret 和 guess 每个数字出现的次数，过滤掉 "Bulls" 之后，剩余的次数总和就是"cows"
     * <p>
     * 验证通过：
     * Runtime 5 ms Beats 87.66%
     * Memory 42.43 MB Beats 57.17%
     *
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_r3_1(String secret, String guess) {
        int bulls = 0, cows = 0;
        int[] cnt_sec = new int[10];
        int[] cnt_gus = new int[10];
        //统计和过滤
        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int c = guess.charAt(i) - '0';
            if (s == c) {
                bulls++;//计算bulls
            } else {
                cnt_sec[secret.charAt(i) - '0']++;
                cnt_gus[guess.charAt(i) - '0']++;
            }
        }
        //计算cows
        for (int i = 0; i < 10; i++) {
            cows += Math.min(cnt_sec[i], cnt_gus[i]);
        }
        return bulls + "A" + cows + "B";
    }

    /**
     * round2
     * getHint_4()的优化，把map替换为array
     * <p>
     * 验证通过：
     * Runtime 11 ms Beats 46.40%
     * Memory 43 MB Beats 50.45%
     *
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_5(String secret, String guess) {
        int bull = 0, cow = 0;
        // FIXME getHint_3()中的实现是把sarr和garr合并为一个数组。
        int[] sarr = new int[10], garr = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char g = guess.charAt(i);
            if (s == g) {
                bull++;
            } else {
                if (garr[s - '0'] > 0) {
                    cow++;
                    garr[s - '0']--;
                } else {
                    sarr[s - '0']++;
                }

                if (sarr[g - '0'] > 0) {
                    cow++;
                    sarr[g - '0']--;
                } else {
                    garr[g - '0']++;
                }
            }
        }

        return bull + "A" + cow + "B";
    }

    /**
     * round 2
     * <p>
     * 思考：
     * 1.需要求解两个值，所以先求bulls再计算cows。
     * 2.计算bulls。遍历secret和guess，统计bulls，记录bulls出现的位置。
     * 3.计算cows。计算时先过滤bulls出现的位置。
     * 4.合并2.3.，一次遍历，先计算bulls再计算cows。
     * <p>
     * 验证通过：
     * Runtime 17 ms Beats 26.78%
     * Memory 43.3 MB Beats 22.88%
     *
     * @param secret
     * @param guess
     * @return
     */
    public static String getHint_4(String secret, String guess) {
        int bull = 0, cow = 0;
        Map<Integer, Integer> smap = new HashMap<>();
        Map<Integer, Integer> gmap = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char g = guess.charAt(i);
            if (s == g) {
                bull++;
            } else {
                // FIXME 这里的逻辑有点复杂，需要优化。总共只有0~9十个数字，所以可以用Array替代Map。参考getHint_3()
                if (gmap.get(s - '0') != null && gmap.get(s - '0') > 0) {
                    cow++;
                    gmap.put(s - '0', gmap.get(s - '0') - 1);
                } else {
                    smap.putIfAbsent(s - '0', 0);
                    smap.put(s - '0', smap.get(s - '0') + 1);
                }
                if (smap.get(g - '0') != null && smap.get(g - '0') > 0) {
                    cow++;
                    smap.put(g - '0', smap.get(g - '0') - 1);
                } else {
                    gmap.putIfAbsent(g - '0', 0);
                    gmap.put(g - '0', gmap.get(g - '0') + 1);
                }
            }
        }

        return bull + "A" + cow + "B";
    }

    /**
     * review round2 巧妙的方法
     * 有点套路
     * 参考思路：
     * https://leetcode.com/problems/bulls-and-cows/discuss/74621/One-pass-Java-solution
     * <p>
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
     *
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
        System.out.println("====================");
    }

    private static void do_func(String secret, String guess, String expected) {
        String ret = getHint(secret, guess);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
