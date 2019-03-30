package cn.com.cmbcc.techstar;

import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Answer8 {


    public static void main(String[] args) {

        if(null==args||args.length!=1){
            System.out.print("param error,please input one param");
            System.exit(1);
        }
        getCertExpired(args[0]);
    }

    public static void getCertExpired(String httpsUrl) {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new Answer8().new NullHostNameVerifier());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            URL url = new URL(httpsUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(5000);
            conn.connect();
            Certificate[] certificates= conn.getServerCertificates();
            X509Certificate x509Certificate = (X509Certificate)certificates[0];
            System.out.println("证书版本:" + x509Certificate.getVersion());
            System.out.println("证书编号:" + x509Certificate.getSerialNumber());
            System.out.println("颁发机构:" + x509Certificate.getSubjectDN().getName());
            System.out.println("颁发者:" + x509Certificate.getIssuerDN().getName());
            System.out.println("证书开始时间:" + x509Certificate.getNotBefore());
            System.out.println("有效期止" + x509Certificate.getNotAfter());
            System.out.println("签名算法:" + x509Certificate.getSigAlgName());
            System.out.println("证书公钥:" + x509Certificate.getPublicKey());
            System.out.println("证书签名:" + x509Certificate.getSignature());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    } };
    public class NullHostNameVerifier implements HostnameVerifier {
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }
}
