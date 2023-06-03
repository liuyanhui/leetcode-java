package leetcode;

/**
 * 383. Ransom Note
 * Easy
 * -----------------------
 * Given two stings ransomNote and magazine, return true if ransomNote can be constructed from magazine and false otherwise.
 *
 * Each letter in magazine can only be used once in ransomNote.
 *
 * Example 1:
 * Input: ransomNote = "a", magazine = "b"
 * Output: false
 *
 * Example 2:
 * Input: ransomNote = "aa", magazine = "ab"
 * Output: false
 *
 * Example 3:
 * Input: ransomNote = "aa", magazine = "aab"
 * Output: true
 *
 * Constraints:
 * 1 <= ransomNote.length, magazine.length <= 10^5
 * ransomNote and magazine consist of lowercase English letters.
 */
public class Ransom_Note_383 {

    public static boolean canConstruct(String ransomNote, String magazine) {
        return canConstruct_2(ransomNote, magazine);
    }

    /**
     * round 2
     * 验证通过：
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct_2(String ransomNote, String magazine) {
        if (magazine.length() < ransomNote.length()) return false;
        int[] cntm = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            cntm[magazine.charAt(i) - 'a']++;
        }
        int[] cntr = new int[26];
        for (int i = 0; i < ransomNote.length(); i++) {
            cntr[ransomNote.charAt(i) - 'a']++;

        }
        for (int i = 0; i < 26; i++) {
            if (cntm[i] < cntr[i]) return false;
        }
        return true;
    }

    /**
     * 验证通过：
     * Runtime: 3 ms, faster than 83.11% of Java online submissions for Ransom Note.
     * Memory Usage: 38.9 MB, less than 93.16% of Java online submissions for Ransom Note.
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public static boolean canConstruct_1(String ransomNote, String magazine) {
        int[] count = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            count[c - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            count[c - 'a']--;
            if (count[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        do_func("a", "b", false);
        do_func("aa", "ab", false);
        do_func("aa", "aab", true);
    }

    private static void do_func(String ransomNote, String magazine, boolean expected) {
        boolean ret = canConstruct(ransomNote, magazine);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
