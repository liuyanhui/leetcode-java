package leetcode;

import java.util.*;

/**
 * 347. Top K Frequent Elements
 * Medium
 * ----------------
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class Top_K_Frequent_Elements_347 {
    public static int[] topKFrequent(int[] nums, int k) {
        return topKFrequent_r3_1(nums, k);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     *
     * Thinking
     * 1. naive solution
     * 先统计每个数的出现次数，再根据出现次数排序，最后返回结果。
     * Time Complexity: O(NlogN)
     * Space Complexity: O(N)
     * 2. 当k较小时，无需排序，执行k*N次查找即可。
     * 3. 先统计，再使用bucket sort
     *
     * 验证通过：
     * Runtime 14 ms Beats 58.76%
     * Memory 48.48 MB Beats 71.55%
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] topKFrequent_r3_1(int[] nums, int k) {
        //统计次数
        Map<Integer,Integer> frequencyMap = new HashMap<>();
        for(int n:nums){
            frequencyMap.computeIfAbsent(n,v->0);
            frequencyMap.put(n,frequencyMap.get(n)+1);
        }
        //bucket sort
        List<Integer>[] buckets = new List[nums.length+1];
        for(int key : frequencyMap.keySet()){
            if(buckets[frequencyMap.get(key)]==null){
                buckets[frequencyMap.get(key)] = new ArrayList<>();
            }
            buckets[frequencyMap.get(key)].add(key);
        }
        //提起top k
        int[] res = new int[k];
        int m = 0;
        for(int i=buckets.length-1;i>=0 && m<k;i--){
            if(buckets[i]==null) continue;
            for(int j = 0;j<buckets[i].size();j++){
                res[m++]=buckets[i].get(j);
            }
        }
        return res;
    }
    /**
     * review round 2
     *
     * 参考topKFrequent_2()的思路
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] topKFrequent_3(int[] nums, int k) {
        if (nums == null || nums.length == 0) return null;
        int[] res = new int[k];
        //统计数字出现的次数
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) {
            counts.computeIfAbsent(n, v -> 0);
            counts.put(n, counts.get(n) + 1);
        }
        //根据出现次数加入Bucket中
        List<Integer>[] buckets = new List[nums.length+1];
        for (int n : counts.keySet()) {
            int f = counts.get(n);
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(n);
        }
        //获取frequency中最大的前k个元素
        int idx = 0;
        for (int i = buckets.length - 1; i >= 0; i--) {
            if (buckets[i] != null) {
                for (int n : buckets[i]) {
                    res[idx++] = n;
                }
            }
            if (idx == k) break;
        }
        return res;
    }

    /**
     * 类似bucket排序的思路
     * 参考思路：
     * https://leetcode.com/problems/top-k-frequent-elements/discuss/81602/Java-O(n)-Solution-Bucket-Sort
     *
     * 金矿：Map中根据value排序时，可以使用bucket排序，bucket的下标就是value的值，形如：bucket[value]=key
     *
     * 验证通过：
     * Runtime: 7 ms, faster than 98.79% of Java online submissions for Top K Frequent Elements.
     * Memory Usage: 41.3 MB, less than 87.95% of Java online submissions for Top K Frequent Elements.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] topKFrequent_2(int[] nums, int k) {
        //统计frequency
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int n : nums) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }
        //根据frequency 加入bucket
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int key : countMap.keySet()) {
            int frequency = countMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }
        //提取结果，按照bucket从后向前提取
        int[] ret = new int[k];
        int idx = 0;
        for (int i = bucket.length - 1; i >= 0 && idx < k; i--) {
            if (bucket[i] != null) {
                for (int n : bucket[i]) {
                    ret[idx++] = n;
                }
            }
        }
        return ret;
    }

    /**
     * 思路如下：
     * 1.先统计次数，
     * 2.再根据次数排序，
     * 3.最后根据排序后的数据返回前k大次数对应的数字
     *
     * 验证通过：
     * Runtime: 12 ms, faster than 34.36% of Java online submissions for Top K Frequent Elements.
     * Memory Usage: 41.8 MB, less than 47.34% of Java online submissions for Top K Frequent Elements.
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] topKFrequent_1(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            //approach 1
            int t = map.computeIfAbsent(n, v -> 0);
            map.put(n, t + 1);

            //approach 2
//            map.put(n, map.getOrDefault(n, 0) + 1);

            //approach 3
//            if (map.containsKey(n)) {
//                map.put(n, map.get(n) + 1);
//            } else {
//                map.put(n, +1);
//            }
        }

        List<Frequent> list = new ArrayList<>();
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            list.add(new Frequent(key, map.get(key)));
        }

        Collections.sort(list, new Comparator<Frequent>() {
            @Override
            public int compare(Frequent o1, Frequent o2) {
                if (o1.count < o2.count) {
                    return 1;
                } else if (o1.count > o2.count) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        int[] ret = new int[k];
        for (int i = 0; i < k; i++) {
            ret[i] = list.get(i).num;
        }
        return ret;
    }

    static class Frequent {
        public int num;
        public int count;

        Frequent(int n, int c) {
            num = n;
            count = c;
        }
    }


    public static void main(String[] args) {
        do_func(new int[]{1, 1, 1, 2, 2, 3}, 2, new int[]{1, 2});
        do_func(new int[]{1}, 1, new int[]{1});
    }

    private static void do_func(int[] nums, int k, int[] expected) {
        int[] ret = topKFrequent(nums, k);
        ArrayUtils.printlnIntArray(ret);
        ArrayUtils.isSameThenPrintln(ret, expected);
        System.out.println("--------------");
    }
}
