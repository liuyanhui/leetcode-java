package airbnb;

import java.util.HashMap;
import java.util.Map;

public class longest_substring_without_repeating_characters {

    public static void main(String[] args) {
        String s = "abcabcbb";
        int ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "bbbbbbbbbbb";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "pwwkew";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "au";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "abcabcbb";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "aab";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);
        s = "dvdf";
        ret = lengthOfLongestSubstring(s);
        System.out.println("s=" + s + ",ret=" + ret);

    }

    /**
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/
     * <p>3. Longest Substring Without</p>
     * Repeating Characters
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        int ret = 0;
        if (s == null || s.length() == 0) {
            return ret;
        }

        //<char,first_index>
        Map<Character, Integer> charAppearedMap = new HashMap<>();
        int left = 0, right = 0;
        while (right < s.length()) {
            char r = s.charAt(right);
            if (charAppearedMap.containsKey(r)) {
                //计算ret
                int t = right - left;
                ret = ret > t ? ret : t;

                //left 向右移动到first_index的位置
                //根据first_index清理字符
                int tmpLeftIndex = charAppearedMap.get(s.charAt(left));
                while (left <= tmpLeftIndex) {
                    //left前进1
                    charAppearedMap.remove(s.charAt(left++));
                }
            } else {
                //right前进1
                charAppearedMap.put(r, right++);
                if (right == s.length()) {
                    //计算ret
                    int t = right - left;
                    ret = ret > t ? ret : t;
                }
            }
        }

        return ret;
    }

    //有错误，带修改
//    public static int lengthOfLongestSubstring2(String s) {
//        int ret = 0;
//        if (s == null || s.length() == 0) {
//            return ret;
//        }
//
//        //<char,first_index>
//        Map<Character, Integer> charAppearedMap = new HashMap<>();
//        int left = 0, right = 0;
//        while (right < s.length()) {
//            char r = s.charAt(right);
//            if (charAppearedMap.containsKey(r)) {
//                left = Math.max(left, charAppearedMap.get(r));
//            }
//            //right前进1
//            charAppearedMap.put(r, right++);
//            //计算ret
//            int t = right - left - 1;
//            ret = ret > t ? ret : t;
//        }
//
//        return ret;
//    }
}
