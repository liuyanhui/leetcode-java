package CCProgrammingContest.V2022Question2;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 题目描述
 * 卡中心现在所有日志都归集到ELK当中，对于指定的某个APP项目都有一个特定的查询接口，可以查询所有日志。
 * 现在设定依据某些特征关键词，每次查询1分钟内该词出现的频率，如果查询到该错误出现，并且出现一定的频率，则需要触发报警。
 * 同时，为了不过多的报警，相同关键词在报警后，相邻的5分钟内将不再报警。超过5分钟，如果该错误仍旧出现，则需要继续报警。
 * 请编程实现其中的报警逻辑。
 *
 * 输入为：
 * 第一行为报警规则设定，格式为：
 * 关键词1,报警触发阈值1,报警间隔时间1|关键词2,报警触发阈值2,报警间隔时间2|关键词3,报警触发阈值3,报警间隔时间3
 * (报警关键词设定不超过10个)。
 * 第二行以后，是一行一行具体的错误日志，以单独的一行-1为结束 比如：
 * 2022-10-19 09:49:10.418 198.203.183.33 9101 INFO  1837014085 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.f.AbstractFireflyFilter doFilterInternal 73 - startProcess this:com.cmbc.firefly.server.filter.FireflyFilter@33841bee,filterName:fireflyFilter,env :com.cmbc.firefly.server.environment.StandardWebEnvironment@185497be,HandlerManagercom.cmbc.firefly.server.handler.manager.StandardDelegatingHandlerManager@59b6476c,HandlerChain:SimpleTwoWayChain{deque=[com.cmbc.firefly.server.handler.security.DefaultSecurityHandler@44381c80]}
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 74 - decrypt_info:transId:addEnergy,appId:credit,encrytype:null
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 88 - keyRule:
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 122 - decrypt type:AES
 * 2022-10-19 09:49:10.441 198.203.183.33 9101 INFO  1837014108 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.StarDreamMfManager addEnergy 98 - ----*询到任务ID：T0000000000088974561----*务页签：2
 * -1
 *
 * 日志的格式
 * %d{yyyy-MM-dd HH:mm:ss.SSS} %X{localAddr} %X{localPort} %-5level %-4r %thread %X{productionMode} %X{method} %X{requestURIWithQueryString} [f=%X{functionId}, tid=%X{transactionId}, ip=%X{remoteAddr}, sid=%X{cookie.cmbcId}]%n %logger{35} %method %line - %m%n
 *
 * 注意：输出的消息体m有可能为多行，且实际日志虽然已脱敏，但所有的监测关键词肯定都没有脱敏，不需要考虑脱敏情况。另外，日志输出可能会换行，并且在新行输出同样格式的时间，但不会同时出现 “时间 ip 端口” 三个项，也就是判断是否为新的日志，需要结合“为新的一行且开始一定是时间 IP 端口” 四个元素判断。
 *
 * 若没有任何需要报警的日志，则输出“系统正常”四个字（输出没有引号）
 *
 * 输入
 * SocketTimeoutException,2,5|ServletException,5,5|doResolveHandlerMethodException,1,1
 * 2022-10-19 09:49:10.342 198.203.183.33 9101 INFO  1837014009 catalina-exec-165  POST /mbank/credit/queryNoLoginStatus.json [f=, tid=, ip=117.80.159.60, 117.80.159.60, 198.203.187.34, sid=]
 * c.c.f.s.f.AbstractFireflyFilter doFilterInternal 156 - {\"service-info\":{\"method\":\"POST\",\"transId\":\"queryNoLoginStatus\",\"url\":\"/mbank/credit/queryNoLoginStatus.json\",\"start\":\"2022-10-19 09:49:10.313\",\"end\":\"2022-10-19 09:49:10.342\",\"rstime\":29}}
 * 2022-10-19 09:49:10.416 198.203.183.33 9101 INFO  1837014083 catalina-exec-444  POST /mbank/credit/getHomePageByHometag.json [f=getHomePageByHometag, tid=, ip=117.93.134.105, 117.93.134.105, 198.203.187.33, sid=6310522E78E1AF387B14B691A8A9AED7]
 *  com.cmbcc.credit.util.CommTrans sendHttpGetWithRequestHeader 825 - ===HttpGetWithHeader response：{\"status\": 200, \"statusMsg\": \"\\u5904\\u7406\\u6210\\u529f\", \"result\": {\"E12\": {\"recdata\": [{\"itemid\": \"I0000000000000398290\"}, {\"itemid\": \"I0000000000000381724\"}, {\"itemid\": \"I0000000000000408246\"}, {\"itemid\": \"I0000000000000384964\"}, {\"itemid\": \"I0000000000000401510\"}, {\"itemid\": \"I0000000000000403738\"}], \"request_id\": \"166614cccc0807\"}, \"use_time\": 102699}}
 * 2022-10-19 09:49:10.417 198.203.183.33 9101 INFO  1837014084 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.f.AbstractFireflyFilter doFilterInternal 60 - test-FireflyFilter:com.cmbc.firefly.server.filter.FireflyFilter@33841bee
 * 2022-10-19 09:49:10.418 198.203.183.33 9101 INFO  1837014085 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.f.AbstractFireflyFilter doFilterInternal 73 - startProcess this:com.cmbc.firefly.server.filter.FireflyFilter@33841bee,filterName:fireflyFilter,env :com.cmbc.firefly.server.environment.StandardWebEnvironment@185497be,HandlerManagercom.cmbc.firefly.server.handler.manager.StandardDelegatingHandlerManager@59b6476c,HandlerChain:SimpleTwoWayChain{deque=[com.cmbc.firefly.server.handler.security.DefaultSecurityHandler@44381c80]}
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 74 - decrypt_info:transId:addEnergy,appId:credit,encrytype:null
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 88 - com.cmbc.mbank.test.HConnectionManager java.util.concurrent.ExecutionException: java.net.SocketTimeoutException: Call to ubuntu/0:0:0:0:0:0:0:1:60020 failed on socket timeout exception: java.net.SocketTimeoutException: 60000 millis timeout while waiting for channel to be ready for read. ch : java.nio.channels.SocketChannel[connected local=/0:0:0:0:0:0:0:1:42426 remote=ubuntu/0:0:0:0:0:0:0:1:60020]
 *      at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:222)
 *      at java.util.concurrent.FutureTask.get(FutureTask.java:83)
 *      at org.apache.hadoop.hbase.client.HConnectionManager$HConnectionImplementation.processBatchCallback(HConnectionManager.java:1553)
 *      at org.apache.hadoop.hbase.client.HConnectionManager$HConnectionImplementation.processBatch(HConnectionManager.java:1376)
 *      at org.apache.hadoop.hbase.client.HTable.flushCommits(HTable.java:937)
 *      at org.apache.hadoop.hbase.client.HTable.doPut(HTable.java:793)
 *      at org.apache.hadoop.hbase.client.HTable.put(HTable.java:768)
 *      at updateTest.put(updateTest.java:61)
 *      at updateTest.main(updateTest.java:96)
 * 2022-10-19 09:49:10.419 198.203.183.33 9101 INFO  1837014086 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.f.s.s.p.s.AbstractSecureProtection doPreProtect 122 - decrypt type:AES
 * 2022-10-19 09:49:10.421 198.203.183.33 9101 INFO  1837014088 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.t.f.c.h.TeslaHttpRequestHandlingController actualDoHandleRequest 348 - Request Payload: {request={body={taskId=T0000000000088974561, uuid=9b5384c1ff24e6bf6dfe62d0aef52c59}, header={appId=credit, noLoginToken=317a3c5065ea05a91daf40847da5a10df74e8cbfd2a8da2dee839fa88d85e91754f93cc3779f27705164c9176085a60d&25f39671e04fe4a020632283b864100b41f036ac08fb867aa5b61c5ca6f96bbbbb08316a3eb8731bced7beeb14ccf8ae, cmbcId=8E4B691960B0413AFED08F8D2A8B37E3, appVersion=9.0.0, clientSessionId=0570410AA0A26D37D84EE185C21C3AC1, transId=addEnergy, reqSeq=g4X3kxyUR0adSLL2tKE0Sw==, device={model=COL-AL10, osVersion=9, imei=9b5384c1ff24e6bf6dfe62d0aef52c59, isRoot=0, nfc=1, brand=HONOR, uuid=9b5384c1ff24e6bf6dfe62d0aef52c59, osType=02, tdid=3f9341b249ecec56d67ff2ba36fad9475}, appEdition=unionPay, net={netType=MOBILE_3gnet, ssid=<unknown ssid>, ip=10.143.39.220}}}}
 * 2022-10-19 09:49:10.432 198.203.183.33 9101 INFO  1837014099 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.CreditCardManager getAllCreditCardList 68 - user session MK50list: [{cardProduct=6405, companyName=, perCardFlag=1, cardStatus=0, field=, cardNo=622602cccc3278, mainCardNo=622602cccc3278, MaxCardLevel=40, cardProCode=14Q117, annFeeCode=27, cardModelFlag=01, MasterFlag=1, cardActDate=20180120, banTradeFlag=2, name=*萍, accountType=0010, sendCardDate=20180115, cardLev=40, vaildDate=2301, vCardStatus=}]
 * 2022-10-19 09:49:10.434 198.203.183.33 9101 INFO  1837014101 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.f.dreamPage.StarDreamFunction addEnergy 212 - *取账户*所有的*片为：[{cardProduct=6405, companyName=, perCardFlag=1, cardStatus=0, field=, cardNo=622602cccc3278, mainCardNo=622602cccc3278, MaxCardLevel=40, cardProCode=14Q117, annFeeCode=27, cardModelFlag=01, MasterFlag=1, cardActDate=20180120, banTradeFlag=2, name=*萍, accountType=0010, sendCardDate=20180115, cardLev=40, vaildDate=2301, vCardStatus=}]
 * 2022-10-19 09:49:10.434 198.203.183.33 9101 INFO  1837014101 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.f.dreamPage.StarDreamFunction addEnergy 222 - *询卡片*息：{cardProduct=6405, companyName=, perCardFlag=1, cardStatus=0, field=, cardNo=622602cccc3278, mainCardNo=622602cccc3278, MaxCardLevel=40, cardProCode=14Q117, annFeeCode=27, cardModelFlag=01, MasterFlag=1, cardActDate=20180120, banTradeFlag=2, name=*萍, accountType=0010, sendCardDate=20180115, cardLev=40, vaildDate=2301, vCardStatus=}
 * 2022-10-19 09:49:10.435 198.203.183.33 9101 INFO  1837014102 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 * c.c.c.f.dreamPage.StarDreamFunction addEnergy 225 - *片状态：0>>*否主卡：1
 * 2022-10-19 09:49:10.436 198.203.183.33 9101 INFO  1837014103 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.f.dreamPage.StarDreamFunction addEnergy 228 - *取任意*张卡片*态正常*主卡卡*为622602cccc3278
 * 2022-10-19 09:49:10.437 198.203.183.33 9101 INFO  1837014104 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.StarDreamMfManager addEnergy 69 - *前用户*息idNo:12011419cccc0327>>>idType:01
 * 2022-10-19 09:49:10.439 198.203.183.33 9101 INFO  1837014106 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.StarDreamMfManager addEnergy 90 - com.cmbc.mbank.test.HConnectionManager java.util.concurrent.ExecutionException: java.net.SocketTimeoutException: Call to ubuntu/0:0:0:0:0:0:0:1:60020 failed on socket timeout exception: java.net.SocketTimeoutException: 60000 millis timeout while waiting for channel to be ready for read. ch : java.nio.channels.SocketChannel[connected local=/0:0:0:0:0:0:0:1:42426 remote=ubuntu/0:0:0:0:0:0:0:1:60020]
 *      at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:222)
 *      at java.util.concurrent.FutureTask.get(FutureTask.java:83)
 *      at org.apache.hadoop.hbase.client.HConnectionManager$HConnectionImplementation.processBatchCallback(HConnectionManager.java:1553)
 *      at org.apache.hadoop.hbase.client.HConnectionManager$HConnectionImplementation.processBatch(HConnectionManager.java:1376)
 *      at org.apache.hadoop.hbase.client.HTable.flushCommits(HTable.java:937)
 *      at org.apache.hadoop.hbase.client.HTable.doPut(HTable.java:793)
 *      at org.apache.hadoop.hbase.client.HTable.put(HTable.java:768)
 *      at updateTest.put(updateTest.java:61)
 *      at updateTest.main(updateTest.java:96)
 * 2022-10-19 09:49:10.441 198.203.183.33 9101 INFO  1837014108 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.StarDreamMfManager addEnergy 98 - ----*询到任务ID：T0000000000088974561----*务页签：2
 * 2022-10-19 09:49:10.441 198.203.183.33 9101 INFO  1837014108 catalina-exec-98  POST /mbank/credit/addEnergy.json [f=addEnergy, tid=, ip=2408:841b:3820:4f2a:832:26c8:e079:4981, 2408:841b:3820:4f2a:832:26c8:e079:4981, 198.203.52.55, sid=8E4B691960B0413AFED08F8D2A8B37E3]
 *  c.c.c.manager.StarDreamBdManager queryStarTaskProgressService 111 - StarDreamBdManager  queryStarTaskProgressService*询大数*开始
 * -1
 *
 * 输出
 * 2022-10-19 09:49:10.439出现SocketTimeoutException错误
 *
 * 提示
 * 同一条消息中（以日期 yyyy-MM-dd HH:mm:ss.SSS 开始，并且后面是IP 端口），到下一个日志出现之前，这都是同一条日志，在同一个日志里出现的错误只能计算一次。
 * 输出报警的时间，以满足条件出现的时间（也就是最后一条日志的时间）为准， 输出格式固定为 时间+出现+关键词+错误  （中间没有任何空格或符号）。
 * 而题目中的一分钟发生的错误次数，指的是物理上的同一分钟（不需要考虑间隔），也就是51分59秒发生的错和52分15秒发生的错也认为不是一分钟的。
 * 题目中限制的间隔N分钟，说的是距离上次报警发出时间（满足报警条件的最后一条日志的时间），最后一条时间的时间需大于等于N*60*1000。
 * 以上面的样例分析如下：
 * 设置
 * SocketTimeoutException,2,5
 * 程序开始的时候
 * Time1 SocketTimeoutException
 * Time2 SocketTimeoutException
 * 若(Time2-Time1)都是9点49分，则满足一分钟出现两次错的情况，且是第一次，因此报警发出。 print输出的时间为Time2。上次报警的时间记录也为Time2。此时错误次数需要清零。
 * Time3 SocketTimeoutException
 * Time4 SocketTimeoutException
 * Time5 SocketTimeoutException
 * 后面，又出现三次错，time3,4,5不属于同一分钟（或者距离上次报警时间不超过5分钟），不满足1分钟两次的条件，不触发报警。
 * Time6 SocketTimeoutException
 * Time7 SocketTimeoutException
 * Time8 SocketTimeoutException
 *
 * 若后面又出现三次报警，（假设此时Time6,Time7,Time8属于同一分钟），假设Time7和Time2间隔大于等于5分钟，则Time7需要报警, 此时报警打印的时间为Time7(最后报警时间改为Time7，错误次数清零，开启下一次循环)。
 *
 */
