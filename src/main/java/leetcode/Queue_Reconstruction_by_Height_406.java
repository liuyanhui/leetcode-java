package leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 406. Queue Reconstruction by Height
 * Medium
 * -----------------------------------------
 * You are given an array of people, people, which are the attributes of some people in a queue (not necessarily in order). Each people[i] = [hi, ki] represents the ith person of height hi with exactly ki other people in front who have a height greater than or equal to hi.
 *
 * Reconstruct and return the queue that is represented by the input array people. The returned queue should be formatted as an array queue, where queue[j] = [hj, kj] is the attributes of the jth person in the queue (queue[0] is the person at the front of the queue).
 *
 * Example 1:
 * Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
 * Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
 * Explanation:
 * Person 0 has height 5 with no other people taller or the same height in front.
 * Person 1 has height 7 with no other people taller or the same height in front.
 * Person 2 has height 5 with two persons taller or the same height in front, which is person 0 and 1.
 * Person 3 has height 6 with one person taller or the same height in front, which is person 1.
 * Person 4 has height 4 with four people taller or the same height in front, which are people 0, 1, 2, and 3.
 * Person 5 has height 7 with one person taller or the same height in front, which is person 1.
 * Hence [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] is the reconstructed queue.
 *
 * Example 2:
 * Input: people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
 * Output: [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
 *
 * Constraints:
 * 1 <= people.length <= 2000
 * 0 <= hi <= 10^6
 * 0 <= ki < people.length
 * It is guaranteed that the queue can be reconstructed.
 */
public class Queue_Reconstruction_by_Height_406 {
    public static int[][] reconstructQueue(int[][] people) {
        return reconstructQueue_1(people);
    }

    /**
     * review
     * 参考资料：
     * https://leetcode.com/problems/queue-reconstruction-by-height/solutions/89345/easy-concept-with-python-c-java-solution/
     * https://leetcode.com/problems/queue-reconstruction-by-height/solutions/427157/three-different-c-solutions-from-o-n-2-to-o-nlogn-faster-than-99/
     *
     * 1.Pick out tallest group of people and sort them in a subarray (S). Since there's no other groups of people taller than them, therefore each guy's index will be just as same as his k value.
     * 2.For 2nd tallest group (and the rest), insert each one of them into (S) by k value. So on and so forth.
     * E.g.
     * input: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * subarray after step 1: [[7,0], [7,1]]
     * subarray after step 2: [[7,0], [6,1], [7,1]]
     * ...
     * It's not the most concise code, but I think it well explained the concept.
     *
     * Thinking：
     * 1.一个二维向量的排序问题。在某一维相等的情况下，比较另一个维度，可初步排序；再合并排序。
     *
     * @param people
     * @return
     */
    public static int[][] reconstructQueue_1(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o2[0] - o1[0];
            }
        });

        List<int[]> res = new LinkedList<>();
        for (int[] t : people) {
            res.add(t[1], t);
        }

        return res.toArray(new int[people.length][]);
    }

    public static void main(String[] args) {
        do_func(new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}}, new int[][]{{5, 0}, {7, 0}, {5, 2}, {6, 1}, {4, 4}, {7, 1}});
        do_func(new int[][]{{6, 0}, {5, 0}, {4, 0}, {3, 2}, {2, 2}, {1, 4}}, new int[][]{{4, 0}, {5, 0}, {2, 2}, {3, 2}, {1, 4}, {6, 0}});

    }

    private static void do_func(int[][] people, int[][] expected) {
        int[][] ret = reconstructQueue(people);
        ArrayUtils.printIntArray(ret);
        System.out.println(ArrayUtils.isSame(ret, expected));
        System.out.println("-----------");
    }
}
