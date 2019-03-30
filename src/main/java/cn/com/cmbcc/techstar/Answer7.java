package cn.com.cmbcc.techstar;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class Answer7 {

    @Autowired
    private UserInfoMapper userInfoMapper;

    static ExecutorService executorService = Executors.newFixedThreadPool(100);

    //密钥 (需要前端和后端保持一致)
    public static final String KEY = "729edc668823083b7477a848cfb78571";
    //算法
    private static final String ALGORITHMSTR = "AES";

    @RequestMapping(value = {"/encrypt"})
    @ResponseBody
    public void convert() {
        Instant msBeg = Instant.now();

        int beg = 10000000;
        int end = 10000002;
        List<UserInfo> list = userInfoMapper.getListByParam(beg, end);
        Instant msBeg2 = Instant.now();
        System.out.println("cost1 : " + Duration.between(msBeg, msBeg2).toMillis());

        list.stream().parallel().forEach(info -> info.setEncrymobile(encrypt(info.getMobile())));
        Instant msBeg3 = Instant.now();
        System.out.println("cost2 : " + Duration.between(msBeg2, msBeg3).toMillis());

        userInfoMapper.updateBatch(list);

        Instant msEnd = Instant.now();
        System.out.println("cost3 : " + Duration.between(msBeg3, msEnd).toMillis());

        System.out.println("cost finall : " + Duration.between(msBeg, msEnd).toMillis());

    }

    @RequestMapping(value = {"/encrypt2"})
    @ResponseBody
    public void multiProcess() {
        Instant msBeg = Instant.now();
        long maxID = userInfoMapper.getMaxID();
        System.out.println("maxID=" + maxID);
        AtomicLong atomicLong = new AtomicLong();
        long step = 10000;
        while (atomicLong.get() < maxID) {
            long beg = atomicLong.get();
            long end = atomicLong.addAndGet(step);
            executorService.submit(new GetThenEncryptCallable(beg, end));

        }

        Instant msEnd = Instant.now();
        System.out.println("cost finall : " + Duration.between(msBeg, msEnd).toMillis());
    }

    class GetThenEncryptCallable implements Callable<Integer> {

        long _beg;
        long _end;

        public GetThenEncryptCallable(long beg, long end) {
            _beg = beg;
            _end = end;
        }

        @Override
        public Integer call() throws Exception {
            Instant msBeg = Instant.now();

            List<UserInfo> list = userInfoMapper.getListByParam(_beg, _end);
            if (CollectionUtils.isEmpty(list)) {
                return 0;
            }

            list.stream().parallel().forEach(info -> info.setEncrymobile(encrypt(info.getMobile())));
            userInfoMapper.updateBatch(list);

            Instant msEnd = Instant.now();
            System.out.println(" _beg=" + _beg + "_end=" + _end + "  cost: " + Duration.between(msBeg, msEnd).toMillis());
            return 1;
        }
    }


    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHMSTR);
            kgen.init(128, new SecureRandom(KEY.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHMSTR);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        encrypt("1234567890");
    }


}
