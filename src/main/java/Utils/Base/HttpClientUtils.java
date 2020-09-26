package Utils.Base;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Author：Sharkey
 * Date：2020/08/18
 * Request请求工具类
 */

public class HttpClientUtils {

    /**
     * 发送Get请求，返回String
     * @param url
     * @param param
     * @return
     */
    public static String httpGetString(String url, JSONObject param){
        return httpGetString(url, param, true);
    }


    /**
     * 发送Get请求，根据needResponse来决定是否返回字符串，若为false，返回null
     * @param url
     * @param param
     * @param needResponse
     * @return
     */
    public static String httpGetString(String url, JSONObject param, boolean needResponse){
        HttpClient httpClient = HttpClientBuilder.create().build();
        String str = "";
        HttpGet method = new HttpGet(url);
        try{
            if(null != param){
                for(JSONObject.Entry<String, Object> entry : param.entrySet()){
                    method.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            HttpResponse result = httpClient.execute(method);
            try{
                str = EntityUtils.toString(result.getEntity());
                String encode = EncodeUtils.getEncoding(str);
                if(!(encode.equals("utf-8") || encode.equals("UTF-8") || encode.equals("GB2312"))){
                    str = new String(str.getBytes(encode), StandardCharsets.UTF_8);
                }
                if(!needResponse){
                    return "";
                }
                return str;
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 发送Get请求，返回JSONObject
     * @param url
     * @param param
     * @return
     */
    public static JSONObject httpGetJSONObject(String url, JSONObject param){
        return JsonUtils.JsonToObject(httpGetString(url, param, true));
    }


    /**
     * 发送Get请求，根据needResponse来返回类型，若为false,返回null
     * @param url
     * @param param
     * @param needResponse
     * @return
     */
    public static JSONObject httpGetJSONObject(String url, JSONObject param, boolean needResponse){
        return JsonUtils.JsonToObject(httpGetString(url, param, needResponse));
    }


    /**
     * 发送Post请求，返回根据needResponse来返回字符串，若为false,返回null
     * @param url
     * @param param
     * @param needResponse
     * @return
     */
    public static String httpPostString(String url, JSONObject param, boolean needResponse){
        HttpClient httpClient = HttpClientBuilder.create().build();
        String str = "";
        HttpPost method = new HttpPost(url);
        try{
            if(null != param){
                for(JSONObject.Entry<String, Object> entry : param.entrySet()){
                    method.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            HttpResponse result = httpClient.execute(method);
            try{
                str = EntityUtils.toString(result.getEntity());
                String encode = EncodeUtils.getEncoding(str);
                if(!(encode.equals("utf-8") || encode.equals("UTF-8"))){
                    str = new String(str.getBytes(encode), StandardCharsets.UTF_8);
                }
                if(!needResponse){
                    return "";
                }
                return str;
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 发送Post请求，返回String
     * @param url
     * @param param
     * @return
     */
    public static String httpPostString(String url, JSONObject param){
        return httpPostString(url, param, true);
    }


    /**
     * 发送Post请求，返回JSONObject
     * @param url
     * @param param
     * @return
     */
    public static JSONObject httpPostJSONObject(String url, JSONObject param){
        return JsonUtils.JsonToObject(httpPostString(url, param, true));
    }


    /**
     * 发送Post请求，返回根据needResponse来返回JSONObject，若为false,返回null
     * @param url
     * @param param
     * @param needResponse
     * @return
     */
    public static JSONObject httpPostJSONObject(String url, JSONObject param, boolean needResponse){
        return JsonUtils.JsonToObject(httpPostString(url, param, needResponse));
    }

}
