package leetcode;

import java.util.*;

/**
 * 380. Insert Delete GetRandom O(1)
 * Medium
 * -----------------------
 * Implement the RandomizedSet class:
 * RandomizedSet() Initializes the RandomizedSet object.
 * bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
 * bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false otherwise.
 * int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
 * You must implement the functions of the class such that each function works in average O(1) time complexity.
 *
 * Example 1:
 * Input
 * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
 * [[], [1], [2], [2], [], [1], [2], []]
 * Output
 * [null, true, false, true, 2, true, false, 2]
 *
 * Explanation
 * RandomizedSet randomizedSet = new RandomizedSet();
 * randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 * randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
 * randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
 * randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
 * randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
 * randomizedSet.insert(2); // 2 was already in the set, so return false.
 * randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.
 *
 *
 * Constraints:
 * -2^31 <= val <= 2^31 - 1
 * At most 2 * 10^5 calls will be made to insert, remove, and getRandom.
 * There will be at least one element in the data structure when getRandom is called.
 */
public class Insert_Delete_GetRandom_380 {
    /**
     * The List is used to store numbers and serve the getRandom() method. The Map contains the mapping between the value and its index in the ArrayList. The Map helps to check whether a value is already inserted or not. The main trick is when you remove a value. ArrayList's remove method is O(n) if you remove from random location. To overcome that, we swap the values between (randomIndex, lastIndex) and always remove the entry from the end of the list. After the swap, you need to update the new index of the swapped value (which was previously at the end of the list) in the map.
     *
     * 思路描述：
     * insert()很容易保证O(1)的复杂度使用hashtable即可；
     * remove()使用hashtable也可以保证O(1)；
     * getRandom()无法通过hashtable保证O(1)，但是使用array可以保证O(1)；
     * 所以需要通过hashtable和array组合实现；
     * insert()和remove()都会修改hashtable和array，getRandom()只会读取array，所以只能在insert()和remove()中处理array；
     * insert()可以顺序或随机操作array，而remove()是必然随机操作；
     *
     * 参考思路：
     * https://leetcode.com/problems/insert-delete-getrandom-o1/discuss/85401/Java-solution-using-a-HashMap-and-an-ArrayList-along-with-a-follow-up.-(131-ms)
     * https://leetcode.com/problems/insert-delete-getrandom-o1/discuss/85425/Java-Solution-(Beats-99.20)-Using-HashMap-and-ArrayList-with-Explanation
     *
     * 验证通过：
     * Runtime: 30 ms, faster than 42.23% of Java .
     * Memory Usage: 83.6 MB, less than 99.47% of Java .
     *
     */
    static class RandomizedSet {
        List<Integer> list;
        Map<Integer, Integer> map;

        /** Initialize your data structure here. */
        public RandomizedSet() {
            list = new ArrayList<>();
            map = new HashMap<>();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            list.add(val);
            map.put(val, list.size() - 1);
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;
            int idx = map.get(val);
            if (idx < list.size() - 1) {
                int lastOne = list.get(list.size() - 1);
                list.set(idx, lastOne);
                map.put(lastOne, idx);
            }
            list.remove(list.size() - 1);
            map.remove(val);
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return list.get(new Random().nextInt(list.size()));
        }

    }

    public static void main(String[] args) {
        //["RandomizedSet","insert","remove","insert","getRandom","remove","insert","getRandom"]
        //[[],[-1],[-2],[-2],[],[-1],[-2],[]]
        RandomizedSet randomizedSet = new RandomizedSet();
        randomizedSet.insert(-1);
        randomizedSet.remove(-2);
        randomizedSet.insert(-2);
        System.out.println(randomizedSet.getRandom());
        randomizedSet.remove(-1);
        randomizedSet.insert(-2);
        System.out.println(randomizedSet.getRandom());
    }

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
}
