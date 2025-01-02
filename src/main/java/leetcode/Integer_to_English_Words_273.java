package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 273. Integer to English Words
 * Hard
 * -----------------------------
 * Convert a non-negative integer num to its English words representation.
 * <p>
 * Example 1:
 * Input: num = 123
 * Output: "One Hundred Twenty Three"
 * <p>
 * Example 2:
 * Input: num = 12345
 * Output: "Twelve Thousand Three Hundred Forty Five"
 * <p>
 * Example 3:
 * Input: num = 1234567
 * Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * <p>
 * Constraints:
 * 0 <= num <= 2^31 - 1
 */
public class Integer_to_English_Words_273 {
    public static String numberToWords(int num) {
        return numberToWords_r3_1(num);
    }

    /**
     * round 3
     * Score[3] Lower is harder
     * <p>
     * Thinking
     * 1. 分段法
     * 1.1. 从右向左，每3个数字为一段，分别对应[billion,million,thousand]，最多4段
     * 1.2. 每段最多3位数，分别是1位数、2位数、3位数。
     * 1位数采用枚举；2位数时，分别对十位和个位采用枚举；3位数时，分别对百、个为采用枚举。
     * 需要注意[10~19]需要单独考虑。
     * 000时忽略该段
     * 2. review 跟简洁的方法
     * https://leetcode.com/problems/integer-to-english-words/solutions/70625/my-clean-java-solution-very-easy-to-understand/
     * <p>
     * 验证通过：
     * Runtime 2 ms Beats 91.52%
     * Memory 41.91 MB Beats 79.57%
     *
     * @param num
     * @return
     */
    public static String numberToWords_r3_1(int num) {
        if (num == 0) return "Zero";
        //从右向左把输入划分为4段，每段3个数字
        int n1 = 0, n2 = 0, n3 = 0, n4 = 0;
        n1 = num % 1000;//thousand以内
        n2 = num / 1000 % 1000;//thousand
        n3 = num / 1000000 % 1000;//million
        n4 = num / 1000000000;//billion
        StringBuilder res = new StringBuilder();
        String billion = getWords(n4);
        if (billion != null && billion.length() != 0) {
            res.append(billion + "Billion ");
        }
        String million = getWords(n3);
        if (million != null && million.length() != 0) {
            res.append(million + "Million ");
        }
        String thousand = getWords(n2);
        if (thousand != null && thousand.length() != 0) {
            res.append(thousand + "Thousand ");
        }
        res.append(getWords(n1));
        return res.toString().trim();
    }

    private static String getWords(int num) {
        if (num <= 0 || num > 999) return "";
        String[] singles = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] teens = new String[]{"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = new String[]{"", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        StringBuilder res = new StringBuilder();
        //百位数
        if (100 <= num) {
            res.append(singles[num / 100 - 1]);
            res.append(" Hundred ");
            num %= 100;
        }

        if (10 <= num && num <= 19) {//10~19
            res.append(teens[num % 10]);
            res.append(" ");
        } else {
            if (20 <= num) {//十位数
                res.append(tens[num / 10 - 1]);
                res.append(" ");
                num %= 10;
            }
            if (0 < num) {//个位数
                res.append(singles[num - 1]);
                res.append(" ");
            }
        }

        return res.toString();
    }

