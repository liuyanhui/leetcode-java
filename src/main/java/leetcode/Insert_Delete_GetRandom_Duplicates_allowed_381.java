package leetcode;

import java.util.*;

/**
 * 381. Insert Delete GetRandom O(1) - Duplicates allowed
 * Hard
 * -----------------------
 * Implement the RandomizedCollection class:
 * RandomizedCollection() Initializes the RandomizedCollection object.
 * bool insert(int val) Inserts an item val into the multiset if not present. Returns true if the item was not present, false otherwise.
 * bool remove(int val) Removes an item val from the multiset if present. Returns true if the item was present, false otherwise. Note that if val has multiple occurrences in the multiset, we only remove one of them.
 * int getRandom() Returns a random element from the current multiset of elements (it's guaranteed that at least one element exists when this method is called). The probability of each element being returned is linearly related to the number of same values the multiset contains.
 *
 * You must implement the functions of the class such that each function works in average O(1) time complexity.
 *
 * Example 1:
 * Input
 * ["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
 * [[], [1], [1], [2], [], [1], []]
 * Output
 * [null, true, false, true, 2, true, 1]
 *
 * Explanation
 * RandomizedCollection randomizedCollection = new RandomizedCollection();
 * randomizedCollection.insert(1);   // return True. Inserts 1 to the collection. Returns true as the collection did not contain 1.
 * randomizedCollection.insert(1);   // return False. Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
 * randomizedCollection.insert(2);   // return True. Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
 * randomizedCollection.getRandom(); // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
 * randomizedCollection.remove(1);   // return True. Removes 1 from the collection, returns true. Collection now contains [1,2].
 * randomizedCollection.getRandom(); // getRandom should return 1 and 2 both equally likely.
 *
 * Constraints:
 * -2^31 <= val <= 2^31 - 1
 * At most 2 * 10^5 calls will be made to insert, remove, and getRandom.
 * There will be at least one element in the data structure when getRandom is called.
 */
public class Insert_Delete_GetRandom_Duplicates_allowed_381 {
    /**
     * 参考思路：Insert_Delete_GetRandom_380，略复杂一点，思路相同。
     *
     * 验证通过：
     * Runtime: 63 ms, faster than 15.54% of Java .
     * Memory Usage: 113.7 MB, less than 6.60% of Java .
     */
    static class RandomizedCollection {
        Map<Integer, Set<Integer>> map;
        List<Integer> array;

        /** Initialize your data structure here. */
        public RandomizedCollection() {
            map = new HashMap<>();
            array = new ArrayList<>();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            boolean exsited = map.containsKey(val) && !map.get(val).isEmpty();
            map.computeIfAbsent(val, v -> new HashSet<>());
            array.add(val);
            map.get(val).add(array.size() - 1);
            return !exsited;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if (!map.containsKey(val) || map.get(val).isEmpty()) return false;
            int lastOne = array.get(array.size() - 1);
            if (lastOne != val) {
                //获取set中的第一个
                int valIdx = map.get(val).stream().findFirst().get();
                //下面的这行代码可以替换上面的一行
//                int valIdx = map.get(val).iterator().next();

                //交换元素
                //先把旧的下标删除
                map.get(lastOne).remove(array.size() - 1);
                //再把新的下标插入
                map.get(lastOne).add(valIdx);

                map.get(val).remove(valIdx);
                array.set(valIdx, lastOne);
            }
            int t = array.size() - 1;
            array.remove(t);
            map.get(val).remove(t);
            return true;
        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return array.get(new Random().nextInt(array.size()));
        }
    }


    public static void main(String[] args) {
        RandomizedCollection randomizedSet = new RandomizedCollection();
//        System.out.println(randomizedSet.insert(1));
//        System.out.println(randomizedSet.insert(1));
//        System.out.println(randomizedSet.insert(2));
//        System.out.println(randomizedSet.getRandom());
//        System.out.println(randomizedSet.remove(1));
//        System.out.println(randomizedSet.getRandom());

//        System.out.println("------------------");
//        randomizedSet = new RandomizedCollection();
//        System.out.println(randomizedSet.insert(1));
//        System.out.println(randomizedSet.remove(1));
//        System.out.println(randomizedSet.insert(1));

        //["RandomizedCollection","insert","insert","insert","insert","insert","insert","remove","remove","remove","remove","remove","insert","remove","remove","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom","getRandom"]
        //[[],[10],[10],[20],[20],[30],[30],[10],[20],[20],[10],[30],[40],[30],[30],[],[],[],[],[],[],[],[],[],[]]
        System.out.println("------------------");
        randomizedSet = new RandomizedCollection();
        System.out.println(randomizedSet.insert(10));
        System.out.println(randomizedSet.insert(10));
        System.out.println(randomizedSet.insert(20));
        System.out.println(randomizedSet.insert(20));
        System.out.println(randomizedSet.insert(30));
        System.out.println(randomizedSet.insert(30));
        System.out.println(randomizedSet.remove(10));
        System.out.println(randomizedSet.remove(20));
        System.out.println(randomizedSet.remove(20));
        System.out.println(randomizedSet.remove(10));
        System.out.println(randomizedSet.remove(30));
        System.out.println(randomizedSet.insert(40));
        System.out.println(randomizedSet.remove(30));
        System.out.println(randomizedSet.remove(30));
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
        System.out.println(randomizedSet.getRandom());
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
