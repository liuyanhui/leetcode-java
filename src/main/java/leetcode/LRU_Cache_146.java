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

    class LRUCache {
        int capacity = 0;
        HashMap<Integer, Node> cache;
        //FIXME 这里可以直接用java自带的Deque = new LinkedList()
        Node head;
        Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();
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
                    cache.remove(key);
                    deleteList(head);
                }
                Node n = new Node(value);
                cache.put(key, n);
                add2Tail(n);
            } else {
                resortQueue(t);
            }
        }

        private void resortQueue(Node t) {
            if (head == tail) return;
            deleteList(t);
            add2Tail(t);
        }

        private void deleteList(Node t) {
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

    class Node {
        int val;
        Node prev;
        Node next;

        public Node(int v) {
            val = v;
        }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
}


