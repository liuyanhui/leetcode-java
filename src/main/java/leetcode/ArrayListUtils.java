package leetcode;

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

}
