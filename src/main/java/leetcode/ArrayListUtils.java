package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArrayListUtils {
    public static boolean isSame(List list1, List list2) {
        if (list1 == null && list2 == null) return false;
        if (list1 == null || list2 == null) return false;
        if (list1.size() != list2.size()) return false;
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

    public static boolean isSame(List<List<Integer>> list, int[][] arr) {
        boolean same = true;
        if (list.size() == arr.length) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).size() != arr[i].length) {
                    same = false;
                    break;
                }
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (list.get(i).get(j) != arr[i][j]) {
                        same = false;
                        break;
                    }
                }
                if (!same) break;
            }
        } else {
            same = false;
        }
        return same;
    }

    public static boolean isSame(List<List<String>> list, String[][] arr) {
        boolean same = true;
        if (list != null && arr.length == list.size()) {
            for (int i = 0; i > arr.length; i++) {
                if (!Arrays.equals(arr[i], list.get(i).toArray())) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        return same;
    }

    public static boolean sortedThenCompare(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) return false;
        if (list1 == null || list2 == null) return false;
        if (list1.size() != list2.size()) return false;
        Comparator<Integer> c = (o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        };
        list1.sort(c);
        list2.sort(c);
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) return false;
        }
        return true;
    }

//    public static List<List<Integer>> arrayToList(Integer[][] input) {
//        if (input == null) return null;
//        List<List<Integer>> ret = new ArrayList<>();
//        for (int i = 0; i < input.length; i++) {
//            ret.add(Arrays.asList(input[i]));
//        }
//        return ret;
//    }

    public static <T> List<List<T>> arrayToList(T[][] input) {
        if (input == null) return null;
        List<List<T>> ret = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            ret.add(Arrays.asList(input[i]));
        }
        return ret;
    }

    public static List<List<Integer>> arrayToList(int[][] input) {
        if (input == null) return null;
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            List<Integer> t = new ArrayList<>();
            for (int j = 0; j < input[i].length; j++) {
                t.add(input[i][j]);
            }
            ret.add(t);
        }
        return ret;
    }

    public static void main(String args[]) {
        Integer[][] input = new Integer[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        List<List<Integer>> r1 = arrayToList(input);
    }
}
