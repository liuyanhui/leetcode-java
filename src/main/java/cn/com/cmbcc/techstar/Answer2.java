package cn.com.cmbcc.techstar;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Answer2 {
    public static List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        Arrays.sort(num);
        int left = 0, right = num.length - 1;
        while (right > 0 && num[right] >= 0) {
            while (left < right && num[left] <= 0) {
                int sumAB = num[left] + num[right];
                for (int mid = left + 1; mid < right; mid++) {
                    if (num[mid] + sumAB == 0) {
                        results.add(Arrays.asList(new Integer[]{num[left], num[mid], num[right]}));
                        break;
                    }
                }
                int preP = num[left];
                while (left < right && num[left] == preP)
                    left++;
            }
            left = 0;
            int preQ = num[right];
            while (right > 0 && num[right] == preQ)
                right--;
        }
        return results;
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            int[] array = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                int tmpNum = Integer.parseInt(args[i]);
                array[i] = tmpNum;
            }
            List<List<Integer>> results = threeSum(array);
            System.out.println(JSON.toJSONString(results));
        }
    }
}
