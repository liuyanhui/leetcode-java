package CCProgrammingContest;

import java.util.Scanner;

/**
 * 题目描述
 * 某银行推出一年期贷款产品，基准利率为 4.35%，根据客户的信用等级，不同客户的实际贷款利率可以上下浮动 30%，银行向某客户贷款P元，分12期还款，前十一期每个月可以灵活还款，但是不得低于当期应还利息（当期应还利息为 剩余本金*月利率)，最后一个月偿还所有本金和当期产生的利息。
 * 现在已知某客户贷款p元，并根据银行的利率做出12期还款计划，求银行给该客户的实际年化利率R。
 * 注意：该利率为单利，其核心为当月的利息永远是剩余本金*月利率，且须按月还掉当月产生的利息，从而不会有滞纳金、罚金等出现。
 *
 * 输入条件为: P,x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12
 * 输出：r
 *
 * 输入
 * 100000,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99,8530.99
 * 输出
 * 4.35
 *
 * 提示:
 * 该产品为一年期，输入永远是13个数，并且输入的数据一定都满足最小还款要求，不需要考虑罚息，滞纳金等额外情况。
 * 该利息计算方式灵活还款，但每月有最小还款要求，要求每个月还得钱超过当月产生的利息，这样的方式可以涵盖 先息后本、等额本息，等额本金，各种灵活还款等情况。
 * 每月偿还的最低金额为剩余金额*月利率（四舍五入保持到分），月利率=年利率/12，年利率四舍五入保留小数点两位。
 * 注意，输出金额时，考虑钱的最小面值只能是分，所以金额需保留到小数点两位。
 * 如果年化利率为4%，应该输出为4.00，不要输出0.0400，也不需要后面的%符号。
 * 再次提示：该计算方法为单利计算方式，非央行推荐的IRR复利计算方式。
 */
public class V2022Question1 {
    public static void main(String args[]) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
//            System.out.println(cin.next());
            double res = 0;
            double[] repayments = new double[12];
            String[] items = cin.next().split(",");
            double principal = Double.parseDouble(items[0]);

            for (int i = 0; i < repayments.length; i++) {
                repayments[i] = Double.parseDouble(items[i + 1]);
            }

            int lowRate = (int) (435 * 0.7 + 0.5), highRate = (int) (435 * 1.3 + 0.5);
            double lowestRepayment = 0;
            //从最低利率到最高利率依次遍历计算，属于穷举法
            for (int i = lowRate; i <= highRate; i++) {
                double total = principal;//本金重置
                double rateByMonth = (double) i / 10000 / 12;//月利率
                for (int j = 0; j < repayments.length; j++) {
                    //当月的还最低金额，仅包含利息部分
                    lowestRepayment = total * rateByMonth;
                    //计算剩余本金，下月还款时的本金
                    total = total - (repayments[j] - lowestRepayment);
                    if (Double.compare(lowestRepayment, repayments[j]) > 0) {
                        break;
                    }
                    //最后一个的还款结束后，本金应该是0
                    if (j == repayments.length - 1 && Double.compare(total, -0.01) >= 0 && Double.compare(total, 0.01) <= 0)
                        res = i;
                }
                if (res > 0) break;
            }
            System.out.println(res / 100);
        }
        cin.close();
    }
}
