package CCProgrammingContest.y2023;

import java.util.*;

public class A_B_Problem {
    public static void main(String[] args) {
        T388011();
    }

    private static void T388038() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String in = cin.nextLine();
        }
        cin.close();
    }

    /**
     * 题目描述
     * 为遵循国家和行业数据安全管理的要求，按照相迎接的数据分类分级管理规范要求，对系统日志中存在有客户敏感信息进行脱敏处理从而保证个人信息安全成为一项非常重要工作；那么请根据脱敏规则要求，对日志中所涉及的身份证号、银行卡号进行相应的脱敏处理，规则如下：
     *
     * 身份证号：屏蔽后6位用6个*代替，保留12位（二代身份证）；注意身份证号前6位为区域代码，中间8位为出生日期，身份证最后一位可能为X或x，例如：
     * 432322198711230421--->432322198711******
     *
     * 银行卡号：所有16位长度的数字均视为银行卡号，保留前6位和最后4位，中间6位用6个*代替，本题只需要考虑16位的银行卡，例如：
     * 6226220149841234 --->622622******1234
     *
     * 注： 本题输入不考虑换行，所有用例均只有一行， 特别提示：一个长度不为18位的数字串里即便包含符合身份证条件的内容，也不能识别为身份证，同时1900年之前或2100年之后的出生日期也不会认为是合法的日期。 同样类似的 一个长度不为16位的数字也不应该识别为卡号。
     *
     * 输入格式
     * 一行文本，例如 432322198711230421是证件号需要脱敏，62262201498412344不是卡号，不需要进行脱敏。
     * 输出格式
     * 一行文本，例如 432322198711230421是证件号需要脱敏，62262201498412344不是卡号，不需要进行脱敏。
     *
     * 输入输出样例
     * 输入 #1复制
     * 本次交易流水号110108100012345678，客户身份证号为432322198711230421，卡号为6226220149841234，需要进行脱敏。
     * 输出 #1复制
     * 本次交易流水号110108100012345678，客户身份证号为432322198711******，卡号为622622******1234，需要进行脱敏。
     * 说明/提示
     * 身份证号码不仅仅考虑18位位长，还需要考虑首位区位码不为0，以及生日的日期范围最多为19000101-20991231
     *
     * 我国地区代码分别如下（编程时需要考虑区位码首位不为0和9，也就是首位为0/9，或者不是正确生日的18位数字不能视为身份证号，不需要脱敏）
     *
     * Thinking:
     * 需要脱敏的特征：卡号和证件号。
     * 卡号：
     *      特征：16位数字
     *      脱敏规则：保留前6位和最后4位，中间6位用6个*代替
     * 证件号：
     *      特征：18位数字，或最后一位为X或x；6位区域代码【首位不为0和9】；中间8位为出生日期【在19000101和20991231之间】
     *      脱敏规则：后6位用6个*代替
     *
     * 用例：
     * 本次交易流水号110108100012345678，客户身份证号为432322198711230421，卡号为6226220149841234，需要进行脱敏。
     * 证件号中日期不应大于31，432322198711320421不是证件号，不需要进行脱敏。
     *
     */
    private static void T388011() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String in = cin.nextLine();
            StringBuilder res = new StringBuilder();
            //记录待替换的字符下标
            Set<Integer> delIndex = new HashSet<>();
            //提取数字
            int beg = 0;
            int end = 0;
            while (end < in.length()) {
                if (Character.isDigit(in.charAt(end)) && end < in.length() - 1) {
                    end++;
                    continue;
                }

                //计算卡号
                if (end - beg == 16) {
                    String card = in.substring(beg, beg + 16);
                    if (isCardNum(card)) {
                        //获取待替换的字符下标
                        for (int i = 0; i < 6; i++) {
                            delIndex.add(beg + 6 + i);
                        }
                    }
                } else if (end - beg == 17 || end - beg == 18) {//计算证件号
                    String id = in.substring(beg, beg + 18);
                    if (isIdNum(id)) {
                        //获取待替换的字符下标
                        for (int i = 0; i < 6; i++) {
                            delIndex.add(beg + 12 + i);
                        }
                    }
                }
                end++;
                beg = end;
            }
            //脱敏
            for (int i = 0; i < in.length(); i++) {
                if (delIndex.contains(i)) {
                    res.append("*");
                } else {
                    res.append(in.charAt(i));
                }
            }
            System.out.println(res.toString());
        }
        cin.close();
    }

    private static boolean isCardNum(String card) {
        if (card != null && card.length() == 16) {
            for (int i = 0; i < card.length(); i++) {
                if (!Character.isDigit(card.charAt(i))) return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean isIdNum(String id) {
        if (id != null && id.length() == 18) {
            for (int i = 0; i < 17; i++) {
                if (!Character.isDigit(id.charAt(i))) return false;
            }
            if (id.charAt(17) != 'X' && id.charAt(17) != 'x' && !Character.isDigit(id.charAt(17)))
                return false;
            if (id.charAt(0) == '0' || id.charAt(0) == '9') return false;
            int birth = Integer.valueOf(id.substring(6, 14));
            if (birth < 19000101 || 20991231 < birth) return false;
            int year = Integer.valueOf(id.substring(6, 10));
            if (year < 1900 || 2099 < year) return false;
            int month = Integer.valueOf(id.substring(10, 12));
            if (month < 1 || 12 < month) return false;
            int day = Integer.valueOf(id.substring(12, 14));
            if (day < 1 || 31 < day) return false;

            return true;
        } else {
            return false;
        }
    }

    /**
     * 题目描述
     * 本次比赛大赛，需要评选大赛排名。大赛得分为0分-100分（分数精确到小数点后两位)，请输出分数以及该分数的排名。
     *
     * 其中，如果分数相同，则需要的排名也相同，例如两个并列第二，则下一个名次就是第四名。
     *
     * 例如：
     * 输入： 100,99,99,98,97,95,95,80
     * 输出： 100:1,99:2,99:2,98:4,97:5,95:6,95:6,80:8
     *
     * 注意：分数相同时，需要输出相同的名次（并列第几），而后面的名次为前面名次总人数+1。比如如果存在两个并列第二，则第四个为4.
     *
     * 所以上列中名次为 1 2 2 4 5 6 6 8
     *
     * 输入格式
     * 99,98,100,99,97.5,95,95,80
     * 输出格式
     * 99:2,98:4,100:1,99:2,97.5:5,95:6,95:6,80:8
     *
     * 输入输出样例
     * 输入 #1复制
     * 99,98,100,99,97.5,95,95,80,99,98,100,95,95.33
     * 输出 #1复制
     * 99:3,98:6,100:1,99:3,97.5:8,95:10,95:10,80:13,99:3,98:6,100:1,95:10,95.33:9
     * 说明/提示
     * 输入的分数只有一行，最多不超过100个数字
     * 输入按人类打分的思维习惯，如果为整数，则不会带小数点。也就是输入分数为100,95.5,88.33这三种形式，不会有100.0， 95.50这样的形式，输出也需要保持原有的分数形式。
     * 输入的分数并没有完全按照从高到低的排序。 输出是也不能改变原有的排序位置，只需要在该分数后面标记该分数属于第几名。
     * 输出形式为:
     * 分数1:名次1,分数2:名次2,分数3:名次3
     * 均为英文的逗号和分号，输入输出均不能有中文的标点符号。
     */
    private static void T388005() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String in = cin.nextLine();
            String[] input_str = in.split(",");
            double[] input_double = new double[input_str.length];
            for (int i = 0; i < input_str.length; i++) {
                input_double[i] = Double.valueOf(input_str[i]);
            }
            //排序
            Arrays.sort(input_double);
            //计算排序后的序号
            Map<Double, Integer> map = new HashMap<>();
            int rank = 1;
            double last = -1;
            for (int i = input_double.length - 1; i >= 0; i--) {
                if (last != input_double[i]) {
                    rank = input_double.length - i;
                }
                last = input_double[i];
                map.put(input_double[i], rank);
            }
            //在原始输入和排序后的序号直接进行查找匹配
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < input_str.length; i++) {
                double t = Double.valueOf(input_str[i]);
                res.append(input_str[i] + ":" + map.get(t));
                if (i != input_str.length - 1) {
                    res.append(",");
                }
            }
            System.out.println(res.toString());
        }
        cin.close();
    }

    /**
     * 题目描述
     * 计算两个整数之和。
     *
     * 输入格式
     * 两个空格分开的整数，分别表示A和B。
     *
     * 输出格式
     * 一个整数，表示A+B的结果。
     */
    private static void T387999() {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String in = cin.nextLine();
            String[] nums = in.split(" ");
            if (nums == null && nums.length != 2) continue;
            int res = Integer.valueOf(nums[0]) + Integer.valueOf(nums[1]);
            System.out.println(String.valueOf(res));
        }
        cin.close();
    }

}