public class Main {
    public static void main(String args[]) throws ParseException {
        LocalDateTime time = LocalDateTime.MIN;
        boolean runWell = true;
        String timeStr = "";
        Map<String, Alarm> keyMap = new HashMap<>();//保存关键字指标
        Map<String, TreeMap<LocalDateTime, Integer>> alertMap = new HashMap<>();//保存关键字在日志中出现的时间和次数
        Set<String> keyAppeared = new HashSet<>();//每行日志中关键字是否已经计算过
        Map<String, LocalDateTime> alertHistory = new HashMap<>();//保存已报警关键字和报警时间

        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
//            System.out.println(cin.next());
            //1.解析输入的文件，按行解析。根据关键字检索。检索时要考虑一条日志多行的情况，同一行日志出现多次只算一次。
            String rowContent = cin.nextLine();
            if (rowContent.equals("-1")) {
                break;
            }
            //解析第一行，获取报警指标数据
            if (keyMap.size() == 0) {
                for (String s : rowContent.split("\\|")) {
                    String[] arr = s.split(",");
                    keyMap.put(arr[0], new Alarm(arr[0], Integer.valueOf(arr[1]), Integer.valueOf(arr[2])));
                    alertHistory.put(arr[0], LocalDateTime.MIN);
                }
                continue;
            }

            //解析日志数据
            String[] contentArray = rowContent.split(" ");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            //表示是新日志行
            if (contentArray.length > 4
                    && contentArray[0].split("-").length == 3 && contentArray[1].split(":").length == 3
                    && contentArray[2].split("\\.").length == 4
                    && contentArray[3].matches("^\\d+")) {
                //清空key是否在当前行出现过的缓存
                keyAppeared.clear();
                //提取日志时间并格式化
                timeStr = contentArray[0] + " " + contentArray[1];
                time = LocalDateTime.parse(timeStr, dtf);
            }
            //依次从关键字map中匹配
            for (String key : keyMap.keySet()) {
                //当前key在该行日志已经加入报警Map，那么忽略；否则，加入报警
                if (!keyAppeared.contains(key) && rowContent.contains(key)) {
                    LocalDateTime timeWithMinite = LocalDateTime.of(time.getYear(),time.getMonth(),time.getDayOfMonth(),time.getHour(),time.getMinute());
                    //2.保存检索结果。格式为：{关键字:[分钟时刻:次数]}
                    saveAlertInfo(key, timeWithMinite, keyAppeared, alertMap);
                    //3.报警。记录上一次报警的时间，格式为：{关键字:时间}。根据上一次报警时间和阈值判断是否需要报警。
                    if (alertCheck(key, time, alertMap.get(key).get(timeWithMinite), keyMap, alertHistory)) {
                        alertHistory.put(key, time);
                        System.out.println(timeStr + "出现" + key + "错误");
                        runWell = false;
                    }
                }
            }
        }
        if (runWell)
            System.out.println("系统正常");
        cin.close();
    }

    private static void saveAlertInfo(String key, LocalDateTime time, Set<String> keyAppeared, Map<String, TreeMap<LocalDateTime, Integer>> alertMap) {
        keyAppeared.add(key);
        alertMap.putIfAbsent(key, new TreeMap<>());
        alertMap.get(key).putIfAbsent(time, 0);
        alertMap.get(key).put(time, alertMap.get(key).get(time) + 1);
    }

    private static boolean alertCheck(String key, LocalDateTime time, int freq, Map<String, Alarm> keyMap, Map<String, LocalDateTime> history) {
        if (!keyMap.containsKey(key)) return false;
        LocalDateTime allow = LocalDateTime.from(time).minusMinutes(keyMap.get(key).interval);
        if (keyMap.get(key).limit <= freq && history.get(key).isBefore(allow)) {
            return true;
        }
        return false;
    }

    static class Alarm {
        String key;
        int limit;
        int interval;

        public Alarm(String key, int limit, int interval) {
            this.key = key;
            this.limit = limit;
            this.interval = interval;
        }
    }
}
