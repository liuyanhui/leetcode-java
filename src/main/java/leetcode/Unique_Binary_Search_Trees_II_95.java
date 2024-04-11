package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 95. Unique Binary Search Trees II
 * Medium
 * ----------------
 * Given an integer n, return all the structurally unique BST's (binary search trees), which has exactly n nodes of unique values from 1 to n. Return the answer in any order.
 *
 * Example 1:
 * Input: n = 3
 * Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
 *
 * Example 2:
 * Input: n = 1
 * Output: [[1]]
 *
 *  Constraints:
 * 1 <= n <= 8
 */
public class Unique_Binary_Search_Trees_II_95 {

    public static List<TreeNode> generateTrees(int n) {
        return generateTrees_5(n);
    }

    /**
     * round 3
     * Score[2] Lower is harder
     *
     * DP，递归都可实现。
     * 递归时返回ret.add(null)很精妙
     *
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees_5(int n) {
        return null;
    }

    /**
     * 还有一种解法，参考
     * https://leetcode.wang/leetCode-95-Unique-Binary-Search-TreesII.html#%E8%A7%A3%E6%B3%95%E5%9B%9B-%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92-2
     *
     * DP思路
     * 通过规律可以发现[1:i-1]确定的情况下，数字i在[1:i-1]结果的基础上出现的位置是有规律的。即，要么[1:i-1]结果集作为i的左子树；要么i作为[1:i-1]结果集的最右右子树
     */

    /**
     * round 2
     * 递归法
     * 节点为[1:n]
     * 如果i为根节点，那么左子树就是f([1:i-1])，右子树为f([i+1,n])
     * i从1~n依次计算，然后合并
     *
     * 不够优雅，需要参考generateTrees_2()
     *
     * 验证通过：
     * Runtime: 2 ms, faster than 70.36% of Java online submissions for Unique Binary Search Trees II.
     * Memory Usage: 45.8 MB, less than 27.02% of Java online submissions for Unique Binary Search Trees II.
     *
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees_4(int n) {
        List<TreeNode> ret = do_recursive_4(1, n);
        return ret;
    }

    private static List<TreeNode> do_recursive_4(int beg, int end) {
        List<TreeNode> ret = new ArrayList<>();
        if (beg > end) return ret;
        if (beg == end) {
            TreeNode t = new TreeNode(beg);
            ret.add(t);
            return ret;
        }
        for (int i = beg; i <= end; i++) {
            List<TreeNode> lefts = do_recursive_4(beg, i - 1);
            List<TreeNode> rights = do_recursive_4(i + 1, end);
            if (lefts.size() == 0) {
                for (TreeNode r : rights) {
                    TreeNode t = new TreeNode(i);
                    t.right = r;
                    ret.add(t);
                }
            } else if (rights.size() == 0) {
                for (TreeNode l : lefts) {
                    TreeNode t = new TreeNode(i);
                    t.left = l;
                    ret.add(t);
                }
            } else {
                for (TreeNode l : lefts) {
                    for (TreeNode r : rights) {
                        TreeNode t = new TreeNode(i);
                        t.left = l;
                        t.right = r;
                        ret.add(t);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * DP思路，一个很厉害的思路
     * 参考文档：
     * https://leetcode.com/problems/unique-binary-search-trees-ii/discuss/31493/Java-Solution-with-DP
     * https://leetcode.wang/leetCode-95-Unique-Binary-Search-TreesII.html#%E8%A7%A3%E6%B3%95%E4%B8%89-%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 93.81% of Java online submissions for Unique Binary Search Trees II.
     * Memory Usage: 38.7 MB, less than 98.70% of Java online submissions for Unique Binary Search Trees II.
     *
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees_3(int n) {
        List<TreeNode>[] ret = new List[n + 1];
        ret[0] = new ArrayList<>();
        ret[0].add(null);//review 省去了后面对列表进行非空判断的逻辑
        for (int i = 1; i <= n; i++) {//依次计算DP数组
            ret[i] = new ArrayList<>();
            //计算dp[i]
            for (int j = 1; j <= i; j++) {
                List<TreeNode> nodeLefts = ret[j - 1];
                //左子树列表和右子树列表的笛卡尔积组合
                for (TreeNode l : nodeLefts) {
                    List<TreeNode> nodeRights = ret[i - j];
                    for (TreeNode r : nodeRights) {
                        TreeNode parent = new TreeNode(j);
                        parent.left = cloneTreeNode(l, 0);
                        parent.right = cloneTreeNode(r, j);
                        ret[i].add(parent);
                    }
                }
            }
        }
        return ret[ret.length - 1];
    }

    /**
     * 这个思路的精彩的地方在于这个clone函数的思想，offset简直了
     * @param node
     * @param offset
     * @return
     */
    private static TreeNode cloneTreeNode(TreeNode node, int offset) {
        if (node == null) return null;
        TreeNode r = new TreeNode(node.val + offset);
        r.left = cloneTreeNode(node.left, offset);
        r.right = cloneTreeNode(node.right, offset);
        return r;
    }

