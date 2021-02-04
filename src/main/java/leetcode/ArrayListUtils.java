package leetcode;

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
}
