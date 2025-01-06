package leetcode;

/**
 * 278. First Bad Version
 * Easy
 * ------------------------------
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.
 * <p>
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
 * <p>
 * You are given an API bool isBadVersion(version) which returns whether version is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.
 * <p>
 * Example 1:
 * Input: n = 5, bad = 4
 * Output: 4
 * Explanation:
 * call isBadVersion(3) -> false
 * call isBadVersion(5) -> true
 * call isBadVersion(4) -> true
 * Then 4 is the first bad version.
 * <p>
 * Example 2:
 * Input: n = 1, bad = 1
 * Output: 1
 * <p>
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
            return firstBadVersion_1(n);
        }

        /**
         * round 3
         * Score[3] Lower is harder
         * <p>
         * firstBadVersion_1()中的mid的写法很关键。
         *
         * @param n
         * @return
         */

        public int firstBadVersion_1(int n) {
            int l = 1, r = n;
            int mid = 0;
            while (l < r) {
                //review 下面的代码不能写成 [mid = r - (r - l) / 2;]，因为无法处理l=4,r=5的情况。因为mid=5，会死循环。
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
        do_func(Integer.MAX_VALUE, 909090, 909090);
        do_func(5, 4, 4);
        do_func(5, 3, 3);
    }


    private static void do_func(int n, int bad, int expected) {
        Solution solution = new Solution(bad);
        int ret = solution.firstBadVersion(n);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
