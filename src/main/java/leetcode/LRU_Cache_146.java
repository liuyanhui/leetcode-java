package leetcode;

import java.util.HashMap;

/**
 * 146. LRU Cache
 * Medium
 * -----------------------------------
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 *
 * Implement the LRUCache class:
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 *
 * The functions get and put must each run in O(1) average time complexity.
 *
 * Example 1:
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 *
 * Constraints:
 * 1 <= capacity <= 3000
 * 0 <= key <= 10^4
 * 0 <= value <= 10^5
 * At most 2 * 10^5 calls will be made to get and put.
 */
public class LRU_Cache_146 {
    /**
     * 验证通过：
     * Runtime: 110 ms, faster than 19.55% of Java online submissions for LRU Cache.
     * Memory Usage: 124.4 MB, less than 61.81% of Java online submissions for LRU Cache.
     *
     */
    static class LRUCache {
        int capacity = 0;
        HashMap<Integer, Node> cache;
        //FIXME 这里可以直接用java自带的Deque = new LinkedList()
        Node head;
        Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();
            //FIXME 如果head和tail都是dumb节点，那么deleteNode和add2Tail就不需要那么复杂的逻辑了。
            //review 时需要注意的部分
            //FIXME 金矿 套路 链表操作必须有dumb节点表示dumbHead，双向链表的话还要有dumbTail
            head = null;
            tail = null;
        }

        public int get(int key) {
            Node t = cache.get(key);
            if (t == null) {
                return -1;
            } else {
                //处理队列
                resortQueue(t);
                return t.val;
            }
        }

        public void put(int key, int value) {
            Node t = cache.get(key);
            //是否存在
            if (t == null) {
                //是否满了
                if (cache.size() == capacity) {
                    cache.remove(head.key);
                    deleteNode(head);
                }
                Node n = new Node(key, value);
                //防止key不变，value改变的情况
                cache.put(key, n);
                add2Tail(n);
            } else {
                t.val = value;
                resortQueue(t);
            }
        }

        private void resortQueue(Node t) {
            if (head == tail) return;
            deleteNode(t);
            add2Tail(t);
        }

        private void deleteNode(Node t) {
            if (head == null) return;
            if (head == tail) {
                head = null;
                tail = null;
            } else if (head == t) {
                head = t.next;
                head.prev = null;
                t.next = null;
            } else if (tail == t) {
                tail = t.prev;
                t.prev = null;
                tail.next = null;
            } else {
                t.prev.next = t.next;
                t.next.prev = t.prev;
                t.next = null;
                t.prev = null;
            }
        }

        private void add2Tail(Node n) {
            if (tail == null) {
                tail = n;
                tail.next = null;
                head = tail;
            } else {
                tail.next = n;
                n.prev = tail;
                tail = n;
            }
        }

    }

    static class Node {
        int key;
        int val;
        Node prev;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

    public static void main(String[] args) {
        int ret = -99999;
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        ret = lRUCache.get(1);    // return 1
        System.out.println(ret);
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1,3=3}
        ret = lRUCache.get(2);    // returns -1 (not found)
        System.out.println(ret);
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        ret = lRUCache.get(1);    // return -1 (not found)
        System.out.println(ret);
        ret = lRUCache.get(3);    // return 3
        System.out.println(ret);
        ret = lRUCache.get(4);    // return 4
        System.out.println(ret);
    }

}


