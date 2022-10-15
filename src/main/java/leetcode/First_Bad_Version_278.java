package leetcode;

/**
 * 278. First Bad Version
 * Easy
 * ------------------------------
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
 *
 * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.
 *
 * Example 1:
 * Input: n = 5, bad = 4
 * Output: 4
 * Explanation:
 * call isBadVersion(3) -> false
 * call isBadVersion(5) -> true
 * call isBadVersion(4) -> true
 * Then 4 is the first bad version.
 *
 * Example 2:
 * Input: n = 1, bad = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= bad <= n <= 2^31 - 1
 */
public class First_Bad_Version_278 {
    /**
     * 验证通过：
     * Runtime: 21 ms, faster than 72.26% of Java online submissions for First Bad Version.
     * Memory Usage: 41.7 MB, less than 5.90% of Java online submissions for First Bad Version.
     */
    public static class Solution extends VersionControl {
        public Solution(int badversion) {
            bad = badversion;
        }

        public int firstBadVersion(int n) {
            int l = 1, r = n;
            int mid = 0;
            while (l < r) {
                mid = l / 2 + r / 2;
                if (isBadVersion(mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return isBadVersion(l) ? l : 0;
        }
    }

    static class VersionControl {
        int bad;

        boolean isBadVersion(int version) {
            return bad <= version;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution(909090);
        System.out.println(solution.firstBadVersion(Integer.MAX_VALUE));
    }
}