    /**
     * do_recursive()的简化版
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 93.81% of Java online submissions for Unique Binary Search Trees II.
     * Memory Usage: 40.1 MB, less than 23.05% of Java online submissions for Unique Binary Search Trees II.
     *
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees_2(int n) {
        return do_recursive_2(1, n);
    }

    /**
     * do_recursive()的简化版
     * 参考资料：
     * https://leetcode.com/problems/unique-binary-search-trees-ii/discuss/31494/A-simple-recursive-solution
     *
     * @param beg
     * @param end
     * @return
     */
    private static List<TreeNode> do_recursive_2(int beg, int end) {
        List<TreeNode> ret = new ArrayList<>();
        if (beg > end) {
            //review tip:这里的代码很巧妙，使得后续的左子树列表和右子树列表进行笛卡尔积的时候无需进行null的判断
            ret.add(null);
            return ret;
        } else if (beg == end) {
            return new ArrayList<TreeNode>() {{
                add(new TreeNode(beg));
            }};
        }
        for (int i = beg; i <= end; i++) {
            List<TreeNode> t_lefts = do_recursive_2(beg, i - 1);
            List<TreeNode> t_rights = do_recursive_2(i + 1, end);
            for (TreeNode left : t_lefts) {
                for (TreeNode right : t_rights) {
                    TreeNode r = new TreeNode(i);
                    r.left = left;
                    r.right = right;
                    ret.add(r);
                }
            }
        }
        return ret;
    }

    /**
     * 递归思路：
     * 公式为：
     * 1.G(n) = T[1:n] = F(1) + F(2) + .. + F(n)
     * 2.F(i)的根为[i],左子树为 T[1:i-1],右子树为T[i+1:n]，F(i)的解为左子树和右子树的笛卡尔积，其中根节点为[i]
     *
     * 验证通过：
     * Runtime: 1 ms, faster than 93.81% of Java online submissions for Unique Binary Search Trees II.
     * Memory Usage: 40 MB, less than 28.96% of Java online submissions for Unique Binary Search Trees II.
     * @param n
     * @return
     */
    public static List<TreeNode> generateTrees_1(int n) {
        return do_recursive(1, n);
    }

    private static List<TreeNode> do_recursive(int beg, int end) {
        List<TreeNode> ret = new ArrayList<>();
        if (beg > end) {
            return null;
        } else if (beg == end) {
            return new ArrayList<TreeNode>() {{
                add(new TreeNode(beg));
            }};
        }
        for (int i = beg; i <= end; i++) {
            List<TreeNode> t_lefts = do_recursive(beg, i - 1);
            List<TreeNode> t_rights = do_recursive(i + 1, end);
            //两个都不为空的情况
            if ((t_lefts != null && t_lefts.size() > 0) && (t_rights != null && t_rights.size() > 0)) {
                for (TreeNode left : t_lefts) {
                    for (TreeNode right : t_rights) {
                        TreeNode r = new TreeNode(i);
                        r.left = left;
                        r.right = right;
                        ret.add(r);
                    }
                }
            } else if (t_lefts != null && t_lefts.size() > 0) {//left不为空，right为空的情况
                for (TreeNode left : t_lefts) {
                    TreeNode r = new TreeNode(i);
                    r.left = left;
                    ret.add(r);
                }
            } else {//left为空，right不为空的情况
                for (TreeNode right : t_rights) {
                    TreeNode r = new TreeNode(i);
                    r.right = right;
                    ret.add(r);
                }
            }
        }
        return ret;
    }

//    public static void main(String[] args) {
//        do_func(3, new TreeNode[]{});
//    }
//
//    private static void do_func(int s, TreeNode[] expected) {
//        List<TreeNode> ret = generateTrees(s);
//        System.out.println(ret);
//        System.out.println(ArrayListUtils.isSame(ret, expected));
//        System.out.println("--------------");
//    }public static void main(String[] args) {
//        do_func(3, new TreeNode[]{});
//    }
//
//    private static void do_func(int s, TreeNode[] expected) {
//        List<TreeNode> ret = generateTrees(s);
//        System.out.println(ret);
//        System.out.println(ArrayListUtils.isSame(ret, expected));
//        System.out.println("--------------");
//    }
}
