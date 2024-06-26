package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. Restore IP Addresses
 * Medium
 * ---------------
 * A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 (inclusive) and cannot have leading zeros.
 * For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
 *
 * Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s. You are not allowed to reorder or remove any digits in s. You may return the valid IP addresses in any order.
 *
 * Example 1:
 * Input: s = "25525511135"
 * Output: ["255.255.11.135","255.255.111.35"]
 *
 * Example 2:
 * Input: s = "0000"
 * Output: ["0.0.0.0"]
 *
 * Example 3:
 * Input: s = "101023"
 * Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 *
 * Constraints:
 * 1 <= s.length <= 20
 * s consists of digits only.
 */
public class Restore_IP_Addresses_93 {
    public static List<String> restoreIpAddresses(String s) {
        return restoreIpAddresses_5(s);
    }

    /**
     * round 3
     * Score[4] Lower is harder
     *
     * Thinking：
     * 1. 递归法
     * helper(String s,int beg,List<String> ip,List<String> ret)
     *
     * 验证通过：
     * Runtime 2 ms Beats 83.30%
     * Memory 41.95 MB Beats 91.19%
     * @param s
     * @return
     */
    public static List<String> restoreIpAddresses_5(String s) {
        List<String> ret = new ArrayList<>();
        helper(s, 0, new ArrayList<>(), ret);
        return ret;
    }

    private static void helper(String s, int beg, List<String> ip, List<String> ret) {
        if (ip.size() <= 3) {
            for (int i = 1; i <= 3 && i + beg <= s.length(); i++) {
                String t = s.substring(beg, beg + i);
                if (check_5(t)) {
                    ip.add(t);
                    helper(s, beg + i, ip, ret);
                    ip.remove(ip.size() - 1);
                }
            }
        } else if (ip.size() == 4 && beg == s.length()) {
            ret.add(String.join(".", ip));
        }
    }

    private static boolean check_5(String s) {
        if (s == null || s.equals("") || s.length() > 3) {
            return false;
        }
        if (s.length() > 1 && s.charAt(0) == '0') {
            return false;
        }
        int t = Integer.valueOf(s);
        return 0 <= t && t <= 255;
    }

    /**
     * round 2
     *
     * 递归思路
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 92.64% of Java online submissions for Restore IP Addresses.
     * Memory Usage: 43.1 MB, less than 26.14% of Java online submissions for Restore IP Addresses.
     *
     * @param s
     * @return
     */
    public static List<String> restoreIpAddresses_4(String s) {
        List<String> ret = new ArrayList<>();
        if (s.length() < 4 || s.length() > 12) return ret;
        List<String> addr = new ArrayList<>();
        dfs(s, 0, addr, ret);
        return ret;
    }

    private static void dfs(String s, int beg, List<String> addr, List<String> ret) {
        if (addr.size() == 3) {
            String t = s.substring(beg);
            if (validate(t)) {
                StringBuilder ip = new StringBuilder();
                for (int i = 0; i < addr.size(); i++) {
                    ip.append(addr.get(i));
                    ip.append(".");
                }
                ip.append(t);
                ret.add(ip.toString());
            }
        } else {
            for (int i = 1; i < 4 && i + beg <= s.length(); i++) {
                String t = s.substring(beg, i + beg);
                if (validate(t)) {
                    addr.add(t);
                    dfs(s, i + beg, addr, ret);
                    addr.remove(addr.size() - 1);
                } else {
                    break;
                }
            }
        }
    }

    private static boolean validate(String s) {
        if (s == null || s.length() == 0 || s.length() > 3) return false;
        if (s.length() > 1 && s.charAt(0) == '0') return false;
        if (Integer.valueOf(s) > 255) return false;
        return true;
    }

    /**
     * 递归思路
     * tip：关键点在于把isValid函数单独剥离出来
     *
     * 验证通过：
     * Runtime: 5 ms, faster than 56.44% of Java online submissions for Restore IP Addresses.
     * Memory Usage: 39 MB, less than 75.00% of Java online submissions for Restore IP Addresses.
     * @param s
     * @return
     */
    public static List<String> restoreIpAddresses_3(String s) {
        List<String> ret = new ArrayList<>();
        do_recursive(s, 0, 1, "", ret);
        return ret;
    }

    private static void do_recursive(String s, int beg, int level, String cached, List<String> ret) {
        if (level == 5) {
            if (beg == s.length()) {
                ret.add(cached.substring(0, cached.length() - 1));
            }
            return;
        }
        // int beg = cached.length()-level;
        for (int i = 1; i <= 3; i++) {
            if (beg + i > s.length()) return;
            String t = s.substring(beg, beg + i);
            if (isValid(t)) {
                do_recursive(s, beg + i, level + 1, cached + t + ".", ret);
            }
        }

    }

