package leetcode;

public class ArrayUtils {
    public static void printIntArray(int[] nums) {
        if (nums == null) return;
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    public static boolean isSame(int[] nums, int[] expected) {
        if (nums == null || expected == null) return false;
        boolean same = true;
        if (nums.length == expected.length) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != expected[i]) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        return same;
    }

    public static void isSameThenPrintln(int[] nums, int[] expected) {
        System.out.println(isSame(nums, expected));
    }
}
