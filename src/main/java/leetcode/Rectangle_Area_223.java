package leetcode;

/**
 * 223. Rectangle Area
 * Medium
 * -------------------------
 * Given the coordinates of two rectilinear rectangles in a 2D plane, return the total area covered by the two rectangles.
 * <p>
 * The first rectangle is defined by its bottom-left corner (ax1, ay1) and its top-right corner (ax2, ay2).
 * <p>
 * The second rectangle is defined by its bottom-left corner (bx1, by1) and its top-right corner (bx2, by2).
 * <p>
 * Example 1:
 * Rectangle Area
 * Input: ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2
 * Output: 45
 * <p>
 * Example 2:
 * Input: ax1 = -2, ay1 = -2, ax2 = 2, ay2 = 2, bx1 = -2, by1 = -2, bx2 = 2, by2 = 2
 * Output: 16
 * <p>
 * Constraints:
 * -10^4 <= ax1 <= ax2 <= 10^4
 * -10^4 <= ay1 <= ay2 <= 10^4
 * -10^4 <= bx1 <= bx2 <= 10^4
 * -10^4 <= by1 <= by2 <= 10^4
 */
public class Rectangle_Area_223 {
    public static int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        return computeArea_r3_1(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
    }

    /**
     * review
     * round 3
     * Score[2] Lower is harder
     * <p>
     * Thinking
     * 1. 矩形面积相加，再减去重叠的部分。
     * 分为以下几种情况：A和B无重叠，A和B部分重叠，A和B完全重叠，A包含B，B包含A
     * 由简单到复杂的顺序。先判断是否无重叠，再判断是否完全重叠或者包含，最后计算部分重叠。
     * 2. 先判断是否重叠，再计算。
     * 计算两个矩阵的互相垂直的边的交点数量，记为jointCnt。共计算4*2=8次。
     * IF jointCnt==0 THEN 要么无重叠，要么包含。根据
     * IF jointCnt>=2 THEN 部分重叠，<=h2，vx1<=vx2
     * 根据交点坐标可以计算重叠部分的面积。
     * 3. 只要求计算面积，不需要得出交点。重点是怎么计算重叠部分的面积。这个思路可以简化代码逻辑。
     * 参考computeArea_1()
     * <p>
     * 验证通过：
     * Runtime 1 ms Beats 100.00%
     * Memory 42.52 MB Beats 44.40%
     *
     * @return
     */
    public static int computeArea_r3_1(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int area1 = getRectangleArea_r3_1(ax1, ay1, ax2, ay2);
        int area2 = getRectangleArea_r3_1(bx1, by1, bx2, by2);
        int area3 = getOverlapedArea_r3_1(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
        return area1 + area2 - area3;
    }

    private static int getOverlapedArea_r3_1(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        return getOverlappedLength_r3_1(ay1, ay2, by1, by2) * getOverlappedLength_r3_1(ax1, ax2, bx1, bx2);
    }

    //a1<a2 , b1<=b2
    private static int getOverlappedLength_r3_1(int a1, int a2, int b1, int b2) {
        if (a1 > b2 || a2 < b1) {
            return 0;
        }
        //review 这里很巧妙。但是如果能想到这个function，采用这样的方案也是顺利成章的。
        return Math.min(a2, b2) - Math.max(a1, b1);
    }

    private static int getRectangleArea_r3_1(int x1, int y1, int x2, int y2) {
        return Math.abs(y2 - y1) * Math.abs(x2 - x1);
    }

    //AC结果中有更简洁的办法。分别计算出min_right，max_left，min_high，max_low，再进行判断并计算。

    /**
     * 思考：
     * 1.重点是计算相交部分的面积。存在完全不交的情况。
     * 2.是否相交有以下集中情况：1.四个交点；2.两个交点；3.一个交点；4.没有交点
     * 3.有以下几种情况：重合、包含、交叉、无交集
     * 4.矩阵是二维的，是否可以转化成一维计算
     * 5.如果x和y轴同时有交集，那么一定有交集；否则，没有交集
     * 6.问题简化为判断两个线段[a1,a2]和[b1,b2]是否相交，并计算交集[c1,c2]。是否相交有3种情况：无交集、有交集、包含。
     * <p>
     * 算法：
     * 1.计算两个rectangle的面积，记为：area1和area2
     * 2.计算是否有交集
     * 2.1.如果x和y轴同时有交集，那么计算出交点，计算交集面积（记为area3）
     * 3.返回area1+area2-area3
     * <p>
     * 验证通过：
     * Runtime: 3 ms, faster than 89.42% of Java online submissions for Rectangle Area.
     * Memory Usage: 43.3 MB, less than 31.61% of Java online submissions for Rectangle Area.
     *
     * @param ax1
     * @param ay1
     * @param ax2
     * @param ay2
     * @param bx1
     * @param by1
     * @param bx2
     * @param by2
     * @return
     */
    public static int computeArea_1(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int area1 = getArea(ax1, ay1, ax2, ay2);
        int area2 = getArea(bx1, by1, bx2, by2);
        int area3 = getLengthOfIntersect(ax1, ax2, bx1, bx2) * getLengthOfIntersect(ay1, ay2, by1, by2);
        return area1 + area2 - area3;
    }

    /**
     * 计算线段的相交长度
     *
     * @param a1
     * @param a2
     * @param b1
     * @param b2
     * @return
     */
    private static int getLengthOfIntersect(int a1, int a2, int b1, int b2) {
        //无交集
        if (a2 < b1 || a1 > b2) {
            return 0;
        }
        //包含
        if (b1 <= a1 && a2 <= b2) {
            return a2 - a1;
        }
        if (a1 <= b1 && b2 <= a2) {
            return b2 - b1;
        }
        //有交集，不是包含
        if (b1 < a2 && a2 < b2) {
            return a2 - b1;
        }
        if (a1 < b2 && b2 < a2) {
            return b2 - a1;
        }
        return 0;
    }

    /**
     * 计算面积
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static int getArea(int x1, int y1, int x2, int y2) {
        return (x2 - x1) * (y2 - y1);
    }

    public static void main(String[] args) {
        do_func(-3, 0, 3, 4, 0, -1, 9, 2, 45);
        do_func(-2, -2, 2, 2, -2, -2, 2, 2, 16);
    }

    private static void do_func(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2, int expected) {
        int ret = computeArea(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
        System.out.println(ret);
        System.out.println(ret == expected);
        System.out.println("--------------");
    }
}
