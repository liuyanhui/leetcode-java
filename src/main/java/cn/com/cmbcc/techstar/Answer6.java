package cn.com.cmbcc.techstar;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Answer6 {
    //线程池，分别发送post请求，解析，打印

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        for (String serialStr : args) {
            executorService.submit(new PostRequestCallable(serialStr));
        }
    }

    static class PostRequestCallable implements Callable<Integer> {

        String urlPattern = "https://exam.cmbccdn.cn/{0}.xml";
        String url;

        public PostRequestCallable(String number) {
            url = urlPattern.replace("{0}", number);
        }


        public Integer call() throws Exception {

            HttpPost post = null;
            HttpGet httpGet = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();

                post = new HttpPost(url);
                httpGet = new HttpGet(url);

                HttpResponse response = httpClient.execute(post);

                // 检验返回码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    //解析
//                    String tmpStr = EntityUtils.toString(response.getEntity(), "utf-8");
//                    System.out.println(tmpStr);

                    InputStream is = response.getEntity().getContent();
                    decodeXML(is);
                } else {
                    System.out.println(-1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (post != null) {
                    try {
                        post.releaseConnection();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return -1;
        }

        public void decodeXML(InputStream inputStream) throws DocumentException {
            // 解析xml文件
            // 创建SAXReader的对象reader
            SAXReader reader = new SAXReader();
            try {
                // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
                Document document = reader.read(inputStream);
                System.out.println("1111111");
                System.out.println(JSON.toJSONString(document.getPath()));
                System.out.println(JSON.toJSONString(document.toString()));
//                List<Node> nodes = document.selectNodes("//IDCARD/CIDCardTemplate[1]");
//                System.out.println(JSON.toJSONString(nodes));
//                node1.get.getName();
                System.out.println("2222222222");
//                System.out.println(document.getRootElement().getData());
                Node node1 = document.selectSingleNode("/IDCARD/CIDCardTemplate/vecAutoProcess/PerspectiveTransform"); //name的元素
                System.out.println(node1.getText());
//                Element elemnt__ = (Element) node1;//强制转化为Element
//                System.out.println(elemnt__.getName());
//                System.out.println(JSON.toJSONString(node1.getText()));

                System.out.println("9999999999");
//                Element root = document.getRootElement();
//                // 得到根元素的所有子节点
//                List<Element> elementList = root.elements();
//                System.out.println(JSON.toJSONString(elementList));

//                Element root = document.getRootElement();//得到根节点
//                String rootName = root.getName();
//                System.out.println("根节点:" + rootName);
//                Element element1 = (Element) root.elementIterator().next();//通过循环来得到
//                String xpath = "/" + rootName + "/" + element1.getName();
//                System.out.println("xpath：" + xpath);
//                Element element2 = root.element(element1.getName());//通过节点name来得到该节点Element
//                System.out.println("通过element2方法类得到节点：" + element2.getName());
//
//                Element element3 = (Element) element2.elementIterator().next();//通过节点name来得到该节点Element
//                System.out.println("通过element3方法类得到节点：" + element3.getName());
//
//                Element element4 = (Element) element3.elementIterator().next();//通过节点name来得到该节点Element
//                System.out.println("通过element4方法类得到节点：" + element4.getName());
//
//                Element element5 = (Element) element4.elementIterator().next();//通过节点name来得到该节点Element
//                System.out.println("通过element5方法类得到节点：" + element5.getName());
//
//                Element element6 = (Element) element5.elementIterator().next();//通过节点name来得到该节点Element
//                System.out.println("通过element6方法类得到节点：" + element6.getName());
//
//
//                Node node = document.selectSingleNode(xpath);
//                System.out.println("---------");
//                System.out.println("通过selectSingleNode来得到node：" + node.getName());

//
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

}