    private static boolean isValid(String s) {
        if (s == null || s.length() == 0) return false;
        if ((s.length() == 1 && "0".equals(s)) || (s.length() <= 3 && s.charAt(0) != '0' && Integer.valueOf(s) <= 255))
            return true;
        return false;
    }

    /**
     * restoreIpAddresses_1的简化版，参考思路
     * https://leetcode.com/problems/restore-ip-addresses/discuss/30949/My-code-in-Java
     * 验证通过：
     * Runtime: 10 ms, faster than 14.27% of Java online submissions for Restore IP Addresses.
     * Memory Usage: 39.5 MB, less than 34.81% of Java online submissions for Restore IP Addresses.
     *
     * @param s
     * @return
     */
    public static List<String> restoreIpAddresses_2(String s) {
        List<String> ret = new ArrayList<>();
        if (s.length() > 12 || s.length() < 4) return ret;
        for (int i = 1; i < 4; i++) {
            for (int j = i + 1; j < i + 4; j++) {
                for (int k = j + 1; k < j + 4; k++) {
                    if (isValid(s, 0, i) && isValid(s, i, j) && isValid(s, j, k) && isValid(s, k, s.length())) {
                        ret.add(s.substring(0, i) + "." + s.substring(i, j) + "." + s.substring(j, k) + "." + s.substring(k, s.length()));
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 参考思路：
     * https://leetcode.com/problems/restore-ip-addresses/discuss/30949/My-code-in-Java
     *
     * 验证通过：
     * Runtime: 10 ms, faster than 14.27% of Java online submissions for Restore IP Addresses.
     * Memory Usage: 39.2 MB, less than 54.63% of Java online submissions for Restore IP Addresses.
     *
     * @param s
     * @return
     */
    public static List<String> restoreIpAddresses_1(String s) {
        List<String> ret = new ArrayList<>();
        if (s.length() > 12 || s.length() < 4) return ret;
        //第1段IP地址
        for (int i1 = 1; i1 <= 3; i1++) {
            String tmp1 = "";
            if (isValid(s, 0, i1)) {
                tmp1 = s.substring(0, i1);
                //第2段IP地址
                for (int i2 = 1; i2 <= 3; i2++) {
                    String tmp2 = "";
                    if (isValid(s, i1, i1 + i2)) {
                        tmp2 = s.substring(i1, i1 + i2);
                        //第3段IP地址
                        for (int i3 = 1; i3 <= 3; i3++) {
                            String tmp3 = "";
                            if (isValid(s, i1 + i2, i1 + i2 + i3)) {
                                tmp3 = s.substring(i1 + i2, i1 + i2 + i3);
                                //第4段IP地址
                                String tmp4 = "";
                                if (isValid(s, i1 + i2 + i3, s.length())) {
                                    tmp4 = s.substring(i1 + i2 + i3, s.length());
                                    ret.add(tmp1 + "." + tmp2 + "." + tmp3 + "." + tmp4);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    private static boolean isValid(String s, int beg, int end) {
        if (beg >= s.length() || end > s.length()) return false;
        if (beg >= end) return false;
        if (beg + 1 == end) {
            return true;
        } else {
            if (s.charAt(beg) == '0') return false;
            String t = s.substring(beg, end);
            if (Integer.valueOf(t) <= 255) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        do_func("25525511135", new String[]{"255.255.11.135", "255.255.111.35"});
        do_func("0000", new String[]{"0.0.0.0"});
        do_func("1111", new String[]{"1.1.1.1"});
        do_func("010010", new String[]{"0.10.0.10", "0.100.1.0"});
        do_func("101023", new String[]{"1.0.10.23", "1.0.102.3", "10.1.0.23", "10.10.2.3", "101.0.2.3"});
        do_func("11111111", new String[]{"1.1.111.111", "1.11.11.111", "1.11.111.11", "1.111.1.111", "1.111.11.11", "1.111.111.1", "11.1.11.111", "11.1.111.11", "11.11.1.111", "11.11.11.11", "11.11.111.1", "11.111.1.11", "11.111.11.1", "111.1.1.111", "111.1.11.11", "111.1.111.1", "111.11.1.11", "111.11.11.1", "111.111.1.1"});
        System.out.println("-------Done-------");
    }

    private static void do_func(String s, String[] expected) {
        List<String> ret = restoreIpAddresses(s);
        System.out.println(ret);
        boolean same = true;
        if (ret.size() == expected.length) {
            for (int i = 0; i < expected.length; i++) {
                if (!expected[i].equals(ret.get(i))) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        System.out.println(same);
        assert same;
        System.out.println("--------------");
    }
}
