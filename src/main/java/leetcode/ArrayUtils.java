package leetcode;

import java.util.stream.Stream;

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
        System.out.print("]");
    }

    public static void printIntArray(int[][] nums) {
        if (nums == null) return;
        System.out.print("[");
        for (int i = 0; i < nums.length; i++) {
            printIntArray(nums[i]);
        }
        System.out.println("]");
    }

    public static void printIntArray(char[] nums) {
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
    public static void printIntArray(String[] arr) {
        if (arr == null) return;
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
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

    public static boolean isSame(int[][] nums, int[][] expected) {
        if (nums == null || expected == null) return false;
        boolean same = true;
        if (nums.length == expected.length) {
            for (int i = 0; i < nums.length; i++) {
                if (!isSame(nums[i], expected[i])) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        return same;
    }

    public static boolean isSame(String[] nums, String[] expected) {
        if (nums == null || expected == null) return false;
        boolean same = true;
        if (nums.length == expected.length) {
            for (int i = 0; i < nums.length; i++) {
                if (!nums[i].equals(expected[i])) {
                    same = false;
                    break;
                }
            }
        } else {
            same = false;
        }
        return same;
    }

    public static boolean isSame(char[] nums, char[] expected) {
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

    public static void isSameThenPrintln(String[] nums, String[] expected) {
        System.out.println(isSame(nums, expected));
    }

    public static void isSameThenPrintln(char[] nums, char[] expected) {
        System.out.println(isSame(nums, expected));
    }


    public static void main(String[] args) {
        System.out.println(Integer.bitCount(15));
        System.out.println(Integer.toBinaryString(15));
        System.out.println(Integer.toBinaryString(4));
        System.out.print("-------");
        Stream.of(Integer.toBinaryString(4).toCharArray()).forEach(s -> System.out.print(s));
        System.out.println("-------");
        System.out.println(Integer.toBinaryString(4).toCharArray()[0]);
        System.out.println(Integer.toBinaryString(4).toCharArray()[1]);
        System.out.println("======");
        System.out.println(Integer.toBinaryString(0));
        System.out.println("^^^^^^^^^");
        String str = "fghjk";
        Stream.of(str.toCharArray()).forEach(s -> System.out.print(s));
        System.out.println("");
        char[] ttt = str.toCharArray();
        System.out.print("[");
        for (int i = 0; i < ttt.length; i++) {
            System.out.print(ttt[i]);
        }
        System.out.println("]");
        System.out.println("ttt[0]=" + ttt[0]);
        System.out.println("str.substring(0,1)=" + str.substring(0, 1));
        System.out.println("ttt[1]=" + ttt[1]);
        System.out.println("ttt[2]=" + ttt[2]);
    }
}