    /**
     * 思考：
     * 1.输入数字用千分符分割。穷举所有可能性，并建立映射表。
     * 2.从后向前计算的方案更优，无需提前计算高位数量级。
     * 3.任务拆解：数字映射，数量级计算，分割数字(千分符，每3个数字为一个unit)
     * <p>
     * 算法：
     * 1.设places[]={"","Thousand","Million","Billion","Trillion"}，r为places的下标
     * 设nun2Str={"1":"One","2":"Two",..,"10":"Ten","11":"Eleven",..,"20":"Twenty",..,"90";"Ninety","100":"Hundred"}
     * 2.从后向前遍历。
     * 2.1.每三个数字(千分符)为一个unit
     * 2.2.IF unit>0，THEN res.append(places[r]),r++
     * 2.3.计算unit，并输出字符串
     * 3.反转res
     * <p>
     * 计算3位数字的算法：
     * 1.边界判断，小于等于0或者大于等于1000，返回空串
     * 2.提取最低两位数字，设为tens
     * 3.IF 0<tens<20, THEN 输出码表对应的值
     * 4.IF tens是10的倍数, THEN 输出码表对应的值
     * 5.IF 20<tens<100,THEN 先输出个位,再输出十位
     * 6.提取高位(百位)数字,THEN 输出"Handred",输入码表对应的值
     * 7.反转
     * <p>
     * 验证通过:
     * Runtime: 5 ms, faster than 82.46% of Java online submissions for Integer to English Words.
     * Memory Usage: 42.2 MB, less than 78.00% of Java online submissions for Integer to English Words.
     *
     * @param num
     * @return
     */
    public static String numberToWords_1(int num) {
        if (num == 0) return "Zero";
        StringBuilder res = new StringBuilder();
        String[] places = new String[]{"", "Thousand", "Million", "Billion", "Trillion"};
        List<String> list = new ArrayList<String>();
        int j = 0;
        //按千分位依次计算，每次计算三位数字
        while (num > 0) {
            List<String> unitRes = convertUnit(num % 1000);
            if (unitRes.size() > 0) {
                if (j > 0) list.add(places[j]);//低于1000的数字不加千分位单词
                list.addAll(unitRes);
            }
            j++;
            num /= 1000;
        }

        //反转并转化成字符串
        for (int i = list.size() - 1; i >= 0; i--) {
            res.append(list.get(i));
            if (i > 0) {
                res.append(" ");
            }
        }

        return res.toString();
    }

    private static List<String> convertUnit(int n) {
        List<String> res = new ArrayList<>();
        if (1 > n || n > 1000) return res;
        //提取最低两位数字，低位数字可能为0
        int tens = n % 100;
        if (tens > 0) {
            //处理输出为单个单词的情况
            if ((1 <= tens && tens <= 20) || tens % 10 == 0) {
                res.add(convertTens2Str(tens));
            } else {
                res.add(convertTens2Str(tens % 10));//先加入个位
                res.add(convertTens2Str(tens / 10 * 10));//再加入十位
            }
        }
        //提取高位（百位）数字
        int hundreds = n / 100;
        if (hundreds > 0) {
            res.add("Hundred");
            res.add(convertTens2Str(hundreds));
        }
        return res;
    }

    private static String convertTens2Str(int n) {
        if (1 > n || n > 100) return "";
        String res;
        switch (n) {
            case 1:
                res = "One";
                break;
            case 2:
                res = "Two";
                break;
            case 3:
                res = "Three";
                break;
            case 4:
                res = "Four";
                break;
            case 5:
                res = "Five";
                break;
            case 6:
                res = "Six";
                break;
            case 7:
                res = "Seven";
                break;
            case 8:
                res = "Eight";
                break;
            case 9:
                res = "Nine";
                break;
            case 10:
                res = "Ten";
                break;
            case 11:
                res = "Eleven";
                break;
            case 12:
                res = "Twelve";
                break;
            case 13:
                res = "Thirteen";
                break;
            case 14:
                res = "Fourteen";
                break;
            case 15:
                res = "Fifteen";
                break;
            case 16:
                res = "Sixteen";
                break;
            case 17:
                res = "Seventeen";
                break;
            case 18:
                res = "Eighteen";
                break;
            case 19:
                res = "Nineteen";
                break;
            case 20:
                res = "Twenty";
                break;
            case 30:
                res = "Thirty";
                break;
            case 40:
                res = "Forty";
                break;
            case 50:
                res = "Fifty";
                break;
            case 60:
                res = "Sixty";
                break;
            case 70:
                res = "Seventy";
                break;
            case 80:
                res = "Eighty";
                break;
            case 90:
                res = "Ninety";
                break;
            default:
                res = "";
        }
        return res;
    }

    public static void main(String[] args) {
        do_func(123, "One Hundred Twenty Three");
        do_func(12345, "Twelve Thousand Three Hundred Forty Five");
        do_func(1234567, "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven");
        do_func(Integer.MAX_VALUE, "Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven");
        do_func(0, "Zero");
        do_func(70, "Seventy");
        do_func(100, "One Hundred");
        do_func(101, "One Hundred One");
        do_func(1000000, "One Million");
        do_func(1000000000, "One Billion");
        do_func(1000000001, "One Billion One");
        do_func(14, "Fourteen");
    }

    private static void do_func(int n, String expected) {
        System.out.println(n);
        String ret = numberToWords(n);
        System.out.println(ret);
        System.out.println(expected.equals(ret));
        System.out.println("--------------");
    }
}
