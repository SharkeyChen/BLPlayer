package Utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

/**
 * Author：Sharkey
 * Date：2020/08/18
 */

public class HttpClientUtils {
    public static String sendPost(String urlParam) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "application/json");

        httpClient.executeMethod(postMethod);

        String result = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        return result;
    }

    public static String sendGet(String urlParam) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置post请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");
        httpClient.executeMethod(getMethod);

//        String result = getMethod.getResponseBodyAsString();
        InputStream in  = getMethod.getResponseBodyAsStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = -1;
        while((len = in.read(bytes)) != -1){
            result.write(bytes,0, len);
        }
        in.close();
        getMethod.releaseConnection();
        return result.toString();
    }

    public static void DownLoadVideos(String url) throws IOException {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Origin", " https://www.bilibili.com");
        getMethod.addRequestHeader("Referer", "https://www.bilibili.com");

        httpClient.executeMethod(getMethod);

        File file = new File("C:\\Users\\DELL\\Desktop\\123.flv");
        if(!file.exists()){
            file.createNewFile();
        }
        try{
            FileOutputStream fos = new FileOutputStream(file);
            InputStream in  = getMethod.getResponseBodyAsStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len = in.read(bytes)) != -1){
                fos.write(bytes, 0 , len);
            }
            in.close();
            fos.close();
        }catch (Exception e){

        }
    }

}
